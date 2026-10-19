package com.almostreliable.lootjs.loot.modifier.handler;

import com.almostreliable.lootjs.core.LootBucket;
import com.almostreliable.lootjs.loot.modifier.LootAction;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class ExplodeAction implements LootAction {
   private final float radius;
   private final boolean fire;
   private final BlockInteraction mode;

   public ExplodeAction(float radius, BlockInteraction mode, boolean fire) {
      this.radius = radius;
      this.fire = fire;
      this.mode = mode;
   }

   @Override
   public void apply(LootContext context, LootBucket loot) {
      Vec3 origin = (Vec3)context.getParamOrNull(LootContextParams.ORIGIN);
   }
}
