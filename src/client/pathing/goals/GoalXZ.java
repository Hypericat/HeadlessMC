package client.pathing.goals;

import client.pathing.Pathfinder;
import math.Vec3d;
import math.Vec3i;

public class GoalXZ implements Goal {

    private static final double SQRT_2 = Math.sqrt(2);

    private final int x;
    private final int z;

    public GoalXZ(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public GoalXZ(Vec3i pos) {
        this.x = pos.getX();
        this.z = pos.getZ();
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return x == this.x && z == this.z;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        int xDiff = x - this.x;
        int zDiff = z - this.z;
        return calculate(xDiff, zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalXZ goal = (GoalXZ) o;
        return x == goal.x && z == goal.z;
    }

    @Override
    public int hashCode() {
        int hash = 1791873246;
        hash = hash * 222601791 + x;
        hash = hash * -1331679453 + z;
        return hash;
    }

    @Override
    public String toString() {
        return "GoalXZ : x " + x + " z " + z;
    }

    public static double calculate(double xDiff, double zDiff) {
        //This is a combination of pythagorean and manhattan distance
        //It takes into account the fact that pathing can either walk diagonally or forwards
        //It's not possible to walk forward 1 and right 2 in sqrt(5) time
        //It's really 1+sqrt(2) because it'll walk forward 1 then diagonally 1

        double x = Math.abs(xDiff);
        double z = Math.abs(zDiff);
        double straight;
        double diagonal;
        if (x < z) {
            straight = z - x;
            diagonal = x;
        } else {
            straight = x - z;
            diagonal = z;
        }
        diagonal *= SQRT_2;
        return (diagonal + straight) * Pathfinder.heuristicCost;
    }

    public static GoalXZ fromDirection(Vec3d origin, float yaw, double distance) {
        float theta = (float) Math.toRadians(yaw);
        double x = origin.x - Math.sin(theta) * distance;
        double z = origin.z + Math.cos(theta) * distance;
        return new GoalXZ((int) Math.floor(x), (int) Math.floor(z));
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
