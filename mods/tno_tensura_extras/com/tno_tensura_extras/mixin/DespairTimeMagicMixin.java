package com.tno_tensura_extras.mixin;

import com.tno_tensura_extras.effect.DespairTimeEffect;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ManasSkillInstance.class, remap = false)
public abstract class DespairTimeMagicMixin {
   @Inject(method = "canInteractSkill", at = @At("HEAD"), cancellable = true)
   private void extras$blockMagicInteraction(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      if (DespairTimeEffect.blocks(entity, (ManasSkillInstance)this)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "onToggleOn", at = @At("HEAD"), cancellable = true)
   private void extras$blockMagicToggle(LivingEntity entity, CallbackInfo ci) {
      ManasSkillInstance instance = (ManasSkillInstance)this;
      if (DespairTimeEffect.blocks(entity, instance)) {
         instance.setToggled(false);
         ci.cancel();
      }
   }

   @Inject(method = "onPressed", at = @At("HEAD"), cancellable = true)
   private void extras$blockMagicPress(LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      if (DespairTimeEffect.blocks(entity, (ManasSkillInstance)this)) {
         ci.cancel();
      }
   }

   @Inject(method = "onHeld", at = @At("HEAD"), cancellable = true)
   private void extras$blockMagicHold(LivingEntity entity, int heldTicks, int mode, CallbackInfoReturnable<Boolean> cir) {
      if (DespairTimeEffect.blocks(entity, (ManasSkillInstance)this)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "onRelease", at = @At("HEAD"), cancellable = true)
   private void extras$blockMagicRelease(LivingEntity entity, int heldTicks, int keyNumber, int mode, CallbackInfo ci) {
      if (DespairTimeEffect.blocks(entity, (ManasSkillInstance)this)) {
         ci.cancel();
      }
   }
}
