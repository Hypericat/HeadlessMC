package client.game.items.component.components;

import client.game.items.component.IComponent;

public class SuspiciousStewEffects implements IComponent {
    @Override
    public IComponent copy() {
        return new SuspiciousStewEffects();
    }

    @Override
    public int getTypeID() {
        return 32;
    }
}
