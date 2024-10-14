package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.Flag;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SynchronizePlayerPositionS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.PLAYER_POSITION_PLAY_S2C;
    double x;
    double y;
    double z;
    float yaw;
    float pitch;
    Flag flags;
    int teleportID;

    public SynchronizePlayerPositionS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSynchronizePlayerPosition(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
        this.flags = new Flag(buf.readByte());
        teleportID = PacketUtil.readVarInt(buf);
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

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Flag getFlags() {
        return flags;
    }

    public int getTeleportID() {
        return teleportID;
    }
}
