package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.storage.player.WarpPoint.TransmissionType;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class AsmodayPassives {
   private static final Map<UUID, Integer> SPACE_LOCK_COOLDOWNS = new ConcurrentHashMap<>();
   private static final Map<UUID, Integer> ANCHOR_PULSE_COOLDOWNS = new ConcurrentHashMap<>();

   private AsmodayPassives() {
   }

   public static void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AsmodaySpatialAbsorption.clearOwner(entity);
   }

   public static void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AsmodaySpatialAbsorption.clearOwner(entity);
      SPACE_LOCK_COOLDOWNS.remove(entity.getUUID());
      ANCHOR_PULSE_COOLDOWNS.remove(entity.getUUID());
   }

   public static void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         tickCooldowns(entity);
         if (!instance.isToggled()) {
            AsmodaySpatialAbsorption.tickExistingOnly(entity);
         } else {
            AsmodaySpatialAbsorption.tickOwner(instance, entity);
            anchorOwnerMotion(instance, entity);
         }
      }
   }

   public static boolean hasActiveState(LivingEntity entity) {
      return AsmodaySpatialAbsorption.hasActiveState(entity)
         || SPACE_LOCK_COOLDOWNS.containsKey(entity.getUUID())
         || ANCHOR_PULSE_COOLDOWNS.containsKey(entity.getUUID());
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      if (!target.level().isClientSide()) {
         Optional<ManasSkillInstance> optional = getActiveAsmoday(target);
         if (!optional.isEmpty()) {
            DamageSource source = event.getSource();
            if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.isCreativePlayer()) {
               Entity attacker = source.getEntity();
               if (attacker instanceof LivingEntity && attacker != target) {
                  ManasSkillInstance instance = optional.get();
                  if (instance.isToggled()) {
                     boolean mastered = instance.isMastered(target);
                     double distance = Math.sqrt(target.distanceToSqr(attacker));
                     double start = mastered ? (Double)cfg().passiveDistanceTaxStartMastered.get() : (Double)cfg().passiveDistanceTaxStart.get();
                     if (!(distance <= start)) {
                        double full = mastered ? (Double)cfg().passiveDistanceTaxFullMastered.get() : (Double)cfg().passiveDistanceTaxFull.get();
                        double maxReduction = mastered
                           ? (Double)cfg().passiveMaxDistanceReductionMastered.get()
                           : (Double)cfg().passiveMaxDistanceReduction.get();
                        double progress = Mth.clamp((distance - start) / Math.max(1.0, full - start), 0.0, 1.0);
                        double reduction = progress * maxReduction;
                        float original = event.getAmount();
                        float reduced = Math.max(0.0F, (float)(original * (1.0 - reduction)));
                        event.setAmount(reduced);
                        instance.addMasteryPoint(target);
                        if (target.level() instanceof ServerLevel level && target.tickCount % 4 == 0) {
                           Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                           level.sendParticles(
                              ParticleTypes.REVERSE_PORTAL, center.x, center.y, center.z, mastered ? 18 : 10, 0.65, 0.35, 0.65, mastered ? 0.1 : 0.06
                           );
                           level.playSound(null, target.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.26F, mastered ? 0.34F : 0.48F);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static EventResult onForceMovement(Entity entity, Entity source, ManasSkillInstance forceInstance, Changeable<Vec3> movement) {
      if (entity instanceof LivingEntity living) {
         if (living.level().isClientSide()) {
            return EventResult.pass();
         }

         Optional<ManasSkillInstance> optional = getActiveAsmoday(living);
         if (optional.isEmpty()) {
            return EventResult.pass();
         }

         ManasSkillInstance instance = optional.get();
         if (!instance.isToggled()) {
            return EventResult.pass();
         }

         boolean mastered = instance.isMastered(living);
         Vec3 original = (Vec3)movement.get();
         if (original == null) {
            return EventResult.pass();
         }

         double strength = original.length();
         if (strength <= (Double)cfg().passiveForcedMovementMinimumStrength.get()) {
            return EventResult.pass();
         }

         Vec3 eaten = original.scale(mastered ? (Double)cfg().passiveForcedMovementScaleMastered.get() : (Double)cfg().passiveForcedMovementScale.get());
         movement.set(eaten);
         pulseAnchor(living, mastered);
         instance.addMasteryPoint(living);
         return EventResult.pass();
      } else {
         return EventResult.pass();
      }
   }

   private static EventResult onSpatialMovement(Entity entity, Entity source, Changeable<Vec3> destination, TransmissionType transmissionType) {
      if (entity.level() instanceof ServerLevel level) {
         if (entity instanceof LivingEntity living && getActiveAsmoday(living).isPresent()) {
            return EventResult.pass();
         } else {
            Vec3 wanted = (Vec3)destination.get();
            if (wanted == null) {
               return EventResult.pass();
            }

            LivingEntity owner = findSpaceLockOwner(level, entity, wanted);
            if (owner == null) {
               return EventResult.pass();
            }

            Optional<ManasSkillInstance> optional = getActiveAsmoday(owner);
            if (optional.isEmpty()) {
               return EventResult.pass();
            }

            ManasSkillInstance instance = optional.get();
            if (!instance.isToggled()) {
               return EventResult.pass();
            }

            boolean mastered = instance.isMastered(owner);
            UUID ownerId = owner.getUUID();
            int cooldown = SPACE_LOCK_COOLDOWNS.getOrDefault(ownerId, 0);
            if (cooldown > 0) {
               return EventResult.pass();
            }

            SPACE_LOCK_COOLDOWNS.put(
               ownerId, mastered ? (Integer)cfg().passiveSpaceLockCooldownTicksMastered.get() : (Integer)cfg().passiveSpaceLockCooldownTicks.get()
            );
            Vec3 ownerCenter = owner.position().add(0.0, owner.getBbHeight() * 0.5, 0.0);
            Vec3 direction = wanted.subtract(ownerCenter);
            if (direction.lengthSqr() < 0.05) {
               direction = entity.position().subtract(ownerCenter);
            }

            if (direction.lengthSqr() < 0.05) {
               direction = owner.getLookAngle().scale(-1.0);
            }

            if (direction.lengthSqr() < 0.05) {
               return EventResult.pass();
            }

            direction = direction.normalize();
            double push = mastered ? (Double)cfg().passiveSpaceLockPushMastered.get() : (Double)cfg().passiveSpaceLockPush.get();
            Vec3 redirected = ownerCenter.add(direction.scale(push));
            destination.set(redirected);
            instance.addMasteryPoint(owner);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, wanted.x, wanted.y + 0.4, wanted.z, mastered ? 48 : 28, 0.55, 0.55, 0.55, mastered ? 0.18 : 0.11);
            level.sendParticles(
               ParticleTypes.PORTAL, redirected.x, redirected.y + 0.4, redirected.z, mastered ? 34 : 18, 0.45, 0.45, 0.45, mastered ? 0.28 : 0.18
            );
            level.playSound(
               null, owner.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 0.72F, mastered ? 0.18F : 0.3F
            );
            level.playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.62F, mastered ? 0.32F : 0.46F);
            return EventResult.pass();
         }
      } else {
         return EventResult.pass();
      }
   }

   private static void anchorOwnerMotion(ManasSkillInstance instance, LivingEntity owner) {
      if (instance.isToggled()) {
         Vec3 motion = owner.getDeltaMovement();
         boolean mastered = instance.isMastered(owner);
         double horizontalCap = mastered ? (Double)cfg().passiveAnchorHorizontalCapMastered.get() : (Double)cfg().passiveAnchorHorizontalCap.get();
         double verticalCap = mastered ? (Double)cfg().passiveAnchorVerticalCapMastered.get() : (Double)cfg().passiveAnchorVerticalCap.get();
         double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
         double x = motion.x;
         double y = motion.y;
         double z = motion.z;
         boolean changed = false;
         if (horizontal > horizontalCap) {
            double scale = horizontalCap / horizontal;
            x *= scale;
            z *= scale;
            changed = true;
         }

         if (Math.abs(y) > verticalCap && !owner.isFallFlying()) {
            y = Math.copySign(verticalCap, y);
            changed = true;
         }

         if (changed) {
            owner.setDeltaMovement(x, y, z);
            owner.hurtMarked = true;
            pulseAnchor(owner, mastered);
            instance.addMasteryPoint(owner);
         }
      }
   }

   private static LivingEntity findSpaceLockOwner(ServerLevel level, Entity mover, Vec3 destination) {
      AABB search = new AABB(destination, destination).inflate((Double)cfg().passiveSpaceLockRangeMastered.get() + 4.0);
      LivingEntity best = null;
      double bestDistance = Double.MAX_VALUE;

      for (LivingEntity possibleOwner : level.getEntitiesOfClass(LivingEntity.class, search, entity -> entity.isAlive() && !entity.isSpectator())) {
         Optional<ManasSkillInstance> optional = getActiveAsmoday(possibleOwner);
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            if (instance.isToggled()) {
               boolean mastered = instance.isMastered(possibleOwner);
               double range = mastered ? (Double)cfg().passiveSpaceLockRangeMastered.get() : (Double)cfg().passiveSpaceLockRange.get();
               double distanceToDestination = possibleOwner.position().distanceToSqr(destination);
               double distanceToMover = possibleOwner.distanceToSqr(mover);
               if (!(distanceToDestination > range * range) || !(distanceToMover > range * range)) {
                  double score = Math.min(distanceToDestination, distanceToMover);
                  if (score < bestDistance) {
                     bestDistance = score;
                     best = possibleOwner;
                  }
               }
            }
         }
      }

      return best;
   }

   private static Optional<ManasSkillInstance> getActiveAsmoday(LivingEntity entity) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)ShadeSkills.ASMODAY.get());
      if (optional.isEmpty()) {
         return Optional.empty();
      }

      ManasSkillInstance instance = optional.get();
      return !instance.isToggled() ? Optional.empty() : optional;
   }

   private static void pulseAnchor(LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      int cooldown = ANCHOR_PULSE_COOLDOWNS.getOrDefault(id, 0);
      if (cooldown <= 0) {
         ANCHOR_PULSE_COOLDOWNS.put(
            id, mastered ? (Integer)cfg().passiveAnchorPulseCooldownTicksMastered.get() : (Integer)cfg().passiveAnchorPulseCooldownTicks.get()
         );
         if (owner.level() instanceof ServerLevel level) {
            Vec3 var6 = owner.position().add(0.0, owner.getBbHeight() * 0.55, 0.0);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, var6.x, var6.y, var6.z, mastered ? 28 : 16, 0.7, 0.45, 0.7, mastered ? 0.13 : 0.08);
            level.sendParticles(ParticleTypes.DRAGON_BREATH, var6.x, var6.y, var6.z, mastered ? 10 : 6, 0.38, 0.26, 0.38, 0.018);
            level.playSound(null, owner.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.42F, mastered ? 0.38F : 0.52F);
         }
      }
   }

   private static void tickCooldowns(LivingEntity owner) {
      tickCooldown(SPACE_LOCK_COOLDOWNS, owner.getUUID());
      tickCooldown(ANCHOR_PULSE_COOLDOWNS, owner.getUUID());
   }

   private static void tickCooldown(Map<UUID, Integer> map, UUID id) {
      int ticks = map.getOrDefault(id, 0);
      if (ticks <= 0) {
         map.remove(id);
      } else {
         map.put(id, ticks - 1);
      }
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }

   static {
      TensuraEntityEvents.FORCE_MOVEMENT_EVENT.register(AsmodayPassives::onForceMovement);
      TensuraEntityEvents.INSTANT_TRANSMISSION_EVENT.register(AsmodayPassives::onSpatialMovement);
   }
}
