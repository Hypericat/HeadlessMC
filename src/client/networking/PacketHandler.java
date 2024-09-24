package client.networking;

import client.networking.packets.C2S.configuration.AcknowledgedFinishedConfigurationC2SPacket;
import client.networking.packets.C2S.configuration.ClientInformationC2SPacket;
import client.networking.packets.C2S.configuration.LoginAcknowledgedC2SPacket;
import client.networking.packets.C2S.configuration.ServerBoundKnownPacksC2SPacket;
import client.HeadlessInstance;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.KeepAliveS2CPacket;

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
    public void onCookieRequest(CookieRequestS2CPacket packet) {
        System.out.println("test");
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
        instance.getNetworkHandler().sendPacket(new ClientInformationC2SPacket());

        instance.getNetworkHandler().setNetworkState(NetworkState.CONFIGURATION);
        instance.config();
    }

    @Override
    public void onFinishConfiguration(FinishConfigurationS2CPacket packet) {
        instance.getNetworkHandler().setNetworkState(NetworkState.PLAY);
        instance.getNetworkHandler().sendPacket(new AcknowledgedFinishedConfigurationC2SPacket());
    }

    @Override
    public void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet) {
        System.out.println("Received server brand : " + packet.getText());
    }

    @Override
    public void onKnowPacks(ClientBoundKnownPacksS2CPacket packet) {
        instance.getNetworkHandler().sendPacket(new ServerBoundKnownPacksC2SPacket(packet));


        instance.getNetworkHandler().setNetworkState(NetworkState.PLAY);
        instance.getNetworkHandler().sendPacket(new AcknowledgedFinishedConfigurationC2SPacket());

    }

    @Override
    public void onKeepAlive(KeepAliveS2CPacket packet) {
        //instance.getNetworkHandler().sendPacket(new KeepAliveC2SPacket(packet.getId()));
    }
}
