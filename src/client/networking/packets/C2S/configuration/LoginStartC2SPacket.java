package client.networking.packets.C2S.configuration;

import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.utils.PacketUtil;

public class LoginStartC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.HELLO_LOGIN_C2S;

    private String name;

    public LoginStartC2SPacket(String name) {
        this.name = name;

    }

    @Override
    public void apply(ClientPacketListener listener) {
        //no response
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {
        PacketUtil.writeVarInt(buf, this.getTypeID());
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
