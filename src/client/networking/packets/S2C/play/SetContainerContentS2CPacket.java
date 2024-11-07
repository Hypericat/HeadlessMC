package client.networking.packets.S2C.play;

import client.Logger;
import client.game.items.ItemStack;
import client.game.items.ItemType;
import client.game.items.component.ComponentReader;
import client.networking.ClientPacketListener;
import client.networking.ItemID;
import client.networking.ItemIDs;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class SetContainerContentS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.CONTAINER_SET_CONTENT_PLAY_S2C;

    private short windowID;
    private int stateID;
    private int count;
    private List<ItemStack> items;
    private ItemStack carriedStack;

    public SetContainerContentS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetContainerContent(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        windowID = buf.readByte();
        stateID = PacketUtil.readVarInt(buf);
        count = PacketUtil.readVarInt(buf);
        items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(ComponentReader.readSlot(buf));
        }
        carriedStack = ComponentReader.readSlot(buf);
        System.out.println("Updated item : " + items);
    }

    public boolean isClientInventory() {
        return windowID == 0;
    }

    public short getWindowID() {
        return windowID;
    }

    public int getStateID() {
        return stateID;
    }

    public int getCount() {
        return count;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public ItemStack getCarriedStack() {
        return carriedStack;
    }
}
