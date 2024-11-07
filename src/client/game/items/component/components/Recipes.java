package client.game.items.component.components;

import client.game.items.component.IComponent;

public class Recipes implements IComponent {
    @Override
    public IComponent copy() {
        return new Recipes();
    }

    @Override
    public int getTypeID() {
        return 43;
    }
}
