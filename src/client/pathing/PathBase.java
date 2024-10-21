package client.pathing;

import math.Vec3i;

public abstract class PathBase implements IPath {
    @Override
    public PathBase staticCutoff(Goal destination) {
        int min = 30;
        if (length() < min) {
            return this;
        }
        if (destination == null || destination.isInGoal(getDest())) {
            return this;
        }
        double factor = 0.9;
        int newLength = (int) ((length() - min) * factor) + min - 1;
        return new CutoffPath(this, newLength);
    }

    @Override
    public PathBase cutoffAtLoadedChunks(IWorldProvider world) {
        for (int i = 0; i < positions().size(); i++) {
            Vec3i pos = positions().get(i);
            if (!world.isLoaded(pos.getX(), pos.getZ())) {
                return new CutoffPath(this, i);
            }
        }

        return this;
    }
}