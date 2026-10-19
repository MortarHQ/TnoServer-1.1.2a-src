package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.ability.skill.ultimate.ArthurOnceAndFutureKingSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.ArthurPromisedVictoryVfxPayload;
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

public final class ArthurExcaliburPromisedVictoryHandler implements ArthurModeHandler {
   private static final Map<UUID, ArthurExcaliburPromisedVictoryHandler.PendingSlash> PENDING = new HashMap<>();
   private static final float LAUNCH_DELAY_TICKS = 68.0F;
   private static final float TRAVEL_TICKS = 88.0F;
   private static final float FINAL_EXPLOSION_TICK = 220.0F;
   private static final float SOFT_CLEANUP_TICK = 300.0F;
   private static final float HARD_CLEANUP_TICK = 440.0F;
   private static final double HIT_RADIUS = 6.5;
   private static final double FINAL_BLAST_RADIUS = 100.0;
   private static final double FINAL_BLAST_VERTICAL = 56.0;
   private static final int VFX_DURATION_TICKS = 334;
   private static final int MAX_TRAVEL_HITS_PER_TICK = 6;
   private static final int MAX_FINAL_HITS_PER_TICK = 8;
   private static final int MAX_HIT_VFX_PER_CAST = 10;

   @Override
   public String modeId() {
      return "arthur_once_and_future_king.excalibur_promised_victory";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictoryCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!entity.getMainHandItem().is((Item)TenaddexItems.EXCALIBUR.get())
            && !entity.getMainHandItem().is((Item)TenaddexItems.TRUE_EXCALIBUR.get())
            && !entity.getMainHandItem().is((Item)TenaddexItems.DIVINE_EXCALIBUR.get())) {
            entity.sendSystemMessage(Component.literal("You must hold Excalibur.").withStyle(ChatFormatting.RED));
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            double configuredRange = (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictoryRange.get();
            double range = Math.min(640.0, Math.max(280.0, configuredRange * 4.0));
            Vec3 direction = entity.getLookAngle();
            if (direction.lengthSqr() < 1.0E-8) {
               direction = new Vec3(0.0, 0.0, 1.0);
            }

            direction = direction.normalize();
            Vec3 vfxStart = RoyalSkillSupport.eye(entity);
            Vec3 slashStart = vfxStart.add(direction.scale(4.4));
            Vec3 end = vfxStart.add(direction.scale(range));
            PENDING.put(entity.getUUID(), new ArthurExcaliburPromisedVictoryHandler.PendingSlash(slashStart, end, direction, Util.getMillis(), mode));
            ArthurPromisedVictoryVfxPayload.send(level, entity, null, vfxStart, end, 16.5F, 334, 0);
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.8F, 1.72F);
            level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 3.4F, 1.48F);
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.arthur.promisedVictoryCooldown.get(), mode);
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         ArthurExcaliburPromisedVictoryHandler.PendingSlash slash = PENDING.get(entity.getUUID());
         if (slash != null) {
            if (entity.isAlive() && !entity.isRemoved()) {
               float ageTicks = Math.max(0.0F, (float)(Util.getMillis() - slash.createdMillis) / 50.0F);
               if (ageTicks >= 68.0F) {
                  if (!slash.launchSoundPlayed) {
                     slash.launchSoundPlayed = true;
                     level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 6.4F, 1.1F);
                  }

                  float progress = smoother(Math.min(1.0F, (ageTicks - 68.0F) / 88.0F));
                  if (progress > slash.lastScannedProgress && ((entity.tickCount & 1) == 0 || progress >= 1.0F)) {
                     Vec3 segmentStart = slash.start.lerp(slash.end, slash.lastScannedProgress);
                     Vec3 segmentEnd = slash.start.lerp(slash.end, progress);
                     queueTravelHits(level, entity, slash, segmentStart, segmentEnd);
                     slash.lastScannedProgress = progress;
                  }

                  processTravelQueue(level, entity, instance, slash);
                  if (progress >= 1.0F && slash.lastScannedProgress >= 1.0F && slash.travelQueue.isEmpty() && !slash.travelFinished) {
                     slash.travelFinished = true;
                     if (slash.totalTravelHits > 0) {
                        instance.addMasteryPoint(entity);
                     }

                     level.playSound(null, slash.end.x, slash.end.y, slash.end.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 4.2F, 0.72F);
                  }
               }

               if (ageTicks >= 220.0F) {
                  if (!slash.magicExplosionSpawned) {
                     slash.magicExplosionSpawned = true;
                     spawnTensuraExplosion(level, entity, instance, slash);
                     level.playSound(
                        null, slash.end.x, slash.end.y, slash.end.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 10.0F, 0.56F
                     );
                     level.playSound(
                        null, slash.end.x, slash.end.y, slash.end.z, (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 8.6F, 0.76F
                     );
                     level.playSound(null, slash.end.x, slash.end.y, slash.end.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 6.8F, 0.52F);
                  }

                  if (!slash.finalTargetsPrepared) {
                     prepareFinalTargets(level, entity, slash);
                  }

                  processFinalTargets(entity, instance, slash);
                  if (slash.finalCursor >= slash.finalTargets.size()) {
                     slash.finalFinished = true;
                  }
               }

               if (ageTicks >= 440.0F || ageTicks >= 300.0F && slash.travelFinished && slash.finalFinished) {
                  PENDING.remove(entity.getUUID());
               }
            } else {
               PENDING.remove(entity.getUUID());
            }
         }
      }
   }

   private static void queueTravelHits(
      ServerLevel level, LivingEntity owner, ArthurExcaliburPromisedVictoryHandler.PendingSlash slash, Vec3 segmentStart, Vec3 segmentEnd
   ) {
      if (!(segmentStart.distanceToSqr(segmentEnd) <= 1.0E-8)) {
         AABB corridor = new AABB(segmentStart, segmentEnd).inflate(10.0);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, corridor, living -> RoyalSkillSupport.validEnemy(owner, living))) {
            UUID id = target.getUUID();
            if (!slash.travelSeen.contains(id)) {
               Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
               double radius = 6.5 + target.getBbWidth() * 0.5;
               if (!(RoyalSkillSupport.distanceToSegment(center, segmentStart, segmentEnd) > radius)) {
                  slash.travelSeen.add(id);
                  slash.travelQueue.addLast(target);
               }
            }
         }
      }
   }

   private static void processTravelQueue(
      ServerLevel level, LivingEntity entity, ManasSkillInstance instance, ArthurExcaliburPromisedVictoryHandler.PendingSlash slash
   ) {
      int processed = 0;

      while (!slash.travelQueue.isEmpty() && processed < 6) {
         LivingEntity target = slash.travelQueue.removeFirst();
         if (RoyalSkillSupport.validEnemy(entity, target)) {
            Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            double radius = 6.5 + target.getBbWidth() * 0.5;
            if (!(RoyalSkillSupport.distanceToSegment(center, slash.start, slash.end) > radius)) {
               applyTravelHit(level, entity, instance, slash, target, center);
               processed++;
            }
         }
      }
   }

   private static void applyTravelHit(
      ServerLevel level,
      LivingEntity entity,
      ManasSkillInstance instance,
      ArthurExcaliburPromisedVictoryHandler.PendingSlash slash,
      LivingEntity target,
      Vec3 targetCenter
   ) {
      double soul = RoyalSkillSupport.existence(target).getSoulPoints();
      float damage = (float)Math.min(
         Float.MAX_VALUE,
         (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictoryBaseDamage.get() + soul * (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictorySoulScale.get()
      );
      unavoidablePromisedVictory(target, entity, instance, slash.mode, damage);
      RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 240, 1);
      IEffect effectStorage = TensuraStorages.getEffectFrom(target);
      effectStorage.increaseSeveranceAmount(damage * 0.35F);
      effectStorage.markDirty();
      RoyalSkillSupport.markCombat(entity, target);
      slash.totalTravelHits++;
      if (slash.hitVfxSent < 10 && shouldSendHitVfx(slash.totalTravelHits)) {
         ArthurPromisedVictoryVfxPayload.send(level, entity, target, targetCenter.subtract(slash.direction.scale(2.2)), targetCenter, 7.5F, 22, 1);
         slash.hitVfxSent++;
      }
   }

   private static boolean shouldSendHitVfx(int hitIndex) {
      return hitIndex <= 4 || hitIndex % 4 == 0;
   }

   private static void spawnTensuraExplosion(
      ServerLevel level, LivingEntity entity, ManasSkillInstance instance, ArthurExcaliburPromisedVictoryHandler.PendingSlash slash
   ) {
      float damage = (float)Math.max(2200.0, (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictoryBaseDamage.get() * 1.35);
      MagicExplosion explosion = new MagicExplosion(level, entity);
      explosion.setSecondaryDamage(damage);
      if (instance.getSkill() instanceof TensuraSkill tensuraSkill) {
         explosion.setSkill(entity, instance, tensuraSkill, slash.mode, 0.0F);
      } else {
         explosion.setSkill(instance);
      }

      explosion.setSize(100.0F);
      explosion.setPos(slash.end.add(0.0, 0.2, 0.0));
      level.addFreshEntity(explosion);
   }

   private static void prepareFinalTargets(ServerLevel level, LivingEntity entity, ArthurExcaliburPromisedVictoryHandler.PendingSlash slash) {
      slash.finalTargetsPrepared = true;
      Vec3 center = slash.end;
      AABB area = new AABB(center, center).inflate(100.0, 56.0, 100.0);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, targetx -> RoyalSkillSupport.validEnemy(entity, targetx))) {
         Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         double dx = targetCenter.x - center.x;
         double dz = targetCenter.z - center.z;
         double horizontal = Math.sqrt(dx * dx + dz * dz);
         if (!(horizontal > 100.0)) {
            float falloff = (float)Math.max(0.18, 1.0 - horizontal / 100.0);
            slash.finalTargets.add(new ArthurExcaliburPromisedVictoryHandler.FinalTarget(target, falloff));
         }
      }

      slash.finalTargets.sort(Comparator.comparingDouble(targetx -> targetx.target.distanceToSqr(slash.end)));
   }

   private static void processFinalTargets(LivingEntity entity, ManasSkillInstance instance, ArthurExcaliburPromisedVictoryHandler.PendingSlash slash) {
      int processed = 0;

      while (slash.finalCursor < slash.finalTargets.size() && processed < 8) {
         ArthurExcaliburPromisedVictoryHandler.FinalTarget queued = slash.finalTargets.get(slash.finalCursor++);
         LivingEntity target = queued.target;
         if (RoyalSkillSupport.validEnemy(entity, target)) {
            float baseDamage = (float)Math.max(2200.0, (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictoryBaseDamage.get() * 1.35);
            double soul = RoyalSkillSupport.existence(target).getSoulPoints();
            float damage = (float)Math.min(
               Float.MAX_VALUE,
               (baseDamage + soul * (Double)MoreSkillsConfigs.GRAND.arthur.promisedVictorySoulScale.get() * 0.7) * (0.55F + queued.falloff * 0.9F)
            );
            unavoidablePromisedVictory(target, entity, instance, slash.mode, damage);
            RoyalSkillSupport.applyEffect(target, entity, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 320, 1);
            IEffect effectStorage = TensuraStorages.getEffectFrom(target);
            effectStorage.increaseSeveranceAmount(damage * (0.28F + queued.falloff * 0.32F));
            effectStorage.markDirty();
            RoyalSkillSupport.markCombat(entity, target);
            processed++;
         }
      }
   }

   private static void unavoidablePromisedVictory(LivingEntity target, LivingEntity owner, ManasSkillInstance instance, int mode, float totalDamage) {
      if (!(totalDamage <= 0.0F) && target.isAlive()) {
         DamageSource holy = RoyalSkillSupport.authoritySource(owner, instance, mode, TensuraDamageTypes.HOLY_DAMAGE);
         DamageSource battlewill = RoyalSkillSupport.authoritySource(owner, instance, mode, TensuraDamageTypes.AURA_SLASH);
         ArthurOnceAndFutureKingSkill.runWithoutAuthorityConversion(() -> {
            RoyalSkillSupport.unavoidableDamage(target, holy, totalDamage * 0.55F);
            if (target.isAlive()) {
               RoyalSkillSupport.unavoidableDamage(target, battlewill, totalDamage * 0.45F);
            }
         });
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

   private static final class PendingSlash {
      private final Vec3 start;
      private final Vec3 end;
      private final Vec3 direction;
      private final long createdMillis;
      private final int mode;
      private final ArrayDeque<LivingEntity> travelQueue = new ArrayDeque<>();
      private final Set<UUID> travelSeen = new HashSet<>();
      private final List<ArthurExcaliburPromisedVictoryHandler.FinalTarget> finalTargets = new ArrayList<>();
      private float lastScannedProgress;
      private boolean launchSoundPlayed;
      private boolean travelFinished;
      private boolean magicExplosionSpawned;
      private boolean finalTargetsPrepared;
      private boolean finalFinished;
      private int finalCursor;
      private int totalTravelHits;
      private int hitVfxSent;

      private PendingSlash(Vec3 start, Vec3 end, Vec3 direction, long createdMillis, int mode) {
         this.start = start;
         this.end = end;
         this.direction = direction;
         this.createdMillis = createdMillis;
         this.mode = mode;
      }
   }
}
