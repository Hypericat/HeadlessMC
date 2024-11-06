package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.IntComponent;

public class MaxDamage extends IntComponent {
    public MaxDamage(int value) {
        super(value);
    }

    @Override
    public IComponent copy() {
        return new MaxDamage(this.getValue());
    }

    @Override
    public int getTypeID() {
        return 2;
    }
}
