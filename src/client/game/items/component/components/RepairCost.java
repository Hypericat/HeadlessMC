package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.IntComponent;

public class RepairCost extends IntComponent {
    public RepairCost(int value) {
        super(value);
    }

    @Override
    public IComponent copy() {
        return new RepairCost(this.getValue());
    }

    @Override
    public int getTypeID() {
        return 16;
    }
}
