package client.game.items.component.components;

import client.game.items.component.IComponent;

public class Container implements IComponent {
    @Override
    public IComponent copy() {
        return null;
    }

    @Override
    public int getTypeID() {
        return 52;
    }
}
