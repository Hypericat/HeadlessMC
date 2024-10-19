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
        this.stackSize = Math.clamp(stackSize, 1, itemType.getComponent().getMaxStackSize());
    }

    private ItemStack(ItemType itemType, int stackSize, boolean IknowThisIsBadCodeButIDC) {
        this.type = itemType;
        this.stackSize = Math.clamp(stackSize, 1, itemType.getComponent().getMaxStackSize());
    }

    public static ItemStack empty() {
        return new ItemStack(null, -1, true);
    }

    public ItemType getType() {
        return type;
    }

    public int getStackSize() {
        return stackSize;
    }

    public boolean isEmpty() {
        return stackSize == -1 || type == null;
    }

    public String toString() {
        return this.getType().getIdentifier() + " (x" + this.stackSize + ")";
    }
}
