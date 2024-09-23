package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import io.netty.buffer.ByteBuf;

public class AcknowledgedFinishedConfigurationC2SPacket extends C2SPacket {
    public final static int typeID = 0x03;
    public AcknowledgedFinishedConfigurationC2SPacket() {

    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(getTypeId());
    }
}
