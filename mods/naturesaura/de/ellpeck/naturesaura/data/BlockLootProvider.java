package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.blocks.BlockFlowerPot;
import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.blocks.Slab;
import de.ellpeck.naturesaura.items.ModItems;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BlockLootProvider extends BlockLootSubProvider {
   public BlockLootProvider(Provider registries) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
   }

   protected void generate() {
      ModData.getAllModItems().forEach(item -> {
         if (item instanceof Block block) {
            if (block instanceof Slab) {
               this.add(block, x$0 -> this.createSlabItemTable(x$0));
            } else if (block instanceof BlockFlowerPot) {
               this.add(block, x$0 -> this.createPotFlowerItemTable(x$0));
            } else {
               this.dropSelf(block);
            }
         }
      });
      this.add(ModBlocks.ANCIENT_LEAVES, x$0 -> this.createSilkTouchOnlyTable(x$0));
      this.add(ModBlocks.DECAYED_LEAVES, x$0 -> this.createSilkTouchOnlyTable(x$0));
      this.add(
         ModBlocks.GOLDEN_LEAVES,
         b -> LootTable.lootTable()
            .withPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     ((Builder)this.applyExplosionCondition(b, LootItem.lootTableItem(ModItems.GOLD_LEAF)))
                        .when(
                           LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                              .setProperties(
                                 net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(BlockGoldenLeaves.STAGE, 3)
                              )
                        )
                  )
                  .when(LootItemRandomChanceCondition.randomChance(0.75F))
            )
      );
      this.add(
         ModBlocks.NETHER_WART_MUSHROOM,
         b -> this.createSilkTouchDispatchTable(
            b, LootItem.lootTableItem(Items.NETHER_WART).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
         )
      );
      this.add(ModBlocks.NETHER_GRASS, b -> this.createSilkTouchDispatchTable(b, LootItem.lootTableItem(Blocks.NETHERRACK)));
   }

   protected Iterable<Block> getKnownBlocks() {
      return ModData.getAllModItems().filter(i -> i instanceof Block).map(i -> (Block)i).toList();
   }
}
