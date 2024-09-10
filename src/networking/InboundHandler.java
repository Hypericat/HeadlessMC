package networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        System.out.println("Received bytes " + buf.toString());
    }
}
