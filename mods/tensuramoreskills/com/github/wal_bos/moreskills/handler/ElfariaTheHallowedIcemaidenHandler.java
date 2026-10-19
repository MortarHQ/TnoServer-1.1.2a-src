package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.ElfariaTheHallowedIcemaidenSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.ElfariaIcemaidenVfxPayload;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.TickEvent.Player;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingDamageEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ElfariaTheHallowedIcemaidenHandler {
   private static final Set<UUID> PACKAGE_GRANTED = new HashSet<>();
   private static final Set<UUID> REVIVING = new HashSet<>();
   private static final Map<UUID, Long> MYRDAS_MODE_UNTIL = new HashMap<>();
   private static final Map<UUID, ElfariaTheHallowedIcemaidenHandler.BarrierState> MYRDAS_BARRIERS = new HashMap<>();
   private static final Map<UUID, Long> REVIVE_DAY = new HashMap<>();
   private static final Map<UUID, Integer> REVIVES_USED = new HashMap<>();
   private static final Map<UUID, ElfariaTheHallowedIcemaidenHandler.Cathedral> CATHEDRALS = new HashMap<>();
   private static final Map<UUID, ElfariaTheHallowedIcemaidenHandler.PossessionState> POSSESSIONS = new HashMap<>();
   private static final Map<UUID, UUID> ANCHOR_TO_OWNER = new HashMap<>();
   private static final List<ElfariaTheHallowedIcemaidenHandler.QueuedSpear> SPEARS = new ArrayList<>();
   private static final List<ElfariaTheHallowedIcemaidenHandler.QueuedGlaciaNova> GLACIA_NOVAS = new ArrayList<>();
   private static final Map<UUID, ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget> CATHEDRAL_FREEZE_LOCKS = new HashMap<>();
   private static final Map<UUID, Long> OWNER_SYSTEM_TICK = new HashMap<>();
   private static final Map<UUID, Long> REVIVE_STEP_UNTIL = new HashMap<>();

   private ElfariaTheHallowedIcemaidenHandler() {
   }

   private static MoreSkillsConfigs.ElfariaTheHallowedIcemaidenConfig cfg() {
      return MoreSkillsConfigs.GRAND.elfariaTheHallowedIcemaiden;
   }

   public static void init() {
      EntityEvents.LIVING_DAMAGE
         .register(
            (LivingDamageEvent)(entity, source, amount) -> {
               if (entity.level().isClientSide()) {
                  return EventResult.pass();
               }

               if (entity instanceof ServerPlayer possessed && isPossessing(possessed)) {
                  float incoming = (Float)amount.get();
                  if (incoming >= possessed.getHealth()) {
                     returnFromPossessedClone(possessed, true);
                     amount.set(0.0F);
                     return EventResult.interruptFalse();
                  }
               }

               if (entity instanceof LivingEntity defender) {
                  ElfariaTheHallowedIcemaidenHandler.BarrierState shield = MYRDAS_BARRIERS.get(defender.getUUID());
                  if (shield != null && defender.level().getGameTime() <= shield.endTime) {
                     float incoming = (Float)amount.get();
                     float reduced = incoming * (shield.mastered ? 0.28F : 0.42F);
                     amount.set(Math.max(0.0F, reduced));
                     if (source.getEntity() instanceof LivingEntity living && living.distanceToSqr(defender) <= (shield.radius + 2.0) * (shield.radius + 2.0)) {
                        int ticks = shield.mastered ? (Integer)cfg().myrdasFreezeAuraEffectTicks.get() * 2 : (Integer)cfg().myrdasFreezeAuraEffectTicks.get();
                        int amp = shield.mastered ? (Integer)cfg().myrdasFreezeAuraAmplifier.get() + 1 : (Integer)cfg().myrdasFreezeAuraAmplifier.get();
                        ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(defender, living, shield.mastered);
                        living.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), ticks, amp, true, false, true)
                        );
                        living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), ticks, amp, true, false, true));
                     }

                     if (defender.level() instanceof ServerLevel level && defender.tickCount % 4 == 0) {
                        ElfariaIcemaidenVfxPayload.sendMyrdasBarrier(level, defender.position(), (float)shield.radius, 24, true);
                     }
                  }
               }

               if (entity instanceof LivingEntity defender && (Float)amount.get() >= defender.getHealth()) {
                  ManasSkillInstance reviveInstance = SkillUtils.getSkillOrNull(defender, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
                  if (tryMyrdasRevive(defender, reviveInstance, source)) {
                     amount.set(0.0F);
                     return EventResult.interruptFalse();
                  }
               }

               if (source.getEntity() instanceof LivingEntity living) {
                  ManasSkillInstance instance = SkillUtils.getSkillOrNull(living, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
                  if (instance == null || !instance.isToggled()) {
                     return EventResult.pass();
                  }

                  if (!isMagicDamage(source)) {
                     return EventResult.pass();
                  }

                  double multiplier = ElfariaTheHallowedIcemaidenSkill.getAllMagicMultiplier(living, instance);
                  if (AlbisVinaHandler.isWaterOrIce(source)) {
                     multiplier *= ElfariaTheHallowedIcemaidenSkill.getWaterIceMultiplier(living, instance);
                  }

                  amount.set((float)(((Float)amount.get()).floatValue() * multiplier));
                  return EventResult.pass();
               } else {
                  return EventResult.pass();
               }
            }
         );
      EntityEvents.DEATH_EVENT_FIRST.register((LivingDeath)(entity, source) -> {
         if (entity.level().isClientSide()) {
            return EventResult.pass();
         } else if (entity instanceof ServerPlayer player && isPossessing(player)) {
            returnFromPossessedClone(player, true);
            return EventResult.interruptFalse();
         } else {
            UUID ownerId = ANCHOR_TO_OWNER.get(entity.getUUID());
            if (ownerId != null) {
               killOwnerFromAnchor(entity.level(), ownerId);
               POSSESSIONS.remove(ownerId);
               ANCHOR_TO_OWNER.remove(entity.getUUID());
               AlbisVinaHandler.removeAllClones(ownerId);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         }
      });
      EntityEvents.DEATH_EVENT_FIRST.register((LivingDeath)(entity, source) -> {
         if (entity.level().isClientSide()) {
            return EventResult.pass();
         }

         ManasSkillInstance instance = SkillUtils.getSkillOrNull(entity, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
         return entity instanceof LivingEntity living && tryMyrdasRevive(living, instance, source) ? EventResult.interruptFalse() : EventResult.pass();
      });
      TickEvent.PLAYER_POST.register((Player)player -> {
         if (player instanceof ServerPlayer serverPlayer) {
            tickPlayer(serverPlayer);
         }
      });
   }

   private static void tickPlayer(ServerPlayer player) {
      ManasSkillInstance instance = SkillUtils.getSkillOrNull(player, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
      if (instance != null) {
         grantPackage(player);
      }

      tickPossession(player);
      tickMyrdasBarrier(player, instance);
      tickReviveSteps(player);
      tickSpears(player);
      tickOwnerSystems(player, instance);
      cleanup(player.level());
   }

   public static void activateMyrdasBarrier(LivingEntity owner, ManasSkillInstance instance) {
      if (owner.level() instanceof ServerLevel level) {
         boolean mastered = instance.isMastered(owner);
         double radius = mastered ? (Double)cfg().myrdasFreezeAuraRadius.get() * 1.25 : (Double)cfg().myrdasFreezeAuraRadius.get();
         int duration = mastered ? (Integer)cfg().myrdasModeDurationTicks.get() * 2 : (Integer)cfg().myrdasModeDurationTicks.get();
         ElfariaTheHallowedIcemaidenHandler.BarrierState state = new ElfariaTheHallowedIcemaidenHandler.BarrierState(
            owner.getUUID(), owner.level().dimension(), owner.level().getGameTime() + duration, radius, mastered
         );
         MYRDAS_BARRIERS.put(owner.getUUID(), state);
         MYRDAS_MODE_UNTIL.put(owner.getUUID(), state.endTime);
         tickFreezeAura(owner, instance, radius, mastered);
         ElfariaIcemaidenVfxPayload.sendMyrdasBarrier(level, owner.position(), (float)radius, duration, true);
         level.playSound(null, owner.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.PLAYERS, 1.15F, 0.65F);
      }
   }

   private static void activateMyrdasReviveBarrier(LivingEntity owner, ManasSkillInstance instance) {
      if (owner.level() instanceof ServerLevel level) {
         boolean mastered = instance.isMastered(owner);
         double radius = mastered ? (Double)cfg().myrdasFreezeAuraRadius.get() * 1.25 : (Double)cfg().myrdasFreezeAuraRadius.get();
         int duration = mastered ? (Integer)cfg().myrdasModeDurationTicks.get() * 2 : (Integer)cfg().myrdasModeDurationTicks.get();
         long endTime = owner.level().getGameTime() + duration;
         ElfariaTheHallowedIcemaidenHandler.BarrierState state = new ElfariaTheHallowedIcemaidenHandler.BarrierState(
            owner.getUUID(), owner.level().dimension(), endTime, radius, mastered
         );
         MYRDAS_BARRIERS.put(owner.getUUID(), state);
         MYRDAS_MODE_UNTIL.put(owner.getUUID(), endTime);
         tickFreezeAura(owner, instance, radius, mastered);
         ElfariaIcemaidenVfxPayload.sendMyrdasBarrier(level, owner.position(), (float)radius, 34, true);
         ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, owner.position(), (float)Math.max(2.0, radius * 0.85), true);
         level.playSound(null, owner.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.PLAYERS, 1.15F, 0.65F);
      }
   }

   private static void startReviveSteps(LivingEntity entity) {
      int ticks = Math.min(Math.max(80, (Integer)cfg().reviveInvulnerabilityTicks.get()), Math.max(80, (Integer)cfg().myrdasModeDurationTicks.get()));
      REVIVE_STEP_UNTIL.put(entity.getUUID(), entity.level().getGameTime() + ticks);
   }

   private static void tickReviveSteps(ServerPlayer player) {
      Long until = REVIVE_STEP_UNTIL.get(player.getUUID());
      if (until != null) {
         if (player.level().getGameTime() <= until && player.isAlive()) {
            if (player.level() instanceof ServerLevel level) {
               Vec3 motion = player.getDeltaMovement();
               boolean moving = motion.horizontalDistanceSqr() > 0.001;
               if (moving) {
                  if (player.tickCount % 3 == 0) {
                     ElfariaIcemaidenVfxPayload.sendIceStep(level, player.position().add(0.0, 0.035, 0.0), player.getLookAngle(), 0.82F, true);
                  }
               } else if (player.tickCount % 12 == 0) {
                  ElfariaIcemaidenVfxPayload.sendIceStep(level, player.position().add(0.0, 0.035, 0.0), player.getLookAngle(), 0.62F, true);
               }
            }
         } else {
            REVIVE_STEP_UNTIL.remove(player.getUUID());
         }
      }
   }

   public static void holdMyrdasBarrier(LivingEntity owner, ManasSkillInstance instance, int heldTicks) {
      if (owner.level() instanceof ServerLevel level) {
         boolean mastered = instance.isMastered(owner);
         double radius = mastered ? (Double)cfg().myrdasFreezeAuraRadius.get() * 1.25 : (Double)cfg().myrdasFreezeAuraRadius.get();
         long endTime = owner.level().getGameTime() + 8L;
         ElfariaTheHallowedIcemaidenHandler.BarrierState old = MYRDAS_BARRIERS.get(owner.getUUID());
         boolean first = old == null || old.endTime < owner.level().getGameTime();
         ElfariaTheHallowedIcemaidenHandler.BarrierState state = new ElfariaTheHallowedIcemaidenHandler.BarrierState(
            owner.getUUID(), owner.level().dimension(), endTime, radius, mastered
         );
         MYRDAS_BARRIERS.put(owner.getUUID(), state);
         MYRDAS_MODE_UNTIL.put(owner.getUUID(), endTime);
         if (first || heldTicks % 6 == 0) {
            ElfariaIcemaidenVfxPayload.sendMyrdasBarrier(level, owner.position(), (float)radius, 12, true);
         }

         if (heldTicks % Math.max(1, (Integer)cfg().myrdasRefreshTicks.get() / 2) == 0) {
            tickFreezeAura(owner, instance, radius, mastered);
         }

         blockProjectiles(owner, state);
         blockBodies(owner, state);
         if (first) {
            level.playSound(null, owner.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.PLAYERS, 1.15F, 0.65F);
         }
      }
   }

   public static void stopMyrdasBarrier(LivingEntity owner) {
      ElfariaTheHallowedIcemaidenHandler.BarrierState state = MYRDAS_BARRIERS.remove(owner.getUUID());
      MYRDAS_MODE_UNTIL.remove(owner.getUUID());
      if (state != null && owner.level() instanceof ServerLevel level) {
         ElfariaIcemaidenVfxPayload.sendMyrdasBreak(level, owner.position(), (float)state.radius, true);
         level.playSound(null, owner.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.7F, 0.65F);
      }
   }

   private static void tickMyrdasBarrier(ServerPlayer player, ManasSkillInstance instance) {
      ElfariaTheHallowedIcemaidenHandler.BarrierState shield = MYRDAS_BARRIERS.get(player.getUUID());
      if (shield != null) {
         if (player.level().dimension() == shield.dimension && player.level().getGameTime() <= shield.endTime && instance != null && instance.isToggled()) {
            if (player.tickCount % 5 == 0 && player.level() instanceof ServerLevel level) {
               ElfariaIcemaidenVfxPayload.sendMyrdasBarrier(level, player.position(), (float)shield.radius, 16, true);
            }

            if (player.tickCount % 4 == 0 && player.level() instanceof ServerLevel level) {
               Vec3 motion = player.getDeltaMovement();
               if (motion.horizontalDistanceSqr() > 0.002 || player.tickCount % 16 == 0) {
                  ElfariaIcemaidenVfxPayload.sendIceStep(level, player.position().add(0.0, 0.03, 0.0), player.getLookAngle(), 0.72F, true);
               }
            }

            blockBodies(player, shield);
            if (player.tickCount % Math.max(1, (Integer)cfg().myrdasRefreshTicks.get()) == 0) {
               ElfariaTheHallowedIcemaidenSkill.refreshPassiveAttributes(player, instance);
               tickFreezeAura(player, instance, shield.radius, shield.mastered);
               blockProjectiles(player, shield);
            } else if (player.tickCount % 3 == 0) {
               blockProjectiles(player, shield);
            }
         } else {
            MYRDAS_BARRIERS.remove(player.getUUID());
            MYRDAS_MODE_UNTIL.remove(player.getUUID());
            if (player.level() instanceof ServerLevel level) {
               ElfariaIcemaidenVfxPayload.sendMyrdasBreak(level, player.position(), (float)shield.radius, true);
            }
         }
      }
   }

   private static void blockProjectiles(LivingEntity owner, ElfariaTheHallowedIcemaidenHandler.BarrierState shield) {
      double radius = shield.radius + 1.5;
      AABB box = owner.getBoundingBox().inflate(radius);

      for (Projectile projectile : owner.level().getEntitiesOfClass(Projectile.class, box, Entity::isAlive)) {
         Entity projectileOwner = projectile.getOwner();
         if (projectileOwner != owner && (projectileOwner == null || !projectileOwner.isAlliedTo(owner)) && projectile.distanceToSqr(owner) <= radius * radius) {
            projectile.discard();
            if (owner.level() instanceof ServerLevel level) {
               ElfariaIcemaidenVfxPayload.sendMyrdasBreak(level, projectile.position(), 1.5F, false);
            }
         }
      }
   }

   private static void blockBodies(LivingEntity owner, ElfariaTheHallowedIcemaidenHandler.BarrierState shield) {
      double radius = Math.max(1.5, shield.radius);
      double radiusSqr = radius * radius;
      AABB box = owner.getBoundingBox().inflate(radius + 1.5);
      int freezeTicks = shield.mastered ? (Integer)cfg().myrdasFreezeAuraEffectTicks.get() * 2 : (Integer)cfg().myrdasFreezeAuraEffectTicks.get();
      int freezeAmp = shield.mastered ? (Integer)cfg().myrdasFreezeAuraAmplifier.get() + 1 : (Integer)cfg().myrdasFreezeAuraAmplifier.get();

      for (LivingEntity target : owner.level()
         .getEntitiesOfClass(LivingEntity.class, box, entity -> entity.isAlive() && entity != owner && !entity.isAlliedTo(owner))) {
         Vec3 fromOwner = target.position().subtract(owner.position());
         double lenSqr = fromOwner.horizontalDistanceSqr();
         if (lenSqr < 1.0E-4) {
            fromOwner = owner.getLookAngle().scale(-1.0);
            lenSqr = fromOwner.horizontalDistanceSqr();
         }

         if (lenSqr <= radiusSqr) {
            double len = Math.sqrt(lenSqr);
            Vec3 dir = new Vec3(fromOwner.x / len, 0.0, fromOwner.z / len);
            double targetX = owner.getX() + dir.x * (radius + 0.35);
            double targetZ = owner.getZ() + dir.z * (radius + 0.35);
            target.moveTo(targetX, target.getY(), targetZ, target.getYRot(), target.getXRot());
            target.setDeltaMovement(target.getDeltaMovement().add(dir.scale(0.28)).add(0.0, 0.04, 0.0));
            target.hurtMarked = true;
            ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(owner, target, shield.mastered);
            target.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), freezeTicks, freezeAmp, true, false, true)
            );
            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), freezeTicks, freezeAmp, true, false, true));
         }
      }
   }

   public static void tickIcemaidenAura(LivingEntity entity, ManasSkillInstance instance) {
      if (entity.level() instanceof ServerLevel) {
         ElfariaTheHallowedIcemaidenHandler.BarrierState shield = MYRDAS_BARRIERS.get(entity.getUUID());
         if (shield == null || entity.level().getGameTime() > shield.endTime) {
            if (entity.tickCount % Math.max(1, (Integer)cfg().passiveAuraRefreshTicks.get()) == 0) {
               if (isInMyrdasMode(entity)) {
                  tickFreezeAura(entity, instance, (Double)cfg().myrdasFreezeAuraRadius.get(), instance.isMastered(entity));
               }
            }
         }
      }
   }

   private static void tickFreezeAura(LivingEntity owner, ManasSkillInstance instance, double radius, boolean mastered) {
      int duration = mastered ? (Integer)cfg().myrdasFreezeAuraEffectTicks.get() * 2 : (Integer)cfg().myrdasFreezeAuraEffectTicks.get();
      int amplifier = mastered ? (Integer)cfg().myrdasFreezeAuraAmplifier.get() + 1 : (Integer)cfg().myrdasFreezeAuraAmplifier.get();
      float damage = ((Double)cfg().myrdasFreezeAuraDamage.get()).floatValue();
      DamageSource source = ((ElfariaTheHallowedIcemaidenSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get()).damageSource(owner, instance, 1);
      AABB box = owner.getBoundingBox().inflate(radius);

      for (LivingEntity target : owner.level().getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != owner && !e.isAlliedTo(owner))) {
         ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(owner, target, mastered);
         target.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), duration, amplifier, true, false, true)
         );
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FROST), duration, amplifier, true, false, true));
         if (damage > 0.0F) {
            forceHurt(target, source, damage);
         }

         Vec3 push = target.position().subtract(owner.position());
         if (push.lengthSqr() > 0.001) {
            target.setDeltaMovement(target.getDeltaMovement().add(push.normalize().scale((Double)cfg().myrdasFreezeAuraKnockback.get())));
            target.hurtMarked = true;
         }
      }

      if (owner.level() instanceof ServerLevel level) {
         ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, owner.position(), (float)radius, true);
      }
   }

   public static boolean isInMyrdasMode(LivingEntity entity) {
      Long until = MYRDAS_MODE_UNTIL.get(entity.getUUID());
      return until != null && entity.level().getGameTime() <= until;
   }

   public static boolean tryMyrdasRevive(LivingEntity entity, ManasSkillInstance instance, DamageSource source) {
      if (entity == null || entity.level().isClientSide()) {
         return false;
      }

      if (instance != null && instance.isToggled()) {
         if (!REVIVING.add(entity.getUUID())) {
            return false;
         }

         try {
            if (source == null || !source.is(DamageTypes.FELL_OUT_OF_WORLD) && !source.is(DamageTypes.GENERIC_KILL)) {
               if (source != null && source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !(Boolean)cfg().reviveWorksAgainstBypassInvulnerability.get()) {
                  return false;
               }

               if (!canRevive(entity)) {
                  if (entity instanceof net.minecraft.world.entity.player.Player player) {
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.myrdas_exhausted").withStyle(ChatFormatting.AQUA), true
                     );
                  }

                  return false;
               } else {
                  activateMyrdasRevive(entity, instance);
                  return true;
               }
            } else {
               return false;
            }
         } finally {
            REVIVING.remove(entity.getUUID());
         }
      } else {
         return false;
      }
   }

   private static boolean canRevive(LivingEntity entity) {
      UUID id = entity.getUUID();
      long day = entity.level().getDayTime() / 24000L;
      if (!REVIVE_DAY.getOrDefault(id, -1L).equals(day)) {
         REVIVE_DAY.put(id, day);
         REVIVES_USED.put(id, 0);
      }

      int used = REVIVES_USED.getOrDefault(id, 0);
      return used < (Integer)cfg().maxRevivesPerDay.get();
   }

   private static void countRevive(LivingEntity entity) {
      UUID id = entity.getUUID();
      long day = entity.level().getDayTime() / 24000L;
      if (!REVIVE_DAY.getOrDefault(id, -1L).equals(day)) {
         REVIVE_DAY.put(id, day);
         REVIVES_USED.put(id, 0);
      }

      REVIVES_USED.put(id, REVIVES_USED.getOrDefault(id, 0) + 1);
   }

   private static void activateMyrdasRevive(LivingEntity entity, ManasSkillInstance instance) {
      countRevive(entity);
      entity.setHealth(0.01F);
      entity.invulnerableTime = (Integer)cfg().reviveInvulnerabilityTicks.get();
      Predicate<Holder<MobEffect>> predicate = effect -> ((MobEffect)effect.value()).getCategory() == MobEffectCategory.HARMFUL;
      TensuraMobEffect.removePredicateEffect(entity, predicate);
      TensuraStorages.resetEffect(entity);
      ElfariaTheHallowedIcemaidenSkill.restoreExistenceResources(
         entity,
         (Double)cfg().reviveHealthRatio.get(),
         (Double)cfg().reviveMinimumHealth.get(),
         (Double)cfg().reviveMagiculeRatio.get(),
         (Double)cfg().reviveAuraRatio.get(),
         (Double)cfg().reviveSpiritualHealthRatio.get()
      );
      entity.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
            (Integer)cfg().reviveRegenerationTicks.get(),
            (Integer)cfg().reviveRegenerationAmplifier.get(),
            true,
            false,
            true
         )
      );
      entity.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
            (Integer)cfg().reviveHakiCoatTicks.get(),
            (Integer)cfg().reviveHakiCoatAmplifier.get(),
            true,
            false,
            true
         )
      );
      MYRDAS_MODE_UNTIL.put(entity.getUUID(), entity.level().getGameTime() + ((Integer)cfg().myrdasModeDurationTicks.get()).intValue());

      for (int i = 0; i < instance.getModes(); i++) {
         instance.setCoolDown((Integer)cfg().reviveCooldownTicks.get(), i);
      }

      instance.markDirty();
      if (entity.level() instanceof ServerLevel level) {
         DamageSource damageSource = ((ElfariaTheHallowedIcemaidenSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get()).damageSource(entity, instance, 1);
         AABB box = entity.getBoundingBox().inflate((Double)cfg().reviveFreezeRadius.get());

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != entity && !e.isAlliedTo(entity))) {
            target.hurt(damageSource, ((Double)cfg().reviveFreezeDamage.get()).floatValue());
            ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(entity, target, instance.isMastered(entity));
            Vec3 push = target.position().subtract(entity.position());
            if (push.lengthSqr() > 0.001) {
               target.setDeltaMovement(
                  target.getDeltaMovement()
                     .add(push.normalize().scale((Double)cfg().reviveKnockback.get()))
                     .add(0.0, (Double)cfg().reviveVerticalKnockback.get(), 0.0)
               );
               target.hurtMarked = true;
            }
         }

         activateMyrdasReviveBarrier(entity, instance);
         startReviveSteps(entity);
         level.playSound(null, entity.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.35F);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.TOTEM_OF_UNDYING, 1.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.TOTEM_OF_UNDYING, 2.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.FLASH, 1.0);
      }

      if (entity instanceof net.minecraft.world.entity.player.Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.myrdas").withStyle(ChatFormatting.AQUA), false
         );
      }
   }

   public static void tickOwnerSystems(LivingEntity owner, ManasSkillInstance instance) {
      if (owner != null && !owner.level().isClientSide()) {
         long now = owner.level().getGameTime();
         Long last = OWNER_SYSTEM_TICK.get(owner.getUUID());
         if (last == null || last != now) {
            OWNER_SYSTEM_TICK.put(owner.getUUID(), now);
            tickGlaciaNovas(owner, instance);
            tickCathedrals(owner, instance);
         }
      }
   }

   public static void startCathedral(LivingEntity owner, ManasSkillInstance instance, Vec3 center, int duration) {
      CATHEDRALS.put(
         owner.getUUID(),
         new ElfariaTheHallowedIcemaidenHandler.Cathedral(
            owner.getUUID(), owner.level().dimension(), center, owner.level().getGameTime() + duration, instance.isMastered(owner)
         )
      );
      tickCathedrals(owner, instance);
      if (owner.level() instanceof ServerLevel level) {
         double radius = instance.isMastered(owner) ? (Double)cfg().fruzelCardeneiaRadiusMastered.get() : (Double)cfg().fruzelCardeneiaRadius.get();
         ElfariaIcemaidenVfxPayload.sendCathedral(level, owner.position(), center, (float)radius, duration, true);
      }
   }

   private static void tickCathedrals(LivingEntity owner, ManasSkillInstance instance) {
      ElfariaTheHallowedIcemaidenHandler.Cathedral cathedral = CATHEDRALS.get(owner.getUUID());
      if (cathedral != null) {
         if (owner.level().dimension() == cathedral.dimension && owner.level().getGameTime() <= cathedral.endTime) {
            double radius = cathedral.mastered ? (Double)cfg().fruzelCardeneiaRadiusMastered.get() : (Double)cfg().fruzelCardeneiaRadius.get();
            double halfXz = Math.max(radius * 2.25, radius + 18.0);
            AABB freezeBox = new AABB(
               cathedral.center.x - halfXz,
               cathedral.center.y - 10.0,
               cathedral.center.z - halfXz,
               cathedral.center.x + halfXz,
               cathedral.center.y + Math.max(radius * 3.5, 48.0),
               cathedral.center.z + halfXz
            );
            DamageSource source = ((ElfariaTheHallowedIcemaidenSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get()).damageSource(owner, instance, 3);
            int refresh = cathedral.mastered ? 4 : 5;
            float baseDamage = cathedral.mastered
               ? ((Double)cfg().fruzelCardeneiaSpikeDamageMastered.get()).floatValue()
               : ((Double)cfg().fruzelCardeneiaSpikeDamage.get()).floatValue();
            float damage = Math.max(baseDamage * (cathedral.mastered ? 7.0F : 5.0F), cathedral.mastered ? 65.0F : 42.0F);
            Set<UUID> seen = new HashSet<>();

            for (LivingEntity target : owner.level().getEntitiesOfClass(LivingEntity.class, freezeBox, e -> shouldAffect(owner, e))) {
               seen.add(target.getUUID());
               ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget lock = CATHEDRAL_FREEZE_LOCKS.computeIfAbsent(
                  target.getUUID(), id -> new ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget(owner.getUUID(), target.position())
               );
               lock.owner = owner.getUUID();
               lock.pos = lock.pos.lerp(target.position(), 0.003);
               lock.lastSeenGameTime = owner.level().getGameTime();
               freezeHard(target, lock.pos);
               target.setYHeadRot(target.getYRot());
               if (target instanceof Mob mob) {
                  if (!lock.forcedNoAi) {
                     lock.hadNoAi = mob.isNoAi();
                     lock.forcedNoAi = true;
                  }

                  mob.setNoAi(true);
               }

               target.invulnerableTime = 0;
               if (owner.level().getGameTime() % refresh == 0L) {
                  forceHurt(target, source, damage);
                  if (owner.level() instanceof ServerLevel level) {
                     ElfariaIcemaidenVfxPayload.sendFruzelSpike(level, lock.pos, cathedral.mastered ? 2.2F : 1.7F, false);
                     ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, lock.pos, cathedral.mastered ? 2.45F : 1.95F, false);
                  }
               } else if (owner.tickCount % 8 == 0 && owner.level() instanceof ServerLevel level) {
                  ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, lock.pos, cathedral.mastered ? 1.95F : 1.55F, false);
               }
            }

            releaseCathedralLocks(owner.level(), owner.getUUID(), seen);
            if ((Boolean)cfg().fruzelCardeneiaDeletesProjectiles.get()) {
               for (Projectile projectile : owner.level().getEntitiesOfClass(Projectile.class, freezeBox, Entity::isAlive)) {
                  Entity projectileOwner = projectile.getOwner();
                  if (projectileOwner != owner && (projectileOwner == null || !projectileOwner.isAlliedTo(owner))) {
                     projectile.discard();
                  }
               }
            }

            if (owner.tickCount % 10 == 0 && owner.level() instanceof ServerLevel level) {
               ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, cathedral.center, (float)Math.max(3.0, radius * 0.42), false);
            }
         } else {
            CATHEDRALS.remove(owner.getUUID());
            releaseCathedralLocks(owner.level(), owner.getUUID(), null);
         }
      }
   }

   public static void damageStellasNatea(LivingEntity owner, ManasSkillInstance instance, Vec3 impact, float baseDamage, boolean main) {
      if (owner != null && impact != null && owner.level() instanceof ServerLevel level) {
         boolean mastered = instance != null && instance.isMastered(owner);
         Vec3 center = snapSpearImpact(level, impact);
         double half = 5.0;
         AABB box = new AABB(center.x - half, center.y - half, center.z - half, center.x + half, center.y + half, center.z + half);
         float damage = Math.max(baseDamage * (main ? 5.0F : 2.5F), main ? 95.0F : 42.0F);
         DamageSource source = ((ElfariaTheHallowedIcemaidenSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get()).damageSource(owner, instance, 2);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldAffect(owner, targetx))) {
            boolean hadDust = target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FROST));
            forceCombatHurt(owner, target, source, damage);
            ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(owner, target, mastered);
            if (hadDust && main && target.isAlive()) {
               forceCombatHurt(owner, target, source, damage * 0.55F);
               ElfariaIcemaidenVfxPayload.sendStellasImpact(level, snapSpearImpact(level, target.position()), 0.95F, false);
            }
         }
      }
   }

   public static void damageGlaciaLastAlbis(
      LivingEntity owner, ManasSkillInstance instance, Vec3 impact, float baseDamage, double radius, boolean main, boolean mastered
   ) {
      if (owner != null && impact != null && owner.level() instanceof ServerLevel level) {
         Vec3 var19 = impact;
         double half = 50.0;
         AABB box = new AABB(var19.x - half, var19.y - half, var19.z - half, var19.x + half, var19.y + half, var19.z + half);
         float damage = Math.max(baseDamage * (main ? 14.0F : 3.0F), main ? 180.0F : 55.0F);
         DamageSource source = ((ElfariaTheHallowedIcemaidenSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get()).damageSource(owner, instance, 4);
         Vec3 visualImpact = snapSpearImpact(level, impact);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldAffect(owner, targetx))) {
            forceCombatHurt(owner, target, source, damage);
            ElfariaTheHallowedIcemaidenSkill.applyDiamondDust(owner, target, mastered);
            Vec3 push = target.position().subtract(visualImpact);
            if (push.lengthSqr() < 0.001) {
               push = target.position().subtract(owner.position()).add(0.0, 0.35, 0.0);
            }

            if (push.lengthSqr() > 0.001) {
               SkillHelper.knockBack(target, push.normalize(), (Double)cfg().glaciaLastAlbisKnockback.get() * 4.0, 0.75, 1.1);
               target.setDeltaMovement(target.getDeltaMovement().add(0.0, 1.05, 0.0));
               target.hasImpulse = true;
               target.hurtMarked = true;
            }
         }
      }
   }

   public static void queueSpear(UUID owner, Vec3 impact, int delay, float damage, boolean main) {
      SPEARS.add(new ElfariaTheHallowedIcemaidenHandler.QueuedSpear(owner, impact, Math.max(0, delay), damage, main));
   }

   private static void tickSpears(ServerPlayer player) {
      if (!SPEARS.isEmpty()) {
         Iterator<ElfariaTheHallowedIcemaidenHandler.QueuedSpear> iterator = SPEARS.iterator();

         while (iterator.hasNext()) {
            ElfariaTheHallowedIcemaidenHandler.QueuedSpear spear = iterator.next();
            if (spear.owner.equals(player.getUUID())) {
               if (spear.delay == spear.initialDelay && player.level() instanceof ServerLevel level) {
                  Vec3 impact = snapSpearImpact(level, spear.impact);
                  ElfariaIcemaidenVfxPayload.sendStellasStrike(level, impact.add(0.0, 14.0, 0.0), impact, spear.main ? 1.18F : 0.86F, spear.main);
               }

               spear.delay--;
               if (spear.delay <= 0) {
                  strikeSpear(player, spear);
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void strikeSpear(ServerPlayer owner, ElfariaTheHallowedIcemaidenHandler.QueuedSpear spear) {
      if (owner.level() instanceof ServerLevel level) {
         ManasSkillInstance instance = SkillUtils.getSkillOrNull(owner, (ManasSkill)AllSkills.ELFARIA_THE_HALLOWED_ICEMAIDEN.get());
         if (instance != null && instance.isMastered(owner)) {
            boolean mastered = true;
         } else {
            boolean mastered = false;
         }

         Vec3 impact = snapSpearImpact(level, spear.impact);
         if (spear.damage > 0.0F) {
            damageStellasNatea(owner, instance, spear.impact, spear.damage, spear.main);
         }

         ElfariaIcemaidenVfxPayload.sendStellasImpact(level, impact, spear.main ? 1.7F : 1.12F, spear.main);
         if (spear.main) {
            ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, impact, 1.9F, false);
         }

         level.playSound(null, BlockPos.containing(impact), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, spear.main ? 0.9F : 0.55F, spear.main ? 0.72F : 0.92F);
      }
   }

   public static void queueGlaciaLastAlbisNova(UUID owner, Vec3 impact, int delay, float damage, double radius, boolean main) {
      queueGlaciaLastAlbisNova(owner, impact, delay, damage, radius, main, main);
   }

   public static void queueGlaciaLastAlbisNova(UUID owner, Vec3 impact, int delay, float damage, double radius, boolean main, boolean mastered) {
      int fixedDelay = Math.max(main ? 72 : 36, Math.min(main ? 150 : 80, delay));
      GLACIA_NOVAS.add(new ElfariaTheHallowedIcemaidenHandler.QueuedGlaciaNova(owner, impact, fixedDelay, damage, radius, main, mastered));

      while (GLACIA_NOVAS.size() > 12) {
         GLACIA_NOVAS.remove(0);
      }
   }

   private static void tickGlaciaNovas(LivingEntity owner, ManasSkillInstance instance) {
      if (!GLACIA_NOVAS.isEmpty()) {
         Iterator<ElfariaTheHallowedIcemaidenHandler.QueuedGlaciaNova> iterator = GLACIA_NOVAS.iterator();

         while (iterator.hasNext()) {
            ElfariaTheHallowedIcemaidenHandler.QueuedGlaciaNova nova = iterator.next();
            if (nova.owner.equals(owner.getUUID())) {
               if (nova.delay > 0) {
                  nova.delay--;
               }

               if (nova.delay <= 0) {
                  strikeGlaciaNova(owner, instance, nova);
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void strikeGlaciaNova(LivingEntity owner, ManasSkillInstance instance, ElfariaTheHallowedIcemaidenHandler.QueuedGlaciaNova nova) {
      if (owner.level() instanceof ServerLevel level) {
         Vec3 var5 = snapSpearImpact(level, nova.impact);
         if (nova.damage > 0.0F) {
            damageGlaciaLastAlbis(owner, instance, nova.impact, nova.damage, nova.radius, nova.main, nova.mastered);
         }

         ElfariaIcemaidenVfxPayload.sendGlaciaBurst(level, var5, 50.0F, nova.main);
         ElfariaIcemaidenVfxPayload.sendFieldFreeze(level, var5, 40.0F, true);
         level.playSound(null, BlockPos.containing(var5), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.6F, 0.55F);
         level.playSound(null, BlockPos.containing(var5), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 2.2F, 0.48F);
         level.playSound(null, BlockPos.containing(var5), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 2.0F, 0.62F);
      }
   }

   private static boolean shouldAffect(LivingEntity owner, LivingEntity target) {
      return target != null && target.isAlive() && target != owner
         ? !(target instanceof net.minecraft.world.entity.player.Player player && (player.isCreative() || player.isSpectator()))
         : false;
   }

   private static void freezeHard(LivingEntity target, Vec3 pos) {
      target.stopRiding();
      target.teleportTo(pos.x, pos.y, pos.z);
      target.moveTo(pos.x, pos.y, pos.z, target.getYRot(), target.getXRot());
      target.setDeltaMovement(Vec3.ZERO);
      target.hasImpulse = true;
      target.hurtMarked = true;
      target.fallDistance = 0.0F;
      if (target instanceof Mob mob) {
         mob.getNavigation().stop();
         mob.setTarget(null);
      }
   }

   private static void damageLiving(LivingEntity living, LivingEntity source, float damage) {
      if (living != null && source != null && living.isAlive() && !(damage <= 0.0F)) {
         DamageSource damageSource = source.damageSources().magic();
         forceCombatHurt(source, living, damageSource, Math.max(1.0F, damage));
      }
   }

   private static void forceCombatHurt(LivingEntity owner, LivingEntity target, DamageSource source, float damage) {
      if (!(damage <= 0.0F) && target != null && target.isAlive()) {
         float beforeHealth = target.getHealth();
         float beforeAbsorb = target.getAbsorptionAmount();
         target.invulnerableTime = 0;
         target.hurt(source, damage);
         target.invulnerableTime = 0;
         TensuraDamageHelper.directSpiritualHurt(target, owner, source, damage);
         if (target.isAlive() && target.getHealth() >= beforeHealth && target.getAbsorptionAmount() >= beforeAbsorb && !target.hasInfiniteMaterials()) {
            float remaining = damage;
            float absorption = target.getAbsorptionAmount();
            if (absorption > 0.0F) {
               float used = Math.min(absorption, remaining);
               target.setAbsorptionAmount(absorption - used);
               remaining -= used;
            }

            if (remaining > 0.0F) {
               target.setHealth(Math.max(0.0F, beforeHealth - remaining));
            }

            if (target.getHealth() <= 0.0F) {
               target.getCombatTracker().recordDamage(source, Math.max(1.0F, beforeHealth));
               target.die(source);
            }
         }

         TensuraDamageHelper.markHurt(target, owner);
         target.invulnerableTime = 0;
         target.hasImpulse = true;
         target.hurtMarked = true;
      }
   }

   private static void forceHurt(LivingEntity target, DamageSource source, float damage) {
      if (!(damage <= 0.0F) && target != null && target.isAlive()) {
         float beforeHealth = target.getHealth();
         float beforeAbsorb = target.getAbsorptionAmount();
         target.invulnerableTime = 0;
         target.hurt(source, damage);
         target.invulnerableTime = 0;
         TensuraDamageHelper.directSpiritualHurt(target, source.getEntity(), source, damage);
         if (target.isAlive() && target.getHealth() >= beforeHealth && target.getAbsorptionAmount() >= beforeAbsorb && !target.hasInfiniteMaterials()) {
            float remaining = damage;
            float absorption = target.getAbsorptionAmount();
            if (absorption > 0.0F) {
               float used = Math.min(absorption, remaining);
               target.setAbsorptionAmount(absorption - used);
               remaining -= used;
            }

            if (remaining > 0.0F) {
               target.setHealth(Math.max(0.0F, beforeHealth - remaining));
            }

            if (target.getHealth() <= 0.0F) {
               target.getCombatTracker().recordDamage(source, beforeHealth);
               target.die(source);
            }
         }

         TensuraDamageHelper.markHurt(target, source.getEntity());
         target.invulnerableTime = 0;
         target.hurtMarked = true;
      }
   }

   private static void releaseCathedralLocks(Level level, UUID owner, Set<UUID> stillSeen) {
      Iterator<Entry<UUID, ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget>> iterator = CATHEDRAL_FREEZE_LOCKS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget> entry = iterator.next();
         ElfariaTheHallowedIcemaidenHandler.FrozenCathedralTarget lock = entry.getValue();
         if (lock.owner.equals(owner)) {
            boolean remove = stillSeen == null || !stillSeen.contains(entry.getKey()) || level.getGameTime() - lock.lastSeenGameTime > 5L;
            if (remove) {
               if ((level instanceof ServerLevel serverLevel ? serverLevel.getEntity(entry.getKey()) : null) instanceof Mob mob
                  && lock.forcedNoAi
                  && !lock.hadNoAi) {
                  mob.setNoAi(false);
               }

               iterator.remove();
            }
         }
      }
   }

   public static boolean isPossessing(net.minecraft.world.entity.player.Player player) {
      return POSSESSIONS.containsKey(player.getUUID());
   }

   public static boolean tryStartPossession(ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         ArsWeissCloneEntity clone = getNearestOwnedClone(player, (Double)cfg().parallelBodyPossessRange.get());
         if (clone != null && clone.isAlive()) {
            ArsWeissCloneEntity anchor = new ArsWeissCloneEntity((EntityType<? extends ArsWeissCloneEntity>)ModEntityTypes.ARS_WEISS_CLONE.get(), level);
            int duration = Math.max((Integer)cfg().parallelBodyPossessionMaxTicks.get(), 40);
            anchor.setOwnerData(player, duration);
            anchor.setAnchorBody(true);
            anchor.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
            anchor.setYHeadRot(player.getYHeadRot());
            anchor.setCustomName(player.getDisplayName());
            level.addFreshEntity(anchor);
            if (anchor instanceof Mob mob) {
               mob.setNoAi(true);
               mob.getNavigation().stop();
            }

            POSSESSIONS.put(
               player.getUUID(),
               new ElfariaTheHallowedIcemaidenHandler.PossessionState(
                  player.getUUID(), anchor.getUUID(), level.dimension(), level.getGameTime() + duration, Math.max(0.0F, player.getHealth())
               )
            );
            ANCHOR_TO_OWNER.put(anchor.getUUID(), player.getUUID());
            Vec3 clonePos = clone.position();
            float yRot = clone.getYRot();
            float xRot = clone.getXRot();
            clone.discard();
            player.teleportTo(level, clonePos.x, clonePos.y, clonePos.z, yRot, xRot);
            player.invulnerableTime = Math.max(player.invulnerableTime, (Integer)cfg().parallelBodyPossessInvulnerabilityTicks.get());
            level.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), SoundSource.PLAYERS, 1.0F, 1.2F);
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.possess"), true);
            return true;
         } else {
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.no_clone"), true);
            return false;
         }
      } else {
         return false;
      }
   }

   public static void returnFromPossessedClone(ServerPlayer player, boolean cloneBodyDied) {
      ElfariaTheHallowedIcemaidenHandler.PossessionState state = POSSESSIONS.remove(player.getUUID());
      if (state != null) {
         Entity anchor = getEntity(player.serverLevel(), state.anchorId);
         if (anchor == null) {
            player.hurt(player.damageSources().genericKill(), Float.MAX_VALUE);
         } else {
            ANCHOR_TO_OWNER.remove(state.anchorId);
            ServerLevel targetLevel = anchor.level() instanceof ServerLevel serverLevel ? serverLevel : player.serverLevel();
            player.teleportTo(targetLevel, anchor.getX(), anchor.getY(), anchor.getZ(), anchor.getYRot(), anchor.getXRot());
            anchor.discard();
            float ratio = cloneBodyDied
               ? ((Double)cfg().parallelBodyCloneDeathReturnHealthRatio.get()).floatValue()
               : ((Double)cfg().parallelBodyManualReturnHealthRatio.get()).floatValue();
            player.setHealth(Math.max(1.0F, Math.min(player.getMaxHealth(), player.getMaxHealth() * ratio)));
            player.invulnerableTime = Math.max(player.invulnerableTime, (Integer)cfg().parallelBodyReturnInvulnerabilityTicks.get());
            if (cloneBodyDied) {
               IExistence existence = TensuraStorages.getExistenceFrom(player);
               existence.setMagicule(
                  Math.max(0.0, existence.getMagicule() - EnergyHelper.getMaxMagicule(player) * (Double)cfg().parallelBodyCloneDeathMagiculeBacklashRatio.get())
               );
               existence.setAura(
                  Math.max(0.0, existence.getAura() - EnergyHelper.getMaxAura(player) * (Double)cfg().parallelBodyCloneDeathAuraBacklashRatio.get())
               );
               existence.markDirty();
            }

            targetLevel.playSound(null, player.blockPosition(), (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), SoundSource.PLAYERS, 1.0F, 0.85F);
            player.displayClientMessage(
               Component.translatable(
                  cloneBodyDied
                     ? "tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.clone_destroyed"
                     : "tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.return"
               ),
               true
            );
         }
      }
   }

   public static void forceClearPossession(ServerPlayer player) {
      ElfariaTheHallowedIcemaidenHandler.PossessionState state = POSSESSIONS.remove(player.getUUID());
      if (state != null) {
         ANCHOR_TO_OWNER.remove(state.anchorId);
         Entity anchor = getEntity(player.serverLevel(), state.anchorId);
         if (anchor != null) {
            anchor.discard();
         }
      }
   }

   public static void forceClearPossession(net.minecraft.world.entity.player.Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         forceClearPossession(serverPlayer);
      } else {
         ElfariaTheHallowedIcemaidenHandler.PossessionState state = POSSESSIONS.remove(player.getUUID());
         if (state != null) {
            ANCHOR_TO_OWNER.remove(state.anchorId);
         }
      }
   }

   private static void tickPossession(ServerPlayer player) {
      ElfariaTheHallowedIcemaidenHandler.PossessionState state = POSSESSIONS.get(player.getUUID());
      if (state != null) {
         Entity anchor = getEntity(player.serverLevel(), state.anchorId);
         if (anchor != null && anchor.isAlive()) {
            if (anchor instanceof Mob mob) {
               mob.setNoAi(true);
               mob.getNavigation().stop();
               mob.setDeltaMovement(Vec3.ZERO);
               mob.hurtMarked = true;
            }

            if (player.level().getGameTime() > state.endTime) {
               returnFromPossessedClone(player, false);
            } else {
               if (!(Boolean)cfg().parallelBodyAllowDimensionTravelWhilePossessing.get() && player.level().dimension() != state.dimension) {
                  returnFromPossessedClone(player, false);
               }
            }
         } else {
            POSSESSIONS.remove(player.getUUID());
            ANCHOR_TO_OWNER.remove(state.anchorId);
            player.hurt(player.damageSources().genericKill(), Float.MAX_VALUE);
         }
      }
   }

   private static List<ArsWeissCloneEntity> validWorkerClones(ServerPlayer owner) {
      List<ArsWeissCloneEntity> out = new ArrayList<>();

      for (ArsWeissCloneEntity clone : AlbisVinaHandler.getClones(owner.getUUID())) {
         if (clone != null && clone.isAlive() && !ANCHOR_TO_OWNER.containsKey(clone.getUUID())) {
            out.add(clone);
         }
      }

      return out;
   }

   private static ArsWeissCloneEntity getNearestOwnedClone(ServerPlayer player, double range) {
      ArsWeissCloneEntity best = null;
      double bestDist = range * range;

      for (ArsWeissCloneEntity clone : validWorkerClones(player)) {
         double dist = clone.distanceToSqr(player);
         if (dist < bestDist && isOwnedClone(player, clone)) {
            bestDist = dist;
            best = clone;
         }
      }

      return best;
   }

   private static boolean isOwnedClone(ServerPlayer player, ArsWeissCloneEntity clone) {
      UUID ownerId = clone.getOwnerUUIDValue();
      return ownerId != null && ownerId.equals(player.getUUID());
   }

   private static Entity getEntity(ServerLevel fallback, UUID uuid) {
      Entity direct = fallback.getEntity(uuid);
      if (direct != null) {
         return direct;
      }

      for (ServerLevel level : fallback.getServer().getAllLevels()) {
         Entity entity = level.getEntity(uuid);
         if (entity != null) {
            return entity;
         }
      }

      return null;
   }

   private static void killOwnerFromAnchor(Level level, UUID ownerId) {
      if (level instanceof ServerLevel serverLevel) {
         ServerPlayer player = serverLevel.getServer().getPlayerList().getPlayer(ownerId);
         if (player != null) {
            POSSESSIONS.remove(ownerId);
            player.hurt(player.damageSources().genericKill(), Float.MAX_VALUE);
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.elfaria_the_hallowed_icemaiden.main_body_destroyed"), false);
         }
      }
   }

   public static void grantPackage(LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if ((Boolean)cfg().grantPackageOnAcquire.get()) {
            if (PACKAGE_GRANTED.add(player.getUUID()) || (Boolean)cfg().retryPackageGrantOnToggle.get()) {
               for (String id : (List)cfg().grantedSkillIds.get()) {
                  ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
                  if (skill != null && !SkillUtils.hasSkill(player, skill)) {
                     SkillHelper.learnSkill(player, skill);
                  }
               }
            }
         }
      }
   }

   public static void clearPlayer(UUID uuid) {
      MYRDAS_MODE_UNTIL.remove(uuid);
      MYRDAS_BARRIERS.remove(uuid);
      CATHEDRALS.remove(uuid);
      CATHEDRAL_FREEZE_LOCKS.entrySet().removeIf(entry -> entry.getValue().owner.equals(uuid));
      GLACIA_NOVAS.removeIf(nova -> nova.owner.equals(uuid));
      REVIVE_STEP_UNTIL.remove(uuid);
      PACKAGE_GRANTED.remove(uuid);
   }

   private static boolean isMagicDamage(DamageSource source) {
      return source == null
         ? false
         : source.typeHolder()
            .unwrapKey()
            .map(
               key -> {
                  String path = key.location().getPath();
                  return path.contains("magic")
                     || path.contains("spell")
                     || path.contains("elemental")
                     || path.contains("ice")
                     || path.contains("water")
                     || path.contains("frost")
                     || path.contains("cold")
                     || path.contains("blizzard");
               }
            )
            .orElse(false);
   }

   private static void cleanup(Level level) {
      Iterator<Entry<UUID, Long>> myrdas = MYRDAS_MODE_UNTIL.entrySet().iterator();

      while (myrdas.hasNext()) {
         if (level.getGameTime() > myrdas.next().getValue()) {
            myrdas.remove();
         }
      }

      Iterator<Entry<UUID, ElfariaTheHallowedIcemaidenHandler.BarrierState>> barriers = MYRDAS_BARRIERS.entrySet().iterator();

      while (barriers.hasNext()) {
         ElfariaTheHallowedIcemaidenHandler.BarrierState state = barriers.next().getValue();
         if (level.getGameTime() > state.endTime) {
            barriers.remove();
         }
      }

      Iterator<Entry<UUID, Long>> reviveSteps = REVIVE_STEP_UNTIL.entrySet().iterator();

      while (reviveSteps.hasNext()) {
         if (level.getGameTime() > reviveSteps.next().getValue()) {
            reviveSteps.remove();
         }
      }
   }

   private static Vec3 snapSpearImpact(Level level, Vec3 impact) {
      MutableBlockPos pos = new MutableBlockPos();
      int startY = Math.min(level.getMaxBuildHeight() - 2, (int)Math.floor(impact.y) + 10);
      int endY = Math.max(level.getMinBuildHeight() + 1, (int)Math.floor(impact.y) - 28);
      int x = (int)Math.floor(impact.x);
      int z = (int)Math.floor(impact.z);

      for (int y = startY; y >= endY; y--) {
         pos.set(x, y, z);
         if (!level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir()) {
            return new Vec3(impact.x, y + 1.04, impact.z);
         }
      }

      return impact;
   }

   private record BarrierState(UUID owner, ResourceKey<Level> dimension, long endTime, double radius, boolean mastered) {
   }

   private record Cathedral(UUID owner, ResourceKey<Level> dimension, Vec3 center, long endTime, boolean mastered) {
   }

   private static final class FrozenCathedralTarget {
      private UUID owner;
      private Vec3 pos;
      private long lastSeenGameTime;
      private boolean forcedNoAi;
      private boolean hadNoAi;

      private FrozenCathedralTarget(UUID owner, Vec3 pos) {
         this.owner = owner;
         this.pos = pos;
      }
   }

   private record PossessionState(UUID owner, UUID anchorId, ResourceKey<Level> dimension, long endTime, float startHealth) {
   }

   private static final class QueuedGlaciaNova {
      private final UUID owner;
      private final Vec3 impact;
      private int delay;
      private final float damage;
      private final double radius;
      private final boolean main;
      private final boolean mastered;

      private QueuedGlaciaNova(UUID owner, Vec3 impact, int delay, float damage, double radius, boolean main, boolean mastered) {
         this.owner = owner;
         this.impact = impact;
         this.delay = delay;
         this.damage = damage;
         this.radius = radius;
         this.main = main;
         this.mastered = mastered;
      }
   }

   private static final class QueuedSpear {
      private final UUID owner;
      private final Vec3 impact;
      private final int initialDelay;
      private int delay;
      private final float damage;
      private final boolean main;

      private QueuedSpear(UUID owner, Vec3 impact, int delay, float damage, boolean main) {
         this.owner = owner;
         this.impact = impact;
         this.initialDelay = delay;
         this.delay = delay;
         this.damage = damage;
         this.main = main;
      }
   }
}
