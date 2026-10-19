package com.tensura_tno.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "io.github.Memoires.mysticism.ability.skill.intrinsic.BulletPunchSkill", remap = false)
public class BulletPunchSkillRecursionFixMixin {
   private static final ThreadLocal<Boolean> BULLET_PUNCH_ACTIVE = ThreadLocal.withInitial(() -> false);

   @Inject(method = "onDamageEntity", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$guardReentry(CallbackInfoReturnable<Boolean> cir) {
      if (BULLET_PUNCH_ACTIVE.get()) {
         cir.setReturnValue(true);
      } else {
         BULLET_PUNCH_ACTIVE.set(true);
      }
   }

   @Inject(method = "onDamageEntity", at = @At("RETURN"), remap = false)
   private void tensuraTno$clearGuard(CallbackInfoReturnable<Boolean> cir) {
      BULLET_PUNCH_ACTIVE.set(false);
   }
}
