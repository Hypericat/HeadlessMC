package client.game.items.component.components;

import client.game.items.component.IComponent;

public class PotDecorations implements IComponent {
    @Override
    public IComponent copy() {
        return new PotDecorations();
    }

    @Override
    public int getTypeID() {
        return 51;
    }
}
