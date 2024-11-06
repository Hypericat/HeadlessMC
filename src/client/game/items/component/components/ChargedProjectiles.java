package client.game.items.component.components;

import client.game.items.component.IComponent;

public class ChargedProjectiles implements IComponent {
    @Override
    public IComponent copy() {
        return null;
    }

    @Override
    public int getTypeID() {
        return 29;
    }
}
