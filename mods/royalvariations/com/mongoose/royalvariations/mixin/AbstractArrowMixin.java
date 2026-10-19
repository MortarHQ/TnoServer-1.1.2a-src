package com.mongoose.royalvariations.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
   @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isCritArrow()Z"))
   public boolean disableCritParticles(boolean original) {
      return this instanceof RoyalArrow ? false : original;
   }
}
