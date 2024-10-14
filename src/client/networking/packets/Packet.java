package client.networking.packets;

import client.networking.PacketListener;

public interface Packet<T extends PacketListener> {
    void apply(T listener);

    int getTypeID();

    PacketID getPacketID();
}
