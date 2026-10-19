package com.tensura_tno.mixin.client;

import com.tensura_tno.ai.SubordinateSkillAiController;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TensuraSkill.class, remap = false)
public abstract class SubordinateSkillHeldTimeMixin {
   @Inject(method = "getMaxHeldTime", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$extendSubordinateAiHold(ManasSkillInstance instance, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
      if (SubordinateSkillAiController.isAiHolding(entity, instance.getSkill())) {
         cir.setReturnValue(72000);
      }
   }
}
