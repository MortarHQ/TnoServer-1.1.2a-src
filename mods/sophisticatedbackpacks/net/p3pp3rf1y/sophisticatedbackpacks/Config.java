package net.p3pp3rf1y.sophisticatedbackpacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilteredUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy.AlchemyUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.battery.BatteryUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.AutoCookingUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.ICookingUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.magnet.MagnetUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.pump.PumpUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.voiding.VoidUpgradeConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.xppump.XpPumpUpgradeConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
   private static final String REGISTRY_NAME_MATCHER = "([a-z0-9_.-]+:[a-z0-9_/.-]+)";
   private static final String MAX_UPGRADES_MATCHER = "([a-z0-9_/.-]+\\|\\d+)";
   public static final Config.Server SERVER;
   public static final ModConfigSpec SERVER_SPEC;
   public static final Config.Common COMMON;
   public static final ModConfigSpec COMMON_SPEC;

   private Config() {
   }

   static {
      Pair<Config.Server, ModConfigSpec> serverSpec = new Builder().configure(Config.Server::new);
      SERVER_SPEC = (ModConfigSpec)serverSpec.getRight();
      SERVER = (Config.Server)serverSpec.getLeft();
      Pair<Config.Common, ModConfigSpec> commonSpec = new Builder().configure(Config.Common::new);
      COMMON_SPEC = (ModConfigSpec)commonSpec.getRight();
      COMMON = (Config.Common)commonSpec.getLeft();
   }

   public static class Common {
      public final BooleanValue chestLootEnabled;

      Common(Builder builder) {
         builder.comment("Common Settings").push("common");
         this.chestLootEnabled = builder.comment("Turns on/off loot added to various vanilla chest loot tables").define("chestLootEnabled", true);
         builder.pop();
      }
   }

   public static class Server {
      public final Config.Server.DisallowedItems disallowedItems;
      public final Config.Server.NoInteractionBlocks noInteractionBlocks;
      public final Config.Server.NoConnectionBlocks noConnectionBlocks;
      public final Config.Server.BackpackConfig leatherBackpack;
      public final Config.Server.BackpackConfig copperBackpack;
      public final Config.Server.BackpackConfig ironBackpack;
      public final Config.Server.BackpackConfig goldBackpack;
      public final Config.Server.BackpackConfig diamondBackpack;
      public final Config.Server.BackpackConfig netheriteBackpack;
      public final FilteredUpgradeConfig compactingUpgrade;
      public final FilteredUpgradeConfig advancedCompactingUpgrade;
      public final FilteredUpgradeConfig depositUpgrade;
      public final FilteredUpgradeConfig advancedDepositUpgrade;
      public final FilteredUpgradeConfig feedingUpgrade;
      public final FilteredUpgradeConfig advancedFeedingUpgrade;
      public final FilteredUpgradeConfig filterUpgrade;
      public final FilteredUpgradeConfig advancedFilterUpgrade;
      public final MagnetUpgradeConfig magnetUpgrade;
      public final MagnetUpgradeConfig advancedMagnetUpgrade;
      public final FilteredUpgradeConfig pickupUpgrade;
      public final FilteredUpgradeConfig advancedPickupUpgrade;
      public final FilteredUpgradeConfig refillUpgrade;
      public final FilteredUpgradeConfig advancedRefillUpgrade;
      public final FilteredUpgradeConfig restockUpgrade;
      public final FilteredUpgradeConfig advancedRestockUpgrade;
      public final VoidUpgradeConfig voidUpgrade;
      public final VoidUpgradeConfig advancedVoidUpgrade;
      public final CookingUpgradeConfig smeltingUpgrade;
      public final CookingUpgradeConfig smokingUpgrade;
      public final CookingUpgradeConfig blastingUpgrade;
      public final AutoCookingUpgradeConfig autoSmeltingUpgrade;
      public final AutoCookingUpgradeConfig autoSmokingUpgrade;
      public final AutoCookingUpgradeConfig autoBlastingUpgrade;
      public final Config.Server.InceptionUpgradeConfig inceptionUpgrade;
      public final Config.Server.EntityBackpackAdditionsConfig entityBackpackAdditions;
      public final BooleanValue itemFluidHandlerEnabled;
      public final BooleanValue allowOpeningOtherPlayerBackpacks;
      public final BooleanValue itemDisplayDisabled;
      public final BooleanValue tickDedupeLogicDisabled;
      public final BooleanValue canBePlacedInContainerItems;
      public final FilteredUpgradeConfig toolSwapperUpgrade;
      public final TankUpgradeConfig tankUpgrade;
      public final BatteryUpgradeConfig batteryUpgrade;
      public final StackUpgradeConfig stackUpgrade;
      public final PumpUpgradeConfig pumpUpgrade;
      public final XpPumpUpgradeConfig xpPumpUpgrade;
      public final JukeboxUpgradeConfig advancedJukeboxUpgrade;
      public final AlchemyUpgradeConfig alchemyUpgrade;
      public final AlchemyUpgradeConfig advancedAlchemyUpgrade;
      public final Config.Server.NerfsConfig nerfsConfig;
      public final Config.Server.MaxUgradesPerStorageConfig maxUpgradesPerStorage;

      public void initListeners(IEventBus modBus) {
         modBus.addListener(this::onConfigReload);
         modBus.addListener(this::onConfigLoad);
      }

      public void onConfigReload(Reloading event) {
         this.clearCache();
      }

      public void onConfigLoad(Loading event) {
         this.clearCache();
      }

      private void clearCache() {
         this.disallowedItems.initialized = false;
         this.stackUpgrade.clearNonStackableItems();
         this.maxUpgradesPerStorage.clearCache();
         this.nerfsConfig.cachedEffect = null;
      }

      Server(Builder builder) {
         builder.comment("Server Settings").push("server");
         this.disallowedItems = new Config.Server.DisallowedItems(builder);
         this.noInteractionBlocks = new Config.Server.NoInteractionBlocks(builder);
         this.noConnectionBlocks = new Config.Server.NoConnectionBlocks(builder);
         this.leatherBackpack = new Config.Server.BackpackConfig(builder, "Leather", 27, 1);
         this.copperBackpack = new Config.Server.BackpackConfig(builder, "Copper", 45, 1);
         this.ironBackpack = new Config.Server.BackpackConfig(builder, "Iron", 54, 2);
         this.goldBackpack = new Config.Server.BackpackConfig(builder, "Gold", 81, 3);
         this.diamondBackpack = new Config.Server.BackpackConfig(builder, "Diamond", 108, 5);
         this.netheriteBackpack = new Config.Server.BackpackConfig(builder, "Netherite", 120, 7);
         this.compactingUpgrade = new FilteredUpgradeConfig(builder, "Compacting Upgrade", "compactingUpgrade", 9, 3);
         this.advancedCompactingUpgrade = new FilteredUpgradeConfig(builder, "Advanced Compacting Upgrade", "advancedCompactingUpgrade", 16, 4);
         this.depositUpgrade = new FilteredUpgradeConfig(builder, "Deposit Upgrade", "depositUpgrade", 9, 3);
         this.advancedDepositUpgrade = new FilteredUpgradeConfig(builder, "Advanced Deposit Upgrade", "advancedDepositUpgrade", 16, 4);
         this.feedingUpgrade = new FilteredUpgradeConfig(builder, "Feeding Upgrade", "feedingUpgrade", 9, 3);
         this.advancedFeedingUpgrade = new FilteredUpgradeConfig(builder, "Advanced Feeding Upgrade", "advancedFeedingUpgrade", 16, 4);
         this.filterUpgrade = new FilteredUpgradeConfig(builder, "Filter Upgrade", "filterUpgrade", 9, 3);
         this.advancedFilterUpgrade = new FilteredUpgradeConfig(builder, "Advanced Filter Upgrade", "advancedFilterUpgrade", 16, 4);
         this.magnetUpgrade = new MagnetUpgradeConfig(builder, "Magnet Upgrade", "magnetUpgrade", 9, 3, 3);
         this.advancedMagnetUpgrade = new MagnetUpgradeConfig(builder, "Advanced Magnet Upgrade", "advancedMagnetUpgrade", 16, 4, 5);
         this.pickupUpgrade = new FilteredUpgradeConfig(builder, "Pickup Upgrade", "pickupUpgrade", 9, 3);
         this.advancedPickupUpgrade = new FilteredUpgradeConfig(builder, "Advanced Pickup Upgrade", "advancedPickupUpgrade", 16, 4);
         this.refillUpgrade = new FilteredUpgradeConfig(builder, "Refill Upgrade", "refillUpgrade", 6, 3);
         this.advancedRefillUpgrade = new FilteredUpgradeConfig(builder, "Advanced Refill Upgrade", "advancedRefillUpgrade", 12, 4);
         this.restockUpgrade = new FilteredUpgradeConfig(builder, "Restock Upgrade", "restockUpgrade", 9, 3);
         this.advancedRestockUpgrade = new FilteredUpgradeConfig(builder, "Advanced Restock Upgrade", "advancedRestockUpgrade", 16, 4);
         this.voidUpgrade = new VoidUpgradeConfig(builder, "Void Upgrade", "voidUpgrade", 9, 3);
         this.advancedVoidUpgrade = new VoidUpgradeConfig(builder, "Advanced Void Upgrade", "advancedVoidUpgrade", 16, 4);
         this.stackUpgrade = new StackUpgradeConfig(builder);
         this.smeltingUpgrade = CookingUpgradeConfig.getInstance(builder, "Smelting Upgrade", "smeltingUpgrade");
         this.smokingUpgrade = CookingUpgradeConfig.getInstance(builder, "Smoking Upgrade", "smokingUpgrade");
         this.blastingUpgrade = CookingUpgradeConfig.getInstance(builder, "Blasting Upgrade", "blastingUpgrade");
         this.autoSmeltingUpgrade = new AutoCookingUpgradeConfig(builder, "Auto-Smelting Upgrade", "autoSmeltingUpgrade");
         this.autoSmokingUpgrade = new AutoCookingUpgradeConfig(builder, "Auto-Smoking Upgrade", "autoSmokingUpgrade");
         this.autoBlastingUpgrade = new AutoCookingUpgradeConfig(builder, "Auto-Blasting Upgrade", "autoBlastingUpgrade");
         this.inceptionUpgrade = new Config.Server.InceptionUpgradeConfig(builder);
         this.toolSwapperUpgrade = new FilteredUpgradeConfig(builder, "Tool Swapper Upgrade", "toolSwapperUpgrade", 8, 4);
         this.tankUpgrade = new TankUpgradeConfig(builder);
         this.batteryUpgrade = new BatteryUpgradeConfig(builder);
         this.pumpUpgrade = new PumpUpgradeConfig(builder);
         this.xpPumpUpgrade = new XpPumpUpgradeConfig(builder);
         this.advancedJukeboxUpgrade = new JukeboxUpgradeConfig(builder, "Advanced Jukebox Upgrade", "advancedJukeboxUpgrade", 12);
         this.alchemyUpgrade = new AlchemyUpgradeConfig(builder, "Alchemy Upgrade", "alchemyUpgrade", 4);
         this.advancedAlchemyUpgrade = new AlchemyUpgradeConfig(builder, "Advanced Alchemy Upgrade", "advancedAlchemyUpgrade", 8);
         this.entityBackpackAdditions = new Config.Server.EntityBackpackAdditionsConfig(builder);
         this.nerfsConfig = new Config.Server.NerfsConfig(builder);
         this.maxUpgradesPerStorage = new Config.Server.MaxUgradesPerStorageConfig(
            builder, Map.of(StackUpgradeItem.UPGRADE_GROUP.name(), 3, ICookingUpgrade.UPGRADE_GROUP.name(), 1, JukeboxUpgradeItem.UPGRADE_GROUP.name(), 1)
         );
         this.itemFluidHandlerEnabled = builder.comment(
               "Turns on/off item fluid handler of backpack in its item form. There are some dupe bugs caused by default fluid handling implementation that manifest when backpack is drained / filled in its item form in another mod's tank and the only way to prevent them is disallowing drain/fill in item form altogether"
            )
            .define("itemFluidHandlerEnabled", true);
         this.allowOpeningOtherPlayerBackpacks = builder.comment(
               "Determines whether player can right click on backpack that another player is wearing to open it. If off will turn off that capability for everyone and remove related settings from backpack."
            )
            .define("allowOpeningOtherPlayerBackpacks", true);
         this.itemDisplayDisabled = builder.comment(
               "Allows disabling item display settings. Primarily in cases where custom backpack model doesn't support showing the item. (Requires game restart to take effect)"
            )
            .define("itemDisplayDisabled", false);
         this.tickDedupeLogicDisabled = builder.comment(
               "Allows disabling logic that dedupes backpacks with the same UUID in players' inventory. This is here to allow turning off the logic just in case it would be causing performance issues."
            )
            .define("tickDedupeLogicDisabled", false);
         this.canBePlacedInContainerItems = builder.comment(
               "Determines if backpacks can be placed in container items (those that check for return value of canFitInsideContainerItems)"
            )
            .define("canBePlacedInContainerItems", false);
         builder.pop();
      }

      public static class BackpackConfig {
         public final IntValue inventorySlotCount;
         public final IntValue upgradeSlotCount;

         public BackpackConfig(Builder builder, String backpackPrefix, int inventorySlotCountDefault, int upgradeSlotCountDefault) {
            builder.comment(backpackPrefix + " Backpack Settings").push(backpackPrefix.toLowerCase(Locale.ENGLISH) + "Backpack");
            this.inventorySlotCount = builder.comment("Number of inventory slots in the backpack")
               .defineInRange("inventorySlotCount", inventorySlotCountDefault, 1, 144);
            this.upgradeSlotCount = builder.comment("Number of upgrade slots in the backpack")
               .defineInRange("upgradeSlotCount", upgradeSlotCountDefault, 0, 10);
            builder.pop();
         }
      }

      public static class DisallowedItems {
         private final BooleanValue containerItemsDisallowed;
         private final ConfigValue<List<String>> disallowedItemsList;
         private boolean initialized = false;
         private Set<Item> disallowedItemsSet = null;

         DisallowedItems(Builder builder) {
            this.disallowedItemsList = builder.comment("List of items that are not allowed to be put in backpacks - e.g. \"minecraft:shulker_box\"")
               .define("disallowedItems", new ArrayList());
            this.containerItemsDisallowed = builder.comment(
                  "Determines if container items (those that override canFitInsideContainerItems to false) are able to fit in backpacks"
               )
               .define("containerItemsDisallowed", false);
         }

         public boolean isItemDisallowed(Item item) {
            if (!Config.SERVER_SPEC.isLoaded()) {
               return true;
            }

            if (!this.initialized) {
               this.loadDisallowedSet();
            }

            return Boolean.TRUE.equals(this.containerItemsDisallowed.get()) && !(item instanceof BackpackItem) && !item.canFitInsideContainerItems()
               ? true
               : this.disallowedItemsSet.contains(item);
         }

         private void loadDisallowedSet() {
            this.initialized = true;
            this.disallowedItemsSet = new HashSet<>();

            for (String disallowedItemName : (List)this.disallowedItemsList.get()) {
               ResourceLocation registryName = ResourceLocation.parse(disallowedItemName);
               BuiltInRegistries.ITEM.getOptional(registryName).ifPresent(this.disallowedItemsSet::add);
            }
         }
      }

      public static class EntityBackpackAdditionsConfig {
         private static final String ENTITY_LOOT_MATCHER = "([a-z0-9_.-]+:[a-z0-9_/.-]+)\\|(null|[a-z0-9_.-]+:[a-z0-9/_.-]+)";
         public final DoubleValue chance;
         public final BooleanValue addLoot;
         public final BooleanValue buffWithPotionEffects;
         public final BooleanValue buffHealth;
         public final BooleanValue equipWithArmor;
         public final BooleanValue playJukebox;
         public final BooleanValue dropToFakePlayers;
         public final DoubleValue backpackDropChance;
         public final DoubleValue lootingChanceIncreasePerLevel;
         public final IntValue leatherWeight;
         public final IntValue copperWeight;
         public final IntValue ironWeight;
         public final IntValue goldWeight;
         public final IntValue diamondWeight;
         public final IntValue netheriteWeight;
         public final IntValue minBackpackTierMidDifficulty;
         public final IntValue minBackpackTierHighDifficulty;
         public final BooleanValue localDifficultyEffectsBackpackSpawns;
         public final ConfigValue<List<? extends String>> entityLootTableList;
         public final ConfigValue<List<? extends String>> discBlockList;
         @Nullable
         private Map<EntityType<?>, ResourceLocation> entityLootTables = null;

         public EntityBackpackAdditionsConfig(Builder builder) {
            builder.comment("Settings for Spawning Entities with Backpack").push("entityBackpackAdditions");
            this.chance = builder.comment("Chance of an entity spawning with Backpack").defineInRange("chance", 0.01, 0.0, 1.0);
            this.addLoot = builder.comment("Turns on/off addition of loot into backpacks").define("addLoot", true);
            this.buffWithPotionEffects = builder.comment(
                  "Turns on/off buffing the entity that wears backpack with potion effects. These are scaled based on how much loot is added."
               )
               .define("buffWithPotionEffects", true);
            this.buffHealth = builder.comment(
                  "Turns on/off buffing the entity that wears backpack with additional health. Health is scaled based on backpack tier the mob wears."
               )
               .define("buffHealth", true);
            this.equipWithArmor = builder.comment(
                  "Turns on/off equiping the entity that wears backpack with armor. What armor material and how enchanted is scaled based on backpack tier the mob wears."
               )
               .define("equipWithArmor", true);
            this.entityLootTableList = builder.comment(
                  "Map of entities that can spawn with backpack and related loot tables (if adding a loot is enabled) in format of \"EntityRegistryName|LootTableName\""
               )
               .defineList(
                  "entityLootTableList",
                  this::getDefaultEntityLootTableList,
                  mapping -> ((String)mapping).matches("([a-z0-9_.-]+:[a-z0-9_/.-]+)\\|(null|[a-z0-9_.-]+:[a-z0-9/_.-]+)")
               );
            this.discBlockList = builder.comment("List of music discs that are not supposed to be played by entities")
               .defineList("discBlockList", this::getDefaultDiscBlockList, mapping -> ((String)mapping).matches("([a-z0-9_.-]+:[a-z0-9_/.-]+)"));
            this.playJukebox = builder.comment("Turns on/off a chance that the entity that wears backpack gets jukebox upgrade and plays a music disc.")
               .define("playJukebox", true);
            this.dropToFakePlayers = builder.comment(
                  "Determines whether backpack drops to fake players if killed by them in addition to real ones that it always drops to"
               )
               .define("dropToFakePlayers", false);
            this.backpackDropChance = builder.comment("Chance of mob dropping backpack when killed by player")
               .defineInRange("backpackDropChance", 0.5, 0.0, 1.0);
            this.lootingChanceIncreasePerLevel = builder.comment("Chance increase per looting level of mob dropping backpack")
               .defineInRange("lootingChanceIncreasePerLevel", 0.15, 0.0, 0.3);
            this.leatherWeight = builder.comment("Weight of selecting a Leather Backpack when an entity spawns with a backpack")
               .defineInRange("leatherWeight", 625, 0, 9999);
            this.copperWeight = builder.comment("Weight of selecting a Copper Backpack when an entity spawns with a backpack")
               .defineInRange("copperWeight", 250, 0, 9999);
            this.ironWeight = builder.comment("Weight of selecting a Iron Backpack when an entity spawns with a backpack")
               .defineInRange("ironWeight", 125, 0, 9999);
            this.goldWeight = builder.comment("Weight of selecting a Gold Backpack when an entity spawns with a backpack")
               .defineInRange("goldWeight", 25, 0, 9999);
            this.diamondWeight = builder.comment("Weight of selecting a Diamond Backpack when an entity spawns with a backpack")
               .defineInRange("diamondWeight", 5, 0, 9999);
            this.netheriteWeight = builder.comment("Weight of selecting a Netherite Backpack when an entity spawns with a backpack")
               .defineInRange("netheriteWeight", 1, 0, 9999);
            this.minBackpackTierMidDifficulty = builder.comment(
                  "Minimum tier of backpack mobs are equipped with at mid local difficulty (above 1/3 of max, 0 is leather)"
               )
               .defineInRange("minBackpackTierMidDifficulty", 1, 0, 6);
            this.minBackpackTierHighDifficulty = builder.comment(
                  "Minimum tier of backpack mobs are equipped with at high local difficulty (above 2/3 of max, 0 is leather)"
               )
               .defineInRange("minBackpackTierHighDifficulty", 2, 0, 6);
            this.localDifficultyEffectsBackpackSpawns = builder.comment(
                  "If local difficulty is taken into consideration when determining the difficulty. If local difficulty is high enough then it will use difficulty settings above"
               )
               .define("localDifficultyEffectsBackpackSpawns", true);
            builder.pop();
         }

         public Optional<ResourceLocation> getLootTableName(EntityType<?> entityType) {
            if (this.entityLootTables == null) {
               this.initEntityLootTables();
            }

            return Optional.ofNullable(this.entityLootTables.get(entityType));
         }

         public boolean canWearBackpack(EntityType<?> entityType) {
            if (this.entityLootTables == null) {
               this.initEntityLootTables();
            }

            return this.entityLootTables.containsKey(entityType);
         }

         private void initEntityLootTables() {
            this.entityLootTables = new HashMap<>();

            for (String mapping : (List)this.entityLootTableList.get()) {
               String[] entityLoot = mapping.split("\\|");
               if (entityLoot.length >= 2) {
                  String entityRegistryName = entityLoot[0];
                  String lootTableName = entityLoot[1];
                  BuiltInRegistries.ENTITY_TYPE
                     .getOptional(ResourceLocation.parse(entityRegistryName))
                     .ifPresent(
                        entityType -> this.entityLootTables
                           .put((EntityType<?>)entityType, lootTableName.equals("null") ? null : ResourceLocation.parse(lootTableName))
                     );
               }
            }
         }

         private List<String> getDefaultDiscBlockList() {
            List<String> ret = new ArrayList<>();
            ret.add("botania:record_gaia_1");
            ret.add("botania:record_gaia_2");
            return ret;
         }

         private List<String> getDefaultEntityLootTableList() {
            return this.getDefaultEntityLootMapping()
               .entrySet()
               .stream()
               .map(e -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getKey()) + "|" + e.getValue().location())
               .collect(Collectors.toList());
         }

         private Map<EntityType<?>, ResourceKey<LootTable>> getDefaultEntityLootMapping() {
            Map<EntityType<?>, ResourceKey<LootTable>> mapping = new LinkedHashMap<>();
            mapping.put(EntityType.CREEPER, BuiltInLootTables.DESERT_PYRAMID);
            mapping.put(EntityType.DROWNED, BuiltInLootTables.SHIPWRECK_TREASURE);
            mapping.put(EntityType.ENDERMAN, BuiltInLootTables.END_CITY_TREASURE);
            mapping.put(EntityType.EVOKER, BuiltInLootTables.WOODLAND_MANSION);
            mapping.put(EntityType.HUSK, BuiltInLootTables.DESERT_PYRAMID);
            mapping.put(EntityType.PIGLIN, BuiltInLootTables.BASTION_BRIDGE);
            mapping.put(EntityType.PIGLIN_BRUTE, BuiltInLootTables.BASTION_TREASURE);
            mapping.put(EntityType.PILLAGER, BuiltInLootTables.PILLAGER_OUTPOST);
            mapping.put(EntityType.SKELETON, BuiltInLootTables.SIMPLE_DUNGEON);
            mapping.put(EntityType.STRAY, BuiltInLootTables.IGLOO_CHEST);
            mapping.put(EntityType.VEX, BuiltInLootTables.WOODLAND_MANSION);
            mapping.put(EntityType.VINDICATOR, BuiltInLootTables.WOODLAND_MANSION);
            mapping.put(EntityType.WITCH, BuiltInLootTables.BURIED_TREASURE);
            mapping.put(EntityType.WITHER_SKELETON, BuiltInLootTables.NETHER_BRIDGE);
            mapping.put(EntityType.ZOMBIE, BuiltInLootTables.SIMPLE_DUNGEON);
            mapping.put(EntityType.ZOMBIE_VILLAGER, BuiltInLootTables.VILLAGE_ARMORER);
            mapping.put(EntityType.ZOMBIFIED_PIGLIN, BuiltInLootTables.BASTION_OTHER);
            return mapping;
         }
      }

      public static class InceptionUpgradeConfig {
         public final BooleanValue upgradesUseInventoriesOfBackpacksInBackpack;
         public final BooleanValue upgradesInContainedBackpacksAreFunctional;

         public InceptionUpgradeConfig(Builder builder) {
            builder.comment("Inception Upgrade Settings").push("inceptionUpgrade");
            this.upgradesUseInventoriesOfBackpacksInBackpack = builder.comment(
                  "Allows / Disallows backpack upgrades to work with inventories of Backpacks in the Backpack with Inception Upgrade"
               )
               .define("upgradesUseInventoriesOfBackpacksInBackpack", true);
            this.upgradesInContainedBackpacksAreFunctional = builder.comment(
                  "Allows / Disallows upgrades to be functional even when they are in Backpacks in the inventory of Backpack with Inception Upgrade"
               )
               .define("upgradesInContainedBackpacksAreFunctional", true);
            builder.pop();
         }
      }

      public static class MaxUgradesPerStorageConfig implements IUpgradeCountLimitConfig {
         private final ConfigValue<List<? extends String>> maxUpgradesPerStorageList;
         @Nullable
         private Map<String, Integer> maxUpgradesPerStorage = null;

         protected MaxUgradesPerStorageConfig(Builder builder, Map<String, Integer> defaultUpgradesPerStorage) {
            this.maxUpgradesPerStorageList = builder.comment(
                  "Maximum number of upgrades of type per backpack in format of \"UpgradeRegistryName[or UpgradeGroup]|MaxNumber\""
               )
               .defineList(
                  "maxUpgradesPerStorage", this.convertToList(defaultUpgradesPerStorage), mapping -> ((String)mapping).matches("([a-z0-9_/.-]+\\|\\d+)")
               );
         }

         private List<String> convertToList(Map<String, Integer> defaultUpgradesPerStorage) {
            return defaultUpgradesPerStorage.entrySet().stream().map(e -> e.getKey() + "|" + e.getValue()).collect(Collectors.toList());
         }

         public void clearCache() {
            this.maxUpgradesPerStorage = null;
         }

         public int getMaxUpgradesPerStorage(String storageType, @Nullable ResourceLocation upgradeRegistryName) {
            if (this.maxUpgradesPerStorage == null) {
               this.initMaxUpgradesPerStorage();
            }

            return upgradeRegistryName == null ? Integer.MAX_VALUE : this.maxUpgradesPerStorage.getOrDefault(upgradeRegistryName.getPath(), Integer.MAX_VALUE);
         }

         private void initMaxUpgradesPerStorage() {
            this.maxUpgradesPerStorage = new HashMap<>();

            for (String mapping : (List)this.maxUpgradesPerStorageList.get()) {
               String[] upgradeMax = mapping.split("\\|");
               if (upgradeMax.length >= 2) {
                  String name = upgradeMax[0];
                  int max = Integer.parseInt(upgradeMax[1]);
                  this.maxUpgradesPerStorage.put(name, max);
               }
            }
         }

         public int getMaxUpgradesInGroupPerStorage(String storageType, UpgradeGroup upgradeGroup) {
            if (this.maxUpgradesPerStorage == null) {
               this.initMaxUpgradesPerStorage();
            }

            return this.maxUpgradesPerStorage.getOrDefault(upgradeGroup.name(), Integer.MAX_VALUE);
         }
      }

      public static class NerfsConfig {
         public final BooleanValue tooManyBackpacksSlowness;
         public final IntValue maxNumberOfBackpacks;
         public final DoubleValue slownessLevelsPerAdditionalBackpack;
         public final BooleanValue onlyWornBackpackTriggersUpgrades;
         public final ConfigValue<String> nerfEffect;
         @Nullable
         private Holder<MobEffect> cachedEffect = null;

         public NerfsConfig(Builder builder) {
            builder.push("nerfs");
            this.tooManyBackpacksSlowness = builder.comment("Determines if too many backpacks in player's inventory cause slowness to the player")
               .define("tooManyBackpacksSlowness", false);
            this.maxNumberOfBackpacks = builder.comment("Maximum number of backpacks in player's inventory that will not cause slowness")
               .defineInRange("maxNumberOfBackpacks", 3, 1, 27);
            this.slownessLevelsPerAdditionalBackpack = builder.comment(
                  "Ratio of slowness levels per every backpack above the maximum number allowed. (number of backpacks above the max gets multiplied by this number and ceiled)"
               )
               .defineInRange("slownessLevelsPerAdditionalBackpack", 1.0, 0.1, 5.0);
            this.onlyWornBackpackTriggersUpgrades = builder.comment(
                  "Determines if active upgrades will only work in the backpack that's worn by the player. Active upgrades are for example magnet, pickup, cooking, feeding upgrades."
               )
               .define("onlyWornBackpackTriggersUpgrades", false);
            this.nerfEffect = builder.comment(
                  "Effect that is applied to player when they have too many backpacks. Can be any effect including modded ones like overencumbered effect some mods have."
               )
               .define("nerfEffect", "minecraft:slowness", s -> s instanceof String str && str.matches("([a-z0-9_.-]+:[a-z0-9_/.-]+)"));
            builder.pop();
         }

         public Holder<MobEffect> getEffect() {
            if (this.cachedEffect == null) {
               this.cachedEffect = BuiltInRegistries.MOB_EFFECT
                  .getHolder(ResourceLocation.parse((String)this.nerfEffect.get()))
                  .map(h -> (Holder<MobEffect>)h)
                  .orElse(MobEffects.MOVEMENT_SLOWDOWN);
            }

            return this.cachedEffect;
         }
      }

      public static class NoConnectionBlocks {
         private final ConfigValue<List<? extends String>> noConnectionBlocksList;
         private final BooleanValue allBlockConnectionsDisallowed;
         private boolean initialized = false;
         private Set<Block> noConnnectionBlocksSet = null;

         NoConnectionBlocks(Builder builder) {
            this.noConnectionBlocksList = builder.comment(
                  "List of blocks that are not allowed to connect to backpacks - e.g. \"refinedstorage:external_storage\""
               )
               .defineList("noConnectionBlocks", new ArrayList(), mapping -> ((String)mapping).matches("([a-z0-9_.-]+:[a-z0-9_/.-]+)"));
            this.allBlockConnectionsDisallowed = builder.comment("If true, disallows all blocks from connecting to backpacks")
               .define("allBlockConnectionsDisallowed", false);
         }

         public boolean isBlockConnectionDisallowed(Block block) {
            if (!Config.SERVER_SPEC.isLoaded()) {
               return true;
            }

            if ((Boolean)this.allBlockConnectionsDisallowed.get()) {
               return true;
            }

            if (!this.initialized) {
               this.loadDisallowedSet();
            }

            return this.noConnnectionBlocksSet.contains(block);
         }

         private void loadDisallowedSet() {
            this.initialized = true;
            this.noConnnectionBlocksSet = new HashSet<>();

            for (String disallowedItemName : (List)this.noConnectionBlocksList.get()) {
               ResourceLocation registryName = ResourceLocation.parse(disallowedItemName);
               if (BuiltInRegistries.BLOCK.containsKey(registryName)) {
                  this.noConnnectionBlocksSet.add((Block)BuiltInRegistries.BLOCK.get(registryName));
               }
            }
         }
      }

      public static class NoInteractionBlocks {
         private final ConfigValue<List<String>> noInteractionBlocksList;
         private boolean initialized = false;
         private Set<Block> noInteractionBlocksSet = null;

         NoInteractionBlocks(Builder builder) {
            this.noInteractionBlocksList = builder.comment(
                  "List of blocks that inventory interaction upgrades can't interact with - e.g. \"minecraft:shulker_box\""
               )
               .define("noInteractionBlocks", new ArrayList());
         }

         public boolean isBlockInteractionDisallowed(Block block) {
            if (!Config.SERVER_SPEC.isLoaded()) {
               return true;
            }

            if (!this.initialized) {
               this.loadDisallowedSet();
            }

            return this.noInteractionBlocksSet.contains(block);
         }

         private void loadDisallowedSet() {
            this.initialized = true;
            this.noInteractionBlocksSet = new HashSet<>();

            for (String disallowedItemName : (List)this.noInteractionBlocksList.get()) {
               ResourceLocation registryName = ResourceLocation.parse(disallowedItemName);
               if (BuiltInRegistries.BLOCK.containsKey(registryName)) {
                  this.noInteractionBlocksSet.add((Block)BuiltInRegistries.BLOCK.get(registryName));
               }
            }
         }
      }
   }
}
