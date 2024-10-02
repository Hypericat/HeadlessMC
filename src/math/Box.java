package math;

import client.networking.packets.C2S.play.BlockFace;

import java.util.Optional;

public class Box {
    public static final double EPSILON = 1.0E-7;
    double minX;
    double minY;
    double minZ;
    double maxX;
    double maxY;
    double maxZ;

    public static Box fromDimensions(Vec3d dimensions) {
        return fromDimensions(dimensions.x, dimensions.y, dimensions.z);
    }

    public static Box fromDimensions(double width, double height) {
        return fromDimensions(width, height, width);
    }

    public static Box fromDimensions(double x, double y, double z) {
        double f = x / 2.0F;
        double d = z / 2.0F;
        double g = y;
        return new Box(-f,0, -d, f, g, d);
    }

    public Box(Vec3d corner1, Vec3d corner2) {
        this(corner1.x, corner1.y, corner1.z, corner2.x, corner2.y, corner2.z);
    }

    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public Box(Box box) {
        this.minX = box.minX;
        this.minY = box.minY;
        this.minZ = box.minZ;
        this.maxX = box.maxX;
        this.maxY = box.maxY;
        this.maxZ = box.maxZ;
    }


    public Box expand(double x, double y, double z) {
        double d = this.minX - x;
        double e = this.minY - y;
        double f = this.minZ - z;
        double g = this.maxX + x;
        double h = this.maxY + y;
        double i = this.maxZ + z;
        return new Box(d, e, f, g, h, i);
    }

    public Box expand(double value) {
        return this.expand(value, value, value);
    }

    public Box intersection(Box box) {
        double d = Math.max(this.minX, box.minX);
        double e = Math.max(this.minY, box.minY);
        double f = Math.max(this.minZ, box.minZ);
        double g = Math.min(this.maxX, box.maxX);
        double h = Math.min(this.maxY, box.maxY);
        double i = Math.min(this.maxZ, box.maxZ);
        return new Box(d, e, f, g, h, i);
    }

    public Box union(Box box) {
        double d = Math.min(this.minX, box.minX);
        double e = Math.min(this.minY, box.minY);
        double f = Math.min(this.minZ, box.minZ);
        double g = Math.max(this.maxX, box.maxX);
        double h = Math.max(this.maxY, box.maxY);
        double i = Math.max(this.maxZ, box.maxZ);
        return new Box(d, e, f, g, h, i);
    }

