package client.networking;

import client.networking.packets.S2C.CompressionRequestS2CPacket;
import client.networking.packets.S2C.EncryptionRequestS2CPacket;
import client.networking.packets.S2C.LoginSuccessfulS2CPacket;
import client.networking.packets.S2C.StatusResponseS2CPacket;

public interface ClientPacketListener extends PacketListener {
    void onStatus(StatusResponseS2CPacket packet);
    void onEncryption(EncryptionRequestS2CPacket packet);
    void onCompression(CompressionRequestS2CPacket packet);
    void onLoginSuccess(LoginSuccessfulS2CPacket packet);
}
