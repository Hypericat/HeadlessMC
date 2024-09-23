package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class KeepAliveC2SPacket extends C2SPacket {
    public static final int typeID = 0x18;
    private long id;

    public KeepAliveC2SPacket(long id) {
        this.id = id;

    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(this.getTypeId());
        PacketUtil.writeVarLong(buf, id);
    }
}
