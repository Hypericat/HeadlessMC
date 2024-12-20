package client.commands;

import java.util.List;

public class StringArgument extends Argument<String> {
    @Override
    public String parse(List<String> s) {
        String in = s.removeFirst();
        return in;
    }

    public String toString() {
        return "<StringArgument>";
    }

}
