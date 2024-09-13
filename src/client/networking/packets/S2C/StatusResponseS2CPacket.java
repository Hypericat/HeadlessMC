package client.networking.packets.S2C;

import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.utils.PacketUtil;

public class StatusResponseS2CPacket extends S2CPacket {
    public static final int typeID = 0x00;
    public final static NetworkState networkState = NetworkState.HANDSHAKE;
    private String string;

    public StatusResponseS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onStatus(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        string = PacketUtil.readString(buf);
    }

    public String getString() {
        return string;
    }

    public static long getTypeIdOffset() {
        return (long) StatusResponseS2CPacket.typeID + (StatusResponseS2CPacket.networkState == NetworkState.HANDSHAKE ? Integer.MAX_VALUE : 0);
    }
}
