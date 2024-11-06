package client;

import client.game.ClientPlayerEntity;
import client.game.InteractionManager;
import client.game.World;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.configuration.HandShakeC2SPacket;
import client.networking.packets.C2S.configuration.LoginStartC2SPacket;
import client.utils.UUID;
import commands.TerminalHandler;

public class HeadlessInstance implements Runnable {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;
    private final String ip;
    private final String userName;
    private long lastTickTime;
    public static final long MSPT = 50;

    private final byte viewDistance;
    private ClientPlayerEntity player;
    private World world;
    private InteractionManager interactionManager;
    private UUID uuid;
    private final Scheduler scheduler;
    private final Logger logger;
    private final TerminalHandler terminal;


    public HeadlessInstance(String userName, String ip, int id, int viewDistance, boolean dev, TerminalHandler terminalHandler) {
        this.userName = userName + id;
        this.logger = new Logger(userName, id, dev);
        this.viewDistance = (byte) viewDistance;
        this.ip = ip;
        this.lastTickTime = System.currentTimeMillis();
        this.scheduler = new Scheduler();
        this.network = new NetworkHandler(this);
        this.terminal = terminalHandler;
    }

    public TerminalHandler getTerminal() {
        return this.terminal;
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
        network.setNetworkState(NetworkState.LOGIN);
        network.sendPacket(new LoginStartC2SPacket(userName));
    }

    public void config() {
        //network.sendPacket(new ServerboundPluginMessageC2SPacket());
        //network.sendPacket(new ClientInformationC2SPacket());
    }

    public void initWorld() {
        if (world != null) return;
        this.world = new World(this);
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
        getLogger().logUser("Starting instance!");

        if (!this.connect(ip)) return;
        network.setNetworkState(NetworkState.HANDSHAKE);
        login(userName);

        while (network.isConnected()) {
            sleep(MSPT - (System.currentTimeMillis() - lastTickTime));
            scheduler.onTick();
            if (player != null) {
                onTick();
            }
            lastTickTime = System.currentTimeMillis();
        }
        logger.logUser("Terminating Instance!");
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

    public byte getViewDistance() {
        return viewDistance;
    }

    private int tickCount = 0;

    public void onTick() {
        if (interactionManager == null || player == null) return;
        tickCount ++;


        this.interactionManager.tick();
        this.player.onTick();
    }
    public Scheduler getScheduler() {
        return scheduler;
    }

    public Logger getLogger() {
        return this.logger;
    }


}
