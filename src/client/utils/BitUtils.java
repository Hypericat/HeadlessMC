package client.utils;

public class BitUtils {
    public static int getBits(int bitIndexStart, int length, long l) {
        return (int) (l >>> bitIndexStart & (1L << length) - 1L);
    }

    public static long setBits(int bitIndexStart, int length, long l, int value) {
        long mask = ((1L << length) - 1L) << bitIndexStart;
        l &= ~mask;
        return (l | ((long) value & (1L << length) - 1L) << bitIndexStart);
    }

    public static long getBitLength(long l) {
        long n = 1;
        while (n <= l) n <<= 1;
        return (n - 1);
    }
}
