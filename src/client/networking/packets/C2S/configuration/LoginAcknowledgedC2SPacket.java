package client.networking.packets.C2S.configuration;

import client.networking.packets.C2S.C2SPacket;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;

public class LoginAcknowledgedC2SPacket extends C2SPacket {
    public final static int typeID = 0x03;
    public LoginAcknowledgedC2SPacket() {

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
