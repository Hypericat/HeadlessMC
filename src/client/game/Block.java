package client.game;

public class Block {
    private final String name;
    private final String type;
    private final BlockProperties properties;
    private final BlockStates states;

    public Block(String name, String type, BlockProperties properties, BlockStates states) {
        this.name = name;
        this.type = type;
        this.properties = properties;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public BlockProperties getProperties() {
        return properties;
    }

    public BlockStates getStates() {
        return states;
    }
}
