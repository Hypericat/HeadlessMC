package client.game;

import math.Box;

public class EntityType<T extends Entity> {
    private final int type;
    private final Box boundingBox;
    private final String id;
    private final Class<T> clzz;

    public EntityType(int type, Box boundingBox, String id, Class<T> clzz) {
        this.type = type;
        this.boundingBox = boundingBox;
        this.id = id;
        this.clzz = clzz;
    }

    public int getType() {
        return type;
    }

    public Box getBoundingBox() {
        return boundingBox;
    }

    public double getHeight() {
        return boundingBox.getLengthY();
    }
    public double getWidth() {
        return boundingBox.getLengthX();
    }
    public double getDepth() {
        return boundingBox.getLengthZ();
    }

    public String getId() {
        return id;
    }

    public Class<?> getEntityClass() {
        return clzz;
    }
}
