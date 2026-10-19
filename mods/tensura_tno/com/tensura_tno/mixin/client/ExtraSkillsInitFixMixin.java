package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.TRUniqueMonstersCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.crypticmc.tr_unique_monsters.registry.skill.ExtraSkills", remap = false)
public class ExtraSkillsInitFixMixin {
   @Inject(method = "init", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$cancelPrematureInit(CallbackInfo ci) {
      if (!TRUniqueMonstersCompat.allowInit) {
         ci.cancel();
      }
   }
}
