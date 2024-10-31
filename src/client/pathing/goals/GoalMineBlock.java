package client.pathing.goals;

import client.game.Block;
import client.pathing.IWorldProvider;

public class GoalMineBlock implements Goal {
    private final IWorldProvider world;
    private final Block block;
    public GoalMineBlock(Block block, IWorldProvider world) {
        this.world = world;
        this.block = block;
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        Block block1 = world.getWorld().getBlock(x, y, z);
        System.out.println("Block1 : " + block1.getName() + " looking for block : " + block.getName());
        return world.getWorld().getBlock(x, y, z) == block;
    }

    @Override
    public double heuristic(int x, int y, int z) {
        return GoalY.calculate(0, y - 11);
    }
}
