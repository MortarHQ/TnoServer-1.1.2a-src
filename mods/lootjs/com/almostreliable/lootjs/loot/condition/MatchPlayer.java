package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import com.almostreliable.lootjs.util.LootContextUtils;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

public record MatchPlayer(EntityPredicate predicate) implements LootItemCondition {
   public boolean test(LootContext context) {
      ServerPlayer player = LootContextUtils.getPlayerOrNull(context);
      Vec3 origin = (Vec3)context.getParamOrNull(LootContextParams.ORIGIN);
      return this.predicate.matches(context.getLevel(), origin, player);
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.MATCH_PLAYER.value();
   }
}
