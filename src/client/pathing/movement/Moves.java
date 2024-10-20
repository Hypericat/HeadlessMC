package client.pathing.movement;

import client.game.Blocks;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.movement.movements.*;
import client.pathing.openset.MutableMoveResult;

public enum Moves {
    DOWNWARD(0, -1, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementDownward.costDown(context, x, y, z);
        }
    },

    UPWARD(0, +1, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementUpward.costUp(context, x, y, z);
        }
    },

    TRAVERSE_NORTH(0, 0, -1) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementTraverse.costTraverse(context, x, y, z, x, z - 1);
        }
    },

    TRAVERSE_SOUTH(0, 0, +1) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementTraverse.costTraverse(context, x, y, z, x, z + 1);
        }
    },

    TRAVERSE_EAST(+1, 0, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementTraverse.costTraverse(context, x, y, z, x + 1, z);
        }
    },

    TRAVERSE_WEST(-1, 0, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return MovementTraverse.costTraverse(context, x, y, z, x - 1, z);
        }
    },

    DIAGONAL_NORTHEAST(+1, 0, -1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            MovementDiagonal.costDiagonal(context, x, y, z, x + 1, z - 1, result);
        }
    },

    DIAGONAL_NORTHWEST(-1, 0, -1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            MovementDiagonal.costDiagonal(context, x, y, z, x - 1, z - 1, result);
        }
    },

    DIAGONAL_SOUTHEAST(+1, 0, +1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            MovementDiagonal.costDiagonal(context, x, y, z, x + 1, z + 1, result);
        }
    },

    DIAGONAL_SOUTHWEST(-1, 0, +1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            MovementDiagonal.costDiagonal(context, x, y, z, x - 1, z + 1, result);
        }
    };

    public final boolean dynamicXZ;
    public final boolean dynamicY;

    public final int xOffset;
    public final int yOffset;
    public final int zOffset;

    Moves(int x, int y, int z, boolean dynamicXZ, boolean dynamicY) {
        this.xOffset = x;
        this.yOffset = y;
        this.zOffset = z;
        this.dynamicXZ = dynamicXZ;
        this.dynamicY = dynamicY;
    }

    Moves(int x, int y, int z) {
        this(x, y, z, false, false);
    }

    public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
        if (dynamicXZ || dynamicY) {
            throw new UnsupportedOperationException();
        }
        result.x = x + xOffset;
        result.y = y + yOffset;
        result.z = z + zOffset;
        result.cost = cost(context, x, y, z);
        applyFloatingPenalty(context, result);
    }

    public static void applyFloatingPenalty(CalculationContext context, MutableMoveResult result) {
        if (isFloating(context, result.x, result.y, result.z)) {
            //floating penalty
            float floatingPenaltyMultiplier = 2;
            result.cost = result.cost * floatingPenaltyMultiplier;
        }
    }

    public static boolean isFloating(CalculationContext context, int x, int y, int z) {
        return context.getWorld().getBlock(x, y - 1, z) == Blocks.AIR;
    }

    public double cost(CalculationContext context, int x, int y, int z) {
        throw new UnsupportedOperationException();
    }
}
