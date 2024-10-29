/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package client.pathing.movement.movements;


import client.game.Block;
import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.movement.BlockBreakTickCache;
import client.pathing.movement.Movement;
import client.pathing.movement.Moves;
import client.pathing.openset.MutableMoveResult;
import math.Vec3d;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class MovementTraverse extends Movement {
    public static final Vec3i[] validOffsets = new Vec3i[] {Vec3i.ZERO, Vec3i.ZERO.addY(1)};

    public MovementTraverse(Vec3i centerPos, Vec3i endPos) {
        super(centerPos, endPos);
    }

    @Override
    public double cost(CalculationContext ctx, MutableMoveResult res, Moves move) {
        return isValidBlock(ctx, getEndPos().getX(), getCenterPos().getY(), getEndPos().getZ()) ? ActionCosts.WALK_ONE_BLOCK_COST * Vec3d.of(getCenterPos()).distanceTo(new Vec3d(getEndPos().getX(), getCenterPos().getY(), getEndPos().getZ())) : ActionCosts.COST_INF;
    }

    @Override
    public List<Vec3i> getValidCheckOffsets() {
        List<Vec3i> offsets = new ArrayList<>();
        for (Vec3i vec : validOffsets) {
            offsets.add(vec.add(getCenterPos()));
        }
        return offsets;
    }

    static double getMiningDurationTicks(CalculationContext ctx, Vec3i blockPos, Block block, boolean includeFalling) {
        if (block.hasNoCollision()) return 0;
        if (block.isFluid()) return ActionCosts.COST_INF;
        int tickDuration = BlockBreakTickCache.getMiningTickCount(ctx, blockPos, block, includeFalling);
        if (tickDuration <= 0) return ActionCosts.COST_INF;
        return tickDuration;
    }
}
