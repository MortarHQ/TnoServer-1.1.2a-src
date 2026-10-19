package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.net.msg.AsmodaySpatialAbsorptionPayload;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public final class AsmodaySpatialAbsorption {
   private static final double NORMAL_RANGE = 13.0;
   private static final double MASTERED_RANGE = 22.0;
   private static final double NORMAL_EVENT_RANGE = 18.0;
   private static final double MASTERED_EVENT_RANGE = 30.0;
   private static final double NORMAL_PATH_RANGE = 7.0;
   private static final double MASTERED_PATH_RANGE = 11.0;
   private static final int NORMAL_HOLD_TICKS = 20;
   private static final int MASTERED_HOLD_TICKS = 28;
   private static final int NORMAL_MAX_CAPTURED = 14;
   private static final int MASTERED_MAX_CAPTURED = 32;
   private static final double NORMAL_COST = 55.0;
   private static final double MASTERED_COST = 22.0;
   private static final double WATCH_RANGE = 190.0;
   private static final Map<UUID, AsmodaySpatialAbsorption.AbsorbState> ABSORBING = new ConcurrentHashMap<>();
   private static final Map<UUID, Integer> OWNER_PULSE_COOLDOWN = new ConcurrentHashMap<>();
   private static boolean initialized;

   private AsmodaySpatialAbsorption() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.register(AsmodaySpatialAbsorption.class);
      }
   }

   @SubscribeEvent
   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         Entity threat = event.getEntity();
         if (isAbsorbableThreat(threat)) {
            if (!threat.getPersistentData().getBoolean("AsmodaySpatialAbsorbed")) {
               tryImmediateCapture(level, threat);
            }
         }
      }
   }

   public static void tickLevel(ServerLevel level) {
      if (level.getGameTime() % 20L == 0L) {
         cleanupDeadEntries(level);
      }
   }

   public static void tickOwner(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         boolean var4 = instance.isMastered(owner);
         tickExisting(level, owner, var4);
         aggressiveAreaCapture(instance, level, owner, var4);
         tickOwnerPulse(owner);
      }
   }

   public static void tickExistingOnly(LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)ShadeSkills.ASMODAY.get());
         boolean mastered = optional.<Boolean>map(instance -> instance.isMastered(owner)).orElse(false);
         tickExisting(level, owner, mastered);
         tickOwnerPulse(owner);
      }
   }

   public static boolean hasActiveState(LivingEntity owner) {
      UUID ownerId = owner.getUUID();

      for (AsmodaySpatialAbsorption.AbsorbState state : ABSORBING.values()) {
         if (state.owner.equals(ownerId)) {
            return true;
         }
      }

      return OWNER_PULSE_COOLDOWN.containsKey(ownerId);
   }

   public static void clearOwner(LivingEntity owner) {
      UUID ownerId = owner.getUUID();
      ABSORBING.entrySet().removeIf(entry -> {
         AsmodaySpatialAbsorption.AbsorbState state = entry.getValue();
         if (!state.owner.equals(ownerId)) {
            return false;
         }

         Entity entity = owner.level() instanceof ServerLevel level ? level.getEntity(entry.getKey()) : null;
         if (entity != null && entity.isAlive()) {
            entity.setNoGravity(state.originalNoGravity);
            entity.setDeltaMovement(state.originalMotion.scale(0.2));
            entity.hurtMarked = true;
            entity.getPersistentData().remove("AsmodaySpatialAbsorbed");
         }

         return true;
      });
      OWNER_PULSE_COOLDOWN.remove(ownerId);
   }

   private static void tryImmediateCapture(ServerLevel level, Entity threat) {
      double threatSpeed = threat.getDeltaMovement().length();
      double searchRange = 30.0 + Math.max(threat.getBbWidth(), threat.getBbHeight()) + threatSpeed * 8.0;
      AABB area = threat.getBoundingBox().inflate(searchRange);
      LivingEntity bestOwner = null;
      ManasSkillInstance bestInstance = null;
      boolean bestMastered = false;
      double bestScore = Double.MAX_VALUE;

      for (LivingEntity possibleOwner : level.getEntitiesOfClass(LivingEntity.class, area, e -> e.isAlive() && !e.isSpectator())) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(possibleOwner).getSkill((ManasSkill)ShadeSkills.ASMODAY.get());
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            if (instance.isToggled() && canAbsorb(possibleOwner, threat)) {
               boolean mastered = instance.isMastered(possibleOwner);
               double eventRange = mastered ? 30.0 : 18.0;
               double pathRange = mastered ? 11.0 : 7.0;
               boolean insideEventRange = possibleOwner.distanceToSqr(threat) <= eventRange * eventRange;
               boolean pathWillPassNearOwner = willThreatPassNearOwner(possibleOwner, threat, pathRange);
               if (insideEventRange || pathWillPassNearOwner) {
                  int owned = countOwned(possibleOwner.getUUID());
                  int max = mastered ? 32 : 14;
                  if (owned < max) {
                     double score = possibleOwner.distanceToSqr(threat);
                     if (pathWillPassNearOwner) {
                        score *= 0.45;
                     }

                     if (score < bestScore) {
                        bestOwner = possibleOwner;
                        bestInstance = instance;
                        bestMastered = mastered;
                        bestScore = score;
                     }
                  }
               }
            }
         }
      }

      if (bestOwner != null && bestInstance != null) {
         double lacked = EnergyHelper.isOutOfMagiculeConsuming(bestOwner, bestMastered ? 22.0 : 55.0);
         if (!(lacked > 0.0)) {
            beginAbsorb(level, bestOwner, threat, bestMastered);
            bestInstance.addMasteryPoint(bestOwner);
         }
      }
   }

   private static void aggressiveAreaCapture(ManasSkillInstance instance, ServerLevel level, LivingEntity owner, boolean mastered) {
      int owned = countOwned(owner.getUUID());
      int max = mastered ? 32 : 14;
      if (owned < max) {
         double range = mastered ? 22.0 : 13.0;
         AABB area = owner.getBoundingBox().inflate(range);

         for (Entity threat : level.getEntities(owner, area, e -> canAbsorb(owner, e))) {
            if (owned >= max) {
               return;
            }

            if (!ABSORBING.containsKey(threat.getUUID()) && shouldAggressivelyClear(owner, threat, mastered)) {
               double lacked = EnergyHelper.isOutOfMagiculeConsuming(owner, mastered ? 22.0 : 55.0);
               if (lacked > 0.0) {
                  return;
               }

               beginAbsorb(level, owner, threat, mastered);
               instance.addMasteryPoint(owner);
               owned++;
            }
         }
      }
   }

   private static boolean shouldAggressivelyClear(LivingEntity owner, Entity threat, boolean mastered) {
      double closeRange = mastered ? 8.0 : 5.0;
      if (owner.distanceToSqr(threat) <= closeRange * closeRange) {
         return true;
      }

      if (willThreatPassNearOwner(owner, threat, mastered ? 11.0 : 7.0)) {
         return true;
      }

      Vec3 motion = threat.getDeltaMovement();
      if (!(motion.lengthSqr() < 0.0025)) {
         Vec3 ownerCenter = owner.position().add(0.0, owner.getBbHeight() * 0.52, 0.0);
         Vec3 toOwner = ownerCenter.subtract(threat.position());
         if (toOwner.lengthSqr() < 0.01) {
            return true;
         }

         double dot = motion.normalize().dot(toOwner.normalize());
         return mastered ? dot > -0.35 : dot > -0.08;
      } else {
         return threat instanceof PrimedTnt || threat.getType() == EntityType.TNT_MINECART || threat instanceof FireworkRocketEntity;
      }
   }

   private static void beginAbsorb(ServerLevel level, LivingEntity owner, Entity threat, boolean mastered) {
      UUID id = threat.getUUID();
      if (!ABSORBING.containsKey(id)) {
         Vec3 start = threat.position().add(0.0, threat.getBbHeight() * 0.5, 0.0);
         Vec3 end = absorptionPoint(owner, threat, mastered);
         Vec3 motion = threat.getDeltaMovement();
         int life = mastered ? 28 : 20;
         double size = Mth.clamp(Math.max(threat.getBbWidth(), threat.getBbHeight()) * 1.65, 0.8, mastered ? 4.4 : 3.2);
         ABSORBING.put(id, new AsmodaySpatialAbsorption.AbsorbState(owner.getUUID(), start, end, motion, threat.isNoGravity(), life, 0, size));
         threat.stopRiding();
         threat.ejectPassengers();
         threat.setNoGravity(true);
         threat.setDeltaMovement(Vec3.ZERO);
         threat.hurtMarked = true;
         threat.getPersistentData().putBoolean("AsmodaySpatialAbsorbed", true);
         sendAbsorbPayload(level, owner, threat, start, end, size, life + 10, mastered);
         spatialCubeParticles(level, start, size, mastered);
         pulseOwner(owner, mastered);
         level.playSound(null, threat.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 0.82F, mastered ? 0.22F : 0.34F);
         level.playSound(null, owner.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.9F, mastered ? 0.4F : 0.58F);
      }
   }

   private static void tickExisting(ServerLevel level, LivingEntity owner, boolean mastered) {
      Iterator<Entry<UUID, AsmodaySpatialAbsorption.AbsorbState>> iterator = ABSORBING.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, AsmodaySpatialAbsorption.AbsorbState> entry = iterator.next();
         AsmodaySpatialAbsorption.AbsorbState state = entry.getValue();
         if (state.owner.equals(owner.getUUID())) {
            Entity threat = level.getEntity(entry.getKey());
            if (threat != null && threat.isAlive()) {
               state.age++;
               Vec3 liveEnd = absorptionPoint(owner, threat, mastered);
               state.absorbPoint = state.absorbPoint.lerp(liveEnd, mastered ? 0.32 : 0.24);
               float progress = (float)state.age / Math.max(1, state.life);
               float eased = progress * progress * (3.0F - 2.0F * progress);
               Vec3 pulled = state.start.lerp(state.absorbPoint, eased);
               Vec3 orbit = microOrbit(owner, threat, state.age, state.size, mastered);
               Vec3 pos = pulled.add(orbit);
               threat.teleportTo(pos.x, pos.y - threat.getBbHeight() * 0.5, pos.z);
               threat.setDeltaMovement(Vec3.ZERO);
               threat.setNoGravity(true);
               threat.hurtMarked = true;
               threat.getPersistentData().putBoolean("AsmodaySpatialAbsorbed", true);
               if (state.age % 2 == 0) {
                  spatialCubeParticles(level, pos, state.size, mastered);
               }

               if (state.age % 3 == 0) {
                  lineToOwner(level, pos, owner.position().add(0.0, owner.getBbHeight() * 0.62, 0.0), mastered);
               }

               if (state.age >= state.life || pos.distanceToSqr(state.absorbPoint) < 0.35) {
                  finishAbsorb(level, owner, threat, mastered);
                  iterator.remove();
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void finishAbsorb(ServerLevel level, LivingEntity owner, Entity threat, boolean mastered) {
      Vec3 pos = threat.position().add(0.0, threat.getBbHeight() * 0.5, 0.0);
      threat.getPersistentData().remove("AsmodaySpatialAbsorbed");
      threat.discard();
      level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, mastered ? 92 : 58, 0.55, 0.55, 0.55, mastered ? 0.3 : 0.2);
      level.sendParticles(ParticleTypes.DRAGON_BREATH, pos.x, pos.y, pos.z, mastered ? 48 : 30, 0.32, 0.32, 0.32, 0.055);
      level.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, mastered ? 30 : 18, 0.25, 0.25, 0.25, 0.02);
      level.playSound(null, pos.x, pos.y, pos.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 0.82F, mastered ? 0.1F : 0.18F);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.64F, mastered ? 0.3F : 0.48F);
      pulseOwner(owner, mastered);
   }

   private static boolean willThreatPassNearOwner(LivingEntity owner, Entity threat, double range) {
      Vec3 motion = threat.getDeltaMovement();
      if (motion.lengthSqr() < 0.0025) {
         return false;
      }

      Vec3 start = threat.position();
      Vec3 end = start.add(motion.normalize().scale(range * 3.0 + 10.0));
      AABB ownerBox = owner.getBoundingBox().inflate(range);
      return ownerBox.clip(start, end).isPresent();
   }

   private static boolean canAbsorb(LivingEntity owner, Entity threat) {
      if (threat == owner) {
         return false;
      } else if (!threat.isAlive() || threat.isSpectator()) {
         return false;
      } else if (threat instanceof LivingEntity) {
         return false;
      } else if (threat.getPersistentData().getBoolean("AsmodaySpatialAbsorbed")) {
         return false;
      } else if (threat instanceof Projectile projectile) {
         return projectile.getOwner() != owner;
      } else if (threat instanceof PrimedTnt) {
         return true;
      } else if (threat.getType() == EntityType.TNT_MINECART) {
         return true;
      } else if (threat instanceof AbstractHurtingProjectile) {
         return true;
      } else if (threat instanceof AbstractWindCharge) {
         return true;
      } else {
         return threat instanceof ThrownPotion ? true : threat instanceof FireworkRocketEntity;
      }
   }

   private static boolean isAbsorbableThreat(Entity threat) {
      return threat instanceof LivingEntity
         ? false
         : threat instanceof Projectile
            || threat instanceof PrimedTnt
            || threat.getType() == EntityType.TNT_MINECART
            || threat instanceof AbstractHurtingProjectile
            || threat instanceof AbstractWindCharge
            || threat instanceof ThrownPotion
            || threat instanceof FireworkRocketEntity;
   }

   private static Vec3 absorptionPoint(LivingEntity owner, Entity threat, boolean mastered) {
      Vec3 ownerCenter = owner.position().add(0.0, owner.getBbHeight() * 0.62, 0.0);
      Vec3 fromThreat = threat.position().subtract(ownerCenter);
      if (fromThreat.lengthSqr() < 0.01) {
         fromThreat = owner.getLookAngle().scale(-1.0);
      }

      Vec3 dir = fromThreat.normalize();
      return ownerCenter.add(dir.scale(mastered ? 1.85 : 1.35));
   }

   private static Vec3 microOrbit(LivingEntity owner, Entity threat, int age, double size, boolean mastered) {
      Vec3 to = threat.position().subtract(owner.position());
      Vec3 axis = to.lengthSqr() < 0.01 ? owner.getLookAngle() : to.normalize();
      Vec3 right = axis.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 0.01) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(axis);
      if (up.lengthSqr() < 0.01) {
         up = new Vec3(0.0, 1.0, 0.0);
      }

      up = up.normalize();
      double amount = (mastered ? 0.2 : 0.13) * Math.max(0.7, size);
      double a = age * (mastered ? 0.7 : 0.52);
      return right.scale(Math.cos(a) * amount).add(up.scale(Math.sin(a) * amount));
   }

   private static void spatialCubeParticles(ServerLevel level, Vec3 pos, double size, boolean mastered) {
      double spread = Math.max(0.18, size * 0.36);
      level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, mastered ? 22 : 14, spread, spread, spread, mastered ? 0.095 : 0.06);
      level.sendParticles(ParticleTypes.DRAGON_BREATH, pos.x, pos.y, pos.z, mastered ? 12 : 7, spread * 0.52, spread * 0.52, spread * 0.52, 0.018);
   }

   private static void lineToOwner(ServerLevel level, Vec3 from, Vec3 to, boolean mastered) {
      int points = mastered ? 13 : 9;

      for (int i = 0; i <= points; i++) {
         double p = (double)i / points;
         Vec3 pos = from.lerp(to, p);
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, 1, 0.035, 0.035, 0.035, 0.02 + p * 0.05);
      }
   }

   private static void pulseOwner(LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      int cooldown = OWNER_PULSE_COOLDOWN.getOrDefault(id, 0);
      if (cooldown <= 0) {
         OWNER_PULSE_COOLDOWN.put(id, mastered ? 4 : 7);
         if (owner.level() instanceof ServerLevel level) {
            Vec3 p = owner.position().add(0.0, owner.getBbHeight() * 0.62, 0.0);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, p.x, p.y, p.z, mastered ? 34 : 20, 0.95, 0.6, 0.95, mastered ? 0.16 : 0.1);
            level.sendParticles(ParticleTypes.DRAGON_BREATH, p.x, p.y, p.z, mastered ? 18 : 10, 0.46, 0.34, 0.46, 0.03);
         }
      }
   }

   private static void tickOwnerPulse(LivingEntity owner) {
      UUID id = owner.getUUID();
      int ticks = OWNER_PULSE_COOLDOWN.getOrDefault(id, 0);
      if (ticks <= 0) {
         OWNER_PULSE_COOLDOWN.remove(id);
      } else {
         OWNER_PULSE_COOLDOWN.put(id, ticks - 1);
      }
   }

   private static int countOwned(UUID owner) {
      int count = 0;

      for (AsmodaySpatialAbsorption.AbsorbState state : ABSORBING.values()) {
         if (state.owner.equals(owner)) {
            count++;
         }
      }

      return count;
   }

   private static void cleanupDeadEntries(ServerLevel level) {
      ABSORBING.entrySet().removeIf(entry -> {
         Entity entity = level.getEntity(entry.getKey());
         return entity == null || !entity.isAlive();
      });
   }

   private static void sendAbsorbPayload(
      ServerLevel level, LivingEntity owner, Entity threat, Vec3 start, Vec3 end, double size, int duration, boolean mastered
   ) {
      AsmodaySpatialAbsorptionPayload payload = new AsmodaySpatialAbsorptionPayload(
         owner.getId() * 65536 + threat.getId() * 31 + owner.tickCount,
         owner.getId(),
         threat.getId(),
         start.x,
         start.y,
         start.z,
         end.x,
         end.y,
         end.z,
         size,
         duration,
         owner.getRandom().nextInt(),
         mastered
      );
      AABB watchers = owner.getBoundingBox().inflate(190.0);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, watchers, p -> p != null && !p.isSpectator())) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static final class AbsorbState {
      private final UUID owner;
      private final Vec3 start;
      private Vec3 absorbPoint;
      private final Vec3 originalMotion;
      private final boolean originalNoGravity;
      private final int life;
      private int age;
      private final double size;

      private AbsorbState(UUID owner, Vec3 start, Vec3 absorbPoint, Vec3 originalMotion, boolean originalNoGravity, int life, int age, double size) {
         this.owner = owner;
         this.start = start;
         this.absorbPoint = absorbPoint;
         this.originalMotion = originalMotion;
         this.originalNoGravity = originalNoGravity;
         this.life = life;
         this.age = age;
         this.size = size;
      }
   }
}
