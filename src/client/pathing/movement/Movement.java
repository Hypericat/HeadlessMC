package client.pathing.movement;

import client.game.Blocks;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.openset.MutableMoveResult;
import math.Vec3d;
import math.Vec3i;

public abstract class Movement {
    public static double costTraverse(CalculationContext context, int x, int y, int z, int destX, int destZ) {
        return isValidPos(context, destX, y, destZ) ? ActionCosts.WALK_ONE_BLOCK_COST * new Vec3d(x, y, z).distanceTo(new Vec3d(destX, y, destZ)) : ActionCosts.COST_INF;
    }

    public static double costDown(CalculationContext context, int x, int y, int z) {
        return isValidPos(context, x, y - 1, z) ? ActionCosts.FALL_N_BLOCKS_COST[1] : ActionCosts.COST_INF;
    }

    public static double costUp(CalculationContext context, int x, int y, int z) {
        return isValidPos(context, x, y + 1, z) ? ActionCosts.JUMP_ONE_BLOCK_COST : ActionCosts.COST_INF;
    }

    public static void costDiagonal(CalculationContext context, int x, int y, int z, int destX, int destZ, MutableMoveResult res, Moves move) {
        res.x = destX;
        res.y = y;
        res.z = destZ;
        boolean isValid = isValidPos(context, destX, y, destZ) && isValidPos(context, x, y, destZ) && isValidPos(context, destX, y, z);
        res.cost = isValid ? ActionCosts.WALK_ONE_BLOCK_COST * new Vec3d(x, y, z).distanceTo(new Vec3d(destX, y, destZ)) : ActionCosts.COST_INF;
        Moves.applyFloatingPenalty(context, res, move);
    }

    public static boolean isValidPos(CalculationContext ctx, int x, int y, int z) {
        return isValidPos(ctx, new Vec3i(x, y, z));
    }

    public static boolean isValidPos(CalculationContext ctx, Vec3i pos) {
        boolean bl = ctx.getWorld().getBlock(pos).hasNoCollision() && ctx.getWorld().getBlock(pos.add(0, 1, 0)).hasNoCollision();
        //System.out.println("Set valid pos " + pos + " to " + bl);
        return bl;
    }
}