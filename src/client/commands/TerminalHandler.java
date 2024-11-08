package client.commands;

import client.HeadlessInstance;
import client.commands.command.GotoCommand;
import client.commands.command.MineBlockCommand;
import client.commands.command.SayCommand;
import client.commands.command.StopCommand;

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
        commands.add(new StopCommand());
        commands.add(new SayCommand());
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
        parseCommand(in);
    }

    public void parseCommand(String in) {
        for (Command command : commands) {
            List<CommandSyntax> commandSyntaxes = command.getSyntaxes(in);
            if (commandSyntaxes.isEmpty()) continue;
            System.out.println("Executing command : " + in);
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
