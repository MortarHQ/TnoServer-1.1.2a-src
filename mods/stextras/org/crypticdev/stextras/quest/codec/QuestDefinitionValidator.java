package org.crypticdev.stextras.quest.codec;

import com.mojang.logging.LogUtils;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.function.Predicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.definition.QuestSubordinateMode;
import org.crypticdev.stextras.quest.definition.TensuraMasteryTypeIds;
import org.crypticdev.stextras.quest.definition.TensuraQuestTargetIds;
import org.slf4j.Logger;

public final class QuestDefinitionValidator {
   private static final Logger LOGGER = LogUtils.getLogger();

   public static boolean validate(QuestDefinition QuestDefinition) {
      String expectedType = QuestDefinition.creator.expectedType();

      boolean ok = switch (expectedType) {
         case "entity" -> {
            if (QuestDefinition.types.stream().anyMatch(QuestDefinitionValidator::isMinecraftAny)) {
               yield true;
            } else {
               boolean typesOk = true;

               for (ResourceLocation type : QuestDefinition.types) {
                  if (!BuiltInRegistries.ENTITY_TYPE.containsKey(type)) {
                     typesOk = false;
                     break;
                  }
               }

               yield typesOk && !QuestDefinition.types.isEmpty() ? true : QuestDefinition.mobCategory != null;
            }
         }
         case "block" -> {
            if (QuestDefinition.types.isEmpty()) {
               yield false;
            } else if (QuestDefinition.types.stream().anyMatch(QuestDefinitionValidator::isMinecraftAny)) {
               yield true;
            } else {
               for (ResourceLocation type : QuestDefinition.types) {
                  boolean validBlock = BuiltInRegistries.BLOCK.containsKey(type);
                  boolean validHarvestItem = (QuestDefinition.creator == QuestObjectiveType.MINE || QuestDefinition.creator == QuestObjectiveType.FARM)
                     && BuiltInRegistries.ITEM.containsKey(type);
                  if (!validBlock && !validHarvestItem) {
                     yield false;
                  }
               }

               yield true;
            }
         }
         case "item" -> {
            if (QuestDefinition.types.isEmpty()) {
               yield false;
            } else if (QuestDefinition.types.stream().anyMatch(QuestDefinitionValidator::isMinecraftAny)) {
               yield true;
            } else {
               for (ResourceLocation type : QuestDefinition.types) {
                  if (!BuiltInRegistries.ITEM.containsKey(type)) {
                     yield false;
                  }
               }

               yield true;
            }
         }
         case "skill" -> {
            if (QuestDefinition.types.isEmpty()) {
               yield false;
            } else if (QuestDefinition.types.stream().anyMatch(QuestDefinitionValidator::isMinecraftAny)) {
               yield true;
            } else {
               for (ResourceLocation type : QuestDefinition.types) {
                  if (!SkillAPI.getSkillRegistry().contains(type)) {
                     yield false;
                  }
               }

               yield true;
            }
         }
         case "entity_or_skill" -> {
            if (QuestDefinition.types.isEmpty()) {
               yield false;
            } else if (QuestDefinition.types.stream().anyMatch(QuestDefinitionValidator::isMinecraftAny)) {
               yield true;
            } else {
               for (ResourceLocation type : QuestDefinition.types) {
                  boolean validEntity = BuiltInRegistries.ENTITY_TYPE.containsKey(type);
                  boolean validSkill = SkillAPI.getSkillRegistry().contains(type);
                  if (!validEntity && !validSkill) {
                     yield false;
                  }
               }

               yield true;
            }
         }
         case "item_or_enchantment" -> QuestDefinition.types.isEmpty()
               && QuestDefinition.enchantItems.isEmpty()
               && QuestDefinition.enchantments.isEmpty()
               && QuestDefinition.minEnchantLevel < 0
               && QuestDefinition.maxEnchantLevel < 0
            ? false
            : validItemFilters(QuestDefinition.enchantItems);
         case "spirit" -> {
            if (QuestDefinition.types.isEmpty()) {
               yield false;
            } else {
               for (ResourceLocation type : QuestDefinition.types) {
                  if (!TensuraQuestTargetIds.isSpiritElement(type) && !TensuraQuestTargetIds.isSpiritLevel(type)) {
                     yield false;
                  }
               }

               yield QuestDefinition.spiritLevel == null || TensuraQuestTargetIds.isSpiritLevel(QuestDefinition.spiritLevel);
            }
         }
         case "none" -> QuestDefinition.types.isEmpty();
         case "skill_type" -> validateTypes(QuestDefinition, TensuraMasteryTypeIds::isSkillMasteryType);
         case "magic_type" -> validateTypes(QuestDefinition, TensuraMasteryTypeIds::isMagicMasteryType);
         case "magic_subtype" -> validateTypes(QuestDefinition, TensuraMasteryTypeIds::isMagicMasterySubtype);
         default -> true;
      };
      if (!ok) {
         LOGGER.warn(
            "[STExtras] QuestDefinition {} has invalid type(s) {} / mobCategory {} for {}",
            new Object[]{QuestDefinition.id, QuestDefinition.types, QuestDefinition.mobCategory, QuestDefinition.creator}
         );
         return false;
      }

      if (QuestDefinition.creator != QuestObjectiveType.DAMAGE && (!QuestDefinition.damageTypes.isEmpty() || !QuestDefinition.damageTypeTags.isEmpty())) {
         LOGGER.warn("[STExtras] QuestDefinition {} uses damage_type on non-damage creator {}", QuestDefinition.id, QuestDefinition.creator);
      }

      if (QuestDefinition.creator != QuestObjectiveType.ENCHANT
         && (
            !QuestDefinition.enchantItems.isEmpty()
               || !QuestDefinition.enchantments.isEmpty()
               || QuestDefinition.minEnchantLevel >= 0
               || QuestDefinition.maxEnchantLevel >= 0
         )) {
         LOGGER.warn("[STExtras] QuestDefinition {} uses enchant filters on non-enchant creator {}", QuestDefinition.id, QuestDefinition.creator);
      }

      if (QuestDefinition.creator != QuestObjectiveType.MASTER && QuestDefinition.amount <= 0) {
         LOGGER.warn("[STExtras] QuestDefinition {} has non-positive amount for {}", QuestDefinition.id, QuestDefinition.creator);
      }

      if (QuestDefinition.subordinate != null
         && QuestDefinition.subordinate != QuestSubordinateMode.NONE
         && !QuestSubordinateMode.supports(QuestDefinition.creator)) {
         LOGGER.warn("[STExtras] QuestDefinition {} uses subordinate mode on unsupported creator {}", QuestDefinition.id, QuestDefinition.creator);
      }

      if (QuestDefinition.category == QuestCategory.REPEATABLE && QuestDefinition.cooldownSeconds < -1) {
         LOGGER.warn("[STExtras] QuestDefinition {} had invalid cooldown {}; clamped to -1", QuestDefinition.id, QuestDefinition.cooldownSeconds);
         QuestDefinition.cooldownSeconds = -1;
      }

      if (QuestDefinition.category == QuestCategory.RACE_PRESTIGE) {
         if (QuestDefinition.race == null) {
            LOGGER.warn(
               "[STExtras] Race prestige QuestDefinition {} missing race mapping! Ensure you use \"race_id\": \"namespace:path\" in your JSON.",
               QuestDefinition.id
            );
            return false;
         }

         if (!RaceRegistry.RACES.contains(QuestDefinition.race)) {
            LOGGER.warn("[STExtras] QuestDefinition {} specifies a race that does not exist in the registry: {}", QuestDefinition.id, QuestDefinition.race);
            return false;
         }
      }

      return true;
   }

   private static boolean isMinecraftAny(ResourceLocation type) {
      return "minecraft".equals(type.getNamespace()) && "any".equals(type.getPath());
   }

   private static boolean validateTypes(QuestDefinition QuestDefinition, Predicate<ResourceLocation> validator) {
      if (QuestDefinition.types.isEmpty()) {
         return false;
      }

      for (ResourceLocation type : QuestDefinition.types) {
         if (!validator.test(type)) {
            return false;
         }
      }

      return true;
   }

   private static boolean validItemFilters(Iterable<ResourceLocation> items) {
      for (ResourceLocation item : items) {
         if (!isMinecraftAny(item) && !BuiltInRegistries.ITEM.containsKey(item)) {
            return false;
         }
      }

      return true;
   }

   private QuestDefinitionValidator() {
   }
}
