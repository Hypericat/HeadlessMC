package math;

import client.networking.packets.C2S.play.BlockFace;

public class Vec3i implements Comparable<Vec3i> {

    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vec3i(Vec3i vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public static Vec3i ofRounded(Vec3d vec3d) {
        return new Vec3i((int) Math.round(vec3d.getX()), (int) Math.round(vec3d.getY()), (int) Math.round(vec3d.getZ()));
    }

    public static Vec3i ofFloored(Vec3d vec3d) {
        return new Vec3i(MathHelper.floor(vec3d.getX()), MathHelper.floor(vec3d.getY()), MathHelper.floor(vec3d.getZ()));
    }

    public static Vec3i ofCeil(Vec3d vec3d) {
        return new Vec3i((int) Math.ceil(vec3d.getX()), (int) Math.ceil(vec3d.getY()), (int) Math.ceil(vec3d.getZ()));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Vec3i vec3i)) {
            return false;
        } else if (this.getX() != vec3i.getX()) {
            return false;
        } else {
            return this.getY() != vec3i.getY() ? false : this.getZ() == vec3i.getZ();
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(Vec3i vec3i) {
        if (this.getY() == vec3i.getY()) {
            return this.getZ() == vec3i.getZ() ? this.getX() - vec3i.getX() : this.getZ() - vec3i.getZ();
        } else {
            return this.getY() - vec3i.getY();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public Vec3i withX(int x) {
        return new Vec3i(x, this.getY(), this.getZ());
    }
    public Vec3i withY(int y) {
        return new Vec3i(this.getX(), y, this.getZ());
    }
    public Vec3i withZ(int z) {
        return new Vec3i(this.getX(), this.getY(), z);
    }

    protected Vec3i setX(int x) {
        this.x = x;
        return this;
    }

    protected Vec3i setY(int y) {
        this.y = y;
        return this;
    }

    protected Vec3i setZ(int z) {
        this.z = z;
        return this;
    }


    public Vec3i add(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new Vec3i(this.getX() + x, this.getY() + y, this.getZ() + z);
    }


    public Vec3i add(Vec3i vec) {
        return this.add(vec.getX(), vec.getY(), vec.getZ());
    }


    public Vec3i subtract(Vec3i vec) {
        return this.subtract(vec.getX(), vec.getY(), vec.getZ());
    }
    public Vec3i subtract(int x, int y, int z) {
        return this.add(-x, -y, -z);
    }


    public Vec3i multiply(int scale) {
        if (scale == 1) {
            return this;
        } else {
            return scale == 0 ? ZERO : new Vec3i(this.getX() * scale, this.getY() * scale, this.getZ() * scale);
        }
    }


    public Vec3i crossProduct(Vec3i vec) {
        return new Vec3i(
                this.getY() * vec.getZ() - this.getZ() * vec.getY(),
                this.getZ() * vec.getX() - this.getX() * vec.getZ(),
                this.getX() * vec.getY() - this.getY() * vec.getX()
        );
    }

    public Vec3i with(BlockFace face, int value) {
        if (face == BlockFace.DOWN || face == BlockFace.UP) {
            return this.withY(value);
        }
        if (face == BlockFace.EAST || face == BlockFace.WEST) {
            return this.withX(value);
        }
        return this.withZ(value);
    }


    public boolean isWithinDistance(Vec3i vec, double distance) {
        return this.getSquaredDistance(vec) < distance * distance;
    }

    public boolean isWithinDistance(Position pos, double distance) {
        return this.getSquaredDistance(pos) < distance * distance;
    }

    public double getSquaredDistance(Vec3i vec) {
        return this.getSquaredDistance((double)vec.getX(), (double)vec.getY(), (double)vec.getZ());
    }

    public double getSquaredDistance(Position pos) {
        return this.getSquaredDistanceFromCenter(pos.getX(), pos.getY(), pos.getZ());
    }

    public double getSquaredDistanceFromCenter(double x, double y, double z) {
        double d = (double)this.getX() + 0.5 - x;
        double e = (double)this.getY() + 0.5 - y;
        double f = (double)this.getZ() + 0.5 - z;
        return d * d + e * e + f * f;
    }

    public double getSquaredDistance(double x, double y, double z) {
        double d = (double)this.getX() - x;
        double e = (double)this.getY() - y;
        double f = (double)this.getZ() - z;
        return d * d + e * e + f * f;
    }

    public int getManhattanDistance(Vec3i vec) {
        float f = (float)Math.abs(vec.getX() - this.getX());
        float g = (float)Math.abs(vec.getY() - this.getY());
        float h = (float)Math.abs(vec.getZ() - this.getZ());
        return (int)(f + g + h);
    }

    public String toString() {
        return "x : " + this.getX() + " Y : " + this.getY() + " Z : " + this.getZ();
    }

    public String toShortString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }
}
