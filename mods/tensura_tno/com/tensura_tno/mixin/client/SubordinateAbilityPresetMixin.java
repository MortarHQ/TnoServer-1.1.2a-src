package com.tensura_tno.mixin.client;

import com.tensura_tno.ai.SubordinateSkillAiController;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.storage.ability.AbilityStorage;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbilityStorage.class, remap = false)
public abstract class SubordinateAbilityPresetMixin {
   @Shadow
   protected abstract LivingEntity getOwner();

   @Inject(method = "isAbilityInActivePreset(ILio/github/manasmods/manascore/skill/api/ManasSkill;)Z", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$allowAiSlot(int slot, ManasSkill skill, CallbackInfoReturnable<Boolean> cir) {
      if (SubordinateSkillAiController.isAiCasting(this.getOwner(), skill)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "isAbilityInActivePreset(ILio/github/manasmods/manascore/skill/api/ManasSkill;I)Z", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$allowAiSlotMode(int slot, ManasSkill skill, int mode, CallbackInfoReturnable<Boolean> cir) {
      if (SubordinateSkillAiController.isAiCasting(this.getOwner(), skill, mode)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "isAbilityInActivePreset(Lio/github/manasmods/manascore/skill/api/ManasSkill;)Z", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$allowAiSkill(ManasSkill skill, CallbackInfoReturnable<Boolean> cir) {
      if (SubordinateSkillAiController.isAiCasting(this.getOwner(), skill)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "isAbilityInActivePreset(Lio/github/manasmods/manascore/skill/api/ManasSkill;I)Z", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$allowAiSkillMode(ManasSkill skill, int mode, CallbackInfoReturnable<Boolean> cir) {
      if (SubordinateSkillAiController.isAiCasting(this.getOwner(), skill, mode)) {
         cir.setReturnValue(true);
      }
   }
}
