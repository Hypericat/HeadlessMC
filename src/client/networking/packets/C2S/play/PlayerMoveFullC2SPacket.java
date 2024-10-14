package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;

public class PlayerMoveFullC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.MOVE_PLAYER_POS_ROT_PLAY_C2S;


    double x;
    double y;
    double z;
    float yaw;
    float pitch;
    boolean onGround;

    public PlayerMoveFullC2SPacket(double x, double feetY, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = feetY;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
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
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(onGround);
    }
}
