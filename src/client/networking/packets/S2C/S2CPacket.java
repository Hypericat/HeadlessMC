package client.networking.packets.S2C;

import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.packets.Packet;

public abstract class S2CPacket implements Packet<ClientPacketListener> {
    private int size;

    public S2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        this.size = size;
        decode(buf);
    }

    public abstract void decode(ByteBuf buf);

}
