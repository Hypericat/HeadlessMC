package networking;

import networking.packets.s2c.StatusResponseS2CPacket;

public interface ClientPacketListener extends PacketListener {
    void onStatus(StatusResponseS2CPacket packet);
}
