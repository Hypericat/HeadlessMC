package networking.packets;

import io.netty.buffer.ByteBuf;
import networking.Packet;
import networking.ClientPacketListener;

public class StatusRequestC2SPacket implements Packet<ClientPacketListener> {
    public StatusRequestC2SPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public int getTypeId() {
        return 0x00;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(getTypeId());
    }
}
