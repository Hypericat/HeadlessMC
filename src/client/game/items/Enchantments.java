package client.game.items;

import java.util.HashMap;

public class Enchantments {
    private Enchantments() {


    }
    public static HashMap<Integer, Enchantment> map = new HashMap<>();
    public static Enchantment AQUA_AFFINITY = register(new Enchantment(0, "aqua_affinity"));
    public static Enchantment BANE_OF_ARTHROPODS = register(new Enchantment(1, "bane_of_arthropods"));
    public static Enchantment BINDING_CURSE = register(new Enchantment(2, "binding_curse"));
    public static Enchantment BLAST_PROTECTION = register(new Enchantment(3, "blast_protection"));
    public static Enchantment BREACH = register(new Enchantment(4, "breach"));
    public static Enchantment CHANNELING = register(new Enchantment(5, "channeling"));
    public static Enchantment DENSITY = register(new Enchantment(6, "density"));
    public static Enchantment DEPTH_STRIDER = register(new Enchantment(7, "depth_strider"));
    public static Enchantment EFFICIENCY = register(new Enchantment(8, "efficiency"));
    public static Enchantment FEATHER_FALLING = register(new Enchantment(9, "feather_falling"));
    public static Enchantment FIRE_ASPECT = register(new Enchantment(10, "fire_aspect"));
    public static Enchantment FIRE_PROTECTION = register(new Enchantment(11, "fire_protection"));
    public static Enchantment FLAME = register(new Enchantment(12, "flame"));
    public static Enchantment FORTUNE = register(new Enchantment(13, "fortune"));
    public static Enchantment FROST_WALKER = register(new Enchantment(14, "frost_walker"));
    public static Enchantment IMPALING = register(new Enchantment(15, "impaling"));
    public static Enchantment INFINITY = register(new Enchantment(16, "infinity"));
    public static Enchantment KNOCKBACK = register(new Enchantment(17, "knockback"));
    public static Enchantment LOOTING = register(new Enchantment(18, "looting"));
    public static Enchantment LOYALTY = register(new Enchantment(19, "loyalty"));
    public static Enchantment LUCK_OF_THE_SEA = register(new Enchantment(20, "luck_of_the_sea"));
    public static Enchantment LURE = register(new Enchantment(21, "lure"));
    public static Enchantment MENDING = register(new Enchantment(22, "mending"));
    public static Enchantment MULTISHOT = register(new Enchantment(23, "multishot"));
    public static Enchantment PIERCING = register(new Enchantment(24, "piercing"));
    public static Enchantment POWER = register(new Enchantment(25, "power"));
    public static Enchantment PROJECTILE_PROTECTION = register(new Enchantment(26, "projectile_protection"));
    public static Enchantment PROTECTION = register(new Enchantment(27, "protection"));
    public static Enchantment PUNCH = register(new Enchantment(28, "punch"));
    public static Enchantment QUICK_CHARGE = register(new Enchantment(29, "quick_charge"));
    public static Enchantment RESPIRATION = register(new Enchantment(30, "respiration"));
    public static Enchantment RIPTIDE = register(new Enchantment(31, "riptide"));
    public static Enchantment SHARPNESS = register(new Enchantment(32, "sharpness"));
    public static Enchantment SILK_TOUCH = register(new Enchantment(33, "silk_touch"));
    public static Enchantment SMITE = register(new Enchantment(34, "smite"));
    public static Enchantment SOUL_SPEED = register(new Enchantment(35, "soul_speed"));
    public static Enchantment SWEEPING_EDGE = register(new Enchantment(36, "sweeping_edge"));
    public static Enchantment SWIFT_SNEAK = register(new Enchantment(37, "swift_sneak"));
    public static Enchantment THORNS = register(new Enchantment(38, "thorns"));
    public static Enchantment UNBREAKING = register(new Enchantment(39, "unbreaking"));
    public static Enchantment VANISHING_CURSE = register(new Enchantment(40, "vanishing_curse"));
    public static Enchantment WIND_BURST = register(new Enchantment(41, "wind_burst"));

    private static Enchantment register(Enchantment enchantment) {
        map.put(enchantment.getTypeID(), enchantment);
        return enchantment;
    }

    public static Enchantment fromID(int id) {
        return map.get(id);
    }
}
