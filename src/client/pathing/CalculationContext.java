package client.pathing;

import client.game.World;

public class CalculationContext {
    private final IWorldProvider worldProvider;

    public CalculationContext(IWorldProvider worldProvider) {
        this.worldProvider = worldProvider;
    }
    public IWorldProvider getWorldProvider() {
        return worldProvider;
    }
    public World getWorld() {
        return worldProvider.getWorld();
    }
}
