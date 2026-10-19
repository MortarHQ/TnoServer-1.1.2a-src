package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record MatchDimension(ResourceLocation[] dimensions) implements LootItemCondition {
   public boolean test(LootContext context) {
      ResourceLocation levelDimension = context.getLevel().dimension().location();

      for (ResourceLocation dimension : this.dimensions) {
         if (dimension.equals(levelDimension)) {
            return true;
         }
      }

      return false;
   }

   public ResourceLocation[] dimensions() {
      return (ResourceLocation[])this.dimensions.clone();
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.ANY_DIMENSION.value();
   }
}
