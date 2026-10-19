package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.IceAndFireEntityCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetingConditions.class)
public abstract class DragonRemainsTargetingConditionsMixin {
   @Inject(method = "test", at = @At("HEAD"), cancellable = true)
   private void tensuraTno$ignoreDragonRemains(LivingEntity observer, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      if (IceAndFireEntityCompat.shouldIgnoreDragonRemains(observer, target)) {
         cir.setReturnValue(false);
      }
   }
}
