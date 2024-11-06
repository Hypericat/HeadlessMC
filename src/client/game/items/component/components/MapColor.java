package client.game.items.component.components;

import client.game.items.component.IntComponent;
import io.netty.buffer.ByteBuf;

public class MapColor extends IntComponent {
    public MapColor(int value) {
        super(value);
    }

    @Override
    public int getTypeID() {
        return 25;
    }

    @Override
    public void read(ByteBuf buf) {
        this.setValue(buf.readInt());
    }
}
