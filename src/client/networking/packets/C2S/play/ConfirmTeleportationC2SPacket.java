package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ConfirmTeleportationC2SPacket extends C2SPacket {
    public static final int typeID = 0x00;
    private int id;

    public ConfirmTeleportationC2SPacket(int id) {
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
        PacketUtil.writeVarInt(buf, id);
    }
}
