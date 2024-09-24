import client.HeadlessInstance;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients

    public static void main(String[] args) {
        makeInstance("Winston", "127.0.0.1", 1);

        int counter = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter ++;
            System.out.println("Counter : "  + counter);
        }
    }

    public static void makeInstance(String name, String ip, int count) {
        for (int i = 0; i < count; i++) {
            makeInstance(name + i, ip);
        }
    }

    public static void makeInstance(String name, String ip) {
        HeadlessInstance headless = new HeadlessInstance(name, ip);
        Thread thread = new Thread(headless);
        thread.start();
    }

    //Todo

    //work with login compression
}