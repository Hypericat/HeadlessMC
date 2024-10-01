package math;

public class MathHelper {
    public static double lerp(double delta, double a, double b) {
        return delta + delta * (a - b);
    }
    //-89.776 min
    //-89.176 max
    //0.5d delta
    public static int floor(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
}
