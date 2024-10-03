import client.HeadlessInstance;

import java.util.ArrayList;
import java.util.List;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients
    public static final boolean dev = true;
    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        //makeInstance(args[0], args[1], Integer.parseInt(args[2]));
        makeInstances("Winsto", "127.0.0.1", 20);
        //makeInstance("Winston", "192.168.2.226", 1);


        loop : while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Thread thread : threads) {
                if (thread.isAlive()) continue loop;;
            }
            break;
        }
        System.out.println("All instances terminated, terminating program!");
    }

    public static void makeInstances(String name, String ip, int count) {
        for (int i = 0; i < count; i++) {
            makeInstance(name, ip, i);
        }
    }
    public static boolean isDev() {
        return dev;
    }

    public static void makeInstance(String name, String ip, int id) {
        if (ip.equalsIgnoreCase("localhost")) ip = "127.0.0.1";

        HeadlessInstance headless = new HeadlessInstance(name, ip, id, isDev());
        Thread thread = new Thread(headless);
        threads.add(thread);
        thread.start();
    }




    //Todo

    //work with login compression
}