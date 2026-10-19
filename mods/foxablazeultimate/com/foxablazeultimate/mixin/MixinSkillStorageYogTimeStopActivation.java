package com.foxablazeultimate.mixin;

import com.foxablazeultimate.ability.skill.yogsothoth.YogTimeStopManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SkillStorage.class, remap = false)
public abstract class MixinSkillStorageYogTimeStopActivation {
   @Shadow
   public abstract LivingEntity getOwner();

   @Inject(method = "startHoldSkill(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;II)Z", at = @At("HEAD"), cancellable = true, require = 0)
   private void foxablazeultimate$handleTimeStop(ManasSkillInstance instance, int keyNumber, int mode, CallbackInfoReturnable<Boolean> cir) {
      if (mode == 8 && instance != null && instance.getSkill() != null && YogTimeStopManager.YOG_SOTHOTH_ID.equals(instance.getSkill().getRegistryName())) {
         LivingEntity owner = this.getOwner();
         if (owner instanceof ServerPlayer player
            && foxablazeultimate$isEquippedInActivePreset(owner, keyNumber, instance)
            && !instance.isTemporarySkill()
            && !instance.isSubInstance()) {
            if (YogTimeStopManager.isActiveForOwner(player)
               || instance.canInteractSkill(owner)
                  && mode >= 0
                  && mode < instance.getModes()
                  && (!instance.onCoolDown(mode) || instance.canIgnoreCoolDown(owner, mode))) {
               cir.setReturnValue(YogTimeStopManager.toggle(player));
            }
         }
      }
   }

   private static boolean foxablazeultimate$isEquippedInActivePreset(LivingEntity owner, int keyNumber, ManasSkillInstance instance) {
      if (keyNumber < 0) {
         return false;
      }

      try {
         IAbility ability = TensuraStorages.getAbilityFrom(owner);
         if (ability == null) {
            return false;
         }

         if (!ability.isAbilityInActivePreset(keyNumber, instance.getSkill()) && !ability.isAbilityInActivePreset(instance.getSkill())) {
            for (AbilitySlot slot : ability.getAbilitySlots()) {
               if (slot != null && slot.getSkill() != null && instance.getSkill().getRegistryName().equals(slot.getSkill().getRegistryName())) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      } catch (RuntimeException ignored) {
         return false;
      }
   }
}
