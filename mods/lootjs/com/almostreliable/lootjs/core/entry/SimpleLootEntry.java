package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootFunctionList;
import com.almostreliable.lootjs.loot.LootFunctionsContainer;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public interface SimpleLootEntry extends LootEntry, LootFunctionsContainer<SimpleLootEntry> {
   LootFunctionList getFunctions();

   default SimpleLootEntry when(Consumer<LootConditionList> callback) {
      callback.accept(this.getConditions());
      return this;
   }

   default SimpleLootEntry apply(Consumer<LootFunctionList> callback) {
      callback.accept(this.getFunctions());
      return this;
   }

   default SimpleLootEntry addFunction(LootItemFunction lootItemFunction) {
      this.getFunctions().add(lootItemFunction);
      return this;
   }

   default SimpleLootEntry setCount(NumberProvider numberProvider) {
      LootItemFunction sc = SetItemCountFunction.setCount(numberProvider).build();
      if (!this.getFunctions().replace(LootItemFunctions.SET_COUNT, sc)) {
         this.getFunctions().add(sc);
      }

      return this;
   }

   default SimpleLootEntry withWeight(int weight) {
      this.setWeight(weight);
      return this;
   }

   void setWeight(int var1);

   int getWeight();

   default SimpleLootEntry withQuality(int quality) {
      this.setQuality(quality);
      return this;
   }

   void setQuality(int var1);

   int getQuality();
}
