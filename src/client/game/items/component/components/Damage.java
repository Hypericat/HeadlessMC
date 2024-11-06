package client.game.items.component.components;

import client.game.items.component.IntComponent;

public class Damage extends IntComponent {
    public Damage(int value) {
        super(value);
    }

    @Override
    public int getTypeID() {
        return 3;
    }
}
