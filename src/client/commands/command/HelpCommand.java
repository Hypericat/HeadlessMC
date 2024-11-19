package client.commands.command;

import client.HeadlessInstance;
import client.commands.Command;
import client.commands.CommandSyntax;
import client.commands.TerminalHandler;

import java.util.List;

public class HelpCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("help")
                .executes((syntax, arguments, instances) -> {
                    execute(instances);
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances) {
        TerminalHandler.getTerminal().getCommands().forEach(HelpCommand::printUses);
    }

    public static void printUses(Command command) {
        command.getSyntaxes().forEach(commandSyntax -> {
            System.out.println(command.getStarts().getFirst() + " : " + commandSyntax.toString());
        });
    }}
