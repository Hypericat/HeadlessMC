package client.game.items.component.components;

import client.game.Block;
import client.game.items.BlockSet;
import client.game.items.component.IComponent;
import client.game.items.component.ToolRule;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Tool implements IComponent {
    private final List<ToolRule> rules;
    private int damagePerBlock = 2;


    public Tool(ToolRule... rules) {
        this.rules = Arrays.stream(rules).toList();
    }

    public Tool(List<ToolRule> rules) {
        this.rules = rules;
    }

    public int getDamagePerBlock() {
        return damagePerBlock;
    }

    public Tool setDamagePerBlock(int damagePerBlock) {
        this.damagePerBlock = damagePerBlock;
        return this;
    }

    public List<ToolRule> getRules() {
        return rules;
    }

    public void addRule(ToolRule rule) {
        this.rules.add(rule);
    }

    @Override
    public IComponent copy() {
        List<ToolRule> newRules = new ArrayList<>();
        for (ToolRule rule : rules) {
            newRules.add(new ToolRule(rule));
        }
        return new Tool(newRules);
    }

    public boolean isTool() {
        return !rules.isEmpty();
    }

    public float getSpeedAgainstBlock(Block block) {
        for (ToolRule rule : rules) {
            if (rule.blockContains(block)) return (float) rule.getSpeed();
            if (rule.getBlockSets().stream().anyMatch(blockset -> blockset.contains(block))) {
                return (float) rule.getSpeed();
            }
        }
        return 1.0F;
    }

    @Override
    public int getTypeID() {
        return 22;
    }
}
