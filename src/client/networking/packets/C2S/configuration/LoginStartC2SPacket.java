package client.networking.packets.C2S.configuration;

import client.networking.packets.C2S.C2SPacket;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.utils.PacketUtil;

public class LoginStartC2SPacket extends C2SPacket {
    public static final int typeID = 0x00;
    private String name;

    public LoginStartC2SPacket(String name) {
        this.name = name;

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
        PacketUtil.writeVarInt(buf, getTypeId());
        PacketUtil.writeString(buf, this.name);
        buf.writeLong(0);
        buf.writeLong(0);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
