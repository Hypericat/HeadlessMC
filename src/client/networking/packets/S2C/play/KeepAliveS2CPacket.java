package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class KeepAliveS2CPacket extends S2CPacket {
    public static final int typeID = 0x26;
    public final static NetworkState networkState = NetworkState.PLAY;
    private long id = -1l;

    public KeepAliveS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onKeepAlive(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = PacketUtil.readVarLong(buf);
    }

    public long getId() {
        return id;
    }

}
