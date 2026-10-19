package com.tensurabossstructure.mixin;

import com.tensurabossstructure.compat.ElementalColossusSpawnerSupport;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.entity.monster.ElementalColossusEntity", remap = false)
public abstract class ElementalColossusSpawnerMixin {
   @Inject(method = "shouldAttack", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraBossStructure$allowSpawnerPlayerTargets(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      if (ElementalColossusSpawnerSupport.isSpawnerColossus(this) && ElementalColossusSpawnerSupport.isValidPlayerTarget(target)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "shouldStopTarget", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraBossStructure$keepSpawnerPlayerTargets(Mob subordinate, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      if (ElementalColossusSpawnerSupport.isSpawnerColossus(this) && ElementalColossusSpawnerSupport.shouldKeepPlayerTarget(subordinate, target)) {
         cir.setReturnValue(false);
      }
   }
}
