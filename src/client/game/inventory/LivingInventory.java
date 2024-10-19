package client.game.inventory;

import client.game.items.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LivingInventory extends Inventory {

    protected LivingInventory(List<ItemStack> contents) {
        super(contents);
    }

    public static LivingInventory empty() {
        return new LivingInventory(new ArrayList<>());
    }

    public static LivingInventory from(List<ItemStack> stacks) {
        return new LivingInventory(new ArrayList<>(stacks));
    }

    public List<ItemStack> getHotbar() {
        return getSlots(36, 44);
    }

    public List<ItemStack> getArmor() {
        return getSlots(5, 8);
    }

    public List<ItemStack> getCraftingContents() {
        return getSlots(1, 4);
    }

    public ItemStack getCraftingResult() {
        return getSlot(0);
    }

    public ItemStack getOffHand() {
        return getSlot(45);
    }

    public ItemStack getMainHand(int hotbarIndex) {
        return getSlot(hotbarIndex + 36);
    }
}
