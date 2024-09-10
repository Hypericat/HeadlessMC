package networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import networking.packets.s2c.StatusResponseS2CPacket;
import utils.PacketUtil;

public class InboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf buf) {
        NetworkHandler.debugBuf(buf);
        int packetSize = PacketUtil.readVarInt(buf);
        int idk = buf.readByte();
        int packetType = PacketUtil.readVarInt(buf);
        System.out.println(PacketUtil.readString(buf));
        ClientPacketListener listener =
        switch (packetType) {
            case StatusResponseS2CPacket.typeID -> new StatusResponseS2CPacket().apply();
        }

    }

    //@Override
    //protected void messageReceived(ChannelHandlerContext ctx, Packet<?> packet) {
    //    ByteBuf buf = Unpooled.buffer();
    //    packet.encode(buf);
    //    int packetSize = PacketUtil.readVarInt(buf);
    //    int packetType = PacketUtil.readVarInt(buf);
    //    System.out.println(PacketUtil.readString(buf));
    //}
}
