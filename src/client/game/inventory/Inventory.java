package client.game.inventory;

import client.game.items.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<ItemStack> contents;

    protected Inventory(List<ItemStack> contents) {
        this.contents = contents;
    }

    public static Inventory empty() {
        return new Inventory(new ArrayList<>());
    }

    public static Inventory from(List<ItemStack> stacks) {
        return new Inventory(new ArrayList<>(stacks));
    }

    /**
     *  returns a list of items starting from index startSlot inclusive
     *  to index endSlot inclusive
     * */

    public List<ItemStack> getSlots(int startSlot, int endSlot) {
        List<ItemStack> stacks = new ArrayList<>();
        return contents.subList(startSlot, endSlot + 1);
    }

    public ItemStack getSlot(int slot) {
        if (slot > contents.size() - 1) {
            return null;
        }
        if (slot < 0) throw new IndexOutOfBoundsException("Slot index given with negative value");
        return contents.get(slot);
    }

    public void setSlot(int slotIndex, ItemStack stack) {
        if (slotIndex < 0) throw new IndexOutOfBoundsException("Slot index given with negative value");
        if (slotIndex > contents.size() - 1) {
            for (int i = contents.size() - 1; i <= slotIndex; i++) {
                contents.add(null);
            }
        }

        contents.set(slotIndex, stack);
    }

    /**
     *  sets the slots starting at startIndex inclusively
     *  for however long stack size is or contents can fit
     * */

    public void setSlots(int startIndex, List<ItemStack> stacks) {
        for (int j = 0, i = startIndex; i < stacks.size() + startIndex - 1; i++, j++) {
            setSlot(i, stacks.get(j));
        }
    }
}
