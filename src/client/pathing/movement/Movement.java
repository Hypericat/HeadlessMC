package client.pathing.movement;

import client.game.Block;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.openset.MutableMoveResult;
import math.Vec3i;

import java.util.Arrays;
import java.util.List;

public abstract class Movement {
    public static final Vec3i[] offsetStanding = new Vec3i[] {Vec3i.ZERO, Vec3i.ZERO.addY(1)};

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

    protected static double getMiningDurationTicks(CalculationContext ctx, Block block, boolean includeFalling) {
        if (block.hasNoCollision()) return 0;
        if (block.isFluid()) return ActionCosts.COST_INF;
        if (block.isUnbreakable()) return ActionCosts.COST_INF;
        int tickDuration = ctx.getBlockBreakTickCache().getMiningTickCount(ctx, block, includeFalling);
        if (tickDuration <= 0) return ActionCosts.COST_INF;
        return tickDuration + ActionCosts.MINE_PENALTY;
    }
}