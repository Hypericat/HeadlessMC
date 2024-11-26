package ux;

import auth.Account;
import client.HeadlessInstance;
import client.commands.TerminalHandler;
import math.Pair;

import java.util.ArrayList;
import java.util.List;

public class InstanceHandler {
    private static final List<Pair<HeadlessInstance, Thread>> instances = new ArrayList<>();
    public static Account[] accounts;
    public static Options options;
    public static TerminalHandler terminal;
    public static boolean running = false;

    public static void fillPremiumAccounts() {
        if (accounts == null) {
            System.err.println("No premium accounts found!");
            return;
        }

        String ip = (String) options.get(OptionTypes.SERVER_ADDRESS).getValue();
        int port = (int) options.get(OptionTypes.PORT).getValue();
        if (ip.equalsIgnoreCase("localhost")) ip = "127.0.0.1";
        if (accounts.length < 1) throw new IllegalArgumentException("No premium accounts provided!");
        for (Account account : accounts) {
            makeInstance(account, ip, port);
        }
    }

    public static void makeFreeAccounts() {
        String name = (String) options.get(OptionTypes.ACCOUNT_NAME).getValue();
        String ip = (String) options.get(OptionTypes.SERVER_ADDRESS).getValue();
        int port = (int) options.get(OptionTypes.PORT).getValue();
        int count = (int) options.get(OptionTypes.BOT_COUNT).getValue();

        for (int i = 0; i < count; i++) {
            makeInstance(new Account(name + i), ip, port);
        }
    }
    public static boolean isDev() {
        return (boolean) InstanceHandler.options.get(OptionTypes.DEV).getValue();
    }

    public static void makeInstance(Account account, String ip, int port) {
        HeadlessInstance headless = new HeadlessInstance(account, ip, port, (int) options.get(OptionTypes.RENDER_DISTANCE).getValue(), (int) options.get(OptionTypes.PROTOCOL_ID).getValue(), isDev(), terminal);
        Thread thread = new Thread(headless);
        instances.add(new Pair<>(headless, thread));
        thread.start();
        terminal.addInstance(headless);
    }

    public static void runMainThread() {
        running = true;
        InstanceHandler.terminal = new TerminalHandler(InstanceHandler.options);
        Thread terminalThread = new Thread(InstanceHandler.terminal);

        terminalThread.start();

        System.out.println("Welcome to Headless MC!");
        System.out.println("Use create to create new instances");
        System.out.println("Use options list see options");
        System.out.println("Type help to list commands!");

        loop : while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < instances.size(); i++) {
                Thread thread = instances.get(i).getRight();
                if (!thread.isAlive()) continue;
                instances.remove(i);
                i--;
            }
        }
        System.out.println("All instances terminated, terminating program!");
        instances.clear();
    }
}
