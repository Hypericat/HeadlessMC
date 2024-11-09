package client.networking.packets.S2C.play;

import client.game.Chunk;
import client.networking.ClientPacketListener;
import client.networking.InboundHandler;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.BiomeSectionContainer;
import client.utils.ChunkSectionContainer;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnloadChunkS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.FORGET_LEVEL_CHUNK_PLAY_S2C;

    private int chunkX;
    private int chunkZ;

    public UnloadChunkS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onUnloadChunk(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        //order is inverted because the packet is normally read as big endian
        chunkZ = buf.readInt();
        chunkX = buf.readInt();
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

}
