package com.sjyueluo.tensura_skill_books.catalog;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import com.sjyueluo.tensura_skill_books.util.SkillCompat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class SkillCatalog {
   private static volatile List<SkillEntry> cachedAll;
   private static volatile Map<SkillCategory, List<SkillEntry>> cachedByCategory;

   private SkillCatalog() {
   }

   public static List<SkillEntry> all() {
      List<SkillEntry> all = cachedAll;
      if (all != null) {
         return all;
      }

      synchronized (SkillCatalog.class) {
         if (cachedAll != null) {
            return cachedAll;
         }

         Registry<?> registry = SkillCompat.getSkillRegistry();
         List<SkillEntry> built = new ArrayList<>();
         if (registry != null) {
            Registry<Object> typedRegistry = (Registry<Object>)registry;

            for (Object skill : registry) {
               if (skill != null) {
                  ResourceLocation id = typedRegistry.getKey(skill);
                  if (id != null) {
                     built.add(new SkillEntry(id, skill, SkillCompat.categorize(skill)));
                  }
               }
            }

            built.sort(Comparator.comparing(entry -> entry.id().toString()));
         } else {
            TensuraSkillBooksMod.LOGGER.warn("[{}] Skill registry unavailable; catalog is empty.", "tensura_skill_books");
         }

         cachedAll = built;
         cachedByCategory = null;
         return built;
      }
   }

   public static Map<SkillCategory, List<SkillEntry>> byCategory() {
      Map<SkillCategory, List<SkillEntry>> map = cachedByCategory;
      if (map != null) {
         return map;
      }

      synchronized (SkillCatalog.class) {
         if (cachedByCategory != null) {
            return cachedByCategory;
         }

         Map<SkillCategory, List<SkillEntry>> built = new EnumMap<>(SkillCategory.class);

         for (SkillCategory category : SkillCategory.values()) {
            built.put(category, new ArrayList<>());
         }

         for (SkillEntry entry : all()) {
            built.get(entry.category()).add(entry);
         }

         cachedByCategory = built;
         return built;
      }
   }

   public static Object get(ResourceLocation id) {
      if (id == null) {
         return null;
      }

      try {
         Registry<?> registry = SkillCompat.getSkillRegistry();
         return registry != null ? registry.get(id) : null;
      } catch (Throwable t) {
         TensuraSkillBooksMod.LOGGER.warn("[{}] Failed to resolve skill {}", "tensura_skill_books", id, t);
         return null;
      }
   }

   public static void invalidate() {
      cachedAll = null;
      cachedByCategory = null;
   }
}
