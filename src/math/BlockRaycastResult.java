package math;

import client.networking.packets.C2S.play.BlockFace;

public class BlockRaycastResult {
    private final Vec3i blockPos;
    private final BlockFace face;
    public static BlockRaycastResult NULL = new BlockRaycastResult(null, null);

    private BlockRaycastResult(Vec3i blockPos, BlockFace face) {
        this.blockPos = blockPos;
        this.face = face;
    }

    public static BlockRaycastResult of(Vec3i blockPos, BlockFace face) {
        return new BlockRaycastResult(blockPos, face);
    }

    public Vec3i getBlockPos() {
        return blockPos;
    }

    public BlockFace getFace() {
        return face;
    }

    public boolean isNull() {
        return blockPos == null && face == null;
    }
}
