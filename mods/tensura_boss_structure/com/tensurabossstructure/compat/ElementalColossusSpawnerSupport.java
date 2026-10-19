package com.tensurabossstructure.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ElementalColossusSpawnerSupport {
   public static final String ENTITY_ID = "tensura:elemental_colossus";
   public static final String SPAWNER_TAG = "tensura_boss_structure_elemental_colossus_spawner";
   private static final Logger LOGGER = LoggerFactory.getLogger("tensura_boss_structure");

   private ElementalColossusSpawnerSupport() {
   }

   public static void prepareSpawnedMob(Mob mob, String entityId) {
      if ("tensura:elemental_colossus".equals(entityId)) {
         mob.addTag("tensura_boss_structure_elemental_colossus_spawner");

         try {
            mob.getClass().getMethod("setSleeping", boolean.class).invoke(mob, false);
         } catch (ReflectiveOperationException exception) {
            LOGGER.warn("Could not wake Elemental Colossus spawned by boss spawner", exception);
         }
      }
   }

   public static boolean isSpawnerColossus(Object entity) {
      return entity instanceof Entity minecraftEntity && minecraftEntity.getTags().contains("tensura_boss_structure_elemental_colossus_spawner");
   }

   public static boolean isValidPlayerTarget(LivingEntity target) {
      return target instanceof Player && target.isAlive() && !target.isSpectator() && !target.hasInfiniteMaterials();
   }

   public static boolean shouldKeepPlayerTarget(Mob mob, LivingEntity target) {
      if (isValidPlayerTarget(target) && !mob.isAlliedTo(target) && mob.canAttack(target)) {
         double followRange = mob.getAttributeValue(Attributes.FOLLOW_RANGE);
         double maxDistance = followRange * 2.0;
         return followRange > 0.0 && mob.distanceToSqr(target) <= maxDistance * maxDistance;
      } else {
         return false;
      }
   }
}
