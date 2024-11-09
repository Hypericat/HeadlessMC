package client.game.items;

import client.game.Block;

import java.util.Arrays;
import java.util.HashSet;

public class BlockSet {
    private final String tag;
    private final HashSet<Block> blocks;

    public BlockSet(String tag, Block ... blocks) {
        this.tag = tag;
        this.blocks = new HashSet<>();
        this.blocks.addAll(Arrays.asList(blocks));
    }

    public BlockSet(String tag) {
        this.tag = tag;
        this.blocks = new HashSet<>();
    }

    public boolean contains(Block block) {
        return blocks.contains(block);
    }

    public String getTag() {
        return this.tag;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tag).append(" : ");
        blocks.forEach(block -> s.append(block.getName()).append(", "));
        if (!blocks.isEmpty()) {
            s.delete(s.length() - 2, s.length() - 1);
        }
        return s.toString();
    }

}
