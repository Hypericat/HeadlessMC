package client.commands.command;

import client.HeadlessInstance;
import client.commands.Command;
import client.commands.CommandSyntax;
import client.commands.MultipleArgument;
import client.commands.StringArgument;
import client.game.Block;
import ux.Option;
import ux.Options;

import java.util.List;

public class OptionCommand extends Command {
    private final Options options;

    public OptionCommand(Options options) {
        this.options = options;
    }

    @Override
    public void initSyntax() {
        getSyntaxes().add(CommandSyntax.builder
                .name("options")
                .argument(new MultipleArgument<>(StringArgument.class, -1))
                .executes((syntax, arguments, instances) -> {
                    execute((List<String>) arguments.get(1));
                })
                .build());
    }


    public void execute(List<String> args) {
        if (!tryExecute(args)) {
            System.err.println("Invalid syntax for options command!");
        }
    }

    public boolean tryExecute(List<String> args) {
        if (args.isEmpty()) return false;
        String type = args.removeFirst();
        switch (type.toLowerCase()) {
            case "list" -> {
                list();
                return true;
            }
            case "get" -> {
                return get(args);
            }
            case "set" -> {
                return set(args);
            }
            case "reset" -> {
                options.initOptions().values().forEach(option -> options.get(option).setObjValue(option.getValue()));
                options.save();
                System.out.println("Successfully reset all option!");
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public void list() {
        for (Option<?> option : options.getOptions()) {
            System.out.println(option.name() + " : " + option.getValue());
        }
    }

    public boolean get(List<String> args) {
        if (args.isEmpty()) return false;
        Option<?> option = options.get(args.removeFirst());
        System.out.println("Option " + option.name() + " has value " + option.getValue());
        return true;
    }

    public boolean set(List<String> args) {
        if (args.size() < 2) return false;
        Option<?> option = options.get(args.removeFirst());
        StringBuilder builder = new StringBuilder();
        while (!args.isEmpty()) {
            if (!builder.isEmpty()) builder.append(" ");
            builder.append(args.removeFirst());
        }
        try {
            option.parse(builder.toString());
            System.out.println("Set option " + option.name() + " to " + option.getValue());
            options.save();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
