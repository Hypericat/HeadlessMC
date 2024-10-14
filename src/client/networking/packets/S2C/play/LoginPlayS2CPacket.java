package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class LoginPlayS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.LOGIN_PLAY_S2C;

    private int entityID;

    public LoginPlayS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onLoginPlay(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.entityID = buf.readInt();
    }

    public int getEntityID() {
        return this.entityID;
    }

}
