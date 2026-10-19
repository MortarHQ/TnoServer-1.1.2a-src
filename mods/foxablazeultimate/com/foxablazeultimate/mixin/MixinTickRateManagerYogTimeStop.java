package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.yogsothoth.YogTimeStopManager;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TickRateManager.class)
public abstract class MixinTickRateManagerYogTimeStop {
   @Inject(method = "isEntityFrozen", at = @At("HEAD"), cancellable = true)
   private void foxablazeultimate$allowYogEntities(Entity entity, CallbackInfoReturnable<Boolean> cir) {
      if (YogTimeStopManager.isTimeStopActive(entity)) {
         if (!(entity instanceof Player) && !YogTimeStopManager.shouldTickDuringTimeStop(entity)) {
            cir.setReturnValue(true);
         } else {
            cir.setReturnValue(false);
         }
      }
   }
}
