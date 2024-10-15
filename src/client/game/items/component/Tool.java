package client.game.items.component;

public class Tool {
    private final ToolRule[] rules;

    public Tool(ToolRule... rules) {
        this.rules = rules;
    }
    public ToolRule[] getRules() {
        return rules;
    }
}
