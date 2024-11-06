package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.IntComponent;

public class Damage extends IntComponent {
    public Damage(int value) {
        super(value);
    }

    @Override
    public IComponent copy() {
        return new Damage(this.getValue());
    }

    @Override
    public int getTypeID() {
        return 3;
    }
}
