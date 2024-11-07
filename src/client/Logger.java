package client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private final String baseName;
    private final int id;
    private final boolean isDev;
    private final List<String> logBuffer;
    private final File logFile;
    private final int flushMinCount;

    public Logger(String baseName, int id, boolean dev) {
       this.baseName = baseName;
       this.logFile = new File("latest.log");
       if (logFile.exists()) logFile.delete();
       this.logBuffer = new ArrayList<>();
       this.id = id;
       this.isDev = dev;
       this.flushMinCount = isDev ? 1 : 10;
    }

    public static void sysLog(Object object) {
        System.out.println("[SYSLOG] " + object);
    }

    private void flushInternal() throws IOException {
        if (!logFile.exists()) logFile.createNewFile();
        FileWriter writer = new FileWriter(logFile, true);
        for (String str : logBuffer) {
            writer.write(str + '\n');
        }
        logBuffer.clear();
        writer.flush();
        writer.close();
    }

    public void flush() {
        try {
            flushInternal();
        } catch (IOException e) {
            System.err.println("Failed to write to log!");
        }
    }

    public Logger(String baseName) {
        this(baseName, -1, false);
    }

    private void consoleLog(String string) {
        System.out.println(string);
        logBuffer.add(string);
    }

    public void logUser(Object object) {
        this.consoleLog("[" + this.getName() + "] " +  object);
        checkLogBuffer();
    }

    public void debug(Object object) {
        if (!isDev) return;
        this.consoleLog("[DEBUG] " + object);
    }

    public void err(Object object) {
        System.err.println("[ERROR] " + object);
        logBuffer.add("[ERROR] " + object);
        checkLogBuffer();
    }

    public void logToFile(Object object) {
        logBuffer.add(String.valueOf(object));
        checkLogBuffer();
    }

    public void checkLogBuffer() {
        if (logBuffer.size() >= flushMinCount) flush();
    }

    public String getName() {
        return baseName + (id == -1 ? "" : id);
    }
}
