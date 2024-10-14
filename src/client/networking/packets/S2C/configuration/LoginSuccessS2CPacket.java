package client.networking.packets.S2C.configuration;

import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.UUID;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;

public class LoginSuccessS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.GAME_PROFILE_LOGIN_S2C;

    private UUID uuid;

    public LoginSuccessS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onLoginSuccess(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        uuid = UUID.fromBuf(buf);
    }

    public UUID getUuid() {
        return uuid;
    }
}
