package client;

import client.game.ClientPlayerEntity;
import client.game.Hand;
import client.game.InteractionManager;
import client.game.World;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.configuration.HandShakeC2SPacket;
import client.networking.packets.C2S.configuration.LoginStartC2SPacket;
import client.networking.packets.C2S.play.ChatCommandC2SPacket;
import client.utils.UUID;
import client.utils.Vec3i;

import java.util.Random;

public class HeadlessInstance implements Runnable {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;
    private final String ip;
    private final String userName;
    private long lastTickTime;
    public static final long MSPT = 50;

    private ClientPlayerEntity player;
    private World world;
    private InteractionManager interactionManager;
    private UUID uuid;
    private final Scheduler scheduler;


    public HeadlessInstance(String userName, String ip) {
        this.userName = userName;
        this.ip = ip;
        this.lastTickTime = System.currentTimeMillis();
        this.scheduler = new Scheduler();
        network = new NetworkHandler(this);
    }

    public boolean connect(String address) {
        return connect(address, 25565);
    }

    public boolean connect(String address, int port) {
        this.currentAddress = address;
        this.currentPort = port;
        return network.connect(address, port);
    }
    public void login(String userName) {
        network.sendPacket(new HandShakeC2SPacket(this.getCurrentAddress(), getCurrentPort(), 2));
        network.sendPacket(new LoginStartC2SPacket(userName));
    }

    public void config() {
        //network.sendPacket(new ServerboundPluginMessageC2SPacket());
        //network.sendPacket(new ClientInformationC2SPacket());
    }

    public void initWorld() {
        if (world != null) return;
        this.world = new World();
    }

    public void initPlayer(int playerEntityID) {
        if (this.player != null) {
            player.setEntityID(playerEntityID);
            return;
        }
        this.player = new ClientPlayerEntity(playerEntityID, this);
        this.interactionManager = new InteractionManager(player, world, network, this);
    }

    public void run() {
        System.out.println("Running!");

        if (!this.connect(ip)) return;

        network.setNetworkState(NetworkState.HANDSHAKE);
        login(userName);

        while (true) {
            sleep(MSPT - (System.currentTimeMillis() - lastTickTime));
            scheduler.onTick();
            if (player != null) {
                onTick();
            }
            lastTickTime = System.currentTimeMillis();
        }
    }

    public static void sleep(long time) {
        if (time <= 0) return;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkHandler getNetworkHandler() {
        return network;
    }

    public String getCurrentAddress() {
        return currentAddress.isEmpty() ? "127.0.0.1" : currentAddress;
    }

    public int getCurrentPort() {
        return currentPort == -1 ? 25565 : currentPort;
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    private int tickCount = 0;

    public void onTick() {
        tickCount ++;


        this.interactionManager.tick();
        this.player.onTick();
    }
    public Scheduler getScheduler() {
        return scheduler;
    }


}
