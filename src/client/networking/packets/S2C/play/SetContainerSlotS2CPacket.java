package client.networking.packets.S2C.play;

import client.game.items.ItemStack;
import client.game.items.ItemType;
import client.game.items.component.ComponentReader;
import client.networking.ClientPacketListener;
import client.networking.ItemIDs;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class SetContainerSlotS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.CONTAINER_SET_SLOT_PLAY_S2C;

    private short windowID;
    private int stateID;
    private short slot;
    private ItemStack itemStack;

    public SetContainerSlotS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetContainerSlot(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        windowID = buf.readByte();
        stateID = PacketUtil.readVarInt(buf);
        this.slot = buf.readShort();
        itemStack = ComponentReader.readSlot(buf);
        System.out.println("Updated item : " + itemStack);
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

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
