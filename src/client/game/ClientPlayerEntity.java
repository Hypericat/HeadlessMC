package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.BlockFace;
import client.networking.packets.C2S.play.ClientStatusC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveFullC2SPacket;
import math.*;

import java.util.ArrayList;
import java.util.List;

public class ClientPlayerEntity extends PlayerEntity {
    private int selectedSlot;
    private int chunkX;
    private int chunkZ;
    private int attackCooldown = 0;

    public ClientPlayerEntity(int entityID, HeadlessInstance instance) {
        super(entityID, instance);
    }

    @Override
    public void onTick() {
        super.onTick();
        tickMovement();
        decrementAttackCooldown();

    }

    public int getChunkX() {
        return chunkX;
    }

    public void resetAttackTicks() {
        attackCooldown = 18;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public void doKillAura() {
        for (Entity e : getInstance().getWorld().getEntitiesWithin(getBoundingBox().getCenter().add(getHeadPos()), 4d)) {
            if ((e instanceof LivingEntity) && attackCooldown <= 0) {
                getInstance().getInteractionManager().attackEntity(e);
                getInstance().getInteractionManager().lookAt(e);
                return;
            }
        }
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void decrementAttackCooldown() {
        if (attackCooldown <= 0) return;
        attackCooldown--;
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
        tickFalling(this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98d));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().getX() * 0.6d, this.getVelocity().getY(), this.getVelocity().getZ() * 0.6d);
        }
        this.setVelocity(this.getVelocity().setX(1));
        updateVelocity();
        updatePosPackets();
    }


    private void updateVelocity() {
        checkBlockCollision();
        this.setPos(this.getPos().add(this.getVelocity()));
    }

    public void tickFalling(Vec3d velocity) {
        double gravity = 0.08;
        double yVel = velocity.y;
        yVel -= gravity;
        this.setVelocity(velocity.x, yVel, velocity.z);
    }

    public void checkBlockCollision() {
        Vec3d velocity = this.getVelocity();
        Vec3d playerPos = this.getPos();
        Vec3i playerBlockPos = Vec3i.ofFloored(playerPos);


        double yVelocity = velocity.y;
        Vec3i endBlockPos = Vec3i.ofFloored(playerPos.add(0, yVelocity, 0));
        int delta = playerBlockPos.getY() - endBlockPos.getY();
        if (delta == 0) return;
        //do this for every block in the hit box X/Z plane
        Pair<Vec3i, Block> intersectingYBlock = getFirstBlockY(playerBlockPos, endBlockPos, delta);
        if (intersectingYBlock != null) {
            Vec3i blockPos = intersectingYBlock.getLeft();
            Block block = intersectingYBlock.getRight();
            Box blockBox = block.getBoundingBox().offset(blockPos);
            System.out.println("Intersecting block " + block + " at position " + blockBox.getRelativeCenter());
            this.setPos(this.getPos().setY(blockBox.getRelativeCenter().y + delta > 0 ? blockBox.getMaxPos().y : blockBox.getMinPos().y));
            this.setVelocity(this.getVelocity().setY(0));
        }


    }

    public Pair<Vec3i, Block> getFirstBlockY(Vec3i playerBlockPos, Vec3i endBlockPos, int delta) {
        for (int i = playerBlockPos.getY(); delta < 0 ? i <= endBlockPos.getY() : i >= endBlockPos.getY(); i += delta < 0 ? 1 : -1) {
            Vec3i blockPos = playerBlockPos.withY(i);
            Block block = getInstance().getWorld().getBlock(blockPos);
            if (block == Blocks.AIR) continue;
            return new Pair<>(blockPos, block);
        }
        return null;
    }


    public void oldCheckBlockCollision() {
        Vec3d velocity = this.getVelocity();
        Vec3d nextPos = getPos().add(velocity);
        Box newBound = calcBoundingBox(nextPos);
        Vec3d min = newBound.getMinPos().add(Box.EPSILON);
        Vec3d max = newBound.getMaxPos().subtract(Box.EPSILON);
        Vec3i bottom = Vec3i.ofFloored(min);
        Vec3i top = Vec3i.ofFloored(max);

        for (int i = bottom.getX(); i <= top.getX(); i++) {
            for (int k = bottom.getY(); k <= top.getY(); k++) {
                for (int j = bottom.getZ(); j <= top.getZ(); j++) {
                    Vec3i pos = new Vec3i(i, k, j);

                }
            }
        }
        this.setOnGround(false);
        System.out.println("Set final velocity " + this.getVelocity());
    }

    public void jump() {
        if (!this.isOnGround()) return;
        this.setVelocity(this.getVelocity().add(0 , 0.60, 0));
    }

}
