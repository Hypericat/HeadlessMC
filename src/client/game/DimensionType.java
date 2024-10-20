package client.game;

public enum DimensionType {
    OVERWORLD(-64, 320),
    NETHER(0, 255),
    END(0, 255);

    private final int minY;
    private final int maxY;

    DimensionType(int minY, int maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getHeight() {
        return maxY - minY;
    }
}
