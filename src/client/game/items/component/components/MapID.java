package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.IntComponent;

public class MapID extends IntComponent {
    public MapID(int value) {
        super(value);
    }

    @Override
    public IComponent copy() {
        return new MapID(this.getValue());
    }

    @Override
    public int getTypeID() {
        return 26;
    }
}
