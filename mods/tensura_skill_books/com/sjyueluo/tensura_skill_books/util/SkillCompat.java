package com.sjyueluo.tensura_skill_books.util;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class SkillCompat {
   private static final String[] SKILL_REGISTRY_CLASSES = new String[]{
      "io.github.manasmods.manascore.skill.impl.SkillRegistry", "com.github.manasmods.manascore.skill.SkillRegistry"
   };
   private static final String[] TENSURA_SKILL_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.skill.Skill", "com.github.manasmods.tensura.ability.skill.Skill"
   };
   private static final String[] BATTLEWILL_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.battlewill.Battlewill", "com.github.manasmods.tensura.ability.battlewill.Battewill"
   };
   private static final String[] MAGIC_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.magic.Magic", "com.github.manasmods.tensura.ability.magic.Magic"
   };
   private static volatile Registry<?> cachedRegistry;
   private static volatile Class<?> cachedTensuraSkillClass;
   private static volatile Class<?> cachedBattlewillClass;
   private static volatile Class<?> cachedMagicClass;

   private SkillCompat() {
   }

   public static Registry<?> getSkillRegistry() {
      Registry<?> registry = cachedRegistry;
      if (registry != null) {
         return registry;
      }

      synchronized (SkillCompat.class) {
         if (cachedRegistry != null) {
            return cachedRegistry;
         }

         for (String className : SKILL_REGISTRY_CLASSES) {
            try {
               Class<?> registryClass = Class.forName(className);
               Field keyField = registryClass.getDeclaredField("KEY");
               if (keyField.get(null) instanceof ResourceKey<?> resourceKey) {
                  Registry<?> resolved = (Registry<?>)BuiltInRegistries.REGISTRY.get(resourceKey.location());
                  if (resolved != null) {
                     cachedRegistry = resolved;
                     return resolved;
                  }
               }
            } catch (Throwable var12) {
            }
         }

         return null;
      }
   }

   public static Component getSkillName(Object skill) {
      if (skill == null) {
         return Component.empty();
      }

      try {
         if (invokeNoArgs(skill, "getName") instanceof Component component) {
            return component;
         }
      } catch (Throwable t) {
         TensuraSkillBooksMod.LOGGER.warn("[{}] Failed to read skill name from {}", "tensura_skill_books", skill.getClass().getName(), t);
      }

      return Component.literal("Unknown Skill");
   }

   public static ResourceLocation getSkillIcon(Object skill) {
      if (skill == null) {
         return null;
      }

      try {
         return invokeNoArgs(skill, "getSkillIcon") instanceof ResourceLocation resourceLocation ? resourceLocation : null;
      } catch (Throwable t) {
         TensuraSkillBooksMod.LOGGER.warn("[{}] Failed to read skill icon from {}", "tensura_skill_books", skill.getClass().getName(), t);
         return null;
      }
   }

   public static SkillCategory categorize(Object skill) {
      if (skill == null) {
         return SkillCategory.MISC;
      }

      if (isBattlewill(skill)) {
         return SkillCategory.COMBAT;
      }

      if (isTensuraSkill(skill)) {
         try {
            Object type = invokeNoArgs(skill, "getType");
            String name = Objects.toString(type, "");

            return switch (name) {
               case "COMMON" -> SkillCategory.COMMON;
               case "RESISTANCE" -> SkillCategory.RESISTANCE;
               case "INTRINSIC" -> SkillCategory.INTRINSIC;
               case "EXTRA" -> SkillCategory.EXTRA;
               case "UNIQUE" -> SkillCategory.UNIQUE;
               case "ULTIMATE" -> SkillCategory.ULTIMATE;
               default -> SkillCategory.MISC;
            };
         } catch (Throwable var6) {
         }
      }

      if (isMagic(skill)) {
         try {
            Object type = invokeNoArgs(skill, "getType");
            String name = Objects.toString(type, "");

            return switch (name) {
               case "ASPECTUAL" -> SkillCategory.MAGIC_ASPECTUAL;
               case "SPIRITUAL" -> SkillCategory.MAGIC_SPIRITUAL;
               case "SUMMONING" -> SkillCategory.MAGIC_SUMMONING;
               case "MISC" -> SkillCategory.MAGIC_MISC;
               default -> SkillCategory.MISC;
            };
         } catch (Throwable var5) {
         }
      }

      return SkillCategory.MISC;
   }

   public static boolean isStudyBookSkill(Object skill) {
      return skill == null ? false : isBattlewill(skill) || isTensuraSkill(skill) || isMagic(skill);
   }

   private static boolean isTensuraSkill(Object skill) {
      Class<?> clazz = resolveClass(TENSURA_SKILL_CLASSES, false);
      return clazz != null && clazz.isInstance(skill);
   }

   private static boolean isBattlewill(Object skill) {
      Class<?> clazz = resolveClass(BATTLEWILL_CLASSES, true);
      return clazz != null && clazz.isInstance(skill);
   }

   private static boolean isMagic(Object skill) {
      Class<?> clazz = cachedMagicClass;
      if (clazz == null) {
         for (String className : MAGIC_CLASSES) {
            try {
               clazz = Class.forName(className);
               cachedMagicClass = clazz;
               break;
            } catch (Throwable var7) {
            }
         }
      }

      return clazz != null && clazz.isInstance(skill);
   }

   private static Class<?> resolveClass(String[] classNames, boolean battlewill) {
      Class<?> cached = battlewill ? cachedBattlewillClass : cachedTensuraSkillClass;
      if (cached != null) {
         return cached;
      }

      for (String className : classNames) {
         try {
            Class<?> resolved = Class.forName(className);
            if (battlewill) {
               cachedBattlewillClass = resolved;
            } else {
               cachedTensuraSkillClass = resolved;
            }

            return resolved;
         } catch (Throwable var8) {
         }
      }

      return null;
   }

   private static Object invokeNoArgs(Object target, String methodName) throws ReflectiveOperationException {
      Method method = target.getClass().getMethod(methodName);
      return method.invoke(target);
   }
}
