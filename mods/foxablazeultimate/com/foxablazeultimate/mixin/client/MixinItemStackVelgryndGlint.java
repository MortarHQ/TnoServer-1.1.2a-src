package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.VelgryndGlintRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStackVelgryndGlint {
   @Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true)
   private void foxablazeultimate$chargedWeaponHasGlint(CallbackInfoReturnable<Boolean> callback) {
      if (VelgryndGlintRenderState.isCharged((ItemStack)this)) {
         callback.setReturnValue(true);
      }
   }
}
