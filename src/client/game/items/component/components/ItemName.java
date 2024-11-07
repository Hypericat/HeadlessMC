package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class ItemName implements IComponent {
    private String name;

    public ItemName(String name) {
        this.name = name;
    }

    @Override
    public IComponent copy() {
        return new ItemName(name);
    }

    @Override
    public int getTypeID() {
        return 6;
    }

    @Override
    public void read(ByteBuf buf) {
        //nothing for now
        IComponent.super.read(buf);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
