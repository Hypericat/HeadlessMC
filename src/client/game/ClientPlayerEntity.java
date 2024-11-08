package client.game;

import client.HeadlessInstance;
import client.networking.packets.C2S.play.ClientStatusC2SPacket;
import client.networking.packets.C2S.play.PlayerMoveFullC2SPacket;
import client.pathing.PathfinderExecutor;
import math.*;

import java.util.ArrayList;
import java.util.List;

public class ClientPlayerEntity extends PlayerEntity {
    private int selectedSlot;
    private int chunkX;
    private int chunkZ;
    private int attackCooldown = 0;
    private PathfinderExecutor pathfinderExecutor;

    public static final float GRAVITY = 0.08f;

    public ClientPlayerEntity(int entityID, HeadlessInstance instance) {
        super(entityID, instance.getUuid(), instance);
    }

    @Override
    public void onTick() {
        super.onTick();
        doKillAura();

        tickMovement();
        decrementAttackCooldown();
    }

    public void setPathfinderExecutor(PathfinderExecutor executor) {
        this.pathfinderExecutor = executor;
    }

    public PathfinderExecutor getPathFinderExecutor() {
        return pathfinderExecutor;
    }


    public int getChunkX() {
        return chunkX;
    }

    public void resetAttackTicks() {
        attackCooldown = 6;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public Vec3i getBlockPos() {
        return Vec3i.ofFloored(this.getPos());
    }

    public void doKillAura() {
        boolean attackPlayers = false;

        for (Entity e : getInstance().getWorld().getEntitiesWithin(getBoundingBox().getCenter().add(getHeadPos()), 4d)) {
            if ((e instanceof LivingEntity living) && (living.getEntityType().getType() != EntityTypes.PLAYER.getType() || attackPlayers) && attackCooldown <= 0) {
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
        getInstance().getLogger().logUser("Set health to " + health);
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
        getInstance().getLogger().logUser("Respawning!");
        this.getInstance().getNetworkHandler().sendPacket(new ClientStatusC2SPacket(0));
    }

    public void tickMovement() {
        tickFalling(this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98d));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().getX() * 0.6d, this.getVelocity().getY(), this.getVelocity().getZ() * 0.6d);
        }
        tickPathfinder();
        updateVelocity();
        updatePosPackets();
    }

    public Vec3d getVelocityFromDir(double velocity) {
        return new Vec3d(-Math.sin(Math.toRadians(getYaw())), 0, Math.cos(Math.toRadians(getYaw()))).normalize().multiply(velocity);
    }


    private void updateVelocity() {
        checkBlockCollision();
        this.setPos(this.getPos().add(this.getVelocity()));
    }

    public void tickFalling(Vec3d velocity) {
        if (this.isPathfinding()) return;
        double yVel = velocity.y;
        yVel -= GRAVITY;
        this.setVelocity(velocity.x, yVel, velocity.z);
    }

    public void checkBlockCollision() {
        doYCollision(this.getVelocity(), this.getPos());
        if (Math.abs(this.getVelocity().x) > Math.abs(this.getVelocity().z)) {
            doXCollision(this.getVelocity(), this.getPos());
            doZCollision(this.getVelocity(), this.getPos());
            return;
        }
        doZCollision(this.getVelocity(), this.getPos());
        doXCollision(this.getVelocity(), this.getPos());
    }

