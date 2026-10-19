package net.p3pp3rf1y.sophisticatedbackpacks.data;

import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class SBPBlockLootSubProvider extends BlockLootSubProvider {
   protected SBPBlockLootSubProvider(Provider registries) {
      super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
   }

   public void generate() {
      this.add(ModBlocks.BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.BACKPACK.get()));
      this.add(ModBlocks.COPPER_BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.COPPER_BACKPACK.get()));
      this.add(ModBlocks.IRON_BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.IRON_BACKPACK.get()));
      this.add(ModBlocks.GOLD_BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.GOLD_BACKPACK.get()));
      this.add(ModBlocks.DIAMOND_BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.DIAMOND_BACKPACK.get()));
      this.add(ModBlocks.NETHERITE_BACKPACK.get(), dropBackpackWithContents((BackpackItem)ModItems.NETHERITE_BACKPACK.get()));
   }

   protected Iterable<Block> getKnownBlocks() {
      return BuiltInRegistries.BLOCK
         .entrySet()
         .stream()
         .filter(e -> ((ResourceKey)e.getKey()).location().getNamespace().equals("sophisticatedbackpacks"))
         .map(Entry::getValue)
         .toList();
   }

   private static net.minecraft.world.level.storage.loot.LootTable.Builder dropBackpackWithContents(BackpackItem item) {
      Builder<?> entry = LootItem.lootTableItem(item);
      net.minecraft.world.level.storage.loot.LootPool.Builder pool = LootPool.lootPool()
         .name("main")
         .setRolls(ConstantValue.exactly(1.0F))
         .add(entry)
         .apply(CopyBackpackDataFunction.builder());
      return LootTable.lootTable().withPool(pool);
   }
}
