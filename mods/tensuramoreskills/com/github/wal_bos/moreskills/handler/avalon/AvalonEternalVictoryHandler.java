package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonEternalVictoryVfxPayload;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvalonEternalVictoryHandler implements AvalonModeHandler {
   private static final Map<UUID, AvalonEternalVictoryHandler.PendingCast> PENDING = new HashMap<>();
   private static final float SKY_IGNITION_TICK = 108.0F;
   private static final float SLAM_START_TICK = 240.0F;
   private static final float IMPACT_TICK = 286.0F;
   private static final float DETONATION_TICK = 366.0F;
   private static final float FINAL_BEAM_TICK = 378.0F;
   private static final float CLEANUP_TICK = 596.0F;
   private static final int VFX_DURATION_TICKS = 2000;
   private static final float TRAVEL_TICKS = 46.0F;
   private static final double HIT_RADIUS = 13.0;
   private static final double FINAL_BLAST_RADIUS = 200.0;
   private static final double FINAL_BLAST_VERTICAL = 112.0;
   private static final int MAX_TRAVEL_HITS_PER_TICK = 6;
   private static final int MAX_FINAL_HITS_PER_TICK = 8;

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.excalibur_eternal_victory";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictoryCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!entity.getMainHandItem().is((Item)TenaddexItems.DIVINE_EXCALIBUR.get())) {
            entity.sendSystemMessage(Component.literal("You must hold Divine Excalibur.").withStyle(ChatFormatting.RED));
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            double range = (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictoryRange.get();
            LivingEntity target = RoyalSkillSupport.lookTarget(entity, range);
            Vec3 start = RoyalSkillSupport.eye(entity);
            Vec3 end = target != null ? target.position().add(0.0, target.getBbHeight() * 0.5, 0.0) : start.add(entity.getLookAngle().normalize().scale(range));
            Vec3 direction = end.subtract(start);
            if (direction.lengthSqr() < 1.0E-8) {
               direction = entity.getLookAngle();
            }

            if (direction.lengthSqr() < 1.0E-8) {
               direction = new Vec3(0.0, 0.0, 1.0);
            }

            direction = direction.normalize();
            Vec3 damageStart = start.add(direction.scale(4.4));
            PENDING.put(entity.getUUID(), new AvalonEternalVictoryHandler.PendingCast(damageStart, end, direction, Util.getMillis(), mode));
            AvalonEternalVictoryVfxPayload.send(level, entity, target, start, end, 18.0F, 2000, 0);
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.8F, 1.64F);
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.8F, 0.9F);
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.avalon.eternalVictoryCooldown.get(), mode);
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         AvalonEternalVictoryHandler.PendingCast cast = PENDING.get(entity.getUUID());
         if (cast != null) {
            if (entity.isAlive() && !entity.isRemoved()) {
               float age = Math.max(0.0F, (float)(Util.getMillis() - cast.createdMillis) / 50.0F);
               if (!cast.skySound && age >= 108.0F) {
                  cast.skySound = true;
                  level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 8.8F, 1.22F);
                  level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 7.0F, 0.74F);
               }

               if (!cast.slamSound && age >= 240.0F) {
                  cast.slamSound = true;
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 10.2F, 0.74F);
                  level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 7.6F, 0.44F);
               }

               if (age >= 240.0F && !cast.travelFinished) {
                  float progress = smoother(Math.min(1.0F, (age - 240.0F) / 46.0F));
                  if (progress > cast.lastScannedProgress && ((entity.tickCount & 3) == 0 || progress >= 1.0F)) {
                     Vec3 segmentStart = cast.start.lerp(cast.end, cast.lastScannedProgress);
                     Vec3 segmentEnd = cast.start.lerp(cast.end, progress);
                     queueTravelHits(level, entity, cast, segmentStart, segmentEnd);
                     cast.lastScannedProgress = progress;
                  }

                  processTravelQueue(entity, instance, cast);
                  if (progress >= 1.0F && cast.lastScannedProgress >= 1.0F && cast.travelQueue.isEmpty()) {
                     cast.travelFinished = true;
                  }
               }

               if (!cast.impactSound && age >= 286.0F) {
                  cast.impactSound = true;
                  level.playSound(null, cast.end.x, cast.end.y, cast.end.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 11.8F, 0.6F);
                  level.playSound(null, cast.end.x, cast.end.y, cast.end.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 8.2F, 1.22F);
               }

               if (age >= 366.0F) {
                  if (!cast.detonated) {
                     cast.detonated = true;
                     spawnTensuraExplosion(level, entity, instance, cast);
                     level.playSound(
                        null, cast.end.x, cast.end.y, cast.end.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 15.0F, 0.34F
                     );
                     level.playSound(
                        null, cast.end.x, cast.end.y, cast.end.z, (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 14.0F, 0.48F
                     );
                     level.playSound(null, cast.end.x, cast.end.y, cast.end.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 11.0F, 0.44F);
                  }

                  if (!cast.finalTargetsPrepared) {
                     prepareFinalTargets(level, entity, cast);
                  }

                  processFinalTargets(entity, instance, cast);
                  if (cast.finalCursor >= cast.finalTargets.size()) {
                     cast.finalFinished = true;
                  }
               }

               if (!cast.finalBeamSound && age >= 378.0F) {
                  cast.finalBeamSound = true;
                  level.playSound(null, cast.end.x, cast.end.y, cast.end.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 12.0F, 0.36F);
                  level.playSound(null, cast.end.x, cast.end.y, cast.end.z, (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 12.4F, 0.6F);
               }

               if (age >= 596.0F) {
                  PENDING.remove(entity.getUUID());
               }
            } else {
               PENDING.remove(entity.getUUID());
            }
         }
      }
   }

   private static void queueTravelHits(ServerLevel level, LivingEntity owner, AvalonEternalVictoryHandler.PendingCast cast, Vec3 segmentStart, Vec3 segmentEnd) {
      if (!(segmentStart.distanceToSqr(segmentEnd) <= 1.0E-8)) {
         AABB corridor = new AABB(segmentStart, segmentEnd).inflate(16.5);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, corridor, living -> RoyalSkillSupport.validEnemy(owner, living))) {
            UUID id = target.getUUID();
            if (!cast.travelSeen.contains(id)) {
               Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
               double radius = 13.0 + target.getBbWidth() * 0.5;
               if (!(RoyalSkillSupport.distanceToSegment(center, segmentStart, segmentEnd) > radius)) {
                  cast.travelSeen.add(id);
                  cast.travelQueue.addLast(target);
               }
            }
         }
      }
   }

   private static void processTravelQueue(LivingEntity entity, ManasSkillInstance instance, AvalonEternalVictoryHandler.PendingCast cast) {
      int processed = 0;

      while (!cast.travelQueue.isEmpty() && processed < 6) {
         LivingEntity target = cast.travelQueue.removeFirst();
         if (RoyalSkillSupport.validEnemy(entity, target)) {
            Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            double radius = 13.0 + target.getBbWidth() * 0.5;
            if (!(RoyalSkillSupport.distanceToSegment(center, cast.start, cast.end) > radius)) {
               applyTravelHit(entity, instance, cast, target);
               processed++;
            }
         }
      }
   }

   private static void applyTravelHit(LivingEntity entity, ManasSkillInstance instance, AvalonEternalVictoryHandler.PendingCast cast, LivingEntity target) {
      double soul = RoyalSkillSupport.existence(target).getSoulPoints();
      float damage = (float)Math.min(
         Float.MAX_VALUE,
         (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictoryBaseDamage.get() + soul * (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictorySoulScale.get()
      );
      unavoidableEternalVictory(target, entity, instance, cast.mode, damage);
      RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 300, 2);
      IEffect effectStorage = TensuraStorages.getEffectFrom(target);
      effectStorage.increaseSeveranceAmount(damage * 0.35F);
      effectStorage.markDirty();
      RoyalSkillSupport.markCombat(entity, target);
      awardMastery(entity, instance, cast);
   }

   private static void spawnTensuraExplosion(ServerLevel level, LivingEntity entity, ManasSkillInstance instance, AvalonEternalVictoryHandler.PendingCast cast) {
      float damage = (float)Math.max(2200.0, (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictoryBaseDamage.get() * 1.35);
      MagicExplosion explosion = new MagicExplosion(level, entity);
      explosion.setSecondaryDamage(damage);
      if (instance.getSkill() instanceof TensuraSkill tensuraSkill) {
         explosion.setSkill(entity, instance, tensuraSkill, cast.mode, 0.0F);
      } else {
         explosion.setSkill(instance);
      }

      explosion.setSize(200.0F);
      explosion.setPos(cast.end.add(0.0, 0.2, 0.0));
      level.addFreshEntity(explosion);
   }

   private static void prepareFinalTargets(ServerLevel level, LivingEntity entity, AvalonEternalVictoryHandler.PendingCast cast) {
      cast.finalTargetsPrepared = true;
      Vec3 center = cast.end;
      AABB area = new AABB(center, center).inflate(200.0, 112.0, 200.0);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, targetx -> RoyalSkillSupport.validEnemy(entity, targetx))) {
         Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         double dx = targetCenter.x - center.x;
         double dz = targetCenter.z - center.z;
         double horizontal = Math.sqrt(dx * dx + dz * dz);
         if (!(horizontal > 200.0)) {
            float falloff = (float)Math.max(0.18, 1.0 - horizontal / 200.0);
            cast.finalTargets.add(new AvalonEternalVictoryHandler.FinalTarget(target, falloff));
         }
      }

      cast.finalTargets.sort(Comparator.comparingDouble(targetx -> targetx.target.distanceToSqr(cast.end)));
   }

   private static void processFinalTargets(LivingEntity entity, ManasSkillInstance instance, AvalonEternalVictoryHandler.PendingCast cast) {
      int processed = 0;

      while (cast.finalCursor < cast.finalTargets.size() && processed < 8) {
         AvalonEternalVictoryHandler.FinalTarget queued = cast.finalTargets.get(cast.finalCursor++);
         LivingEntity target = queued.target;
         if (RoyalSkillSupport.validEnemy(entity, target)) {
            float baseDamage = (float)Math.max(2200.0, (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictoryBaseDamage.get() * 1.35);
            double soul = RoyalSkillSupport.existence(target).getSoulPoints();
            float damage = (float)Math.min(
               Float.MAX_VALUE,
               (baseDamage + soul * (Double)MoreSkillsConfigs.GRAND.avalon.eternalVictorySoulScale.get() * 0.7) * (0.55F + queued.falloff * 0.9F)
            );
            unavoidableEternalVictory(target, entity, instance, cast.mode, damage);
            RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 320, 2);
            IEffect effectStorage = TensuraStorages.getEffectFrom(target);
            effectStorage.increaseSeveranceAmount(damage * (0.28F + queued.falloff * 0.32F));
            effectStorage.markDirty();
            RoyalSkillSupport.markCombat(entity, target);
            awardMastery(entity, instance, cast);
            processed++;
         }
      }
   }

   private static void unavoidableEternalVictory(LivingEntity target, LivingEntity owner, ManasSkillInstance instance, int mode, float totalDamage) {
      if (!(totalDamage <= 0.0F) && target.isAlive()) {
         target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT));
         DamageSource holy = RoyalSkillSupport.authoritySource(owner, instance, mode, TensuraDamageTypes.HOLY_DAMAGE);
         DamageSource light = RoyalSkillSupport.authoritySource(owner, instance, mode, TensuraDamageTypes.LIGHT_ELEMENTAL);
         RoyalSkillSupport.unavoidableDamage(target, holy, totalDamage * 0.55F);
         if (target.isAlive()) {
            RoyalSkillSupport.unavoidableDamage(target, light, totalDamage * 0.45F);
         }
      }
   }

   private static void awardMastery(LivingEntity entity, ManasSkillInstance instance, AvalonEternalVictoryHandler.PendingCast cast) {
      if (!cast.masteryAwarded) {
         cast.masteryAwarded = true;
         instance.addMasteryPoint(entity);
      }
   }

   private static float smoother(float value) {
      value = Math.max(0.0F, Math.min(1.0F, value));
      return value * value * value * (value * (value * 6.0F - 15.0F) + 10.0F);
   }

   @Override
   public void cleanup(LivingEntity entity) {
      PENDING.remove(entity.getUUID());
   }

   private static final class FinalTarget {
      private final LivingEntity target;
      private final float falloff;

      private FinalTarget(LivingEntity target, float falloff) {
         this.target = target;
         this.falloff = falloff;
      }
   }

   private static final class PendingCast {
      private final Vec3 start;
      private final Vec3 end;
      private final Vec3 direction;
      private final long createdMillis;
      private final int mode;
      private final ArrayDeque<LivingEntity> travelQueue = new ArrayDeque<>();
      private final Set<UUID> travelSeen = new HashSet<>();
      private final List<AvalonEternalVictoryHandler.FinalTarget> finalTargets = new ArrayList<>();
      private float lastScannedProgress;
      private boolean skySound;
      private boolean slamSound;
      private boolean impactSound;
      private boolean detonated;
      private boolean finalBeamSound;
      private boolean travelFinished;
      private boolean finalTargetsPrepared;
      private boolean finalFinished;
      private boolean masteryAwarded;
      private int finalCursor;

      private PendingCast(Vec3 start, Vec3 end, Vec3 direction, long createdMillis, int mode) {
         this.start = start;
         this.end = end;
         this.direction = direction;
         this.createdMillis = createdMillis;
         this.mode = mode;
      }
   }
}
