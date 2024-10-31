package client.pathing;

import client.game.World;
import client.pathing.movement.BlockBreakTickCache;

public class CalculationContext {
    private final IWorldProvider worldProvider;
    private final BlockBreakTickCache blockBreakTickCache;

    public CalculationContext(IWorldProvider worldProvider, BlockBreakTickCache blockBreakTickCache) {
        this.worldProvider = worldProvider;
        this.blockBreakTickCache = blockBreakTickCache;
    }

    public IWorldProvider getWorldProvider() {
        return worldProvider;
    }
    public World getWorld() {
        return worldProvider.getWorld();
    }
    public BlockBreakTickCache getBlockBreakTickCache() {
        return this.blockBreakTickCache;
    }
}
