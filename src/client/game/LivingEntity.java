package client.game;

import client.HeadlessInstance;
import math.Vec3d;

public class LivingEntity extends Entity {

    float maxHealth;
    float health;

    public <T extends LivingEntity> LivingEntity(int entityID, float maxHealth, float health, Vec3d pos, Vec3d velocity, float yaw, float pitch, boolean onGround, EntityType<T> type, HeadlessInstance instance) {
        super(entityID, pos, velocity, yaw, pitch, onGround, type, instance);
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
    @Override
    public void onTick() {
        super.onTick();

    }
}
