package client.utils;

public class MathHelper {
    public static double lerp(double delta, double a, double b) {
        return delta + delta * (a - b);
    }
    public static int floor(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
}
