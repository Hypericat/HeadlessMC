package client.networking.packets.S2C.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class ClientBoundKnownPacksS2CPacket extends S2CPacket {
    public static final int typeID = 0x0E;
    public final static NetworkState networkState = NetworkState.CONFIGURATION;
    private ByteBuf buf;

    public ClientBoundKnownPacksS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);

        this.buf = buf.slice().copy();
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onKnowPacks(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {

    }
    public ByteBuf getBuf() {
        return buf;
    }
}
