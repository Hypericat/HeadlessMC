package networking;

public interface PacketListener {
    void apply(Packet<ClientPacketListener> packet);
}