    public boolean intersects(Box box) {
        return this.intersects(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
    }

    public boolean intersects(Vec3d pos1, Vec3d pos2) {
        return this.intersects(
                Math.min(pos1.x, pos2.x), Math.min(pos1.y, pos2.y), Math.min(pos1.z, pos2.z), Math.max(pos1.x, pos2.x), Math.max(pos1.y, pos2.y), Math.max(pos1.z, pos2.z)
        );
    }

    public boolean contains(double x, double y, double z) {
        return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY && z >= this.minZ && z < this.maxZ;
    }

    public double getAverageSideLength() {
        double d = this.getLengthX();
        double e = this.getLengthY();
        double f = this.getLengthZ();
        return (d + e + f) / 3.0;
    }

    public double getLengthX() {
        return this.maxX - this.minX;
    }
    public double getLengthY() {
        return this.maxY - this.minY;
    }
    public double getLengthZ() {
        return this.maxZ - this.minZ;
    }

    public Box contract(double x, double y, double z) {
        return this.expand(-x, -y, -z);
    }

    public Vec3d getCenter() {
        return new Vec3d(MathHelper.lerp(1d, minX, maxX), -MathHelper.lerp(1d, minY, maxY), MathHelper.lerp(1d, minZ, maxZ));
    }

    public Vec3d getRelativeCenter() {
        return getCenter().add(minX, minY, minZ);
    }

    public Vec3d getBottomCenter() {
        return new Vec3d(MathHelper.lerp(1d, this.minX, this.maxX), this.minY, MathHelper.lerp(1d, this.minZ, this.maxZ));
    }

    public Box offset(double x, double y, double z) {
        return new Box(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }
    public Box offset(Vec3d pos) {
        return new Box(this.minX + pos.x, this.minY + pos.y, this.minZ + pos.z, this.maxX + pos.x, this.maxY + pos.y, this.maxZ + pos.z);
    }

    public Box offset(Vec3i pos) {
        return new Box(
                this.minX + (double)pos.getX(),
                this.minY + (double)pos.getY(),
                this.minZ + (double)pos.getZ(),
                this.maxX + (double)pos.getX(),
                this.maxY + (double)pos.getY(),
                this.maxZ + (double)pos.getZ()
        );
    }


    public Vec3d getMinPos() {
        return new Vec3d(this.minX, this.minY, this.minZ);
    }

    public Vec3d getMaxPos() {
        return new Vec3d(this.maxX, this.maxY, this.maxZ);
    }

    public static BlockRaycastResult raycast(Iterable<Box> boxes, Vec3d from, Vec3d to, Vec3i blockPos) {
        double[] ds = new double[]{1.0};
        BlockFace direction = null;
        double d = to.x - from.x;
        double e = to.y - from.y;
        double f = to.z - from.z;

        for (Box box : boxes) {
            direction = traceCollisionSide(box.offset(blockPos), from, ds, direction, d, e, f);
        }

        if (direction == null) {
            return BlockRaycastResult.NULL;
        } else {
            double g = ds[0];
            return BlockRaycastResult.of(Vec3i.ofFloored(from.add(g * d, g * e, g * f)), direction);
        }
    }

    private static BlockFace traceCollisionSide(Box box, Vec3d intersectingVector, double[] traceDistanceResult, BlockFace approachDirection, double deltaX, double deltaY, double deltaZ) {
        System.out.println("dx : " + deltaX);
        System.out.println("dy : " + deltaY);
        System.out.println("dz : " + deltaZ);

        if (deltaX > EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaX,
                    deltaY,
                    deltaZ,
                    box.minX,
                    box.minY,
                    box.maxY,
                    box.minZ,
                    box.maxZ,
                    BlockFace.WEST,
                    intersectingVector.x,
                    intersectingVector.y,
                    intersectingVector.z
            );
        } else if (deltaX < -EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaX,
                    deltaY,
                    deltaZ,
                    box.maxX,
                    box.minY,
                    box.maxY,
                    box.minZ,
                    box.maxZ,
                    BlockFace.EAST,
                    intersectingVector.x,
                    intersectingVector.y,
                    intersectingVector.z
            );
        }

        if (deltaY > EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaY,
                    deltaZ,
                    deltaX,
                    box.minY,
                    box.minZ,
                    box.maxZ,
                    box.minX,
                    box.maxX,
                    BlockFace.DOWN,
                    intersectingVector.y,
                    intersectingVector.z,
                    intersectingVector.x
            );
        } else if (deltaY < -EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaY,
                    deltaZ,
                    deltaX,
                    box.maxY,
                    box.minZ,
                    box.maxZ,
                    box.minX,
                    box.maxX,
                    BlockFace.UP,
                    intersectingVector.y,
                    intersectingVector.z,
                    intersectingVector.x
            );
        }

        if (deltaZ > EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaZ,
                    deltaX,
                    deltaY,
                    box.minZ,
                    box.minX,
                    box.maxX,
                    box.minY,
                    box.maxY,
                    BlockFace.NORTH,
                    intersectingVector.z,
                    intersectingVector.x,
                    intersectingVector.y
            );
        } else if (deltaZ < -EPSILON) {
            approachDirection = traceCollisionSide(
                    traceDistanceResult,
                    approachDirection,
                    deltaZ,
                    deltaX,
                    deltaY,
                    box.maxZ,
                    box.minX,
                    box.maxX,
                    box.minY,
                    box.maxY,
                    BlockFace.SOUTH,
                    intersectingVector.z,
                    intersectingVector.x,
                    intersectingVector.y
            );
        }

        return approachDirection;
    }
    public Vec3d getDelta(Box box) {
        double dx = Math.max(Math.min(this.maxX, box.maxX) - Math.max(this.minX, box.minX), 0);
        double dy = Math.max(Math.min(this.maxY, box.maxY) - Math.max(this.minY, box.minY), 0);
        double dz = Math.max(Math.min(this.maxZ, box.maxZ) - Math.max(this.minZ, box.minZ), 0);
        return new Vec3d(dx, dy, dz);
    }

    public Vec3d getClosestPointTo(Vec3d target) {
        double d = Math.clamp(target.getX(), minX, maxX);
        double e = Math.clamp(target.getY(), minY, maxY);
        double f = Math.clamp(target.getZ(), minZ, maxZ);
        return new Vec3d(d, e, f);
    }

    private static BlockFace traceCollisionSide(
            double[] traceDistanceResult,
            BlockFace approachDirection,
            double deltaX,
            double deltaY,
            double deltaZ,
            double begin,
            double minX,
            double maxX,
            double minZ,
            double maxZ,
            BlockFace resultDirection,
            double startX,
            double startY,
            double startZ
    ) {
        double d = (begin - startX) / deltaX;
        double e = startY + d * deltaY;
        double f = startZ + d * deltaZ;
        if (0.0 < d && d < traceDistanceResult[0] && minX - EPSILON < e && e < maxX + EPSILON && minZ - EPSILON < f && f < maxZ + EPSILON) {
            traceDistanceResult[0] = d;
            return resultDirection;
        } else {
            return resultDirection;
        }
    }

    @Override
    public String toString() {
        return "Box[minX=" + minX + ", minY=" + minY + ", minZ=" + minZ + ", maxX=" + maxX + ", maxY=" + maxY + ", maxZ=" + maxZ + "]";
    }

}
