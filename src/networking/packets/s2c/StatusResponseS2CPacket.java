package networking.packets.s2c;

import io.netty.buffer.ByteBuf;
import networking.Packet;
import networking.ClientPacketListener;

public class StatusResponseS2CPacket implements Packet<ClientPacketListener> {
    public static final int typeID = 122;
    public StatusResponseS2CPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onStatus(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {

    }
}
