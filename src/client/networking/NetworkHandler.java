package client.networking;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import client.utils.PacketUtil;
import com.jcraft.jzlib.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import client.networking.packets.C2S.C2SPacket;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibEncoder;

import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.zip.ZipOutputStream;

public class NetworkHandler {
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;
    private NetworkState networkState;
    private HeadlessInstance instance;
    private boolean compressionEnabled;
    private int compressionThreshold;

    public NetworkHandler(HeadlessInstance instance) {
        networkState = NetworkState.HANDSHAKE;
        compressionEnabled = false;
        this.compressionThreshold = -1;
        this.instance = instance;
    }

    public void sendPacket(C2SPacket packet) {
        if (channel == null || !channel.isActive()) return;
        sendPacket(packet, channel);
    }

    public void sendPacket(C2SPacket packet, Channel channel) {
        if (!isCompressionEnabled() || compressionThreshold < 0) {
            sendUncompressedPacket(packet, channel);
            return;
        }
        sendCompressedPacket(packet, channel);
    }

    private void sendUncompressedPacket(C2SPacket packet, Channel channel) {
        ByteBuf buf = Unpooled.buffer();
        ByteBuf sizeBuf = Unpooled.buffer();

        packet.encode(buf);
        PacketUtil.writeVarInt(sizeBuf, buf.readableBytes());

        channel.write(sizeBuf);
        channel.writeAndFlush(buf);
    }

    public ByteBuf compress(ByteBuf buf) {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return Unpooled.buffer().writeBytes(compress(data)).readerIndex(0);
    }

    public byte[] compress(byte[] buf) {
        Deflater deflater;
        try {
            deflater = new Deflater(7);
        } catch (GZIPException e) {
            throw new RuntimeException(e);
        }
        deflater.setInput(buf);
        deflater.setOutput(new byte[1024]);
        deflater.deflate(4);
        System.out.println("Compressing data : " + Arrays.toString(buf));
        byte[] compressedData = new byte[(int) deflater.total_out];
        System.arraycopy(deflater.next_out, 0, compressedData, 0, compressedData.length);
        System.out.println("Compressed finished data: " + Arrays.toString(compressedData));
        return compressedData;
    }

    public ByteBuf decompress(ByteBuf buf) {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return Unpooled.buffer().writeBytes(decompress(data)).readerIndex(0);
    }

    public byte[] decompress(byte[] buf) {
        Inflater inflater;
        inflater = new Inflater();
        inflater.setInput(buf);
        inflater.setOutput(new byte[1024]);
        inflater.inflate(4);

        byte[] uncompressedData = new byte[(int) inflater.total_out];
        System.arraycopy(inflater.next_out, 0, uncompressedData, 0, uncompressedData.length);
        System.out.println("Decompress data: ");
        return uncompressedData;
    }


    public void flush() {
        channel.flush();
    }

    private void sendCompressedPacket(C2SPacket packet, Channel channel) {
        ByteBuf sizeBuf = Unpooled.buffer();
        ByteBuf dataLength = Unpooled.buffer();
        ByteBuf dataBuf = Unpooled.buffer();

        packet.encode(dataBuf);

        ByteBuf compressedBuf = compress(dataBuf);
        if (compressedBuf.readableBytes() >= compressionThreshold) {
            PacketUtil.writeVarInt(dataLength, dataBuf.readableBytes());
            PacketUtil.writeVarInt(sizeBuf,dataLength.readableBytes() + compressedBuf.readableBytes());
            dataBuf = compressedBuf;
            System.out.println("Sending compressed packet bigger than threshold");
        } else {
            PacketUtil.writeVarInt(dataLength, 0);
            PacketUtil.writeVarInt(sizeBuf,dataLength.readableBytes() + dataBuf.readableBytes());
            System.out.println("Sending compressed packet smaller than threshold");

        }
        channel.write(sizeBuf);
        channel.write(dataLength);
        channel.writeAndFlush(dataBuf);
    }


    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public boolean connect(String address, int port) {
        try {
            connectInternal(address, port);
            return true;
        } catch (ConnectException e) {
            System.err.println("Failed to connect to server. Most likely server offline!");
            return false;
        }
    }
    private void connectInternal(String address, int port) throws ConnectException {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        InboundHandler handler = new InboundHandler(this, instance);
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(handler);
            }

        });
        System.out.println("Attempting to connect to server!");
        try {
            ChannelFuture future = bootstrap.connect(address, port).sync();
            channel = future.channel();
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Successfully connected to server!");
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isCompressionEnabled() {
        return compressionEnabled;
    }

    public void setCompression(int threshold) {
        this.compressionEnabled = true;
        this.compressionThreshold = threshold;
    }


    public static void debugBuf(ByteBuf buf) {
        int readIndex = buf.readerIndex();
        byte[] byteArray = new byte[buf.readableBytes()];
        buf.getBytes(readIndex, byteArray);
        buf.readerIndex(readIndex);
        debugBuf(byteArray);
    }
    public static void debugBuf(byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
    }
}
