package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class UpdateEntityPositionS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.MOVE_ENTITY_POS_PLAY_S2C;

    private int entityID;
    private short deltaX;
    private short deltaY;
    private short deltaZ;
    private boolean onGround;

    public UpdateEntityPositionS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onUpdateEntityPos(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        entityID = PacketUtil.readVarInt(buf);
        deltaX = buf.readShort();
        deltaY = buf.readShort();
        deltaZ = buf.readShort();
        onGround = buf.readBoolean();
    }

    public int getEntityID() {
        return entityID;
    }

    public short getDeltaX() {
        return deltaX;
    }

    public short getDeltaY() {
        return deltaY;
    }

    public short getDeltaZ() {
        return deltaZ;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
