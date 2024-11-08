package client.commands.command;

import client.HeadlessInstance;
import client.commands.Command;
import client.commands.CommandSyntax;

import java.util.List;

public class StopCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("Stop")
                .executes((syntax, arguments, instances) -> {
                    execute(instances);
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances) {
        instances.forEach(instance -> {
            instance.getPlayer().setPathfinderExecutor(null);
        });
    }
}
