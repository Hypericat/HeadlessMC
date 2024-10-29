package client.pathing.movement;

import client.pathing.CalculationContext;
import client.pathing.openset.MutableMoveResult;
import math.Vec3i;

import java.util.Arrays;
import java.util.List;

public abstract class Movement {
    protected Movement(Vec3i centerPos, Vec3i endPos) {
        this.centerPos = centerPos;
        this.endPos = endPos;
    }
    private final Vec3i centerPos;
    private final Vec3i endPos;

    public Vec3i getCenterPos() {
        return centerPos;
    }

    public Vec3i getEndPos() {
        return endPos;
    }

    public abstract double cost(CalculationContext ctx, MutableMoveResult res, Moves move);
    public abstract List<Vec3i> getValidCheckOffsets();

    public double cost(CalculationContext ctx, Moves move) {
        return cost(ctx, null, move);
    }

    public static boolean isValidBlock(CalculationContext ctx, int x, int y, int z) {
        return isValidBlock(ctx, new Vec3i(x, y, z));
    }

    public static boolean isValidBlock(CalculationContext ctx, Vec3i pos) {
        return ctx.getWorld().getBlock(pos).hasNoCollision();
    }

    public List<Vec3i> findInvalid(CalculationContext ctx) {
        return getValidCheckOffsets().stream().filter(vec3i -> !isValidBlock(ctx, vec3i)).toList();
    }
}