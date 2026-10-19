package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.SkillLockEligibility;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", remap = false)
public class SkillLockValidAllSkillsMixin {
   private static final Set<String> UNLOCKABLE_SKILL_IDS = Set.of("tensura_tno:spirit_summon", "tensura_tno:contract_little_fox");

   @Inject(method = "isSkillValid", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tno$makeAllSkillsValid(ResourceLocation skill, CallbackInfoReturnable<Boolean> cir) {
      if (skill != null) {
         if (UNLOCKABLE_SKILL_IDS.contains(skill.toString())) {
            cir.setReturnValue(false);
         } else if (SkillLockEligibility.isUltimateSkill(skill)) {
            cir.setReturnValue(false);
         } else {
            cir.setReturnValue(true);
         }
      }
   }
}
