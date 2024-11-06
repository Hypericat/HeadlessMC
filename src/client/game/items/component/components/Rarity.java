package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public enum Rarity implements IComponent {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    EPIC("epic");

    private final String rarity;

    Rarity(String rarity) {
        this.rarity = rarity;
    }
    public String toString() {
        return rarity;
    }

    @Override
    public int getTypeID() {
        return 8;
    }

    @Override
    public void read(ByteBuf buf) {
        PacketUtil.readVarInt(buf);
        //we dont really care and it would be too much effort to set the rarity, aslong as we read it we dont care
    }
}
