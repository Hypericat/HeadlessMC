package client.game.items;

import java.util.HashMap;

public class EnchantmentTypes {
    private EnchantmentTypes() {


    }
    public static HashMap<Integer, EnchantmentType> map = new HashMap<>();
    public static EnchantmentType AQUA_AFFINITY = register(new EnchantmentType(0, "aqua_affinity"));
    public static EnchantmentType BANE_OF_ARTHROPODS = register(new EnchantmentType(1, "bane_of_arthropods"));
    public static EnchantmentType BINDING_CURSE = register(new EnchantmentType(2, "binding_curse"));
    public static EnchantmentType BLAST_PROTECTION = register(new EnchantmentType(3, "blast_protection"));
    public static EnchantmentType BREACH = register(new EnchantmentType(4, "breach"));
    public static EnchantmentType CHANNELING = register(new EnchantmentType(5, "channeling"));
    public static EnchantmentType DENSITY = register(new EnchantmentType(6, "density"));
    public static EnchantmentType DEPTH_STRIDER = register(new EnchantmentType(7, "depth_strider"));
    public static EnchantmentType EFFICIENCY = register(new EnchantmentType(8, "efficiency"));
    public static EnchantmentType FEATHER_FALLING = register(new EnchantmentType(9, "feather_falling"));
    public static EnchantmentType FIRE_ASPECT = register(new EnchantmentType(10, "fire_aspect"));
    public static EnchantmentType FIRE_PROTECTION = register(new EnchantmentType(11, "fire_protection"));
    public static EnchantmentType FLAME = register(new EnchantmentType(12, "flame"));
    public static EnchantmentType FORTUNE = register(new EnchantmentType(13, "fortune"));
    public static EnchantmentType FROST_WALKER = register(new EnchantmentType(14, "frost_walker"));
    public static EnchantmentType IMPALING = register(new EnchantmentType(15, "impaling"));
    public static EnchantmentType INFINITY = register(new EnchantmentType(16, "infinity"));
    public static EnchantmentType KNOCKBACK = register(new EnchantmentType(17, "knockback"));
    public static EnchantmentType LOOTING = register(new EnchantmentType(18, "looting"));
    public static EnchantmentType LOYALTY = register(new EnchantmentType(19, "loyalty"));
    public static EnchantmentType LUCK_OF_THE_SEA = register(new EnchantmentType(20, "luck_of_the_sea"));
    public static EnchantmentType LURE = register(new EnchantmentType(21, "lure"));
    public static EnchantmentType MENDING = register(new EnchantmentType(22, "mending"));
    public static EnchantmentType MULTISHOT = register(new EnchantmentType(23, "multishot"));
    public static EnchantmentType PIERCING = register(new EnchantmentType(24, "piercing"));
    public static EnchantmentType POWER = register(new EnchantmentType(25, "power"));
    public static EnchantmentType PROJECTILE_PROTECTION = register(new EnchantmentType(26, "projectile_protection"));
    public static EnchantmentType PROTECTION = register(new EnchantmentType(27, "protection"));
    public static EnchantmentType PUNCH = register(new EnchantmentType(28, "punch"));
    public static EnchantmentType QUICK_CHARGE = register(new EnchantmentType(29, "quick_charge"));
    public static EnchantmentType RESPIRATION = register(new EnchantmentType(30, "respiration"));
    public static EnchantmentType RIPTIDE = register(new EnchantmentType(31, "riptide"));
    public static EnchantmentType SHARPNESS = register(new EnchantmentType(32, "sharpness"));
    public static EnchantmentType SILK_TOUCH = register(new EnchantmentType(33, "silk_touch"));
    public static EnchantmentType SMITE = register(new EnchantmentType(34, "smite"));
    public static EnchantmentType SOUL_SPEED = register(new EnchantmentType(35, "soul_speed"));
    public static EnchantmentType SWEEPING_EDGE = register(new EnchantmentType(36, "sweeping_edge"));
    public static EnchantmentType SWIFT_SNEAK = register(new EnchantmentType(37, "swift_sneak"));
    public static EnchantmentType THORNS = register(new EnchantmentType(38, "thorns"));
    public static EnchantmentType UNBREAKING = register(new EnchantmentType(39, "unbreaking"));
    public static EnchantmentType VANISHING_CURSE = register(new EnchantmentType(40, "vanishing_curse"));
    public static EnchantmentType WIND_BURST = register(new EnchantmentType(41, "wind_burst"));

    private static EnchantmentType register(EnchantmentType enchantmentType) {
        map.put(enchantmentType.getTypeID(), enchantmentType);
        return enchantmentType;
    }

    public static EnchantmentType fromID(int id) {
        return map.get(id);
    }
}
