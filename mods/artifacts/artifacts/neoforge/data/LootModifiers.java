package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.loot.ConfigValueChance;
import artifacts.neoforge.loot.RollLootTableModifier;
import artifacts.registry.ModItems;
import artifacts.registry.ModLootTables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class LootModifiers extends GlobalLootModifierProvider {
   protected final List<LootModifiers.Builder> lootBuilders = new ArrayList<>();

   public LootModifiers(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, "artifacts");
   }

   private void addLoot() {
      for (ResourceKey<LootTable> lootTable : List.of(EntityType.COW.getDefaultLootTable(), EntityType.MOOSHROOM.getDefaultLootTable())) {
         this.lootBuilders
            .add(
               new LootModifiers.Builder(lootTable)
                  .lootPoolCondition(ConfigValueChance.everlastingBeefChance())
                  .lootModifierCondition(LootTableIdCondition.builder(lootTable.location()))
                  .parameterSet(LootContextParamSets.ENTITY)
                  .lootPoolCondition(LootItemKilledByPlayerCondition.killedByPlayer())
                  .everlastingBeef()
            );
      }

      for (ResourceKey<LootTable> lootTable : Arrays.asList(
         BuiltInLootTables.VILLAGE_DESERT_HOUSE, BuiltInLootTables.VILLAGE_PLAINS_HOUSE, BuiltInLootTables.VILLAGE_SAVANNA_HOUSE
      )) {
         this.builder(lootTable, 0.05F).item((Item)ModItems.VILLAGER_HAT.value());
      }

      for (ResourceKey<LootTable> lootTable : Arrays.asList(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, BuiltInLootTables.VILLAGE_TAIGA_HOUSE)) {
         this.builder(lootTable, 0.08F).item((Item)ModItems.VILLAGER_HAT.value()).item((Item)ModItems.SNOWSHOES.value());
      }

      this.builder(BuiltInLootTables.SPAWN_BONUS_CHEST, 1.0F).item((Item)ModItems.WHOOPEE_CUSHION.value());
      this.builder(BuiltInLootTables.VILLAGE_ARMORER, 0.1F)
         .item((Item)ModItems.STEADFAST_SPIKES.value())
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.RUNNING_SHOES.value())
         .item((Item)ModItems.VAMPIRIC_GLOVE.value());
      this.builder(BuiltInLootTables.VILLAGE_BUTCHER, 0.05F).item((Item)ModItems.EVERLASTING_BEEF.value());
      this.builder(BuiltInLootTables.VILLAGE_TANNERY, 0.2F)
         .item((Item)ModItems.UMBRELLA.value(), 3)
         .item((Item)ModItems.WHOOPEE_CUSHION.value(), 2)
         .item((Item)ModItems.KITTY_SLIPPERS.value())
         .item((Item)ModItems.BUNNY_HOPPERS.value())
         .item((Item)ModItems.SCARF_OF_INVISIBILITY.value())
         .item((Item)ModItems.ANGLERS_HAT.value());
      this.builder(BuiltInLootTables.VILLAGE_TEMPLE, 0.2F)
         .item((Item)ModItems.CROSS_NECKLACE.value())
         .item((Item)ModItems.ANTIDOTE_VESSEL.value())
         .item((Item)ModItems.CHARM_OF_SINKING.value())
         .item((Item)ModItems.WARP_DRIVE.value());
      this.builder(BuiltInLootTables.VILLAGE_TOOLSMITH, 0.15F).item((Item)ModItems.DIGGING_CLAWS.value()).item((Item)ModItems.POCKET_PISTON.value());
      this.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.1F).item((Item)ModItems.FERAL_CLAWS.value(), 2).item((Item)ModItems.CHARM_OF_SHRINKING.value());
      this.builder(BuiltInLootTables.ABANDONED_MINESHAFT, 0.3F)
         .item((Item)ModItems.ONION_RING.value(), 2)
         .item((Item)ModItems.NIGHT_VISION_GOGGLES.value())
         .item((Item)ModItems.PANIC_NECKLACE.value())
         .item((Item)ModItems.OBSIDIAN_SKULL.value())
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.DIGGING_CLAWS.value())
         .item((Item)ModItems.CLOUD_IN_A_BOTTLE.value())
         .item((Item)ModItems.VAMPIRIC_GLOVE.value())
         .item((Item)ModItems.AQUA_DASHERS.value())
         .item((Item)ModItems.PICKAXE_HEATER.value())
         .drinkingHat(1);
      this.builder(BuiltInLootTables.BASTION_HOGLIN_STABLE, 0.25F)
         .artifact(5)
         .item((Item)ModItems.BUNNY_HOPPERS.value(), 3)
         .item((Item)ModItems.FLAME_PENDANT.value(), 3)
         .item((Item)ModItems.COWBOY_HAT.value(), 3)
         .item((Item)ModItems.PICKAXE_HEATER.value(), 3)
         .item((Item)ModItems.WITHERED_BRACELET.value(), 3)
         .item((Item)ModItems.EVERLASTING_BEEF.value());
      this.builder(BuiltInLootTables.BASTION_TREASURE, 0.65F)
         .artifact(6)
         .item((Item)ModItems.GOLDEN_HOOK.value(), 3)
         .item((Item)ModItems.CROSS_NECKLACE.value(), 3)
         .item((Item)ModItems.FIRE_GAUNTLET.value(), 2)
         .item((Item)ModItems.STEADFAST_SPIKES.value())
         .item((Item)ModItems.PANIC_NECKLACE.value())
         .item((Item)ModItems.CRYSTAL_HEART.value())
         .item((Item)ModItems.ANTIDOTE_VESSEL.value());
      this.builder(BuiltInLootTables.BURIED_TREASURE, 0.25F)
         .item((Item)ModItems.SNORKEL.value(), 5)
         .item((Item)ModItems.FLIPPERS.value(), 5)
         .item((Item)ModItems.UMBRELLA.value(), 5)
         .item((Item)ModItems.GOLDEN_HOOK.value(), 5)
         .item((Item)ModItems.FERAL_CLAWS.value(), 3)
         .item((Item)ModItems.DIGGING_CLAWS.value(), 3)
         .item((Item)ModItems.KITTY_SLIPPERS.value(), 3)
         .item((Item)ModItems.BUNNY_HOPPERS.value(), 3)
         .item((Item)ModItems.LUCKY_SCARF.value(), 3)
         .item((Item)ModItems.AQUA_DASHERS.value(), 3)
         .item((Item)ModItems.ANGLERS_HAT.value(), 3)
         .drinkingHat(3);
      this.builder(BuiltInLootTables.DESERT_PYRAMID, 0.2F)
         .item((Item)ModItems.FLAME_PENDANT.value(), 2)
         .item((Item)ModItems.THORN_PENDANT.value(), 2)
         .item((Item)ModItems.WHOOPEE_CUSHION.value(), 2)
         .item((Item)ModItems.CHARM_OF_SINKING.value(), 2)
         .item((Item)ModItems.SHOCK_PENDANT.value())
         .item((Item)ModItems.UMBRELLA.value())
         .item((Item)ModItems.SCARF_OF_INVISIBILITY.value())
         .item((Item)ModItems.UNIVERSAL_ATTRACTOR.value())
         .item((Item)ModItems.VAMPIRIC_GLOVE.value())
         .item((Item)ModItems.ONION_RING.value());
      this.builder(BuiltInLootTables.END_CITY_TREASURE, 0.3F)
         .item((Item)ModItems.CHORUS_TOTEM.value(), 6)
         .item((Item)ModItems.HELIUM_FLAMINGO.value(), 4)
         .item((Item)ModItems.WARP_DRIVE.value(), 2)
         .item((Item)ModItems.CRYSTAL_HEART.value())
         .item((Item)ModItems.CLOUD_IN_A_BOTTLE.value());
      this.builder(BuiltInLootTables.JUNGLE_TEMPLE, 0.35F)
         .item((Item)ModItems.KITTY_SLIPPERS.value(), 2)
         .item((Item)ModItems.ROOTED_BOOTS.value(), 2)
         .item((Item)ModItems.BUNNY_HOPPERS.value())
         .item((Item)ModItems.ANGLERS_HAT.value());
      this.builder(BuiltInLootTables.NETHER_BRIDGE, 0.15F)
         .item((Item)ModItems.CROSS_NECKLACE.value())
         .item((Item)ModItems.NIGHT_VISION_GOGGLES.value())
         .item((Item)ModItems.POCKET_PISTON.value())
         .item((Item)ModItems.RUNNING_SHOES.value())
         .item((Item)ModItems.COWBOY_HAT.value())
         .item((Item)ModItems.CHARM_OF_SHRINKING.value())
         .item((Item)ModItems.STRIDER_SHOES.value())
         .item((Item)ModItems.WITHERED_BRACELET.value())
         .drinkingHat(1);
      this.builder(BuiltInLootTables.PILLAGER_OUTPOST, 0.25F)
         .item((Item)ModItems.PANIC_NECKLACE.value())
         .item((Item)ModItems.POCKET_PISTON.value())
         .item((Item)ModItems.STEADFAST_SPIKES.value())
         .item((Item)ModItems.POWER_GLOVE.value())
         .item((Item)ModItems.CROSS_NECKLACE.value())
         .item((Item)ModItems.SCARF_OF_INVISIBILITY.value())
         .item((Item)ModItems.CRYSTAL_HEART.value())
         .item((Item)ModItems.CLOUD_IN_A_BOTTLE.value())
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.ROOTED_BOOTS.value());
      this.builder(BuiltInLootTables.RUINED_PORTAL, 0.15F)
         .item((Item)ModItems.NIGHT_VISION_GOGGLES.value())
         .item((Item)ModItems.THORN_PENDANT.value())
         .item((Item)ModItems.FIRE_GAUNTLET.value())
         .item((Item)ModItems.POWER_GLOVE.value())
         .item((Item)ModItems.UNIVERSAL_ATTRACTOR.value())
         .item((Item)ModItems.OBSIDIAN_SKULL.value())
         .item((Item)ModItems.LUCKY_SCARF.value())
         .item((Item)ModItems.COWBOY_HAT.value())
         .item((Item)ModItems.CHARM_OF_SHRINKING.value())
         .item((Item)ModItems.STRIDER_SHOES.value());
      this.builder(BuiltInLootTables.SHIPWRECK_TREASURE, 0.15F)
         .item((Item)ModItems.GOLDEN_HOOK.value(), 3)
         .item((Item)ModItems.SNORKEL.value())
         .item((Item)ModItems.FLIPPERS.value())
         .item((Item)ModItems.SCARF_OF_INVISIBILITY.value())
         .item((Item)ModItems.STEADFAST_SPIKES.value())
         .item((Item)ModItems.UNIVERSAL_ATTRACTOR.value())
         .item((Item)ModItems.FERAL_CLAWS.value())
         .item((Item)ModItems.NIGHT_VISION_GOGGLES.value())
         .item((Item)ModItems.OBSIDIAN_SKULL.value())
         .item((Item)ModItems.RUNNING_SHOES.value())
         .item((Item)ModItems.CHARM_OF_SINKING.value());
      this.builder(BuiltInLootTables.SIMPLE_DUNGEON, 0.15F)
         .item((Item)ModItems.CHARM_OF_SHRINKING.value())
         .item((Item)ModItems.WARP_DRIVE.value())
         .item((Item)ModItems.WITHERED_BRACELET.value())
         .item((Item)ModItems.STRIDER_SHOES.value());
      this.builder(BuiltInLootTables.STRONGHOLD_CORRIDOR, 0.3F)
         .item((Item)ModItems.POWER_GLOVE.value())
         .item((Item)ModItems.ANTIDOTE_VESSEL.value())
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.LUCKY_SCARF.value())
         .item((Item)ModItems.AQUA_DASHERS.value())
         .item((Item)ModItems.HELIUM_FLAMINGO.value())
         .item((Item)ModItems.ROOTED_BOOTS.value())
         .item((Item)ModItems.PICKAXE_HEATER.value());
      this.builder(BuiltInLootTables.UNDERWATER_RUIN_BIG, 0.45F)
         .item((Item)ModItems.SNORKEL.value(), 3)
         .item((Item)ModItems.FLIPPERS.value(), 3)
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.LUCKY_SCARF.value())
         .item((Item)ModItems.FIRE_GAUNTLET.value())
         .item((Item)ModItems.CROSS_NECKLACE.value())
         .item((Item)ModItems.POWER_GLOVE.value())
         .item((Item)ModItems.CLOUD_IN_A_BOTTLE.value())
         .item((Item)ModItems.ANGLERS_HAT.value());
      this.builder(BuiltInLootTables.WOODLAND_MANSION, 0.4F).item((Item)ModItems.CHORUS_TOTEM.value(), 2).artifact(1);
      this.builder(BuiltInLootTables.IGLOO_CHEST, 0.3F)
         .item((Item)ModItems.SNOWSHOES.value(), 2)
         .item((Item)ModItems.VILLAGER_HAT.value())
         .item((Item)ModItems.LUCKY_SCARF.value());
      this.builder(BuiltInLootTables.ANCIENT_CITY_ICE_BOX, 0.2F).item((Item)ModItems.SNOWSHOES.value());
      this.builder(BuiltInLootTables.ANCIENT_CITY, 0.15F)
         .item((Item)ModItems.ROOTED_BOOTS.value())
         .item((Item)ModItems.PICKAXE_HEATER.value())
         .item((Item)ModItems.ONION_RING.value())
         .item((Item)ModItems.AQUA_DASHERS.value())
         .item((Item)ModItems.CHARM_OF_SINKING.value())
         .item((Item)ModItems.SHOCK_PENDANT.value())
         .item((Item)ModItems.HELIUM_FLAMINGO.value())
         .item((Item)ModItems.WARP_DRIVE.value());
      this.archaeologyBuilder(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY)
         .item((Item)ModItems.COWBOY_HAT.value())
         .item((Item)ModItems.OBSIDIAN_SKULL.value())
         .item((Item)ModItems.SNORKEL.value())
         .item((Item)ModItems.POWER_GLOVE.value())
         .item((Item)ModItems.KITTY_SLIPPERS.value())
         .item((Item)ModItems.NIGHT_VISION_GOGGLES.value())
         .item((Item)ModItems.SHOCK_PENDANT.value());
      this.archaeologyBuilder(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY)
         .item((Item)ModItems.CHARM_OF_SINKING.value())
         .item((Item)ModItems.UNIVERSAL_ATTRACTOR.value())
         .item((Item)ModItems.SUPERSTITIOUS_HAT.value())
         .item((Item)ModItems.UMBRELLA.value())
         .item((Item)ModItems.WARP_DRIVE.value())
         .drinkingHat(1);
      this.archaeologyBuilder(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY)
         .item((Item)ModItems.LUCKY_SCARF.value())
         .item((Item)ModItems.FIRE_GAUNTLET.value())
         .item((Item)ModItems.ANGLERS_HAT.value())
         .item((Item)ModItems.DIGGING_CLAWS.value())
         .item((Item)ModItems.ANTIDOTE_VESSEL.value())
         .item((Item)ModItems.WITHERED_BRACELET.value());
      this.archaeologyBuilder(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY)
         .item((Item)ModItems.AQUA_DASHERS.value())
         .item((Item)ModItems.ONION_RING.value())
         .item((Item)ModItems.RUNNING_SHOES.value())
         .item((Item)ModItems.BUNNY_HOPPERS.value())
         .item((Item)ModItems.VAMPIRIC_GLOVE.value())
         .item((Item)ModItems.STRIDER_SHOES.value());
      this.archaeologyBuilder(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)
         .item((Item)ModItems.ROOTED_BOOTS.value())
         .item((Item)ModItems.PICKAXE_HEATER.value())
         .item((Item)ModItems.AQUA_DASHERS.value())
         .item((Item)ModItems.SNOWSHOES.value())
         .item((Item)ModItems.STEADFAST_SPIKES.value())
         .item((Item)ModItems.VILLAGER_HAT.value())
         .item((Item)ModItems.CLOUD_IN_A_BOTTLE.value())
         .item((Item)ModItems.FERAL_CLAWS.value())
         .item((Item)ModItems.POCKET_PISTON.value())
         .item((Item)ModItems.WHOOPEE_CUSHION.value())
         .item((Item)ModItems.FLAME_PENDANT.value())
         .item((Item)ModItems.THORN_PENDANT.value())
         .item((Item)ModItems.CHARM_OF_SHRINKING.value());
   }

   protected LootModifiers.Builder builder(ResourceKey<LootTable> lootTable, float baseChance) {
      if (!ModLootTables.INJECTED_LOOT_TABLES.contains(lootTable)) {
         throw new IllegalArgumentException("Missing injected loot table: %s".formatted(lootTable));
      }

      LootModifiers.Builder builder = new LootModifiers.Builder(lootTable);
      builder.lootPoolCondition(ArtifactRarityAdjustedChance.adjustedChance(baseChance));
      builder.lootModifierCondition(LootTableIdCondition.builder(lootTable.location()));
      this.lootBuilders.add(builder);
      return builder;
   }

   protected LootModifiers.Builder archaeologyBuilder(ResourceKey<LootTable> lootTable) {
      if (!ModLootTables.ARCHAEOLOGY_LOOT_TABLES.contains(lootTable)) {
         throw new IllegalArgumentException("Missing archaeology loot table: %s".formatted(lootTable));
      }

      LootModifiers.Builder builder = new LootModifiers.Builder(lootTable).replace();
      builder.lootModifierCondition(LootTableIdCondition.builder(lootTable.location()));
      builder.lootModifierCondition(ConfigValueChance.archaeologyChance());
      this.lootBuilders.add(builder);
      return builder;
   }

   protected void start() {
      this.addLoot();

      for (LootModifiers.Builder lootBuilder : this.lootBuilders) {
         this.add("inject/" + lootBuilder.getName(), lootBuilder.build(), new ICondition[0]);
      }
   }

   protected static class Builder {
      private final ResourceKey<LootTable> lootTable;
      private final net.minecraft.world.level.storage.loot.LootPool.Builder lootPool = LootPool.lootPool();
      private final List<LootItemCondition> conditions;
      private boolean replace = false;
      private LootContextParamSet paramSet = LootContextParamSets.CHEST;

      private Builder(ResourceKey<LootTable> lootTable) {
         this.lootTable = lootTable;
         this.conditions = new ArrayList<>();
      }

      private RollLootTableModifier build() {
         return new RollLootTableModifier(
            this.conditions.toArray(new LootItemCondition[0]),
            Artifacts.key(Registries.LOOT_TABLE, "inject/" + this.lootTable.location().getPath()),
            this.replace
         );
      }

      protected net.minecraft.world.level.storage.loot.LootTable.Builder createLootTable() {
         return new net.minecraft.world.level.storage.loot.LootTable.Builder().withPool(this.lootPool);
      }

      public LootContextParamSet getParameterSet() {
         return this.paramSet;
      }

      protected String getName() {
         return this.lootTable.location().getPath();
      }

      private LootModifiers.Builder parameterSet(LootContextParamSet paramSet) {
         this.paramSet = paramSet;
         return this;
      }

      public LootModifiers.Builder replace() {
         this.replace = true;
         return this;
      }

      private LootModifiers.Builder lootPoolCondition(net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder condition) {
         this.lootPool.when(condition);
         return this;
      }

      private LootModifiers.Builder lootModifierCondition(net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder condition) {
         this.conditions.add(condition.build());
         return this;
      }

      private LootModifiers.Builder item(Item item, int weight) {
         this.lootPool.add(LootTables.item(item, weight));
         return this;
      }

      private LootModifiers.Builder item(Item item) {
         return this.item(item, 1);
      }

      private LootModifiers.Builder artifact(int weight) {
         this.lootPool.add(LootTables.artifact(weight));
         return this;
      }

      private LootModifiers.Builder drinkingHat(int weight) {
         this.lootPool.add(LootTables.drinkingHat(weight));
         return this;
      }

      private LootModifiers.Builder everlastingBeef() {
         this.lootPool
            .add(
               LootTables.item((Item)ModItems.EVERLASTING_BEEF.value(), 1)
                  .apply(
                     SmeltItemFunction.smelted()
                        .when(
                           LootItemEntityPropertyCondition.hasProperties(
                              EntityTarget.THIS,
                              net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
                                 .flags(net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags().setOnFire(true))
                           )
                        )
                  )
            );
         return this;
      }
   }
}
