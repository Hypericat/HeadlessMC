package client.game;

import client.utils.Box;
import client.utils.Vec3d;

import java.util.HashMap;

public class EntityTypes {
    private static final HashMap<Integer, EntityType<?>> entityTypes = new HashMap<>();

    public static final EntityType<LivingEntity> ALLAY = register(new EntityType<>(0, Box.fromDimensions(new Vec3d(0.35d, 0.6d, 0.35d)), "minecraft:allay", LivingEntity.class));
    public static final EntityType<Entity> AREA_EFFECT_CLOUD = register(new EntityType<>(1, Box.fromDimensions(new Vec3d(2.0d, 0.5d, 2.0d)), "minecraft:area_effect_cloud", Entity.class));
    public static final EntityType<LivingEntity> ARMADILLO = register(new EntityType<>(2, Box.fromDimensions(new Vec3d(0.7d, 0.65d, 0.7d)), "minecraft:armadillo", LivingEntity.class));
    public static final EntityType<Entity> ARMOR_STAND = register(new EntityType<>(3, Box.fromDimensions(new Vec3d(0.5d, 1.975d, 0.5d)), "minecraft:armor_stand", Entity.class));
    public static final EntityType<Entity> ARROW = register(new EntityType<>(4, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:arrow", Entity.class));
    public static final EntityType<LivingEntity> AXOLOTL = register(new EntityType<>(5, Box.fromDimensions(new Vec3d(0.75d, 0.42d, 0.75d)), "minecraft:axolotl", LivingEntity.class));
    public static final EntityType<LivingEntity> BAT = register(new EntityType<>(6, Box.fromDimensions(new Vec3d(0.5d, 0.9d, 0.5d)), "minecraft:bat", LivingEntity.class));
    public static final EntityType<LivingEntity> BEE = register(new EntityType<>(7, Box.fromDimensions(new Vec3d(0.7d, 0.6d, 0.7d)), "minecraft:bee", LivingEntity.class));
    public static final EntityType<LivingEntity> BLAZE = register(new EntityType<>(8, Box.fromDimensions(new Vec3d(0.6d, 1.8d, 0.6d)), "minecraft:blaze", LivingEntity.class));
    public static final EntityType<Entity> BLOCK_DISPLAY = register(new EntityType<>(9, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:block_display", Entity.class));
    public static final EntityType<Entity> BOAT = register(new EntityType<>(10, Box.fromDimensions(new Vec3d(1.375d, 0.5625d, 1.375d)), "minecraft:boat", Entity.class));
    public static final EntityType<LivingEntity> BOGGED = register(new EntityType<>(11, Box.fromDimensions(new Vec3d(0.6f, 1.99f, 0.6f)), "minecraft:bogged", LivingEntity.class));
    public static final EntityType<LivingEntity> BREEZE = register(new EntityType<>(12, Box.fromDimensions(new Vec3d(0.6f, 1.77f, 0.6f)), "minecraft:breeze", LivingEntity.class));
    public static final EntityType<Entity> BREEZE_WIND_CHARGE = register(new EntityType<>(13, Box.fromDimensions(new Vec3d(0.3125, 0.3125, 0.3125)), "minecraft:breeze_wind_charge", Entity.class));
    public static final EntityType<LivingEntity> CAMEL = register(new EntityType<>(14, Box.fromDimensions(new Vec3d(1.7d, 2.375d, 1.7d)), "minecraft:camel", LivingEntity.class));
    public static final EntityType<LivingEntity> CAT = register(new EntityType<>(15, Box.fromDimensions(new Vec3d(0.6d, 0.7d, 0.6d)), "minecraft:cat", LivingEntity.class));
    public static final EntityType<LivingEntity> CAVE_SPIDER = register(new EntityType<>(16, Box.fromDimensions(new Vec3d(0.7d, 0.5d, 0.7d)), "minecraft:cave_spider", LivingEntity.class));
    public static final EntityType<Entity> CHEST_BOAT = register(new EntityType<>(17, Box.fromDimensions(new Vec3d(1.375d, 0.5625d, 1.375d)), "minecraft:chest_boat", Entity.class));
    public static final EntityType<Entity> CHEST_MINECART = register(new EntityType<>(18, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:chest_minecart", Entity.class));
    public static final EntityType<LivingEntity> CHICKEN = register(new EntityType<>(19, Box.fromDimensions(new Vec3d(0.4d, 0.7d, 0.4d)), "minecraft:chicken", LivingEntity.class));
    public static final EntityType<LivingEntity> COD = register(new EntityType<>(20, Box.fromDimensions(new Vec3d(0.5d, 0.3d, 0.5d)), "minecraft:cod", LivingEntity.class));
    public static final EntityType<Entity> COMMAND_BLOCK_MINECART = register(new EntityType<>(21, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:command_block_minecart", Entity.class));
    public static final EntityType<LivingEntity> COW = register(new EntityType<>(22, Box.fromDimensions(new Vec3d(0.9d, 1.4d, 0.9d)), "minecraft:cow", LivingEntity.class));
    public static final EntityType<LivingEntity> CREEPER = register(new EntityType<>(23, Box.fromDimensions(new Vec3d(0.6d, 1.7d, 0.6d)), "minecraft:creeper", LivingEntity.class));
    public static final EntityType<LivingEntity> DOLPHIN = register(new EntityType<>(24, Box.fromDimensions(new Vec3d(0.9d, 0.6d, 0.9d)), "minecraft:dolphin", LivingEntity.class));
    public static final EntityType<LivingEntity> DONKEY = register(new EntityType<>(25, Box.fromDimensions(new Vec3d(1.3964844d, 1.5d, 1.3964844d)), "minecraft:donkey", LivingEntity.class));
    public static final EntityType<Entity> DRAGON_FIREBALL = register(new EntityType<>(26, Box.fromDimensions(new Vec3d(1.0d, 1.0d, 1.0d)), "minecraft:dragon_fireball", Entity.class));
    public static final EntityType<LivingEntity> DROWNED = register(new EntityType<>(27, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:drowned", LivingEntity.class));
    public static final EntityType<Entity> EGG = register(new EntityType<>(28, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:egg", Entity.class));
    public static final EntityType<LivingEntity> ELDER_GUARDIAN = register(new EntityType<>(29, Box.fromDimensions(new Vec3d(1.9975d, 1.9975d, 1.9975d)), "minecraft:elder_guardian", LivingEntity.class));
    public static final EntityType<Entity> END_CRYSTAL = register(new EntityType<>(30, Box.fromDimensions(new Vec3d(2.0d, 2.0d, 2.0d)), "minecraft:end_crystal", Entity.class));
    public static final EntityType<LivingEntity> ENDER_DRAGON = register(new EntityType<>(31, Box.fromDimensions(new Vec3d(16.0d, 8.0d, 16.0d)), "minecraft:ender_dragon", LivingEntity.class));
    public static final EntityType<Entity> ENDER_PEARL = register(new EntityType<>(32, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:ender_pearl", Entity.class));
    public static final EntityType<LivingEntity> ENDERMAN = register(new EntityType<>(33, Box.fromDimensions(new Vec3d(0.6d, 2.9d, 0.6d)), "minecraft:enderman", LivingEntity.class));
    public static final EntityType<LivingEntity> ENDERMITE = register(new EntityType<>(34, Box.fromDimensions(new Vec3d(0.4d, 0.3d, 0.4d)), "minecraft:endermite", LivingEntity.class));
    public static final EntityType<LivingEntity> EVOKER = register(new EntityType<>(35, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:evoker", LivingEntity.class));
    public static final EntityType<Entity> EVOKER_FANGS = register(new EntityType<>(36, Box.fromDimensions(new Vec3d(0.5d, 0.8d, 0.5d)), "minecraft:evoker_fangs", Entity.class));
    public static final EntityType<Entity> EXPERIENCE_BOTTLE = register(new EntityType<>(37, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:experience_bottle", Entity.class));
    public static final EntityType<Entity> EXPERIENCE_ORB = register(new EntityType<>(38, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:experience_orb", Entity.class));
    public static final EntityType<Entity> EYE_OF_ENDER = register(new EntityType<>(39, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:eye_of_ender", Entity.class));
    public static final EntityType<Entity> FALLING_BLOCK = register(new EntityType<>(40, Box.fromDimensions(new Vec3d(0.98d, 0.98d, 0.98d)), "minecraft:falling_block", Entity.class));
    public static final EntityType<Entity> FIREBALL = register(new EntityType<>(62, Box.fromDimensions(new Vec3d(1.0d, 1.0d, 1.0d)), "minecraft:fireball", Entity.class));
    public static final EntityType<Entity> FIREWORK_ROCKET = register(new EntityType<>(41, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:firework_rocket", Entity.class));
    public static final EntityType<Entity> FISHING_BOBBER = register(new EntityType<>(129, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:fishing_bobber", Entity.class));
    public static final EntityType<LivingEntity> FOX = register(new EntityType<>(42, Box.fromDimensions(new Vec3d(0.6d, 0.7d, 0.6d)), "minecraft:fox", LivingEntity.class));
    public static final EntityType<LivingEntity> FROG = register(new EntityType<>(43, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:frog", LivingEntity.class));
    public static final EntityType<Entity> FURNACE_MINECART = register(new EntityType<>(44, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:furnace_minecart", Entity.class));
    public static final EntityType<LivingEntity> GHAST = register(new EntityType<>(45, Box.fromDimensions(new Vec3d(4.0d, 4.0d, 4.0d)), "minecraft:ghast", LivingEntity.class));
    public static final EntityType<LivingEntity> GIANT = register(new EntityType<>(46, Box.fromDimensions(new Vec3d(3.6d, 12.0d, 3.6d)), "minecraft:giant", LivingEntity.class));
    public static final EntityType<Entity> GLOW_ITEM_FRAME = register(new EntityType<>(47, Box.fromDimensions(new Vec3d(0.75d, 0.75d, 0.0625)), "minecraft:glow_item_frame", Entity.class));
    public static final EntityType<LivingEntity> GLOW_SQUID = register(new EntityType<>(48, Box.fromDimensions(new Vec3d(0.8d, 0.8d, 0.8d)), "minecraft:glow_squid", LivingEntity.class));
    public static final EntityType<LivingEntity> GOAT = register(new EntityType<>(49, Box.fromDimensions(new Vec3d(1.3d, 0.9d, 1.3d)), "minecraft:goat", LivingEntity.class));
    public static final EntityType<LivingEntity> GUARDIAN = register(new EntityType<>(50, Box.fromDimensions(new Vec3d(0.85d, 0.85d, 0.85d)), "minecraft:guardian", LivingEntity.class));
    public static final EntityType<LivingEntity> HOGLIN = register(new EntityType<>(51, Box.fromDimensions(new Vec3d(1.3964844d, 1.4d, 1.3964844d)), "minecraft:hoglin", LivingEntity.class));
    public static final EntityType<Entity> HOPPER_MINECART = register(new EntityType<>(52, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:hopper_minecart", Entity.class));
    public static final EntityType<LivingEntity> HORSE = register(new EntityType<>(53, Box.fromDimensions(new Vec3d(1.3964844d, 1.6d, 1.3964844d)), "minecraft:horse", LivingEntity.class));
    public static final EntityType<LivingEntity> HUSK = register(new EntityType<>(54, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:husk", LivingEntity.class));
    public static final EntityType<LivingEntity> ILLUSIONER = register(new EntityType<>(55, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:illusioner", LivingEntity.class));
    public static final EntityType<Entity> INTERACTION = register(new EntityType<>(56, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:interaction", Entity.class));
    public static final EntityType<LivingEntity> IRON_GOLEM = register(new EntityType<>(57, Box.fromDimensions(new Vec3d(1.4d, 2.7d, 1.4d)), "minecraft:iron_golem", LivingEntity.class));
    public static final EntityType<Entity> ITEM = register(new EntityType<>(58, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:item", Entity.class));
    public static final EntityType<Entity> ITEM_DISPLAY = register(new EntityType<>(59, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:item_display", Entity.class));
    public static final EntityType<Entity> ITEM_FRAME = register(new EntityType<>(60, Box.fromDimensions(new Vec3d(0.75d, 0.75d, 0.75d)), "minecraft:item_frame", Entity.class));
    public static final EntityType<Entity> LEASH_KNOT = register(new EntityType<>(63, Box.fromDimensions(new Vec3d(0.375d, 0.5d, 0.375d)), "minecraft:leash_knot", Entity.class));
    public static final EntityType<Entity> LIGHTNING_BOLT = register(new EntityType<>(64, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:lightning_bolt", Entity.class));
    public static final EntityType<LivingEntity> LLAMA = register(new EntityType<>(65, Box.fromDimensions(new Vec3d(0.9d, 1.87d, 0.9d)), "minecraft:llama", LivingEntity.class));
    public static final EntityType<LivingEntity> LLAMA_SPIT = register(new EntityType<>(66, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:llama_spit", LivingEntity.class));
    public static final EntityType<LivingEntity> MAGMA_CUBE = register(new EntityType<>(67, Box.fromDimensions(new Vec3d(0.5202d, 0.5202d, 0.5202d)), "minecraft:magma_cube", LivingEntity.class));
    public static final EntityType<Entity> MARKER = register(new EntityType<>(68, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:marker", Entity.class));
    public static final EntityType<Entity> MINECART = register(new EntityType<>(69, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:minecart", Entity.class));
    public static final EntityType<LivingEntity> MOOSHROOM = register(new EntityType<>(70, Box.fromDimensions(new Vec3d(0.9d, 1.4d, 0.9d)), "minecraft:mooshroom", LivingEntity.class));
    public static final EntityType<LivingEntity> MULE = register(new EntityType<>(71, Box.fromDimensions(new Vec3d(1.3964844d, 1.6d, 1.3964844d)), "minecraft:mule", LivingEntity.class));
    public static final EntityType<LivingEntity> OCELOT = register(new EntityType<>(72, Box.fromDimensions(new Vec3d(0.6d, 0.7d, 0.6d)), "minecraft:ocelot", LivingEntity.class));
    public static final EntityType<Entity> OMINOUS_ITEM_SPAWNER = register(new EntityType<>(61, Box.fromDimensions(new Vec3d(0.25f, 0.25f, 0.25f)), "minecraft:ominous_item_spawner", Entity.class));
    public static final EntityType<Entity> PAINTING = register(new EntityType<>(73, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:painting", Entity.class));
    public static final EntityType<LivingEntity> PANDA = register(new EntityType<>(74, Box.fromDimensions(new Vec3d(1.3d, 1.25d, 1.3d)), "minecraft:panda", LivingEntity.class));
    public static final EntityType<LivingEntity> PARROT = register(new EntityType<>(75, Box.fromDimensions(new Vec3d(0.5d, 0.9d, 0.5d)), "minecraft:parrot", LivingEntity.class));
    public static final EntityType<LivingEntity> PHANTOM = register(new EntityType<>(76, Box.fromDimensions(new Vec3d(0.9d, 0.5d, 0.9d)), "minecraft:phantom", LivingEntity.class));
    public static final EntityType<LivingEntity> PIG = register(new EntityType<>(77, Box.fromDimensions(new Vec3d(0.9d, 0.9d, 0.9d)), "minecraft:pig", LivingEntity.class));
    public static final EntityType<LivingEntity> PIGLIN = register(new EntityType<>(78, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:piglin", LivingEntity.class));
    public static final EntityType<LivingEntity> PIGLIN_BRUTE = register(new EntityType<>(79, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:piglin_brute", LivingEntity.class));
    public static final EntityType<LivingEntity> PILLAGER = register(new EntityType<>(80, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:pillager", LivingEntity.class));
    public static final EntityType<LivingEntity> PLAYER = register(new EntityType<>(128, Box.fromDimensions(new Vec3d(0.6d, 1.8d, 0.6d)), "minecraft:player", LivingEntity.class));
    public static final EntityType<LivingEntity> POLAR_BEAR = register(new EntityType<>(81, Box.fromDimensions(new Vec3d(1.4d, 1.4d, 1.4d)), "minecraft:polar_bear", LivingEntity.class));
    public static final EntityType<Entity> POTION = register(new EntityType<>(82, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:potion", Entity.class));
    public static final EntityType<LivingEntity> PUFFERFISH = register(new EntityType<>(83, Box.fromDimensions(new Vec3d(0.7d, 0.7d, 0.7d)), "minecraft:pufferfish", LivingEntity.class));
    public static final EntityType<LivingEntity> RABBIT = register(new EntityType<>(84, Box.fromDimensions(new Vec3d(0.4d, 0.5d, 0.4d)), "minecraft:rabbit", LivingEntity.class));
    public static final EntityType<LivingEntity> RAVAGER = register(new EntityType<>(85, Box.fromDimensions(new Vec3d(1.95d, 2.2d, 1.95d)), "minecraft:ravager", LivingEntity.class));
    public static final EntityType<LivingEntity> SALMON = register(new EntityType<>(86, Box.fromDimensions(new Vec3d(0.7d, 0.4d, 0.7d)), "minecraft:salmon", LivingEntity.class));
    public static final EntityType<LivingEntity> SHEEP = register(new EntityType<>(87, Box.fromDimensions(new Vec3d(0.9d, 1.3d, 0.9d)), "minecraft:sheep", LivingEntity.class));
    public static final EntityType<LivingEntity> SHULKER = register(new EntityType<>(88, Box.fromDimensions(new Vec3d(1.0d, 1.0d, 1.0d)), "minecraft:shulker", LivingEntity.class));
    public static final EntityType<Entity> SHULKER_BULLET = register(new EntityType<>(89, Box.fromDimensions(new Vec3d(0.3125d, 0.3125d, 0.3125d)), "minecraft:shulker_bullet", Entity.class));
    public static final EntityType<LivingEntity> SILVERFISH = register(new EntityType<>(90, Box.fromDimensions(new Vec3d(0.4d, 0.3d, 0.4d)), "minecraft:silverfish", LivingEntity.class));
    public static final EntityType<LivingEntity> SKELETON = register(new EntityType<>(91, Box.fromDimensions(new Vec3d(0.6d, 1.99d, 0.6d)), "minecraft:skeleton", LivingEntity.class));
    public static final EntityType<LivingEntity> SKELETON_HORSE = register(new EntityType<>(92, Box.fromDimensions(new Vec3d(1.3964844d, 1.6d, 1.3964844d)), "minecraft:skeleton_horse", LivingEntity.class));
    public static final EntityType<LivingEntity> SLIME = register(new EntityType<>(93, Box.fromDimensions(new Vec3d(0.51d, 0.51d, 0.51d)), "minecraft:slime", LivingEntity.class));
    public static final EntityType<Entity> SMALL_FIREBALL = register(new EntityType<>(94, Box.fromDimensions(new Vec3d(0.3125d, 0.3125d, 0.3125d)), "minecraft:small_fireball", Entity.class));
    public static final EntityType<LivingEntity> SNIFFER = register(new EntityType<>(95, Box.fromDimensions(new Vec3d(1.9d, 1.75d, 1.9d)), "minecraft:sniffer", LivingEntity.class));
    public static final EntityType<LivingEntity> SNOW_GOLEM = register(new EntityType<>(96, Box.fromDimensions(new Vec3d(0.7d, 1.9d, 0.7d)), "minecraft:snow_golem", LivingEntity.class));
    public static final EntityType<Entity> SNOWBALL = register(new EntityType<>(97, Box.fromDimensions(new Vec3d(0.25d, 0.25d, 0.25d)), "minecraft:snowball", Entity.class));
    public static final EntityType<Entity> SPAWNER_MINECART = register(new EntityType<>(98, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:spawner_minecart", Entity.class));
    public static final EntityType<Entity> SPECTRAL_ARROW = register(new EntityType<>(99, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:spectral_arrow", Entity.class));
    public static final EntityType<LivingEntity> SPIDER = register(new EntityType<>(100, Box.fromDimensions(new Vec3d(1.4d, 0.9d, 1.4d)), "minecraft:spider", LivingEntity.class));
    public static final EntityType<LivingEntity> SQUID = register(new EntityType<>(101, Box.fromDimensions(new Vec3d(0.8d, 0.8d, 0.8d)), "minecraft:squid", LivingEntity.class));
    public static final EntityType<LivingEntity> STRAY = register(new EntityType<>(102, Box.fromDimensions(new Vec3d(0.6d, 1.99d, 0.6d)), "minecraft:stray", LivingEntity.class));
    public static final EntityType<LivingEntity> STRIDER = register(new EntityType<>(103, Box.fromDimensions(new Vec3d(0.9d, 1.7d, 0.9d)), "minecraft:strider", LivingEntity.class));
    public static final EntityType<LivingEntity> TADPOLE = register(new EntityType<>(104, Box.fromDimensions(new Vec3d(0.4d, 0.3d, 0.4d)), "minecraft:tadpole", LivingEntity.class));
    public static final EntityType<Entity> TEXT_DISPLAY = register(new EntityType<>(105, Box.fromDimensions(new Vec3d(0.0d, 0.0d, 0.0d)), "minecraft:text_display", Entity.class));
    public static final EntityType<Entity> TNT = register(new EntityType<>(106, Box.fromDimensions(new Vec3d(0.98d, 0.98d, 0.98d)), "minecraft:tnt", Entity.class));
    public static final EntityType<Entity> TNT_MINECART = register(new EntityType<>(107, Box.fromDimensions(new Vec3d(0.98d, 0.7d, 0.98d)), "minecraft:tnt_minecart", Entity.class));
    public static final EntityType<LivingEntity> TRADER_LLAMA = register(new EntityType<>(108, Box.fromDimensions(new Vec3d(0.9d, 1.87d, 0.9d)), "minecraft:trader_llama", LivingEntity.class));
    public static final EntityType<LivingEntity> TRIDENT = register(new EntityType<>(109, Box.fromDimensions(new Vec3d(0.5d, 0.5d, 0.5d)), "minecraft:trident", LivingEntity.class));
    public static final EntityType<LivingEntity> TROPICAL_FISH = register(new EntityType<>(110, Box.fromDimensions(new Vec3d(0.5d, 0.4d, 0.5d)), "minecraft:tropical_fish", LivingEntity.class));
    public static final EntityType<LivingEntity> TURTLE = register(new EntityType<>(111, Box.fromDimensions(new Vec3d(1.2d, 0.4d, 1.2d)), "minecraft:turtle", LivingEntity.class));
    public static final EntityType<LivingEntity> VEX = register(new EntityType<>(112, Box.fromDimensions(new Vec3d(0.4d, 0.8d, 0.4d)), "minecraft:vex", LivingEntity.class));
    public static final EntityType<LivingEntity> VILLAGER = register(new EntityType<>(113, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:villager", LivingEntity.class));
    public static final EntityType<LivingEntity> VINDICATOR = register(new EntityType<>(114, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:vindicator", LivingEntity.class));
    public static final EntityType<LivingEntity> WANDERING_TRADER = register(new EntityType<>(115, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:wandering_trader", LivingEntity.class));
    public static final EntityType<LivingEntity> WARDEN = register(new EntityType<>(116, Box.fromDimensions(new Vec3d(0.9d, 2.9d, 0.9d)), "minecraft:warden", LivingEntity.class));
    public static final EntityType<Entity> WIND_CHARGE = register(new EntityType<>(117, Box.fromDimensions(new Vec3d(0.3125f, 0.3125f, 0.3125f)), "minecraft:wind_charge", Entity.class));
    public static final EntityType<LivingEntity> WITCH = register(new EntityType<>(118, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:witch", LivingEntity.class));
    public static final EntityType<LivingEntity> WITHER = register(new EntityType<>(119, Box.fromDimensions(new Vec3d(0.9d, 3.5d, 0.9d)), "minecraft:wither", LivingEntity.class));
    public static final EntityType<LivingEntity> WITHER_SKELETON = register(new EntityType<>(120, Box.fromDimensions(new Vec3d(0.6d, 1.99d, 0.6d)), "minecraft:wither_skeleton", LivingEntity.class));
    public static final EntityType<Entity> WITHER_SKULL = register(new EntityType<>(121, Box.fromDimensions(new Vec3d(0.3125d, 0.3125d, 0.3125d)), "minecraft:wither_skull", Entity.class));
    public static final EntityType<LivingEntity> WOLF = register(new EntityType<>(122, Box.fromDimensions(new Vec3d(0.6d, 0.85d, 0.6d)), "minecraft:wither_skull", LivingEntity.class));
    public static final EntityType<LivingEntity> ZOGLIN = register(new EntityType<>(123, Box.fromDimensions(new Vec3d(1.2d, 1.4d, 1.2d)), "minecraft:zoglin", LivingEntity.class));
    public static final EntityType<LivingEntity> ZOMBIE = register(new EntityType<>(124, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:zombie", LivingEntity.class));
    public static final EntityType<LivingEntity> ZOMBIE_HORSE = register(new EntityType<>(125, Box.fromDimensions(new Vec3d(1.3964844d, 1.6d, 1.3964844d)), "minecraft:zombie_horse", LivingEntity.class));
    public static final EntityType<LivingEntity> ZOMBIE_VILLAGER = register(new EntityType<>(126, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:zombie_villager", LivingEntity.class));
    public static final EntityType<LivingEntity> ZOMBIFIED_PIGLIN = register(new EntityType<>(127, Box.fromDimensions(new Vec3d(0.6d, 1.95d, 0.6d)), "minecraft:zombified_piglin", LivingEntity.class));





    private EntityTypes() {};

    public static <T extends Entity> EntityType<T> register(EntityType<T> type) {
        entityTypes.put(type.getType(), type);
        return type;
    }

    public static <T extends Entity> EntityType<T> getTypeByID(int id) {
        return (EntityType<T>) entityTypes.get(id);
    }
}
