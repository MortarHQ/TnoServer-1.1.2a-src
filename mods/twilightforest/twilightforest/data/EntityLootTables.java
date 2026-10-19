package twilightforest.data;

import java.util.stream.Stream;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.block.TrophyBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.loot.LootingEnchantNumberProvider;
import twilightforest.loot.MultiplayerBasedAdditionLootFunction;
import twilightforest.loot.MultiplayerBasedNumberProvider;
import twilightforest.loot.TFLootTables;
import twilightforest.loot.conditions.IsMinionCondition;

public class EntityLootTables extends EntityLootSubProvider {
   protected EntityLootTables(Provider provider) {
      super(FeatureFlags.REGISTRY.allFlags(), provider);
   }

   public void generate() {
      this.add((EntityType)TFEntities.ADHERENT.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.LICH_MINION.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.LOYAL_ZOMBIE.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.PLATEAU_BOSS.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.HARBINGER_CUBE.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.MOSQUITO_SWARM.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.PINCH_BEETLE.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.QUEST_RAM.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.ROVING_CUBE.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.SQUIRREL.get(), this.emptyLootTable());
      this.add((EntityType)TFEntities.DWARF_RABBIT.get(), this.fromEntityLootTable(EntityType.RABBIT));
      this.add((EntityType)TFEntities.HEDGE_SPIDER.get(), this.fromEntityLootTable(EntityType.SPIDER));
      this.add((EntityType)TFEntities.HOSTILE_WOLF.get(), this.fromEntityLootTable(EntityType.WOLF));
      this.add((EntityType)TFEntities.KING_SPIDER.get(), this.fromEntityLootTable(EntityType.SPIDER));
      this.add((EntityType)TFEntities.MIST_WOLF.get(), this.fromEntityLootTable(EntityType.WOLF));
      this.add((EntityType)TFEntities.REDCAP_SAPPER.get(), this.fromEntityLootTable((EntityType<?>)TFEntities.REDCAP.get()));
      this.add((EntityType)TFEntities.SWARM_SPIDER.get(), this.fromEntityLootTable(EntityType.SPIDER));
      this.add((EntityType)TFEntities.CARMINITE_BROODLING.get(), this.fromEntityLootTable(EntityType.SPIDER));
      this.add((EntityType)TFEntities.CARMINITE_GHASTGUARD.get(), this.fromEntityLootTable(EntityType.GHAST));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), this.fromEntityLootTable(EntityType.SHEEP));
      this.add((EntityType)TFEntities.RISING_ZOMBIE.get(), this.fromEntityLootTable(EntityType.ZOMBIE));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_BLACK, sheepLootTableBuilderWithDrop(Blocks.BLACK_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_BLUE, sheepLootTableBuilderWithDrop(Blocks.BLUE_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_BROWN, sheepLootTableBuilderWithDrop(Blocks.BROWN_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_CYAN, sheepLootTableBuilderWithDrop(Blocks.CYAN_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_GRAY, sheepLootTableBuilderWithDrop(Blocks.GRAY_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_GREEN, sheepLootTableBuilderWithDrop(Blocks.GREEN_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_LIGHT_BLUE, sheepLootTableBuilderWithDrop(Blocks.LIGHT_BLUE_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_LIGHT_GRAY, sheepLootTableBuilderWithDrop(Blocks.LIGHT_GRAY_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_LIME, sheepLootTableBuilderWithDrop(Blocks.LIME_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_MAGENTA, sheepLootTableBuilderWithDrop(Blocks.MAGENTA_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_ORANGE, sheepLootTableBuilderWithDrop(Blocks.ORANGE_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_PINK, sheepLootTableBuilderWithDrop(Blocks.PINK_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_PURPLE, sheepLootTableBuilderWithDrop(Blocks.PURPLE_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_RED, sheepLootTableBuilderWithDrop(Blocks.RED_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_WHITE, sheepLootTableBuilderWithDrop(Blocks.WHITE_WOOL));
      this.add((EntityType)TFEntities.BIGHORN_SHEEP.get(), TFLootTables.BIGHORN_SHEEP_YELLOW, sheepLootTableBuilderWithDrop(Blocks.YELLOW_WOOL));
      this.add(
         (EntityType)TFEntities.FIRE_BEETLE.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.GUNPOWDER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.ARMORED_GIANT.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.GIANT_SWORD.get()))
                  .when(LootItemKilledByPlayerCondition.killedByPlayer())
            )
      );
      this.add(
         (EntityType)TFEntities.GIANT_MINER.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.GIANT_PICKAXE.get()))
                  .when(LootItemKilledByPlayerCondition.killedByPlayer())
            )
      );
      this.add(
         (EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARMOR_SHARD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.CARMINITE_GHASTLING.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(NestedLootTable.lootTableReference(EntityType.GHAST.getDefaultLootTable()))
                  .when(IsMinionCondition.builder(true))
            )
      );
      this.add(
         (EntityType)TFEntities.BOAR.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.PORKCHOP)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(
                           SetNameFunction.setName(
                                 Component.translatable("item.twilightforest.boarkchop").withStyle(Style.EMPTY.withItalic(false)), Target.ITEM_NAME
                              )
                              .when(LootItemRandomChanceCondition.randomChance(0.002F))
                              .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.HELMET_CRAB.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARMOR_SHARD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.COD)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
                  .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.5F, 0.1F))
            )
      );
      this.add(
         (EntityType)TFEntities.UPPER_GOBLIN_KNIGHT.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARMOR_SHARD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARMOR_SHARD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.WRAITH.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.GLOWSTONE_DUST)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.REDCAP.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.COAL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.YETI.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARCTIC_FUR.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.WINTER_WOLF.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ARCTIC_FUR.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.TINY_BIRD.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.FEATHER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.PENGUIN.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.FEATHER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.ICE_CRYSTAL.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.SNOWBALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.SNOWBALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.STABLE_ICE_CORE.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.SNOWBALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.SNOW_GUARDIAN.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.SNOWBALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.RAVEN.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.RAVEN_FEATHER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.TOWERWOOD_BORER.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.BORER_ESSENCE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.SKELETON_DRUID.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.BONE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.TORCHBERRIES.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.DEER.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.LEATHER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.RAW_VENISON.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.KOBOLD.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.WHEAT)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.GOLD_NUGGET)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
                  .when(LootItemKilledByPlayerCondition.killedByPlayer())
            )
      );
      this.add(
         (EntityType)TFEntities.MAZE_SLIME.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(TFItems.MAZE_SLIME_BALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .when(this.killedByFrog().invert())
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.MAZE_SLIME_BALL)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .when(this.killedByFrog())
                  )
                  .when(LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, Builder.entity().subPredicate(SlimePredicate.sized(Ints.exactly(1)))))
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.CHARM_OF_KEEPING_1.get()))
                  .when(LootItemKilledByPlayerCondition.killedByPlayer())
                  .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.015F, 0.005F))
            )
      );
      this.add(
         (EntityType)TFEntities.MINOTAUR.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.RAW_MEEF.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.MAZE_MAP_FOCUS.get()))
                  .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))
            )
      );
      this.add(
         (EntityType)TFEntities.CARMINITE_GOLEM.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.IRON_INGOT)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFBlocks.TOWERWOOD.get()))
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                  .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
            )
      );
      this.add(
         (EntityType)TFEntities.SLIME_BEETLE.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.SLIME_BALL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.TROLL.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.MAGIC_BEANS.get()))
                  .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))
            )
      );
      this.add(
         (EntityType)TFEntities.DEATH_TOME.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(Items.PAPER))
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)))
                  .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(Items.WRITABLE_BOOK).setWeight(2).setQuality(3))
                  .add(LootItem.lootTableItem(Items.BOOK).setWeight(19))
                  .add(NestedLootTable.lootTableReference(TFLootTables.DEATH_TOME_BOOKS).setWeight(1))
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .when(LootItemKilledByPlayerCondition.killedByPlayer())
                  .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.005F))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.MAGIC_MAP_FOCUS.get()))
            )
      );
      this.add(
         (EntityType)TFEntities.DEATH_TOME.get(),
         TFLootTables.DEATH_TOME_HURT,
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(NestedLootTable.lootTableReference(BuiltInLootTables.EMPTY))
                  .add(LootItem.lootTableItem(Items.PAPER))
            )
      );
      this.add(
         (EntityType)TFEntities.DEATH_TOME.get(),
         TFLootTables.DEATH_TOME_BOOKS,
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.BOOK)
                        .setWeight(32)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(1.0F, 10.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.BOOK)
                        .setWeight(8)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(11.0F, 20.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.BOOK)
                        .setWeight(4)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(21.0F, 30.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.BOOK)
                        .setWeight(1)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(31.0F, 40.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.NAGA.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("naga_scales")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.NAGA_SCALE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 11.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(2.0F, 4.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("naga_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.NAGA_TROPHY.get()).asItem()))
            )
      );
      this.add(
         (EntityType)TFEntities.LICH.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("lich_scepters")
                  .setRolls(MultiplayerBasedNumberProvider.rollsForPlayers(UniformGenerator.between(-2.0F, 1.0F), ConstantValue.exactly(1.0F)))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.TWILIGHT_SCEPTER.get()))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.LIFEDRAIN_SCEPTER.get()))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.ZOMBIE_SCEPTER.get()))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.FORTIFICATION_SCEPTER.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .name("lich_armor")
                  .setRolls(MultiplayerBasedNumberProvider.rollsForPlayers(UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(2.0F, 4.0F)))
                  .add(
                     LootItem.lootTableItem(Items.GOLDEN_SWORD)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(10.0F, 40.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.GOLDEN_HELMET)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(10.0F, 40.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(10.0F, 40.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(10.0F, 40.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(10.0F, 40.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("lich_pearls")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.ENDER_PEARL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(0.0F, 2.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("lich_bones")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.BONE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 9.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(LootPool.lootPool().name("lich_trophy").setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TFBlocks.LICH_TROPHY)))
            .withPool(
               LootPool.lootPool()
                  .name("essence")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(TFItems.EXANIMATE_ESSENCE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.MINOSHROOM.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("minoshroom_stroganoff")
                  .setRolls(MultiplayerBasedNumberProvider.rollsForPlayers(UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(2.0F, 5.0F)))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.MEEF_STROGANOFF.get())
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("minoshroom_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.MINOSHROOM_TROPHY.get()).asItem()))
            )
            .withPool(
               LootPool.lootPool()
                  .name("minoshroom_axe")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.DIAMOND_MINOTAUR_AXE.get())
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(-2.0F, 1.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.HYDRA.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("hydra_chop")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.HYDRA_CHOP.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 35.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(5.0F, 10.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("hydra_blood")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.FIERY_BLOOD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(7.0F, 10.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 2.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(1.0F, 3.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("hydra_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.HYDRA_TROPHY.get()).asItem()))
            )
      );
      this.add(
         (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("knight_phantom_weapon")
                  .setRolls(
                     new BinomialDistributionGenerator(
                        LootingEnchantNumberProvider.applyLootingLevelTo(
                           this.registries, MultiplayerBasedNumberProvider.rollsForPlayers(ConstantValue.exactly(1.5F), ConstantValue.exactly(4.0F))
                        ),
                        ConstantValue.exactly(0.17F)
                     )
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.KNIGHTMETAL_SWORD)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(20.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.KNIGHTMETAL_PICKAXE)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(20.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.KNIGHTMETAL_AXE)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(20.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(
                     new BinomialDistributionGenerator(
                        LootingEnchantNumberProvider.applyLootingLevelTo(
                           this.registries, MultiplayerBasedNumberProvider.rollsForPlayers(ConstantValue.exactly(1.0F), ConstantValue.exactly(2.0F))
                        ),
                        ConstantValue.exactly(0.17F)
                     )
                  )
                  .name("knight_phantom_armor")
                  .add(
                     LootItem.lootTableItem(TFItems.PHANTOM_HELMET)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(20.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.PHANTOM_CHESTPLATE)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(20.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .setRolls(
                     new BinomialDistributionGenerator(
                        LootingEnchantNumberProvider.applyLootingLevelTo(
                           this.registries, MultiplayerBasedNumberProvider.rollsForPlayers(ConstantValue.exactly(0.5F), ConstantValue.exactly(1.0F))
                        ),
                        ConstantValue.exactly(0.17F)
                     )
                  )
                  .name("knight_phantom_rare_armor")
                  .add(
                     LootItem.lootTableItem(TFItems.PHANTOM_HELMET)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(30.0F)))
                  )
                  .add(
                     LootItem.lootTableItem(TFItems.PHANTOM_CHESTPLATE)
                        .apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(30.0F)))
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.UR_GHAST.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("ur_ghast_carminite")
                  .setRolls(ConstantValue.exactly(4.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.CARMINITE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(1.0F, 3.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("ur_ghast_tears")
                  .setRolls(ConstantValue.exactly(2.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.FIERY_TEARS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(1.0F, 2.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("ur_ghast_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.UR_GHAST_TROPHY.get()).asItem()))
            )
      );
      this.add(
         (EntityType)TFEntities.ALPHA_YETI.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("alpha_yeti_fur")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ALPHA_YETI_FUR.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(1.0F, 3.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("alpha_yeti_bombs")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)TFItems.ICE_BOMB.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(MultiplayerBasedAdditionLootFunction.addForAllParticipatingPlayers(UniformGenerator.between(0.0F, 2.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("alpha_yeti_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.ALPHA_YETI_TROPHY.get()).asItem()))
            )
      );
      this.add(
         (EntityType)TFEntities.SNOW_QUEEN.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .name("snow_queen_bows")
                  .setRolls(MultiplayerBasedNumberProvider.rollsForPlayers(UniformGenerator.between(-2.0F, 1.0F), ConstantValue.exactly(1.0F)))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.TRIPLE_BOW.get()))
                  .add(LootItem.lootTableItem((ItemLike)TFItems.SEEKER_BOW.get()))
            )
            .withPool(
               LootPool.lootPool()
                  .name("snow_queen_ice")
                  .setRolls(UniformGenerator.between(1.0F, 4.0F))
                  .add(
                     LootItem.lootTableItem(Blocks.PACKED_ICE.asItem())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                  )
            )
            .withPool(
               LootPool.lootPool()
                  .name("snow_queen_snowballs")
                  .setRolls(UniformGenerator.between(2.0F, 5.0F))
                  .add(LootItem.lootTableItem(Items.SNOWBALL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(16.0F))))
            )
            .withPool(
               LootPool.lootPool()
                  .name("snow_queen_trophy")
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(((TrophyBlock)TFBlocks.SNOW_QUEEN_TROPHY.get()).asItem()))
            )
      );
      this.add(
         (EntityType)TFEntities.QUEST_RAM.get(),
         TFLootTables.QUESTING_RAM_REWARDS,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem((ItemLike)TFItems.CRUMBLE_HORN.get())))
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(Items.BUNDLE)
                        .apply(
                           SetContainerContents.setContents(ContainerComponentManipulators.BUNDLE_CONTENTS)
                              .withEntry(LootItem.lootTableItem((ItemLike)TFBlocks.QUEST_RAM_TROPHY.value()))
                              .withEntry(NestedLootTable.lootTableReference(TFLootTables.QUESTING_RAM_REWARD_BLOCKS))
                        )
                  )
            )
      );
      this.add(
         (EntityType)TFEntities.QUEST_RAM.get(),
         TFLootTables.QUESTING_RAM_REWARD_BLOCKS,
         LootTable.lootTable()
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.COAL_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.IRON_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.COPPER_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.LAPIS_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.GOLD_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.DIAMOND_BLOCK)))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.EMERALD_BLOCK)))
      );
   }

   public net.minecraft.world.level.storage.loot.LootTable.Builder emptyLootTable() {
      return LootTable.lootTable();
   }

   public net.minecraft.world.level.storage.loot.LootTable.Builder fromEntityLootTable(EntityType<?> parent) {
      return LootTable.lootTable()
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(parent.getDefaultLootTable())));
   }

   private static net.minecraft.world.level.storage.loot.LootTable.Builder sheepLootTableBuilderWithDrop(ItemLike wool) {
      return LootTable.lootTable()
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(wool)))
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
   }

   protected Stream<EntityType<?>> getKnownEntityTypes() {
      return TFEntities.ENTITIES.getEntries().stream().map(DeferredHolder::value);
   }
}
