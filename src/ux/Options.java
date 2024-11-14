package ux;

import client.HeadlessInstance;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class Options {
    private final HashMap<Integer, Option<?>> options;
    private final FileHandler fileHandler;

    public static final File configFile = new File(HeadlessInstance.HEADLESSMC_DIRECTORY + "\\" + "config.cfg");

    public static Options newInstance() {
        return new Options();
    }

    private Options() {
        this.options = initOptions();
        this.fileHandler = new FileHandler(configFile);
        this.fileHandler.loadOptions(this);
    }

    public HashMap<Integer, Option<?>> initOptions() {
        HashMap<Integer, Option<?>> options = new HashMap<>();
        Arrays.stream(OptionTypes.values()).forEach(option -> addOption(option.get(), options));
        return options;
    }

    public void addOption(Option<?> option, HashMap<Integer, Option<?>> options) {
        options.put(option.hash(), option);
    }

    public Option<?>[] getOptions() {
        return options.values().toArray(new Option<?>[options.size()]);
    }

    public Option<?> get(String name) {
        return get(name.toLowerCase().hashCode());
    }

    public Option<?> get(Option<?> option) {
        return get(option.hash());
    }

    public Option<?> get(OptionTypes type) {
        return get(type.get());
    }

    public Option<?> get(int hash) {
        return options.get(hash);
    }

    public void save() {
        fileHandler.saveOptions(this);
    }
}
