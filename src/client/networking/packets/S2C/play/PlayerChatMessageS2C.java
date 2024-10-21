package client.networking.packets.S2C.play;

import client.networking.ClientPacketListener;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import client.utils.PacketUtil;
import client.utils.UUID;
import io.netty.buffer.ByteBuf;
import math.Vec3i;

import java.util.Optional;

public class PlayerChatMessageS2C extends S2CPacket {
    public final static PacketID packetID = PacketIDS.PLAYER_CHAT_PLAY_S2C;
    private UUID senderUUID;
    private int index;
    private Optional<byte[]> signature;
    String message;

    public PlayerChatMessageS2C(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onChatMessageS2C(this);
    }

    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.senderUUID = UUID.fromBuf(buf);
        this.index = PacketUtil.readVarInt(buf);
        boolean messageSignature = buf.readBoolean();
        if (messageSignature) {
            byte[] array = new byte[256];
            buf.readBytes(array);
            this.signature = Optional.of(array);
        } else {
            this.signature = Optional.empty();
        }
        int messageSize = PacketUtil.readVarInt(buf);
        this.message = PacketUtil.readString(buf, messageSize);
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public int getIndex() {
        return index;
    }

    public Optional<byte[]> getSignature() {
        return signature;
    }

    public String getMessage() {
        return message;
    }
}
