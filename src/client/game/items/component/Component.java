package client.game.items.component;

public class Component {
    private AttributeModifiers modifiers;
    private Enchantments enchantments;
    private Lore lore;
    private int maxStackSize;
    private Rarity rarity;
    private int repairCost;
    private boolean fireResistant;
    private Food food;
    private boolean bucketEntityData;
    private Container container;
    private Bees bees;
    private BannerPatterns bannerPatterns;
    private int damage;
    private int maxDamage;
    private BundleContents contents;
    private ChargedProjectiles projectiles;
    private DebugStickState debugStickState;
    private boolean enchantmentGlintOverride;
    private PotDecorations potDecorations;
    private Tool tool;
    private StoredEnchantments storedEnchantments;
    private int mapColor;
    private MapDecorations decorations;
    private Fireworks fireworks;
    private Recipes recipes;
    private PotionContents potionContents;
    private JukeboxPlayable jukeboxPlayable;
    private int ominousBottleAmplifier;
    private SuspiciousStewEffects effects;
    private WritableBookContent writableBookContent;

    public Component(builder builder) {
        this.modifiers = builder.modifiers;
        this.enchantments = builder.enchantments;
        this.lore = builder.lore;
        this.maxStackSize = builder.maxStackSize;
        this.rarity = builder.rarity;
        this.repairCost = builder.repairCost;
        this.fireResistant = builder.fireResistant;
        this.food = builder.food;
        this.bucketEntityData = builder.bucketEntityData;
        this.container = builder.container;
        this.bees = builder.bees;
        this.bannerPatterns = builder.bannerPatterns;
        this.damage = builder.damage;
        this.maxDamage = builder.maxDamage;
        this.contents = builder.contents;
        this.projectiles = builder.projectiles;
        this.debugStickState = builder.debugStickState;
        this.enchantmentGlintOverride = builder.enchantmentGlintOverride;
        this.potDecorations = builder.potDecorations;
        this.tool = builder.tool;
        this.storedEnchantments = builder.storedEnchantments;
        this.mapColor = builder.mapColor;
        this.decorations = builder.decorations;
        this.fireworks = builder.fireworks;
        this.recipes = builder.recipes;
        this.potionContents = builder.potionContents;
        this.jukeboxPlayable = builder.jukeboxPlayable;
        this.ominousBottleAmplifier = builder.ominousBottleAmplifier;
        this.effects = builder.effects;
        this.writableBookContent = builder.writableBookContent;
    }

    public static builder getBuilder() {
        return new builder();
    }

    public AttributeModifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(AttributeModifiers modifiers) {
        this.modifiers = modifiers;
    }

