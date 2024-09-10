package networking;

import networking.packets.S2C.StatusResponseS2CPacket;

public interface ClientPacketListener extends PacketListener {
    void onStatus(StatusResponseS2CPacket packet);
}
