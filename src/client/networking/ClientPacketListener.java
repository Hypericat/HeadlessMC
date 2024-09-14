package client.networking;

import client.networking.packets.S2C.*;

public interface ClientPacketListener extends PacketListener {
    void onStatus(StatusResponseS2CPacket packet);
    void onEncryption(EncryptionRequestS2CPacket packet);
    void onCompression(CompressionRequestS2CPacket packet);
    void onLoginSuccess(LoginSuccessfulS2CPacket packet);

    void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet);
    void onKnowPacks(ClientBoundKnownPacksS2CPacket packet);
}
