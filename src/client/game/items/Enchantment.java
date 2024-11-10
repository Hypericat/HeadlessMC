package client.game.items;

public class Enchantment {
    private final EnchantmentType type;
    private final int level;

    public Enchantment(EnchantmentType type, int level) {
        this.type = type;
        this.level = level;
    }

    public int hash() {
        return this.getType().getTypeID() << 16 | (level << 16 >> 16);
    }

    public EnchantmentType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
