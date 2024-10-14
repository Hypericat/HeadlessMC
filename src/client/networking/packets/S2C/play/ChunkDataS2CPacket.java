package client.networking.packets.S2C.play;

import client.game.Chunk;
import client.networking.ClientPacketListener;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.BiomeSectionContainer;
import client.utils.ChunkSectionContainer;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ChunkDataS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.LEVEL_CHUNK_WITH_LIGHT_PLAY_S2C;

    private Chunk chunk;

    public ChunkDataS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onChunkData(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        int chunkX = buf.readInt();
        int chunkZ = buf.readInt();
        chunk = new Chunk(chunkX, chunkZ);

        int typeID = buf.readByte();

        for (int i = 0; i < 2; i++) {
            int itemTypeID = buf.readByte();
            int nameLength = buf.readShort();
            String name = PacketUtil.readString(buf, nameLength);

            int arrayLength = buf.readInt();
            for (int j = 0; j < arrayLength; j++) {
                buf.readLong();
            }
        }
        //Tag end
        buf.readByte();

        int size = PacketUtil.readVarInt(buf);
        byte[] chunkBuf = new byte[size];

        buf.readBytes(chunkBuf);
        ByteBuf chunkData = Unpooled.wrappedBuffer(chunkBuf);
        //buf.readerIndex(buf.readerIndex() + size);

        int worldHeight = 384;
        for (int i = 1; i <= worldHeight >> 4; i++) {
            chunkData.readShort();
            ChunkSectionContainer chunkSectionContainer = new ChunkSectionContainer(i << 4);
            chunkSectionContainer.readPacket(chunkData);
            chunk.addSection(chunkSectionContainer);

            BiomeSectionContainer biome = new BiomeSectionContainer(i << 4);
            biome.readPacket(chunkData);
        }
    }

    public Chunk getChunk() {
        return this.chunk;
    }
}
