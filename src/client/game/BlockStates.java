package client.game;

public class BlockStates {

    private final int blockIDStart;
    private final int blockIDEnd;
    private final int defaultID;

    public BlockStates(int blockID) {
        this(blockID, blockID);
    }
    public BlockStates(int blockIDStart, int blockIDEnd) {
        this(blockIDStart, blockIDEnd, blockIDStart);
    }
    public BlockStates(int blockIDStart, int blockIDEnd, int defaultID) {
        if (blockIDStart > blockIDEnd) throw new IllegalArgumentException();
        this.blockIDStart = blockIDStart;
        this.blockIDEnd = blockIDEnd;

        if (!isID(defaultID)) throw new IllegalArgumentException();
        this.defaultID = defaultID;
    }

    public int getStateCount() {
        return blockIDEnd - blockIDStart + 1;
    }

    public boolean isID(int id) {
        return id >= blockIDStart && id <= blockIDEnd;
    }

    public int getDefault() {
        return defaultID;
    }

    public int[] getIds() {
        int[] ids = new int[getStateCount()];
        int index = 0;
        for (int i = blockIDStart; i <= blockIDEnd; i++) {
            ids[index] = i;
            index++;
        }
        return ids;
    }
}
