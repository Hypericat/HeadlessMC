package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Command {
    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    public List<String> getStarts() {
        return syntaxes.stream().map(CommandSyntax::getName).collect(Collectors.toList());
    }

    public boolean isInstance(String s) {
        return getStarts().stream().anyMatch(start -> start.equalsIgnoreCase(s.split(" ")[0]));
    }

    public void execute(CommandSyntax[] commandSyntaxes, String s) {
        for (CommandSyntax syntax : commandSyntaxes) {
            syntax.execute(s);
        }
    }

    protected List<CommandSyntax> getSyntaxes() {
        return syntaxes;
    }

    public CommandSyntax[] getSyntaxes(String s) {
        String[] array = s.split(" ");
        if (array.length < 1) return null;
        return (CommandSyntax[]) getSyntaxes().stream().filter(syntax -> syntax.getName().equalsIgnoreCase(array[0])).toArray();
    }

    public abstract Command initSyntax();

    public boolean execute(String s) {

    }

}
