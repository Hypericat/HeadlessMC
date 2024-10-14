package client.networking.packets.S2C.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;

public class FinishConfigurationS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.FINISH_CONFIGURATION_CONFIGURATION_S2C;
    private String text;

    public FinishConfigurationS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onFinishConfiguration(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {

    }

}
