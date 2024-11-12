package client;

import auth.Account;
import auth.SessionHandler;
import client.game.ClientPlayerEntity;
import client.game.InteractionManager;
import client.game.World;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.configuration.HandShakeC2SPacket;
import client.networking.packets.C2S.configuration.LoginStartC2SPacket;
import java.util.UUID;
import client.commands.TerminalHandler;

import java.io.File;

public class HeadlessInstance implements Runnable {

    public static final File HEADLESSMC_DIRECTORY = new File(System.getProperty("user.home") + "\\Documents\\HeadlessMC");
    public static final long MSPT = 50;


    private final NetworkHandler network;
    private final String serverAddress;
    private final int currentPort;
    private final Account account;
    private long lastTickTime;
    private final int protocolID;

    private final byte viewDistance;
    private ClientPlayerEntity player;
    private World world;
    private InteractionManager interactionManager;
    private final Scheduler scheduler;
    private final Logger logger;
    private final TerminalHandler terminal;
    private boolean dev;

    public static void initDir() {
        if (!HEADLESSMC_DIRECTORY.exists()) HEADLESSMC_DIRECTORY.mkdirs();
        SessionHandler.initSessionFolder();
    }


    public HeadlessInstance(Account account, String serverAddress, int port, int viewDistance, int protocolID, boolean dev, TerminalHandler terminalHandler) {
        this.account = account;
        this.logger = new Logger(account, dev);
        this.dev = dev;
        this.viewDistance = (byte) viewDistance;
        this.serverAddress = serverAddress;
        this.currentPort = port;
        this.lastTickTime = System.currentTimeMillis();
        this.scheduler = new Scheduler();
        this.protocolID = protocolID;
        this.network = new NetworkHandler(this);
        this.terminal = terminalHandler;
    }

    public boolean isDev() {
        return this.dev;
    }

    public TerminalHandler getTerminal() {
        return this.terminal;
    }

    public boolean connect(String address) {
        return connect(address, this.currentPort);
    }

    public boolean connect(String address, int port) {
        return network.connect(address, port);
    }
    public void login(String userName) {
        network.sendPacket(new HandShakeC2SPacket(this.getProtocolID(), this.getServerAddress(), getCurrentPort(), 2));
        network.setNetworkState(NetworkState.LOGIN);
        network.sendPacket(new LoginStartC2SPacket(userName));
    }

    public int getProtocolID() {
        return protocolID;
    }

    public Account getAccount() {
        return this.account;
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

        if (!this.connect(this.serverAddress)) return;
        network.setNetworkState(NetworkState.HANDSHAKE);
        login(account.getName());

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

    public String getServerAddress() {
        return serverAddress.isEmpty() ? "127.0.0.1" : serverAddress;
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
        this.account.setUuid(uuid);
    }

    public UUID getUuid() {
        return this.account.getUuid();
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
