package client.pathing;

import math.Vec3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Path extends PathBase {

    private final Vec3i start;
    private final Vec3i end;
    private final List<Vec3i> path;

    private final List<PathNode> nodes;

    private final Goal goal;

    private final int numNodes;

    private volatile boolean verified;

    Path(Vec3i realStart, PathNode start, PathNode end, int numNodes, Goal goal) {
        this.start = realStart;
        this.end = new Vec3i(end.x, end.y, end.z);
        this.numNodes = numNodes;
        this.goal = goal;

        PathNode current = end;
        List<Vec3i> tempPath = new ArrayList<>();
        List<PathNode> tempNodes = new ArrayList<>();
        while (current != null) {
            tempNodes.add(current);
            tempPath.add(new Vec3i(current.x, current.y, current.z));
            current = current.previous;
        }
        this.path = tempPath.reversed();
        this.nodes = tempNodes.reversed();
    }

    @Override
    public Goal getGoal() {
        return goal;
    }

    @Override
    public List<IMovement> movements() {
        return List.of();
    }

    @Override
    public List<Vec3i> positions() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public IPath postProcess() {
        return super.postProcess();
    }

    @Override
    public int length() {
        return super.length();
    }

    @Override
    public int getNumNodesConsidered() {
        return numNodes;
    }

    @Override
    public Vec3i getSrc() {
        return start;
    }

    @Override
    public Vec3i getDest() {
        return end;
    }

    @Override
    public double ticksRemainingFrom(int pathPosition) {
        return super.ticksRemainingFrom(pathPosition);
    }

    @Override
    public void sanityCheck() {
        super.sanityCheck();
    }
}
