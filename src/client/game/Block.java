package client.game;

import math.Box;
import math.Vec3d;

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
    public Box getBoundingBox() {
        return new Box(Vec3d.ZERO, new Vec3d(1, 1, 1));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean wasLastSpace = true;
        for (char c : name.toLowerCase().replaceAll("_", " ").replaceAll("minecraft:", "").toCharArray()) {
            if (wasLastSpace) {
                c = String.valueOf(c).toUpperCase().charAt(0);
                wasLastSpace = false;
            }
            if (c == ' ') wasLastSpace = true;
            builder.append(c);
        }
        return builder.toString();
    }
}
