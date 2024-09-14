package client.networking.packets.C2S;

import client.networking.ClientPacketListener;
import client.networking.packets.S2C.ClientBoundKnownPacksS2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ServerBoundKnownPacksC2SPacket extends C2SPacket {
    public final static int typeID = 0x07;
    private ClientBoundKnownPacksS2CPacket packet;
    public ServerBoundKnownPacksC2SPacket(ClientBoundKnownPacksS2CPacket packet) {
        this.packet = packet;
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
        PacketUtil.writeVarInt(buf, 1);

        PacketUtil.writeString(buf, "minecraft");
        PacketUtil.writeString(buf, "core");
        PacketUtil.writeString(buf, "1.21.1");

    }
}
