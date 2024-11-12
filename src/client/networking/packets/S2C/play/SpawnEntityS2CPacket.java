package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import java.util.UUID;
import io.netty.buffer.ByteBuf;

public class SpawnEntityS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.ADD_ENTITY_PLAY_S2C;
    private int entityID;
    private UUID entityUuid;
    private int entityType;
    private double x;
    private double y;
    private double z;
    private byte pitch;
    private byte yaw;
    private byte headYaw;
    private int data;
    private short velocityX;
    private short velocityY;
    private short velocityZ;

    public SpawnEntityS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSpawnEntity(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        entityID = PacketUtil.readVarInt(buf);
        entityUuid = PacketUtil.readUUID(buf);
        entityType = PacketUtil.readVarInt(buf);
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        pitch = buf.readByte();
        yaw = buf.readByte();
        headYaw = buf.readByte();
        data = PacketUtil.readVarInt(buf);
        velocityX = buf.readShort();
        velocityY = buf.readShort();
        velocityZ = buf.readShort();
    }

    public int getEntityID() {
        return entityID;
    }

    public UUID getEntityUuid() {
        return entityUuid;
    }

    public int getEntityType() {
        return entityType;
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

    public byte getPitch() {
        return pitch;
    }

    public byte getYaw() {
        return yaw;
    }

    public byte getHeadYaw() {
        return headYaw;
    }

    public int getData() {
        return data;
    }

    public short getVelocityX() {
        return velocityX;
    }

    public short getVelocityY() {
        return velocityY;
    }

    public short getVelocityZ() {
        return velocityZ;
    }
}
