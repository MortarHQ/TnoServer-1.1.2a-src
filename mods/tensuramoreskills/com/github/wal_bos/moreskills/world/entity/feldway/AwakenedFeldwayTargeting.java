package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public final class AwakenedFeldwayTargeting {
   private AwakenedFeldwayTargeting() {
   }

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   public static void tick(FeldwayAwakenedEntity boss) {
      if (boss.level() instanceof ServerLevel sl) {
         int var16 = Math.max(1, (Integer)cfg().targetingTickInterval.get());
         if (boss.tickCount % var16 == 0) {
            LivingEntity locked = boss.resolveLockedTarget();
            if (locked != null && locked.isAlive()) {
               boss.setTarget(locked);
               reveal(locked);
            } else {
               LivingEntity current = boss.getTarget();
               if (current != null && boss.isValidEnemy(current)) {
                  boss.setLockedTarget(current.getUUID());
                  reveal(current);
               } else {
                  double range = Math.max((Double)cfg().targetingMinRange.get(), boss.getAttributeValue(Attributes.FOLLOW_RANGE));
                  AABB box = boss.getBoundingBox().inflate(range, (Double)cfg().targetingVerticalInflate.get(), range);
                  List<LivingEntity> list = sl.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy);
                  LivingEntity best = null;
                  double bestDist = Double.MAX_VALUE;

                  for (LivingEntity entity : list) {
                     if (!(entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                        double dist = boss.distanceToSqr(entity);
                        if (dist < bestDist) {
                           bestDist = dist;
                           best = entity;
                        }
                     }
                  }

                  if (best != null) {
                     boss.setLockedTarget(best.getUUID());
                     boss.setTarget(best);
                     reveal(best);
                  }
               }
            }
         }
      }
   }

   private static void reveal(Entity entity) {
      if (entity instanceof LivingEntity living) {
         if ((Boolean)cfg().revealPresenceConcealment.get()) {
            try {
               living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
            } catch (Throwable var4) {
            }
         }

         if ((Boolean)cfg().revealShadowStep.get()) {
            try {
               living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SHADOW_STEP));
            } catch (Throwable var3) {
            }
         }
      }
   }
}
