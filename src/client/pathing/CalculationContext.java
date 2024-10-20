package client.pathing;

import client.game.World;

public class CalculationContext {
    IWorldProvider worldProvider;

    public CalculationContext(IWorldProvider worldProvider) {
        this.worldProvider = worldProvider;
    }
    public World getWorld() {
        return worldProvider.getWorld();
    }
}
