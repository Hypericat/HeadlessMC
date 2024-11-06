package commands.command;

import client.HeadlessInstance;
import client.game.Block;
import commands.*;
import math.Vec3i;

import java.util.List;

public class MineBlockCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("Mine")
                .argument(new MultipleArgument<>(BlockArgument.class, -1))
                .executes((syntax, arguments, instances) -> {
                    execute(instances, (List<Block>) arguments.get(1));
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances, List<Block> blocks) {
        instances.forEach(instance -> {
            instance.getInteractionManager().pathfindMineForBlocks(blocks);
        });
    }
}
