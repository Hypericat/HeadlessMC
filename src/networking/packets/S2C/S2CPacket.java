package networking.packets.S2C;

import io.netty.buffer.ByteBuf;
import networking.ClientPacketListener;
import networking.packets.Packet;
import utils.PacketUtil;

public abstract class S2CPacket implements Packet<ClientPacketListener> {
    private int size;

    public S2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        this.size = size;
        decode(buf);
    }

    public abstract void decode(ByteBuf buf);
}
