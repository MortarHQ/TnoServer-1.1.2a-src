package com.tno_tensura_extras.mixin;

import com.tno_tensura_extras.entity.DiabloEntity;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TensuraProjectile.class)
public abstract class DiabloProjectileTargetMixin {
   @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true)
   private void extras$limitDiabloTargets(Entity target, CallbackInfoReturnable<Boolean> cir) {
      TensuraProjectile projectile = (TensuraProjectile)this;
      if (projectile.getOwner() instanceof DiabloEntity diablo && target instanceof LivingEntity living && !diablo.isAllowedCombatTarget(living)) {
         cir.setReturnValue(false);
      }
   }
}
