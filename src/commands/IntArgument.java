package commands;

import java.util.List;

public class IntArgument extends Argument<Integer> {
    @Override
    public Integer parse(List<String> s) {
        String in = s.removeFirst();
        System.out.println("Parsing " + in + " to " + Integer.parseInt(in));
        return Integer.parseInt(in);
    }
}
