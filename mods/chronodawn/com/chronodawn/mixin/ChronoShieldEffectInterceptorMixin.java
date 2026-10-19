package com.chronodawn.mixin;

import com.chronodawn.items.shield.ChronoShieldEffectHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class ChronoShieldEffectInterceptorMixin {
   @ModifyVariable(
      method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private MobEffectInstance chronodawn$shortenShieldDebuff(MobEffectInstance incoming) {
      LivingEntity self = (LivingEntity)this;
      return ChronoShieldEffectHandler.maybeShortenDebuff(self, incoming);
   }
}
