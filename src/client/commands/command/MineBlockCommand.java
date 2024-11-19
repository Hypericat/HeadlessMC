package client.commands.command;

import client.HeadlessInstance;
import client.game.Block;
import client.commands.*;

import java.util.List;

public class MineBlockCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("mine")
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
