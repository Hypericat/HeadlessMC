package client.networking;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import client.networking.packets.C2S.C2SPacket;

import java.net.ConnectException;
import java.util.Arrays;

public class NetworkHandler {
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;
    private NetworkState networkState;
    private HeadlessInstance instance;
    private boolean compressionEnabled;

    public NetworkHandler(HeadlessInstance instance) {
        networkState = NetworkState.HANDSHAKE;
        compressionEnabled = false;
        this.instance = instance;
    }

    public void sendPacket(C2SPacket packet) {
        if (channel == null || !channel.isActive()) return;
        sendPacket(packet, channel);
    }

    public void sendPacket(C2SPacket packet, Channel channel) {
        if (!isCompressionEnabled()) {
            sendUncompressedPacket(packet, channel);
            return;
        }
        sendCompressedPacket(packet, channel);
    }
    private void sendUncompressedPacket(C2SPacket packet, Channel channel) {

        ByteBuf buf = Unpooled.buffer();
        ByteBuf sizeBuf = Unpooled.buffer();

        packet.encode(buf);
        sizeBuf.writeInt(buf.readableBytes());

        channel.writeAndFlush(sizeBuf);
        channel.writeAndFlush(buf);
    }

    public void flush() {
        channel.flush();
    }
    private void sendCompressedPacket(C2SPacket packet, Channel channel) {

        ByteBuf buf = Unpooled.buffer();
        ByteBuf sizeBuf = Unpooled.buffer();
        packet.encode(buf);
        sizeBuf.writeInt(buf.readableBytes());
        sizeBuf.writeInt(0);

        channel.writeAndFlush(sizeBuf);
        channel.writeAndFlush(buf);
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

    public void setCompressionEnabled(boolean compressionEnabled) {
        this.compressionEnabled = compressionEnabled;
    }

    public static void debugBuf(ByteBuf buf) {
        int readIndex = buf.readerIndex();;
        byte[] byteArray = new byte[buf.readableBytes()];
        buf.getBytes(readIndex, byteArray);
        buf.readerIndex(readIndex);
        debugBuf(byteArray);
    }
    public static void debugBuf(byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
    }
}
