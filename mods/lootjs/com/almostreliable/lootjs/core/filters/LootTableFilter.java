package com.almostreliable.lootjs.core.filters;

import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.loot.extension.LootContextExtension;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

public interface LootTableFilter {
   boolean test(LootTable var1);

   boolean test(LootContext var1);

   record ByIdFilter(IdFilter filter) implements LootTableFilter {
      @Override
      public boolean test(LootTable table) {
         return this.filter.test(table.getLootTableId());
      }

      @Override
      public boolean test(LootContext context) {
         return this.filter.test(context.getQueriedLootTableId());
      }
   }

   record ByLootType(LootType type) implements LootTableFilter {
      @Override
      public boolean test(LootTable lootTable) {
         return this.type == LootType.getLootType(lootTable.getParamSet());
      }

      @Override
      public boolean test(LootContext context) {
         return LootContextExtension.cast(context).lootjs$isType(this.type);
      }
   }
}
