package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

public record IsLightLevel(int min, int max) implements LootItemCondition {
   public boolean test(LootContext context) {
      Vec3 origin = (Vec3)context.getParamOrNull(LootContextParams.ORIGIN);
      if (origin == null) {
         return false;
      }

      BlockPos blockPos = new BlockPos((int)origin.x, (int)origin.y, (int)origin.z);
      int light = context.getLevel().getMaxLocalRawBrightness(blockPos);
      return this.min <= light && light <= this.max;
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.LIGHT_LEVEL.value();
   }
}
