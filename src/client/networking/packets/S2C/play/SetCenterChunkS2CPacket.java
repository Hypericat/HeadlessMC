package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.PacketHandler;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetCenterChunkS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.SET_CHUNK_CACHE_CENTER_PLAY_S2C;

    int chunkX;
    int chunkZ;

    public SetCenterChunkS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetCenterChunk(this);
    }

    public PacketID getPacketID() {
        return packetID;
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
