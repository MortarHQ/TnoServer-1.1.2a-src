package com.trbeyond.tensura_better_subs.entity;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public interface IExpandedSkillAcquisition {
   boolean usedSkillItem(Item var1, LivingEntity var2);

   void attemptTallyKill(Entity var1, LivingEntity var2);

   void attemptTallyDamage(LivingEntity var1, float var2);

   void entityEPUpdate(LivingEntity var1);

   static boolean grantSkill(LivingEntity entity, ManasSkill skill) {
      return grantSkill(entity, skill, false);
   }

   static boolean grantSkill(LivingEntity entity, ManasSkill skill, boolean maxMastery) {
      ManasSkillInstance instance = skill.createDefaultInstance();
      if (maxMastery) {
         instance.setMastery(instance.getMaxMastery());
      }

      return grantSkill(entity, instance, Component.translatable("tensura_better_subs.skill_source.learning"));
   }

   static boolean grantSkill(LivingEntity entity, ManasSkillInstance instance, Component source) {
      if (instance.getSkill() instanceof TensuraSkill tensuraSkill
         && tensuraSkill.getAcquiringMagiculeCost(instance) > TensuraStorages.getExistenceFrom(entity).getMagicule()) {
         return false;
      } else {
         boolean learned = SkillHelper.learnSkill(entity, instance);
         if (learned) {
            if (SubordinateHelper.getSubordinateOwner(entity) instanceof ServerPlayer owningPlayer) {
               owningPlayer.sendSystemMessage(
                  Component.translatable(
                     "tensura_better_subs.skill.subordinate_acquire_skill", new Object[]{entity.getName(), instance.getChatDisplayName(true), source}
                  )
               );
            }

            return true;
         } else {
            return false;
         }
      }
   }

   void markUpdateExistence();

   void tickStorage(LivingEntity var1);
}
