package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

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
        return 5;
    }

    @Override
    public void read(ByteBuf buf) {
        throw new IllegalStateException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
