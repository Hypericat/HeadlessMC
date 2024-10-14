package client.networking.packets.C2S.configuration;

import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;

public class StatusRequestC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.STATUS_REQUEST_STATUS_C2S;

    public StatusRequestC2SPacket() {

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
