package client.game.items;

import client.game.items.component.*;

import java.util.HashMap;

public class Items {
    private static final HashMap<String, Item> map = new HashMap<>();
    public static Item NETHERITE_PICKAXE = register(new Item("netherite_pickaxe", Component.getBuilder().setTool(new Tool(new ToolRule("minecraft:cobweb", true, 15))).build()));

    public static Item register(Item item) {
        map.put(item.getIdentifier(), item);
        return item;
    }

    public static Item fromIdentifier(String identifier) {
        return map.get(identifier);
    }
}
