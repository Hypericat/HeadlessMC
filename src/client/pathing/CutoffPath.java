package client.pathing;

import math.Vec3i;

import java.util.Collections;
import java.util.List;

public class CutoffPath extends PathBase {

    private final List<Vec3i> path;

    private final int numNodes;

    private final Goal goal;

    public CutoffPath(IPath prev, int firstPositionToInclude, int lastPositionToInclude) {
        path = prev.positions().subList(firstPositionToInclude, lastPositionToInclude + 1);
        numNodes = prev.getNumNodesConsidered();
        goal = prev.getGoal();
        sanityCheck();
    }

    public CutoffPath(IPath prev, int lastPositionToInclude) {
        this(prev, 0, lastPositionToInclude);
    }

    @Override
    public Goal getGoal() {
        return goal;
    }

    @Override
    public List<Vec3i> positions() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public int getNumNodesConsidered() {
        return numNodes;
    }
}
