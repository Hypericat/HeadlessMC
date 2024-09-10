package networking.packets;

import io.netty.buffer.ByteBuf;
import networking.Packet;
import networking.ClientPacketListener;

public class StatusRequestS2CPacket implements Packet<ClientPacketListener> {
    public StatusRequestS2CPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onStatus(this);
    }

    @Override
    public int getTypeId() {
        return 0x01;
    }

    @Override
    public void encode(ByteBuf buf) {

    }
}
