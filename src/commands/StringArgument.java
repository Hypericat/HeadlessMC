package commands;

import java.util.List;

public class StringArgument extends Argument<String> {
    @Override
    public String parse(List<String> s) {
        String in = s.getFirst();
        s.removeFirst();
        return in;
    }

}
