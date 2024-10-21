package client.game;

import client.HeadlessInstance;
import client.game.inventory.Inventory;
import client.game.inventory.LivingInventory;
import client.utils.UUID;
import math.Vec3d;

public class LivingEntity extends Entity {

    float maxHealth;
    float health;
    private LivingInventory inventory;

    public <T extends LivingEntity> LivingEntity(int entityID, float maxHealth, float health, Vec3d pos, Vec3d velocity, float yaw, float pitch, boolean onGround, EntityType<T> type, UUID uuid, HeadlessInstance instance) {
        super(entityID, pos, velocity, yaw, pitch, onGround, type, uuid, instance);
        this.maxHealth = maxHealth;
        this.health = health;
        this.inventory = LivingInventory.empty();
    }

    public LivingInventory getInventory() {
        return inventory;
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
