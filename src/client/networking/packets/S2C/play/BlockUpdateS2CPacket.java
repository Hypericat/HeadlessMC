package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.S2C.S2CPacket;
import client.utils.BitUtils;
import client.utils.PacketUtil;
import client.utils.Vec3i;
import io.netty.buffer.ByteBuf;

public class BlockUpdateS2CPacket extends S2CPacket {
    public static final int typeID = 0x09;
    public final static NetworkState networkState = NetworkState.PLAY;
    private Vec3i pos;
    private int blockID;

    public BlockUpdateS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onBlockUpdate(this);
    }

    @Override
    public int getTypeId() {
        return typeID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.pos = PacketUtil.readPosition(buf);
        this.blockID = PacketUtil.readVarInt(buf);
    }

    public Vec3i getPos() {
        return pos;
    }

    public int getBlockID() {
        return blockID;
    }
}
