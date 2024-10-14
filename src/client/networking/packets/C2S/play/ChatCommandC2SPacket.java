package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public class ChatCommandC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.CHAT_COMMAND_PLAY_C2S;

    private final String command;

    public ChatCommandC2SPacket(String command) {
        this.command = command;
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
        buf.writeByte(this.getTypeID());
        PacketUtil.writeString(buf, command);
    }
}
