package client.networking.packets.C2S;

import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.packets.Packet;

public abstract class C2SPacket implements Packet<ClientPacketListener> {

    public C2SPacket() {

    }

    public abstract void encode(ByteBuf buf);

    public int getTypeID() {
        return this.getPacketID().getPacketID();
    }

}
