package client.networking.packets.S2C.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ClientBoundPluginMessageS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.CUSTOM_PAYLOAD_CONFIGURATION_S2C;
    private String text;

    public ClientBoundPluginMessageS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onClientPluginMessage(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.text = PacketUtil.readString(buf);
    }
    public String getText() {
        return text;
    }

}
