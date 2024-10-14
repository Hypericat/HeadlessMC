package client.networking.packets.C2S.play;

import client.game.Hand;
import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class SwingArmC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.SWING_PLAY_C2S;
    private final Hand hand;



    public SwingArmC2SPacket(Hand hand) {
        this.hand = hand;
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
        PacketUtil.writeVarInt(buf, hand.getValue());
    }

    public Hand getHand() {
        return hand;
    }
}
