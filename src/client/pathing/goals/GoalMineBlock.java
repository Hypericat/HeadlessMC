package client.pathing.goals;

import client.game.Block;
import client.pathing.IWorldProvider;
import math.Vec3d;
import math.Vec3i;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;

public class GoalMineBlock implements Goal {
    private final IWorldProvider world;
    private final HashSet<Block> blocks;
    private static final int BEST_COUNT = 10;
    private final Vec3i[] best;

    public GoalMineBlock(List<Block> blocks, IWorldProvider world, Vec3i start) {
        this.world = world;
        this.blocks = new HashSet<>(blocks);
        this.best = filterBest(start);
    }
    public GoalMineBlock(Block[] blocks, IWorldProvider world, Vec3i start) {
        this(List.of(blocks), world, start);
    }

    public boolean next() {
        return true;
    }

    public Vec3i[] filterBest(Vec3i pos) {
        Vec3i[] best = new Vec3i[BEST_COUNT];
        Double[] distances = new Double[BEST_COUNT];
        Arrays.fill(distances, Double.MAX_VALUE);
        double longestDistance = Double.MAX_VALUE;
        int longestIndex = 0;

        for (Block block : blocks) {
            for (Vec3i vec3i : world.getWorld().findCachedBlock(block)) {
                //if (vec3i == null) continue; //No time for checks we neeeed speeed

                Vec3d delta = new Vec3d(pos.getX(), pos.getY(), pos.getZ()).subtract(Vec3d.fromBlock(vec3i));
                double current = GoalXYZ.calculate(delta.getX(), (int) delta.getY(), delta.getZ());
                if (current < longestDistance) {
                    distances[longestIndex] = current;
                    best[longestIndex] = vec3i;
                    longestDistance = Double.MIN_VALUE;
                    for (int i = 0; i < distances.length; i++) {
                        double d = distances[i];
                        if (d > longestDistance) {
                            longestDistance = d;
                            longestIndex = i;
                        }
                    }
                }
            }
        }
        return best;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return blocks.contains(world.getWorld().getBlock(x, y, z));
    }

    @Override
    public double heuristic(int x, int y, int z) {
        //concurrent exceptions fucking with this so just try again
        for (int i = 0; i < 10; i++) {
            try {
                return heuristic0(x, y, z);
            } catch (ConcurrentModificationException ignore) {

            }
        }
        return Double.MAX_VALUE;
    }
    private double heuristic0(int x, int y, int z) {
        double best = Double.MAX_VALUE;
        for (Vec3i vec3i : this.best) {
            if (vec3i == null) continue;
            Vec3d delta = new Vec3d(x, y, z).subtract(Vec3d.fromBlock(vec3i));
            double current = GoalXYZ.calculate(delta.getX(), (int) delta.getY(), delta.getZ());
            if (current < best) {
                best = current;
            }
        }
        return best;
    }
}
