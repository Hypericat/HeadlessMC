package client.networking.packets.C2S;

import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;

public class StatusRequestC2SPacket extends C2SPacket {
    public final static int typeID = 0x00;
    public StatusRequestC2SPacket() {

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
