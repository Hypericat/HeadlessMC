package client.game.items.component;

import client.game.items.component.components.*;

import java.util.HashMap;
import java.util.List;

public class Component {
    private AttributeModifiers modifiers;
    private EnchantmentsComponent enchantments;
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
    private CustomName customName;
    private ItemName itemName;
    private Unbreakable unbreakable;
    private DyedColor dyedColor;
    private MapID mapID;

    private HashMap<Integer, IComponent> components;

    public Component(Component component) {
        this.modifiers = (AttributeModifiers) component.modifiers.copy();
        this.enchantments = (EnchantmentsComponent) component.enchantments.copy();
        this.lore = (Lore) component.lore.copy();
        this.maxStackSize = (MaxStackSize) component.maxStackSize.copy();
        this.rarity = (Rarity) component.rarity.copy();
        this.repairCost = (RepairCost) component.repairCost.copy();
        this.fireResistant = (FireResistant) component.fireResistant.copy();
        this.food = (Food) component.food.copy();
        this.bucketEntityData = (BucketEntityData) component.bucketEntityData.copy();
        this.container = (Container) component.container.copy();
        this.bees = (Bees) component.bees.copy();
        this.bannerPatterns = (BannerPatterns) component.bannerPatterns.copy();
        this.damage = (Damage) component.damage.copy();
        this.maxDamage = (MaxDamage) component.maxDamage.copy();
        this.contents = (BundleContents) component.contents.copy();
        this.projectiles = (ChargedProjectiles) component.projectiles.copy();
        this.debugStickState = (DebugStickState) component.debugStickState.copy();
        this.enchantmentGlintOverride = (EnchantmentGlintOverride) component.enchantmentGlintOverride.copy();
        this.potDecorations = (PotDecorations) component.potDecorations.copy();
        this.tool = (Tool) component.tool.copy();
        this.storedEnchantments = (StoredEnchantments) component.storedEnchantments.copy();
        this.mapColor = (MapColor) component.mapColor.copy();
        this.decorations = (MapDecorations) component.decorations.copy();
        this.fireworks = (Fireworks) component.fireworks.copy();
        this.recipes = (Recipes) component.recipes.copy();
        this.potionContents = (PotionContents) component.potionContents.copy();
        this.jukeboxPlayable = (JukeboxPlayable) component.jukeboxPlayable.copy();
        this.ominousBottleAmplifier = (OminousBottleAmplifier) component.ominousBottleAmplifier.copy();
        this.effects = (SuspiciousStewEffects) component.effects.copy();
        this.writableBookContent = (WritableBookContent) component.writableBookContent.copy();
        this.customName = (CustomName) component.customName.copy();
        this.itemName = (ItemName) component.itemName.copy();
        this.unbreakable = (Unbreakable) component.unbreakable.copy();
        this.dyedColor = (DyedColor) component.dyedColor.copy();
        this.mapID = (MapID) component.mapID.copy();
        initComponents();
    }

    public Component(builder builder) {
        this.modifiers = builder.modifiers;
        this.enchantments = builder.enchantmentsComponent;
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
        this.customName = builder.customName;
        this.itemName = builder.itemName;
        this.unbreakable = builder.unbreakable;
        this.dyedColor = builder.dyedColor;
        this.mapID = builder.mapID;
        initComponents();
    }

    public void initComponents() {
        components = new HashMap<>();
        addToComponents(modifiers, "modifiers");
        addToComponents(enchantments, "enchantments");
        addToComponents(lore, "lore");
        addToComponents(maxStackSize, "maxStackSize");
        addToComponents(rarity, "rarity");
        addToComponents(repairCost, "repairCost");
        addToComponents(fireResistant, "fireResistant");
        addToComponents(food, "food");
        addToComponents(bucketEntityData, "bucketEntityData");
        addToComponents(container, "container");
        addToComponents(bees, "bees");
        addToComponents(bannerPatterns, "bannerPatterns");
        addToComponents(damage, "damage");
        addToComponents(maxDamage, "maxDamage");
        addToComponents(contents, "contents");
        addToComponents(projectiles, "projectiles");
        addToComponents(debugStickState, "debugStickState");
        addToComponents(enchantmentGlintOverride, "enchantmentGlintOverride");
        addToComponents(potDecorations, "potDecorations");
        addToComponents(tool, "tool");
        addToComponents(storedEnchantments, "storedEnchantments");
        addToComponents(mapColor, "mapColor");
        addToComponents(decorations, "decorations");
        addToComponents(fireworks, "fireworks");
        addToComponents(recipes, "recipes");
        addToComponents(potionContents, "potionContents");
        addToComponents(jukeboxPlayable, "jukeboxPlayable");
        addToComponents(ominousBottleAmplifier, "ominousBottleAmplifier");
        addToComponents(effects, "effects");
        addToComponents(writableBookContent, "writableBookContent");
        addToComponents(customName, "customName");
        addToComponents(itemName, "itemName");
        addToComponents(unbreakable, "unbreakable");
        addToComponents(dyedColor, "dyedColor");
        addToComponents(mapID, "mapID");
    }

