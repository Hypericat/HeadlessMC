package commands;

import java.util.ArrayList;
import java.util.List;

public class MineBlockCommand extends Command {
    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    @Override
    public List<String> getStarts() {
        return syntaxes.stream().collect();
    }

    @Override
    public Command initSyntax() {
        syntaxes.add(CommandSyntax.builder
                .name("Mine")
                .argument(new IntArgument())
                .argument(new IntArgument())
                .argument(new IntArgument())
                .executes((syntax, arguments) -> {
                    execute((Integer) arguments.get(0), (Integer) arguments.get(1), (Integer) arguments.get(2));
                    })
                .build());
        return this;
    }

    public void execute(int x, int y, int z) {

    }

    @Override
    public void execute(String s) {

    }
}
