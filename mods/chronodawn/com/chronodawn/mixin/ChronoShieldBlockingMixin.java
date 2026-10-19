package com.chronodawn.mixin;

import com.chronodawn.items.shield.ChronoShieldEffectHandler;
import com.chronodawn.items.shield.ChronoShieldMarker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class ChronoShieldBlockingMixin {
   @Inject(method = "hurtCurrentlyUsedShield(F)V", at = @At("HEAD"))
   private void chronodawn$onShieldBlockSuccess(float amount, CallbackInfo ci) {
      Player self = (Player)this;
      if (self instanceof ServerPlayer sp) {
         ItemStack useItem = self.getUseItem();
         if (useItem.getItem() instanceof ChronoShieldMarker marker) {
            ChronoShieldEffectHandler.onBlockSuccess(sp, marker.getChronoShieldTier());
         }
      }
   }
}
