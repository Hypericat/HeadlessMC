package client.networking.packets.C2S.play;

import client.game.Hand;
import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SyncSelectedSlotC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.SET_CARRIED_ITEM_PLAY_C2S;
    private final short slot;

    public SyncSelectedSlotC2SPacket(int slot) {
        this.slot = (short) slot;
    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeID());
        buf.writeShort(slot);
    }

    public short getSlot() {
        return slot;
    }
}
