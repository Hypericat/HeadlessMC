package client.game.items.component.components;

import client.game.items.component.IComponent;

public class StoredEnchantments implements IComponent {
    @Override
    public IComponent copy() {
        return new StoredEnchantments();
    }

    @Override
    public int getTypeID() {
        return 23;
    }
}
