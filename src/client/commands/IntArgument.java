package client.commands;

import java.util.List;

public class IntArgument extends Argument<Integer> {
    @Override
    public Integer parse(List<String> s) {
        String in = s.removeFirst();
        return Integer.parseInt(in);
    }

    @Override
    public String toString() {
        return "<IntArgument>";
    }
}
