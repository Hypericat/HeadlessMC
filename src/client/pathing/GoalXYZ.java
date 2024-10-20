package client.pathing;

import math.Vec3i;

public class GoalXYZ implements Goal {

    public final int x;
    public final int y;
    public final int z;

    public GoalXYZ(Vec3i pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public GoalXYZ(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return x == this.x && y == this.y && z == this.z;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return calculate(xDiff, yDiff, zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalXYZ goal = (GoalXYZ) o;
        return x == goal.x
                && y == goal.y
                && z == goal.z;
    }

    @Override
    public int hashCode() {
        return (int) Vec3i.longHash(x, y, z) * 905165533;
    }

    @Override
    public String toString() {
        return "BlockGoal : " + x + " " + y + " " + z;
    }

    public Vec3i getGoalPos() {
        return new Vec3i(x, y, z);
    }

    public static double calculate(double xDiff, int yDiff, double zDiff) {
        double heuristic = 0;
        //manhattan distance
        heuristic += GoalY.calculate(0, yDiff);

        //pythagorean distance and some manhattan
        heuristic += GoalXZ.calculate(xDiff, zDiff);
        return heuristic;
    }
}
