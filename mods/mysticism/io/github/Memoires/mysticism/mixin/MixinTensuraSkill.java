package io.github.Memoires.mysticism.mixin;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.util.MysticismEnergyCostHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TensuraSkill.class)
public class MixinTensuraSkill {
   @Inject(method = "canInteractSkill", at = @At("HEAD"), cancellable = true, remap = false)
   private void mysticism$canInteractSkill(ManasSkillInstance instance, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.ENKIDU))) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "isOutOfEnergy*", at = @At("HEAD"), cancellable = true, remap = false)
   private void mysticism$interceptSkillEnergyCheck(
      LivingEntity entity, ManasSkillInstance instance, int mode, float costMultiplier, CallbackInfoReturnable<Boolean> cir
   ) {
      TensuraSkill skill = (TensuraSkill)this;
      double baseAp = skill.getAuraCost(entity, instance, mode) * costMultiplier;
      double baseMp = skill.getMagiculeCost(entity, instance, mode) * costMultiplier;
      double modifiedAp = MysticismEnergyCostHelper.modifyApEnergyCost(entity, baseAp);
      double modifiedMp = MysticismEnergyCostHelper.modifyMpEnergyCost(entity, baseMp);
      Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY);
      if (entity.hasEffect(effect) && SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.CONSTANT.get())) {
         ManasSkillInstance constant = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.CONSTANT.get()).get();
         double constantEnergyMPCost = constant.getOrCreateTag().getDouble("ConstantEnergyMPCost");
         double constantEnergyAPCost = constant.getOrCreateTag().getDouble("ConstantEnergyAPCost");
         constant.getOrCreateTag().putDouble("ConstantEnergyMPCost", baseMp * 2.0 + constantEnergyMPCost);
         constant.getOrCreateTag().putDouble("ConstantEnergyAPCost", baseAp * 2.0 + constantEnergyAPCost);
         constant.markDirty();
      }

      boolean isOutOfEnergy = EnergyHelper.isOutOfEnergy(entity, modifiedAp, modifiedMp);
      cir.setReturnValue(isOutOfEnergy);
   }
}
