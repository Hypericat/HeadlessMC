package client.networking.packets.S2C.configuration;

import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.utils.PacketUtil;

public class CompressionRequestS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.LOGIN_COMPRESSION_LOGIN_S2C;
    private int compressionType;

    public CompressionRequestS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onCompression(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }


    @Override
    public void decode(ByteBuf buf) {
        compressionType = PacketUtil.readVarInt(buf);
    }

    public int getCompressionType() {
        return compressionType;
    }
}
