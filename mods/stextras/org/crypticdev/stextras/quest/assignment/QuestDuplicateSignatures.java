package org.crypticdev.stextras.quest.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;

final class QuestDuplicateSignatures {
   static boolean shouldSkipDupes() {
      return PrestigeUtils.CONFIG_PRESTIGE.skipDupe;
   }

   static Set<String> collectActiveSignatures(STExtrasQuestStorage store, QuestCategory... excludedCategories) {
      Set<String> signatures = new HashSet<>();
      if (shouldSkipDupes() && store != null) {
         EnumSet<QuestCategory> excluded = EnumSet.of(QuestCategory.REPEATABLE, QuestCategory.RACE_PRESTIGE);
         if (excludedCategories != null) {
            Collections.addAll(excluded, excludedCategories);
         }

         for (QuestCategory category : QuestCategory.values()) {
            if (!excluded.contains(category)) {
               for (ResourceLocation id : store.active(category)) {
                  addSignature(signatures, QuestRegistry.INSTANCE.byId(id));
               }
            }
         }

         return signatures;
      } else {
         return signatures;
      }
   }

   static boolean isBlocked(QuestDefinition quest, Set<String> blockedSignatures) {
      if (shouldSkipDupes() && blockedSignatures != null && !blockedSignatures.isEmpty()) {
         String signature = signatureOf(quest);
         return signature != null && blockedSignatures.contains(signature);
      } else {
         return false;
      }
   }

   static void addSignature(Set<String> blockedSignatures, QuestDefinition quest) {
      if (shouldSkipDupes() && blockedSignatures != null) {
         String signature = signatureOf(quest);
         if (signature != null) {
            blockedSignatures.add(signature);
         }
      }
   }

   static void addSignatures(Set<String> blockedSignatures, Iterable<ResourceLocation> ids) {
      if (shouldSkipDupes() && blockedSignatures != null && ids != null) {
         for (ResourceLocation id : ids) {
            addSignature(blockedSignatures, QuestRegistry.INSTANCE.byId(id));
         }
      }
   }

   static String signatureOf(QuestDefinition quest) {
      if (quest != null && quest.category != QuestCategory.REPEATABLE && quest.category != QuestCategory.RACE_PRESTIGE && quest.creator != null) {
         List<String> parts = new ArrayList<>();
         if (quest.types != null && !quest.types.isEmpty()) {
            List<String> types = quest.types.stream().filter(type -> type != null).<String>map(ResourceLocation::toString).sorted().toList();
            if (!types.isEmpty()) {
               parts.add("types:" + String.join(",", types));
            }
         }

         addResourcePart(parts, "enchant_items", quest.enchantItems);
         addResourcePart(parts, "enchantments", quest.enchantments);
         if (quest.minEnchantLevel >= 0 || quest.maxEnchantLevel >= 0) {
            parts.add("enchant_level:" + quest.minEnchantLevel + "-" + quest.maxEnchantLevel);
         }

         if (quest.mobCategory != null) {
            parts.add("mob:" + quest.mobCategory.getName());
         }

         if (quest.tags != null && !quest.tags.isEmpty()) {
            List<String> tags = quest.tags.stream().filter(tag -> tag != null && !tag.isBlank()).sorted().toList();
            if (!tags.isEmpty()) {
               parts.add("tags:" + String.join(",", tags));
            }
         }

         return parts.isEmpty() ? null : quest.creator.name() + "|" + String.join("|", parts);
      } else {
         return null;
      }
   }

   private static void addResourcePart(List<String> parts, String key, List<ResourceLocation> values) {
      if (values != null && !values.isEmpty()) {
         List<String> resources = values.stream().filter(value -> value != null).<String>map(ResourceLocation::toString).sorted().toList();
         if (!resources.isEmpty()) {
            parts.add(key + ":" + String.join(",", resources));
         }
      }
   }

   private QuestDuplicateSignatures() {
   }
}
