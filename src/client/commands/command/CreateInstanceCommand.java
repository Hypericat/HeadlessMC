package client.commands.command;

import client.HeadlessInstance;
import client.commands.*;
import client.game.Block;
import ux.InstanceHandler;

import java.util.List;
import java.util.Scanner;

public class CreateInstanceCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("create")
                .executes((syntax, arguments, instances) -> {
                    execute(instances);
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances) {
        Scanner scanner = new Scanner(System.in);
        String response;

        System.out.println("Use premium? (T/F) : ");
        response = scanner.nextLine();
        if (response.startsWith("t") || response.startsWith("T")) {
            System.out.println("Creating premium instances!");
            InstanceHandler.fillPremiumAccounts();
            return;
        }
        InstanceHandler.makeFreeAccounts();
    }
}
