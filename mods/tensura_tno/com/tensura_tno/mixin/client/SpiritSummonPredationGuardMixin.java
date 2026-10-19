package com.tensura_tno.mixin.client;

import com.tensura_tno.race.fox_spirit.SpiritSummonLifecycle;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.entity.magic.beam.PredatorMistProjectile;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PredatorMistProjectile.class, remap = false)
public abstract class SpiritSummonPredationGuardMixin {
   @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true)
   private void tno$skipMaterializingSummon(Entity target, CallbackInfoReturnable<Boolean> cir) {
      if (SpiritSummonLifecycle.isSummoning(target)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "hitEntity", at = @At("HEAD"), cancellable = true)
   private void tno$preventMaterializingSummonPredation(Entity target, ProjectileHitResult result, CallbackInfoReturnable<Boolean> cir) {
      if (SpiritSummonLifecycle.isSummoning(target)) {
         cir.setReturnValue(false);
      }
   }
}