    private void doZCollision(Vec3d velocity, Vec3d playerPos) {
        Vec3i playerBlockPos = Vec3i.ofFloored(playerPos);
        Vec3d min = this.getBoundingBox().getMinPos().add(Box.EPSILON);
        Vec3d max = this.getBoundingBox().getMaxPos().subtract(Box.EPSILON);
        Vec3i maxBlock = Vec3i.ofFloored(max);
        Vec3i minBlock = Vec3i.ofFloored(min);
        List<Vec3i> sideBlocks = new ArrayList<>();
        for (int k = minBlock.getX(); k <= maxBlock.getX(); k++) {
            for (int j = minBlock.getY(); j <= maxBlock.getY(); j++) {
                sideBlocks.add(new Vec3i(k, j, 0));
            }
        }

        double zVelocity = velocity.z;
        Vec3i endBlockPos = Vec3i.ofFloored(playerPos.add(0, 0, zVelocity));
        int delta = playerBlockPos.getZ() - endBlockPos.getZ();
        if (delta == 0) return;
        //do this for every block in the hit box X/Z plane
        Pair<Vec3i, Block> intersectingBlock = getFirstBlock(playerBlockPos.getZ(), endBlockPos.getZ(), delta, sideBlocks, BlockFace.NORTH);
        if (intersectingBlock != null) {
            Vec3i blockPos = intersectingBlock.getLeft();
            Block block = intersectingBlock.getRight();
            Box blockBox = block.getBoundingBox().offset(blockPos);
            this.setPos(this.getPos().setZ(delta > 0 ? blockBox.getMaxPos().z  + this.getBoundingBox().getLengthZ() : blockBox.getMinPos().z - this.getBoundingBox().getLengthZ() / 2d));
            this.setVelocity(this.getVelocity().setZ(0));
        }
    }
    private void doXCollision(Vec3d velocity, Vec3d playerPos) {
        Vec3i playerBlockPos = Vec3i.ofFloored(playerPos);
        Vec3d min = this.getBoundingBox().getMinPos().add(Box.EPSILON);
        Vec3d max = this.getBoundingBox().getMaxPos().subtract(Box.EPSILON);
        Vec3i maxBlock = Vec3i.ofFloored(max);
        Vec3i minBlock = Vec3i.ofFloored(min);
        List<Vec3i> sideBlocks = new ArrayList<>();
        for (int k = minBlock.getY(); k <= maxBlock.getY(); k++) {
            for (int j = minBlock.getZ(); j <= maxBlock.getZ(); j++) {
                sideBlocks.add(new Vec3i(0, k, j));
            }
        }

        double xVelocity = velocity.x;
        Vec3i endBlockPos = Vec3i.ofFloored(playerPos.add(xVelocity, 0, 0));
        int delta = playerBlockPos.getX() - endBlockPos.getX();
        if (delta == 0) return;
        //do this for every block in the hit box Y/Z plane
        Pair<Vec3i, Block> intersectingBlock = getFirstBlock(playerBlockPos.getX(), endBlockPos.getX(), delta, sideBlocks, BlockFace.EAST);
        if (intersectingBlock != null) {
            Vec3i blockPos = intersectingBlock.getLeft();
            Block block = intersectingBlock.getRight();
            Box blockBox = block.getBoundingBox().offset(blockPos);
            Vec3d newPos = this.getPos().setX(delta > 0 ? blockBox.getMaxPos().x  + this.getBoundingBox().getLengthX() / 2d : blockBox.getMinPos().x - this.getBoundingBox().getLengthX() / 2d);
            this.setPos(newPos);
            this.setVelocity(this.getVelocity().setX(0));
        }
    }

    private void doYCollision(Vec3d velocity, Vec3d playerPos) {
        Vec3i playerBlockPos = Vec3i.ofFloored(playerPos);
        Vec3d min = this.getBoundingBox().getMinPos().add(Box.EPSILON);
        Vec3d max = this.getBoundingBox().getMaxPos().subtract(Box.EPSILON);
        Vec3i maxBlock = Vec3i.ofFloored(max);
        Vec3i minBlock = Vec3i.ofFloored(min);
        List<Vec3i> sideBlocks = new ArrayList<>();
        for (int k = minBlock.getX(); k <= maxBlock.getX(); k++) {
            for (int j = minBlock.getZ(); j <= maxBlock.getZ(); j++) {
                sideBlocks.add(new Vec3i(k, 0, j));
            }
        }

        double yVelocity = velocity.y;
        Vec3i endBlockPos = Vec3i.ofFloored(playerPos.add(0, yVelocity, 0));
        int delta = playerBlockPos.getY() - endBlockPos.getY();
        if (delta == 0) return;
        //do this for every block in the hit box X/Z plane
        Pair<Vec3i, Block> intersectingYBlock = getFirstBlock(playerBlockPos.getY(), endBlockPos.getY(), delta, sideBlocks, BlockFace.DOWN);
        if (intersectingYBlock == null) {
            this.setOnGround(false);
            return;
        }
        Vec3i blockPos = intersectingYBlock.getLeft();
        Block block = intersectingYBlock.getRight();
        Box blockBox = block.getBoundingBox().offset(blockPos);
        this.setPos(this.getPos().setY(delta > 0 ? blockBox.getMaxPos().y : blockBox.getMinPos().y));

        this.setVelocity(this.getVelocity().setY(0));
        this.setOnGround(true);
    }

    public Pair<Vec3i, Block> getFirstBlock(int blockPosStart, int blockPosEnd, int delta, List<Vec3i> sideBlocks, BlockFace faceAxis) {
        if (delta < 0) blockPosEnd ++;
        else blockPosEnd --; //add one extra range to make sure bounding box is checked anyways
        for (int i = blockPosStart; delta > 0 ? i >= blockPosEnd : i <= blockPosEnd; i += (delta > 0 ? -1 : 1)) {
            for (Vec3i sideBlock : sideBlocks) {
                Vec3i blockPos = sideBlock.with(faceAxis, i);
                Block block = getInstance().getWorld().getBlock(blockPos);
                Box playerBox = this.calcBoundingBox(Vec3d.of(blockPos));
                Box blockBox = block.getBoundingBox().offset(blockPos);
                if (!playerBox.intersects(blockBox)) continue;
                if (block.hasNoCollision()) continue;
                return new Pair<>(blockPos, block);
            }
        }
        return null;
    }

    public boolean isPathfinding() {
        return this.getPathFinderExecutor() != null;
    }

    public void jump() {
        if (!this.isOnGround()) return;
        this.setVelocity(this.getVelocity().add(0 , 0.60, 0));
    }

    public void tickPathfinder() {
        if (pathfinderExecutor == null) return;
        if (pathfinderExecutor.isFinished()) {
            pathfinderExecutor = null;
            return;
        }
        pathfinderExecutor.doNextMovement();
    }

}
