package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;

public class PlayerMovePosC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.MOVE_PLAYER_POS_PLAY_C2S;


    double x;
    double y;
    double z;
    boolean onGround;

    public PlayerMovePosC2SPacket(double x, double feetY, double z, boolean onGround) {
        this.x = x;
        this.y = feetY;
        this.z = z;
        this.onGround = onGround;
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
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeBoolean(onGround);
    }
}
