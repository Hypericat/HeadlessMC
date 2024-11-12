package client.networking.packets.C2S.configuration;

import auth.AuthUtil;
import client.networking.ClientPacketListener;
import client.networking.packets.C2S.C2SPacket;
import client.networking.packets.PacketID;
import client.networking.packets.PacketIDS;
import client.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class EncryptionResponseC2SPacket extends C2SPacket {
    public final static PacketID packetID = PacketIDS.KEY_LOGIN_C2S;

    private final byte[] encryptedSecretKey;
    private final byte[] verifyToken;

    public EncryptionResponseC2SPacket(SecretKey secretKey, PublicKey publicKey, byte[] sharedSecret) {
        this.encryptedSecretKey = AuthUtil.encrypt(publicKey, secretKey.getEncoded());
        this.verifyToken = AuthUtil.encrypt(publicKey, sharedSecret);
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

        PacketUtil.writeVarInt(buf, encryptedSecretKey.length);
        buf.writeBytes(encryptedSecretKey);

        PacketUtil.writeVarInt(buf, verifyToken.length);
        buf.writeBytes(verifyToken);
    }
}
