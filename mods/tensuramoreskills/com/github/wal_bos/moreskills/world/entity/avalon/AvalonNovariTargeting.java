package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public final class AvalonNovariTargeting {
   private AvalonNovariTargeting() {
   }

   public static void tick(AvalonNovariEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         if (boss.tickCount % 5 == 0) {
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
                  double range = Math.max(96.0, boss.getAttributeValue(Attributes.FOLLOW_RANGE));
                  AABB box = boss.getBoundingBox().inflate(range, 96.0, range);
                  List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy);
                  LivingEntity best = null;
                  double bestScore = Double.MAX_VALUE;

                  for (LivingEntity living : list) {
                     if (!(living instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                        double score = boss.distanceToSqr(living);
                        if (boss.getLastHurtByMob() == living) {
                           score *= 0.25;
                        }

                        if (score < bestScore) {
                           bestScore = score;
                           best = living;
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
         try {
            living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
         } catch (Throwable var4) {
         }

         try {
            living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SHADOW_STEP));
         } catch (Throwable var3) {
         }
      }
   }
}
