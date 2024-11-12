package auth;

import client.networking.packets.S2C.configuration.EncryptionRequestS2CPacket;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

public class AuthUtil {
    private AuthUtil() {

    }


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

    public static SecretKey generateSecretKey()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static PublicKey decodeEncodedRsaPublicKey(byte[] key) {
        try {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Cipher cipherFromKey(int opMode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] encrypt(Key key, byte[] data) {
        return crypt(1, key, data);
    }

    private static byte[] crypt(int opMode, Key key, byte[] data) {
        try {
            return createCipher(opMode, key.getAlgorithm(), key).doFinal(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Cipher createCipher(int opMode, String algorithm, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(opMode, key);
        return cipher;
    }

    public static void authenticateSession(Account account, String hash) {
        if (!account.isPremium()) throw new IllegalStateException();
        String payload =
                "  {\n" +
                        "    \"accessToken\": \"" + account.getSessionID() + "\",\n" +
                        "    \"selectedProfile\": \"" + UndashedUuid.toString(account.getUuid()) + "\",\n" +
                        "    \"serverId\": \"" + hash + "\"\n" +
                        "  }";
        httpPost("https://sessionserver.mojang.com/session/minecraft/join", payload);
    }

    public static void httpPost(String strUrl, String payload) {
        try{
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            //connection.setRequestProperty("Accept", "application/json");
            //connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes()));
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);
            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                String response = connection.getResponseMessage();
                throw new IllegalStateException("Failed to authenticate with minecraft server : " + responseCode + " " + response);
            }
            connection.disconnect();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
