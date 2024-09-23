package client.networking.packets.S2C.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class CookieRequestS2CPacket extends S2CPacket {
    public static final int typeID = 0x00;
    public final static NetworkState networkState = NetworkState.CONFIGURATION;
    private byte[] identifier;

    public CookieRequestS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onCookieRequest(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        identifier = new byte[this.size];
        buf.readBytes(identifier);
    }

    public byte[] getIdentifier() {
        return identifier;
    }
}
