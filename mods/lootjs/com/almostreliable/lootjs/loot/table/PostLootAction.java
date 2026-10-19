package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.LootBucket;
import net.minecraft.world.level.storage.loot.LootContext;

@FunctionalInterface
public interface PostLootAction {
   void alter(LootContext var1, LootBucket var2);
}
