package client.networking.packets.S2C;

import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.utils.PacketUtil;

public class CompressionRequestS2CPacket extends S2CPacket {
    public static final int typeID = -1;
    public final static NetworkState networkState = NetworkState.HANDSHAKE;
    private int compressionType;

    public CompressionRequestS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onCompression(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        compressionType = PacketUtil.readVarInt(buf);
    }

    public int getCompressionType() {
        return compressionType;
    }

    public static long getTypeIdOffset() {
        return (long) CompressionRequestS2CPacket.typeID + (CompressionRequestS2CPacket.networkState == NetworkState.HANDSHAKE ? Integer.MAX_VALUE : 0);
    }
}
