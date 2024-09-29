package client.utils;

import io.netty.buffer.ByteBuf;

public class UUID {
    private long mostSignificant;
    private long leastSignificant;

    public UUID(long mostSignificant, long leastSignificant) {
        this.mostSignificant = mostSignificant;
        this.leastSignificant = leastSignificant;
    }
    public static UUID fromBuf(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public long getMostSignificant() {
        return mostSignificant;
    }

    public void setMostSignificant(long mostSignificant) {
        this.mostSignificant = mostSignificant;
    }

    public long getLeastSignificant() {
        return leastSignificant;
    }

    public void setLeastSignificant(long leastSignificant) {
        this.leastSignificant = leastSignificant;
    }
}
