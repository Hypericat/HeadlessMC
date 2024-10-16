package client.networking;

import client.game.items.ItemType;

public class ItemID {
    private final ItemType type;
    private final int id;

    protected ItemID(ItemType type, int id) {
        this.type = type;
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
