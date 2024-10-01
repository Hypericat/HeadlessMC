package math;

public class Pair<V, K> {
    private final V left;
    private final K right;

    public Pair(V left, K right) {
        this.left = left;
        this.right = right;
    }

    public K getRight() {
        return right;
    }

    public V getLeft() {
        return left;
    }
}
