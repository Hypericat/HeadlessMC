package auth;

import client.HeadlessInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class SessionHandler {
    private SessionHandler() {

    }

    public static final File SESSION_DIRECTORY = new File(HeadlessInstance.HEADLESSMC_DIRECTORY + "\\Sessions");

    public static void initSessionFolder() {
        if (!SESSION_DIRECTORY.exists()) SESSION_DIRECTORY.mkdir();
    }

    public static Account[] readAccounts() {
        List<Account> accounts = new ArrayList<>();
        for (File f : SESSION_DIRECTORY.listFiles()) {
            if (f.isDirectory()) continue;


            String fileName = f.getName();
            Optional<String> playerName;
            if (fileName.contains("."))
                playerName = Optional.empty();
            else
                playerName = Optional.of(fileName);

            Scanner scanner;
            try {
            scanner = new Scanner(f);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            String data = scanner.nextLine();
            if (data.startsWith("token:")) data = data.substring(6);
            String[] tokens = data.split(":");
            if (tokens.length != 2) continue;
            String session = tokens[0];
            String uuid = tokens[1];
            Account account = new Account(playerName, session, UndashedUuid.fromString(uuid));
            System.out.println("Created new account : " + account.toStringCensored());
            accounts.add(account);

        }
        return accounts.toArray(new Account[accounts.size()]);
    }
}
