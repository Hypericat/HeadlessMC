package client.networking;

import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.KeepAliveS2CPacket;

public interface ClientPacketListener extends PacketListener {
    //login
    void onStatus(StatusResponseS2CPacket packet);
    void onCookieRequest(CookieRequestS2CPacket packet);
    void onEncryption(EncryptionRequestS2CPacket packet);
    void onCompression(CompressionRequestS2CPacket packet);
    void onLoginSuccess(LoginSuccessfulS2CPacket packet);

    //configuration
    void onFinishConfiguration(FinishConfigurationS2CPacket packet);
    void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet);
    void onKnowPacks(ClientBoundKnownPacksS2CPacket packet);

    //play
    void onKeepAlive(KeepAliveS2CPacket packet);


}
