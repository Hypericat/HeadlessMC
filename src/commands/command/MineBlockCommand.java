package commands.command;

import client.HeadlessInstance;
import commands.Command;
import commands.CommandSyntax;
import commands.IntArgument;
import commands.RelativePositionArgument;
import math.Vec3i;

import java.util.List;

public class MineBlockCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("Mine")
                .argument(new IntArgument())
                .argument(new IntArgument())
                .argument(new IntArgument())
                .executes((syntax, arguments, instances) -> {
                    execute(instances, (int) arguments.get(1), (int) arguments.get(2), (int) arguments.get(3));
                })
                .build());
        getSyntaxes().add(CommandSyntax.builder
                .name("Mine")
                .argument(new RelativePositionArgument())
                .argument(new RelativePositionArgument())
                .argument(new RelativePositionArgument())
                .executes((syntax, arguments, instances) -> {
                    executeRelative(instances, (int) arguments.get(1), (int) arguments.get(2), (int) arguments.get(3));
                })
                .build());
    }

    public void executeRelative(List<HeadlessInstance> instances, int x, int y, int z) {
        Vec3i delta = new Vec3i(x, y, z);
        instances.forEach(instance -> {
            try {
                instance.getInteractionManager().playerMineBlock(instance.getPlayer().getBlockPos().add(delta));
                instance.getLogger().logUser("Mining block at " + instance.getPlayer().getBlockPos().add(delta));
            } catch (NullPointerException e) {

            }
        });
    }

    public void execute(List<HeadlessInstance> instances, int x, int y, int z) {
        instances.forEach(instance -> {
            try {
                instance.getInteractionManager().playerMineBlock(x, y, z);
                instance.getLogger().logUser("Mining block at " + x + " " + y + " " + z);
            } catch (NullPointerException e) {

            }
        });
    }
}
