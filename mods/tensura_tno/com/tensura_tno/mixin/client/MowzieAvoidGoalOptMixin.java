package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOCompatConfig;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.bobmowzie.mowziesmobs.server.ai.AvoidEntityIfNotTamedGoal", remap = false)
public abstract class MowzieAvoidGoalOptMixin extends AvoidEntityGoal<LivingEntity> {
   @Unique
   private int tensuraTno$throttleCounter = ThreadLocalRandom.current().nextInt(4);

   private MowzieAvoidGoalOptMixin(PathfinderMob mob, Class<LivingEntity> cls, float dist, double walk, double sprint) {
      super(mob, cls, dist, walk, sprint);
   }

   @Inject(method = "canUse", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$optimizeCanUse(CallbackInfoReturnable<Boolean> cir) {
      if (TensuraTNOCompatConfig.isMowzieAvoidGoalOptEnabled()) {
         if (this.mob instanceof TamableAnimal ta && ta.isTame()) {
            cir.setReturnValue(false);
         } else {
            if (++this.tensuraTno$throttleCounter % 4 != 0) {
               cir.setReturnValue(false);
            }
         }
      }
   }
}
