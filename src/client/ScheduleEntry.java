package client;

import java.util.function.Consumer;

public class ScheduleEntry {
    private final int interval;
    private long lastTick;
    private final Scheduler.Type type;
    private final Consumer<?> consumer;
    private final long id;

    public ScheduleEntry(int interval, long lastTick, Scheduler.Type type, Consumer<?> consumer, long id) {
        this.interval = interval;
        this.lastTick = lastTick;
        this.type = type;
        this.consumer = consumer;
        this.id = id;
    }

    public long getNextTime() {
        return lastTick + interval;
    }

    public void updateLastTick(long lastTick) {
        this.lastTick = lastTick;
    }

    public int getInterval() {
        return interval;
    }

    public Scheduler.Type getType() {
        return type;
    }

    public Consumer<?> getConsumer() {
        return consumer;
    }

    public long getId() {
        return id;
    }
    public void execute() {
        consumer.accept(null);
    }
}
