package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class RemoveEntitiesS2CPacket extends S2CPacket {
    public static final int typeID = 0x42;
    public final static NetworkState networkState = NetworkState.PLAY;
    private int[] entityIDs;

    public RemoveEntitiesS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onRemoveEntities(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        int count = PacketUtil.readVarInt(buf);
        entityIDs = new int[count];
        for (int i = 0; i < count; i++) {
            entityIDs[i] = PacketUtil.readVarInt(buf);
        }
    }

    public int[] getEntityIDs() {
        return entityIDs;
    }
}
