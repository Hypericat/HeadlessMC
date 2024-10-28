package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.configuration.ClientBoundKnownPacksS2CPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.unix.DomainSocketReadMode;

public class ServerBoundKnownPacksC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.SELECT_KNOWN_PACKS_CONFIGURATION_C2S;

    private final ClientBoundKnownPacksS2CPacket packet;
    public ServerBoundKnownPacksC2SPacket(ClientBoundKnownPacksS2CPacket packet) {
        this.packet = packet;
    }

    @Override
    public void apply(ClientPacketListener listener) {

    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void encode(ByteBuf buf) {

        buf.writeByte(this.getTypeID());
        //byte[] array = {1, 9, 109, 105, 110, 101, 99, 114, 97, 102, 116, 4, 99, 111, 114, 101, 6, 49, 46, 50, 49, 46, 49};
        //buf.writeBytes(array);


        //copy the packs from the server so it doesn't fucking complain
        buf.writeBytes(packet.getBuf());

        //PacketUtil.writeVarInt(buf, 1);
        //PacketUtil.encodeString(buf, "minecraft");
        //PacketUtil.encodeString(buf, "core");
        //PacketUtil.encodeString(buf, Version);


    }
}
