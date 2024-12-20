package client.game.items.component;

import client.game.items.ItemStack;
import client.game.items.ItemType;
import client.game.items.Items;
import client.networking.ItemIDs;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import math.Pair;

public class ComponentReader {

    public static Pair<ItemStack, Boolean> readSlot(ByteBuf buf) {
        int itemCount = PacketUtil.readVarInt(buf);
        if (itemCount < 1) {
            return new Pair<>(new ItemStack(Items.AIR, 1), false);
        }

        int itemID = PacketUtil.readVarInt(buf);
        ItemType type = ItemIDs.getCopyFromID(itemID).getType();
        int add = PacketUtil.readVarInt(buf);
        int remove = PacketUtil.readVarInt(buf);
        try {
            addValues(type, add, buf);
            removeValues(type, remove, buf);
        } catch (IllegalStateException ex) {
            System.err.println("ReadSlot was given item with add/remove components which is not supported at the moment. The item was : " + type.getIdentifier());
            return new Pair<>(new ItemStack(type, itemCount), false);
        }
        return new Pair<>(new ItemStack(type, itemCount), true);
    }

    public static void addValues(ItemType baseType, int amountToAdd, ByteBuf buf) {
        for (int i = 0; i < amountToAdd; i++) {
            int componentID = PacketUtil.readVarInt(buf);
            //System.out.println("ComponentID : " + componentID);
            baseType.getComponent().getComponentByID(componentID).read(buf);
        }
    }

    public static void removeValues(ItemType baseType, int amountToRemove, ByteBuf buf) {
        for (int i = 0; i < amountToRemove; i++) {
            int componentID = PacketUtil.readVarInt(buf);
            IComponent component = baseType.getComponent().getComponentByID(componentID);
            if (component != null) component.remove();
        }
    }
}
