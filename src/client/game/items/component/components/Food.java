package client.game.items.component.components;

import client.game.items.component.IComponent;

public class Food implements IComponent {
    @Override
    public IComponent copy() {
        return new Food();
    }

    @Override
    public int getTypeID() {
        return 20;
    }
}
