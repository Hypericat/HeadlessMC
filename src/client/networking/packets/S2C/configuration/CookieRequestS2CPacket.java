package client.networking.packets.S2C.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class CookieRequestS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.COOKIE_REQUEST_CONFIGURATION_S2C;

    private byte[] identifier;

    public CookieRequestS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onCookieRequest(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
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
