package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.extra.HeroHakiSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public final class ArthurHolyOrderHandler implements ArthurModeHandler {
   private static HeroHakiSkill heroHaki() {
      return (HeroHakiSkill)ExtraSkills.HERO_HAKI.get();
   }

   @Override
   public String modeId() {
      return "arthur_once_and_future_king.holy_order";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return heroHaki().getMagiculeCost(entity, instance, 0);
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         HeroHakiSkill haki = heroHaki();
         haki.onPressed(instance, entity, keyNumber, mode);
         haki.addHeldAttributeModifiers(instance, entity, mode);
         applyHolyOrderPulse(instance, entity, mode);
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      HeroHakiSkill haki = heroHaki();
      if (!haki.onHeld(instance, entity, heldTicks, mode)) {
         return false;
      }

      if (heldTicks > 0 && heldTicks % 10 == 0) {
         applyHolyOrderPulse(instance, entity, mode);
      }

      return true;
   }

   private static void applyHolyOrderPulse(ManasSkillInstance instance, LivingEntity entity, int mode) {
      double radius = (Double)MoreSkillsConfigs.GRAND.arthur.holyOrderRadius.get();

      for (LivingEntity target : RoyalSkillSupport.enemiesInRange(entity, radius)) {
         double soulPoints = RoyalSkillSupport.existence(target).getSoulPoints();
         float damage = (float)Math.max(12.0, soulPoints * (Double)MoreSkillsConfigs.GRAND.arthur.holyOrderSoulDamageScale.get());
         target.invulnerableTime = 0;
         target.hurt(RoyalSkillSupport.source(entity, instance, mode, TensuraDamageTypes.HOLY_DAMAGE), damage);
         RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.FEAR), 30, 0);
         RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 30, 1);
         RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 30, 1);
         RoyalSkillSupport.markCombat(entity, target);
      }

      for (LivingEntity ally : RoyalSkillSupport.alliesInRange(entity, radius)) {
         RoyalSkillSupport.applyEffect(ally, entity, TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 30, 1);
         RoyalSkillSupport.applyEffect(ally, entity, MobEffects.MOVEMENT_SPEED, 30, 0);
         RoyalSkillSupport.applyEffect(ally, entity, TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 30, 0);
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         HeroHakiSkill haki = heroHaki();
         haki.onRelease(instance, entity, heldTicks, keyNumber, mode);
         haki.removeAttributeModifiers(instance, entity, mode);
      }
   }

   @Override
   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return true;
   }
}
