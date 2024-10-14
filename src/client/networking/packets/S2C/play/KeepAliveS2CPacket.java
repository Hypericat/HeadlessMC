package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class KeepAliveS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.KEEP_ALIVE_PLAY_S2C;
    private long id;

    public KeepAliveS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onKeepAlive(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = buf.readLong();
    }

    public long getId() {
        return this.id;
    }

}
