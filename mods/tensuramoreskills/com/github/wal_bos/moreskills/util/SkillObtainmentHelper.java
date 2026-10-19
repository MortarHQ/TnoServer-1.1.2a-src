package com.github.wal_bos.moreskills.util;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public final class SkillObtainmentHelper {
   private SkillObtainmentHelper() {
   }

   public static boolean hasRequiredSkill(Player entity, String id, boolean mustBeMastered) {
      if (id != null && !id.isBlank()) {
         ManasSkill required;
         try {
            required = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
         } catch (Throwable ignored) {
            return false;
         }

         if (required == null) {
            return false;
         }

         Skills skills = SkillAPI.getSkillsFrom(entity);
         Optional<ManasSkillInstance> optional = skills.getSkill(required);
         return optional.isEmpty() ? false : !mustBeMastered || optional.get().isMastered(entity);
      } else {
         return true;
      }
   }

   public static boolean hasAcquirementMagiculeCapacity(Player entity, double baseCost) {
      if (!(baseCost <= 0.0) && !entity.hasInfiniteMaterials()) {
         AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
         if (magicule == null) {
            return false;
         }

         double cost = baseCost * entity.level().getGameRules().getInt(TensuraGameRules.MP_SKILL_COST) / 100.0;
         return magicule.getValue() > cost;
      } else {
         return true;
      }
   }

   public static boolean canAcquire(Player entity, double baseMagiculeCost, String requiredSkillId, boolean requiredSkillMustBeMastered) {
      return hasAcquirementMagiculeCapacity(entity, baseMagiculeCost) && hasRequiredSkill(entity, requiredSkillId, requiredSkillMustBeMastered);
   }
}
