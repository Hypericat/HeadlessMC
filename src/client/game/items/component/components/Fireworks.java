package client.game.items.component.components;

import client.game.items.component.IComponent;

public class Fireworks implements IComponent {
    @Override
    public IComponent copy() {
        return new Fireworks();
    }

    @Override
    public int getTypeID() {
        return 46;
    }
}
