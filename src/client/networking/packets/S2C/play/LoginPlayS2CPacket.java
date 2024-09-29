package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class LoginPlayS2CPacket extends S2CPacket {
    public static final int typeID = 0x2B;
    public final static NetworkState networkState = NetworkState.PLAY;
    private int entityID;

    public LoginPlayS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onLoginPlay(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.entityID = buf.readInt();
    }

    public int getEntityID() {
        return this.entityID;
    }

}
