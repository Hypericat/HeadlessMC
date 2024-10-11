package commands;

import client.HeadlessInstance;

import java.util.List;

public interface Executor {
    public void execute(CommandSyntax syntax, List<Object> arguments, List<HeadlessInstance> instances);
}
