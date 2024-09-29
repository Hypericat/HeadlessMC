package client.game;

import client.HeadlessInstance;
import client.networking.NetworkHandler;
import client.networking.packets.C2S.play.ChatCommandC2SPacket;
import client.networking.packets.C2S.play.ChatMessageC2SPacket;
import client.networking.packets.C2S.play.SwingArmC2SPacket;
import client.utils.Vec3i;

public class InteractionManager {
    private ClientPlayerEntity player;
    private World world;
    private NetworkHandler networkHandler;
    private HeadlessInstance instance;

    public InteractionManager(ClientPlayerEntity player, World world, NetworkHandler networkHandler, HeadlessInstance instance) {
        this.player = player;
        this.world = world;
        this.networkHandler = networkHandler;
        this.instance = instance;
    }

    public void sendCommand(String command) {
        if (command.startsWith("/")) command = command.substring(1);
        networkHandler.sendPacket(new ChatCommandC2SPacket(command));
    }
    public void setBlockCommand(Vec3i pos, Block block) {
        sendCommand("/setblock " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " " + block.getName());
    }

    public void sendChatMessage(String message) {
        networkHandler.sendPacket(new ChatMessageC2SPacket(message));
    }

    public void swingHand(Hand hand) {
        networkHandler.sendPacket(new SwingArmC2SPacket(hand));
    }
}
