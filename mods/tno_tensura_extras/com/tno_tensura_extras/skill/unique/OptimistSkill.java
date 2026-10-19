package com.tno_tensura_extras.skill.unique;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class OptimistSkill extends Skill {
   private static final int TICKS_PER_SECOND = 20;
   private static final int ACTIVE_TICKS = 1200;
   private static final int COOLDOWN_TICKS = 2400;
   private static final float BASE_MULTIPLIER = 3.0F;
   private static final float MASTERED_MULTIPLIER = 3.5F;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/skill/unique/optimist.png");

   public OptimistSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && !instance.onCoolDown(mode)) {
         instance.addMasteryPoint(entity);
         instance.setCoolDown(3600, mode);
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (this.isActive(instance, owner) && target != owner) {
         if (EnergyHelper.getMaxEP(target) < EnergyHelper.getMaxEP(owner)) {
            amount.set((Float)amount.get() * this.getMultiplier(instance, owner));
         }

         return true;
      } else {
         return true;
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (!this.isActive(instance, owner)) {
         return true;
      }

      if (source.getEntity() instanceof LivingEntity attacker && attacker != owner && EnergyHelper.getMaxEP(attacker) < EnergyHelper.getMaxEP(owner)) {
         amount.set((Float)amount.get() / this.getMultiplier(instance, owner));
      }

      return true;
   }

   private boolean isActive(ManasSkillInstance instance, LivingEntity owner) {
      return instance.getCoolDown(0) > 2400 && this.isInSlot(owner, instance);
   }

   private float getMultiplier(ManasSkillInstance instance, LivingEntity owner) {
      return instance.isMastered(owner) ? 3.5F : 3.0F;
   }
}
