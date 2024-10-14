package client.networking;

public enum NetworkState {
    HANDSHAKE(Integer.MAX_VALUE),
    CONFIGURATION(((long) Integer.MAX_VALUE) << 1),
    PLAY(((long) Integer.MAX_VALUE) << 2),
    LOGIN(((long) Integer.MAX_VALUE) << 3),
    STATUS(((long) Integer.MAX_VALUE) << 4);





    private final long offset;

    NetworkState(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public long calcOffset(long original) {
        return getOffset() + original;
    }
}
