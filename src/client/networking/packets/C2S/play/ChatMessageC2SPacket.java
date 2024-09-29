package client.networking.packets.C2S.play;

import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import java.util.BitSet;
import java.util.Optional;

public class ChatMessageC2SPacket extends C2SPacket {
    public static final int typeID = 0x06;
    private final String message;
    private final long timestamp;
    private final long salt;
    private final boolean hasSignature;
    private final Optional<byte[]> signature;
    private final int messageCount;
    private final BitSet bitSet;


    public ChatMessageC2SPacket(String message) {
        this(message, System.currentTimeMillis(), 1L, false, Optional.empty(), 0, BitSet.valueOf(new byte[5]));
    }

    public ChatMessageC2SPacket(String message, long timestamp, long salt, boolean hasSignature, Optional<byte[]> signature, int messageCount, BitSet bitSet) {
        if (message.length() > 256) message = message.substring(0, 255);
        this.message = message;
        this.timestamp = timestamp;
        this.salt = salt;
        this.hasSignature = hasSignature;
        this.signature = signature;
        this.messageCount = messageCount;
        this.bitSet = bitSet;
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
        PacketUtil.writeString(buf, message);
        buf.writeLong(timestamp);
        buf.writeLong(salt);
        buf.writeBoolean(hasSignature);
        if (signature.isPresent()) {
            buf.writeBytes(signature.get());
        }
        PacketUtil.writeVarInt(buf, messageCount);
        PacketUtil.writeBitSet(bitSet, 20, buf);
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getSalt() {
        return salt;
    }

    public boolean isHasSignature() {
        return hasSignature;
    }

    public Optional<byte[]> getSignature() {
        return signature;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public BitSet getBitSet() {
        return bitSet;
    }
}
