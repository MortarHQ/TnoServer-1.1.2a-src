package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.ciel.CielPassiveHandler;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = EnergyHelper.class, remap = false)
public abstract class MixinEnergyHelperCielOptimization {
   @ModifyVariable(method = "isOutOfEnergy(Lnet/minecraft/world/entity/LivingEntity;DD)Z", at = @At("HEAD"), argsOnly = true, ordinal = 1)
   private static double foxablazeultimate$optimizeMagiculeCost(double magiculeCost, @Local(argsOnly = true) LivingEntity owner) {
      return CielPassiveHandler.optimizeMagiculeCost(owner, magiculeCost);
   }

   @ModifyVariable(method = "isOutOfMagiculeConsuming(Lnet/minecraft/world/entity/LivingEntity;DD)D", at = @At("HEAD"), argsOnly = true, ordinal = 0)
   private static double foxablazeultimate$optimizeDynamicMagiculeCost(double magiculeCost, @Local(argsOnly = true) LivingEntity owner) {
      return CielPassiveHandler.optimizeMagiculeCost(owner, magiculeCost);
   }
}
