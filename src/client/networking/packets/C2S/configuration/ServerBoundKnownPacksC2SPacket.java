package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.NetworkHandler;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.S2C.configuration.ClientBoundKnownPacksS2CPacket;
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
        byte[] array = {1   , 9, 109, 105, 110, 101, 99, 114, 97, 102, 116, 4, 99, 111, 114, 101, 6, 49, 46, 50, 49, 46, 49};
        NetworkHandler.debugBuf(packet.getBuf());
        buf.writeBytes(array);


        //I couldn't get to create a new one because arrays are weird so just copy the one from the server
        //buf.writeBytes(packet.getBuf());

        //PacketUtil.writeVarInt(buf, 1);
        //PacketUtil.encodeString(buf, "minecraft");
        //PacketUtil.encodeString(buf, "core");
        //PacketUtil.encodeString(buf, "1.21.1");


    }
}
