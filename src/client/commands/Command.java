package client.commands;

import client.HeadlessInstance;
import client.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Command {
    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    public Command() {
        initSyntax();
    }

    public List<String> getStarts() {
        return syntaxes.stream().map(CommandSyntax::getName).collect(Collectors.toList());
    }

    public boolean isInstance(String s) {
        return getStarts().stream().anyMatch(start -> start.equalsIgnoreCase(s.split(" ")[0]));
    }

    public void execute(List<CommandSyntax> commandSyntaxes, String s, List<HeadlessInstance> instances) {
        for (CommandSyntax syntax : commandSyntaxes) {
            if (syntax.execute(s, instances)) return;
        }
        Logger.sysLog("Failed to find valid command syntax for command : " + s);
    }

    protected List<CommandSyntax> getSyntaxes() {
        return syntaxes;
    }

    public List<CommandSyntax> getSyntaxes(String s) {
        String[] array = s.split(" ");
        if (array.length < 1) return null;
        return getSyntaxes().stream().filter(syntax -> syntax.getName().equalsIgnoreCase(array[0])).toList();
    }

    public abstract void initSyntax();

}
