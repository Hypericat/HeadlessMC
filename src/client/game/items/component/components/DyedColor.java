package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class DyedColor implements IComponent {
    private int color;

    public DyedColor(int color) {
        this.color = color;
    }
    @Override
    public IComponent copy() {
        return new DyedColor(this.color);
    }

    @Override
    public int getTypeID() {
        return 24;
    }

    @Override
    public void read(ByteBuf buf) {
        this.color = buf.readInt();
        buf.readBoolean();
    }

    public void setColor(int color) {
        this.color = color;
    }
    public int getColor() {
        return this.color;
    }
}
