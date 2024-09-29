package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import io.netty.buffer.ByteBuf;

public class PlayerMoveFullC2SPacket extends C2SPacket {
    public static final int typeID = 0x1B;

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
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(onGround);
    }
}
