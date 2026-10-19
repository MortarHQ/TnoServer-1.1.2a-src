package net.darkhax.bookshelf.common.api.registry.register;

import java.util.List;
import net.darkhax.bookshelf.common.api.data.loot.PoolTarget;
import net.darkhax.bookshelf.common.api.data.loot.modifiers.LootPoolAddition;
import net.darkhax.bookshelf.common.impl.data.loot.entries.LootItemStack;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public record RegisterLootPoolAdditions(String owner, RegisterLootPoolAdditions.RegisterFunc registerFunc) {
   public void add(String id, PoolTarget pool, ItemStack item, int weight) {
      this.add(id, pool.table(), pool.index(), pool.hash(), item, weight);
   }

   public void add(String id, PoolTarget pool, Item item, int weight) {
      this.add(id, pool.table(), pool.index(), pool.hash(), item, weight);
   }

   public void add(String id, ResourceKey<LootTable> tableId, int poolIndex, int poolHash, ItemStack item, int weight) {
      this.add(id, tableId.location(), poolIndex, poolHash, item, weight);
   }

   public void add(String id, ResourceLocation tableId, int poolIndex, int poolHash, ItemStack item, int weight) {
      this.add(id, tableId, poolIndex, poolHash, LootItemStack.of(item, weight));
   }

   public void add(String id, ResourceKey<LootTable> tableId, int poolIndex, int poolHash, Item item, int weight) {
      this.add(id, tableId.location(), poolIndex, poolHash, item, weight);
   }

   public void add(String id, ResourceLocation tableId, int poolIndex, int poolHash, Item item, int weight) {
      this.add(id, tableId, poolIndex, poolHash, AccessorLootItem.bookshelf$create(item.builtInRegistryHolder(), weight, 0, List.of(), List.of()));
   }

   public void add(String id, PoolTarget pool, LootPoolEntryContainer addition) {
      this.add(id, pool.table(), pool.index(), pool.hash(), addition);
   }

   public void add(String id, ResourceKey<LootTable> tableId, int poolIndex, int poolHash, LootPoolEntryContainer addition) {
      this.add(id, tableId.location(), poolIndex, poolHash, addition);
   }

   public void add(String id, ResourceLocation tableId, int poolIndex, int poolHash, LootPoolEntryContainer addition) {
      this.registerFunc.register(tableId, poolIndex, poolHash, new LootPoolAddition(this.id(id), addition));
   }

   private ResourceLocation id(String id) {
      return ResourceLocation.fromNamespaceAndPath(this.owner, id);
   }

   @FunctionalInterface
   public interface RegisterFunc {
      void register(ResourceLocation var1, int var2, int var3, LootPoolAddition var4);
   }
}
