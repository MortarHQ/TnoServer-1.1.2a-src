package dev.foxboss.entity;

import io.github.manasmods.tensura.damage.TensuraDamageSource;
import java.util.function.BooleanSupplier;
import net.minecraft.world.damagesource.DamageSource;

final class MilimDragonAura {
   static final float ORDINARY_PENETRATION = 0.25F;
   static final float HEAVY_PENETRATION = 0.5F;
   private float penetration;

   boolean strike(boolean heavy, BooleanSupplier attack) {
      float previous = this.penetration;
      this.penetration = heavy ? 0.5F : 0.25F;

      try {
         return attack.getAsBoolean();
      } finally {
         this.penetration = previous;
      }
   }

   DamageSource decorate(DamageSource source) {
      if (this.penetration > 0.0F) {
         TensuraDamageSource nativeSource = (TensuraDamageSource)source;
         nativeSource.tensura$setBarrierBypassLevel(Math.max(nativeSource.tensura$getBarrierBypassLevel(), this.penetration));
      }

      return source;
   }
}
