package client.game.items;

public class ItemStack {
    private final ItemType type;
    private final int stackSize;

    public ItemStack(ItemStack stack) {
        this(stack.type, stack.stackSize);
    }

    public ItemStack(ItemStack stack, int stackSize) {
        this(stack.type, stackSize);
    }

    public ItemStack(ItemType itemType, int stackSize) {
        this.type = itemType;
        this.stackSize = stackSize;
    }

    public ItemType getType() {
        return type;
    }

    public int getStackSize() {
        return stackSize;
    }
}
