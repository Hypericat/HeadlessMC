package client.commands.command;

import client.HeadlessInstance;
import client.commands.Command;
import client.commands.CommandSyntax;
import client.commands.MultipleArgument;
import client.commands.StringArgument;

import java.util.List;

public class SayCommand extends Command {
    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("say")
                .argument(new MultipleArgument<>(StringArgument.class, -1))
                .executes((syntax, arguments, instances) -> {
                    execute(instances, (List<String>) arguments.get(1));
                })
                .build());
    }

    public void execute(List<HeadlessInstance> instances, List<String> text) {
        StringBuilder builder = new StringBuilder();
        text.forEach(t -> builder.append(t).append(" "));
        String str = builder.toString();
        instances.forEach(instance -> {
            instance.getInteractionManager().sendChatMessage(str);
        });
    }
}
