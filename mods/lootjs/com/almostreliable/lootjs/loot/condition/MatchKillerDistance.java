package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record MatchKillerDistance(DistancePredicate predicate) implements LootItemCondition {
   public boolean test(LootContext context) {
      Entity entity = (Entity)context.getParamOrNull(LootContextParams.THIS_ENTITY);
      Entity killerEntity = (Entity)context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
      return entity != null
         && killerEntity != null
         && this.predicate.matches(entity.getX(), entity.getY(), entity.getZ(), killerEntity.getX(), killerEntity.getY(), killerEntity.getZ());
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.DISTANCE.value();
   }
}
