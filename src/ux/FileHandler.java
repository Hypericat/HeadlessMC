package ux;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private final File configFile;
    public FileHandler(File configFile) {
        this.configFile = configFile;
    }

    public void loadOptions(Options options) {
        if (!configFile.exists()) {
            saveOptions(options);
            return;
        }
        try {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()) {
                readOption(scanner.nextLine(), options);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOptions(Options options) {
        try {
            FileWriter fileWriter = new FileWriter(configFile, false);
            for (Option<?> option : options.getOptions()) {
                writeOption(fileWriter, option);
            }
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeOption(FileWriter writer, Option<?> option) throws IOException {
        writer.write(option.name() + " : " + option.getValue() + '\n');
    }

    private static void readOption(String line, Options options) {
        String[] splits = line.replaceAll("\n", "").replaceAll(" ", "").split(":");
        if (splits.length != 2) return;
        try {
            Option<?> option = options.get(splits[0]);
            option.parse(splits[1]);
        } catch (Exception e) {
            return;
        }
    }

}
