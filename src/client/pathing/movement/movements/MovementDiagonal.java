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


import client.pathing.ActionCosts;
import client.pathing.CalculationContext;
import client.pathing.movement.Movement;
import client.pathing.movement.Moves;
import client.pathing.openset.MutableMoveResult;
import math.Vec3d;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class MovementDiagonal extends Movement {

    public MovementDiagonal(Vec3i centerPos, Vec3i endPos) {
        super(centerPos, endPos);
    }

    @Override
    public double cost(CalculationContext ctx, MutableMoveResult res, Moves move) {
        res.x = getEndPos().getX();
        res.y = getCenterPos().getY();
        res.z = getEndPos().getZ();

        List<Vec3i> invalid = findInvalid(ctx);
        double cost =ActionCosts.WALK_ONE_BLOCK_COST * Vec3d.of(getCenterPos()).distanceTo(new Vec3d(getEndPos().getX(), getCenterPos().getY(), getEndPos().getZ()));
        if (invalid.isEmpty()) {
            return cost;
        }
        for (Vec3i vec3i : invalid) {
            for (Vec3i sur : vec3i.getSurrounding()) {
                if (ctx.getWorld().getBlock(sur).shouldAvoidSurrounding()) return ActionCosts.COST_INF;
            }
            double tick = getMiningDurationTicks(ctx, ctx.getWorld().getBlock(vec3i), false);
            if (tick >= ActionCosts.COST_INF) return ActionCosts.COST_INF;
            cost += tick;
        }
        res.cost = cost;
        Moves.applyFloatingPenalty(ctx, res, move);
        Moves.applyInWaterPenalty(ctx, res, move);
        return res.cost;
    }

    @Override
    public List<Vec3i> getValidCheckOffsets() {
        List<Vec3i> offsets = new ArrayList<>();
        for (Vec3i posOffset : offsetStanding) {
            offsets.add(new Vec3i(getEndPos().withY(getCenterPos().getY() + posOffset.getY())));
            offsets.add(new Vec3i(getCenterPos().getX(), getCenterPos().getY() + posOffset.getY(), getEndPos().getZ()));
            offsets.add(new Vec3i(getEndPos().getX(), getCenterPos().getY() + posOffset.getY(), getCenterPos().getZ()));
        }
        return offsets;
    }

}
