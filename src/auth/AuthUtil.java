package auth;

import client.networking.packets.S2C.configuration.EncryptionRequestS2CPacket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class AuthUtil {


    public static byte[] hash(byte[]... bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

            for (byte[] bs : bytes) {
                messageDigest.update(bs);
            }

            return messageDigest.digest();

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] computeServerId(String baseServerId, PublicKey publicKey, SecretKey secretKey) {
        try {
            return hash(baseServerId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String hashString(String s) {
        return getStringFromHash(hash(s.getBytes(Charset.defaultCharset())));
    }

    public static String getStringFromHash(byte[] hash) {
        return new BigInteger(hash).toString(16);
    }

    public void onEncrypt(EncryptionRequestS2CPacket packet) {
        /*
        this.switchTo(ClientLoginNetworkHandler.State.AUTHORIZING);

        Cipher cipher;
        Cipher cipher2;
        String string;
        LoginKeyC2SPacket loginKeyC2SPacket;
        try {
            SecretKey secretKey = NetworkEncryptionUtils.generateSecretKey();
            PublicKey publicKey = packet.getPublicKey();
            string = new BigInteger(NetworkEncryptionUtils.computeServerId(packet.getServerId(), publicKey, secretKey)).toString(16);
            cipher = NetworkEncryptionUtils.cipherFromKey(2, secretKey);
            cipher2 = NetworkEncryptionUtils.cipherFromKey(1, secretKey);
            byte[] bs = packet.getNonce();
            loginKeyC2SPacket = new LoginKeyC2SPacket(secretKey, publicKey, bs);
        } catch (Exception var9) {
            throw new IllegalStateException("Protocol error", var9);
        }

        if (packet.needsAuthentication()) {
            Util.getIoWorkerExecutor().submit(() -> {
                Text text = this.joinServerSession(string);
                if (text != null) {
                    if (this.serverInfo == null || !this.serverInfo.isLocal()) {
                        this.connection.disconnect(text);
                        return;
                    }

                    LOGGER.warn(text.getString());
                }

                this.setupEncryption(loginKeyC2SPacket, cipher, cipher2);
            });
        } else {
            this.setupEncryption(loginKeyC2SPacket, cipher, cipher2);
        }
         */
    }
}
