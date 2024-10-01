package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.BlockFace;
import client.networking.packets.C2S.play.ClientStatusC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveFullC2SPacket;
import math.BlockRaycastResult;
import math.Box;
import math.Vec3d;
import math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class ClientPlayerEntity extends PlayerEntity{
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
        attackCooldown --;
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

    public void onBlockCollision(Vec3i blockPos, World world, Box playerBoundingBox, Vec3d playerPos) {
        Block block = world.getBlock(blockPos);
        if (block == Blocks.AIR) return;
        List<Box> boundingBoxes = new ArrayList<>();
        boundingBoxes.add(playerBoundingBox);
        boundingBoxes.add(new Box(Vec3d.ZERO, new Vec3d(1, 1, 1)).offset(blockPos));
        BlockRaycastResult raycast = Box.raycast(boundingBoxes, playerBoundingBox.getRelativeCenter(), Vec3d.of(blockPos));
        BlockFace direction = raycast.getFace();
        System.out.println("Block type : " + block + " direction : " + direction);
        if (direction == null) return;
        validateCollision(direction, blockPos);
        if (direction == BlockFace.DOWN) {
            this.setOnGround(true);
        }

    }
    private void updateVelocity() {
        checkBlockCollision();
        this.setPos(this.getPos().add(this.getVelocity()));
    }

    private void validateCollision(BlockFace direction, Vec3i blockPos) {
        double x = this.getVelocity().x;
        double y = this.getVelocity().y;
        double z = this.getVelocity().z;

        if (direction == BlockFace.DOWN || direction == BlockFace.UP) {
            this.setVelocity(x, 0, z);
            this.setY(blockPos.getY() + 1 * BlockFace.getSign(direction));
            return;
        }
        if (direction == BlockFace.EAST || direction == BlockFace.WEST) {
            this.setVelocity(0, y, z);
            this.setX(blockPos.getX() + 1 * BlockFace.getSign(direction));
            //if (getInstance().getAutoJump()) jump();
            return;
        }
        if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH) {
            this.setVelocity(x, y, 0);
            this.setZ(blockPos.getZ() + 1 * BlockFace.getSign(direction));
            //if (getInstance().getAutoJump()) jump();
            return;
        }
    }

    public void tickFalling(Vec3d velocity) {
        double gravity = 0.08;
        double yVel = velocity.y;
        yVel -= gravity;
        this.setVelocity(velocity.x, yVel, velocity.z);
    }

    public void checkBlockCollision() {
        Vec3d nextPos = getPos().add(this.getVelocity());
        Box newBound = calcBoundingBox(nextPos);
        Vec3d min = newBound.getMinPos().add(1.0E-7);
        Vec3d max = newBound.getMaxPos().subtract(1.0E-7);
        Vec3i bottom = Vec3i.ofFloored(min);
        Vec3i top = Vec3i.ofFloored(max);

        for (int i = bottom.getX(); i <= top.getX(); i++) {
            for (int k = bottom.getY(); k <= top.getY(); k++) {
                for (int j = bottom.getZ(); j <= top.getZ(); j++) {
                    Vec3i pos = new Vec3i(i, k, j);
                    onBlockCollision(pos, getInstance().getWorld(), newBound, nextPos);
                }
            }
        }
        this.setOnGround(false);

    }

    public void jump() {
        if (!this.isOnGround()) return;
        this.setVelocity(this.getVelocity().add(0 , 0.60, 0));
    }

}
