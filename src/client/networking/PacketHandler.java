package client.networking;

import client.networking.packets.C2S.LoginAcknowledgedC2SPacket;
import client.networking.packets.C2S.ServerBoundKnownPacksC2SPacket;
import client.networking.packets.S2C.*;
import client.HeadlessInstance;
import client.utils.PacketUtil;

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
        instance.getNetworkHandler().setCompressionEnabled(false);
        instance.getNetworkHandler().sendPacket(new LoginAcknowledgedC2SPacket());

        instance.getNetworkHandler().setNetworkState(NetworkState.CONFIGURATION);
        instance.config();
    }

    @Override
    public void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet) {
        System.out.println("Received server brand : " + packet.getText());
    }

    @Override
    public void onKnowPacks(ClientBoundKnownPacksS2CPacket packet) {
        instance.getNetworkHandler().sendPacket(new ServerBoundKnownPacksC2SPacket(packet));
    }
}
