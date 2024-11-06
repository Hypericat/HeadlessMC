package client.game.items;

import client.game.items.component.Component;

public class ItemType {
    private final String identifier;
    private final Component component;

    protected ItemType(String identifier, Component component) {
        this.identifier = identifier;
        this.component = component;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Component getComponent() {
        return component;
    }

    public static ItemType copy(ItemType type) {
        return new ItemType(type.getIdentifier(), new Component(type.getComponent()));
    }
}
