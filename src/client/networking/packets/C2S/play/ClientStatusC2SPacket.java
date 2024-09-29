package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ClientStatusC2SPacket extends C2SPacket {
    public static final int typeID = 0x09;
    //0 -> respawn
    //1 -> request stats
    private int status;

    public ClientStatusC2SPacket(int status) {
        if (status != 0 && status != 1) throw new IllegalArgumentException("Invalid Status Provided");
        this.status = status;
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
        PacketUtil.writeVarInt(buf, status);
    }
}
