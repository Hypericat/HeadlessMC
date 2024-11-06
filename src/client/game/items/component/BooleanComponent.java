package client.game.items.component;

import io.netty.buffer.ByteBuf;

public abstract class BooleanComponent implements IComponent {
    private boolean value;

    public BooleanComponent(boolean value) {
        this.value = value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    public boolean getValue() {
        return value;
    }

    public void read(ByteBuf buf) {
        setValue(buf.readBoolean());
    }
}
