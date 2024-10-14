package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import math.Vec3i;
import io.netty.buffer.ByteBuf;

public class BlockUpdateS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.BLOCK_UPDATE_PLAY_S2C;
    private Vec3i pos;
    private int blockID;

    public BlockUpdateS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onBlockUpdate(this);
    }

    public PacketID getPacketID() {
        return packetID;
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
