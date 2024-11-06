package client.game.items.component.components;

import client.game.items.component.IComponent;
import client.game.items.component.ToolRule;

import java.util.Arrays;
import java.util.List;

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

    public void addRule() {

    }

    @Override
    public IComponent copy() {
        List<ToolRule>
        return new Tool(rules);
    }

    @Override
    public int getTypeID() {
        return 22;
    }
}
