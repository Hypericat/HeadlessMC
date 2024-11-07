package client.game.items.component.components;

import client.game.items.component.IComponent;

public class JukeboxPlayable implements IComponent {
    @Override
    public IComponent copy() {
        return new JukeboxPlayable();
    }

    @Override
    public int getTypeID() {
        return 42;
    }
}
