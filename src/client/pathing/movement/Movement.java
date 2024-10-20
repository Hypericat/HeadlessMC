package client.pathing.movement;

import client.game.Blocks;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.openset.MutableMoveResult;
import math.Vec3d;

public abstract class Movement {
    public static double costTraverse(CalculationContext context, int x, int y, int z, int destX, int destZ) {
        return context.getWorld().getBlock(x, y, z) == Blocks.AIR ? ActionCosts.WALK_ONE_BLOCK_COST * new Vec3d(x, 0, z).distanceTo(new Vec3d(destX, 0, destZ)) : ActionCosts.COST_INF;
    }

    public static double costDown(CalculationContext context, int x, int y, int z) {
        return context.getWorld().getBlock(x, y, z) == Blocks.AIR ? ActionCosts.FALL_N_BLOCKS_COST[1] : ActionCosts.COST_INF;
    }

    public static void costDiagonal(CalculationContext context, int x, int y, int z, int destX, int destZ, MutableMoveResult res) {
        res.x = destX;
        res.z = destZ;
        res.cost = context.getWorld().getBlock(x, y, z) != Blocks.AIR ? ActionCosts.WALK_ONE_BLOCK_COST * new Vec3d(x, 0, z).distanceTo(new Vec3d(destX, 0, destZ)) : ActionCosts.COST_INF;
    }
}