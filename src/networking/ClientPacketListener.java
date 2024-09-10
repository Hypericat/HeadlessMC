package networking;

import networking.packets.StatusRequestS2CPacket;

public interface ClientPacketListener extends PacketListener {
    void onStatus(StatusRequestS2CPacket packet);
}
