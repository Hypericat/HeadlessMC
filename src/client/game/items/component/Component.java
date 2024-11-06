package client.game.items.component;

import client.game.items.component.components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component {
    private AttributeModifiers modifiers;
    private Enchantments enchantments;
    private Lore lore;
    private MaxStackSize maxStackSize;
    private Rarity rarity;
    private RepairCost repairCost;
    private FireResistant fireResistant;
    private Food food;
    private BucketEntityData bucketEntityData;
    private Container container;
    private Bees bees;
    private BannerPatterns bannerPatterns;
    private Damage damage;
    private MaxDamage maxDamage;
    private BundleContents contents;
    private ChargedProjectiles projectiles;
    private DebugStickState debugStickState;
    private EnchantmentGlintOverride enchantmentGlintOverride;
    private PotDecorations potDecorations;
    private Tool tool;
    private StoredEnchantments storedEnchantments;
    private MapColor mapColor;
    private MapDecorations decorations;
    private Fireworks fireworks;
    private Recipes recipes;
    private PotionContents potionContents;
    private JukeboxPlayable jukeboxPlayable;
    private OminousBottleAmplifier ominousBottleAmplifier;
    private SuspiciousStewEffects effects;
    private WritableBookContent writableBookContent;

    private HashMap<Integer, IComponent> components;

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
        initComponents();
    }

    public void initComponents() {
        components = new HashMap<>();
        addToComponents(modifiers);
        addToComponents(enchantments);
        addToComponents(lore);
        addToComponents(maxStackSize);
        addToComponents(rarity);
        addToComponents(repairCost);
        addToComponents(fireResistant);
        addToComponents(food);
        addToComponents(bucketEntityData);
        addToComponents(container);
        addToComponents(bees);
        addToComponents(bannerPatterns);
        addToComponents(damage);
        addToComponents(maxDamage);
        addToComponents(contents);
        addToComponents(projectiles);
        addToComponents(debugStickState);
        addToComponents(enchantmentGlintOverride);
        addToComponents(potDecorations);
        addToComponents(tool);
        addToComponents(storedEnchantments);
        addToComponents(mapColor);
        addToComponents(decorations);
        addToComponents(fireworks);
        addToComponents(recipes);
        addToComponents(potionContents);
        addToComponents(jukeboxPlayable);
        addToComponents(ominousBottleAmplifier);
        addToComponents(effects);
        addToComponents(writableBookContent);
    }

    private void addToComponents(IComponent component) {
        if (components.containsKey(component.getTypeID())) throw new RuntimeException();
        if (component.getTypeID() == 0) throw new RuntimeException();
        components.put(component.getTypeID(), component);
    }

    public List<IComponent> getAllComponents() {
        return components.values().stream().toList();
    }
    public IComponent getComponentByID(int id) {
        return components.get(id);
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

    public MaxStackSize getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize.setValue(maxStackSize);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public RepairCost getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(int repairCost) {
        this.repairCost.setValue(repairCost);
    }

    public boolean isFireResistant() {
        return fireResistant != null;
    }

    public void setFireResistant(boolean fireResistant) {
        if (isFireResistant() == fireResistant) return;
        this.fireResistant = (fireResistant ? new FireResistant() : null);
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public BucketEntityData isBucketEntityData() {
        return bucketEntityData;
    }

    public void setBucketEntityData(boolean bucketEntityData) {
        throw new IllegalStateException();
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

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage.setValue(damage);
    }

    public MaxDamage getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage.setValue(maxDamage);
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

    public EnchantmentGlintOverride getEnchantmentGlintOverride() {
        return enchantmentGlintOverride;
    }

    public void setEnchantmentGlintOverride(boolean enchantmentGlintOverride) {
        if (enchantmentGlintOverride)
            this.enchantmentGlintOverride.setValue(1);
        else
            this.enchantmentGlintOverride.setValue(0);
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

    public MapColor getMapColor() {
        return mapColor;
    }

    public void setMapColor(int mapColor) {
        this.mapColor.setValue(mapColor);
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

    public OminousBottleAmplifier getOminousBottleAmplifier() {
        return ominousBottleAmplifier;
    }

    public void setOminousBottleAmplifier(int ominousBottleAmplifier) {
        this.ominousBottleAmplifier.setValue(ominousBottleAmplifier);
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
        private MaxStackSize maxStackSize;
        private Rarity rarity;
        private RepairCost repairCost;
        private FireResistant fireResistant;
        private Food food;
        private BucketEntityData bucketEntityData;
        private Container container;
        private Bees bees;
        private BannerPatterns bannerPatterns;
        private Damage damage;
        private MaxDamage maxDamage;
        private BundleContents contents;
        private ChargedProjectiles projectiles;
        private DebugStickState debugStickState;
        private EnchantmentGlintOverride enchantmentGlintOverride;
        private PotDecorations potDecorations;
        private Tool tool;
        private StoredEnchantments storedEnchantments;
        private MapColor mapColor;
        private MapDecorations decorations;
        private Fireworks fireworks;
        private Recipes recipes;
        private PotionContents potionContents;
        private JukeboxPlayable jukeboxPlayable;
        private OminousBottleAmplifier ominousBottleAmplifier;
        private SuspiciousStewEffects effects;
        private WritableBookContent writableBookContent;

        public builder() {
            this.modifiers = null;
            this.enchantments = null;
            this.lore = null;
            this.maxStackSize = new MaxStackSize(64);
            this.rarity = Rarity.COMMON;
            this.repairCost = new RepairCost(0);
            this.fireResistant = null;
            this.food = null;
            this.bucketEntityData = null;
            this.container = null;
            this.bees = null;
            this.bannerPatterns = null;
            this.damage = new Damage(0);
            this.maxDamage = new MaxDamage(-1);
            this.contents = null;
            this.projectiles = null;
            this.debugStickState = null;
            this.enchantmentGlintOverride = new EnchantmentGlintOverride(0);
            this.potDecorations = null;
            this.tool = null;
            this.storedEnchantments = null;
            this.mapColor = new MapColor(-1);
            this.decorations = null;
            this.fireworks = null;
            this.recipes = null;
            this.potionContents = null;
            this.jukeboxPlayable = null;
            this.ominousBottleAmplifier = new OminousBottleAmplifier(-1);
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

        public MaxStackSize getMaxStackSize() {
            return maxStackSize;
        }

        public Rarity getRarity() {
            return rarity;
        }

        public RepairCost getRepairCost() {
            return repairCost;
        }

        public FireResistant isFireResistant() {
            return fireResistant;
        }

        public Food getFood() {
            return food;
        }

        public BucketEntityData isBucketEntityData() {
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

        public Damage getDamage() {
            return damage;
        }

        public MaxDamage getMaxDamage() {
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

        public EnchantmentGlintOverride isEnchantmentGlintOverride() {
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

        public MapColor getMapColor() {
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

        public OminousBottleAmplifier getOminousBottleAmplifier() {
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
            this.maxStackSize.setValue(maxStackSize);
            return this;
        }

        public builder setRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public builder setRepairCost(int repairCost) {
            this.repairCost.setValue(repairCost);
            return this;
        }

        public builder setFireResistant(boolean fireResistant) {
            if (fireResistant)
                this.fireResistant = new FireResistant();
            return this;
        }

        public builder setFood(Food food) {
            this.food = food;
            return this;
        }

        public builder setBucketEntityData(boolean bucketEntityData) {
            if (bucketEntityData)
                this.bucketEntityData = new BucketEntityData();
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
            this.damage.setValue(damage);
            return this;
        }

        public builder setMaxDamage(int maxDamage) {
            this.maxDamage.setValue(maxDamage);
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
            if (enchantmentGlintOverride)
                this.enchantmentGlintOverride.setValue(1);
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
            this.mapColor.setValue(mapColor);
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
            this.ominousBottleAmplifier.setValue(ominousBottleAmplifier);
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
