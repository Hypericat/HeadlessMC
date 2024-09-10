package networking.packets.c2s;

import io.netty.buffer.ByteBuf;
import networking.Packet;
import networking.ClientPacketListener;

public class StatusRequestC2SPacket implements Packet<ClientPacketListener> {
    public final static int typeID = 0x00;
    public StatusRequestC2SPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(getTypeId());
    }
}
