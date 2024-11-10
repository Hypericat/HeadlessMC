package client.game.items;

public class EnchantmentType {
    private final int typeID;
    private final String name;

    protected EnchantmentType(int id, String name) {
        this.typeID = id;
        this.name = name;
    }

    public int getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }
}
