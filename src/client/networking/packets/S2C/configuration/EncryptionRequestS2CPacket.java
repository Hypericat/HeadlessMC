package client.networking.packets.S2C.configuration;

import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.networking.packets.S2C.S2CPacket;
import io.netty.buffer.ByteBuf;
import client.networking.ClientPacketListener;
import client.networking.NetworkState;
import client.utils.PacketUtil;

public class EncryptionRequestS2CPacket extends S2CPacket {
    public final static PacketID packetID = PacketIDS.HELLO_LOGIN_S2C;

    private String serverID;
    private int publicKeyLength;
    private byte[] publicKey;
    private int verifyTokenLength;
    private byte[] verifyToken;
    private boolean shouldAuthenticate;

    public EncryptionRequestS2CPacket(ByteBuf buf, int size) throws IllegalArgumentException {
        super(buf, size);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onEncryption(this);
    }

    @Override
    public PacketID getPacketID() {
        return packetID;
    }

    @Override
    public void decode(ByteBuf buf) {
        //serverID = PacketUtil.readString(buf, 20);

        publicKeyLength = PacketUtil.readVarInt(buf);
        publicKey = new byte[publicKeyLength];
        buf.readBytes(publicKey);

        verifyTokenLength = PacketUtil.readVarInt(buf);
        verifyToken = new byte[verifyTokenLength];
        buf.readBytes(verifyToken);
        shouldAuthenticate = buf.readBoolean();
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public int getPublicKeyLength() {
        return publicKeyLength;
    }

    public void setPublicKeyLength(int publicKeyLength) {
        this.publicKeyLength = publicKeyLength;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public int getVerifyTokenLength() {
        return verifyTokenLength;
    }

    public void setVerifyTokenLength(int verifyTokenLength) {
        this.verifyTokenLength = verifyTokenLength;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(byte[] verifyToken) {
        this.verifyToken = verifyToken;
    }

    public boolean isShouldAuthenticate() {
        return shouldAuthenticate;
    }

    public void setShouldAuthenticate(boolean shouldAuthenticate) {
        this.shouldAuthenticate = shouldAuthenticate;
    }
}
