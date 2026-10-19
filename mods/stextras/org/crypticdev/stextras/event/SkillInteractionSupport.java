package org.crypticdev.stextras.event;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

public final class SkillInteractionSupport {
   private static final Map<SkillInteractionSupport.PassiveCallbackKey, Boolean> PASSIVE_OVERRIDE_CACHE = new ConcurrentHashMap<>();

   public static boolean overridesPassiveCallback(ManasSkillInstance instance, SkillInteractionSupport.PassiveCallback callback) {
      if (instance != null && callback != null) {
         ManasSkill skill = instance.getSkill();
         return skill == null
            ? false
            : PASSIVE_OVERRIDE_CACHE.computeIfAbsent(
               new SkillInteractionSupport.PassiveCallbackKey(skill.getClass(), callback), key -> resolvePassiveOverride(key.skillClass(), key.callback())
            );
      } else {
         return false;
      }
   }

   private static boolean resolvePassiveOverride(Class<?> skillClass, SkillInteractionSupport.PassiveCallback callback) {
      try {
         Method method = skillClass.getMethod(callback.methodName(), callback.parameterTypes());
         return method.getDeclaringClass() != ManasSkill.class;
      } catch (NoSuchMethodException e) {
         return false;
      }
   }

   private SkillInteractionSupport() {
   }

   public enum PassiveCallback {
      BEING_DAMAGED("onBeingDamaged", ManasSkillInstance.class, LivingEntity.class, DamageSource.class, float.class),
      DAMAGE_ENTITY("onDamageEntity", ManasSkillInstance.class, LivingEntity.class, LivingEntity.class, DamageSource.class, Changeable.class),
      TOUCH_ENTITY("onTouchEntity", ManasSkillInstance.class, LivingEntity.class, LivingEntity.class, DamageSource.class, Changeable.class),
      TAKEN_DAMAGE("onTakenDamage", ManasSkillInstance.class, LivingEntity.class, DamageSource.class, Changeable.class),
      EFFECT_ADDED("onEffectAdded", ManasSkillInstance.class, LivingEntity.class, Entity.class, Changeable.class),
      BEING_TARGETED("onBeingTargeted", ManasSkillInstance.class, Changeable.class, LivingEntity.class),
      PROJECTILE_HIT(
         "onProjectileHit", ManasSkillInstance.class, LivingEntity.class, EntityHitResult.class, Projectile.class, Changeable.class, Changeable.class
      );

      private final String methodName;
      private final Class<?>[] parameterTypes;

      PassiveCallback(String methodName, Class<?>... parameterTypes) {
         this.methodName = methodName;
         this.parameterTypes = parameterTypes;
      }

      private String methodName() {
         return this.methodName;
      }

      private Class<?>[] parameterTypes() {
         return this.parameterTypes;
      }
   }

   private record PassiveCallbackKey(Class<?> skillClass, SkillInteractionSupport.PassiveCallback callback) {
   }
}
