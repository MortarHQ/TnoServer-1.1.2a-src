package com.chronodawn.mixin;

import com.chronodawn.items.shield.ChronoShieldEffectHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class ChronoShieldDamageMixin {
   @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
   private void chronodawn$maybeConsumeEcho(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      Player self = (Player)this;
      if (self instanceof ServerPlayer sp) {
         if (sp.level() instanceof ServerLevel) {
            if (!self.isBlocking()) {
               if (isEchoBlockable(source)) {
                  if (ChronoShieldEffectHandler.tryConsumeEcho(sp)) {
                     cir.setReturnValue(false);
                  }
               }
            }
         }
      }
   }

   private static boolean isEchoBlockable(DamageSource source) {
      if (source.is(DamageTypeTags.BYPASSES_SHIELD)) {
         return false;
      } else if (source.is(DamageTypeTags.BYPASSES_ARMOR)) {
         return false;
      } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      } else if (source.is(DamageTypeTags.IS_FIRE)) {
         return false;
      } else {
         return source.is(DamageTypeTags.IS_FALL) ? false : !source.is(DamageTypeTags.IS_DROWNING);
      }
   }
}
