import auth.SessionHandler;
import client.HeadlessInstance;
import client.game.Block;
import client.commands.TerminalHandler;
import ux.InstanceHandler;
import ux.OptionTypes;
import ux.Options;

public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients

    public static void main(String[] args) {
        HeadlessInstance.initDir();
        InstanceHandler.options = Options.newInstance();

        if ((boolean) InstanceHandler.options.get(OptionTypes.PREMIUM).getValue())
            InstanceHandler.accounts = SessionHandler.readAccounts();

        InstanceHandler.terminal = new TerminalHandler(InstanceHandler.options);
        Thread terminalThread = new Thread(InstanceHandler.terminal);

        InstanceHandler.makeFreeAccounts();

        //don't remove this
        Block.initCollisions();

        terminalThread.start();

        InstanceHandler.runMainThread();
        return;
    }


    public static boolean isDev() {
        return (boolean) InstanceHandler.options.get(OptionTypes.DEV).getValue();
    }

    //Todo
    //refactor inbound handler, make uncompressed and compressed packet parsers / handlers
}