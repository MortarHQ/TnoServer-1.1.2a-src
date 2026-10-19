package com.foxablazeultimate.mixin.stextras;

import com.foxablazeultimate.client.ClientCrystalLockState;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", priority = 500, remap = false)
public class STExtrasIsSkillValidUnlockRaphaelMixin {
   @Inject(method = "isSkillValid", at = @At("HEAD"), cancellable = true, remap = false)
   private static void foxablazeultimate$allowWhenCrystalLocked(ResourceLocation skill, CallbackInfoReturnable<Boolean> cir) {
      if (skill != null) {
         if (ClientCrystalLockState.isLocked(skill)) {
            cir.setReturnValue(true);
         }
      }
   }
}
