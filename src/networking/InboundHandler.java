package networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import utils.PacketUtil;

public class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf buf) {
        NetworkHandler.debugBuf(buf);
        int packetSize = PacketUtil.readVarInt(buf);
        int packetType = PacketUtil.readVarInt(buf);
        System.out.println(PacketUtil.readString(buf));
    }
}
