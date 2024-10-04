package commands;

import java.util.List;

public class IntArgument extends Argument<Integer> {
    @Override
    public Integer parse(List<String> s) {
        String in = s.getFirst();
        s.removeFirst();
        return Integer.parseInt(in);
    }

}
