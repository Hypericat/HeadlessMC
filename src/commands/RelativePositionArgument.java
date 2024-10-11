package commands;

import java.util.List;

public class RelativePositionArgument extends Argument<Integer> {
    @Override
    public Integer parse(List<String> s) {
        String in = s.removeFirst();
        if (!in.startsWith("~")) throw new IllegalArgumentException();
        in = in.substring(1);
        if (in.isEmpty()) return 0;
        return Integer.parseInt(in);
    }
}
