package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.PlayerMoveRotC2SPacket;
import client.utils.Vec3d;

public class PlayerEntity extends LivingEntity{
    protected PlayerEntity(int entityID, HeadlessInstance instance) {
        super(entityID, 20, 20, Vec3d.ZERO, Vec3d.ZERO, 90, 0, true, EntityTypes.PLAYER, instance);
    }

    @Override
    public void onTick() {
        super.onTick();
    }
}
