package com.progwml6.ironchest.common.data.loot;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Collections;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction.Source;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public class IronChestsBlockLoot extends BlockLootSubProvider {
   private final Set<Block> knownBlocks = new ReferenceOpenHashSet();

   public IronChestsBlockLoot(Provider provider) {
      super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), provider);
   }

   protected void generate() {
      this.add((Block)IronChestsBlocks.IRON_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.GOLD_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.DIAMOND_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.COPPER_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.CRYSTAL_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.OBSIDIAN_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.DIRT_CHEST.get(), this::createDirtChestNameableBlockEntityTable);
      this.add((Block)IronChestsBlocks.TRAPPED_IRON_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_GOLD_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_COPPER_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(), x$0 -> this.createNameableBlockEntityTable(x$0));
      this.add((Block)IronChestsBlocks.TRAPPED_DIRT_CHEST.get(), this::createDirtChestNameableBlockEntityTable);
   }

   protected Builder createDirtChestNameableBlockEntityTable(Block pBlock) {
      return LootTable.lootTable()
         .withPool(
            (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
               pBlock,
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     LootItem.lootTableItem(pBlock)
                        .apply(
                           CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY)
                              .include(DataComponents.CUSTOM_NAME)
                              .include((DataComponentType)IronChestsDataComponents.CHEST_PLACED_ALREADY.get())
                        )
                  )
            )
         );
   }

   protected void add(@NotNull Block block, @NotNull Builder table) {
      super.add(block, table);
      this.knownBlocks.add(block);
   }

   protected Iterable<Block> getKnownBlocks() {
      return this.knownBlocks;
   }
}
