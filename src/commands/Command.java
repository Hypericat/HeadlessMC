package commands;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    public boolean isInstance(String s) {
        return getStarts().stream().anyMatch(start -> start.equalsIgnoreCase(s.split(" ")[0]));
    }

    public abstract List<String> getStarts();

    public abstract Command initSyntax();

    public abstract void execute(String s);

}
