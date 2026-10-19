package com.almostreliable.lootjs.loot;

import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public record LootFunction() implements LootFunctionsContainer<LootItemFunction> {
   public LootItemFunction addFunction(LootItemFunction lootItemFunction) {
      return lootItemFunction;
   }
}
