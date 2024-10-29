package client.pathing.movement;

import client.HeadlessInstance;
import client.game.Blocks;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.movement.movements.*;
import client.pathing.openset.MutableMoveResult;
import math.Vec3i;

public enum Moves {
    DOWNWARD(0, -1, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementDownward(new Vec3i(x, y, z), null).cost(context, this);
        }
    },

    UPWARD(0, +1, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementUpward(new Vec3i(x, y, z), null).cost(context, this);
        }
    },

    TRAVERSE_NORTH(0, 0, -1) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementTraverse(new Vec3i(x, y, z), new Vec3i(x, -1, z - 1)).cost(context, this);
        }
    },

    TRAVERSE_SOUTH(0, 0, +1) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementTraverse(new Vec3i(x, y, z), new Vec3i(x, -1, z + 1)).cost(context, this);
        }
    },

    TRAVERSE_EAST(+1, 0, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementTraverse(new Vec3i(x, y, z), new Vec3i(x + 1, -1, z)).cost(context, this);
        }
    },

    TRAVERSE_WEST(-1, 0, 0) {
        @Override
        public double cost(CalculationContext context, int x, int y, int z) {
            return new MovementTraverse(new Vec3i(x, y, z), new Vec3i(x - 1, -1, z)).cost(context, this);
        }
    },

    DIAGONAL_NORTHEAST(+1, 0, -1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            new MovementDiagonal(new Vec3i(x, y, z), new Vec3i(x + 1, -1, z - 1)).cost(context, result, this);
        }
    },

    DIAGONAL_NORTHWEST(-1, 0, -1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            new MovementDiagonal(new Vec3i(x, y, z), new Vec3i(x - 1, -1, z - 1)).cost(context, result, this);
        }
    },

    DIAGONAL_SOUTHEAST(+1, 0, +1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            new MovementDiagonal(new Vec3i(x, y, z), new Vec3i(x + 1, -1, z + 1)).cost(context, result, this);
        }
    },

    DIAGONAL_SOUTHWEST(-1, 0, +1, false, true) {
        @Override
        public void apply(CalculationContext context, int x, int y, int z, MutableMoveResult result) {
            new MovementDiagonal(new Vec3i(x, y, z), new Vec3i(x - 1, -1, z + 1)).cost(context, result, this);
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
        applyFloatingPenalty(context, result, this);
    }

    public static void applyFloatingPenalty(CalculationContext context, MutableMoveResult result, Moves move) {
        //dont remove if going down
        if (!move.dynamicY && move.yOffset < 0) {
            return;
        }

        if (isFloating(context, result.x, result.y, result.z)) {
            float floatingPenaltyMultiplier = 5;
            result.cost = result.cost * floatingPenaltyMultiplier;
        }
    }

    public static boolean isFloating(CalculationContext context, int x, int y, int z) {
        return context.getWorld().getBlock(x, y - 1, z).hasNoCollision();
    }

    public double cost(CalculationContext context, int x, int y, int z) {
        throw new UnsupportedOperationException();
    }
}
