package client.networking.packets.C2S.configuration;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ClientInformationC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.CLIENT_INFORMATION_CONFIGURATION_C2S;

    private String locale;
    byte viewDistance;
    int chatMode; //0 for enabled, 1 for commands only, 2 for hidden
    boolean chatColors;
    byte skinParts;
    int mainHand;
    boolean textFiltering;
    boolean allowServerListings;

    public ClientInformationC2SPacket(byte viewDistance) {
        this("en_GB", viewDistance, 0, false, (byte) 0x01, 1, false, true);
    }

    public ClientInformationC2SPacket(String locale, byte viewDistance, int chatMode, boolean chatColors, byte skinParts, int mainHand, boolean textFiltering, boolean allowServerListings) {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.skinParts = skinParts;
        this.mainHand = mainHand;
        this.textFiltering = textFiltering;
        this.allowServerListings = allowServerListings;
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
        PacketUtil.writeString(buf, locale);
        buf.writeByte(viewDistance);
        PacketUtil.writeVarInt(buf, chatMode);
        buf.writeBoolean(chatColors);
        buf.writeByte(skinParts);
        PacketUtil.writeVarInt(buf, mainHand);
        buf.writeBoolean(textFiltering);
        buf.writeBoolean(allowServerListings);
    }

}
