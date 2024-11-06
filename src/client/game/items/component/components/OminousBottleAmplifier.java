package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.IntComponent;

public class OminousBottleAmplifier extends IntComponent {
    public OminousBottleAmplifier(int value) {
        super(value);
    }

    @Override
    public IComponent copy() {
        return new OminousBottleAmplifier(this.getValue());
    }

    @Override
    public int getTypeID() {
        return 41;
    }
}
