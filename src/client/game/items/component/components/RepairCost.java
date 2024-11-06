package client.game.items.component.components;

import client.game.items.component.IntComponent;

public class RepairCost extends IntComponent {
    public RepairCost(int value) {
        super(value);
    }

    @Override
    public int getTypeID() {
        return 16;
    }
}
