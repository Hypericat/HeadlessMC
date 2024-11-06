package math;

import java.util.Random;

public class Vec3d implements Position {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public final double x;
    public final double y;
    public final double z;


    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3d of(Vec3i vec) {
        return new Vec3d(vec.getX(), vec.getY(), vec.getZ());
    }

    public static Vec3d add(Vec3i vec, double deltaX, double deltaY, double deltaZ) {
        return new Vec3d((double)vec.getX() + deltaX, (double)vec.getY() + deltaY, (double)vec.getZ() + deltaZ);
    }

    public static Vec3d fromBlock(Vec3i vec) {
        return new Vec3d(vec.getX() + 0.5d, vec.getY(), vec.getZ() + 0.5d);
    }

    public static Vec3d ofCenter(Vec3i vec) {
        return add(vec, 0.5, 0.5, 0.5);
    }

    public static Vec3d ofBottomCenter(Vec3i vec) {
        return add(vec, 0.5, 0.0, 0.5);
    }

    public static Vec3d ofCenter(Vec3i vec, double deltaY) {
        return add(vec, 0.5, deltaY, 0.5);
    }

    public Vec3i toVec3i() {
        return new Vec3i((int) Math.floor(x), (int) Math.round(y), (int) Math.floor(z));
    }

    public static Vec3d unpackRgb(int rgb) {
        double d = (double)(rgb >> 16 & 0xFF) / 255.0;
        double e = (double)(rgb >> 8 & 0xFF) / 255.0;
        double f = (double)(rgb & 0xFF) / 255.0;
        return new Vec3d(d, e, f);
    }

    public Vec3d relativize(Vec3d vec) {
        return new Vec3d(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    public Vec3d normalize() {
        double d = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d < 1.0E-4 ? ZERO : new Vec3d(this.x / d, this.y / d, this.z / d);
    }

    public Vec3d fastNormalize() {
        double d = MathHelper.Qrsqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d < 1.0E-4 ? ZERO : new Vec3d(this.x * d, this.y * d, this.z * d);
    }

    public double dotProduct(Vec3d vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public Vec3d crossProduct(Vec3d vec) {
        return new Vec3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vec3d subtract(Vec3d vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Vec3d subtract(double x, double y, double z) {
        return this.add(-x, -y, -z);
    }
    public Vec3d subtract(double d) {
        return subtract(d, d, d);
    }

    public Vec3d add(Vec3d vec) {
        return this.add(vec.x, vec.y, vec.z);
    }

    public Vec3d add(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }
    public Vec3d add(double d) {
        return add(d, d, d);
    }

    public boolean isInRange(Vec3d pos, double radius) {
        return this.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < radius * radius;
    }

    public Vec3d setX(double x) {
        return new Vec3d(x, this.getY(), this.getZ());
    }
    public Vec3d setY(double y) {
        return new Vec3d(this.getX(), y, this.getZ());
    }
    public Vec3d setZ(double z) {
        return new Vec3d(this.getX(), this.getY(), z);
    }


    public double distanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        double e = vec.y - this.y;
        double f = vec.z - this.z;
        return Math.sqrt(d * d + e * e + f * f);
    }

    public double squaredDistanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        double e = vec.y - this.y;
        double f = vec.z - this.z;
        return d * d + e * e + f * f;
    }

    public double squaredDistanceTo(double x, double y, double z) {
        double d = x - this.x;
        double e = y - this.y;
        double f = z - this.z;
        return d * d + e * e + f * f;
    }

    public Vec3d multiply(double value) {
        return this.multiply(value, value, value);
    }

    public Vec3d negate() {
        return this.multiply(-1.0);
    }

    public Vec3d multiply(Vec3d vec) {
        return this.multiply(vec.x, vec.y, vec.z);
    }

    public Vec3d multiply(double x, double y, double z) {
        return new Vec3d(this.x * x, this.y * y, this.z * z);
    }


    public Vec3d addRandom(Random random, float multiplier) {
        return this.add(
                (double)((random.nextFloat() - 0.5F) * multiplier), (double)((random.nextFloat() - 0.5F) * multiplier), (double)((random.nextFloat() - 0.5F) * multiplier)
        );
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }


    public double horizontalLength() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public double horizontalLengthSquared() {
        return this.x * this.x + this.z * this.z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Vec3d vec3d)) {
            return false;
        } else if (Double.compare(vec3d.x, this.x) != 0) {
            return false;
        } else {
            return Double.compare(vec3d.y, this.y) != 0 ? false : Double.compare(vec3d.z, this.z) == 0;
        }
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.x);
        int i = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.z);
        return 31 * i + (int)(l ^ l >>> 32);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vec3d lerp(Vec3d to, double delta) {
        return new Vec3d(MathHelper.lerp(delta, this.x, to.x), MathHelper.lerp(delta, this.y, to.y), MathHelper.lerp(delta, this.z, to.z));
    }


    public Vec3d rotateX(float angle) {
        double f = Math.cos(angle);
        double g = Math.sin(angle);
        double d = this.x;
        double e = this.y * f + this.z * g;
        double h = this.z * f - this.y * g;
        return new Vec3d(d, e, h);
    }


    public Vec3d rotateY(float angle) {
        double f = Math.cos(angle);
        double g = Math.sin(angle);
        double d = this.x * f + this.z * g;
        double e = this.y;
        double h = this.z * f - this.x * g;
        return new Vec3d(d, e, h);
    }


    public Vec3d rotateZ(float angle) {
        double f = Math.cos(angle);
        double g = Math.sin(angle);
        double d = this.x * f + this.y * g;
        double e = this.y * f - this.x * g;
        double h = this.z;
        return new Vec3d(d, e, h);
    }



    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final double getZ() {
        return this.z;
    }
}
