package networking.packets;

import networking.ClientPacketListener;
import networking.Packet;
import networking.packets.s2c.StatusResponseS2CPacket;

public class PacketHandler implements ClientPacketListener {
    @Override
    public void onStatus(StatusResponseS2CPacket packet) {

    }

    @Override
    public void apply(Packet<ClientPacketListener> packet) {

    }
}
