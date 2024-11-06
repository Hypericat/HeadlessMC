package math;

public class MathHelper {
    public static final float THREEHALFS = 1.5f;
    private static final double ROUNDER_256THS = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ARCSINE_TABLE = new double[257];
    private static final double[] COSINE_OF_ARCSINE_TABLE = new double[257];

    //initialize arccosine and arcsine tables
    static {
        for (int i = 0; i < 257; i++) {
            double d = (double)i / 256.0;
            double e = Math.asin(d);
            COSINE_OF_ARCSINE_TABLE[i] = Math.cos(e);
            ARCSINE_TABLE[i] = e;
        }
    }

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

    public static float Qrsqrt(float f) {
        return Qrsqrt(f, 2);
    }

    public static double Qrsqrt(double d) {
        return Qrsqrt(d, 2);
    }

    public static float Qrsqrt(float f, int accuracy) {
        float x2 = 0.5f * f;
        int i = Float.floatToIntBits(f);
        i = 0x5f3759df - (i >> 1);
        f = Float.intBitsToFloat(i);
        for (int j = 0; j < accuracy; j++) {
            f *= (THREEHALFS - x2 * f * f); //newton iterations
        }
        return f;
    }

    public static double Qrsqrt(double d, int accuracy) {
        double x2 = 0.5d * d;
        long i = Double.doubleToLongBits(d);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        d = Double.longBitsToDouble(i);
        for (int j = 0; j < accuracy; j++) {
            d *= (THREEHALFS - x2 * d * d);
        }
        return d;
    }
    public static float wrapDegrees(float degrees) {
        float f = degrees % 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }

    public static double atan2(double y, double x) {
        double d = x * x + y * y;
        if (Double.isNaN(d)) {
            return Double.NaN;
        } else {
            boolean bl = y < 0.0;
            if (bl) {
                y = -y;
            }

            boolean bl2 = x < 0.0;
            if (bl2) {
                x = -x;
            }

            boolean bl3 = y > x;
            if (bl3) {
                double e = x;
                x = y;
                y = e;
            }

            double e = Qrsqrt(d, 1);
            x *= e;
            y *= e;
            double f = ROUNDER_256THS + y;
            int i = (int)Double.doubleToRawLongBits(f);
            double g = ARCSINE_TABLE[i];
            double h = COSINE_OF_ARCSINE_TABLE[i];
            double j = f - ROUNDER_256THS;
            double k = y * h - x * j;
            double l = (6.0 + k * k) * k * 0.16666666666666666;
            double m = g + l;
            if (bl3) {
                m = (Math.PI / 2) - m;
            }

            if (bl2) {
                m = Math.PI - m;
            }

            if (bl) {
                m = -m;
            }

            return m;
        }
    }
}
