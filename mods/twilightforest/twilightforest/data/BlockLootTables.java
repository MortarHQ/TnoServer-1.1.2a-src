package twilightforest.data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction.Source;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.block.BrazierBlock;
import twilightforest.block.ClimbableHollowLogBlock;
import twilightforest.block.FallenLeavesBlock;
import twilightforest.block.FireflySpawnerBlock;
import twilightforest.block.HorizontalHollowLogBlock;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.OminousCandleBlock;
import twilightforest.block.RopeBlock;
import twilightforest.block.TFBushBlock;
import twilightforest.block.TorchberryPlantBlock;
import twilightforest.block.TrophyBlock;
import twilightforest.enums.HollowLogVariants;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.loot.TFLootTables;

public class BlockLootTables extends BlockLootSubProvider {
   private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
   private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
   private static final net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder HAS_SHEARS = CanItemPerformAbility.canItemPerformAbility(
      ItemAbilities.SHEARS_DIG
   );

   public BlockLootTables(Provider provider) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
   }

   protected void generate() {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      this.dropSelf((Block)TFBlocks.TOWERWOOD.get());
      this.dropSelf((Block)TFBlocks.ENCASED_TOWERWOOD.get());
      this.dropSelf((Block)TFBlocks.CRACKED_TOWERWOOD.get());
      this.dropSelf((Block)TFBlocks.MOSSY_TOWERWOOD.get());
      this.dropSelf((Block)TFBlocks.CARMINITE_BUILDER.get());
      this.dropSelf((Block)TFBlocks.GHAST_TRAP.get());
      this.dropSelf((Block)TFBlocks.CARMINITE_REACTOR.get());
      this.dropSelf((Block)TFBlocks.REAPPEARING_BLOCK.get());
      this.dropSelf((Block)TFBlocks.VANISHING_BLOCK.get());
      this.dropSelf((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get());
      this.dropSelf((Block)TFBlocks.FIREFLY.get());
      this.dropSelf((Block)TFBlocks.CICADA.get());
      this.dropSelf((Block)TFBlocks.MOONWORM.get());
      this.dropSelf((Block)TFBlocks.TROPHY_PEDESTAL.get());
      this.dropSelf((Block)TFBlocks.AURORA_BLOCK.get());
      this.dropSelf((Block)TFBlocks.AURORA_PILLAR.get());
      this.add((Block)TFBlocks.AURORA_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.AURORA_SLAB.get()));
      this.dropWhenSilkTouch((Block)TFBlocks.AURORALIZED_GLASS.get());
      this.dropSelf((Block)TFBlocks.UNDERBRICK.get());
      this.dropSelf((Block)TFBlocks.CRACKED_UNDERBRICK.get());
      this.dropSelf((Block)TFBlocks.MOSSY_UNDERBRICK.get());
      this.dropSelf((Block)TFBlocks.UNDERBRICK_FLOOR.get());
      this.dropSelf((Block)TFBlocks.THORN_ROSE.get());
      this.add((Block)TFBlocks.THORN_LEAVES.get(), this.silkAndStick((Block)TFBlocks.THORN_LEAVES.get(), Items.STICK, RARE_SAPLING_DROP_RATES));
      this.add(
         (Block)TFBlocks.BEANSTALK_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.BEANSTALK_LEAVES.get(), (ItemLike)TFItems.MAGIC_BEANS.get(), RARE_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.DEADROCK.get());
      this.dropSelf((Block)TFBlocks.CRACKED_DEADROCK.get());
      this.dropSelf((Block)TFBlocks.WEATHERED_DEADROCK.get());
      this.dropSelf((Block)TFBlocks.TROLLSTEINN.get());
      this.dropWhenSilkTouch((Block)TFBlocks.WISPY_CLOUD.get());
      this.dropSelf((Block)TFBlocks.FLUFFY_CLOUD.get());
      this.dropSelf((Block)TFBlocks.RAINY_CLOUD.get());
      this.dropSelf((Block)TFBlocks.SNOWY_CLOUD.get());
      this.dropSelf((Block)TFBlocks.GIANT_COBBLESTONE.get());
      this.dropSelf((Block)TFBlocks.GIANT_LOG.get());
      this.dropSelf((Block)TFBlocks.GIANT_LEAVES.get());
      this.dropSelf((Block)TFBlocks.GIANT_OBSIDIAN.get());
      this.add((Block)TFBlocks.UBEROUS_SOIL.get(), this.createSingleItemTable(Blocks.DIRT));
      this.dropSelf((Block)TFBlocks.HUGE_STALK.get());
      this.add((Block)TFBlocks.HUGE_MUSHGLOOM.get(), this.createMushroomBlockDrop((Block)TFBlocks.HUGE_MUSHGLOOM.get(), (ItemLike)TFBlocks.MUSHGLOOM.get()));
      this.add(
         (Block)TFBlocks.HUGE_MUSHGLOOM_STEM.get(), this.createMushroomBlockDrop((Block)TFBlocks.HUGE_MUSHGLOOM_STEM.get(), (ItemLike)TFBlocks.MUSHGLOOM.get())
      );
      this.add((Block)TFBlocks.TROLLVIDR.get(), createShearsOnlyDrop((ItemLike)TFBlocks.TROLLVIDR.get()));
      this.add((Block)TFBlocks.UNRIPE_TROLLBER.get(), createShearsOnlyDrop((ItemLike)TFBlocks.UNRIPE_TROLLBER.get()));
      this.add(
         (Block)TFBlocks.TROLLBER.get(),
         this.createShearsDispatchTable(
            (Block)TFBlocks.TROLLBER.get(),
            LootItem.lootTableItem((ItemLike)TFItems.TORCHBERRIES.get())
               .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))
               .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
         )
      );
      this.dropSelf((Block)TFBlocks.HUGE_LILY_PAD.get());
      this.dropSelf((Block)TFBlocks.HUGE_WATER_LILY.get());
      this.dropSelf((Block)TFBlocks.CASTLE_BRICK.get());
      this.dropSelf((Block)TFBlocks.WORN_CASTLE_BRICK.get());
      this.dropSelf((Block)TFBlocks.CRACKED_CASTLE_BRICK.get());
      this.dropSelf((Block)TFBlocks.MOSSY_CASTLE_BRICK.get());
      this.dropSelf((Block)TFBlocks.THICK_CASTLE_BRICK.get());
      this.dropSelf((Block)TFBlocks.CASTLE_ROOF_TILE.get());
      this.dropSelf((Block)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
      this.dropSelf((Block)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get());
      this.dropSelf((Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      this.dropSelf((Block)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      this.dropSelf((Block)TFBlocks.CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      this.dropSelf((Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get());
      this.dropSelf((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get());
      this.dropSelf((Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get());
      this.dropSelf((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get());
      this.dropSelf((Block)TFBlocks.CINDER_FURNACE.get());
      this.dropSelf((Block)TFBlocks.CINDER_LOG.get());
      this.dropSelf((Block)TFBlocks.CINDER_WOOD.get());
      this.dropSelf((Block)TFBlocks.VIOLET_CASTLE_DOOR.get());
      this.dropSelf((Block)TFBlocks.YELLOW_CASTLE_DOOR.get());
      this.dropSelf((Block)TFBlocks.PINK_CASTLE_DOOR.get());
      this.dropSelf((Block)TFBlocks.BLUE_CASTLE_DOOR.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get());
      this.dropSelf((Block)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get());
      this.dropSelf((Block)TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get());
      this.dropSelf((Block)TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get());
      this.dropSelf((Block)TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get());
      this.dropSelf((Block)TFBlocks.KNIGHTMETAL_BLOCK.get());
      this.dropSelf((Block)TFBlocks.IRONWOOD_BLOCK.get());
      this.dropSelf((Block)TFBlocks.FIERY_BLOCK.get());
      this.dropSelf((Block)TFBlocks.STEELEAF_BLOCK.get());
      this.dropSelf((Block)TFBlocks.ARCTIC_FUR_BLOCK.get());
      this.dropSelf((Block)TFBlocks.CARMINITE_BLOCK.get());
      this.dropSelf((Block)TFBlocks.MAZESTONE.get());
      this.dropSelf((Block)TFBlocks.MAZESTONE_BRICK.get());
      this.dropSelf((Block)TFBlocks.CUT_MAZESTONE.get());
      this.dropSelf((Block)TFBlocks.DECORATIVE_MAZESTONE.get());
      this.dropSelf((Block)TFBlocks.CRACKED_MAZESTONE.get());
      this.dropSelf((Block)TFBlocks.MOSSY_MAZESTONE.get());
      this.dropSelf((Block)TFBlocks.MAZESTONE_MOSAIC.get());
      this.dropSelf((Block)TFBlocks.MAZESTONE_BORDER.get());
      this.dropSelf((Block)TFBlocks.MAZE_SLIME_BLOCK.get());
      this.add((Block)TFBlocks.RED_THREAD.get(), this.redThread());
      this.dropWhenSilkTouch((Block)TFBlocks.HEDGE.get());
      this.add(
         (Block)TFBlocks.ROOT_BLOCK.get(),
         this.createSingleItemTableWithSilkTouch((Block)TFBlocks.ROOT_BLOCK.get(), Items.STICK, UniformGenerator.between(3.0F, 5.0F))
      );
      this.add(
         (Block)TFBlocks.LIVEROOT_BLOCK.get(),
         this.createSilkTouchDispatchTable(
            (Block)TFBlocks.LIVEROOT_BLOCK.get(),
            (Builder)this.applyExplosionCondition(
               (ItemLike)TFBlocks.LIVEROOT_BLOCK.get(),
               LootItem.lootTableItem((ItemLike)TFItems.LIVEROOT.get())
                  .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)TFBlocks.MANGROVE_ROOT.get(),
         this.createSingleItemTableWithSilkTouch((Block)TFBlocks.MANGROVE_ROOT.get(), Items.STICK, UniformGenerator.between(3.0F, 5.0F))
      );
      this.dropSelf((Block)TFBlocks.UNCRAFTING_TABLE.get());
      this.add((Block)TFBlocks.BRAZIER.get(), block -> this.createSinglePropConditionTable(block, BrazierBlock.HALF, DoubleBlockHalf.LOWER));
      this.add(
         (Block)TFBlocks.MASON_JAR.get(),
         LootTable.lootTable()
            .withPool(
               (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
                  (ItemLike)TFBlocks.MASON_JAR.get(),
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(
                        LootItem.lootTableItem((ItemLike)TFBlocks.MASON_JAR.get())
                           .apply(
                              CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY)
                                 .include(DataComponents.CUSTOM_NAME)
                                 .include(DataComponents.CONTAINER)
                                 .include(DataComponents.LOCK)
                                 .include(DataComponents.CONTAINER_LOOT)
                                 .include((DataComponentType)TFDataComponents.JAR_LID.get())
                           )
                     )
               )
            )
      );
      this.add(
         (Block)TFBlocks.FIREFLY_JAR.get(),
         LootTable.lootTable()
            .withPool(
               (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
                  (ItemLike)TFBlocks.FIREFLY_JAR.get(),
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(
                        LootItem.lootTableItem((ItemLike)TFBlocks.FIREFLY_JAR.get())
                           .apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include((DataComponentType)TFDataComponents.JAR_LID.get()))
                     )
               )
            )
      );
      this.add(
         (Block)TFBlocks.CICADA_JAR.get(),
         LootTable.lootTable()
            .withPool(
               (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
                  (ItemLike)TFBlocks.CICADA_JAR.get(),
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(
                        LootItem.lootTableItem((ItemLike)TFBlocks.CICADA_JAR.get())
                           .apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include((DataComponentType)TFDataComponents.JAR_LID.get()))
                     )
               )
            )
      );
      this.add((Block)TFBlocks.FIREFLY_SPAWNER.get(), this.particleSpawner());
      this.add((Block)TFBlocks.MOSS_PATCH.get(), createShearsOnlyDrop((ItemLike)TFBlocks.MOSS_PATCH.get()));
      this.add((Block)TFBlocks.MAYAPPLE.get(), createShearsOnlyDrop((ItemLike)TFBlocks.MAYAPPLE.get()));
      this.addTFBush((Block)TFBlocks.IRON_OREBERRY.get(), TFLootTables.IRON_OREBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.GOLD_OREBERRY.get(), TFLootTables.GOLD_OREBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.COPPER_OREBERRY.get(), TFLootTables.COPPER_OREBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.ESSENCE_OREBERRY.get(), TFLootTables.ESSENCE_BERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.RASPBERRY_BUSH.get(), TFLootTables.RASPBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.BLUEBERRY_BUSH.get(), TFLootTables.BLUEBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.BLACKBERRY_BUSH.get(), TFLootTables.BLACKBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.MALOBERRY_BUSH.get(), TFLootTables.MALOBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.BLIGHTBERRY_BUSH.get(), TFLootTables.BLIGHTBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.DUSKBERRY_BUSH.get(), TFLootTables.DUSKBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.SKYBERRY_BUSH.get(), TFLootTables.SKYBERRY_BUSH_DROPS);
      this.addTFBush((Block)TFBlocks.STINGBERRY_BUSH.get(), TFLootTables.STINGBERRY_BUSH_DROPS);
      this.add((Block)TFBlocks.CLOVER_PATCH.get(), createShearsOnlyDrop((ItemLike)TFBlocks.CLOVER_PATCH.get()));
      this.add((Block)TFBlocks.FIDDLEHEAD.get(), createShearsOnlyDrop((ItemLike)TFBlocks.FIDDLEHEAD.get()));
      this.dropSelf((Block)TFBlocks.MUSHGLOOM.get());
      this.add((Block)TFBlocks.TORCHBERRY_PLANT.get(), this.torchberryPlant((Block)TFBlocks.TORCHBERRY_PLANT.get()));
      this.add(
         (Block)TFBlocks.ROOT_STRAND.get(),
         block -> this.createShearsDispatchTable(
            block,
            (Builder<?>)this.applyExplosionDecay(
               block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
            )
         )
      );
      this.add((Block)TFBlocks.FALLEN_LEAVES.get(), this.fallenLeaves());
      this.dropSelf((Block)TFBlocks.SMOKER.get());
      this.dropSelf((Block)TFBlocks.ENCASED_SMOKER.get());
      this.dropSelf((Block)TFBlocks.FIRE_JET.get());
      this.dropSelf((Block)TFBlocks.ENCASED_FIRE_JET.get());
      this.dropSelf((Block)TFBlocks.NAGASTONE_HEAD.get());
      this.dropSelf((Block)TFBlocks.NAGASTONE.get());
      this.dropSelf((Block)TFBlocks.SPIRAL_BRICKS.get());
      this.dropSelf((Block)TFBlocks.NAGASTONE_PILLAR.get());
      this.dropSelf((Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get());
      this.dropSelf((Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get());
      this.dropSelf((Block)TFBlocks.ETCHED_NAGASTONE.get());
      this.dropSelf((Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get());
      this.dropSelf((Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get());
      this.dropSelf((Block)TFBlocks.NAGASTONE_STAIRS_LEFT.get());
      this.dropSelf((Block)TFBlocks.NAGASTONE_STAIRS_RIGHT.get());
      this.dropSelf((Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get());
      this.dropSelf((Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get());
      this.dropSelf((Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get());
      this.dropSelf((Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get());
      this.add((Block)TFBlocks.NAGA_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.NAGA_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.NAGA_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.NAGA_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.LICH_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.LICH_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.LICH_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.LICH_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.MINOSHROOM_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.MINOSHROOM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.MINOSHROOM_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.MINOSHROOM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.HYDRA_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.HYDRA_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.HYDRA_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.HYDRA_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.KNIGHT_PHANTOM_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.KNIGHT_PHANTOM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.KNIGHT_PHANTOM_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.KNIGHT_PHANTOM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.UR_GHAST_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.UR_GHAST_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.UR_GHAST_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.UR_GHAST_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.ALPHA_YETI_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.ALPHA_YETI_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.ALPHA_YETI_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.ALPHA_YETI_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.SNOW_QUEEN_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.SNOW_QUEEN_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.SNOW_QUEEN_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.SNOW_QUEEN_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.QUEST_RAM_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.QUEST_RAM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.QUEST_RAM_WALL_TROPHY.get(), this.createSingleItemTable(((TrophyBlock)TFBlocks.QUEST_RAM_TROPHY.get()).asItem()));
      this.add((Block)TFBlocks.ZOMBIE_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.ZOMBIE_HEAD));
      this.add((Block)TFBlocks.ZOMBIE_WALL_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.ZOMBIE_HEAD));
      this.add((Block)TFBlocks.SKELETON_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.SKELETON_SKULL));
      this.add((Block)TFBlocks.SKELETON_WALL_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.SKELETON_SKULL));
      this.add((Block)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.WITHER_SKELETON_SKULL));
      this.add((Block)TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.WITHER_SKELETON_SKULL));
      this.add((Block)TFBlocks.CREEPER_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.CREEPER_HEAD));
      this.add((Block)TFBlocks.CREEPER_WALL_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.CREEPER_HEAD));
      this.add(
         (Block)TFBlocks.PLAYER_SKULL_CANDLE.get(),
         this.createSingleItemTable(Blocks.PLAYER_HEAD)
            .apply(
               CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY)
                  .include(DataComponents.PROFILE)
                  .include(DataComponents.NOTE_BLOCK_SOUND)
                  .include(DataComponents.CUSTOM_NAME)
            )
      );
      this.add(
         (Block)TFBlocks.PLAYER_WALL_SKULL_CANDLE.get(),
         this.createSingleItemTable(Blocks.PLAYER_HEAD)
            .apply(
               CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY)
                  .include(DataComponents.PROFILE)
                  .include(DataComponents.NOTE_BLOCK_SOUND)
                  .include(DataComponents.CUSTOM_NAME)
            )
      );
      this.add((Block)TFBlocks.PIGLIN_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.PIGLIN_HEAD));
      this.add((Block)TFBlocks.PIGLIN_WALL_SKULL_CANDLE.get(), this.createSingleItemTable(Blocks.PIGLIN_HEAD));
      this.dropSelf((Block)TFBlocks.IRON_LADDER.get());
      this.add((Block)TFBlocks.ROPE.get(), this.rope());
      this.dropWhenSilkTouch((Block)TFBlocks.CANOPY_WINDOW.value());
      this.dropWhenSilkTouch((Block)TFBlocks.CANOPY_WINDOW_PANE.value());
      this.dropSelf((Block)TFBlocks.TWISTED_STONE.get());
      this.dropSelf((Block)TFBlocks.TWISTED_STONE_PILLAR.get());
      this.dropSelf((Block)TFBlocks.BOLD_STONE_PILLAR.get());
      this.add((Block)TFBlocks.SKULL_CHEST.get(), skullChest((Block)TFBlocks.SKULL_CHEST.get()));
      this.add((Block)TFBlocks.KEEPSAKE_CASKET.get(), casketInfo((Block)TFBlocks.KEEPSAKE_CASKET.get()));
      this.dropSelf((Block)TFBlocks.CANDELABRA.get());
      this.dropSelf((Block)TFBlocks.WROUGHT_IRON_FENCE.get());
      this.dropSelf((Block)TFBlocks.TERRORCOTTA_ARCS.value());
      this.dropSelf((Block)TFBlocks.TERRORCOTTA_CURVES.value());
      this.dropSelf((Block)TFBlocks.TERRORCOTTA_LINES.value());
      this.dropSelf((Block)TFBlocks.CORONATION_CARPET.value());
      this.dropPottedContents((Block)TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_CANOPY_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_MANGROVE_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_DARKWOOD_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_HOLLOW_OAK_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_TIME_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_TRANSFORMATION_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_MINING_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_SORTING_SAPLING.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_MAYAPPLE.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_FIDDLEHEAD.get());
      this.dropPottedContents((Block)TFBlocks.POTTED_MUSHGLOOM.get());
      this.add((Block)TFBlocks.POTTED_THORN.get(), this.createSingleItemTable(Items.FLOWER_POT));
      this.add((Block)TFBlocks.POTTED_GREEN_THORN.get(), this.createSingleItemTable(Items.FLOWER_POT));
      this.add((Block)TFBlocks.POTTED_DEAD_THORN.get(), this.createSingleItemTable(Items.FLOWER_POT));
      this.dropSelf((Block)TFBlocks.OAK_BANISTER.get());
      this.dropSelf((Block)TFBlocks.SPRUCE_BANISTER.get());
      this.dropSelf((Block)TFBlocks.BIRCH_BANISTER.get());
      this.dropSelf((Block)TFBlocks.JUNGLE_BANISTER.get());
      this.dropSelf((Block)TFBlocks.ACACIA_BANISTER.get());
      this.dropSelf((Block)TFBlocks.DARK_OAK_BANISTER.get());
      this.dropSelf((Block)TFBlocks.CRIMSON_BANISTER.get());
      this.dropSelf((Block)TFBlocks.WARPED_BANISTER.get());
      this.dropSelf((Block)TFBlocks.VANGROVE_BANISTER.get());
      this.dropSelf((Block)TFBlocks.BAMBOO_BANISTER.get());
      this.dropSelf((Block)TFBlocks.CHERRY_BANISTER.get());
      this.dropSelf((Block)TFBlocks.OAK_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.SPRUCE_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.BIRCH_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.JUNGLE_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.ACACIA_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.DARK_OAK_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.CRIMSON_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.WARPED_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.VANGROVE_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.BAMBOO_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.CHERRY_DRYING_RACK.get());
      this.add((Block)TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get(), this.hollowLog((Block)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get()));
      this.add((Block)TFBlocks.HOLLOW_OAK_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_OAK_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_WARPED_STEM_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_WARPED_STEM_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_VANGROVE_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_VANGROVE_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CHERRY_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_CHERRY_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_DARK_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TIME_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_TIME_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_MINING_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_MINING_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_SORTING_LOG_VERTICAL.get(), this.verticalHollowLog((Block)TFBlocks.HOLLOW_SORTING_LOG_VERTICAL.get()));
      this.add((Block)TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get()));
      this.add((Block)TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get(), this.hollowLog((Block)TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get()));
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_SAPLING.get());
      this.add(
         (Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(), (ItemLike)TFBlocks.TWILIGHT_OAK_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.RAINBOW_OAK_SAPLING.get());
      this.add(
         (Block)TFBlocks.RAINBOW_OAK_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.RAINBOW_OAK_LEAVES.get(), (ItemLike)TFBlocks.RAINBOW_OAK_SAPLING.get(), RARE_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.HOLLOW_OAK_SAPLING.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_PLANKS.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_STAIRS.get());
      this.add((Block)TFBlocks.TWILIGHT_OAK_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.TWILIGHT_OAK_SLAB.get()));
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_BUTTON.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_FENCE.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_GATE.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_PLATE.get());
      this.add(
         (Block)TFBlocks.TWILIGHT_OAK_DOOR.get(),
         this.createSinglePropConditionTable((Block)TFBlocks.TWILIGHT_OAK_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER)
      );
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_TRAPDOOR.get());
      this.add((Block)TFBlocks.TWILIGHT_OAK_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TWILIGHT_OAK_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.TWILIGHT_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TWILIGHT_OAK_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get(),
         this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get()).asItem())
      );
      this.add(
         (Block)TFBlocks.TWILIGHT_OAK_WALL_HANGING_SIGN.get(),
         this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get()).asItem())
      );
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_BANISTER.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_CHEST.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.CANOPY_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_CANOPY_LOG.get());
      this.dropSelf((Block)TFBlocks.CANOPY_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_CANOPY_WOOD.get());
      this.dropSelf((Block)TFBlocks.CANOPY_SAPLING.get());
      this.add(
         (Block)TFBlocks.CANOPY_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.CANOPY_LEAVES.get(), (ItemLike)TFBlocks.CANOPY_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.CANOPY_PLANKS.get());
      this.dropSelf((Block)TFBlocks.CANOPY_STAIRS.get());
      this.add((Block)TFBlocks.CANOPY_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.CANOPY_SLAB.get()));
      this.dropSelf((Block)TFBlocks.CANOPY_BUTTON.get());
      this.dropSelf((Block)TFBlocks.CANOPY_FENCE.get());
      this.dropSelf((Block)TFBlocks.CANOPY_GATE.get());
      this.dropSelf((Block)TFBlocks.CANOPY_PLATE.get());
      this.add((Block)TFBlocks.CANOPY_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.CANOPY_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER));
      this.dropSelf((Block)TFBlocks.CANOPY_TRAPDOOR.get());
      this.add((Block)TFBlocks.CANOPY_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.CANOPY_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.CANOPY_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.CANOPY_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.CANOPY_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.CANOPY_HANGING_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.CANOPY_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.CANOPY_HANGING_SIGN.get()).asItem())
      );
      this.add(
         (Block)TFBlocks.CANOPY_BOOKSHELF.get(),
         this.createSingleItemTableWithSilkTouch((Block)TFBlocks.CANOPY_BOOKSHELF.get(), Items.BOOK, ConstantValue.exactly(2.0F))
      );
      this.dropSelf((Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get());
      this.dropSelf((Block)TFBlocks.CANOPY_BANISTER.get());
      this.dropSelf((Block)TFBlocks.CANOPY_CHEST.get());
      this.dropSelf((Block)TFBlocks.CANOPY_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.CANOPY_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_MANGROVE_LOG.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_SAPLING.get());
      this.add(
         (Block)TFBlocks.MANGROVE_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.MANGROVE_LEAVES.get(), (ItemLike)TFBlocks.MANGROVE_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.MANGROVE_PLANKS.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_STAIRS.get());
      this.add((Block)TFBlocks.MANGROVE_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.MANGROVE_SLAB.get()));
      this.dropSelf((Block)TFBlocks.MANGROVE_BUTTON.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_FENCE.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_GATE.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_PLATE.get());
      this.add(
         (Block)TFBlocks.MANGROVE_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.MANGROVE_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER)
      );
      this.dropSelf((Block)TFBlocks.MANGROVE_TRAPDOOR.get());
      this.add((Block)TFBlocks.MANGROVE_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.MANGROVE_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.MANGROVE_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.MANGROVE_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.MANGROVE_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.MANGROVE_HANGING_SIGN.get()).asItem())
      );
      this.add(
         (Block)TFBlocks.MANGROVE_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.MANGROVE_HANGING_SIGN.get()).asItem())
      );
      this.dropSelf((Block)TFBlocks.MANGROVE_BANISTER.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_CHEST.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.MANGROVE_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.DARK_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_DARK_LOG.get());
      this.dropSelf((Block)TFBlocks.DARK_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_DARK_WOOD.get());
      this.dropSelf((Block)TFBlocks.DARKWOOD_SAPLING.get());
      this.add(
         (Block)TFBlocks.DARK_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.DARK_LEAVES.get(), (ItemLike)TFBlocks.DARKWOOD_SAPLING.get(), RARE_SAPLING_DROP_RATES)
      );
      this.add(
         (Block)TFBlocks.HARDENED_DARK_LEAVES.get(),
         this.silkAndStick((Block)TFBlocks.DARK_LEAVES.get(), (ItemLike)TFBlocks.DARKWOOD_SAPLING.get(), RARE_SAPLING_DROP_RATES)
      );
      this.dropSelf((Block)TFBlocks.DARK_PLANKS.get());
      this.dropSelf((Block)TFBlocks.DARK_STAIRS.get());
      this.add((Block)TFBlocks.DARK_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.DARK_SLAB.get()));
      this.dropSelf((Block)TFBlocks.DARK_BUTTON.get());
      this.dropSelf((Block)TFBlocks.DARK_FENCE.get());
      this.dropSelf((Block)TFBlocks.DARK_GATE.get());
      this.dropSelf((Block)TFBlocks.DARK_PLATE.get());
      this.add((Block)TFBlocks.DARK_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.DARK_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER));
      this.dropSelf((Block)TFBlocks.DARK_TRAPDOOR.get());
      this.add((Block)TFBlocks.DARK_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.DARK_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.DARK_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.DARK_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.DARK_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.DARK_HANGING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.DARK_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.DARK_HANGING_SIGN.get()).asItem()));
      this.dropSelf((Block)TFBlocks.DARK_BANISTER.get());
      this.dropSelf((Block)TFBlocks.DARK_CHEST.get());
      this.dropSelf((Block)TFBlocks.DARK_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.DARK_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.TIME_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TIME_LOG.get());
      this.dropSelf((Block)TFBlocks.TIME_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TIME_WOOD.get());
      this.dropOther((Block)TFBlocks.TIME_LOG_CORE.get(), (ItemLike)TFBlocks.TIME_LOG.get());
      this.dropSelf((Block)TFBlocks.TIME_SAPLING.get());
      this.registerLeavesNoSapling((Block)TFBlocks.TIME_LEAVES.get(), registrylookup);
      this.dropSelf((Block)TFBlocks.TIME_PLANKS.get());
      this.dropSelf((Block)TFBlocks.TIME_STAIRS.get());
      this.add((Block)TFBlocks.TIME_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.TIME_SLAB.get()));
      this.dropSelf((Block)TFBlocks.TIME_BUTTON.get());
      this.dropSelf((Block)TFBlocks.TIME_FENCE.get());
      this.dropSelf((Block)TFBlocks.TIME_GATE.get());
      this.dropSelf((Block)TFBlocks.TIME_PLATE.get());
      this.add((Block)TFBlocks.TIME_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.TIME_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER));
      this.dropSelf((Block)TFBlocks.TIME_TRAPDOOR.get());
      this.add((Block)TFBlocks.TIME_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TIME_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.TIME_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TIME_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.TIME_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TIME_HANGING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.TIME_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TIME_HANGING_SIGN.get()).asItem()));
      this.dropSelf((Block)TFBlocks.TIME_BANISTER.get());
      this.dropSelf((Block)TFBlocks.TIME_CHEST.get());
      this.dropSelf((Block)TFBlocks.TIME_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.TIME_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get());
      this.dropOther((Block)TFBlocks.TRANSFORMATION_LOG_CORE.get(), (ItemLike)TFBlocks.TRANSFORMATION_LOG.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_SAPLING.get());
      this.registerLeavesNoSapling((Block)TFBlocks.TRANSFORMATION_LEAVES.get(), registrylookup);
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_PLANKS.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_STAIRS.get());
      this.add((Block)TFBlocks.TRANSFORMATION_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.TRANSFORMATION_SLAB.get()));
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_BUTTON.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_FENCE.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_GATE.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_PLATE.get());
      this.add(
         (Block)TFBlocks.TRANSFORMATION_DOOR.get(),
         this.createSinglePropConditionTable((Block)TFBlocks.TRANSFORMATION_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER)
      );
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_TRAPDOOR.get());
      this.add((Block)TFBlocks.TRANSFORMATION_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TRANSFORMATION_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.TRANSFORMATION_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.TRANSFORMATION_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.TRANSFORMATION_HANGING_SIGN.get(),
         this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TRANSFORMATION_HANGING_SIGN.get()).asItem())
      );
      this.add(
         (Block)TFBlocks.TRANSFORMATION_WALL_HANGING_SIGN.get(),
         this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.TRANSFORMATION_HANGING_SIGN.get()).asItem())
      );
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_BANISTER.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_CHEST.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.TRANSFORMATION_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.MINING_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_MINING_LOG.get());
      this.dropSelf((Block)TFBlocks.MINING_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_MINING_WOOD.get());
      this.dropOther((Block)TFBlocks.MINING_LOG_CORE.get(), (ItemLike)TFBlocks.MINING_LOG.get());
      this.dropSelf((Block)TFBlocks.MINING_SAPLING.get());
      this.registerLeavesNoSapling((Block)TFBlocks.MINING_LEAVES.get(), registrylookup);
      this.dropSelf((Block)TFBlocks.MINING_PLANKS.get());
      this.dropSelf((Block)TFBlocks.MINING_STAIRS.get());
      this.add((Block)TFBlocks.MINING_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.MINING_SLAB.get()));
      this.dropSelf((Block)TFBlocks.MINING_BUTTON.get());
      this.dropSelf((Block)TFBlocks.MINING_FENCE.get());
      this.dropSelf((Block)TFBlocks.MINING_GATE.get());
      this.dropSelf((Block)TFBlocks.MINING_PLATE.get());
      this.add((Block)TFBlocks.MINING_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.MINING_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER));
      this.dropSelf((Block)TFBlocks.MINING_TRAPDOOR.get());
      this.add((Block)TFBlocks.MINING_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.MINING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.MINING_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.MINING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.MINING_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.MINING_HANGING_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.MINING_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.MINING_HANGING_SIGN.get()).asItem())
      );
      this.dropSelf((Block)TFBlocks.MINING_BANISTER.get());
      this.dropSelf((Block)TFBlocks.MINING_CHEST.get());
      this.dropSelf((Block)TFBlocks.MINING_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.MINING_DRYING_RACK.get());
      this.dropSelf((Block)TFBlocks.SORTING_LOG.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_SORTING_LOG.get());
      this.dropSelf((Block)TFBlocks.SORTING_WOOD.get());
      this.dropSelf((Block)TFBlocks.STRIPPED_SORTING_WOOD.get());
      this.dropOther((Block)TFBlocks.SORTING_LOG_CORE.get(), (ItemLike)TFBlocks.SORTING_LOG.get());
      this.dropSelf((Block)TFBlocks.SORTING_SAPLING.get());
      this.registerLeavesNoSapling((Block)TFBlocks.SORTING_LEAVES.get(), registrylookup);
      this.dropSelf((Block)TFBlocks.SORTING_PLANKS.get());
      this.dropSelf((Block)TFBlocks.SORTING_STAIRS.get());
      this.add((Block)TFBlocks.SORTING_SLAB.get(), this.createSlabItemTable((Block)TFBlocks.SORTING_SLAB.get()));
      this.dropSelf((Block)TFBlocks.SORTING_BUTTON.get());
      this.dropSelf((Block)TFBlocks.SORTING_FENCE.get());
      this.dropSelf((Block)TFBlocks.SORTING_GATE.get());
      this.dropSelf((Block)TFBlocks.SORTING_PLATE.get());
      this.add(
         (Block)TFBlocks.SORTING_DOOR.get(), this.createSinglePropConditionTable((Block)TFBlocks.SORTING_DOOR.get(), DoorBlock.HALF, DoubleBlockHalf.LOWER)
      );
      this.dropSelf((Block)TFBlocks.SORTING_TRAPDOOR.get());
      this.add((Block)TFBlocks.SORTING_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.SORTING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.SORTING_WALL_SIGN.get(), this.createSingleItemTable(((StandingSignBlock)TFBlocks.SORTING_SIGN.get()).asItem()));
      this.add((Block)TFBlocks.SORTING_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.SORTING_HANGING_SIGN.get()).asItem()));
      this.add(
         (Block)TFBlocks.SORTING_WALL_HANGING_SIGN.get(), this.createSingleItemTable(((CeilingHangingSignBlock)TFBlocks.SORTING_HANGING_SIGN.get()).asItem())
      );
      this.dropSelf((Block)TFBlocks.SORTING_BANISTER.get());
      this.dropSelf((Block)TFBlocks.SORTING_CHEST.get());
      this.dropSelf((Block)TFBlocks.SORTING_TRAPPED_CHEST.get());
      this.dropSelf((Block)TFBlocks.SORTING_DRYING_RACK.get());
      this.add((Block)TFBlocks.OMINOUS_FIRE.get(), noDrop());
      this.ominousCandle(TFBlocks.OMINOUS_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_WHITE_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_ORANGE_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_MAGENTA_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_LIGHT_BLUE_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_YELLOW_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_LIME_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_PINK_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_GRAY_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_LIGHT_GRAY_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_CYAN_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_PURPLE_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_BLUE_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_BROWN_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_GREEN_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_RED_CANDLE);
      this.ominousCandle(TFBlocks.OMINOUS_BLACK_CANDLE);
   }

   private void registerLeavesNoSapling(Block leaves, RegistryLookup<Enchantment> registrylookup) {
      Builder<?> sticks = (Builder<?>)this.applyExplosionDecay(
         leaves,
         (FunctionUserBuilder)LootItem.lootTableItem(Items.STICK)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
            .when(
               BonusLevelTableCondition.bonusLevelFlatChance(
                  registrylookup.getOrThrow(Enchantments.FORTUNE), new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F}
               )
            )
      );
      this.add(leaves, this.createSilkTouchOrShearsDispatchTable(leaves, sticks));
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder hollowLog(Block log) {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(log.asItem())
                        .when(this.hasSilkTouch()))
                     .otherwise(
                        LootItem.lootTableItem(Items.STICK)
                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                           .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(Blocks.SHORT_GRASS)
                        .when(this.hasSilkTouch()))
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(HorizontalHollowLogBlock.VARIANT, HollowLogVariants.Horizontal.MOSS_AND_GRASS)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                           (ItemLike)TFBlocks.MOSS_PATCH.get()
                        )
                        .when(this.hasSilkTouch()))
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(HorizontalHollowLogBlock.VARIANT, HollowLogVariants.Horizontal.MOSS_AND_GRASS)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                           (ItemLike)TFBlocks.MOSS_PATCH.get()
                        )
                        .when(this.hasSilkTouch()))
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(HorizontalHollowLogBlock.VARIANT, HollowLogVariants.Horizontal.MOSS)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(Items.SNOWBALL)
                        .when(this.hasSilkTouch()))
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(HorizontalHollowLogBlock.VARIANT, HollowLogVariants.Horizontal.SNOW)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(Blocks.VINE)
                        .when(this.hasSilkTouch()))
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(ClimbableHollowLogBlock.VARIANT, HollowLogVariants.Climbable.VINE)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  LootItem.lootTableItem(Blocks.LADDER)
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(ClimbableHollowLogBlock.VARIANT, HollowLogVariants.Climbable.LADDER)
                           )
                     )
               )
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  LootItem.lootTableItem(Blocks.LADDER)
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(log)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(ClimbableHollowLogBlock.VARIANT, HollowLogVariants.Climbable.LADDER_WATERLOGGED)
                           )
                     )
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder verticalHollowLog(Block log) {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(log.asItem())
                        .when(this.hasSilkTouch()))
                     .otherwise(
                        LootItem.lootTableItem(Items.STICK)
                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                           .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                     )
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return this.createSilkTouchOrShearsDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)this.applyExplosionCondition(
                  block, LootItem.lootTableItem(nonSilk.asItem())
               ))
               .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), nonSilkFortune))
         )
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .when(HAS_SHEARS.or(this.hasSilkTouch()).invert())
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)this.applyExplosionDecay(
                        block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                     ))
                     .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                           registrylookup.getOrThrow(Enchantments.FORTUNE), new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F}
                        )
                     )
               )
         );
   }

   private void addTFBush(Block block, ResourceKey<LootTable> berry) {
      if (block instanceof TFBushBlock bush) {
         this.add(
            block,
            LootTable.lootTable()
               .withPool(
                  LootPool.lootPool()
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(bush)
                           .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(TFBushBlock.AGE, 3))
                     )
                     .add(NestedLootTable.lootTableReference(berry))
               )
               .withPool(LootPool.lootPool().add(LootItem.lootTableItem(bush.asItem())))
         );
      } else {
         throw new IllegalArgumentException(block + " is not a TFBushBlock");
      }
   }

   private static net.minecraft.world.level.storage.loot.LootTable.Builder skullChest(Block block) {
      return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)));
   }

   private static net.minecraft.world.level.storage.loot.LootTable.Builder casketInfo(Block block) {
      return LootTable.lootTable()
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).apply(CopyBlockState.copyState(block).copy(KeepsakeCasketBlock.BREAKAGE)));
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder particleSpawner() {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  (Builder)this.applyExplosionDecay((ItemLike)TFBlocks.FIREFLY_SPAWNER.get(), LootItem.lootTableItem((ItemLike)TFBlocks.FIREFLY_SPAWNER.get()))
               )
         )
         .withPool(
            LootPool.lootPool()
               .add(
                  AlternativesEntry.alternatives(
                     new Builder[]{
                        AlternativesEntry.alternatives(
                           FireflySpawnerBlock.RADIUS.getPossibleValues(),
                           layer -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                    (ItemLike)TFBlocks.FIREFLY.get()
                                 )
                                 .when(
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.FIREFLY_SPAWNER.get())
                                       .setProperties(
                                          net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                             .hasProperty(FireflySpawnerBlock.RADIUS, layer)
                                       )
                                 ))
                              .apply(SetItemCountFunction.setCount(ConstantValue.exactly(layer - 1)))
                        )
                     }
                  )
               )
         );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder torchberryPlant(Block pBlock) {
      return LootTable.lootTable()
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pBlock).when(HAS_SHEARS)))
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  LootItem.lootTableItem((ItemLike)TFItems.TORCHBERRIES.get())
                     .when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(TorchberryPlantBlock.HAS_BERRIES, true)
                           )
                     )
               )
         );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder redThread() {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(
                  (Builder)this.applyExplosionDecay(
                     (ItemLike)TFBlocks.RED_THREAD.get(),
                     LootItem.lootTableItem((ItemLike)TFBlocks.RED_THREAD.get())
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.EAST, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.WEST, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.NORTH, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.SOUTH, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.UP, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.RED_THREAD.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.DOWN, true)
                                    )
                              )
                        )
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true))
                  )
               )
         );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder rope() {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(
                  (Builder)this.applyExplosionDecay(
                     (ItemLike)TFBlocks.ROPE.get(),
                     LootItem.lootTableItem((ItemLike)TFBlocks.ROPE.get())
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.ROPE.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(RopeBlock.X, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.ROPE.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(RopeBlock.Y, true)
                                    )
                              )
                        )
                        .apply(
                           SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true)
                              .when(
                                 LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.ROPE.get())
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(RopeBlock.Z, true)
                                    )
                              )
                        )
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true))
                  )
               )
         );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder fallenLeaves() {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(
                  AlternativesEntry.alternatives(
                     new Builder[]{
                        AlternativesEntry.alternatives(
                              FallenLeavesBlock.LAYERS.getPossibleValues(),
                              layer -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                       (ItemLike)TFBlocks.FALLEN_LEAVES.get()
                                    )
                                    .when(
                                       LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)TFBlocks.FALLEN_LEAVES.get())
                                          .setProperties(
                                             net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FallenLeavesBlock.LAYERS, layer)
                                          )
                                    ))
                                 .apply(SetItemCountFunction.setCount(ConstantValue.exactly(layer.intValue())))
                           )
                           .when(HAS_SHEARS)
                     }
                  )
               )
         );
   }

   protected void ominousCandle(DeferredBlock<OminousCandleBlock> block) {
      this.add(
         (Block)block.get(),
         LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     (Builder)this.applyExplosionDecay(
                        (ItemLike)block.get(),
                        LootItem.lootTableItem(((OminousCandleBlock)block.get()).candle)
                           .apply(
                              List.of(2, 3, 4),
                              value -> SetItemCountFunction.setCount(ConstantValue.exactly(value.intValue()))
                                 .when(
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)block.get())
                                       .setProperties(
                                          net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                             .hasProperty(OminousCandleBlock.CANDLES, value)
                                       )
                                 )
                           )
                     )
                  )
            )
      );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder createShearsDispatchTable(Block block, Builder<?> builder) {
      return createSelfDropDispatchTable(block, HAS_SHEARS, builder);
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder createSilkTouchOrShearsDispatchTable(Block block, Builder<?> builder) {
      return createSelfDropDispatchTable(block, HAS_SHEARS.or(this.hasSilkTouch()), builder);
   }

   protected static net.minecraft.world.level.storage.loot.LootTable.Builder createShearsOnlyDrop(ItemLike p_124287_) {
      return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS).add(LootItem.lootTableItem(p_124287_)));
   }

   protected Iterable<Block> getKnownBlocks() {
      return TFBlocks.BLOCKS.getEntries().stream().<Block>map(DeferredHolder::value).collect(Collectors.toList());
   }
}
