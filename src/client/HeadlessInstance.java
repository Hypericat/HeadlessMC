package client;

import client.game.ClientPlayerEntity;
import client.game.World;
import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.configuration.HandShakeC2SPacket;
import client.networking.packets.C2S.configuration.LoginStartC2SPacket;
import client.networking.packets.C2S.play.ChatCommandC2SPacket;
import client.utils.Vec3i;

import java.util.Random;

public class HeadlessInstance implements Runnable {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;
    private String ip;
    private static Random random = new Random();
    private String userName;
    private long lastTickTime;
    public static final long mspt = 50;

    private ClientPlayerEntity player;
    private World world;


    public HeadlessInstance(String userName, String ip) {
        this.userName = userName;
        this.ip = ip;
        this.lastTickTime = System.currentTimeMillis();
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

    public void initPlayer() {
         this.player = new ClientPlayerEntity(this);
         this.world = new World();
    }

    public void run() {
        System.out.println("Running!");

        if (!this.connect(ip)) return;

        network.setNetworkState(NetworkState.HANDSHAKE);
        login(userName);

        while (true) {
            sleep(50 - (System.currentTimeMillis() - lastTickTime));
            onTick();
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

    private int tickCount = 0;

    public void onTick() {
        tickCount ++;
        if (tickCount >= 50) {
            Vec3i pos = this.player.getPos().toVec3i().subtract(new Vec3i(0 ,1, 0));
            //System.out.println("Block at " + pos.toString() + " : " + world.getBlock(pos));
            if (tickCount % 20 == 0) {
                //network.sendPacket(new ChatCommandC2SPacket("setblock " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " bedrock"));
                network.sendPacket(new ChatCommandC2SPacket("say blocktype : " + world.getBlock(pos)));
            }
        }
        if (this.player != null) {
            this.player.onTick();
        }
    }


}
