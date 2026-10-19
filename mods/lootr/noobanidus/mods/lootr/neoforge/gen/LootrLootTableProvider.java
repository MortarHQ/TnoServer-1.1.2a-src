package noobanidus.mods.lootr.neoforge.gen;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction.Source;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.neoforge.init.ModBlocks;

public class LootrLootTableProvider {
   public static LootTableProvider create(PackOutput output, CompletableFuture<Provider> provider) {
      return new LootTableProvider(
         output,
         Set.of(LootrAPI.ELYTRA_CHEST),
         List.of(
            new SubProviderEntry(LootrLootTableProvider.ChestLootTables::new, LootContextParamSets.CHEST),
            new SubProviderEntry(LootrLootTableProvider.LootrBlockLootTables::new, LootContextParamSets.BLOCK)
         ),
         provider
      );
   }

   public static class ChestLootTables implements LootTableSubProvider {
      public ChestLootTables(Provider provider) {
      }

      public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
         consumer.accept(
            LootrAPI.ELYTRA_CHEST,
            LootTable.lootTable()
               .withPool(
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(LootItem.lootTableItem(Items.ELYTRA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
               )
         );
         consumer.accept(
            LootrAPI.TROPHY_REWARD,
            LootTable.lootTable()
               .withPool(
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(LootItem.lootTableItem(LootrRegistry.getTrophyBlock()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
               )
         );
         consumer.accept(LootrAPI.ITEM_FRAME_EMPTY, LootTable.lootTable());
      }
   }

   public static class LootrBlockLootTables extends BlockLootSubProvider {
      protected LootrBlockLootTables(Provider arg2) {
         super(Set.of(), FeatureFlags.REGISTRY.allFlags(), arg2);
      }

      protected Builder lootrBlockDrop(Block block) {
         return LootTable.lootTable()
            .withPool(
               (net.minecraft.world.level.storage.loot.LootPool.Builder)this.applyExplosionCondition(
                  block,
                  LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1.0F))
                     .add(LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponents(Source.BLOCK_ENTITY).include(DataComponents.CUSTOM_NAME)))
               )
            );
      }

      protected void generate() {
         this.add((Block)ModBlocks.CHEST.get(), this.lootrBlockDrop(Blocks.CHEST));
         this.add((Block)ModBlocks.BARREL.get(), this.lootrBlockDrop(Blocks.BARREL));
         this.add((Block)ModBlocks.INVENTORY.get(), this.lootrBlockDrop(Blocks.CHEST));
         this.add((Block)ModBlocks.TRAPPED_CHEST.get(), this.lootrBlockDrop(Blocks.TRAPPED_CHEST));
         this.add((Block)ModBlocks.SHULKER.get(), this.lootrBlockDrop(Blocks.SHULKER_BOX));
         this.dropSelf((Block)ModBlocks.TROPHY.get());
      }

      public void generate(BiConsumer<ResourceKey<LootTable>, Builder> biConsumer) {
         this.generate();
         HashSet<ResourceKey<LootTable>> set = new HashSet<>();

         for (Block block : List.of(
            (Block)ModBlocks.CHEST.get(),
            (Block)ModBlocks.BARREL.get(),
            (Block)ModBlocks.INVENTORY.get(),
            (Block)ModBlocks.TRAPPED_CHEST.get(),
            (Block)ModBlocks.SHULKER.get(),
            (Block)ModBlocks.TROPHY.get()
         )) {
            ResourceKey<LootTable> resourceKey = block.getLootTable();
            if (resourceKey != BuiltInLootTables.EMPTY && set.add(resourceKey)) {
               Builder builder = (Builder)this.map.remove(resourceKey);
               if (builder == null) {
                  throw new IllegalStateException(
                     String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", resourceKey.location(), BuiltInRegistries.BLOCK.getKey(block))
                  );
               }

               biConsumer.accept(resourceKey, builder);
            }
         }

         if (!this.map.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
         }
      }
   }
}
