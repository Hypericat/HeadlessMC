package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class Enchantments implements IComponent {
    @Override
    public IComponent copy() {
        return null;
    }

    @Override
    public int getTypeID() {
        return 9;
    }

    @Override
    public void read(ByteBuf buf) {
        int enchantmentCount = PacketUtil.readVarInt(buf);
        for (int i = 0; i < enchantmentCount; i++) {
            int enchantmentID = PacketUtil.readVarInt(buf);
            int level = PacketUtil.readVarInt(buf);
            buf.readBoolean(); //should show tooltip, again we don't care
        }
    }
}
