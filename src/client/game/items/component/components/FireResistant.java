package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class FireResistant implements IComponent {
    public FireResistant() {

    }

    @Override
    public int getTypeID() {
        return 21;
    }

    @Override
    public void read(ByteBuf buf) {
        //do nothing, nothing to read
    }

}
