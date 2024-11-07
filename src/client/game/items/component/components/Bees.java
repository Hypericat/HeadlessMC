package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class Bees implements IComponent {
    @Override
    public IComponent copy() {
        return new Bees();
    }

    @Override
    public int getTypeID() {
        return 54;
    }
}
