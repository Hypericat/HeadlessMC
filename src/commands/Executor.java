package commands;

import java.util.List;

public interface Executor {
    public void execute(CommandSyntax syntax, List<Object> arguments);
}
