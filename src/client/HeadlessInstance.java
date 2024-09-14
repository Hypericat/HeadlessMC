package client;

import client.networking.NetworkHandler;
import client.networking.NetworkState;
import client.networking.packets.C2S.*;

import java.util.Random;

public class HeadlessInstance {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;
    private static Random random = new Random();

    public HeadlessInstance() {
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
        network.sendPacket(new LoginStartC2SPacket(userName
                + random.nextInt()
        ));
    }

    public void config() {
        //network.sendPacket(new ServerboundPluginMessageC2SPacket());
        //network.sendPacket(new ClientInformationC2SPacket());
    }

    public void run() {
        System.out.println("Running!");

        network.setNetworkState(NetworkState.HANDSHAKE);
        login("User");

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
