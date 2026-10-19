package com.bobmowzie.mowziesmobs.server.loot;

import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record LootConditionFrostmawHasCrystal() implements LootItemCondition {
   public static final MapCodec<LootConditionFrostmawHasCrystal> CODEC = MapCodec.unit(LootConditionFrostmawHasCrystal::new);

   public boolean test(LootContext context) {
      return context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof EntityFrostmaw frostmaw ? frostmaw.getHasCrystal() : false;
   }

   @NotNull
   public LootItemConditionType getType() {
      return (LootItemConditionType)LootTableHandler.FROSTMAW_HAS_CRYSTAL.get();
   }
}
