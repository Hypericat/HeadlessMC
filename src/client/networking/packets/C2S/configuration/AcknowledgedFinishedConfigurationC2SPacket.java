package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;

public class AcknowledgedFinishedConfigurationC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.LOGIN_ACKNOWLEDGED_LOGIN_C2S;

    public AcknowledgedFinishedConfigurationC2SPacket() {

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
    }
}
