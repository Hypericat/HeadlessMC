package client.networking;

import client.networking.packets.C2S.LoginAcknowledgedC2SPacket;
import client.networking.packets.S2C.CompressionRequestS2CPacket;
import client.networking.packets.S2C.EncryptionRequestS2CPacket;
import client.networking.packets.S2C.LoginSuccessfulS2CPacket;
import client.networking.packets.S2C.StatusResponseS2CPacket;
import client.HeadlessInstance;

import java.util.Arrays;

public class PacketHandler implements ClientPacketListener {
    private HeadlessInstance instance;

    public PacketHandler(HeadlessInstance instance) {
        this.instance = instance;
    }

    @Override
    public void onStatus(StatusResponseS2CPacket packet) {
        String response = packet.getString();
        System.out.println("Received status response from server");
        System.out.println(response);
    }

    @Override
    public void onEncryption(EncryptionRequestS2CPacket packet) {
        System.out.println("Received encryption packet from server");
        System.out.println("Server id " + packet.getServerID());
        System.out.println("Public Key Length " + packet.getPublicKeyLength());
        System.out.println("Public Key " + Arrays.toString(packet.getPublicKey()));
        System.out.println("Token Length " + packet.getVerifyTokenLength());
        System.out.println("Token " + Arrays.toString(packet.getVerifyToken()));
        System.out.println("Should Authenticate " + packet.isShouldAuthenticate());
    }

    @Override
    public void onCompression(CompressionRequestS2CPacket packet) {
        int compressionType = packet.getCompressionType();
        System.out.println("Received compression request from server");
        System.out.println(compressionType);
    }

    @Override
    public void onLoginSuccess(LoginSuccessfulS2CPacket packet) {
        instance.getNetworkHandler().setCompressionEnabled(true);
        instance.getNetworkHandler().sendPacket(new LoginAcknowledgedC2SPacket());
    }
}
