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
    public static final Vec3i[] validOffsets = new Vec3i[] {Vec3i.ZERO, Vec3i.ZERO.addY(1)};

    public MovementDiagonal(Vec3i centerPos, Vec3i endPos) {
        super(centerPos, endPos);
    }

    @Override
    public double cost(CalculationContext ctx, MutableMoveResult res, Moves move) {
        res.x = getEndPos().getX();
        res.y = getCenterPos().getY();
        res.z = getEndPos().getZ();
        boolean isValid = isValidBlock(ctx, getEndPos().getX(), getCenterPos().getY(), getEndPos().getZ()) && isValidBlock(ctx, getCenterPos().withZ(getEndPos().getZ())) && isValidBlock(ctx, getCenterPos().withX(getEndPos().getX()));
        res.cost = isValid ? ActionCosts.WALK_ONE_BLOCK_COST * Vec3d.of(getCenterPos()).distanceTo(new Vec3d(getEndPos().getX(), getCenterPos().getY(), getEndPos().getZ())) : ActionCosts.COST_INF;
        Moves.applyFloatingPenalty(ctx, res, move);
        return res.cost;
    }

    @Override
    public List<Vec3i> getValidCheckOffsets() {
        List<Vec3i> offsets = new ArrayList<>();
        return offsets;
    }

}