    private void addToComponents(IComponent component, String name) {
        if (components.containsKey(component.getTypeID())) throw new RuntimeException("Duplicated component id : " + component.getTypeID() + " name : " + name);
        if (component.getTypeID() == 0) throw new RuntimeException();
        components.put(component.getTypeID(), component);
    }

    public List<IComponent> getAllComponents() {
        return components.values().stream().toList();
    }

    public IComponent getComponentByID(int id) {
        return components.get(id);
    }

    public MapID getMapID() {
        return mapID;
    }

    public void setMapID(MapID mapID) {
        this.mapID = mapID;
    }

    public DyedColor getDyedColor() {
        return dyedColor;
    }

    public void setDyedColor(DyedColor dyedColor) {
        this.dyedColor = dyedColor;
    }

    public Unbreakable getUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(Unbreakable unbreakable) {
        this.unbreakable = unbreakable;
    }

    public ItemName getItemName() {
        return itemName;
    }

    public void setItemName(ItemName itemName) {
        this.itemName = itemName;
    }

    public CustomName getCustomName() {
        return customName;
    }

    public void setCustomName(CustomName customName) {
        this.customName = customName;
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

    public EnchantmentsComponent getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(EnchantmentsComponent enchantmentsComponent) {
        this.enchantments = enchantmentsComponent;
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
        private EnchantmentsComponent enchantmentsComponent;
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
        private CustomName customName;
        private ItemName itemName;
        private Unbreakable unbreakable;
        private DyedColor dyedColor;
        private MapID mapID;

        public builder() {
            this.modifiers = new AttributeModifiers();
            this.enchantmentsComponent = new EnchantmentsComponent();
            this.lore = new Lore();
            this.maxStackSize = new MaxStackSize(64);
            this.rarity = Rarity.COMMON;
            this.repairCost = new RepairCost(0);
            this.fireResistant = new FireResistant();
            this.food = new Food();
            this.bucketEntityData = new BucketEntityData();
            this.container = new Container(null);
            this.bees = new Bees();
            this.bannerPatterns = new BannerPatterns();
            this.damage = new Damage(0);
            this.maxDamage = new MaxDamage(-1);
            this.contents = new BundleContents();
            this.projectiles = new ChargedProjectiles();
            this.debugStickState = new DebugStickState();
            this.enchantmentGlintOverride = new EnchantmentGlintOverride(-1);
            this.potDecorations = new PotDecorations();
            this.tool = new Tool();
            this.storedEnchantments = new StoredEnchantments();
            this.mapColor = new MapColor(-1);
            this.decorations = new MapDecorations();
            this.fireworks = new Fireworks();
            this.recipes = new Recipes();
            this.potionContents = new PotionContents();
            this.jukeboxPlayable = new JukeboxPlayable();
            this.ominousBottleAmplifier = new OminousBottleAmplifier(-1);
            this.effects = new SuspiciousStewEffects();
            this.writableBookContent = new WritableBookContent();
            this.itemName = new ItemName("");
            this.unbreakable = new Unbreakable();
            this.dyedColor = new DyedColor(-1);
            this.mapID = new MapID(-1);
            this.customName = new CustomName("");
        }

        public MapID getMapID() {
            return mapID;
        }

        public void setMapID(int mapID) {
            this.mapID = new MapID(mapID);
        }

        public DyedColor getDyedColor() {
            return dyedColor;
        }

        public void setDyedColor(DyedColor dyedColor) {
            this.dyedColor = dyedColor;
        }

        public Unbreakable getUnbreakable() {
            return unbreakable;
        }

        public void setUnbreakable(boolean unbreakable) {
            if (this.unbreakable == null == !unbreakable) return;
            if (unbreakable)
                this.unbreakable = new Unbreakable();
            else
                this.unbreakable = null;
        }

        public ItemName getItemName() {
            return itemName;
        }

        public void setItemName(String name) {
            this.itemName = new ItemName(name);
        }

        public CustomName getCustomName() {
            return customName;
        }

        public void setCustomName(String name) {
            this.customName = new CustomName(name);
        }

        public AttributeModifiers getModifiers() {
            return modifiers;
        }

        public EnchantmentsComponent getEnchantments() {
            return enchantmentsComponent;
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

        public builder setEnchantments(EnchantmentsComponent enchantmentsComponent) {
            this.enchantmentsComponent = enchantmentsComponent;
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
                this.enchantmentGlintOverride = new EnchantmentGlintOverride(1);
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
            this.mapColor = new MapColor(mapColor);
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
            this.ominousBottleAmplifier = new OminousBottleAmplifier(ominousBottleAmplifier);
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
