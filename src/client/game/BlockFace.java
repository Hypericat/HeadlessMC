package client.game;

import math.Vec3d;

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

    public static BlockFace fromVec(Vec3d vec, Vec3d direction) {
        double minOverlap = Math.min(vec.x, Math.min(vec.y, vec.z));
        System.out.println("delta vector " + vec);

        if (minOverlap == vec.x) return (direction.x > 0) ? BlockFace.EAST : BlockFace.WEST;
        if (minOverlap == vec.y) return (direction.y > 0) ? BlockFace.UP : BlockFace.DOWN;
        return (direction.z > 0) ? BlockFace.SOUTH : BlockFace.NORTH;
    }
}
