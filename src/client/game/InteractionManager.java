package client.game;

import client.HeadlessInstance;
import client.networking.NetworkHandler;
import client.networking.packets.C2S.play.*;
import client.pathing.*;
import client.pathing.goals.GoalXYZ;
import client.pathing.goals.GoalXZ;
import client.utils.UUID;
import math.Vec3d;
import math.Vec3i;

public class InteractionManager {
    private ClientPlayerEntity player;
    private World world;
    private NetworkHandler networkHandler;
    private HeadlessInstance instance;
    private Vec3i currentBlockBreaking;
    private int blockBreakingProgress;

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

    public void lookAt(Entity entity) {
        lookAt(entity.getBoundingBox().getCenter().add(entity.getPos()));
    }

    public void lookAt(Vec3i pos) {
        lookAt(Vec3d.of(pos));
    }

    public void lookAt(Vec3d pos) {
        Vec3d delta = this.player.getHeadPos().subtract(pos);
        double radius = Math.sqrt(delta.x * delta.x + delta.y * delta.y + delta.z * delta.z);
        float yaw = (float) Math.floorMod((int) (-Math.atan2(delta.x, delta.z) / Math.PI * 180) + 180, 360);

        float pitch = (float) (-Math.asin(delta.y / radius) / Math.PI * 180);

        this.player.setYaw(yaw);
        this.player.setPitch(-pitch);
    }

    public void pathTo(Vec3i vec) {
        instance.getPlayer().setPathfinderExecutor(new PathfinderExecutor(new GoalXZ(vec.getX(), vec.getZ()), instance));
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
    public boolean playerSilentlyMineBlock(Vec3i blockPos) {
        if (isBreakingBlock()) return false;
        startDestroyBlock(blockPos);
        instance.getScheduler().schedule(6, o -> {
            finishDestroyBlock(blockPos);
        });
        return true;
    }

    public void onReceiveChatMessage(UUID senderUUID, String chatMessage) {
        instance.getLogger().logUser("Receieved chat message : " + chatMessage);
        if (chatMessage.startsWith("goto ")) {
            chatMessage = chatMessage.replace("goto ", "");
            String[] pos = chatMessage.split(" ");
            pathTo(new Vec3i(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2])));
            return;
        }

        if (chatMessage.startsWith("drawTo ")) {
            chatMessage = chatMessage.replace("drawTo ", "");
            String[] pos = chatMessage.split(" ");
            drawPathTo(new Vec3i(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2])));
            return;
        }

        Entity sender = world.getEntityByUUID(senderUUID);
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


    public boolean playerMineBlock(int x, int y, int z) {
        return playerMineBlock(new Vec3i(x, y, z));
    }

    public boolean playerMineBlock(Vec3i blockPos) {
        boolean result = playerSilentlyMineBlock(blockPos);
        if (result) {
            startDestroyBlock(blockPos);
            swingHand(Hand.MAIN);
            lookAt(blockPos);
        }
        return result;
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
