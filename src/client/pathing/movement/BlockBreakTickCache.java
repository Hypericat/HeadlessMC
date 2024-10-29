package client.pathing.movement;

import client.game.Block;
import client.game.inventory.Inventory;
import client.game.inventory.LivingInventory;
import client.pathing.CalculationContext;
import math.Vec3i;

import java.util.HashMap;

public class BlockBreakTickCache {
    private final LivingInventory playerInventory;
    private final HashMap<Block, Integer> cache;
    public BlockBreakTickCache(LivingInventory playerInventory) {
        this.playerInventory = playerInventory;
        this.cache = new HashMap<>();
    }

    public int getMiningTickCount(CalculationContext ctx, Vec3i blockPos, Block block, boolean includeFalling) {
        if (cache.containsKey(block)) return cache.get(block);
        double result = 1 / strVsBlock;

        return result;
    }
}
