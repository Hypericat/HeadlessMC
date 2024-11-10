package client.game;

import java.util.Arrays;
import java.util.HashSet;

public class BlockSet implements BlockTypeList {
    private final String tag;
    private final HashSet<Block> blocks;

    public BlockSet(String tag, BlockTypeList ... blocks) {
        this.tag = tag;
        this.blocks = new HashSet<>();
        for (BlockTypeList block : blocks) {
            this.blocks.addAll(Arrays.asList(block.getBlocks()));
        }
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

    @Override
    public Block[] getBlocks() {
        return blocks.toArray(new Block[blocks.size()]);
    }
}
