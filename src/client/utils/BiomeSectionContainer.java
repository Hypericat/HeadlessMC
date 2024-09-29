package client.utils;

import io.netty.buffer.ByteBuf;

public class BiomeSectionContainer {

    int simpleID = -1;
    int[] mappings;
    long[] data;
    int bpe;
    private final int y;

    public BiomeSectionContainer(int y) {
        this.y = y;
    }

    public void readPacket(ByteBuf buf) {
        //bpe
        bpe = buf.readByte();
        if (bpe == 0) {
            readPacketSimple(buf);
            return;
        }
        if (bpe >= 1 && bpe <= 3) {
            readPacketIndirect(buf);
            return;
        }
        if (bpe >= 6) {
            readPacketDirect(buf);
            return;
        }
        throw new IllegalArgumentException("Invalid bpe provided!");
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

    public static int getBits(int bitIndexStart, int length, long l) {
        return (int) (l >>> bitIndexStart & (1L << length) - 1L);
    }

    public int getIdAt(int x, int y, int z) {
        if (true) return -1;

        //this coded needs changing if needed
        if (simpleID == -1 && data == null) throw new RuntimeException("Simple ID and Data have no data!");
        if (simpleID != -1) return simpleID;
        int dataIndex = x + z * 16 + y * 256;
        int dataPerLong = Long.SIZE / bpe;
        int longIndex = dataIndex / dataPerLong;
        long l = data[longIndex];
        int firstBitIndex = dataIndex % dataPerLong * bpe;
        int bits = getBits(firstBitIndex, bpe, l);
        if (mappings == null) {
            return bits;
        }
        if (bits < 0 || bits > mappings.length) {
            throw new IllegalArgumentException("Invalid mapping length received");
        }
        return mappings[bits];

    }
    public int getY() {
        return y;
    }
}
