import networking.NetworkHandler;
import networking.packets.C2S.HandShakeC2SPacket;
import networking.packets.C2S.StatusRequestC2SPacket;

public class HeadlessInstance {

    private final NetworkHandler network;
    private String currentAddress = "";
    private int currentPort = -1;

    public HeadlessInstance() {
        network = new NetworkHandler();
    }

    public boolean connect(String address) {
        return connect(address, 25565);
    }

    public boolean connect(String address, int port) {
        this.currentAddress = address;
        this.currentPort = port;
        return network.connect(address, port);
    }

    public void run() {
        System.out.println("Running!");
        network.sendPacket(new HandShakeC2SPacket(this.getCurrentAddress(), getCurrentPort(), 1));
        network.sendPacket(new StatusRequestC2SPacket());

        try {
            Thread.sleep(5000);
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
}
