package commands;

import client.HeadlessInstance;
import commands.command.GotoCommand;
import commands.command.MineBlockCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalHandler implements Runnable {
    private final List<HeadlessInstance> instances = new ArrayList<>();
    private final List<Command> commands;
    private final Scanner systemIn;

    public TerminalHandler() {
        systemIn = new Scanner(System.in);
        commands = new ArrayList<>();
        initCommands();
    }

    private void initCommands() {
        commands.add(new MineBlockCommand());
        commands.add(new GotoCommand());
    }

    private Command getCommand(String in) {
        return commands.stream().filter(command -> command.isInstance(in)).findFirst().orElse(null);
    }



    public void addInstance(HeadlessInstance instance) {
        instances.add(instance);
    }

    public void removeInstance(HeadlessInstance instance) {
        instances.remove(instance);
    }

    private List<HeadlessInstance> getInstances() {
        return instances;
    }

    private void pollInput(List<HeadlessInstance> instances) {
        String in = systemIn.nextLine();
        for (Command command : commands) {
            List<CommandSyntax> commandSyntaxes = command.getSyntaxes(in);
            if (commandSyntaxes.isEmpty()) continue;
            System.out.println(commandSyntaxes.get(0).getName());
            command.execute(commandSyntaxes, in, instances);
            return;
        }
        System.err.println("Error invalid command name : " + in);
    }

    @Override
    public void run() {
        while (instances.stream().anyMatch(instance -> instance.getNetworkHandler().isConnected())) {
            pollInput(instances);
        }
    }
}
