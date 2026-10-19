package com.chronodawn.core.time;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MobAICanceller {
   private static final int WEAKNESS_DURATION_TICKS = 40;

   public static void cancelAttackAI(Level level, Vec3 center, double radius) {
      AABB boundingBox = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);

      for (Mob mob : level.getEntitiesOfClass(Mob.class, boundingBox, mobx -> mobx.distanceToSqr(center) <= radius * radius)) {
         mob.setTarget(null);
         MobEffectInstance weakness = new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false, false);
         mob.addEffect(weakness);
      }
   }
}
