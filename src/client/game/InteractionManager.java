package client.game;

import client.HeadlessInstance;
import client.networking.NetworkHandler;
import client.networking.packets.C2S.play.*;
import client.networking.packets.S2C.play.PlayerChatMessageS2C;
import client.networking.packets.S2C.play.SynchronizePlayerPositionS2CPacket;
import client.pathing.*;
import client.pathing.goals.GoalMineBlock;
import client.pathing.goals.GoalXYZ;
import client.pathing.movement.BlockBreakTickCache;
import client.utils.Flag;
import client.utils.UUID;
import math.MathHelper;
import math.Pair;
import math.Vec3d;
import math.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InteractionManager {
    private ClientPlayerEntity player;
    private World world;
    private NetworkHandler networkHandler;
    private HeadlessInstance instance;
    private Vec3i currentBlockBreaking;
    private int blockBreakingProgress;
    private int selectedSlot;

    private static final double PATHFINDING_MAX_RESET_POSITION_DELTA = 8d;
    private static final double PATHFINDING_MAX_RESET_POSITION_DELTA_SQUARED = PATHFINDING_MAX_RESET_POSITION_DELTA * PATHFINDING_MAX_RESET_POSITION_DELTA;


    public InteractionManager(ClientPlayerEntity player, World world, NetworkHandler networkHandler, HeadlessInstance instance) {
        this.player = player;
        this.world = world;
        this.networkHandler = networkHandler;
        this.instance = instance;
    }
    public void tick() {
        if (isBreakingBlock()) blockBreakingProgress ++;
    }

    public void sendCommand(String command) {
        if (command.startsWith("/")) command = command.substring(1);
        networkHandler.sendPacket(new ChatCommandC2SPacket(command));
    }

    public void setBlockCommand(Vec3i pos, Block block) {
        sendCommand("/setblock " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " " + block.getName());
    }

    public void onSynchronizePlayerPositions(SynchronizePlayerPositionS2CPacket packet) {
        Flag flags = packet.getFlags();
        ClientPlayerEntity player = instance.getPlayer();
        Vec3d oldPos = player.getPos();
        //instance.getInteractionManager().sendChatMessage("Server resetting client position!");

        player.setX(flags.contains(0x01) ? player.getX() + packet.getX() : packet.getX());
        player.setY(flags.contains(0x02) ? player.getY() + packet.getY() : packet.getY());
        player.setZ(flags.contains(0x04) ? player.getZ() + packet.getZ() : packet.getZ());
        player.setYaw(flags.contains(0x08) ? player.getYaw() + packet.getYaw() : packet.getYaw());
        player.setPitch(flags.contains(0x10) ? player.getPitch() + packet.getPitch() : packet.getPitch());

        instance.getNetworkHandler().sendPacket(new ConfirmTeleportationC2SPacket(packet.getTeleportID()));


        if (instance.getPlayer().getPathFinderExecutor() != null && oldPos.squaredDistanceTo(player.getPos()) > PATHFINDING_MAX_RESET_POSITION_DELTA_SQUARED) {
            instance.getLogger().logUser("Cancelling Pathfinding because the server reset client position!");
            instance.getPlayer().setPathfinderExecutor(null);
        }
    }

    public void lookAt(Entity entity) {
        lookAt(entity.getBoundingBox().getCenter().add(entity.getPos()));
    }

    public void lookAt(Vec3i target) {
        lookAt(Vec3d.fromBlock(target));
    }

    public void lookAt(Vec3d target) {
        Vec3d vec3d = this.player.getHeadPos();
        //deltas
        double d = target.x - vec3d.x;
        double e = target.y - vec3d.y;
        double f = target.z - vec3d.z;
        //area distance
        double g = Math.sqrt(d * d + f * f);

        this.player.setPitch(MathHelper.wrapDegrees((float)(-(MathHelper.atan2(e, g) * 180.0F / (float)Math.PI))));
        this.player.setYaw(MathHelper.wrapDegrees((float)(MathHelper.atan2(f, d) * 180.0F / (float)Math.PI) - 90.0F));
    }

    public void pathTo(Vec3i vec) {
        instance.getPlayer().setPathfinderExecutor(new PathfinderExecutor(new GoalXYZ(vec), instance));
    }

    public void drawPathTo(Vec3i vec) {
        instance.getPlayer().setPathfinderExecutor(PathfinderExecutor.draw(new GoalXYZ(vec.getX(), vec.getY(), vec.getZ()), instance));
    }

    public void attackEntity(Entity entity) {
        attackEntity(entity.getEntityID());
        player.resetAttackTicks();
    }

    public void attackEntity(int entityID) {
        networkHandler.sendPacket(new InteractC2SPacket(entityID, InteractType.ATTACK));
        swingHand(Hand.MAIN);
    }

    public void startDestroyBlock(Vec3i blockPos) {
        networkHandler.sendPacket(PlayerActionC2SPacket.startDigging(blockPos, BlockFace.UP, 0));
        blockBreakingProgress = 0;
        currentBlockBreaking = blockPos;
    }

    public void cancelDestroyBlock(Vec3i blockPos) {
        networkHandler.sendPacket(PlayerActionC2SPacket.cancelDigging(blockPos, 0));
        currentBlockBreaking = null;
    }

    public void finishDestroyBlock(Vec3i blockPos) {
        networkHandler.sendPacket(PlayerActionC2SPacket.finishDigging(blockPos, BlockFace.UP, 0));
        currentBlockBreaking = null;
    }

    public boolean playerSilentlyMineBlock(Vec3i blockPos, int tickTime) {
        if (isBreakingBlock()) return false;
        startDestroyBlock(blockPos);
        instance.getScheduler().schedule(tickTime, o -> {
            finishDestroyBlock(blockPos);
        });
        return true;
    }

    public void pathfindMineForBlocks(List<Block> blockTypes) {
        if (blockTypes.isEmpty()) throw new IllegalArgumentException("Gave invalid blocks list");
        List<Vec3i> positions = new ArrayList<>();
        StringBuilder blockNames = new StringBuilder();
        for (Block block : blockTypes) {
            positions.addAll(instance.getWorld().findCachedBlock(block));
            blockNames.append(block.getNameNoPrefix()).append(", ");
        }
        instance.getLogger().logUser("Found " + positions.size() + " " + blockNames.substring(0, blockNames.length() - 2) + " blocks!");

        if (positions.isEmpty()) {
            instance.getLogger().logUser("No blocks found, canceling pathing!");
            return;
        }

        instance.getPlayer().setPathfinderExecutor(new PathfinderExecutor(new GoalMineBlock(blockTypes, instance.getWorld(), instance.getPlayer().getBlockPos()), instance));
    }

    public void pathfindMineForBlocks(Block ... blocks) {
        pathfindMineForBlocks(List.of(blocks));
    }

    public void onReceiveChatMessage(PlayerChatMessageS2C packet) {
        String chatMessage = packet.getMessage();
        instance.getLogger().logUser("Received chat message : " + chatMessage);

        //this will fuck up if more than one instance but idc

        if (chatMessage.startsWith("#")) {
            instance.getTerminal().parseCommand(chatMessage.substring(1));
            return;
        }

        //REMOVE THIS FOR RELEASE THIS IS BAD THIS SHOULD NOT BE HERE
        Entity sender = world.getEntityByUUID(packet.getSenderUUID());
        if (sender == null) return;
        if (chatMessage.equalsIgnoreCase("here")) {
            pathTo(Vec3i.ofFloored(sender.getPos()));
            return;
        }
        if (chatMessage.equalsIgnoreCase("draw")) {
            drawPathTo(Vec3i.ofFloored(sender.getPos()));
            return;
        }
    }

    public void cancelBlockBreaking() {
        this.currentBlockBreaking = null;
    }

    public boolean mineWithBestSlot(Vec3i pos) {
        if (this.isBreakingBlock()) return false;
        Pair<Integer, Integer> pair = BlockBreakTickCache.getBestSlotAndTickTimeUncached(this.player.getInventory(), this.world.getBlock(pos), false);
        this.setSelectedSlot(pair.getLeft());
        this.syncSelectedSlots();
        return this.playerMineBlock(pos, pair.getRight());
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public void syncSelectedSlots() {
        networkHandler.sendPacket(new SyncSelectedSlotC2SPacket(this.selectedSlot));
    }


    public boolean playerMineBlock(int x, int y, int z) {
        return playerMineBlock(new Vec3i(x, y, z));
    }

    public boolean playerMineBlock(Vec3i blockPos, int tickTime) {
        boolean result = playerSilentlyMineBlock(blockPos, tickTime);
        if (result) {
            startDestroyBlock(blockPos);
            swingHand(Hand.MAIN);
            lookAt(blockPos);
        }
        return result;
    }

    public boolean playerMineBlock(Vec3i blockPos) {
        return playerMineBlock(blockPos, 6);
    }

    public void sendChatMessage(String message) {
        networkHandler.sendPacket(new ChatMessageC2SPacket(message));
    }

    public void swingHand(Hand hand) {
        networkHandler.sendPacket(new SwingArmC2SPacket(hand));
    }
    public boolean isBreakingBlock() {
        return currentBlockBreaking != null;
    }
    public boolean isBreakingBlock(Vec3i blockPos) {
        return (isBreakingBlock() && currentBlockBreaking.equals(blockPos));
    }

    public int getBlockBreakingProgress() {
        return blockBreakingProgress;
    }

}
