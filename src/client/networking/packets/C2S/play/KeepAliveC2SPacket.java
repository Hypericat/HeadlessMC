package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;

public class KeepAliveC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.KEEP_ALIVE_PLAY_C2S;
    private long id;

    public KeepAliveC2SPacket(long id) {
        this.id = id;

    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeID());
        buf.writeLong(this.id);
    }
}
