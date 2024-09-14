package client.networking.packets.S2C;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.PacketHandler;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ClientBoundKnownPacksS2CPacket extends S2CPacket {
    public static final int typeID = 0x0E;
    public final static NetworkState networkState = NetworkState.CONFIGURATION;
    private ByteBuf buf;

    public ClientBoundKnownPacksS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
        this.buf = buf.copy();
        System.out.println(PacketUtil.readString(buf));
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
