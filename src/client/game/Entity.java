package client.game;

import client.HeadlessInstance;
import java.util.UUID;
import math.Box;
import math.Vec3d;

public class Entity {
    private int entityID;
    private Vec3d pos;
    private Vec3d velocity;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private final UUID uuid;
    private final HeadlessInstance instance;
    private Box boundingBox;
    private final EntityType<?> entityType;

    public <T extends Entity> Entity(int entityID, Vec3d pos, Vec3d velocity, float yaw, float pitch, boolean onGround, EntityType<T> entityType, UUID uuid, HeadlessInstance instance) {
        this.entityID = entityID;
        this.pos = pos;
        this.velocity = velocity;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.entityType = entityType;
        this.uuid = uuid;
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
    public void setVelocity(double x, double y, double z) {
        setVelocity(new Vec3d(x, y, z));
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

    public UUID getUuid() {
        return this.uuid;
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

    public double getHeight() {
        return entityType.getHeight();
    }

    public double getWidth() {
        return entityType.getWidth();
    }

    public double getDepth() {
        return entityType.getDepth();
    }
    public Box getBoundingBox() {
        calcBoundingBox();
        return this.boundingBox;
    }

    public int getEntityID() {
        return entityID;
    }
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public void onTick() {
        calcBoundingBox();
    }

    public void setPos(double x, double y, double z) {
        setPos(new Vec3d(x, y, z));
    }

    public void calcBoundingBox() {
        this.boundingBox = calcBoundingBox(getPos());
    }

    public Box calcBoundingBox(Vec3d offset) {
        return entityType.getBoundingBox().offset(offset);
    }

    public <T extends Entity> EntityType<T> getEntityType() {
        return (EntityType<T>) entityType;
    }
}
