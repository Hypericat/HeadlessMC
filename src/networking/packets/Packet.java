package networking.packets;

import networking.PacketListener;

public interface Packet<T extends PacketListener> {
    void apply(T listener);

    int getTypeId();
}
