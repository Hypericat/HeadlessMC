import client.HeadlessInstance;
import client.game.Block;
import commands.TerminalHandler;
import math.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients
    public static final boolean dev = true;
    private static final List<Pair<HeadlessInstance, Thread>> instances = new ArrayList<>();
    private static TerminalHandler terminal;

    public static void main(String[] args) {
        terminal = new TerminalHandler();
        Thread terminalThread = new Thread(terminal);

        //makeInstance(args[0], args[1], Integer.parseInt(args[2]));
        makeInstances("Winston", "127.0.0.1", 10);
        //makeInstance("Winston", "192.168.2.226", 1);

        //don't remove this
        Block.initCollisions();

        terminalThread.start();


        loop : while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < instances.size(); i++) {
                Thread thread = instances.get(i).getRight();
                if (thread.isAlive()) continue loop;
                instances.remove(i);
                i--;
            }
            break;
        }
        System.out.println("All instances terminated, terminating program!");
        instances.clear();
        return;
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

        HeadlessInstance headless = new HeadlessInstance(name, ip, id, 5, isDev());
        Thread thread = new Thread(headless);
        instances.add(new Pair<>(headless, thread));
        thread.start();
        terminal.addInstance(headless);
    }




    //Todo

    //process unload chunk packets
    //if not done this could cause chunks to get desynced because we wont receieve block updates ^^

    //fix GoalXZ
}