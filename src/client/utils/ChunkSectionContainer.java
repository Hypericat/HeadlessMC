package client.utils;

import client.game.Block;
import client.game.Blocks;
import io.netty.buffer.ByteBuf;
import math.Pair;
import math.Vec3i;

public class ChunkSectionContainer {

    int simpleID = -1;
    int[] mappings;
    long[] data;
    int bpe;
    private final int y;

    public ChunkSectionContainer(int y) {
        this.y = y;
    }

    public void readPacket(ByteBuf buf) {
        //bpe
        bpe = buf.readByte();
        if (bpe == 0) {
            readPacketSimple(buf);
            return;
        }
        if (bpe >= 4 && bpe <= 8) {
            readPacketIndirect(buf);
            return;
        }
        if (bpe >= 15 && bpe <= 31) {
            readPacketDirect(buf);
            return;
        }
        throw new IllegalArgumentException("Invalid bpe of " + bpe + " provided!");
    }
    private void readPacketSimple(ByteBuf buf) {
        simpleID = PacketUtil.readVarInt(buf);
        mappings = null;
        data = null;
        buf.readByte();
    }

    private void readPacketDirect(ByteBuf buf) {
        simpleID = -1;
        mappings = null;
        int dataLength = PacketUtil.readVarInt(buf);
        this.data = new long[dataLength];
        for (int i = 0; i < dataLength; i++) {
            this.data[i] = buf.readLong();
        }
    }

    private void readPacketIndirect(ByteBuf buf) {
        simpleID = -1;
        int paletteLength = PacketUtil.readVarInt(buf);
        mappings = new int[paletteLength];
        for (int i = 0; i < paletteLength; i++) {
            mappings[i] = PacketUtil.readVarInt(buf);
        }
        int dataLength = PacketUtil.readVarInt(buf);
        this.data = new long[dataLength];
        for (int i = 0; i < dataLength; i++) {
            this.data[i] = buf.readLong();
        }
    }

    public int getIdAt(int x, int y, int z) {
        if (simpleID == -1 && data == null) throw new RuntimeException("Both Simple ID and Data have no data!");
        if (simpleID != -1) return simpleID;
        if (x < 0 || y < 0 || z < 0) return Blocks.AIR.getStates().getDefault(); // return air if invalid position
        int dataIndex = x + z * 16 + y * 256; // calculate the index of the block from the coordinates
        int dataPerLong = Long.SIZE / bpe; // calculate how many blocks we store per long
        int longIndex = dataIndex / dataPerLong; // calculate the index of the long which contains our data
        long l = data[longIndex]; // get this long
        int firstBitIndex = dataIndex % dataPerLong * bpe; // get the index of the first bit from the 64 bit long
        int bits = BitUtils.getBits(firstBitIndex, bpe, l); // get the bits with the block ID
        if (mappings == null) {
            return bits; // return the block ID, if mapping is null we are using direct therefore the bits should be the block ID
        }
        if (bits < 0 || bits > mappings.length) { // sanity check
            throw new IllegalArgumentException("Invalid mapping length received");
        }
        return mappings[bits]; // return the block ID from the index of the mappings, this means we are using indirect.

    }
    public void setAtID(int x, int y, int z, int blockID) {
        if (simpleID != -1) {
            simpleToIndirect();
        }
        int dataIndex = x + z * 16 + y * 256;
        int dataPerLong = Long.SIZE / bpe;
        int longIndex = dataIndex / dataPerLong;
        if (longIndex >= data.length) {
            expandData();
        }
        long l = data[longIndex];
        int firstBitIndex = dataIndex % dataPerLong * bpe;
        if (mappings == null) {
            data[longIndex] = BitUtils.setBits(firstBitIndex, bpe, l, blockID);
            return;
        }
        //int index = getMappingIndex(blockID);

        data[longIndex] = BitUtils.setBits(firstBitIndex, bpe, l, getMappingIndex(blockID));
    }
    public void fillAtIDs(Pair<Block, Vec3i>[] blocks) {
        if (simpleID != -1) {
            simpleToIndirect();
        }
        for (Pair<Block, Vec3i> pair : blocks) {
            int blockID = pair.getLeft().getStates().getDefault();
            Vec3i blockPos = pair.getRight();

            int dataIndex = blockPos.getX() + blockPos.getZ() * 16 + blockPos.getY() * 256;
            int dataPerLong = Long.SIZE / bpe;
            int longIndex = dataIndex / dataPerLong;
            if (longIndex >= data.length) {
                expandData();
            }
            long l = data[longIndex];
            int firstBitIndex = dataIndex % dataPerLong * bpe;
            if (mappings == null) {
                data[longIndex] = BitUtils.setBits(firstBitIndex, bpe, l, blockID);
                return;
            }
            data[longIndex] = BitUtils.setBits(firstBitIndex, bpe, l, getMappingIndex(blockID));
        }
    }

    private int getMappingIndex(int blockID) {
        //this should probably update bpe but idc
        for (int i = 0; i < mappings.length; i++) {
            if (mappings[i] == blockID) return i;
        }
        int[] temp = new int[mappings.length + 1];
        System.arraycopy(mappings, 0, temp, 0, mappings.length);
        mappings = temp;
        mappings[mappings.length - 1] = blockID;
        return mappings.length - 1;
    }
    private void expandData() {
        long[] temp = new long[data.length + 1];
        System.arraycopy(data, 0, temp, 0, data.length);
        data = temp;
    }

    private void simpleToIndirect() {
        mappings = new int[]{simpleID};
        bpe = 1;
        int dataPerLong = Long.SIZE / bpe;
        int longCount = (int) Math.ceil(4096d / dataPerLong);
        data = new long[longCount];
        long currentLong = 0L;
        for (int i = 0; i < dataPerLong; i++) {
            currentLong = BitUtils.setBits(i * bpe, bpe, currentLong, 0);
        }

        for (int i = 0; i < longCount; i++) {
            data[i] = currentLong;
        }
        simpleID = -1;
    }

    public int getY() {
        return y;
    }
}
