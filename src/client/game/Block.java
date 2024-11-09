package client.game;

import math.Box;
import math.Vec3d;

import java.util.HashMap;
import java.util.HashSet;

public class Block {
    private final String name;
    private final String type;
    private final BlockProperties properties;
    private final BlockStates states;
    private final static HashSet<Block> noCollision = new HashSet<>();

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
        return noCollision.contains(this);
    }

    public static void initCollisions() {
        noCollision.add(Blocks.WATER); // For now put water

        noCollision.add(Blocks.AIR);
        noCollision.add(Blocks.OAK_SAPLING);
        noCollision.add(Blocks.SPRUCE_SAPLING);
        noCollision.add(Blocks.BIRCH_SAPLING);
        noCollision.add(Blocks.JUNGLE_SAPLING);
        noCollision.add(Blocks.ACACIA_SAPLING);
        noCollision.add(Blocks.CHERRY_SAPLING);
        noCollision.add(Blocks.DARK_OAK_SAPLING);
        noCollision.add(Blocks.MANGROVE_PROPAGULE);
        noCollision.add(Blocks.POWERED_RAIL);
        noCollision.add(Blocks.DETECTOR_RAIL);
        noCollision.add(Blocks.SHORT_GRASS);
        noCollision.add(Blocks.FERN);
        noCollision.add(Blocks.DEAD_BUSH);
        noCollision.add(Blocks.SEAGRASS);
        noCollision.add(Blocks.DANDELION);
        noCollision.add(Blocks.TORCHFLOWER);
        noCollision.add(Blocks.POPPY);
        noCollision.add(Blocks.BLUE_ORCHID);
        noCollision.add(Blocks.ALLIUM);
        noCollision.add(Blocks.AZURE_BLUET);
        noCollision.add(Blocks.RED_TULIP);
        noCollision.add(Blocks.ORANGE_TULIP);
        noCollision.add(Blocks.WHITE_TULIP);
        noCollision.add(Blocks.PINK_TULIP);
        noCollision.add(Blocks.OXEYE_DAISY);
        noCollision.add(Blocks.CORNFLOWER);
        noCollision.add(Blocks.LILY_OF_THE_VALLEY);
        noCollision.add(Blocks.BROWN_MUSHROOM);
        noCollision.add(Blocks.RED_MUSHROOM);
        noCollision.add(Blocks.TORCH);
        noCollision.add(Blocks.REDSTONE_WIRE);
        noCollision.add(Blocks.WHEAT);
        noCollision.add(Blocks.OAK_SIGN);
        noCollision.add(Blocks.SPRUCE_SIGN);
        noCollision.add(Blocks.BIRCH_SIGN);
        noCollision.add(Blocks.ACACIA_SIGN);
        noCollision.add(Blocks.CHERRY_SIGN);
        noCollision.add(Blocks.JUNGLE_SIGN);
        noCollision.add(Blocks.DARK_OAK_SIGN);
        noCollision.add(Blocks.MANGROVE_SIGN);
        noCollision.add(Blocks.BAMBOO_SIGN);
        noCollision.add(Blocks.RAIL);
        noCollision.add(Blocks.OAK_HANGING_SIGN);
        noCollision.add(Blocks.SPRUCE_HANGING_SIGN);
        noCollision.add(Blocks.BIRCH_HANGING_SIGN);
        noCollision.add(Blocks.ACACIA_HANGING_SIGN);
        noCollision.add(Blocks.CHERRY_HANGING_SIGN);
        noCollision.add(Blocks.JUNGLE_HANGING_SIGN);
        noCollision.add(Blocks.DARK_OAK_HANGING_SIGN);
        noCollision.add(Blocks.CRIMSON_HANGING_SIGN);
        noCollision.add(Blocks.WARPED_HANGING_SIGN);
        noCollision.add(Blocks.MANGROVE_HANGING_SIGN);
        noCollision.add(Blocks.BAMBOO_HANGING_SIGN);
        noCollision.add(Blocks.LEVER);
        //noCollision.add()(Blocks.STONE_PRESSURE_PLATE);
        //noCollision.add()(Blocks.OAK_PRESSURE_PLATE);
        //noCollision.add()(Blocks.SPRUCE_PRESSURE_PLATE);
        //noCollision.add()(Blocks.BIRCH_PRESSURE_PLATE);
        //noCollision.add()(Blocks.JUNGLE_PRESSURE_PLATE);
        //noCollision.add()(Blocks.ACACIA_PRESSURE_PLATE);
        //noCollision.add()(Blocks.CHERRY_PRESSURE_PLATE);
        //noCollision.add()(Blocks.DARK_OAK_PRESSURE_PLATE);
        //noCollision.add()(Blocks.MANGROVE_PRESSURE_PLATE);
        //noCollision.add()(Blocks.BAMBOO_PRESSURE_PLATE);
        noCollision.add(Blocks.REDSTONE_TORCH);
        noCollision.add(Blocks.STONE_BUTTON);
        noCollision.add(Blocks.SUGAR_CANE);
        noCollision.add(Blocks.SOUL_TORCH);
        noCollision.add(Blocks.PUMPKIN_STEM);
        noCollision.add(Blocks.ATTACHED_PUMPKIN_STEM);
        noCollision.add(Blocks.MELON_STEM);
        noCollision.add(Blocks.ATTACHED_MELON_STEM);
        noCollision.add(Blocks.VINE);
        noCollision.add(Blocks.GLOW_LICHEN);
        noCollision.add(Blocks.NETHER_WART);
        noCollision.add(Blocks.CARROTS);
        noCollision.add(Blocks.POTATOES);
        noCollision.add(Blocks.OAK_BUTTON);
        noCollision.add(Blocks.SPRUCE_BUTTON);
        noCollision.add(Blocks.BIRCH_BUTTON);
        noCollision.add(Blocks.JUNGLE_BUTTON);
        noCollision.add(Blocks.ACACIA_BUTTON);
        noCollision.add(Blocks.CHERRY_BUTTON);
        noCollision.add(Blocks.DARK_OAK_BUTTON);
        noCollision.add(Blocks.MANGROVE_BUTTON);
        noCollision.add(Blocks.BAMBOO_BUTTON);
        noCollision.add(Blocks.ACTIVATOR_RAIL);
        noCollision.add(Blocks.SUNFLOWER);
        noCollision.add(Blocks.LILAC);
        noCollision.add(Blocks.ROSE_BUSH);
        noCollision.add(Blocks.PEONY);
        noCollision.add(Blocks.TALL_GRASS);
        noCollision.add(Blocks.LARGE_FERN);
        noCollision.add(Blocks.WHITE_BANNER);
        noCollision.add(Blocks.ORANGE_BANNER);
        noCollision.add(Blocks.MAGENTA_BANNER);
        noCollision.add(Blocks.LIGHT_BLUE_BANNER);
        noCollision.add(Blocks.YELLOW_BANNER);
        noCollision.add(Blocks.LIME_BANNER);
        noCollision.add(Blocks.PINK_BANNER);
        noCollision.add(Blocks.GRAY_BANNER);
        noCollision.add(Blocks.LIGHT_GRAY_BANNER);
        noCollision.add(Blocks.CYAN_BANNER);
        noCollision.add(Blocks.PURPLE_BANNER);
        noCollision.add(Blocks.BLUE_BANNER);
        noCollision.add(Blocks.BROWN_BANNER);
        noCollision.add(Blocks.GREEN_BANNER);
        noCollision.add(Blocks.RED_BANNER);
        noCollision.add(Blocks.BLACK_BANNER);
        noCollision.add(Blocks.PITCHER_PLANT);
        noCollision.add(Blocks.BEETROOTS);
        noCollision.add(Blocks.STRUCTURE_VOID);
        noCollision.add(Blocks.KELP);
        noCollision.add(Blocks.DEAD_TUBE_CORAL);
        noCollision.add(Blocks.DEAD_BRAIN_CORAL);
        noCollision.add(Blocks.DEAD_BUBBLE_CORAL);
        noCollision.add(Blocks.DEAD_FIRE_CORAL);
        noCollision.add(Blocks.DEAD_HORN_CORAL);
        noCollision.add(Blocks.TUBE_CORAL);
        noCollision.add(Blocks.BRAIN_CORAL);
        noCollision.add(Blocks.BUBBLE_CORAL);
        noCollision.add(Blocks.FIRE_CORAL);
        noCollision.add(Blocks.HORN_CORAL);
        noCollision.add(Blocks.DEAD_TUBE_CORAL_FAN);
        noCollision.add(Blocks.DEAD_BRAIN_CORAL_FAN);
        noCollision.add(Blocks.DEAD_BUBBLE_CORAL_FAN);
        noCollision.add(Blocks.DEAD_FIRE_CORAL_FAN);
        noCollision.add(Blocks.DEAD_HORN_CORAL_FAN);
        noCollision.add(Blocks.TUBE_CORAL_FAN);
        noCollision.add(Blocks.BRAIN_CORAL_FAN);
        noCollision.add(Blocks.BUBBLE_CORAL_FAN);
        noCollision.add(Blocks.FIRE_CORAL_FAN);
        noCollision.add(Blocks.HORN_CORAL_FAN);
        noCollision.add(Blocks.WARPED_FUNGUS);
        noCollision.add(Blocks.WARPED_ROOTS);
        noCollision.add(Blocks.NETHER_SPROUTS);
        noCollision.add(Blocks.CRIMSON_FUNGUS);
        noCollision.add(Blocks.WEEPING_VINES);
        noCollision.add(Blocks.TWISTING_VINES);
        noCollision.add(Blocks.CRIMSON_ROOTS);
        noCollision.add(Blocks.CRIMSON_PRESSURE_PLATE);
        noCollision.add(Blocks.WARPED_PRESSURE_PLATE);
        noCollision.add(Blocks.CRIMSON_BUTTON);
        noCollision.add(Blocks.WARPED_BUTTON);
        noCollision.add(Blocks.CRIMSON_SIGN);
        noCollision.add(Blocks.WARPED_SIGN);
        //noCollision.add()(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
        noCollision.add(Blocks.POLISHED_BLACKSTONE_BUTTON);
        noCollision.add(Blocks.SCULK_VEIN);
        noCollision.add(Blocks.SPORE_BLOSSOM);
        noCollision.add(Blocks.PINK_PETALS);
        noCollision.add(Blocks.HANGING_ROOTS);
        noCollision.add(Blocks.FROGSPAWN);
    }

    public boolean isUnbreakable() {
        return this == Blocks.BEDROCK || this == Blocks.COMMAND_BLOCK || this == Blocks.STRUCTURE_BLOCK || this == Blocks.WATER || this == Blocks.LAVA;
    }

    public String getNameNoPrefix() {
        return this.getName().substring(10);
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
