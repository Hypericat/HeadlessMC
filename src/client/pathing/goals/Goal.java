package client.pathing.goals;

import math.Vec3i;

public interface Goal {
    boolean isInGoal(int x, int y, int z);
    double heuristic(int x, int y, int z);

    default boolean isInGoal(Vec3i pos) {
        return isInGoal(pos.getX(), pos.getY(), pos.getZ());
    }

    default double heuristic(Vec3i pos) {
        return heuristic(pos.getX(), pos.getY(), pos.getZ());
    }

    default double heuristic() {
        return 0;
    }
}
