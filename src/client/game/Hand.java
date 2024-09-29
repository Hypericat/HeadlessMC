package client.game;

public enum Hand {
    MAIN(0),
    OFFHAND(1);

    private int val;

    Hand(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }
}
