package client.pathing.goals;

import client.game.Block;
import client.pathing.IWorldProvider;
import math.Vec3d;
import math.Vec3i;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;

public class GoalMineBlock implements Goal {
    private final IWorldProvider world;
    private final HashSet<Block> blocks;

    public GoalMineBlock(List<Block> blocks, IWorldProvider world) {
        this.world = world;
        this.blocks = new HashSet<>(blocks);
    }
    public GoalMineBlock(Block[] blocks, IWorldProvider world) {
        this(List.of(blocks), world);
    }

    public boolean next() {
        return true;
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
        for (Block block : blocks) {
            for (Vec3i vec3i : world.getWorld().findCachedBlock(block)) {
                //fromBlock may not be the best
                if (vec3i == null) continue;
                Vec3d delta = new Vec3d(x, y, z).subtract(Vec3d.fromBlock(vec3i));
                double current = GoalXYZ.calculate(delta.getX(), (int) delta.getY(), delta.getZ());
                if (current < best) {
                    best = current;
                }
            }
        }
        return best;
    }
}
