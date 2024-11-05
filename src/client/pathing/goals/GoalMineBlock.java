package client.pathing.goals;

import client.game.Block;
import client.pathing.IWorldProvider;
import math.Vec3d;
import math.Vec3i;

public class GoalMineBlock implements Goal {
    private final IWorldProvider world;
    private final Block block;

    public GoalMineBlock(Block block, IWorldProvider world) {
        this.world = world;
        this.block = block;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        return world.getWorld().getBlock(x, y, z) == block;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        double best = Double.MAX_VALUE;
        for (Vec3i vec3i : world.getWorld().findCachedBlock(block)) {
            //fromBlock may not be the best
            Vec3d delta = new Vec3d(x, y, z).subtract(Vec3d.fromBlock(vec3i));
            double current = GoalXYZ.calculate(delta.getX(), (int) delta.getY(), delta.getZ());
            if (current < best) {
                best = current;
            }
        }
        return best;
    }
}
