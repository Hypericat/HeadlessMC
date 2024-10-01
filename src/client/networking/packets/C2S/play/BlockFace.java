package client.networking.packets.C2S.play;

public enum BlockFace {
    DOWN(0),
    UP(1),
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5);

    private final byte value;
    BlockFace(int value) {
        this.value = (byte) value;
    }
    public byte getValue() {
        return value;
    }

    public static double getSign(BlockFace face) {
        if (face == UP || face == SOUTH || face == EAST) return 1;
        return -1;
    }
}
