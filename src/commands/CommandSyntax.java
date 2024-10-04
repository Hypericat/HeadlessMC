package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandSyntax {
    private final String name;
    private final Executor executor;
    private final List<Argument<?>> arguments;

    private CommandSyntax(builder builder) {
        this.name = builder.name;
        this.executor = builder.executor;
        this.arguments = builder.arguments;
    }

    public Optional<List<Object>> tryParse(String s) {
        List<String> stringList = List.of(s.split(" "));
        List<Object> results = new ArrayList<>();
        for (Argument<?> argument : arguments) {
            results.add(argument.parse(stringList));
        }
        return Optional.of(results);
    }

    public String getName() {
        return name;
    }

    public boolean isInstance(String s) {
        return getName().equalsIgnoreCase(s.split(" ")[0]);
    }

    public void execute(String s) {
        Optional<List<Object>> args = tryParse(s);
        if (args.isEmpty()) return;
        executor.execute(this, args.get());
    }



    public static class builder {
        private final String name;
        private Executor executor;
        private final List<Argument<?>> arguments;

        private builder(String name) {
            this.name = name;
            this.arguments = new ArrayList<>();
            arguments.add(new StringArgument());
        }

        public static builder name(String commandName) {
            return new builder(commandName);
        }

        public builder executes(Executor executor) {
            this.executor = executor;
            return this;
        }

        public builder argument(Argument<?> argument) {
            arguments.add(argument);
            return this;
        }

        public CommandSyntax build() {
            return new CommandSyntax(this);
        }
    }
}
