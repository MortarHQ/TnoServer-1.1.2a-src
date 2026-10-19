package com.natamus.collective.forge.mixin;

import com.natamus.collective_common_forge.services.Services;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PrimaryLevelData.class, priority = 1001)
public class PrimaryLevelDataMixin {
   @Shadow
   private boolean confirmedExperimentalWarning;

   @Inject(method = "<init>*", at = @At("TAIL"))
   private void PrimaryLevelData(CallbackInfo ci) {
      if (Services.MODLOADER.isDevelopmentEnvironment() || Services.MODLOADER.isModLoaded("hideexperimentalwarning")) {
         this.confirmedExperimentalWarning = true;
      }
   }
}
