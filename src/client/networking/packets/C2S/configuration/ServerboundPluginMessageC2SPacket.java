package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ServerboundPluginMessageC2SPacket extends C2SPacket {
    public final static int typeID = 0x02;
    public ServerboundPluginMessageC2SPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(getTypeId());
        PacketUtil.writeString(buf, "minecraft:vanilla");
    }
}
