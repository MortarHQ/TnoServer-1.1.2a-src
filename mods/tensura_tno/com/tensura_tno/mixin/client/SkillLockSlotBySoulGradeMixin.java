package com.tensura_tno.mixin.client;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", remap = false)
public class SkillLockSlotBySoulGradeMixin {
   @Inject(method = "getSoulGradeLevel(ILjava/util/List;)I", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tno$soulGradeLevelByRaw(int soulGrade, List<Integer> thresholds, CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(Math.max(0, soulGrade / 20));
   }

   @Inject(method = "getMaxLocksForSoulGradeLevel", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tno$maxLockEqualsLevel(int soulGradeLevel, CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(soulGradeLevel);
   }
}
