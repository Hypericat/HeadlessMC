package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetEntityVelocityS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.SET_ENTITY_MOTION_PLAY_S2C;
    private int entityID;
    private double x;
    private double y;
    private double z;

    public SetEntityVelocityS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetEntityVelocity(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.entityID = PacketUtil.readVarInt(buf);
        this.x = buf.readShort() / 8000d;
        this.y = buf.readShort() / 8000d;
        this.z = buf.readShort() / 8000d;
    }

    public int getEntityID() {
        return entityID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
