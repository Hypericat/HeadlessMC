package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.ClientStatusC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveFullC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveRotC2SPacket;
import client.utils.Box;
import client.utils.Vec3d;
import client.utils.Vec3i;

import java.util.List;

public class ClientPlayerEntity extends PlayerEntity{
    private int selectedSlot;
    private int chunkX;
    private int chunkZ;

    public ClientPlayerEntity(int entityID, HeadlessInstance instance) {
        super(entityID, instance);
    }
    @Override
    public void onTick() {
        super.onTick();

        for (Entity e : getInstance().getWorld().getEntitiesWithin(getPos(), 4d)) {
            getInstance().getInteractionManager().sendChatMessage("Hey I see you " + e.getEntityType().getId());
        }
        tickMovement();
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    public void doTestMovement() {
        this.setYaw((float) (Math.random() * 360));
        this.setPitch((float) (Math.random() * 180 - 90));
    }

    public void updatePosPackets() {
        getInstance().getNetworkHandler().sendPacket(new PlayerMoveFullC2SPacket(getX(), getY(), getZ(), this.getYaw(), this.getPitch(), true));
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);
        System.out.println("Set health to " + health);
        if (this.getHealth() <= 0) {
            respawn();
        }
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public void respawn() {
        setHealth(20);
        System.out.println("Respawning!");
        this.getInstance().getNetworkHandler().sendPacket(new ClientStatusC2SPacket(0));
    }

    public void tickMovement() {
        calcBoundingBox();

        doTestMovement();


        tickFalling(this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98d));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().getX() * 0.6d, this.getVelocity().getY(), this.getVelocity().getZ() * 0.6d);
        }
        this.setPos(this.getPos().add(this.getVelocity()));
        updatePosPackets();
    }

    public void tickFalling(Vec3d velocity) {
        double gravity = 0.08;
        double yVel = velocity.y;
        yVel -= gravity;
        this.setVelocity(velocity.x, yVel, velocity.z);
        checkBlockCollision();
    }

    public void checkBlockCollision() {
        Vec3d nextPos = getPos().add(this.getVelocity());
        Box newBound = calcBoundingBox(nextPos);
        Vec3d min = newBound.getMinPos().add(1.0E-7);
        Vec3d max = newBound.getMaxPos().subtract(1.0E-7);
        Vec3i bottom = Vec3i.ofFloored(min);
        Vec3i top = Vec3i.ofFloored(max);

        for (int i = bottom.getX(); i <= top.getX(); i++) {
            for (int k = bottom.getZ(); k <= top.getZ(); k++) {
                Vec3i blockPos = new Vec3i(i, bottom.getY(), k);
                Block block = this.getInstance().getWorld().getBlock(blockPos);
                if (block != Blocks.AIR) {
                    onLanding(blockPos);
                    return;
                }
            }
        }
        this.setOnGround(false);

    }

    public void jump() {
        if (!this.isOnGround()) return;
        this.setVelocity(this.getVelocity().add(0 , 0.60, 0));
    }

    public void onLanding(Vec3i blockPos) {
        this.setVelocity(this.getVelocity().x, 0, this.getVelocity().z);
        this.setOnGround(true);
        this.setPos(this.getX(), blockPos.getY() + 1, this.getZ());
    }

}
