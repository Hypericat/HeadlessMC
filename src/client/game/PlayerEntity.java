package client.game;

import client.HeadlessInstance;
import client.utils.UUID;
import math.Vec3d;

public class PlayerEntity extends LivingEntity{
    public static final float eyeHeight = 1.62F;

    public PlayerEntity(int entityID, UUID uuid, HeadlessInstance instance) {
        super(entityID, 20, 20, Vec3d.ZERO, Vec3d.ZERO, 90, 0, true, EntityTypes.PLAYER, uuid, instance);
    }

    public Vec3d getHeadPos() {
        return this.getPos().add(0, eyeHeight, 0);
    }

    @Override
    public void onTick() {
        super.onTick();
    }
}
