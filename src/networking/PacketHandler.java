package networking;

import networking.packets.C2S.C2SPacket;
import networking.packets.Packet;
import networking.packets.S2C.StatusResponseS2CPacket;

public class PacketHandler implements ClientPacketListener {
    @Override
    public void onStatus(StatusResponseS2CPacket packet) {
        String response = packet.getString();
        System.out.println("Received status response from server");
        System.out.println(response);
    }
}
