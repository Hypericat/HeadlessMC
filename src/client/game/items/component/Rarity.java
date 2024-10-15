package client.game.items.component;

public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    EPIC("epic");

    private String rarity;

    Rarity(String rarity) {

    }
    public String toString() {
        return rarity;
    }
}
