package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.IceAndFireEntityCompat;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DragonRemainsLivingTargetMixin {
   @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
   private void tensuraTno$ignoreDragonRemains(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      if (IceAndFireEntityCompat.shouldIgnoreDragonRemains((LivingEntity)this, target)) {
         cir.setReturnValue(false);
      }
   }
}
