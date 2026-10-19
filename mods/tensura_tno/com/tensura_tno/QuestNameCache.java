package com.tensura_tno;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;

public final class QuestNameCache {
   private static final String LANG_PREFIX = "@lang:";
   private static final Map<ResourceLocation, QuestNameCache.Entry> QUESTS = new HashMap<>();

   private QuestNameCache() {
   }

   public static void update(List<?> quests, boolean replace) {
      Map<ResourceLocation, QuestNameCache.Entry> previous = new HashMap<>(QUESTS);
      if (replace) {
         QUESTS.clear();
      }

      for (Object quest : quests) {
         if (quest != null) {
            try {
               ResourceLocation id = (ResourceLocation)quest.getClass().getField("id").get(quest);
               if (id != null) {
                  Field nameField = quest.getClass().getField("name");
                  QuestNameCache.Entry old = previous.get(id);
                  String original = old != null && old.quest == quest ? old.original : (String)nameField.get(quest);
                  QuestNameCache.Entry entry = new QuestNameCache.Entry(quest, nameField, original);
                  QUESTS.put(id, entry);
                  nameField.set(quest, resolveName(id, original));
               }
            } catch (ReflectiveOperationException var10) {
            }
         }
      }
   }

   public static void reapply() {
      QUESTS.forEach((id, entry) -> {
         try {
            entry.nameField.set(entry.quest, resolveName(id, entry.original));
         } catch (IllegalAccessException var3) {
         }
      });
   }

   public static String resolveName(ResourceLocation id, String fallback) {
      QuestNameCache.Entry entry = id == null ? null : QUESTS.get(id);
      String original = entry == null ? fallback : entry.original;
      Language language = Language.getInstance();
      if (original != null && original.startsWith("@lang:")) {
         return resolveLangExpression(language, original.substring("@lang:".length()));
      }

      if (id != null) {
         String key = id.getNamespace() + ".quest.name." + id.getPath();
         if (language.has(key)) {
            return language.getOrDefault(key);
         }
      }

      return original;
   }

   public static String resolveLangExpression(Language language, String expression) {
      StringBuilder result = new StringBuilder();

      for (String part : expression.split("\\+")) {
         if (!part.isEmpty()) {
            result.append(part.startsWith("#") ? part.substring(1) : language.getOrDefault(part));
         }
      }

      return result.toString();
   }

   private record Entry(Object quest, Field nameField, String original) {
   }
}
