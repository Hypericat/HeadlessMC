package commands;

import java.util.ArrayList;
import java.util.List;

public class MultipleArgument<T> extends Argument<List<T>> {
    /**
     *
     * @param size
     * amount of arguments to expect, negative values indicates until the end of the message
     */
    private int size;
    private Class<? extends Argument<T>> argumentClass;

    public MultipleArgument(Class<? extends Argument<T>> argumentClass, int size) {
        this.size = size;
        this.argumentClass = argumentClass;
    }

    @Override
    public List<T> parse(List<String> s) {
        List<T> arguments = new ArrayList<>();
        for (int i = 0; i != size; i++) {
            if (s.isEmpty() && size < 0) return arguments;
            if (s.isEmpty()) throw new IllegalArgumentException("Not enough arguments specified!");
            Argument<?> arg;
            try {
                arg = (Argument<T>) argumentClass.getDeclaredConstructors()[0].newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            arguments.add((T) arg.parse(s));
        }
        return arguments;
    }

}
