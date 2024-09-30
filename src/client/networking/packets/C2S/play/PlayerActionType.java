package client.networking.packets.C2S.play;

public enum PlayerActionType {
    START_DIGGING(0),
    CANCEL_DIGGING(1),
    FINISHED_DIGGING(2),
    DROP_ITEM_STACK(3),
    DROP_ITEM(4),
    SHOOT_ARROW(5),
    FINISH_EATING(5),
    SWAP_HANDS(6);

    private final int value;
    PlayerActionType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
