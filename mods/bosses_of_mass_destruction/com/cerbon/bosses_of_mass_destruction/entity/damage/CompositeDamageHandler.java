package com.cerbon.bosses_of_mass_destruction.entity.damage;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class CompositeDamageHandler implements IDamageHandler {
   private final List<IDamageHandler> handlerList;

   public CompositeDamageHandler(IDamageHandler... handlers) {
      this.handlerList = Arrays.asList(handlers);
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
      for (IDamageHandler handler : this.handlerList) {
         handler.beforeDamage(stats, damageSource, amount);
      }
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      for (IDamageHandler handler : this.handlerList) {
         handler.afterDamage(stats, damageSource, amount, result);
      }
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      for (IDamageHandler handler : this.handlerList) {
         if (!handler.shouldDamage(actor, damageSource, amount)) {
            return false;
         }
      }

      return true;
   }
}
