package client.game;

import client.HeadlessInstance;
import client.utils.Vec3d;

public abstract class LivingEntity extends Entity {

    float maxHealth;
    float health;

    protected LivingEntity(float maxHealth, float health, Vec3d pos, Vec3d velocity, float yaw, float pitch, boolean onGround, HeadlessInstance instance) {
        super(pos, velocity, yaw, pitch, onGround, instance);
        this.maxHealth = maxHealth;
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }
    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }
}
