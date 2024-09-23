package client.networking.packets.S2C.configuration;

import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;

public class LoginSuccessfulS2CPacket extends S2CPacket {
    public static final int typeID = 0x02;
    public final static NetworkState networkState = NetworkState.HANDSHAKE;
    private String text;

    public LoginSuccessfulS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onLoginSuccess(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {

    }

}
