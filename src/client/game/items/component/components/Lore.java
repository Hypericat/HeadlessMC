package client.game.items.component.components;

import client.game.items.component.IComponent;
import io.netty.buffer.ByteBuf;

public class Lore implements IComponent {
    private String[] lore;

    public Lore(String ... lore) {
        this.lore = lore;
    }

    @Override
    public IComponent copy() {
        return new Lore(lore);
    }

    @Override
    public int getTypeID() {
        return 7;
    }

    @Override
    public void read(ByteBuf buf) {
        //nothing for now
        IComponent.super.read(buf);
    }

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }
}

