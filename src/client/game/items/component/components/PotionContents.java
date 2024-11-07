package client.game.items.component.components;

import client.game.items.component.IComponent;

public class PotionContents implements IComponent {
    @Override
    public IComponent copy() {
        return new PotionContents();
    }

    @Override
    public int getTypeID() {
        return 31;
    }
}
