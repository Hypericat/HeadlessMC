package networking.packets.C2S;

import io.netty.buffer.ByteBuf;
import networking.ClientPacketListener;
import networking.packets.Packet;

public abstract class C2SPacket implements Packet<ClientPacketListener> {

    public C2SPacket() {

    }

    public abstract void encode(ByteBuf buf);

}
