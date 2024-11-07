package client.game.items.component.components;

import client.game.items.component.IComponent;

public class DebugStickState implements IComponent {

    @Override
    public IComponent copy() {
        return new DebugStickState();
    }

    @Override
    public int getTypeID() {
        return 36;
    }
}
