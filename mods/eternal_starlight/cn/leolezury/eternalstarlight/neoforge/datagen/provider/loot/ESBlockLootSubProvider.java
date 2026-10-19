package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.block.AbyssalKelp;
import cn.leolezury.eternalstarlight.common.block.BerriesVines;
import cn.leolezury.eternalstarlight.common.block.CrinoaBlock;
import cn.leolezury.eternalstarlight.common.block.LayeredBlock;
import cn.leolezury.eternalstarlight.common.block.LunarisCactusBlock;
import cn.leolezury.eternalstarlight.common.block.NocturnalMilletTopBlock;
import cn.leolezury.eternalstarlight.common.block.PungencyFruitVinesBlock;
import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.ThioquartzBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction.Source;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ESBlockLootSubProvider extends BlockLootSubProvider {
   protected static final Builder HAS_SHEARS_OR_SICKLE = HAS_SHEARS.or(
      MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ESTags.Items.SICKLES))
   );

   public ESBlockLootSubProvider(Provider lookup) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
   }

   protected void generate() {
      RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      this.add(
         (Block)ESBlocks.LUNAR_LEAVES.get(), block -> this.createLunarLeavesDrops(block, (Block)ESBlocks.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.dropSelf((Block)ESBlocks.LUNAR_LOG.get());
      this.dropSelf((Block)ESBlocks.LUNAR_WOOD.get());
      this.dropSelf(ESBlocks.LUNAR_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_LUNAR_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_LUNAR_WOOD.get());
      this.add((Block)ESBlocks.LUNAR_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.LUNAR_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.LUNAR_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.LUNAR_BUTTON.get());
      this.dropSelf((Block)ESBlocks.LUNAR_FENCE.get());
      this.dropSelf((Block)ESBlocks.LUNAR_FENCE_GATE.get());
      this.add((Block)ESBlocks.LUNAR_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.LUNAR_STAIRS.get());
      this.dropSelf((Block)ESBlocks.LUNAR_SIGN.get());
      this.dropSelf((Block)ESBlocks.LUNAR_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.LUNAR_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.LUNAR_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.LUNAR_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_LUNAR_SAPLING.get());
      this.dropSelf((Block)ESBlocks.DEAD_LUNAR_LOG.get());
      this.dropSelf((Block)ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get());
      this.dropSelf((Block)ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get());
      this.add(
         (Block)ESBlocks.NORTHLAND_LEAVES.get(), block -> this.createLeavesDrops(block, (Block)ESBlocks.NORTHLAND_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.dropSelf((Block)ESBlocks.NORTHLAND_LOG.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_WOOD.get());
      this.dropSelf(ESBlocks.NORTHLAND_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_NORTHLAND_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_NORTHLAND_WOOD.get());
      this.add((Block)ESBlocks.NORTHLAND_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.NORTHLAND_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_BUTTON.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_FENCE.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_FENCE_GATE.get());
      this.add((Block)ESBlocks.NORTHLAND_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.NORTHLAND_STAIRS.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_SIGN.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.NORTHLAND_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_NORTHLAND_SAPLING.get());
      this.add((Block)ESBlocks.BANYIN_LEAVES.get(), block -> this.createLeavesDrops(block, (Block)ESBlocks.BANYIN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
      this.dropSelf((Block)ESBlocks.BANYIN_LOG.get());
      this.dropSelf((Block)ESBlocks.BANYIN_WOOD.get());
      this.dropSelf(ESBlocks.BANYIN_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_BANYIN_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_BANYIN_WOOD.get());
      this.add((Block)ESBlocks.BANYIN_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.BANYIN_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.BANYIN_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.BANYIN_BUTTON.get());
      this.dropSelf((Block)ESBlocks.BANYIN_FENCE.get());
      this.dropSelf((Block)ESBlocks.BANYIN_FENCE_GATE.get());
      this.add((Block)ESBlocks.BANYIN_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.BANYIN_STAIRS.get());
      this.dropSelf((Block)ESBlocks.BANYIN_SIGN.get());
      this.dropSelf((Block)ESBlocks.BANYIN_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.BANYIN_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.BANYIN_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.BANYIN_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_BANYIN_SAPLING.get());
      this.dropSelf((Block)ESBlocks.BANYIN_ROOTS.get());
      this.dropSelf((Block)ESBlocks.MUDDY_BANYIN_ROOTS.get());
      this.add(
         (Block)ESBlocks.SCARLET_LEAVES.get(), block -> this.createLeavesDrops(block, (Block)ESBlocks.SCARLET_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.add(
         ESBlocks.SCARLET_LEAVES_PILE.get(),
         block -> LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .when(LootItemEntityPropertyCondition.entityPresent(EntityTarget.THIS))
                  .add(
                     AlternativesEntry.alternatives(
                        new net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder[]{
                           AlternativesEntry.alternatives(
                                 LayeredBlock.LAYERS.getPossibleValues(),
                                 i -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(Items.STICK)
                                       .when(
                                          LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                             .setProperties(
                                                net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                   .hasProperty(LayeredBlock.LAYERS, i)
                                             )
                                       ))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, i.intValue())))
                              )
                              .when(HAS_SHEARS_OR_SICKLE.invert()),
                           AlternativesEntry.alternatives(
                              LayeredBlock.LAYERS.getPossibleValues(),
                              i -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                       (ItemLike)ESBlocks.SCARLET_LEAVES_PILE.get()
                                    )
                                    .when(
                                       LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                          .setProperties(
                                             net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(LayeredBlock.LAYERS, i)
                                          )
                                    ))
                                 .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i.intValue())))
                           )
                        }
                     )
                  )
            )
      );
      this.dropSelf((Block)ESBlocks.SCARLET_LOG.get());
      this.dropSelf((Block)ESBlocks.SCARLET_WOOD.get());
      this.dropSelf(ESBlocks.SCARLET_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_SCARLET_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_SCARLET_WOOD.get());
      this.add((Block)ESBlocks.SCARLET_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.SCARLET_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.SCARLET_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.SCARLET_BUTTON.get());
      this.dropSelf((Block)ESBlocks.SCARLET_FENCE.get());
      this.dropSelf((Block)ESBlocks.SCARLET_FENCE_GATE.get());
      this.add((Block)ESBlocks.SCARLET_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.SCARLET_STAIRS.get());
      this.dropSelf((Block)ESBlocks.SCARLET_SIGN.get());
      this.dropSelf((Block)ESBlocks.SCARLET_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.SCARLET_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.SCARLET_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.SCARLET_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_SCARLET_SAPLING.get());
      this.add(
         (Block)ESBlocks.TORREYA_LEAVES.get(), block -> this.createLeavesDrops(block, (Block)ESBlocks.TORREYA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.dropSelf((Block)ESBlocks.TORREYA_LOG.get());
      this.dropSelf((Block)ESBlocks.TORREYA_WOOD.get());
      this.dropSelf(ESBlocks.TORREYA_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_TORREYA_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_TORREYA_WOOD.get());
      this.add((Block)ESBlocks.TORREYA_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.TORREYA_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.TORREYA_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.TORREYA_BUTTON.get());
      this.dropSelf((Block)ESBlocks.TORREYA_FENCE.get());
      this.dropSelf((Block)ESBlocks.TORREYA_FENCE_GATE.get());
      this.add((Block)ESBlocks.TORREYA_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TORREYA_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TORREYA_SIGN.get());
      this.dropSelf((Block)ESBlocks.TORREYA_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.TORREYA_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.TORREYA_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.TORREYA_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_TORREYA_SAPLING.get());
      this.dropSelf((Block)ESBlocks.TORREYA_VINES.get());
      this.dropOther((Block)ESBlocks.TORREYA_VINES_PLANT.get(), (ItemLike)ESBlocks.TORREYA_VINES.get());
      this.add(
         (Block)ESBlocks.TORREYA_CAMPFIRE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block, LootItem.lootTableItem((ItemLike)ESItems.RAW_AMARAMBER.get())
            )
         )
      );
      this.add(
         (Block)ESBlocks.HANGING_ALGALEAVES.get(),
         block -> this.createLeavesDrops(block, (Block)ESBlocks.JINGLESTEM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.add(
         (Block)ESBlocks.HANGING_ALGALEAVES_PLANT.get(),
         block -> this.createLeavesDrops((Block)ESBlocks.HANGING_ALGALEAVES.get(), (Block)ESBlocks.JINGLESTEM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.add((Block)ESBlocks.ALGALEAVES.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
      this.dropSelf((Block)ESBlocks.JINGLESTEM_LOG.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_WOOD.get());
      this.dropSelf(ESBlocks.JINGLESTEM_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_JINGLESTEM_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_JINGLESTEM_WOOD.get());
      this.add((Block)ESBlocks.JINGLESTEM_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.JINGLESTEM_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_BUTTON.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_FENCE.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_FENCE_GATE.get());
      this.add((Block)ESBlocks.JINGLESTEM_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.JINGLESTEM_STAIRS.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_SIGN.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.JINGLESTEM_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_JINGLESTEM_SAPLING.get());
      this.add(
         (Block)ESBlocks.CRADLEWOOD_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.CRADLEWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
      );
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_LOG.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_WOOD.get());
      this.dropSelf(ESBlocks.CRADLEWOOD_PLANKS.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_CRADLEWOOD_LOG.get());
      this.dropSelf((Block)ESBlocks.STRIPPED_CRADLEWOOD_WOOD.get());
      this.add((Block)ESBlocks.CRADLEWOOD_DOOR.get(), x$0 -> this.createDoorTable(x$0));
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_TRAPDOOR.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_PRESSURE_PLATE.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_BUTTON.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_FENCE.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_FENCE_GATE.get());
      this.add((Block)ESBlocks.CRADLEWOOD_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_STAIRS.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_SIGN.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_WALL_SIGN.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_HANGING_SIGN.get());
      this.dropSelf((Block)ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get());
      this.dropSelf(ESBlocks.CRADLEWOOD_SAPLING.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_CRADLEWOOD_SAPLING.get());
      this.add(ESBlocks.GRIMSTONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.COBBLED_GRIMSTONE.get()));
      this.add((Block)ESBlocks.GRIMSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.GRIMSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.GRIMSTONE_WALL.get());
      this.dropSelf(ESBlocks.COBBLED_GRIMSTONE.get());
      this.add((Block)ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.COBBLED_GRIMSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.COBBLED_GRIMSTONE_WALL.get());
      this.dropSelf(ESBlocks.GRIMSTONE_BRICKS.get());
      this.add((Block)ESBlocks.GRIMSTONE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.GRIMSTONE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.GRIMSTONE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CRACKED_GRIMSTONE_BRICKS.get());
      this.dropSelf(ESBlocks.POLISHED_GRIMSTONE.get());
      this.add((Block)ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_GRIMSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_GRIMSTONE_WALL.get());
      this.dropSelf(ESBlocks.GRIMSTONE_TILES.get());
      this.add((Block)ESBlocks.GRIMSTONE_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.GRIMSTONE_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.GRIMSTONE_TILE_WALL.get());
      this.dropSelf(ESBlocks.CRACKED_GRIMSTONE_TILES.get());
      this.dropSelf(ESBlocks.POLISHED_GRIMSTONE_TILES.get());
      this.add((Block)ESBlocks.POLISHED_GRIMSTONE_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_GRIMSTONE_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_GRIMSTONE_TILE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_GRIMSTONE.get());
      this.dropSelf(ESBlocks.GLOWING_GRIMSTONE.get());
      this.add(ESBlocks.VOIDSTONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.COBBLED_VOIDSTONE.get()));
      this.add((Block)ESBlocks.VOIDSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.VOIDSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.VOIDSTONE_WALL.get());
      this.dropSelf(ESBlocks.COBBLED_VOIDSTONE.get());
      this.add((Block)ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.COBBLED_VOIDSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.COBBLED_VOIDSTONE_WALL.get());
      this.dropSelf(ESBlocks.VOIDSTONE_BRICKS.get());
      this.add((Block)ESBlocks.VOIDSTONE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.VOIDSTONE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.VOIDSTONE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CRACKED_VOIDSTONE_BRICKS.get());
      this.dropSelf(ESBlocks.POLISHED_VOIDSTONE.get());
      this.add((Block)ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_VOIDSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_VOIDSTONE_WALL.get());
      this.dropSelf(ESBlocks.VOIDSTONE_TILES.get());
      this.add((Block)ESBlocks.VOIDSTONE_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.VOIDSTONE_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.VOIDSTONE_TILE_WALL.get());
      this.dropSelf(ESBlocks.CRACKED_VOIDSTONE_TILES.get());
      this.dropSelf(ESBlocks.CHISELED_VOIDSTONE.get());
      this.dropSelf(ESBlocks.GLOWING_VOIDSTONE.get());
      this.dropSelf(ESBlocks.ETERNAL_ICE.get());
      this.dropWhenSilkTouch((Block)ESBlocks.THIN_ETERNAL_ICE.get());
      this.dropSelf(ESBlocks.ETERNAL_ICE_BRICKS.get());
      this.add((Block)ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.ETERNAL_ICE_BRICK_WALL.get());
      this.dropSelf((Block)ESBlocks.ETERNAL_ICE_LANTERN.get());
      this.dropSelf(ESBlocks.HAZE_ICE.get());
      this.dropSelf(ESBlocks.HAZE_ICE_BRICKS.get());
      this.add((Block)ESBlocks.HAZE_ICE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.HAZE_ICE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.HAZE_ICE_BRICK_WALL.get());
      this.dropSelf((Block)ESBlocks.HAZE_ICE_LANTERN.get());
      this.dropSelf((Block)ESBlocks.REINFORCED_ICE.get());
      this.dropSelf((Block)ESBlocks.REINFORCED_ICE_PANE.get());
      this.dropSelf(ESBlocks.ICICLE.get());
      this.add(
         ESBlocks.ASHEN_SNOW.get(),
         block -> LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .when(LootItemEntityPropertyCondition.entityPresent(EntityTarget.THIS))
                  .add(
                     AlternativesEntry.alternatives(
                        new net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder[]{
                           AlternativesEntry.alternatives(
                                 SnowLayerBlock.LAYERS.getPossibleValues(),
                                 i -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                          (ItemLike)ESItems.ASHEN_SNOWBALL.get()
                                       )
                                       .when(
                                          LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                             .setProperties(
                                                net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                   .hasProperty(SnowLayerBlock.LAYERS, i)
                                             )
                                       ))
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i.intValue())))
                              )
                              .when(this.doesNotHaveSilkTouch()),
                           AlternativesEntry.alternatives(
                              SnowLayerBlock.LAYERS.getPossibleValues(),
                              i -> ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                                       (ItemLike)ESBlocks.ASHEN_SNOW.get()
                                    )
                                    .when(
                                       LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                          .setProperties(
                                             net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(SnowLayerBlock.LAYERS, i)
                                          )
                                    ))
                                 .apply(SetItemCountFunction.setCount(ConstantValue.exactly(i.intValue())))
                           )
                        }
                     )
                  )
            )
      );
      this.dropSelf(ESBlocks.NEBULAITE.get());
      this.dropSelf(ESBlocks.NEBULAITE_BRICKS.get());
      this.add((Block)ESBlocks.NEBULAITE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.NEBULAITE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.NEBULAITE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());
      this.dropSelf(ESBlocks.STARCORE_BLOCK.get());
      this.dropSelf(ESBlocks.BLAZING_STARCORE_BLOCK.get());
      this.dropSelf(ESBlocks.STARCORE_LIGHT.get());
      this.add(
         (Block)ESBlocks.GRIMSTONE_STARCORE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARCORE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.VOIDSTONE_STARCORE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARCORE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.ETERNAL_ICE_STARCORE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARCORE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.HAZE_ICE_STARCORE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARCORE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(ESBlocks.RADIANITE.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.COBBLED_RADIANITE.get()));
      this.add((Block)ESBlocks.RADIANITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.RADIANITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.RADIANITE_WALL.get());
      this.dropSelf(ESBlocks.COBBLED_RADIANITE.get());
      this.add((Block)ESBlocks.COBBLED_RADIANITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.COBBLED_RADIANITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.COBBLED_RADIANITE_WALL.get());
      this.dropSelf((Block)ESBlocks.RADIANITE_PILLAR.get());
      this.dropSelf(ESBlocks.POLISHED_RADIANITE.get());
      this.add((Block)ESBlocks.POLISHED_RADIANITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_RADIANITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_RADIANITE_WALL.get());
      this.dropSelf(ESBlocks.RADIANITE_BRICKS.get());
      this.add((Block)ESBlocks.RADIANITE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.RADIANITE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.RADIANITE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_RADIANITE.get());
      this.dropSelf(ESBlocks.FLARE_BRICKS.get());
      this.add((Block)ESBlocks.FLARE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.FLARE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.FLARE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CUT_FLARE_BRICKS.get());
      this.add((Block)ESBlocks.CUT_FLARE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.CUT_FLARE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.CUT_FLARE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.FLARE_TILES.get());
      this.add((Block)ESBlocks.FLARE_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.FLARE_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.FLARE_TILE_WALL.get());
      this.dropSelf(ESBlocks.CUT_FLARE_TILES.get());
      this.add((Block)ESBlocks.CUT_FLARE_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.CUT_FLARE_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.CUT_FLARE_TILE_WALL.get());
      this.dropSelf((Block)ESBlocks.CHISELED_FLARE_PILLAR.get());
      this.dropSelf(ESBlocks.STELLAGMITE.get());
      this.add((Block)ESBlocks.STELLAGMITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.STELLAGMITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.STELLAGMITE_WALL.get());
      this.dropOther(ESBlocks.MOLTEN_STELLAGMITE.get(), (ItemLike)ESBlocks.STELLAGMITE.get());
      this.add((Block)ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(), block -> this.createSlabItemTable((Block)ESBlocks.STELLAGMITE_SLAB.get()));
      this.dropOther((Block)ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(), (ItemLike)ESBlocks.STELLAGMITE_STAIRS.get());
      this.dropOther((Block)ESBlocks.MOLTEN_STELLAGMITE_WALL.get(), (ItemLike)ESBlocks.STELLAGMITE_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_STELLAGMITE.get());
      this.add((Block)ESBlocks.POLISHED_STELLAGMITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_STELLAGMITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_STELLAGMITE_WALL.get());
      this.add((Block)ESBlocks.ABYSSAL_FIRE.get(), noDrop());
      this.dropSelf(ESBlocks.ABYSSLATE.get());
      this.dropSelf(ESBlocks.POLISHED_ABYSSLATE.get());
      this.add((Block)ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_ABYSSLATE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_ABYSSLATE_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
      this.add((Block)ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get());
      this.dropSelf((Block)ESBlocks.ABYSSAL_MAGMA_BLOCK.get());
      this.dropOther((Block)ESBlocks.ABYSSAL_GEYSER.get(), (ItemLike)ESItems.ABYSSLATE.get());
      this.dropSelf(ESBlocks.THERMABYSSLATE.get());
      this.dropSelf(ESBlocks.POLISHED_THERMABYSSLATE.get());
      this.add((Block)ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_THERMABYSSLATE_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
      this.add((Block)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get());
      this.dropSelf((Block)ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get());
      this.dropOther((Block)ESBlocks.THERMABYSSAL_GEYSER.get(), (ItemLike)ESItems.THERMABYSSLATE.get());
      this.dropSelf(ESBlocks.CRYOBYSSLATE.get());
      this.dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE.get());
      this.add((Block)ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
      this.add((Block)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get());
      this.dropSelf((Block)ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get());
      this.dropOther((Block)ESBlocks.CRYOBYSSAL_GEYSER.get(), (ItemLike)ESItems.CRYOBYSSLATE.get());
      this.dropSelf((Block)ESBlocks.NIGHTFALL_MUD.get());
      this.dropSelf((Block)ESBlocks.GLOWING_NIGHTFALL_MUD.get());
      this.dropSelf(ESBlocks.PACKED_NIGHTFALL_MUD.get());
      this.dropSelf(ESBlocks.NIGHTFALL_MUD_BRICKS.get());
      this.add((Block)ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get());
      this.dropSelf((Block)ESBlocks.TWILIGHT_SAND.get());
      this.dropSelf(ESBlocks.TWILIGHT_SANDSTONE.get());
      this.add((Block)ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TWILIGHT_SANDSTONE_WALL.get());
      this.dropSelf(ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
      this.add((Block)ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get());
      this.add(
         (Block)ESBlocks.DUSTED_GRAVEL.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block,
               ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)ESItems.DUSTED_SHARD.get())
                     .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                           enchantments.getOrThrow(Enchantments.FORTUNE), new float[]{0.1F, 0.14285715F, 0.25F, 1.0F}
                        )
                     ))
                  .otherwise(LootItem.lootTableItem(block))
            )
         )
      );
      this.dropSelf(ESBlocks.DUSTED_BRICKS.get());
      this.add((Block)ESBlocks.DUSTED_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.DUSTED_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.DUSTED_BRICK_WALL.get());
      this.add(
         ESBlocks.MOSSY_DUSTED_GRAVEL.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block,
               ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)ESItems.DUSTED_SHARD.get())
                     .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                           enchantments.getOrThrow(Enchantments.FORTUNE), new float[]{0.1F, 0.14285715F, 0.25F, 1.0F}
                        )
                     ))
                  .otherwise(LootItem.lootTableItem((ItemLike)ESBlocks.DUSTED_GRAVEL.get()))
            )
         )
      );
      this.add(
         ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block,
               ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)ESItems.DUSTED_SHARD.get())
                     .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                           enchantments.getOrThrow(Enchantments.FORTUNE), new float[]{0.1F, 0.14285715F, 0.25F, 1.0F}
                        )
                     ))
                  .otherwise(LootItem.lootTableItem((ItemLike)ESBlocks.DUSTED_GRAVEL.get()))
            )
         )
      );
      this.add((Block)ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get(), noDrop());
      this.add(
         (Block)ESBlocks.DIMSLAG.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block,
               AlternativesEntry.alternatives(
                  new net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder[]{
                     AlternativesEntry.alternatives(
                           new net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder[]{
                              LootItem.lootTableItem((ItemLike)ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get())
                                 .when(LootItemRandomChanceCondition.randomChance(0.05F)),
                              LootItem.lootTableItem((ItemLike)ESItems.DEEPSILVER_NUGGET.get())
                           }
                        )
                        .when(
                           BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), new float[]{0.15F, 0.25F, 0.5F, 1.0F})
                        ),
                     LootItem.lootTableItem(block)
                  }
               )
            )
         )
      );
      this.add((Block)ESBlocks.SUSPICIOUS_DIMSLAG.get(), noDrop());
      this.dropSelf((Block)ESBlocks.STARLIGHT_FLOWER.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_STARLIGHT_FLOWER.get());
      this.dropSelf((Block)ESBlocks.AUREATE_FLOWER.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_AUREATE_FLOWER.get());
      this.dropSelf((Block)ESBlocks.CONEBLOOM.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_CONEBLOOM.get());
      this.dropSelf((Block)ESBlocks.NIGHTFAN.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_NIGHTFAN.get());
      this.dropSelf((Block)ESBlocks.PINK_ROSE.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_PINK_ROSE.get());
      this.dropSelf((Block)ESBlocks.STARLIGHT_TORCHFLOWER.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get());
      this.add((Block)ESBlocks.NIGHTFAN_BUSH.get(), this::createDoublePlantDrops);
      this.add((Block)ESBlocks.PINK_ROSE_BUSH.get(), this::createDoublePlantDrops);
      this.plant((Block)ESBlocks.NIGHT_SPROUTS.get());
      this.plant((Block)ESBlocks.SMALL_NIGHT_SPROUTS.get());
      this.plant((Block)ESBlocks.GLOWING_NIGHT_SPROUTS.get());
      this.plant((Block)ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get());
      this.plant((Block)ESBlocks.LUNAR_GRASS.get());
      this.plant((Block)ESBlocks.GLOWING_LUNAR_GRASS.get());
      this.plant((Block)ESBlocks.CRESCENT_GRASS.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_CRESCENT_GRASS.get());
      this.plant((Block)ESBlocks.GLOWING_CRESCENT_GRASS.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get());
      this.plant((Block)ESBlocks.PARASOL_GRASS.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_PARASOL_GRASS.get());
      this.plant((Block)ESBlocks.GLOWING_PARASOL_GRASS.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get());
      this.plant((Block)ESBlocks.LUNAR_BUSH.get());
      this.plant((Block)ESBlocks.GLOWING_LUNAR_BUSH.get());
      this.add((Block)ESBlocks.TALL_CRESCENT_GRASS.get(), this::createDoublePlantDrops);
      this.add((Block)ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get(), this::createDoublePlantDrops);
      this.add((Block)ESBlocks.LUNAR_REED.get(), this::createDoublePlantDrops);
      this.dropSelf((Block)ESBlocks.WHISPERBLOOM.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_WHISPERBLOOM.get());
      this.dropSelf((Block)ESBlocks.GLADESPIKE.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_GLADESPIKE.get());
      this.plant((Block)ESBlocks.VIVIDSTALK.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_VIVIDSTALK.get());
      this.add((Block)ESBlocks.TALL_GLADESPIKE.get(), this::createDoublePlantDrops);
      this.plant((Block)ESBlocks.MOONLIGHT_BUSH.get());
      this.add((Block)ESBlocks.GLINTGRASS.get(), this::createDoublePlantDrops);
      this.dropSelf((Block)ESBlocks.GLOWING_MUSHROOM.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_GLOWING_MUSHROOM.get());
      this.add((Block)ESBlocks.GLOWING_MUSHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, (ItemLike)ESBlocks.GLOWING_MUSHROOM.get()));
      this.dropWhenSilkTouch((Block)ESBlocks.GLOWING_MUSHROOM_STEM.get());
      this.dropSelf((Block)ESBlocks.SHINING_MUSHROOM.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_SHINING_MUSHROOM.get());
      this.add((Block)ESBlocks.SHINING_MUSHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, (ItemLike)ESBlocks.SHINING_MUSHROOM.get()));
      this.dropWhenSilkTouch((Block)ESBlocks.SHINING_MUSHROOM_STEM.get());
      this.add((Block)ESBlocks.BERRIES_VINES.get(), this::createBerriesVinesDrop);
      this.add((Block)ESBlocks.BERRIES_VINES_PLANT.get(), this::createBerriesVinesDrop);
      this.plant((Block)ESBlocks.CAVE_MOSS.get());
      this.plantDropOther((Block)ESBlocks.CAVE_MOSS_PLANT.get(), (ItemLike)ESBlocks.CAVE_MOSS.get());
      this.plantDropOther((Block)ESBlocks.CAVE_MOSS_VEIN.get(), (ItemLike)ESBlocks.CAVE_MOSS.get());
      this.dropSelf(ESBlocks.CAVE_MOSS_BLOCK.get());
      this.dropSelf((Block)ESBlocks.CAVE_MOSS_CARPET.get());
      this.dropSelf(ESBlocks.BOULDERSHROOM.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_BOULDERSHROOM.get());
      this.add((Block)ESBlocks.BOULDERSHROOM_BLOCK.get(), block -> this.createMushroomBlockDrop(block, (ItemLike)ESBlocks.BOULDERSHROOM.get()));
      this.dropWhenSilkTouch((Block)ESBlocks.BOULDERSHROOM_STEM.get());
      this.add((Block)ESBlocks.BOULDERSHROOM_ROOTS.get(), noDrop());
      this.add((Block)ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get(), noDrop());
      this.dropSelf((Block)ESBlocks.SWAMP_ROSE.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_SWAMP_ROSE.get());
      this.plant((Block)ESBlocks.FANTABUD.get());
      this.plant((Block)ESBlocks.GREEN_FANTABUD.get());
      this.plant((Block)ESBlocks.FANTAFERN.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_FANTAFERN.get());
      this.plant((Block)ESBlocks.GREEN_FANTAFERN.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_GREEN_FANTAFERN.get());
      this.plant((Block)ESBlocks.FANTAGRASS.get());
      this.plant((Block)ESBlocks.GREEN_FANTAGRASS.get());
      this.plant((Block)ESBlocks.HANGING_FANTAGRASS.get());
      this.plant((Block)ESBlocks.HANGING_FANTAGRASS_PLANT.get());
      this.plant((Block)ESBlocks.ORANGE_SCARLET_BUD.get());
      this.plant((Block)ESBlocks.PURPLE_SCARLET_BUD.get());
      this.plant((Block)ESBlocks.RED_SCARLET_BUD.get());
      this.plant((Block)ESBlocks.SCARLET_GRASS.get());
      this.plant((Block)ESBlocks.MAUVE_FERN.get());
      this.dropSelf((Block)ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get());
      this.dropSelf((Block)ESBlocks.AMARAMBER_GRASS.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_AMARAMBER_GRASS.get());
      this.dropSelf((Block)ESBlocks.AMARAMBER_GRASS_BUSH.get());
      this.plant((Block)ESBlocks.GLOOMCANDLE_ROOT.get());
      this.dropSelf(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
      this.dropSelf(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
      this.add(
         ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                     (ItemLike)ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()
                  )
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                  .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
               .otherwise(
                  (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
                     block,
                     LootItem.lootTableItem((ItemLike)ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                  )
               )
         )
      );
      this.add(
         ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                     (ItemLike)ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()
                  )
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                  .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
               .otherwise(
                  (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
                     block,
                     LootItem.lootTableItem((ItemLike)ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                  )
               )
         )
      );
      this.dropSelf(ESBlocks.RED_STARLIGHT_CRYSTAL_LANTERN.get());
      this.dropSelf(ESBlocks.BLUE_STARLIGHT_CRYSTAL_LANTERN.get());
      this.deadBush((Block)ESBlocks.DEAD_LUNAR_BUSH.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_DEAD_LUNAR_BUSH.get());
      this.dropSelf((Block)ESBlocks.DESERT_AMETHYSIA.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_DESERT_AMETHYSIA.get());
      this.dropSelf((Block)ESBlocks.WITHERED_DESERT_AMETHYSIA.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get());
      this.dropSelf((Block)ESBlocks.SUNSET_THORNBLOOM.get());
      this.add((Block)ESBlocks.AMETHYSIA_GRASS.get(), block -> this.createPlantDrops(block, ESItems.CRINOA_SEEDS.get()));
      this.dropPottedContents((Block)ESBlocks.POTTED_SUNSET_THORNBLOOM.get());
      this.add(ESBlocks.LUNARIS_CACTUS.get(), this::createLunarisCactusDrop);
      this.dropSelf((Block)ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());
      this.dropSelf((Block)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get());
      this.dropSelf((Block)ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get());
      this.add(
         ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                     (ItemLike)ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()
                  )
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                  .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
               .otherwise(
                  (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
                     block,
                     LootItem.lootTableItem((ItemLike)ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                  )
               )
         )
      );
      this.add(
         ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(
                     (ItemLike)ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()
                  )
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                  .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
               .otherwise(
                  (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
                     block,
                     LootItem.lootTableItem((ItemLike)ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                  )
               )
         )
      );
      this.dropSelf((Block)ESBlocks.RED_CRYSTALFLEUR.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_RED_CRYSTALFLEUR.get());
      this.dropSelf((Block)ESBlocks.BLUE_CRYSTALFLEUR.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_BLUE_CRYSTALFLEUR.get());
      this.add((Block)ESBlocks.RED_CRYSTALFLEUR_VINE.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
      this.add((Block)ESBlocks.BLUE_CRYSTALFLEUR_VINE.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
      this.dropSelf(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get());
      this.dropSelf(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get());
      this.dropSelf((Block)ESBlocks.RED_CRYSTAL_MOSS_CARPET.get());
      this.dropSelf((Block)ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get());
      this.dropSelf((Block)ESBlocks.FIRE_ORCHID.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_FIRE_ORCHID.get());
      this.plant((Block)ESBlocks.BLAZEBANK_GRASS.get());
      this.dropSelf((Block)ESBlocks.MOONLIGHT_LILY_PAD.get());
      this.dropSelf((Block)ESBlocks.STARLIT_LILY_PAD.get());
      this.dropSelf((Block)ESBlocks.MOONLIGHT_DUCKWEED.get());
      this.add((Block)ESBlocks.ABYSSAL_KELP.get(), this::createAbyssalKelpDrop);
      this.add((Block)ESBlocks.ABYSSAL_KELP_PLANT.get(), this::createAbyssalKelpDrop);
      this.dropSelf((Block)ESBlocks.ORBFLORA.get());
      this.dropOther((Block)ESBlocks.ORBFLORA_PLANT.get(), (ItemLike)ESBlocks.ORBFLORA.get());
      this.dropSelf(ESBlocks.ORBFLORA_LIGHT.get());
      this.dropSelf((Block)ESBlocks.SPIRAL_KELP.get());
      this.dropOther((Block)ESBlocks.SPIRAL_KELP_PLANT.get(), (ItemLike)ESBlocks.SPIRAL_KELP.get());
      this.add((Block)ESBlocks.SEA_ROSA.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
      this.plant((Block)ESBlocks.WICK_GRASS.get());
      this.dropSelf((Block)ESBlocks.LUMENSTEM.get());
      this.dropOther((Block)ESBlocks.LUMENSTEM_PLANT.get(), (ItemLike)ESBlocks.LUMENSTEM.get());
      this.plant((Block)ESBlocks.MARIMOLD.get());
      this.add((Block)ESBlocks.MARIMOLD_BLOCK.get(), block -> this.createMushroomBlockDrop(block, (ItemLike)ESBlocks.MARIMOLD.get()));
      this.dropWhenSilkTouch((Block)ESBlocks.MARIMOLD_STEM.get());
      this.plant((Block)ESBlocks.CIRCULUSH.get());
      this.plant((Block)ESBlocks.STONETT.get());
      this.plant((Block)ESBlocks.LUMINIS.get());
      this.plant((Block)ESBlocks.GLOWLIS.get());
      this.plant((Block)ESBlocks.GLOREED.get());
      this.plant((Block)ESBlocks.STARLIGHT_SEAGRASS.get());
      this.dropSelf((Block)ESBlocks.JINGLING_PICKLE.get());
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_TENTACLES_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.TENTACLES_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
      this.dropWhenSilkTouch((Block)ESBlocks.TENTACLES_CORAL_FAN.get());
      this.dropSelf(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get());
      this.add(
         (Block)ESBlocks.TENTACLES_CORAL_BLOCK.get(),
         block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get())
      );
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_GOLDEN_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.GOLDEN_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
      this.dropWhenSilkTouch((Block)ESBlocks.GOLDEN_CORAL_FAN.get());
      this.dropSelf(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get());
      this.add(
         (Block)ESBlocks.GOLDEN_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get())
      );
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_CRYSTALLUM_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.CRYSTALLUM_CORAL.get());
      this.dropWhenSilkTouch((Block)ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
      this.dropWhenSilkTouch((Block)ESBlocks.CRYSTALLUM_CORAL_FAN.get());
      this.dropSelf(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
      this.add(
         (Block)ESBlocks.CRYSTALLUM_CORAL_BLOCK.get(),
         block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get())
      );
      this.add(
         ESBlocks.VELVETUMOSS.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionCondition(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.VELVETUMOSS_BALL.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropWhenSilkTouch(ESBlocks.VELVETUMOSS_VILLI.get());
      this.dropSelf(ESBlocks.RED_VELVETUMOSS.get());
      this.dropWhenSilkTouch(ESBlocks.RED_VELVETUMOSS_VILLI.get());
      this.dropSelf((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get());
      this.dropPottedContents((Block)ESBlocks.POTTED_RED_VELVETUMOSS_FLOWER.get());
      this.plant((Block)ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
      this.dropSelf((Block)ESBlocks.RED_CRYSTAL_ROOTS.get());
      this.dropSelf((Block)ESBlocks.BLUE_CRYSTAL_ROOTS.get());
      this.add((Block)ESBlocks.TWILVEWRYM_HERB.get(), this::createDoublePlantDrops);
      this.add((Block)ESBlocks.STELLAFLY_BUSH.get(), this::createDoublePlantDrops);
      this.add((Block)ESBlocks.GLIMMERFLY_BUSH.get(), this::createDoublePlantDrops);
      this.plant((Block)ESBlocks.GOLDEN_GRASS.get());
      this.add((Block)ESBlocks.TALL_GOLDEN_GRASS.get(), this::createDoublePlantDrops);
      this.dropSelf(ESBlocks.NIGHTFALL_DIRT.get());
      this.dropOther((Block)ESBlocks.NIGHTFALL_FARMLAND.get(), (ItemLike)ESBlocks.NIGHTFALL_DIRT.get());
      this.dropOther((Block)ESBlocks.NIGHTFALL_DIRT_PATH.get(), (ItemLike)ESBlocks.NIGHTFALL_DIRT.get());
      this.add((Block)ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.NIGHTFALL_DIRT.get()));
      this.add((Block)ESBlocks.NIGHTFALL_PODZOL.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.NIGHTFALL_DIRT.get()));
      this.add(
         (Block)ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(),
         block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.NIGHTFALL_DIRT.get())
      );
      this.add((Block)ESBlocks.FANTASY_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.NIGHTFALL_MUD.get()));
      this.dropSelf((Block)ESBlocks.FANTASY_GRASS_CARPET.get());
      this.add((Block)ESBlocks.GOLDEN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, (ItemLike)ESBlocks.NIGHTFALL_DIRT.get()));
      this.add(
         (Block)ESBlocks.CRINOA.get(),
         this.createCropDrops(
            (Block)ESBlocks.CRINOA.get(),
            ESItems.CRINOA.get(),
            ESItems.CRINOA_SEEDS.get(),
            LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)ESBlocks.CRINOA.get())
               .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(CrinoaBlock.AGE, 7))
         )
      );
      this.dropSelf(ESBlocks.CRINOA_BALE.get());
      this.add(
         (Block)ESBlocks.NOCTURNAL_MILLET_PANICLE.get(),
         this.createCropDrops(
            (Block)ESBlocks.NOCTURNAL_MILLET_PANICLE.get(),
            ESItems.NOCTURNAL_MILLET.get(),
            ESItems.NOCTURNAL_MILLET_SEEDS.get(),
            LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)ESBlocks.NOCTURNAL_MILLET_PANICLE.get())
               .setProperties(
                  net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                     .hasProperty(NocturnalMilletTopBlock.AGE, 2)
                     .hasProperty(NocturnalMilletTopBlock.FORGOTTEN, false)
               )
         )
      );
      this.dropOther((Block)ESBlocks.NOCTURNAL_MILLET_STALK.get(), (ItemLike)ESItems.NOCTURNAL_MILLET_SEEDS.get());
      this.dropSelf(ESBlocks.RAW_AETHERSENT_BLOCK.get());
      this.dropSelf(ESBlocks.AETHERSENT_BLOCK.get());
      this.dropSelf(ESBlocks.SPRINGSTONE.get());
      this.add((Block)ESBlocks.SPRINGSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.SPRINGSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.SPRINGSTONE_WALL.get());
      this.dropSelf(ESBlocks.SPRINGSTONE_BRICKS.get());
      this.add((Block)ESBlocks.SPRINGSTONE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.SPRINGSTONE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.SPRINGSTONE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_SPRINGSTONE.get());
      this.add((Block)ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_SPRINGSTONE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_SPRINGSTONE.get());
      this.dropSelf(ESBlocks.THERMAL_SPRINGSTONE.get());
      this.add((Block)ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.THERMAL_SPRINGSTONE_WALL.get());
      this.dropSelf(ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get());
      this.add((Block)ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get());
      this.add(
         (Block)ESBlocks.GLACITE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.GLACITE_SHARD.get())
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropSelf(ESBlocks.GLACITE_BLOCK.get());
      this.add(
         (Block)ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARLIT_DIAMOND.get())
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARLIT_DIAMOND.get())
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARLIT_DIAMOND.get())
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.STARLIT_DIAMOND.get())
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropSelf(ESBlocks.STARLIT_DIAMOND_BLOCK.get());
      this.add(ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.add(ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.add(ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.add(ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.add(ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.add(ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
      this.dropSelf(ESBlocks.RAW_DEEPSILVER_BLOCK.get());
      this.dropSelf(ESBlocks.DEEPSILVER_BLOCK.get());
      this.dropSelf((Block)ESBlocks.DEEPSILVER_GRATE.get());
      this.dropSelf((Block)ESBlocks.DEEPSILVER_BARS.get());
      this.dropSelf(ESBlocks.UNREALIUM_BLOCK.get());
      this.dropSelf((Block)ESBlocks.UNREALIUM_BARS.get());
      this.add(
         (Block)ESBlocks.GRIMSTONE_MALARITE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.MALARITE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.VOIDSTONE_MALARITE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.MALARITE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.MALARITE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.MALARITE.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropSelf(ESBlocks.MALARITE_BLOCK.get());
      this.add(
         (Block)ESBlocks.GRIMSTONE_SALTPETER_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.SALTPETER_POWDER.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.VOIDSTONE_SALTPETER_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.SALTPETER_POWDER.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.SALTPETER_POWDER.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.HAZE_ICE_SALTPETER_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem((ItemLike)ESItems.SALTPETER_POWDER.get())
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropSelf(ESBlocks.SALTPETER_BLOCK.get());
      this.add(
         (Block)ESBlocks.PUNGENCY_FRUIT_VINES.get(),
         this.createCropDrops(
            (Block)ESBlocks.PUNGENCY_FRUIT_VINES.get(),
            ESItems.PUNGENCY_FRUIT.get(),
            ESItems.PUNGENCY_FRUIT_SEEDS.get(),
            LootItemBlockStatePropertyCondition.hasBlockStateProperties((Block)ESBlocks.PUNGENCY_FRUIT_VINES.get())
               .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(PungencyFruitVinesBlock.AGE, 7))
         )
      );
      this.dropSelf((Block)ESBlocks.TEAR_BOMB.get());
      this.dropSelf((Block)ESBlocks.DRYING_RACK.get());
      this.add((Block)ESBlocks.STARFIRE_BIRD_NEST.get(), this::createStarfireBirdNestDrop);
      this.add((Block)ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.add((Block)ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
      this.dropSelf(ESBlocks.RAW_FLOWGLAZE.get());
      this.dropSelf((Block)ESBlocks.FLOWGLAZE.get());
      this.dropSelf((Block)ESBlocks.FLOWGLAZE_PANE.get());
      this.dropSelf(ESBlocks.FLOWGLAZE_BRICKS.get());
      this.add((Block)ESBlocks.FLOWGLAZE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.FLOWGLAZE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.FLOWGLAZE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.RAW_AMARAMBER_BLOCK.get());
      this.dropSelf((Block)ESBlocks.AMARAMBER_LANTERN.get());
      this.add((Block)ESBlocks.AMARAMBER_CANDLE.get(), x$0 -> this.createCandleDrops(x$0));
      this.add((Block)ESBlocks.AMARAMBER_CANDLE_CAKE.get(), createCandleCakeDrops((Block)ESBlocks.AMARAMBER_CANDLE.get()));
      this.add((Block)ESBlocks.AMARAMBER_FIRE.get(), noDrop());
      this.dropSelf(ESBlocks.AMARAMBER_BRICKS.get());
      this.add((Block)ESBlocks.AMARAMBER_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.AMARAMBER_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.AMARAMBER_BRICK_WALL.get());
      this.dropSelf(ESBlocks.TORREYA_TILES.get());
      this.add((Block)ESBlocks.TORREYA_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TORREYA_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TORREYA_TILE_WALL.get());
      this.add(ESBlocks.THIOQUARTZ_BLOCK.get(), this::createThioquartzBlockDrop);
      this.add(ESBlocks.BUDDING_THIOQUARTZ.get(), noDrop());
      this.add(
         ESBlocks.THIOQUARTZ_CLUSTER.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)ESItems.THIOQUARTZ_SHARD.get())
                  .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                  .when(MatchTool.toolMatches(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
               .otherwise(
                  (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
                     block, LootItem.lootTableItem((ItemLike)ESItems.THIOQUARTZ_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                  )
               )
         )
      );
      this.dropSelf(ESBlocks.TOXITE.get());
      this.add((Block)ESBlocks.TOXITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TOXITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TOXITE_WALL.get());
      this.dropSelf(ESBlocks.TOXITE_BRICKS.get());
      this.add((Block)ESBlocks.TOXITE_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TOXITE_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TOXITE_BRICK_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_TOXITE.get());
      this.add((Block)ESBlocks.POLISHED_TOXITE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_TOXITE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_TOXITE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_TOXITE.get());
      this.add(
         (Block)ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem(Items.REDSTONE)
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         (Block)ESBlocks.VOIDSTONE_REDSTONE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem(Items.REDSTONE)
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem(Items.REDSTONE)
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.add(
         ESBlocks.HAZE_ICE_REDSTONE_ORE.get(),
         block -> this.createSilkTouchDispatchTable(
            block,
            (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
               block,
               LootItem.lootTableItem(Items.REDSTONE)
                  .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                  .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
            )
         )
      );
      this.dropSelf((Block)ESBlocks.WHITE_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.ORANGE_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.MAGENTA_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.LIGHT_BLUE_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.YELLOW_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.LIME_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.PINK_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.GRAY_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.LIGHT_GRAY_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.CYAN_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.PURPLE_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.BLUE_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.BROWN_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.GREEN_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.RED_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.BLACK_YETI_FUR.get());
      this.dropSelf((Block)ESBlocks.WHITE_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.ORANGE_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.YELLOW_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.LIME_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.PINK_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.GRAY_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.CYAN_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.PURPLE_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.BLUE_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.BROWN_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.GREEN_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.RED_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.BLACK_YETI_FUR_CARPET.get());
      this.dropSelf((Block)ESBlocks.TANGLED_SKULL.get());
      this.dropOther((Block)ESBlocks.TANGLED_WALL_SKULL.get(), (ItemLike)ESBlocks.TANGLED_SKULL.get());
      this.dropSelf(ESBlocks.TOOTH_OF_HUNGER_TILES.get());
      this.add((Block)ESBlocks.TOOTH_OF_HUNGER_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.TOOTH_OF_HUNGER_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.TOOTH_OF_HUNGER_TILE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_TOOTH_OF_HUNGER_TILES.get());
      this.dropSelf((Block)ESBlocks.CRYSTALBORN_CATALYST.get());
      this.dropSelf(ESBlocks.CRYSTALLIZED_SAND.get());
      this.add((Block)ESBlocks.LOOT_CHEST.get(), noDrop());
      this.add((Block)ESBlocks.THE_GATEKEEPER_SPAWNER.get(), noDrop());
      this.add((Block)ESBlocks.STARLIGHT_GOLEM_SPAWNER.get(), noDrop());
      this.add((Block)ESBlocks.PERMAFROST_SPAWNER.get(), noDrop());
      this.add((Block)ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get(), noDrop());
      this.add((Block)ESBlocks.SOLAR_EGG.get(), noDrop());
      this.dropSelf(ESBlocks.GOLEM_STEEL_BLOCK.get());
      this.dropSelf(ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get());
      this.dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
      this.add((Block)ESBlocks.GOLEM_STEEL_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.add((Block)ESBlocks.WAXED_GOLEM_STEEL_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.add((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_STAIRS.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get());
      this.dropSelf(ESBlocks.GOLEM_STEEL_TILES.get());
      this.dropSelf(ESBlocks.WAXED_GOLEM_STEEL_TILES.get());
      this.dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
      this.add((Block)ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.add((Block)ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.add((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_GRATE.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_GRATE.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get());
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_PILLAR.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get());
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_BARS.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_BARS.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get());
      this.dropSelf(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
      this.dropSelf(ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get());
      this.dropSelf(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
      this.dropSelf((Block)ESBlocks.GOLEM_STEEL_JET.get());
      this.dropSelf((Block)ESBlocks.WAXED_GOLEM_STEEL_JET.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get());
      this.add((Block)ESBlocks.GOLEM_STEEL_CRATE.get(), x$0 -> this.createShulkerBoxDrop(x$0));
      this.dropSelf((Block)ESBlocks.ENERGY_TRANSMITTER.get());
      this.dropSelf((Block)ESBlocks.ACCUMULATOR.get());
      this.add((Block)ESBlocks.MECHANICAL_SPAWNER.get(), noDrop());
      this.dropSelf((Block)ESBlocks.ALLOY_FURNACE.get());
      this.dropSelf((Block)ESBlocks.WAXED_ALLOY_FURNACE.get());
      this.dropSelf((Block)ESBlocks.OXIDIZED_ALLOY_FURNACE.get());
      this.add(ESBlocks.ENERGY_BLOCK.get(), noDrop());
      this.dropSelf(ESBlocks.SHADEGRIEVE.get());
      this.dropSelf(ESBlocks.BLOOMING_SHADEGRIEVE.get());
      this.dropSelf((Block)ESBlocks.LUNAR_VINE.get());
      this.dropSelf(ESBlocks.LUNAR_MOSAIC.get());
      this.add((Block)ESBlocks.LUNAR_MOSAIC_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.LUNAR_MOSAIC_STAIRS.get());
      this.dropSelf((Block)ESBlocks.LUNAR_MOSAIC_FENCE.get());
      this.dropSelf((Block)ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get());
      this.dropSelf((Block)ESBlocks.LUNAR_MAT.get());
      this.dropWhenSilkTouch((Block)ESBlocks.DUSK_GLASS.get());
      this.dropSelf((Block)ESBlocks.DUSK_LIGHT.get());
      this.dropSelf((Block)ESBlocks.REINFORCED_DUSK_LIGHT.get());
      this.dropSelf((Block)ESBlocks.DUSK_EMITTER.get());
      this.add((Block)ESBlocks.DUSK_LOCKBOX.get(), noDrop());
      this.add((Block)ESBlocks.FLARE_SPAWNER.get(), noDrop());
      this.add((Block)ESBlocks.ECLIPSE_CORE.get(), noDrop());
      this.dropSelf((Block)ESBlocks.DOOMED_TORCH.get());
      this.dropOther((Block)ESBlocks.WALL_DOOMED_TORCH.get(), (ItemLike)ESItems.DOOMED_TORCH.get());
      this.dropSelf((Block)ESBlocks.DOOMED_REDSTONE_TORCH.get());
      this.dropOther((Block)ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get(), (ItemLike)ESItems.DOOMED_REDSTONE_TORCH.get());
      this.dropSelf(ESBlocks.DOOMEDEN_BRICKS.get());
      this.add((Block)ESBlocks.DOOMEDEN_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.DOOMEDEN_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.DOOMEDEN_BRICK_WALL.get());
      this.dropSelf(ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
      this.add((Block)ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
      this.dropSelf((Block)ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get());
      this.dropSelf(ESBlocks.DOOMEDEN_TILES.get());
      this.add((Block)ESBlocks.DOOMEDEN_TILE_SLAB.get(), x$0 -> this.createSlabItemTable(x$0));
      this.dropSelf((Block)ESBlocks.DOOMEDEN_TILE_STAIRS.get());
      this.dropSelf((Block)ESBlocks.DOOMEDEN_TILE_WALL.get());
      this.dropSelf(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.dropSelf(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.dropSelf((Block)ESBlocks.DOOMEDEN_LIGHT.get());
      this.dropSelf(ESBlocks.DOOMEDEN_KEYHOLE.get());
      this.dropSelf(ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get());
      this.dropSelf((Block)ESBlocks.STELLAR_RACK.get());
      this.add((Block)ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), noDrop());
      this.add((Block)ESBlocks.STARLIGHT_PORTAL.get(), noDrop());
   }

   protected Iterable<Block> getKnownBlocks() {
      return BuiltInRegistries.BLOCK
         .stream()
         .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals("eternal_starlight"))
         .collect(Collectors.toList());
   }

   private void plant(Block block) {
      this.add(block, this::createSickleOrShearsOnlyDrop);
   }

   private void plantDropOther(Block block, ItemLike other) {
      this.add(block, this.createSickleOrShearsOnlyDrop(other));
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createSickleOrShearsOnlyDrop(ItemLike item) {
      return LootTable.lootTable()
         .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS_OR_SICKLE).add(LootItem.lootTableItem(item)));
   }

   private void deadBush(Block block) {
      this.add(block, b -> this.createPlantDrops(b, Items.STICK));
   }

   public net.minecraft.world.level.storage.loot.LootTable.Builder createPlantDrops(Block block, Item additional) {
      RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return this.createShearsDispatchTable(
         block,
         (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)this.applyExplosionDecay(
            block,
            ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(additional)
                  .when(LootItemRandomChanceCondition.randomChance(0.125F)))
               .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE), 2))
         )
      );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder createDoublePlantDrops(Block block) {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(LootItem.lootTableItem(block).when(HAS_SHEARS_OR_SICKLE))
               .when(
                  LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                     .setProperties(
                        net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                           .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                     )
                     .and(
                        LocationCheck.checkLocation(
                           net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                              .setBlock(
                                 net.minecraft.advancements.critereon.BlockPredicate.Builder.block()
                                    .of(new Block[]{block})
                                    .setProperties(
                                       net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                          .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                                    )
                              ),
                           new BlockPos(0, 1, 0)
                        )
                     )
                     .or(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                           .setProperties(
                              net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                 .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                           )
                           .and(
                              LocationCheck.checkLocation(
                                 net.minecraft.advancements.critereon.LocationPredicate.Builder.location()
                                    .setBlock(
                                       net.minecraft.advancements.critereon.BlockPredicate.Builder.block()
                                          .of(new Block[]{block})
                                          .setProperties(
                                             net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                          )
                                    ),
                                 new BlockPos(0, -1, 0)
                              )
                           )
                     )
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createLunarisCactusDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
               block,
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(LootItem.lootTableItem(block))
                  .when(
                     LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(
                           net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(LunarisCactusBlock.FRUIT, false)
                        )
                  )
            )
         )
         .withPool(
            LootPool.lootPool()
               .add(LootItem.lootTableItem((ItemLike)ESItems.LUNARIS_CACTUS_FRUIT.get()))
               .when(
                  LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                     .setProperties(
                        net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(LunarisCactusBlock.FRUIT, true)
                     )
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createBerriesVinesDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(LootItem.lootTableItem((ItemLike)ESItems.LUNAR_BERRIES.get()))
               .when(
                  LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                     .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(BerriesVines.BERRIES, true))
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createAbyssalKelpDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .add(LootItem.lootTableItem((ItemLike)ESItems.ABYSSAL_FRUIT.get()))
               .when(
                  LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                     .setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(AbyssalKelp.BERRIES, true))
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createLunarLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
      RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
      return this.createLeavesDrops(leaves, sapling, saplingChances)
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .when(this.doesNotHaveShearsOrSilkTouch())
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)this.applyExplosionCondition(
                        leaves, LootItem.lootTableItem((ItemLike)ESItems.LUNAR_BERRIES.get())
                     ))
                     .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                           registrylookup.getOrThrow(Enchantments.FORTUNE), new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F}
                        )
                     )
               )
         );
   }

   private net.minecraft.world.level.storage.loot.LootTable.Builder createThioquartzBlockDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
               block,
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem((ItemLike)ESItems.NOCTURNAL_MILLET_SEEDS.get())
                        .when(
                           LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                              .setProperties(
                                 net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(ThioquartzBlock.SEED, true)
                              )
                        )
                  )
            )
         )
         .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)));
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder createStarfireBirdNestDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .when(this.hasSilkTouch())
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  LootItem.lootTableItem(block)
                     .apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include(DataComponents.CONTAINER).include(ESDataComponents.BIRDS.get()))
                     .apply(CopyBlockState.copyState(block).copy(StarfireBirdNestBlock.EGGS))
               )
         );
   }

   protected net.minecraft.world.level.storage.loot.LootTable.Builder createStarfireBirdAviaryDrop(Block block) {
      return LootTable.lootTable()
         .withPool(
            LootPool.lootPool()
               .setRolls(ConstantValue.exactly(1.0F))
               .add(
                  ((net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder)LootItem.lootTableItem(block).when(this.hasSilkTouch()))
                     .apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include(DataComponents.CONTAINER).include(ESDataComponents.BIRDS.get()))
                     .apply(CopyBlockState.copyState(block).copy(StarfireBirdNestBlock.EGGS))
                     .otherwise(
                        LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include(DataComponents.CONTAINER))
                     )
               )
         );
   }

   private Builder hasShearsOrSilkTouch() {
      return HAS_SHEARS.or(this.hasSilkTouch());
   }

   private Builder doesNotHaveShearsOrSilkTouch() {
      return this.hasShearsOrSilkTouch().invert();
   }
}
