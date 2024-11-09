package client.utils;

import java.util.HashMap;

public class Timer {
    private static HashMap<Integer, Long> times = new HashMap<>();
    private static int lastID = -1;

    public static int start() {
        lastID ++;
        times.put(lastID, System.currentTimeMillis());
        return lastID;
    }

    public static long end(int id) {
        int duration = (int) (System.currentTimeMillis() - times.getOrDefault(id, -1L));
        times.remove(id);
        System.out.println("Finished timer! Took " + duration + "ms");
        return duration;
    }
}
