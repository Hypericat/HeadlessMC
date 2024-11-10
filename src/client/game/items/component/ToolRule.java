package client.game.items.component;

import client.game.Block;
import client.game.Blocks;
import client.game.BlockSet;
import client.game.BlockSets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ToolRule {
    private final List<BlockSet> set;
    private final HashSet<Block> blocks;
    private final boolean correctForDrops;
    private final double speed;

    public ToolRule(List<String> blocks, boolean correctForDrops, double speed) {
        this.set = new ArrayList<>(0);
        this.blocks = new HashSet<>(0);
        this.correctForDrops = correctForDrops;
        this.speed = speed;

        for (String s : blocks) {
            if (s.startsWith("#")) {
                this.set.add(BlockSets.getByTag(s));
                continue;
            }
            //could be better optimized by storing the string or a hash
            this.blocks.add(Blocks.getBlockByName(s));
        }
    }

    public ToolRule(ToolRule rule) {
        this.set = rule.set;
        this.blocks = rule.blocks;
        this.correctForDrops = rule.correctForDrops;
        this.speed = rule.speed;
    }

    public List<BlockSet> getBlockSets() {
        return set;
    }

    public boolean isCorrectForDrops() {
        return correctForDrops;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean blockContains(Block block) {
        return this.blocks.contains(block);
    }
}
