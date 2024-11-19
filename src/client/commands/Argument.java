package client.commands;

import java.util.List;

public abstract class Argument<T> {
    public abstract T parse(List<String> s);
    public abstract String toString();
}
