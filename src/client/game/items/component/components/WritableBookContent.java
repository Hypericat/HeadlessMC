package client.game.items.component.components;

import client.game.items.component.IComponent;

public class WritableBookContent implements IComponent {
    @Override
    public IComponent copy() {
        return new WritableBookContent();
    }

    @Override
    public int getTypeID() {
        return 33;
    }
}
