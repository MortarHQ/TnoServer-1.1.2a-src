package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.IceAndFireEntityCompat;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ISubordinate.class, remap = false)
public interface DragonRemainsSubordinateTargetMixin {
   @Inject(method = "canAttackDefault", at = @At("HEAD"), cancellable = true)
   private void tensuraTno$ignoreDragonRemains(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      if (this instanceof Entity attacker && IceAndFireEntityCompat.shouldIgnoreDragonRemains(attacker, target)) {
         cir.setReturnValue(false);
      }
   }
}
