package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class Unbreakable implements IComponent {
    @Override
    public IComponent copy() {
        return new Unbreakable();
    }

    @Override
    public int getTypeID() {
        return 4;
    }

    @Override
    public void read(ByteBuf buf) {
        buf.readBoolean(); //show tool tip? we don't care
    }
}
