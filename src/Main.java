import client.HeadlessInstance;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients

    public static void main(String[] args) {
        HeadlessInstance headless = new HeadlessInstance();
        if (!headless.connect("127.0.0.1")) return;
        headless.run();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}