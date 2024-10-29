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


import client.HeadlessInstance;
import client.pathing.CalculationContext;
import client.pathing.movement.Movement;
import client.pathing.movement.Moves;
import client.pathing.openset.MutableMoveResult;
import math.Vec3i;

import java.util.List;

public class MovementFall extends Movement {

    public MovementFall(Vec3i centerPos, Vec3i endPos) {
        super(centerPos, endPos);
    }

    @Override
    public double cost(CalculationContext ctx, MutableMoveResult res, Moves move) {
        return 0;
    }

    @Override
    public List<Vec3i> getValidCheckOffsets() {
        return List.of();
    }
}
