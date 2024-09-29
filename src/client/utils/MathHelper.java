package client.utils;

public class MathHelper {
    public static double lerp(double delta, double a, double b) {
        return delta + delta * (a - b);
    }
}
