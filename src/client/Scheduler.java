package client;

import client.utils.Pair;

import java.util.HashMap;
import java.util.function.Consumer;

public class Scheduler {

    private long tickCounter;
    private long lastId;
    HashMap<Long, ScheduleEntry> schedules = new HashMap<>();

    public Scheduler() {
        tickCounter = 0;
        lastId = -1;
    }

    public void onTick() {
        tickCounter ++;
        for (ScheduleEntry entry : schedules.values()) {
            System.out.println("Entry time " + entry.getNextTime());
            System.out.println("tick counter " + tickCounter);
            if (entry.getNextTime() <= tickCounter) {
                entry.execute();
                if (entry.getType() == Type.ONCE) {
                    schedules.remove(entry.getId());
                    continue;
                }
                entry.updateLastTick(tickCounter);
            }
        }
    }

    public long schedule(int interval, Consumer<?> consumer) {
        return schedule(interval, Type.ONCE, consumer);
    }

    public long schedule(int interval, Type type, Consumer<?> consumer) {
        lastId++;
        schedules.put(lastId, new ScheduleEntry(interval, tickCounter, type, consumer, lastId));
        return lastId;
    }

    public void remove(long id) {
        schedules.remove(id);
    }

    public static enum Type {
        ONCE,
        REPEATING;
    }
}
