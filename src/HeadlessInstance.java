import networking.NetworkHandler;
import networking.packets.HandShakeC2SPacket;
import networking.packets.StatusRequestC2SPacket;

public class HeadlessInstance {

    private final NetworkHandler network;

    public HeadlessInstance() {
        network = new NetworkHandler();
    }

    public void connect(String address) {
        connect(address, 25565);
    }

    public void connect(String address, int port) {
        network.connect(address, port);
    }

    public void run() {
        System.out.println("Running!");
        network.sendPacket(new HandShakeC2SPacket("127.0.0.1", 25565, 1));
        network.sendPacket(new StatusRequestC2SPacket());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
