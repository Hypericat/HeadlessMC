package networking;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkHandler {
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;

    public NetworkHandler() {

    }

    public void sendPacket(Packet<?> packet) {
        if (channel == null || !channel.isActive()) return;
        sendPacket(packet, channel);
    }

    public void sendPacket(Packet<?> packet, Channel channel) {
        System.out.println("Sending packet!");
        ByteBuf buf = Unpooled.buffer();
        ByteBuf sizeBuf = Unpooled.buffer();

        packet.encode(buf);
        sizeBuf.writeInt(buf.readableBytes());


        channel.writeAndFlush(sizeBuf);
        channel.writeAndFlush(buf);
    }

    public void connect(String address, int port) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new InboundHandler());
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
}
