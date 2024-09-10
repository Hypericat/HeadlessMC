package networking.packets.S2C;

import io.netty.buffer.ByteBuf;
import networking.ClientPacketListener;
import utils.PacketUtil;

public class StatusResponseS2CPacket extends S2CPacket {
    public static final int typeID = 102;
    private String string;

    public StatusResponseS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onStatus(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        string = PacketUtil.readString(buf);
    }

    public String getString() {
        return string;
    }
}