    public Enchantments getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Enchantments enchantments) {
        this.enchantments = enchantments;
    }

    public Lore getLore() {
        return lore;
    }

    public void setLore(Lore lore) {
        this.lore = lore;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public int getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(int repairCost) {
        this.repairCost = repairCost;
    }

    public boolean isFireResistant() {
        return fireResistant;
    }

    public void setFireResistant(boolean fireResistant) {
        this.fireResistant = fireResistant;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public boolean isBucketEntityData() {
        return bucketEntityData;
    }

    public void setBucketEntityData(boolean bucketEntityData) {
        this.bucketEntityData = bucketEntityData;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Bees getBees() {
        return bees;
    }

    public void setBees(Bees bees) {
        this.bees = bees;
    }

    public BannerPatterns getBannerPatterns() {
        return bannerPatterns;
    }

    public void setBannerPatterns(BannerPatterns bannerPatterns) {
        this.bannerPatterns = bannerPatterns;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public BundleContents getContents() {
        return contents;
    }

    public void setContents(BundleContents contents) {
        this.contents = contents;
    }

    public ChargedProjectiles getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ChargedProjectiles projectiles) {
        this.projectiles = projectiles;
    }

    public DebugStickState getDebugStickState() {
        return debugStickState;
    }

    public void setDebugStickState(DebugStickState debugStickState) {
        this.debugStickState = debugStickState;
    }

    public boolean isEnchantmentGlintOverride() {
        return enchantmentGlintOverride;
    }

    public void setEnchantmentGlintOverride(boolean enchantmentGlintOverride) {
        this.enchantmentGlintOverride = enchantmentGlintOverride;
    }

    public PotDecorations getPotDecorations() {
        return potDecorations;
    }

    public void setPotDecorations(PotDecorations potDecorations) {
        this.potDecorations = potDecorations;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public StoredEnchantments getStoredEnchantments() {
        return storedEnchantments;
    }

    public void setStoredEnchantments(StoredEnchantments storedEnchantments) {
        this.storedEnchantments = storedEnchantments;
    }

    public int getMapColor() {
        return mapColor;
    }

    public void setMapColor(int mapColor) {
        this.mapColor = mapColor;
    }

    public MapDecorations getDecorations() {
        return decorations;
    }

    public void setDecorations(MapDecorations decorations) {
        this.decorations = decorations;
    }

    public Fireworks getFireworks() {
        return fireworks;
    }

    public void setFireworks(Fireworks fireworks) {
        this.fireworks = fireworks;
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipes recipes) {
        this.recipes = recipes;
    }

    public PotionContents getPotionContents() {
        return potionContents;
    }

    public void setPotionContents(PotionContents potionContents) {
        this.potionContents = potionContents;
    }

    public JukeboxPlayable getJukeboxPlayable() {
        return jukeboxPlayable;
    }

    public void setJukeboxPlayable(JukeboxPlayable jukeboxPlayable) {
        this.jukeboxPlayable = jukeboxPlayable;
    }

    public int getOminousBottleAmplifier() {
        return ominousBottleAmplifier;
    }

    public void setOminousBottleAmplifier(int ominousBottleAmplifier) {
        this.ominousBottleAmplifier = ominousBottleAmplifier;
    }

    public SuspiciousStewEffects getEffects() {
        return effects;
    }

    public void setEffects(SuspiciousStewEffects effects) {
        this.effects = effects;
    }

    public WritableBookContent getWritableBookContent() {
        return writableBookContent;
    }

    public void setWritableBookContent(WritableBookContent writableBookContent) {
        this.writableBookContent = writableBookContent;
    }

    public static class builder {
        private AttributeModifiers modifiers;
        private Enchantments enchantments;
        private Lore lore;
        private int maxStackSize;
        private Rarity rarity;
        private int repairCost;
        private boolean fireResistant;
        private Food food;
        private boolean bucketEntityData;
        private Container container;
        private Bees bees;
        private BannerPatterns bannerPatterns;
        private int damage;
        private int maxDamage;
        private BundleContents contents;
        private ChargedProjectiles projectiles;
        private DebugStickState debugStickState;
        private boolean enchantmentGlintOverride;
        private PotDecorations potDecorations;
        private Tool tool;
        private StoredEnchantments storedEnchantments;
        private int mapColor;
        private MapDecorations decorations;
        private Fireworks fireworks;
        private Recipes recipes;
        private PotionContents potionContents;
        private JukeboxPlayable jukeboxPlayable;
        private int ominousBottleAmplifier;
        private SuspiciousStewEffects effects;
        private WritableBookContent writableBookContent;

        public builder() {
            this.modifiers = null;
            this.enchantments = null;
            this.lore = null;
            this.maxStackSize = 64;
            this.rarity = Rarity.COMMON;
            this.repairCost = 0;
            this.fireResistant = false;
            this.food = null;
            this.bucketEntityData = false;
            this.container = null;
            this.bees = null;
            this.bannerPatterns = null;
            this.damage = 0;
            this.maxDamage = -1;
            this.contents = null;
            this.projectiles = null;
            this.debugStickState = null;
            this.enchantmentGlintOverride = false;
            this.potDecorations = null;
            this.tool = null;
            this.storedEnchantments = null;
            this.mapColor = -1;
            this.decorations = null;
            this.fireworks = null;
            this.recipes = null;
            this.potionContents = null;
            this.jukeboxPlayable = null;
            this.ominousBottleAmplifier = -1;
            this.effects = null;
            this.writableBookContent = null;
        }

        public AttributeModifiers getModifiers() {
            return modifiers;
        }

        public Enchantments getEnchantments() {
            return enchantments;
        }

        public Lore getLore() {
            return lore;
        }

        public int getMaxStackSize() {
            return maxStackSize;
        }

        public Rarity getRarity() {
            return rarity;
        }

        public int getRepairCost() {
            return repairCost;
        }

        public boolean isFireResistant() {
            return fireResistant;
        }

        public Food getFood() {
            return food;
        }

        public boolean isBucketEntityData() {
            return bucketEntityData;
        }

        public Container getContainer() {
            return container;
        }

        public Bees getBees() {
            return bees;
        }

        public BannerPatterns getBannerPatterns() {
            return bannerPatterns;
        }

        public int getDamage() {
            return damage;
        }

        public int getMaxDamage() {
            return maxDamage;
        }

        public BundleContents getContents() {
            return contents;
        }

        public ChargedProjectiles getProjectiles() {
            return projectiles;
        }

        public DebugStickState getDebugStickState() {
            return debugStickState;
        }

        public boolean isEnchantmentGlintOverride() {
            return enchantmentGlintOverride;
        }

        public PotDecorations getPotDecorations() {
            return potDecorations;
        }

        public Tool getTool() {
            return tool;
        }

        public StoredEnchantments getStoredEnchantments() {
            return storedEnchantments;
        }

        public int getMapColor() {
            return mapColor;
        }

        public MapDecorations getDecorations() {
            return decorations;
        }

        public Fireworks getFireworks() {
            return fireworks;
        }

        public Recipes getRecipes() {
            return recipes;
        }

        public PotionContents getPotionContents() {
            return potionContents;
        }

        public JukeboxPlayable getJukeboxPlayable() {
            return jukeboxPlayable;
        }

        public int getOminousBottleAmplifier() {
            return ominousBottleAmplifier;
        }

        public SuspiciousStewEffects getEffects() {
            return effects;
        }

        public WritableBookContent getWritableBookContent() {
            return writableBookContent;
        }

        public builder setModifiers(AttributeModifiers modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public builder setEnchantments(Enchantments enchantments) {
            this.enchantments = enchantments;
            return this;
        }

        public builder setLore(Lore lore) {
            this.lore = lore;
            return this;
        }

        public builder setMaxStackSize(int maxStackSize) {
            this.maxStackSize = maxStackSize;
            return this;
        }

        public builder setRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public builder setRepairCost(int repairCost) {
            this.repairCost = repairCost;
            return this;
        }

        public builder setFireResistant(boolean fireResistant) {
            this.fireResistant = fireResistant;
            return this;
        }

        public builder setFood(Food food) {
            this.food = food;
            return this;
        }

        public builder setBucketEntityData(boolean bucketEntityData) {
            this.bucketEntityData = bucketEntityData;
            return this;
        }

        public builder setContainer(Container container) {
            this.container = container;
            return this;
        }

        public builder setBees(Bees bees) {
            this.bees = bees;
            return this;
        }

        public builder setBannerPatterns(BannerPatterns bannerPatterns) {
            this.bannerPatterns = bannerPatterns;
            return this;
        }

        public builder setDamage(int damage) {
            this.damage = damage;
            return this;
        }

        public builder setMaxDamage(int maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        public builder setContents(BundleContents contents) {
            this.contents = contents;
            return this;
        }

        public builder setProjectiles(ChargedProjectiles projectiles) {
            this.projectiles = projectiles;
            return this;
        }

        public builder setDebugStickState(DebugStickState debugStickState) {
            this.debugStickState = debugStickState;
            return this;
        }

        public builder setEnchantmentGlintOverride(boolean enchantmentGlintOverride) {
            this.enchantmentGlintOverride = enchantmentGlintOverride;
            return this;
        }

        public builder setPotDecorations(PotDecorations potDecorations) {
            this.potDecorations = potDecorations;
            return this;
        }

        public builder setTool(Tool tool) {
            this.tool = tool;
            return this;
        }

        public builder setStoredEnchantments(StoredEnchantments storedEnchantments) {
            this.storedEnchantments = storedEnchantments;
            return this;
        }

        public builder setMapColor(int mapColor) {
            this.mapColor = mapColor;
            return this;
        }

        public builder setDecorations(MapDecorations decorations) {
            this.decorations = decorations;
            return this;
        }

        public builder setFireworks(Fireworks fireworks) {
            this.fireworks = fireworks;
            return this;
        }

        public builder setRecipes(Recipes recipes) {
            this.recipes = recipes;
            return this;
        }

        public builder setPotionContents(PotionContents potionContents) {
            this.potionContents = potionContents;
            return this;
        }

        public builder setJukeboxPlayable(JukeboxPlayable jukeboxPlayable) {
            this.jukeboxPlayable = jukeboxPlayable;
            return this;
        }

        public builder setOminousBottleAmplifier(int ominousBottleAmplifier) {
            this.ominousBottleAmplifier = ominousBottleAmplifier;
            return this;
        }

        public builder setEffects(SuspiciousStewEffects effects) {
            this.effects = effects;
            return this;
        }

        public builder setWritableBookContent(WritableBookContent writableBookContent) {
            this.writableBookContent = writableBookContent;
            return this;
        }

        public Component build() {
            return new Component(this);
        }
    }
}
