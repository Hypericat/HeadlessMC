package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.PacketHandler;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetCenterChunkS2CPacket extends S2CPacket {
    public static final int typeID = 0x54;
    public final static NetworkState networkState = NetworkState.PLAY;
    int chunkX;
    int chunkZ;

    public SetCenterChunkS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetCenterChunk(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.chunkX = PacketUtil.readVarInt(buf);
        this.chunkZ = PacketUtil.readVarInt(buf);
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
