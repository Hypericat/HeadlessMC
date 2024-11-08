package client.commands.command;

import client.HeadlessInstance;
import client.commands.Command;
import client.commands.CommandSyntax;
import client.commands.IntArgument;
import math.Vec3i;

import java.util.List;

public class GotoCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("goto")
                .argument(new IntArgument())
                .argument(new IntArgument())
                .argument(new IntArgument())
                .executes((syntax, arguments, instances) -> {
                    execute(instances, (int) arguments.get(1), (int) arguments.get(2), (int) arguments.get(3));
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances, int x, int y, int z) {
        instances.forEach(instance -> {
            instance.getInteractionManager().pathTo(new Vec3i(x, y, z));
            instance.getLogger().logUser("Going to block at " + x + " " + y + " " + z);
        });
    }
}
