package com.tensura_tno.compat;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.function.BooleanSupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;

public final class IceAndFireEntityCompat {
   private static final ResourceLocation DRAGON_SKULL = iceAndFireId("dragon_skull");
   private static final Set<ResourceLocation> DRAGON_TYPES = Set.of(iceAndFireId("fire_dragon"), iceAndFireId("ice_dragon"), iceAndFireId("lightning_dragon"));
   private static final ClassValue<Optional<Method>> DEAD_STATE_METHOD = new ClassValue<Optional<Method>>() {
      protected Optional<Method> computeValue(Class<?> type) {
         for (String methodName : new String[]{"isModelDead", "isMobDead"}) {
            try {
               Method method = type.getMethod(methodName);
               Class<?> returnType = method.getReturnType();
               if (returnType == boolean.class || returnType == Boolean.class) {
                  return Optional.of(method);
               }
            } catch (ReflectiveOperationException | RuntimeException var8) {
            }
         }

         return Optional.empty();
      }
   };

   private IceAndFireEntityCompat() {
   }

   public static boolean isDragonRemains(Entity entity) {
      if (entity instanceof PartEntity<?> part) {
         entity = part.getParent();
      }

      if (entity == null) {
         return false;
      }

      ResourceLocation entityTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      Entity dragon = entity;
      return isDragonRemains(entityTypeId, () -> readModelDead(dragon));
   }

   public static boolean isTensuraCombatEntity(Entity entity) {
      return entity != null && isTensuraCombatEntity(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
   }

   static boolean isTensuraCombatEntity(ResourceLocation entityTypeId) {
      return entityTypeId != null && ("tensura".equals(entityTypeId.getNamespace()) || "tensura_neb".equals(entityTypeId.getNamespace()));
   }

   public static boolean shouldIgnoreDragonRemains(Entity attacker, Entity target) {
      return isTensuraCombatEntity(attacker) && isDragonRemains(target);
   }

   public static boolean shouldBlockDragonRemainsDamage(DamageSource source, Entity target) {
      Entity attacker = source.getEntity() != null ? source.getEntity() : source.getDirectEntity();
      return shouldIgnoreDragonRemains(attacker, target);
   }

   static boolean isDragonRemains(ResourceLocation entityTypeId, BooleanSupplier modelDeadState) {
      if (DRAGON_SKULL.equals(entityTypeId)) {
         return true;
      }

      if (entityTypeId != null && DRAGON_TYPES.contains(entityTypeId)) {
         try {
            return modelDeadState.getAsBoolean();
         } catch (RuntimeException ignored) {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean readModelDead(Entity entity) {
      Optional<Method> method = DEAD_STATE_METHOD.get(entity.getClass());
      if (!method.isPresent()) {
         return true;
      }

      try {
         return !(method.get().invoke(entity) instanceof Boolean dead && !dead);
      } catch (ReflectiveOperationException | RuntimeException ignored) {
         return true;
      }
   }

   private static ResourceLocation iceAndFireId(String path) {
      return ResourceLocation.fromNamespaceAndPath("iceandfire", path);
   }
}
