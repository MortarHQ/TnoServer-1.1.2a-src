package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import io.github.manasmods.tensura.entity.template.subclass.IFlying;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvalonNovariAntiFlight {
   private AvalonNovariAntiFlight() {
   }

   public static void tick(AvalonNovariEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         if (boss.tickCount % 2 == 0) {
            double radius = boss.getPhase() >= 4 ? 96.0 : (boss.getPhase() >= 3 ? 72.0 : 48.0);
            AABB box = boss.getBoundingBox().inflate(radius, 96.0, radius);

            for (Entity entity : level.getEntitiesOfClass(Entity.class, box, Entity::isAlive)) {
               if (entity != boss
                  && !(entity instanceof ServerPlayer player && (player.isCreative() || player.isSpectator()))
                  && entity instanceof LivingEntity living
                  && boss.isValidEnemy(living)) {
                  forceGrounded(living, boss);
               }
            }
         }
      }
   }

   private static void forceGrounded(LivingEntity living, AvalonNovariEntity boss) {
      try {
         if (living instanceof IFlying flying) {
            flying.setFlying(false);
         }
      } catch (Throwable var7) {
      }

      if (living instanceof ServerPlayer player) {
         try {
            if (player.getAbilities().flying) {
               player.getAbilities().flying = false;
               player.onUpdateAbilities();
            }
         } catch (Throwable var6) {
         }

         try {
            player.stopFallFlying();
         } catch (Throwable var5) {
         }
      }

      if (living instanceof Mob mob) {
         mob.getNavigation().stop();
      }

      if (!living.onGround()) {
         Vec3 motion = living.getDeltaMovement();
         double down = boss.getPhase() >= 4 ? -0.42 : -0.26;
         living.setDeltaMovement(motion.x * 0.72, Math.min(motion.y, down), motion.z * 0.72);
         living.hurtMarked = true;
         living.fallDistance = 0.0F;
      }

      living.setNoGravity(false);
   }
}
