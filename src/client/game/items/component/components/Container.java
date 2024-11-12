package client.game.items.component.components;

import client.game.items.ItemStack;
import client.game.items.component.ComponentReader;
import client.game.items.component.IComponent;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import math.Pair;

import java.util.Arrays;

public class Container implements IComponent {
    private ItemStack[] slots;
    public Container(ItemStack[] slots) {
        this.slots = slots;
    }
    @Override
    public IComponent copy() {
        return new Container(slots == null ? null : Arrays.copyOf(slots, slots.length));
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
            Pair<ItemStack, Boolean> pair = ComponentReader.readSlot(buf);
            slots[i] = pair.getLeft();
            if (!pair.getRight()) return;
        }
    }

    public ItemStack[] getSlots() {
        return slots;
    }

    public void setSlots(ItemStack[] slots) {
        this.slots = slots;
    }
}
