import auth.Account;
import auth.AuthUtil;
import auth.SessionHandler;
import client.HeadlessInstance;
import client.game.Block;
import client.commands.TerminalHandler;
import client.game.Blocks;
import client.game.items.ItemType;
import client.game.items.Items;
import client.pathing.movement.BlockBreakTickCache;
import math.Pair;
import ux.Option;
import ux.OptionTypes;
import ux.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients
    private static final List<Pair<HeadlessInstance, Thread>> instances = new ArrayList<>();
    private static TerminalHandler terminal;
    private static Account[] accounts;
    private static Options options;

    public static void main(String[] args) {
        HeadlessInstance.initDir();
        options = Options.newInstance();

        if ((boolean) options.get(OptionTypes.PREMIUM).getValue())
            accounts = SessionHandler.readAccounts();

        terminal = new TerminalHandler(options);
        Thread terminalThread = new Thread(terminal);

        makeFreeAccounts((String) options.get(OptionTypes.ACCOUNT_NAME).getValue(), (String) options.get(OptionTypes.SERVER_ADDRESS).getValue(), (int) options.get(OptionTypes.BOT_COUNT).getValue(), (int) options.get(OptionTypes.PORT).getValue());

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

    public static void fillPremiumAccounts(String ip, int port) {
        if (ip.equalsIgnoreCase("localhost")) ip = "127.0.0.1";
        if (accounts.length < 1) throw new IllegalArgumentException("No premium accounts provided!");
        for (Account account : accounts) {
            makeInstance(account, ip, port);
        }
    }

    public static void makeFreeAccounts(String name, String ip, int count, int port) {
        for (int i = 0; i < count; i++) {
            makeInstance(new Account(name + i), ip, port);
        }
    }
    public static boolean isDev() {
        return (boolean) options.get(OptionTypes.DEV).getValue();
    }

    public static void makeInstance(Account account, String ip, int port) {
        HeadlessInstance headless = new HeadlessInstance(account, ip, port, (int) options.get(OptionTypes.RENDER_DISTANCE).getValue(), (int) options.get(OptionTypes.PROTOCOL_ID).getValue(), isDev(), terminal);
        Thread thread = new Thread(headless);
        instances.add(new Pair<>(headless, thread));
        thread.start();
        terminal.addInstance(headless);
    }




    //Todo
    //refactor inbound handler, make uncompressed and compressed packet parsers / handlers
}