package client.pathing.movement;

import client.game.Block;
import client.game.Blocks;
import client.game.inventory.Inventory;
import client.game.inventory.LivingInventory;
import client.game.items.*;
import client.game.items.component.components.Tool;
import client.pathing.CalculationContext;
import math.Pair;
import math.Vec3i;

import java.util.HashMap;

public class BlockBreakTickCache {
    private final LivingInventory playerInventory;
    private final HashMap<Block, Integer> cache;
    public BlockBreakTickCache(LivingInventory playerInventory) {
        this.playerInventory = playerInventory;
        this.cache = new HashMap<>();
    }

    public int getMiningSpeedWithBestItem(CalculationContext ctx, Block block, boolean includeFalling) {
        if (cache.containsKey(block)) return cache.get(block);
        int tickCount = getMiningSpeedWithBestItemUncached(this.playerInventory, block, includeFalling);
        cache.put(block, tickCount);
        return tickCount;
    }

    /**
     * @param playerInventory
     * @param block
     * @param includeFalling
     * @return a pair of integer, the left is the hotbar slot and the right is the time
     */

    public static Pair<Integer, Integer> getBestSlotAndTickTimeUncached(LivingInventory playerInventory, Block block, boolean includeFalling) {
        int bestSpeed = Integer.MAX_VALUE;
        int bestSlot = 0;
        for (int i = 0; i < playerInventory.getHotbar().size(); i++) {
            ItemStack stack = playerInventory.getHotbar().get(i);
            if (stack == null) continue;
            int speed = getMiningTickCount(stack.getType(), block);
            if (speed < bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }
        System.out.println("Found best item : " + playerInventory.getHotbar().get(bestSlot) + " for block : " + block + " speed : " + bestSpeed + " ticks");
        return new Pair<>(bestSlot, bestSpeed);
    }

    public static int getMiningSpeedWithBestItemUncached(LivingInventory playerInventory, Block block, boolean includeFalling) {
        return getBestSlotAndTickTimeUncached(playerInventory, block, includeFalling).getRight();
    }

    public static int getMiningTickCount(ItemType itemType, Block  block) {
        return (int) Math.ceil(1f / getStrengthVsBlock(itemType, block));
    }

    public static float getStrengthVsBlock(ItemType itemType, Block block) {
        float hardness = block.getHardness();
        if (hardness < 0) return -1f;

        float speed = itemType.getComponent().getTool().getSpeedAgainstBlock(block);
        if (speed > 1f && itemType.getComponent().getEnchantments().hasEnchantment(EnchantmentTypes.EFFICIENCY)) {
            int level = itemType.getComponent().getEnchantments().getByType(EnchantmentTypes.EFFICIENCY).getLevel();
            if (level > 0)
                speed += level * level + 1;
        }
        speed /= hardness;
        return speed / 30;
    }

}
