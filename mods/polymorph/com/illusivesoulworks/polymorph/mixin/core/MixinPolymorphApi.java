package com.illusivesoulworks.polymorph.mixin.core;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.common.PolymorphApiImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PolymorphApi.class, remap = false)
public class MixinPolymorphApi {
   @Inject(at = @At("HEAD"), method = "getInstance", cancellable = true)
   private static void polymorph$getInstance(CallbackInfoReturnable<PolymorphApi> cir) {
      cir.setReturnValue(PolymorphApiImpl.INSTANCE);
   }
}
