package client.pathing;

import math.Vec3i;

public class PathNode {

    public final int x;
    public final int y;
    public final int z;

    public final double heuristic;
    public double cost;
    public double combinedCost;
    public PathNode previous;
    public int heapPosition;

    public PathNode(int x, int y, int z, Goal goal) {
        this.previous = null;
        this.cost = ActionCosts.COST_INF;
        this.heuristic = goal.heuristic(x, y, z);
        if (Double.isNaN(heuristic)) {
            throw new IllegalStateException(goal + " calculated implausible heuristic");
        }
        this.heapPosition = -1;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isOpen() {
        return heapPosition != -1;
    }

    public int hashCode() {
        return (int) Vec3i.longHash(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        PathNode other = (PathNode) obj;
        return x == other.x && y == other.y && z == other.z;
    }
}