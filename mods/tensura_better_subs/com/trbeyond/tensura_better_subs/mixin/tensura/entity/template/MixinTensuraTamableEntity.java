package com.trbeyond.tensura_better_subs.mixin.tensura.entity.template;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.manascore.storage.impl.StorageManager;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TensuraTamableEntity.class)
public class MixinTensuraTamableEntity {
   private boolean usingHaki = false;
   private ManasSkill hakiSkill = null;
   private int hakiSlot = -1;
   private int hakiCooldown = 0;
   private int hakiActiveChecks = 0;
   private int releaseThresholdTicks = 0;

   @Inject(method = "tick", at = @At("TAIL"))
   private void hakiTick(CallbackInfo ci) {
      TensuraTamableEntity self = (TensuraTamableEntity)this;
      if (!self.level().isClientSide()) {
         if (self.tickCount % 20 == 0) {
            if (this.hakiCooldown > 0) {
               this.hakiCooldown--;
            } else {
               LivingEntity target = self.getTarget();
               boolean shouldContinueToUseHaki = false;
               if (target != null) {
                  double targetEPMul = EnergyHelper.getMaxEP(self) / EnergyHelper.getMaxEP(target);
                  shouldContinueToUseHaki = this.shouldUseHaki(self.distanceToSqr(target), targetEPMul);
                  if (this.hakiSkill == null) {
                     List<ManasSkill> hakiSkills = List.of(
                        (ManasSkill)UniqueSkills.VILLAIN.get(),
                        (ManasSkill)ExtraSkills.HERO_HAKI.get(),
                        (ManasSkill)ExtraSkills.DEMON_LORD_HAKI.get(),
                        (ManasSkill)ExtraSkills.MORTAL_FEAR.get(),
                        (ManasSkill)ExtraSkills.HAKI.get()
                     );
                     List<AbilitySlot> slots = TensuraStorages.getAbilityFrom(self).getAbilitySlots();

                     label77:
                     for (ManasSkill skill : hakiSkills) {
                        for (int i = 0; i < slots.size(); i++) {
                           if (slots.get(i).getSkill() == skill) {
                              this.hakiSkill = skill;
                              this.hakiSlot = i;
                              break label77;
                           }
                        }
                     }

                     if (shouldContinueToUseHaki && this.hakiSkill != null && !this.usingHaki) {
                        Skills storage = SkillAPI.getSkillsFrom(self);
                        ManasSkillInstance instance = SkillUtils.getSkillOrNull(self, this.hakiSkill);
                        this.usingHaki = instance != null
                           && !TickingSkill.isTickingSkill(self, this.hakiSkill)
                           && storage.startHoldSkill(instance, this.hakiSlot, 0);
                        if (!this.usingHaki) {
                           this.hakiSkill = null;
                           this.hakiCooldown = 3;
                           this.hakiSlot = -1;
                        }
                     }
                  }
               }

               this.releaseThresholdTicks = shouldContinueToUseHaki ? 0 : this.releaseThresholdTicks + 1;
               this.hakiActiveChecks = this.usingHaki ? this.hakiActiveChecks + 1 : 0;
               if (this.usingHaki && (this.releaseThresholdTicks > 2 || this.hakiActiveChecks > 6)) {
                  this.usingHaki = false;
                  SkillStorage storage = (SkillStorage)StorageManager.getStorage(self, SkillStorage.getKey());
                  if (storage == null) {
                     return;
                  }

                  storage.getSkill(this.hakiSkill).ifPresent(skillInstance -> storage.handleSkillRelease(skillInstance, this.hakiSlot, 0, false));
                  this.hakiSkill = null;
                  this.hakiSlot = -1;
                  this.hakiActiveChecks = 0;
                  this.hakiCooldown = 30;
               }
            }
         }
      }
   }

   private boolean shouldUseHaki(double distanceSqr, double targetEPMul) {
      if (targetEPMul >= 10.0 && distanceSqr < 225.0) {
         return true;
      } else {
         return targetEPMul >= 6.0 && distanceSqr < 100.0 ? true : targetEPMul >= 2.0 && distanceSqr < 36.0;
      }
   }
}
