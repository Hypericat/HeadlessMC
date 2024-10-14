package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class UpdateEntityRotationS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.MOVE_ENTITY_ROT_PLAY_S2C;
    private int entityID;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    public UpdateEntityRotationS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onUpdateEntityRotation(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        entityID = PacketUtil.readVarInt(buf);
        yaw = buf.readByte();
        pitch = buf.readByte();
        onGround = buf.readBoolean();
    }

    public int getEntityID() {
        return entityID;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public byte getYaw() {
        return yaw;
    }

    public byte getPitch() {
        return pitch;
    }
}
