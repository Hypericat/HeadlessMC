package client.networking.packets.S2C.play;

import client.game.Block;
import client.game.Blocks;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import client.utils.Pair;
import client.utils.Vec3i;
import io.netty.buffer.ByteBuf;

public class UpdateBlockSectionS2CPacket extends S2CPacket {
    public static final int typeID = 0x49;
    public final static NetworkState networkState = NetworkState.PLAY;
    Pair<Block, Vec3i>[] blocks;
    private Vec3i chunkPos;

    public UpdateBlockSectionS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onBlockSectionUpdate(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        long chunkPos = buf.readLong();
        int chunkX = (int) (chunkPos >> 42);
        int chunkY = (int) (chunkPos << 44 >> 44);
        int chunkZ = (int) (chunkPos << 22 >> 42);
        this.chunkPos = new Vec3i(chunkX, chunkY, chunkZ);

        int blockArraySize = PacketUtil.readVarInt(buf);
        blocks = new Pair[blockArraySize];
        System.out.println("Array size : " + blockArraySize);

        for (int i = 0; i < blockArraySize; i++) {
            long data = PacketUtil.readVarLong(buf);
            int blockStateId = (int) (data >> 12);
            int x = (int) ((data >> 8) & 0xF);
            int y = (int) (data & 0xF);
            int z = (int) ((data >> 4) & 0xF);
            Vec3i pos = new Vec3i(x, y, z);
            blocks[i] = new Pair<>(Blocks.getBlockByID(blockStateId), pos);
        }
    }

    public Pair<Block, Vec3i>[] getBlocks() {
        return blocks;
    }

    public Vec3i getChunkPos() {
        return this.chunkPos;
    }


}
