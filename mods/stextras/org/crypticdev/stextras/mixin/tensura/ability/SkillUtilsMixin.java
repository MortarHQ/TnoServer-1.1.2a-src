package org.crypticdev.stextras.mixin.tensura.ability;

import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.effect.skill.WispModeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkillUtils.class)
public class SkillUtilsMixin {
   @Inject(method = "shouldCancelInteraction(Lnet/minecraft/world/entity/LivingEntity;Z)Z", at = @At("HEAD"), cancellable = true)
   private static void stextras$cancelInteractionForWispMode(LivingEntity entity, boolean ignoreMainStates, CallbackInfoReturnable<Boolean> cir) {
      if (!ignoreMainStates && WispModeEffect.isActive(entity)) {
         cir.setReturnValue(true);
      }
   }
}
