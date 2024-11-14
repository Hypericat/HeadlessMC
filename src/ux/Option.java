package ux;

public class Option<T> {
    private T value;
    private final Class<?> clazz;
    private final String name;
    private final int nameHash;

    protected Option(String name, T value) {
        this.value = value;
        this.name = name;
        this.clazz = value.getClass();
        this.nameHash = name.toLowerCase().hashCode();
    }

    protected Option(Option<T> option) {
        this.value = option.value;
        this.clazz = option.clazz;
        this.name = option.name;
        this.nameHash = option.nameHash;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setObjValue(Object value) {
        this.value = (T) value;
    }

    @Override
    public boolean equals(Object o) {
        // it is possible for o to not be of type option but we don't care, we need speed!
        return ((Option<?>) o).hash() == hash();
    }

    public int hash() {
        return nameHash;
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return this.name + " : " + value;
    }

    public void parse(String s) {
        if (clazz.isAssignableFrom(String.class)) {
            value = (T) s;
            return;
        }
        if (clazz.isAssignableFrom(Long.class)) {
            value = (T) Long.valueOf(Long.parseLong(s));
            return;
        }
        if (clazz.isAssignableFrom(Boolean.class)) {
            value = (T) Boolean.valueOf(Boolean.parseBoolean(s));
            return;
        }
        if (clazz.isAssignableFrom(Integer.class)) {
            value = (T) Integer.valueOf(Integer.parseInt(s));
            return;
        }
        if (clazz.isAssignableFrom(Byte.class)) {
            value = (T) Byte.valueOf(Byte.parseByte(s));
            return;
        }
        if (clazz.isAssignableFrom(Short.class)) {
            value = (T) Short.valueOf(Short.parseShort(s));
            return;
        }
    }

}
