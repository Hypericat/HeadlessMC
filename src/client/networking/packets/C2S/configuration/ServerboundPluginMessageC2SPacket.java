package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ServerboundPluginMessageC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.CUSTOM_PAYLOAD_CONFIGURATION_C2S;

    public ServerboundPluginMessageC2SPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeID());
        PacketUtil.writeString(buf, "minecraft:vanilla");
    }
}
