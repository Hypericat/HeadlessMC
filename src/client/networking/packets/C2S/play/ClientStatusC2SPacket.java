package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ClientStatusC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.RESPAWN_PLAY_S2C;

    //0 -> respawn
    //1 -> request stats
    private int status;

    public ClientStatusC2SPacket(int status) {
        if (status != 0 && status != 1) throw new IllegalArgumentException("Invalid Status Provided");
        this.status = status;
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
        PacketUtil.writeVarInt(buf, status);
    }
}
