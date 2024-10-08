package commands;

public class MineBlockCommand extends Command {
    @Override
    public Command initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
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
}
