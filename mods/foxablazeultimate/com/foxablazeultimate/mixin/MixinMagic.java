package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.ciel.CielPassiveHandler;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Magic;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Magic.class, remap = false)
public class MixinMagic {
   @ModifyVariable(
      method = "isOutOfEnergy(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;DD)Z",
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 1
   )
   private double foxablazeultimate$cielMagiculeOptimization(double magiculeCost, @Local(argsOnly = true) LivingEntity owner) {
      return CielPassiveHandler.optimizeMagiculeCost(owner, magiculeCost);
   }

   @Inject(method = "isInstantCast", at = @At("HEAD"), cancellable = true)
   private void foxablazeultimate$raphaelInstantCast(ManasSkillInstance instance, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      if (entity != null) {
         if (SkillUtils.isSkillToggled(entity, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
            || SkillUtils.isSkillToggled(entity, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())) {
            cir.setReturnValue(true);
         }
      }
   }
}
