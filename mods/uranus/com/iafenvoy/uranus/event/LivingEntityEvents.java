package com.iafenvoy.uranus.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityEvents {
   public static final Event<LivingEntityEvents.Fall> FALL = new Event<>(callbacks -> (entity, fallDistance, multiplier, source) -> {
      for (LivingEntityEvents.Fall e : callbacks) {
         e.onFall(entity, fallDistance, multiplier, source);
      }
   });
   public static final Event<LivingEntityEvents.DamageCallback> DAMAGE = new Event<>(callbacks -> (entity, source, amount) -> {
      for (LivingEntityEvents.DamageCallback e : callbacks) {
         float newAmount = e.onLivingDamage(entity, source, amount);
         if (newAmount != amount) {
            return newAmount;
         }
      }

      return amount;
   });

   @FunctionalInterface
   public interface DamageCallback {
      float onLivingDamage(LivingEntity var1, DamageSource var2, float var3);
   }

   @FunctionalInterface
   public interface Fall {
      void onFall(LivingEntity var1, float var2, float var3, DamageSource var4);
   }
}
