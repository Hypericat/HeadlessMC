package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class AttributeModifiers implements IComponent {
    @Override
    public IComponent copy() {
        return null;
    }

    @Override
    public int getTypeID() {
        return 12;
    }
}
