package client.pathing;

import client.pathing.goals.Goal;
import math.Vec3i;

import java.util.HashSet;
import java.util.List;

public interface IPath {

    List<Vec3i> positions();

    default IPath postProcess() {
        throw new UnsupportedOperationException();
    }

    default int length() {
        return positions().size();
    }

    Goal getGoal();

    int getNumNodesConsidered();

    default Vec3i getSrc() {
        return positions().get(0);
    }

    default Vec3i getDest() {
        List<Vec3i> pos = positions();
        return pos.get(pos.size() - 1);
    }

    default IPath cutoffAtLoadedChunks(IWorldProvider world) {
        throw new UnsupportedOperationException();
    }

    default IPath staticCutoff(Goal destination) {
        throw new UnsupportedOperationException();
    }


    default void sanityCheck() {
        List<Vec3i> path = positions();
        if (!getSrc().equals(path.get(0))) {
            throw new IllegalStateException("Start node does not equal first path element");
        }
        if (!getDest().equals(path.get(path.size() - 1))) {
            throw new IllegalStateException("End node does not equal last path element");
        }
        HashSet<Vec3i> seenSoFar = new HashSet<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Vec3i src = path.get(i);
            Vec3i dest = path.get(i + 1);
            if (seenSoFar.contains(src)) {
                throw new IllegalStateException("Path doubles back on itself, making a loop");
            }
            seenSoFar.add(src);
        }
    }
}
