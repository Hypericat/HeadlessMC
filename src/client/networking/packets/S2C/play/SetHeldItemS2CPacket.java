package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SetHeldItemS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.SET_CARRIED_ITEM_PLAY_S2C;

    byte slot;

    public SetHeldItemS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onSetHeldItem(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.slot = buf.readByte();
    }

    public byte getSlot() {
        return slot;
    }
}
