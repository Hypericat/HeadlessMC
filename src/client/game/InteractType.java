package client.game;

public enum InteractType {
    INTERACT(0),
    ATTACK(1),
    INTERACT_AT(2);

    private final int value;
    InteractType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
