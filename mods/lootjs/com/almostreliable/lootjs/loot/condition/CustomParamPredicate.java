package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class CustomParamPredicate<T> implements LootItemCondition {
   private final Predicate<T> predicate;
   private final LootContextParam<T> param;

   public CustomParamPredicate(LootContextParam<T> param, Predicate<T> predicate) {
      Objects.requireNonNull(param);
      Objects.requireNonNull(predicate);
      this.param = param;
      this.predicate = predicate;
   }

   public boolean test(LootContext lootContext) {
      T paramOrNull = (T)lootContext.getParamOrNull(this.param);
      return paramOrNull != null && this.predicate.test(paramOrNull);
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.PARAM.value();
   }
}
