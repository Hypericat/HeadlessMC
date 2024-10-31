package client.pathing.movement;

import client.game.Block;
import client.game.inventory.Inventory;
import client.game.inventory.LivingInventory;
import client.game.items.ItemStack;
import client.game.items.ItemType;
import client.game.items.Items;
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

    public int getMiningTickCount(CalculationContext ctx, Block block, boolean includeFalling) {
        if (cache.containsKey(block)) return cache.get(block);
        int tickCount = getMiningTickCount0(ctx, block, includeFalling);
        cache.put(block, tickCount);
        return tickCount;
    }

    private int getMiningTickCount0(CalculationContext ctx, Block block, boolean includeFalling) {
        for (ItemStack stack : playerInventory.getHotbar()) {
            if (stack == null) continue;
            if (stack.getType() == Items.DIAMOND_PICKAXE) return 10;
        }
        return -1;
    }
}
