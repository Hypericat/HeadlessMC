package client.networking.packets.C2S.play;

public enum BlockFace {
    BOTTOM(0),
    TOP(1),
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
}
