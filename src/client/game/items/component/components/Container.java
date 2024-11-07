package client.game.items.component.components;

import client.game.items.ItemStack;
import client.game.items.component.ComponentReader;
import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class Container implements IComponent {
    private ItemStack[] slots;
    public Container(ItemStack[] slots) {
        this.slots = slots;
    }
    @Override
    public IComponent copy() {
        return null;
    }

    @Override
    public int getTypeID() {
        return 52;
    }

    @Override
    public void read(ByteBuf buf) {
        int num = PacketUtil.readVarInt(buf);
        slots = new ItemStack[num];
        for (int i = 0; i < num; i++) {
            slots[i] = ComponentReader.readSlot(buf);
        }
    }

    public ItemStack[] getSlots() {
        return slots;
    }

    public void setSlots(ItemStack[] slots) {
        this.slots = slots;
    }
}
