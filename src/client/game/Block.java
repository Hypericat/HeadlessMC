package client.game;

import math.Box;
import math.Vec3d;

import java.util.HashMap;

public class Block {
    private final String name;
    private final String type;
    private final BlockProperties properties;
    private final BlockStates states;
    private final static HashMap<Block, Byte> noCollision = new HashMap<>();

    public Block(String name, String type, BlockProperties properties, BlockStates states) {
        this.name = name;
        this.type = type;
        this.properties = properties;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public BlockProperties getProperties() {
        return properties;
    }

    public BlockStates getStates() {
        return states;
    }

    public boolean isFluid() {
        return this == Blocks.WATER || this == Blocks.LAVA;
    }

    public Box getBoundingBox() {
        return new Box(Vec3d.ZERO, new Vec3d(1, 1, 1));
    }

    public boolean hasCollision() {
        return !hasNoCollision();
    }
    public boolean hasNoCollision() {
        return noCollision.containsKey(this);
    }

    public static void initCollisions() {
        noCollision.put(Blocks.AIR, (byte) 1);
        noCollision.put(Blocks.OAK_SAPLING, (byte) 1);
        noCollision.put(Blocks.SPRUCE_SAPLING, (byte) 1);
        noCollision.put(Blocks.BIRCH_SAPLING, (byte) 1);
        noCollision.put(Blocks.JUNGLE_SAPLING, (byte) 1);
        noCollision.put(Blocks.ACACIA_SAPLING, (byte) 1);
        noCollision.put(Blocks.CHERRY_SAPLING, (byte) 1);
        noCollision.put(Blocks.DARK_OAK_SAPLING, (byte) 1);
        noCollision.put(Blocks.MANGROVE_PROPAGULE, (byte) 1);
        noCollision.put(Blocks.POWERED_RAIL, (byte) 1);
        noCollision.put(Blocks.DETECTOR_RAIL, (byte) 1);
        noCollision.put(Blocks.SHORT_GRASS, (byte) 1);
        noCollision.put(Blocks.FERN, (byte) 1);
        noCollision.put(Blocks.DEAD_BUSH, (byte) 1);
        noCollision.put(Blocks.SEAGRASS, (byte) 1);
        noCollision.put(Blocks.DANDELION, (byte) 1);
        noCollision.put(Blocks.TORCHFLOWER, (byte) 1);
        noCollision.put(Blocks.POPPY, (byte) 1);
        noCollision.put(Blocks.BLUE_ORCHID, (byte) 1);
        noCollision.put(Blocks.ALLIUM, (byte) 1);
        noCollision.put(Blocks.AZURE_BLUET, (byte) 1);
        noCollision.put(Blocks.RED_TULIP, (byte) 1);
        noCollision.put(Blocks.ORANGE_TULIP, (byte) 1);
        noCollision.put(Blocks.WHITE_TULIP, (byte) 1);
        noCollision.put(Blocks.PINK_TULIP, (byte) 1);
        noCollision.put(Blocks.OXEYE_DAISY, (byte) 1);
        noCollision.put(Blocks.CORNFLOWER, (byte) 1);
        noCollision.put(Blocks.LILY_OF_THE_VALLEY, (byte) 1);
        noCollision.put(Blocks.BROWN_MUSHROOM, (byte) 1);
        noCollision.put(Blocks.RED_MUSHROOM, (byte) 1);
        noCollision.put(Blocks.TORCH, (byte) 1);
        noCollision.put(Blocks.REDSTONE_WIRE, (byte) 1);
        noCollision.put(Blocks.WHEAT, (byte) 1);
        noCollision.put(Blocks.OAK_SIGN, (byte) 1);
        noCollision.put(Blocks.SPRUCE_SIGN, (byte) 1);
        noCollision.put(Blocks.BIRCH_SIGN, (byte) 1);
        noCollision.put(Blocks.ACACIA_SIGN, (byte) 1);
        noCollision.put(Blocks.CHERRY_SIGN, (byte) 1);
        noCollision.put(Blocks.JUNGLE_SIGN, (byte) 1);
        noCollision.put(Blocks.DARK_OAK_SIGN, (byte) 1);
        noCollision.put(Blocks.MANGROVE_SIGN, (byte) 1);
        noCollision.put(Blocks.BAMBOO_SIGN, (byte) 1);
        noCollision.put(Blocks.RAIL, (byte) 1);
        noCollision.put(Blocks.OAK_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.SPRUCE_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.BIRCH_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.ACACIA_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.CHERRY_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.JUNGLE_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.DARK_OAK_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.CRIMSON_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.WARPED_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.MANGROVE_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.BAMBOO_HANGING_SIGN, (byte) 1);
        noCollision.put(Blocks.LEVER, (byte) 1);
        //noCollision.put(Blocks.STONE_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.OAK_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.SPRUCE_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.BIRCH_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.JUNGLE_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.ACACIA_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.CHERRY_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.DARK_OAK_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.MANGROVE_PRESSURE_PLATE, (byte) 1);
        //noCollision.put(Blocks.BAMBOO_PRESSURE_PLATE, (byte) 1);
        noCollision.put(Blocks.REDSTONE_TORCH, (byte) 1);
        noCollision.put(Blocks.STONE_BUTTON, (byte) 1);
        noCollision.put(Blocks.SUGAR_CANE, (byte) 1);
        noCollision.put(Blocks.SOUL_TORCH, (byte) 1);
        noCollision.put(Blocks.PUMPKIN_STEM, (byte) 1);
        noCollision.put(Blocks.ATTACHED_PUMPKIN_STEM, (byte) 1);
        noCollision.put(Blocks.MELON_STEM, (byte) 1);
        noCollision.put(Blocks.ATTACHED_MELON_STEM, (byte) 1);
        noCollision.put(Blocks.VINE, (byte) 1);
        noCollision.put(Blocks.GLOW_LICHEN, (byte) 1);
        noCollision.put(Blocks.NETHER_WART, (byte) 1);
        noCollision.put(Blocks.CARROTS, (byte) 1);
        noCollision.put(Blocks.POTATOES, (byte) 1);
        noCollision.put(Blocks.OAK_BUTTON, (byte) 1);
        noCollision.put(Blocks.SPRUCE_BUTTON, (byte) 1);
        noCollision.put(Blocks.BIRCH_BUTTON, (byte) 1);
        noCollision.put(Blocks.JUNGLE_BUTTON, (byte) 1);
        noCollision.put(Blocks.ACACIA_BUTTON, (byte) 1);
        noCollision.put(Blocks.CHERRY_BUTTON, (byte) 1);
        noCollision.put(Blocks.DARK_OAK_BUTTON, (byte) 1);
        noCollision.put(Blocks.MANGROVE_BUTTON, (byte) 1);
        noCollision.put(Blocks.BAMBOO_BUTTON, (byte) 1);
        noCollision.put(Blocks.ACTIVATOR_RAIL, (byte) 1);
        noCollision.put(Blocks.SUNFLOWER, (byte) 1);
        noCollision.put(Blocks.LILAC, (byte) 1);
        noCollision.put(Blocks.ROSE_BUSH, (byte) 1);
        noCollision.put(Blocks.PEONY, (byte) 1);
        noCollision.put(Blocks.TALL_GRASS, (byte) 1);
        noCollision.put(Blocks.LARGE_FERN, (byte) 1);
        noCollision.put(Blocks.WHITE_BANNER, (byte) 1);
        noCollision.put(Blocks.ORANGE_BANNER, (byte) 1);
        noCollision.put(Blocks.MAGENTA_BANNER, (byte) 1);
        noCollision.put(Blocks.LIGHT_BLUE_BANNER, (byte) 1);
        noCollision.put(Blocks.YELLOW_BANNER, (byte) 1);
        noCollision.put(Blocks.LIME_BANNER, (byte) 1);
        noCollision.put(Blocks.PINK_BANNER, (byte) 1);
        noCollision.put(Blocks.GRAY_BANNER, (byte) 1);
        noCollision.put(Blocks.LIGHT_GRAY_BANNER, (byte) 1);
        noCollision.put(Blocks.CYAN_BANNER, (byte) 1);
        noCollision.put(Blocks.PURPLE_BANNER, (byte) 1);
        noCollision.put(Blocks.BLUE_BANNER, (byte) 1);
        noCollision.put(Blocks.BROWN_BANNER, (byte) 1);
        noCollision.put(Blocks.GREEN_BANNER, (byte) 1);
        noCollision.put(Blocks.RED_BANNER, (byte) 1);
        noCollision.put(Blocks.BLACK_BANNER, (byte) 1);
        noCollision.put(Blocks.PITCHER_PLANT, (byte) 1);
        noCollision.put(Blocks.BEETROOTS, (byte) 1);
        noCollision.put(Blocks.STRUCTURE_VOID, (byte) 1);
        noCollision.put(Blocks.KELP, (byte) 1);
        noCollision.put(Blocks.DEAD_TUBE_CORAL, (byte) 1);
        noCollision.put(Blocks.DEAD_BRAIN_CORAL, (byte) 1);
        noCollision.put(Blocks.DEAD_BUBBLE_CORAL, (byte) 1);
        noCollision.put(Blocks.DEAD_FIRE_CORAL, (byte) 1);
        noCollision.put(Blocks.DEAD_HORN_CORAL, (byte) 1);
        noCollision.put(Blocks.TUBE_CORAL, (byte) 1);
        noCollision.put(Blocks.BRAIN_CORAL, (byte) 1);
        noCollision.put(Blocks.BUBBLE_CORAL, (byte) 1);
        noCollision.put(Blocks.FIRE_CORAL, (byte) 1);
        noCollision.put(Blocks.HORN_CORAL, (byte) 1);
        noCollision.put(Blocks.DEAD_TUBE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.DEAD_BRAIN_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.DEAD_BUBBLE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.DEAD_FIRE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.DEAD_HORN_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.TUBE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.BRAIN_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.BUBBLE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.FIRE_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.HORN_CORAL_FAN, (byte) 1);
        noCollision.put(Blocks.WARPED_FUNGUS, (byte) 1);
        noCollision.put(Blocks.WARPED_ROOTS, (byte) 1);
        noCollision.put(Blocks.NETHER_SPROUTS, (byte) 1);
        noCollision.put(Blocks.CRIMSON_FUNGUS, (byte) 1);
        noCollision.put(Blocks.WEEPING_VINES, (byte) 1);
        noCollision.put(Blocks.TWISTING_VINES, (byte) 1);
        noCollision.put(Blocks.CRIMSON_ROOTS, (byte) 1);
        noCollision.put(Blocks.CRIMSON_PRESSURE_PLATE, (byte) 1);
        noCollision.put(Blocks.WARPED_PRESSURE_PLATE, (byte) 1);
        noCollision.put(Blocks.CRIMSON_BUTTON, (byte) 1);
        noCollision.put(Blocks.WARPED_BUTTON, (byte) 1);
        noCollision.put(Blocks.CRIMSON_SIGN, (byte) 1);
        noCollision.put(Blocks.WARPED_SIGN, (byte) 1);
        //noCollision.put(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE, (byte) 1);
        noCollision.put(Blocks.POLISHED_BLACKSTONE_BUTTON, (byte) 1);
        noCollision.put(Blocks.SCULK_VEIN, (byte) 1);
        noCollision.put(Blocks.SPORE_BLOSSOM, (byte) 1);
        noCollision.put(Blocks.PINK_PETALS, (byte) 1);
        noCollision.put(Blocks.HANGING_ROOTS, (byte) 1);
        noCollision.put(Blocks.FROGSPAWN, (byte) 1);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean wasLastSpace = true;
        for (char c : name.toLowerCase().replaceAll("_", " ").replaceAll("minecraft:", "").toCharArray()) {
            if (wasLastSpace) {
                c = String.valueOf(c).toUpperCase().charAt(0);
                wasLastSpace = false;
            }
            if (c == ' ') wasLastSpace = true;
            builder.append(c);
        }
        return builder.toString();
    }
}
