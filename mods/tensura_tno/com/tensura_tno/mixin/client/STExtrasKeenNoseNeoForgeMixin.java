package com.tensura_tno.mixin.client;

import com.tensura_tno.client.compat.STExtrasKeenNoseNeoForgeCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.ability.skill.extra.KeenNoseSkill", remap = false)
public class STExtrasKeenNoseNeoForgeMixin {
   @Inject(method = "<init>", at = @At("RETURN"), require = 0, remap = false)
   private void tensuraTno$registerNeoForgeXrayTypes(CallbackInfo ci) {
      STExtrasKeenNoseNeoForgeCompat.registerIfNeeded();
   }
}
