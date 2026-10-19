package com.almostreliable.lootjs.loot.modifier.handler;

import com.almostreliable.lootjs.core.LootBucket;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.loot.modifier.LootAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public record ModifyLootAction(ItemFilter predicate, ModifyLootAction.Callback callback) implements LootAction {
   @Override
   public void apply(LootContext context, LootBucket loot) {
      loot.modifyItems(itemStack -> this.predicate.test(itemStack) ? this.callback.modify(itemStack) : itemStack);
   }

   @FunctionalInterface
   public interface Callback {
      ItemStack modify(ItemStack var1);
   }
}
