package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.event.LivingEntityEvents;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
   @ModifyExpressionValue(
      method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V",
      at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F")
   )
   private float livingDamageEvent(float value, DamageSource pDamageSource) {
      return LivingEntityEvents.DAMAGE.invoker().onLivingDamage((LivingEntity)this, pDamageSource, value);
   }
}
