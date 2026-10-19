package com.tensura_tno.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.trbeyond.neoforge.event.Common", remap = false)
public class BeyondAdventuresDebugLogCrashFixMixin {
   @Redirect(
      method = "onAnvilUpdate",
      at = @At(value = "INVOKE", target = "Lcom/trbeyond/utils/DebugUtils;log(Ljava/lang/Object;)V"),
      remap = false,
      require = 0
   )
   private static void tensuraTno$suppressClientOnlyDebugLog(Object object) {
   }
}
