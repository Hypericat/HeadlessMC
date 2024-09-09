import Networking.NetworkHandler;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients

    public static void main(String[] args) {
        HeadlessInstance headless = new HeadlessInstance();
        headless.connect("127.0.0.1");
    }
}