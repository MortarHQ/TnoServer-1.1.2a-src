package com.sjyueluo.tensura_skill_books.util;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public final class SkillGrantCompat {
   private static final String[] MANAS_SKILL_CLASSES = new String[]{
      "io.github.manasmods.manascore.skill.api.ManasSkill", "com.github.manasmods.manascore.skill.api.ManasSkill"
   };
   private static final String[] SKILL_UTILS_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.SkillUtils", "com.github.manasmods.tensura.ability.SkillUtils"
   };
   private static final String[] SKILL_HELPER_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.SkillHelper", "com.github.manasmods.tensura.ability.SkillHelper"
   };
   private static final String[] TENSURA_SKILL_INSTANCE_CLASSES = new String[]{
      "io.github.manasmods.tensura.ability.TensuraSkillInstance", "com.github.manasmods.tensura.ability.TensuraSkillInstance"
   };
   private static volatile Class<?> cachedManasSkillClass;
   private static volatile Method cachedHasSkillMethod;
   private static volatile Method cachedLearnSkillMethod;
   private static volatile Constructor<?> cachedSkillInstanceConstructor;
   private static volatile Method cachedCreateDefaultInstanceMethod;
   private static volatile Method cachedGetOrCreateTagMethod;

   private SkillGrantCompat() {
   }

   public static SkillGrantCompat.GrantResult grantSkill(LivingEntity livingEntity, Object skill) {
      if (livingEntity != null && skill != null) {
         Class<?> manasSkillClass = resolveManasSkillClass();
         if (manasSkillClass != null && manasSkillClass.isInstance(skill)) {
            try {
               if (hasSkill(livingEntity, skill)) {
                  return SkillGrantCompat.GrantResult.ALREADY_HAS;
               } else {
                  Object instance = createSkillInstance(skill);
                  if (instance == null) {
                     return SkillGrantCompat.GrantResult.INVALID;
                  } else if (learnSkill(livingEntity, instance)) {
                     return SkillGrantCompat.GrantResult.SUCCESS;
                  } else {
                     return hasSkill(livingEntity, skill) ? SkillGrantCompat.GrantResult.ALREADY_HAS : SkillGrantCompat.GrantResult.INVALID;
                  }
               }
            } catch (Throwable throwable) {
               TensuraSkillBooksMod.LOGGER.warn("[{}] Failed to grant skill {}", "tensura_skill_books", skill.getClass().getName(), throwable);
               return SkillGrantCompat.GrantResult.ERROR;
            }
         } else {
            return SkillGrantCompat.GrantResult.INVALID;
         }
      } else {
         return SkillGrantCompat.GrantResult.INVALID;
      }
   }

   public static boolean hasSkillQuietly(LivingEntity livingEntity, Object skill) {
      try {
         return livingEntity != null && skill != null && hasSkill(livingEntity, skill);
      } catch (Throwable ignored) {
         return false;
      }
   }

   private static boolean hasSkill(LivingEntity livingEntity, Object skill) throws ReflectiveOperationException {
      Method method = cachedHasSkillMethod;
      if (method == null) {
         Class<?> utilsClass = resolveClass(SKILL_UTILS_CLASSES);
         Class<?> manasSkillClass = resolveManasSkillClass();
         if (utilsClass == null || manasSkillClass == null) {
            return false;
         }

         method = utilsClass.getMethod("hasSkill", LivingEntity.class, manasSkillClass);
         cachedHasSkillMethod = method;
      }

      return method.invoke(null, livingEntity, skill) instanceof Boolean bool && bool;
   }

   private static boolean learnSkill(LivingEntity livingEntity, Object instance) throws ReflectiveOperationException {
      Method method = cachedLearnSkillMethod;
      if (method == null) {
         Class<?> helperClass = resolveClass(SKILL_HELPER_CLASSES);
         if (helperClass == null) {
            return false;
         }

         for (Method candidate : helperClass.getMethods()) {
            Class<?>[] parameterTypes = candidate.getParameterTypes();
            if (candidate.getName().equals("learnSkill")
               && parameterTypes.length == 4
               && parameterTypes[0] == LivingEntity.class
               && parameterTypes[2] == int.class
               && MutableComponent.class.isAssignableFrom(parameterTypes[3])
               && parameterTypes[1].getName().endsWith("ManasSkillInstance")) {
               method = candidate;
               cachedLearnSkillMethod = candidate;
               break;
            }
         }
      }

      return method == null ? false : method.invoke(null, livingEntity, instance, -1, Component.empty()) instanceof Boolean bool && bool;
   }

   private static Object createSkillInstance(Object skill) throws ReflectiveOperationException {
      Object instance = tryCreateTensuraInstance(skill);
      if (instance == null) {
         instance = tryCreateDefaultInstance(skill);
      }

      if (instance != null) {
         markNoMagiculeCost(instance);
      }

      return instance;
   }

   private static Object tryCreateTensuraInstance(Object skill) throws ReflectiveOperationException {
      Constructor<?> constructor = cachedSkillInstanceConstructor;
      if (constructor == null) {
         Class<?> instanceClass = resolveClass(TENSURA_SKILL_INSTANCE_CLASSES);
         Class<?> manasSkillClass = resolveManasSkillClass();
         if (instanceClass == null || manasSkillClass == null) {
            return null;
         }

         constructor = instanceClass.getConstructor(manasSkillClass);
         cachedSkillInstanceConstructor = constructor;
      }

      return constructor.newInstance(skill);
   }

   private static Object tryCreateDefaultInstance(Object skill) throws ReflectiveOperationException {
      Method method = cachedCreateDefaultInstanceMethod;
      if (method == null) {
         Class<?> manasSkillClass = resolveManasSkillClass();
         if (manasSkillClass == null) {
            return null;
         }

         method = manasSkillClass.getMethod("createDefaultInstance");
         cachedCreateDefaultInstanceMethod = method;
      }

      return method.invoke(skill);
   }

   private static void markNoMagiculeCost(Object instance) {
      try {
         Method method = cachedGetOrCreateTagMethod;
         if (method == null) {
            method = instance.getClass().getMethod("getOrCreateTag");
            cachedGetOrCreateTagMethod = method;
         }

         if (method.invoke(instance) instanceof CompoundTag compoundTag) {
            compoundTag.putBoolean("NoMagiculeCost", true);
         }
      } catch (Throwable var4) {
      }
   }

   private static Class<?> resolveManasSkillClass() {
      Class<?> clazz = cachedManasSkillClass;
      if (clazz != null) {
         return clazz;
      }

      clazz = resolveClass(MANAS_SKILL_CLASSES);
      cachedManasSkillClass = clazz;
      return clazz;
   }

   private static Class<?> resolveClass(String[] names) {
      for (String name : names) {
         try {
            return Class.forName(name);
         } catch (Throwable var6) {
         }
      }

      return null;
   }

   public enum GrantResult {
      SUCCESS,
      ALREADY_HAS,
      INVALID,
      ERROR;
   }
}
