package client.utils;

public class Flag {
    byte flags;
    public Flag(byte flags) {
        this.flags = flags;
    }
    public boolean contains(byte mask) {
        return (this.flags & mask) != 0;
    }
    public boolean contains(int mask) {
        return contains((byte) mask);
    }
}
