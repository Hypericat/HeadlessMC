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

    public void run() throws InterruptedException {
        System.out.println("Running!");
        network.sendPacket(new HandShakeC2SPacket("192.168.2.226", 25565, 1));
        network.sendPacket(new StatusRequestC2SPacket());

        Thread.sleep(5000);
    }


}
