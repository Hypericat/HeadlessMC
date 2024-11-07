package client.game.items;

public class Enchantment {
    private final int typeID;
    private final String name;

    protected Enchantment(int id, String name) {
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
