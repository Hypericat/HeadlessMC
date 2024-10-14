package client.networking.packets;

import client.networking.Boundness;
import client.networking.NetworkState;

public class PacketID {
    private final Boundness boundness;
    private final NetworkState networkState;
    private final int packetID;
    private final String identifier;

    public PacketID(Boundness boundness, NetworkState networkState, int packetID, String identifier) {
        this.boundness = boundness;
        this.networkState = networkState;
        this.packetID = packetID;
        this.identifier = identifier;
    }

    public Boundness getBoundness() {
        return boundness;
    }

    public NetworkState getNetworkState() {
        return networkState;
    }

    public int getPacketID() {
        return packetID;
    }

    public String getIdentifier() {
        return identifier;
    }
    public String toString() {
        return identifier + " " + networkState.name() + " " + boundness.name();
    }
    public long getOffset() {
        return networkState.calcOffset(packetID);
    }
}
