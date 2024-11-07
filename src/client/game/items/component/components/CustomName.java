package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class CustomName implements IComponent {
    private String name;

    public CustomName(String name) {
        this.name = name;
    }

    @Override
    public IComponent copy() {
        return new CustomName(name);
    }

    @Override
    public int getTypeID() {
        return 4;
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
