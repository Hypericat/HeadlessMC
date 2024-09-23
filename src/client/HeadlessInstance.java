package client;

import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.configuration.HandShakeC2SPacket;
import client.networking.packets.C2S.configuration.LoginStartC2SPacket;

import java.util.Random;

public class HeadlessInstance implements Runnable {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;
    private String ip;
    private static Random random = new Random();
    private String userName;

    public HeadlessInstance(String userName, String ip) {
        this.userName = userName;
        this.ip = ip;
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

    public void run() {
        System.out.println("Running!");

        if (!this.connect(ip)) return;

        network.setNetworkState(NetworkState.HANDSHAKE);
        login(userName);

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
}
