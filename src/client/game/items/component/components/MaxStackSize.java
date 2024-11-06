package client.game.items.component.components;

import client.game.items.component.IntComponent;

public class MaxStackSize extends IntComponent {
    public MaxStackSize(int value) {
        super(value);
    }

    @Override
    public int getTypeID() {
        return 1;
    }
}
