package client.networking;

import client.HeadlessInstance;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import client.networking.packets.C2S.C2SPacket;

import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class NetworkHandler {
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;
    private NetworkState networkState;
    private HeadlessInstance instance;
    private boolean compressionEnabled;
    private int compressionThreshold;
    private boolean connected;

    public static final boolean logIn = false;
    public static final boolean logOut = false;

    public NetworkHandler(HeadlessInstance instance) {
        networkState = NetworkState.HANDSHAKE;
        compressionEnabled = false;
        this.connected = true;
        this.compressionThreshold = -1;
        this.instance = instance;
    }

    public void sendPacket(C2SPacket packet) {
        if (channel == null || !channel.isActive()) return;
        sendPacket(packet, channel);
    }

    public void sendPacket(C2SPacket packet, Channel channel) {
        if (logOut) {
            logPacket(Boundness.C2S, packet.getTypeID());
        }

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
        int readerIndex = buf.readerIndex();
        buf.readBytes(data);
        buf.readerIndex(readerIndex);
        return Unpooled.buffer().writeBytes(compress(data)).readerIndex(0);
    }

    public byte[] compress(byte[] buf) {
        Deflater deflater;
        deflater = new Deflater(7);
        deflater.setInput(buf);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] outputBuffer = new byte[1024];

        while (!deflater.finished()) {
            int bytesCompressed = deflater.deflate(outputBuffer);
            byteArrayOutputStream.write(outputBuffer, 0, bytesCompressed);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public ByteBuf decompress(ByteBuf buf) {
        byte[] data = new byte[buf.readableBytes()];
        int readerIndex = buf.readerIndex();
        buf.readBytes(data);
        buf.readerIndex(readerIndex);
        return Unpooled.buffer().writeBytes(decompress(data)).readerIndex(0);
    }

    protected void logPacket(Boundness boundness, int packetID) {
        instance.getLogger().debug((boundness == Boundness.C2S ? "Sending packet : " : "Receiving packet : ") + PacketIDS.get(packetID, getNetworkState(), boundness).getIdentifier());
    }

    public byte[] decompress(byte[] buf) {
        Inflater inflater = new Inflater();
        inflater.setInput(buf);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] outputBuffer = new byte[1024];
        while (!inflater.finished()) {
            int bytesCompressed;
            try {
                bytesCompressed = inflater.inflate(outputBuffer);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
            byteArrayOutputStream.write(outputBuffer, 0, bytesCompressed);
        }
        inflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    public HeadlessInstance getInstance() {
        return instance;
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
            //instance.getLogger().debug("Sending compressed packet bigger than threshold");
        } else {
            PacketUtil.writeVarInt(dataLength, 0);
            PacketUtil.writeVarInt(sizeBuf,dataLength.readableBytes() + dataBuf.readableBytes());
            //instance.getLogger().debug("Sending compressed packet smaller than threshold");

        }
        channel.write(sizeBuf);
        channel.write(dataLength);
        channel.writeAndFlush(dataBuf);
    }

    public void onDisconnect() {
        channel.close().awaitUninterruptibly();
        this.connected = false;
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
        instance.getLogger().logUser("Attempting to connect to server!");
        try {
            ChannelFuture future = bootstrap.connect(address, port).sync();
            channel = future.channel();
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        instance.getLogger().logUser("Successfully connected to server!");
    }

    public Channel getChannel() {
        return channel;
    }
    public boolean isConnected() {
        return this.connected;
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
