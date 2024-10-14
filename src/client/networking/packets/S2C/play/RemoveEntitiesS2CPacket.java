package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class RemoveEntitiesS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.REMOVE_ENTITIES_PLAY_S2C;

    private int[] entityIDs;

    public RemoveEntitiesS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onRemoveEntities(this);
    }

    public PacketID getPacketID() {
        return packetID;
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
