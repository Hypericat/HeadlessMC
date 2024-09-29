package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import io.netty.buffer.ByteBuf;

public class PlayerMovePosC2SPacket extends C2SPacket {
    public static final int typeID = 0x1A;

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
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeBoolean(onGround);
    }
}
