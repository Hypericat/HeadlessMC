package client.game.items.component;

public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    EPIC("epic");

    private final String rarity;

    Rarity(String rarity) {
        this.rarity = rarity;
    }
    public String toString() {
        return rarity;
    }
}
