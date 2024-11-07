package client.game.items.component;

import java.util.ArrayList;
import java.util.List;

public class ToolRule {
    private final List<String> affectedBlocks;
    private final boolean correctForDrops;
    private final double speed;

    public ToolRule(List<String> affectedBlocks, boolean correctForDrops, double speed) {
        this.affectedBlocks = affectedBlocks;
        this.correctForDrops = correctForDrops;
        this.speed = speed;
    }
    public ToolRule(String affectedBlocks, boolean correctForDrops, double speed) {
        this.affectedBlocks = new ArrayList<>();
        this.affectedBlocks.add(affectedBlocks);
        this.correctForDrops = correctForDrops;
        this.speed = speed;
    }

    public ToolRule(ToolRule rule) {
        this.affectedBlocks = new ArrayList<>(rule.affectedBlocks);
        this.correctForDrops = rule.correctForDrops;
        this.speed = rule.speed;
    }

    public List<String> getAffectedBlocks() {
        return affectedBlocks;
    }

    public boolean isCorrectForDrops() {
        return correctForDrops;
    }

    public double getSpeed() {
        return speed;
    }
}
