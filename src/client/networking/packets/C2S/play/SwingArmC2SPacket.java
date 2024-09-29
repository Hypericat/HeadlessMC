package client.networking.packets.C2S.play;

import client.game.Hand;
import client.networking.ClientPacketListener;
import client.networking.PacketHandler;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.BitSet;
import java.util.Optional;

public class SwingArmC2SPacket extends C2SPacket {
    public static final int typeID = 0x36;
    private final Hand hand;



    public SwingArmC2SPacket(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        PacketUtil.writeVarInt(buf, hand.getValue());
    }

    public Hand getHand() {
        return hand;
    }
}
