package client.game;

import client.HeadlessInstance;
import client.utils.Vec3d;

public abstract class Entity {
    private Vec3d pos;
    private Vec3d velocity;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private HeadlessInstance instance;

    protected Entity(Vec3d pos, Vec3d velocity, float yaw, float pitch, boolean onGround, HeadlessInstance instance) {
        this.pos = pos;
        this.velocity = velocity;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.instance = instance;
    }

    public Vec3d getPos() {
        return pos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    public HeadlessInstance getInstance() {
        return this.instance;
    }

    public double getX() {
        return pos.x;
    }

    public void setX(double x) {
        pos = new Vec3d(x, pos.y, pos.z);
    }

    public double getY() {
        return pos.y;
    }

    public void setY(double y) {
        pos = new Vec3d(pos.x, y, pos.z);
    }

    public double getZ() {
        return pos.z;
    }

    public void setZ(double z) {
        pos = new Vec3d(pos.x, pos.y, z);
    }

    public abstract void onTick();
}
