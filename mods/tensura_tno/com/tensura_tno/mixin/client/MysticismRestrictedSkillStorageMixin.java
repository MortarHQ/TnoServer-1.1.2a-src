package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tensura_tno.event.MysticismRestrictedSkillHandler;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SkillStorage.class, remap = false)
public abstract class MysticismRestrictedSkillStorageMixin {
   @Shadow(remap = false)
   public abstract LivingEntity getOwner();

   @Inject(
      method = "learnSkill(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/network/chat/MutableComponent;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   private void tensuraTno$blockRestrictedAcquisition(ManasSkillInstance instance, MutableComponent message, CallbackInfoReturnable<Boolean> cir) {
      if (MysticismRestrictedSkillHandler.shouldBlock(this.getOwner(), instance.getSkillId())) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "updateSkill", at = @At("HEAD"), cancellable = true)
   private void tensuraTno$blockRestrictedUpdate(ManasSkillInstance instance, boolean sync, CallbackInfo ci) {
      if (MysticismRestrictedSkillHandler.shouldBlock(this.getOwner(), instance.getSkillId())) {
         ci.cancel();
      }
   }

   @ModifyExpressionValue(
      method = "forgetSkill",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/manascore/skill/api/SkillEvents$RemoveSkillEvent;removeSkill(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/network/api/util/Changeable;)Ldev/architectury/event/EventResult;"
      )
   )
   private EventResult tensuraTno$removeInvalidRestrictedDespiteLock(EventResult result, ResourceLocation skillId, MutableComponent message) {
      return MysticismRestrictedSkillHandler.shouldBlock(this.getOwner(), skillId) ? EventResult.pass() : result;
   }
}
