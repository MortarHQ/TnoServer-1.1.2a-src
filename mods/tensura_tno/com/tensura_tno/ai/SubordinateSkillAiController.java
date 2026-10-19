package com.tensura_tno.ai;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.world.TensuraTNOGameRules;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.battlewill.melee.EightPetalsFlashArt;
import io.github.manasmods.tensura.ability.battlewill.melee.FivePetalsThrustArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.DarkEightPalmsArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.DeathMarchDanceArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.ElephantStampedeArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.MagicBulletArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.MaximumMagicBulletArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.OgreFlameArt;
import io.github.manasmods.tensura.ability.battlewill.projectile.OgreSwordCannonArt;
import io.github.manasmods.tensura.ability.battlewill.utility.ViolentBreakArt;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.aspectual.explosion.ExplosionMagic;
import io.github.manasmods.tensura.ability.magic.aspectual.fire.FireBallMagic;
import io.github.manasmods.tensura.ability.magic.aspectual.ice.IceBlizzardMagic;
import io.github.manasmods.tensura.ability.skill.extra.BlackFlameSkill;
import io.github.manasmods.tensura.ability.skill.extra.SpatialDominationSkill;
import io.github.manasmods.tensura.ability.skill.unique.BerserkerSkill;
import io.github.manasmods.tensura.ability.skill.unique.GreedSkill;
import io.github.manasmods.tensura.ability.skill.unique.LustSkill;
import io.github.manasmods.tensura.ability.skill.unique.WrathSkill;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.config.ability.BattlewillConfig;
import io.github.manasmods.tensura.config.ability.magic.AspectualMagicConfig;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;

public final class SubordinateSkillAiController {
   public static final int AI_HELD_TIME_LIMIT = 72000;
   private static final int POLL_INTERVAL = 10;
   private static final int RETRY_DELAY_MIN = 20;
   private static final int RETRY_DELAY_VARIANCE = 21;
   private static final int DEFAULT_CHARGE_TICKS = 30;
   private static final int DEFAULT_CHANNEL_TICKS = 40;
   private static final int WRATH_REACTOR_TICKS = 101;
   private static final int MAX_CAST_TICKS = 600;
   private static final double MAX_TARGET_DISTANCE_SQR = 4096.0;
   private static final int KEY_NUMBER = 0;
   private static final TagKey<ManasSkill> EXCLUDED_SKILLS = TagKey.create(
      SkillAPI.getSkillRegistryKey(), ResourceLocation.fromNamespaceAndPath("tensura_tno", "subordinate_ai_excluded")
   );
   private static final ResourceLocation BERSERKER_MODIFIER = ResourceLocation.fromNamespaceAndPath("tensura", "berserker");
   private static final Set<String> UNSAFE_EXACT_PATHS = Set.of(
      "air_flight",
      "analyze",
      "analyst",
      "analytical_appraisal",
      "body_double",
      "clairvoyance",
      "contract_little_fox",
      "creator",
      "create_greater_undead",
      "create_lesser_undead",
      "create_undead",
      "degenerate",
      "doppelganger",
      "drainage",
      "dragon_eye",
      "earth",
      "edo_tensei",
      "earth_domination",
      "earth_manipulation",
      "escape",
      "farsight",
      "flight",
      "freeze",
      "gate",
      "godly_craftsman",
      "gravity_flight",
      "great_sage",
      "healing_rain",
      "human_form",
      "instant_move",
      "light",
      "liquidize",
      "magic_sense",
      "magic_ore_sense",
      "mathematician",
      "observer",
      "possession",
      "possession_magic",
      "reincarnation",
      "researcher",
      "search_enemy",
      "seeker",
      "sense_heat_source",
      "space",
      "spatial_motion",
      "spatial_storage",
      "spirit_summon",
      "teleport",
      "thought_communication",
      "unyielding",
      "universal_perception",
      "water",
      "water_aspectual",
      "weather_domination",
      "weather_manipulation",
      "warp_portal"
   );
   private static final Set<String> UNSAFE_MODE_IDS = Set.of(
      "commander.movement_communication",
      "commander.targeting_communication",
      "earth_lock.earth",
      "falsifier.fake_death",
      "falsifier.illusion",
      "fusionist.disassemble",
      "infinity_prison.imaginary_space",
      "lust.rebirth",
      "molecular_manipulation.block",
      "predator.analysis",
      "predator.mimicry",
      "predator.stomach",
      "reaper.attack",
      "reverser.buff",
      "shadow_motion.step",
      "shadow_motion.storage",
      "shadow_striker.espionage",
      "shadow_striker.ultra_acceleration",
      "starved.provide",
      "starved.receive",
      "starved.stomach",
      "traveler.instant_motion",
      "traveler.teleport",
      "ultrasonic_waves.auditory_sense"
   );
   private static final Set<String> SELF_CAST_SKILL_PATHS = Set.of(
      "agility", "antidote", "full_recovery", "healing", "lighten", "protection", "reinforcement", "strength_aspectual", "recovery"
   );
   private static final Set<String> SELF_CAST_MODE_IDS = Set.of(
      "earth_lock.self", "law_manipulation.cleanse", "predator.isolation", "ranged_barrier.5", "ranged_barrier.10", "ranged_barrier.20"
   );
   private static final Set<String> CAST_ARTIFACT_TAG_PREFIXES = Set.of(
      "BarrierID",
      "BeamID",
      "BlessingID",
      "BreathEntity",
      "BulletID",
      "FieldID",
      "FlowerID",
      "HakiID",
      "LanceID",
      "MagicCircleID",
      "Mist",
      "ShellID",
      "ShieldEntity",
      "SphereID",
      "StoneID"
   );
   private static final Map<LivingEntity, SubordinateSkillAiController.CastSession> ACTIVE_CASTS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<LivingEntity, SubordinateSkillAiController.CleanupCast> CLEANUP_CASTS = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<LivingEntity, Long> NEXT_ATTEMPT = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<LivingEntity, SubordinateSkillAiController.CastKey> LAST_SKILL = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<Class<?>, SubordinateSkillAiController.CallbackProfile> CALLBACK_CACHE = new ConcurrentHashMap<>();
   private static final Set<ResourceLocation> BROKEN_SKILLS = ConcurrentHashMap.newKeySet();
   private static final SubordinateSkillAiController.ActivationMethod CAN_ACTIVATE_SKILL = findCanActivateSkillMethod();

   private SubordinateSkillAiController() {
   }

   public static boolean tick(Mob subordinate, @Nullable LivingEntity target, @Nullable UUID ownerId, boolean combatAllowed) {
      if (subordinate.level().isClientSide()) {
         return false;
      }

      if (!TensuraTNOGameRules.isSubordinateSkillAiEnabled(subordinate.level())) {
         cancel(subordinate);
         return false;
      }

      long gameTime = subordinate.level().getGameTime();
      if (retryDeferredCleanup(subordinate, gameTime)) {
         return false;
      }

      SubordinateSkillAiController.CastSession active = ACTIVE_CASTS.get(subordinate);
      if (active != null) {
         return tickActiveCast(subordinate, target, ownerId, combatAllowed, active);
      }

      if (combatAllowed && ownerId != null && isValidTarget(subordinate, target, ownerId)) {
         if (gameTime < NEXT_ATTEMPT.getOrDefault(subordinate, 0L)) {
            return false;
         }

         NEXT_ATTEMPT.put(subordinate, gameTime + 10L);

         SkillStorage storage;
         try {
            storage = SkillAPI.getSkillsFrom(subordinate);
         } catch (RuntimeException | LinkageError error) {
            return false;
         }

         return !storage.getHeldSkills().isEmpty() ? false : tryStartCast(subordinate, target, storage, gameTime);
      } else {
         return false;
      }
   }

   public static boolean isCasting(LivingEntity entity) {
      return ACTIVE_CASTS.containsKey(entity);
   }

   public static boolean isAiCasting(LivingEntity entity, ManasSkill skill) {
      SubordinateSkillAiController.CastSession session = ACTIVE_CASTS.get(entity);
      return session != null && session.instance.getSkill().equals(skill);
   }

   public static boolean isAiCasting(LivingEntity entity, ManasSkill skill, int mode) {
      SubordinateSkillAiController.CastSession session = ACTIVE_CASTS.get(entity);
      return session != null && session.instance.getSkill().equals(skill) && session.mode == mode;
   }

   public static boolean isAiHolding(LivingEntity entity, ManasSkill skill) {
      SubordinateSkillAiController.CastSession active = ACTIVE_CASTS.get(entity);
      if (active != null && active.instance.getSkill().equals(skill)) {
         return true;
      }

      SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(entity);
      return cleanup != null && cleanup.session.instance.getSkill().equals(skill);
   }

   public static boolean shouldStopHeldTick(LivingEntity entity, ManasSkill skill, int mode, int keyNumber) {
      SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(entity);
      if (cleanup != null && cleanup.session.matches(skill, mode, keyNumber)) {
         return true;
      }

      SubordinateSkillAiController.CastSession active = ACTIVE_CASTS.get(entity);
      if (active == null || !active.matches(skill, mode, keyNumber)) {
         return false;
      }

      if (matchesEffectiveSnapshot(active)) {
         return false;
      }

      deferCleanup(entity, active, entity.level().getGameTime());
      return true;
   }

   public static boolean shouldPoll(Mob mob) {
      if (TensuraTNOGameRules.isSubordinateSkillAiEnabled(mob.level())) {
         if (isCasting(mob)) {
            return true;
         }

         SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(mob);
         return cleanup != null ? mob.level().getGameTime() >= cleanup.nextAttempt : Math.floorMod(mob.tickCount + mob.getId(), 10) == 0;
      } else {
         return isCasting(mob) || CLEANUP_CASTS.containsKey(mob);
      }
   }

   @Nullable
   public static LivingEntity resolveTarget(Mob mob) {
      LivingEntity target = mob.getTarget();
      if (target != null) {
         return target;
      }

      try {
         return (LivingEntity)mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
      } catch (RuntimeException ignored) {
         return null;
      }
   }

   @Nullable
   public static LivingEntity getCastTarget(Mob mob) {
      SubordinateSkillAiController.CastSession session = ACTIVE_CASTS.get(mob);
      if (session != null && mob.level() instanceof ServerLevel level) {
         return level.getEntity(session.targetId) instanceof LivingEntity living ? living : null;
      } else {
         return null;
      }
   }

   @Nullable
   public static UUID findOwnerId(LivingEntity subordinate) {
      try {
         return SubordinateHelper.getSubordinateOwnerUUID(subordinate);
      } catch (RuntimeException | LinkageError ignored) {
         return null;
      }
   }

   @Nullable
   public static LivingEntity resolveOwner(LivingEntity subordinate, UUID ownerId) {
      try {
         LivingEntity owner = SubordinateHelper.getSubordinateOwner(subordinate);
         if (owner != null && ownerId.equals(owner.getUUID())) {
            return owner;
         }
      } catch (RuntimeException | LinkageError var5) {
      }

      if (subordinate.level() instanceof ServerLevel level) {
         return (LivingEntity)(level.getEntity(ownerId) instanceof LivingEntity living ? living : level.getServer().getPlayerList().getPlayer(ownerId));
      } else {
         return null;
      }
   }

   public static boolean isValidTarget(Mob subordinate, @Nullable LivingEntity target, UUID ownerId) {
      if (target != null && target.isAlive() && !target.isRemoved()) {
         if (target == subordinate || target.level() != subordinate.level()) {
            return false;
         }

         if (subordinate.distanceToSqr(target) > 4096.0) {
            return false;
         }

         if (ownerId.equals(target.getUUID())) {
            return false;
         }

         UUID targetOwner = findOwnerId(target);
         if (ownerId.equals(targetOwner)) {
            return false;
         }

         if (subordinate.isAlliedTo(target)) {
            return false;
         }

         LivingEntity owner = resolveOwner(subordinate, ownerId);
         return owner == null || !owner.isAlliedTo(target);
      } else {
         return false;
      }
   }

   public static void cancel(LivingEntity subordinate) {
      SubordinateSkillAiController.CastSession session = ACTIVE_CASTS.get(subordinate);
      long gameTime = subordinate.level().getGameTime();
      if (session != null) {
         boolean artifactsRemoved = cancelSkillArtifacts(subordinate, session);
         boolean heldRemoved = removeHeldSkill(subordinate, session);
         if (artifactsRemoved && heldRemoved) {
            if (ACTIVE_CASTS.remove(subordinate, session)) {
               scheduleNextAttempt(subordinate, gameTime);
            }
         } else {
            deferCleanup(subordinate, session, gameTime);
         }
      } else {
         SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(subordinate);
         if (cleanup != null) {
            boolean artifactsRemoved = cancelSkillArtifacts(subordinate, cleanup.session);
            boolean heldRemoved = removeHeldSkill(subordinate, cleanup.session);
            if (!artifactsRemoved || !heldRemoved) {
               CLEANUP_CASTS.replace(subordinate, cleanup, new SubordinateSkillAiController.CleanupCast(cleanup.session, gameTime + 10L));
            } else if (CLEANUP_CASTS.remove(subordinate, cleanup)) {
               scheduleNextAttempt(subordinate, gameTime);
            }
         }
      }
   }

   public static void terminate(LivingEntity subordinate) {
      SubordinateSkillAiController.CastSession active = ACTIVE_CASTS.get(subordinate);
      SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(subordinate);
      if (active != null) {
         cancelSkillArtifacts(subordinate, active);
         removeHeldSkill(subordinate, active);
      }

      if (cleanup != null && cleanup.session != active) {
         cancelSkillArtifacts(subordinate, cleanup.session);
         removeHeldSkill(subordinate, cleanup.session);
      }

      ACTIVE_CASTS.remove(subordinate);
      CLEANUP_CASTS.remove(subordinate);
      NEXT_ATTEMPT.remove(subordinate);
      LAST_SKILL.remove(subordinate);
   }

   private static boolean tickActiveCast(
      Mob subordinate, @Nullable LivingEntity target, @Nullable UUID ownerId, boolean combatAllowed, SubordinateSkillAiController.CastSession session
   ) {
      if (!combatAllowed || ownerId == null || target == null || !session.targetId.equals(target.getUUID()) || !isValidTarget(subordinate, target, ownerId)) {
         cancel(subordinate);
         return false;
      }

      if (!matchesEffectiveSnapshot(session)) {
         cancel(subordinate);
         return false;
      }

      SkillStorage storage;
      try {
         storage = SkillAPI.getSkillsFrom(subordinate);
      } catch (RuntimeException | LinkageError error) {
         deferCleanup(subordinate, session, subordinate.level().getGameTime());
         return false;
      }

      TickingSkill ticking = findTickingSkill(storage, session);
      if (ticking == null) {
         if (removeHeldSkill(subordinate, session, storage)) {
            completeSession(subordinate, session);
         } else {
            deferCleanup(subordinate, session, subordinate.level().getGameTime());
         }

         return false;
      } else {
         aimForCast(subordinate, target, session.selfCast);
         subordinate.getNavigation().stop();
         long elapsed = subordinate.level().getGameTime() - session.startedAt;
         if (elapsed > session.holdTicks + 40L) {
            cancel(subordinate);
            return false;
         }

         if (ticking.getDuration() < session.holdTicks) {
            return true;
         }

         releaseSession(subordinate, storage, session);
         return true;
      }
   }

   private static boolean tryStartCast(Mob subordinate, LivingEntity target, SkillStorage storage, long gameTime) {
      List<SubordinateSkillAiController.CastCandidate> candidates = collectCandidates(subordinate, target, storage);
      if (candidates.isEmpty()) {
         return false;
      }

      SubordinateSkillAiController.CastKey previous = LAST_SKILL.get(subordinate);
      int offset = subordinate.getRandom().nextInt(candidates.size());

      for (int i = 0; i < candidates.size(); i++) {
         SubordinateSkillAiController.CastCandidate candidate = candidates.get((offset + i) % candidates.size());
         if ((candidates.size() <= 1 || !candidate.key.equals(previous)) && startCandidate(subordinate, target, storage, gameTime, candidate)) {
            return true;
         }
      }

      if (previous != null) {
         for (SubordinateSkillAiController.CastCandidate candidate : candidates) {
            if (candidate.key.equals(previous) && startCandidate(subordinate, target, storage, gameTime, candidate)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean startCandidate(
      Mob subordinate, LivingEntity target, SkillStorage storage, long gameTime, SubordinateSkillAiController.CastCandidate candidate
   ) {
      aimForCast(subordinate, target, candidate.selfCast);
      SubordinateSkillAiController.CastSession provisional = new SubordinateSkillAiController.CastSession(
         candidate.instance, target.getUUID(), candidate.mode, 0, candidate.holdTicks, gameTime, candidate.effective, candidate.key.skillId, candidate.selfCast
      );
      ACTIVE_CASTS.put(subordinate, provisional);

      try {
         if (!canActivateSkill(candidate.instance, subordinate, candidate.mode)) {
            ACTIVE_CASTS.remove(subordinate, provisional);
            return false;
         }

         if (!storage.startHoldSkill(candidate.instance, 0, candidate.mode)) {
            ACTIVE_CASTS.remove(subordinate, provisional);
            return false;
         }

         List<TickingSkill> held = storage.getHeldSkills();
         if (held.isEmpty()) {
            boolean artifactsRemoved = cancelSkillArtifacts(subordinate, provisional);
            boolean heldRemoved = removeHeldSkill(subordinate, provisional, storage);
            if (artifactsRemoved && heldRemoved) {
               ACTIVE_CASTS.remove(subordinate, provisional);
               return false;
            } else {
               deferCleanup(subordinate, provisional, gameTime);
               return true;
            }
         } else {
            TickingSkill ticking = held.get(held.size() - 1);
            ManasSkillInstance actual = storage.getSkill(ticking.getSkill()).orElse(candidate.instance);
            SubordinateSkillAiController.EffectiveCast fallbackEffective = actual == candidate.instance
               ? candidate.effective
               : new SubordinateSkillAiController.EffectiveCast(actual, ticking.getMode());
            ResourceLocation fallbackId = actual == candidate.instance ? candidate.key.skillId : actual.getSkillId();
            SubordinateSkillAiController.CastSession cleanupSession = new SubordinateSkillAiController.CastSession(
               actual,
               target.getUUID(),
               ticking.getMode(),
               ticking.getKeyNumber(),
               1,
               gameTime,
               fallbackEffective,
               fallbackId,
               isSelfCast(fallbackEffective, fallbackId)
            );
            ACTIVE_CASTS.put(subordinate, cleanupSession);
            SubordinateSkillAiController.EffectiveCast effective = resolveEffectiveCast(actual, ticking.getMode());
            ResourceLocation effectiveId = effective == null ? null : effective.instance.getSkillId();
            SubordinateSkillAiController.CastSession session = new SubordinateSkillAiController.CastSession(
               actual,
               target.getUUID(),
               ticking.getMode(),
               ticking.getKeyNumber(),
               effective == null ? 1 : determineHoldTicks(effective, subordinate),
               gameTime,
               effective == null ? fallbackEffective : effective,
               effectiveId == null ? fallbackId : effectiveId,
               effectiveId != null && isSelfCast(effective, effectiveId)
            );
            ACTIVE_CASTS.put(subordinate, session);
            if (effective != null
               && effectiveId != null
               && !(effective.instance.getMastery() < 0.0)
               && isEffectiveModeLearned(effective, subordinate)
               && !isExcluded(effective.instance, effectiveId)
               && !isExcludedMode(effective.instance, effective.mode)
               && callbackProfile(effective.instance.getSkill()).hasActiveEffect()) {
               LAST_SKILL.put(subordinate, new SubordinateSkillAiController.CastKey(effectiveId, effective.mode));
               if (session.holdTicks == 0) {
                  aimForCast(subordinate, target, session.selfCast);
                  releaseSession(subordinate, storage, session);
               }

               return true;
            } else {
               boolean artifactsRemoved = cancelSkillArtifacts(subordinate, session);
               boolean heldRemoved = removeHeldSkill(subordinate, session, storage);
               if (artifactsRemoved && heldRemoved) {
                  ACTIVE_CASTS.remove(subordinate, session);
                  return false;
               } else {
                  deferCleanup(subordinate, session, gameTime);
                  return true;
               }
            }
         }
      } catch (RuntimeException | LinkageError error) {
         markBroken(candidate.instance, "activation", error);
         SubordinateSkillAiController.CastSession session = ACTIVE_CASTS.get(subordinate);
         if (session != null) {
            boolean artifactsRemoved = cancelSkillArtifacts(subordinate, session);
            boolean heldRemoved = removeHeldSkill(subordinate, session, storage);
            if (artifactsRemoved && heldRemoved) {
               ACTIVE_CASTS.remove(subordinate, session);
            } else {
               deferCleanup(subordinate, session, gameTime);
            }
         }

         return ACTIVE_CASTS.containsKey(subordinate) || CLEANUP_CASTS.containsKey(subordinate);
      }
   }

   private static List<SubordinateSkillAiController.CastCandidate> collectCandidates(Mob subordinate, LivingEntity target, SkillStorage storage) {
      List<SubordinateSkillAiController.CastCandidate> candidates = new ArrayList<>();

      for (ManasSkillInstance instance : List.copyOf(storage.getLearnedSkills())) {
         ResourceLocation id;
         try {
            id = instance.getSkillId();
         } catch (RuntimeException | LinkageError error) {
            continue;
         }

         if (id != null && !BROKEN_SKILLS.contains(id) && !(instance.getMastery() < 0.0) && !isExcluded(instance, id)) {
            for (int mode : collectUsableModes(subordinate, instance)) {
               try {
                  SubordinateSkillAiController.EffectiveCast effective = resolveEffectiveCast(instance, mode);
                  if (effective != null && !(effective.instance.getMastery() < 0.0) && isEffectiveModeLearned(effective, subordinate)) {
                     ResourceLocation effectiveId = effective.instance.getSkillId();
                     if (effectiveId != null
                        && !BROKEN_SKILLS.contains(effectiveId)
                        && !isExcluded(effective.instance, effectiveId)
                        && !isExcludedMode(effective.instance, effective.mode)
                        && isUsefulCurrentState(effective.instance, effectiveId, effective.mode, subordinate, target)
                        && callbackProfile(effective.instance.getSkill()).hasActiveEffect()
                        && (!instance.onCoolDown(mode) || instance.canIgnoreCoolDown(subordinate, mode))) {
                        candidates.add(
                           new SubordinateSkillAiController.CastCandidate(
                              instance,
                              mode,
                              effective,
                              determineHoldTicks(effective, subordinate),
                              new SubordinateSkillAiController.CastKey(effectiveId, effective.mode),
                              isSelfCast(effective, effectiveId)
                           )
                        );
                     }
                  }
               } catch (RuntimeException | LinkageError error) {
                  markBroken(instance, "eligibility check", error);
                  break;
               }
            }
         }
      }

      return candidates;
   }

   private static List<Integer> collectUsableModes(LivingEntity entity, ManasSkillInstance instance) {
      int modeCount;
      try {
         modeCount = Mth.clamp(instance.getModes(), 0, 32);
      } catch (RuntimeException | LinkageError error) {
         return List.of();
      }

      if (modeCount <= 0) {
         return List.of();
      }

      if (instance.getSkill() instanceof TensuraSkill tensuraSkill) {
         ArrayList var13 = new ArrayList();
         HashSet var14 = new HashSet();
         int current = 0;

         for (int i = 0; i < modeCount && current >= 0 && current < modeCount && var14.add(current); i++) {
            if (isModeLearned(tensuraSkill, instance, entity, current)) {
               var13.add(current);
            }

            int next;
            try {
               next = tensuraSkill.nextMode(entity, instance, current, false);
            } catch (RuntimeException | LinkageError error) {
               break;
            }

            if (next == current) {
               break;
            }

            current = next;
         }

         return var13;
      } else {
         List<Integer> all = new ArrayList<>(modeCount);

         for (int mode = 0; mode < modeCount; mode++) {
            all.add(mode);
         }

         return all;
      }
   }

   private static boolean isModeLearned(TensuraSkill skill, ManasSkillInstance instance, LivingEntity entity, int mode) {
      try {
         if (!skill.getModeLearningList(instance).contains(mode)) {
            return true;
         }

         if (instance.getTag() == null) {
            return false;
         }

         double required = skill.getLearningPointRequirement(instance, entity, mode);
         if (skill instanceof BlackFlameSkill && mode == 2) {
            required = TensuraSkill.BASE_CONFIG.Learning.learningPointRequirement * 2.0;
         }

         return instance.getTag().getDouble(skill.getModeId(instance, mode)) >= required;
      } catch (RuntimeException | LinkageError error) {
         return false;
      }
   }

   private static boolean isEffectiveModeLearned(SubordinateSkillAiController.EffectiveCast effective, LivingEntity entity) {
      return effective.instance.getSkill() instanceof TensuraSkill skill ? isModeLearned(skill, effective.instance, entity, effective.mode) : true;
   }

   private static int determineHoldTicks(SubordinateSkillAiController.EffectiveCast effective, LivingEntity entity) {
      ManasSkillInstance instance = effective.instance;
      ManasSkill skill = instance.getSkill();
      int mode = effective.mode;

      try {
         AspectualMagicConfig aspectual = (AspectualMagicConfig)ConfigRegistry.getConfig(AspectualMagicConfig.class);
         if (skill instanceof ExplosionMagic) {
            int levels = mode == 1
               ? aspectual.Explosion.maxLevelTrap
               : (instance.isMastered(entity) ? aspectual.Explosion.maxLevelMastered : aspectual.Explosion.maxLevel);
            long powerStep = Math.max(1L, aspectual.Explosion.castTimeLevel / 10L);
            return clampHoldTicks(powerStep * Math.max(1, levels) * 10L + 1L);
         }

         if (skill instanceof FireBallMagic fireBall && instance.isMastered(entity)) {
            return clampHoldTicks(fireBall.getCastingTime(instance, entity, aspectual.FireBall.castTime, false));
         }

         if (skill instanceof IceBlizzardMagic iceBlizzard) {
            int castingTicks = iceBlizzard.getCastingTime(instance, entity);
            int stages = instance.isMastered(entity) ? aspectual.IceBlizzard.maxHoldMastered : aspectual.IceBlizzard.maxHold;
            return clampHoldTicks(castingTicks + (long)aspectual.IceBlizzard.castTimeHold * Math.max(0, stages - 1) + 1L);
         }
      } catch (RuntimeException | LinkageError var11) {
      }

      try {
         BattlewillConfig battlewill = (BattlewillConfig)ConfigRegistry.getConfig(BattlewillConfig.class);
         if (skill instanceof MagicBulletArt) {
            return chargedThreshold(instance, entity, battlewill.MagicBullet.holdTime, battlewill.MagicBullet.holdTimeMastered);
         }

         if (skill instanceof MaximumMagicBulletArt) {
            return chargedThreshold(instance, entity, battlewill.MaximumMagicBullet.holdTime, battlewill.MaximumMagicBullet.holdTimeMastered);
         }

         if (skill instanceof OgreSwordCannonArt) {
            return chargedThreshold(instance, entity, battlewill.OgreSwordCannon.holdTime, battlewill.OgreSwordCannon.holdTimeMastered);
         }

         if (skill instanceof DeathMarchDanceArt) {
            return chargedThreshold(instance, entity, battlewill.DeathMarchDance.holdTime, battlewill.DeathMarchDance.holdTimeMastered);
         }

         if (skill instanceof DarkEightPalmsArt) {
            return chargedThreshold(instance, entity, battlewill.DarkEightPalms.holdTime, battlewill.DarkEightPalms.holdTimeMastered);
         }

         if (skill instanceof FivePetalsThrustArt) {
            return clampHoldTicks(battlewill.FivePetalsThrust.chargeTick);
         }

         if (skill instanceof EightPetalsFlashArt) {
            return clampHoldTicks(battlewill.EightPetalsFlash.chargeTick);
         }

         if (skill instanceof ElephantStampedeArt) {
            return clampHoldTicks(Math.max(40L, battlewill.ElephantStampede.holdTime + 1L));
         }

         if (skill instanceof OgreFlameArt) {
            return clampHoldTicks(battlewill.OgreFlame.castTime + 40L);
         }

         if (skill instanceof ViolentBreakArt) {
            return clampHoldTicks(battlewill.ViolentBreak.holdTime + 1L);
         }

         UniqueSkillConfig unique = (UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class);
         if (skill instanceof BerserkerSkill) {
            return clampHoldTicks(unique.Berserker.holdTime + 1L);
         }

         if (skill instanceof WrathSkill && mode == 0) {
            return 101;
         }

         if (skill instanceof LustSkill && mode == 4) {
            return clampHoldTicks(unique.Lust.blessTime + 1L);
         }

         if (skill instanceof GreedSkill && mode == 2) {
            return clampHoldTicks(unique.Greed.deathTime * 20L + 1L);
         }

         if (skill instanceof SpatialDominationSkill && mode == 2) {
            ExtraSkillConfig extra = (ExtraSkillConfig)ConfigRegistry.getConfig(ExtraSkillConfig.class);
            return clampHoldTicks((long)Math.floor(extra.SpatialManipulation.rayDuration) + 2L);
         }
      } catch (RuntimeException | LinkageError var10) {
      }

      SubordinateSkillAiController.CallbackProfile profile = callbackProfile(skill);
      if (skill instanceof Magic magic) {
         try {
            int castingTicks = Mth.clamp(magic.getCastingTime(instance, entity), 1, 600);
            return profile.heldEffect ? clampHoldTicks(castingTicks + 40L) : castingTicks;
         } catch (RuntimeException | LinkageError ignored) {
            return Math.min(40, 600);
         }
      } else if (profile.release && !profile.heldEffect) {
         return 0;
      } else if (profile.release) {
         return 30;
      } else {
         return profile.heldEffect ? 40 : 1;
      }
   }

   private static int chargedThreshold(ManasSkillInstance instance, LivingEntity entity, int normalTicks, int masteredTicks) {
      int threshold = instance.isMastered(entity) ? masteredTicks : normalTicks;
      return clampHoldTicks(threshold + 1L);
   }

   private static int clampHoldTicks(long ticks) {
      return (int)Math.max(1L, Math.min(600L, ticks));
   }

   @Nullable
   private static SubordinateSkillAiController.EffectiveCast resolveEffectiveCast(ManasSkillInstance instance, int mode) {
      ManasSkillInstance currentInstance = instance;
      int currentMode = mode;
      Set<ManasSkill> visited = new HashSet<>();
      int depth = 0;

      while (depth < 8) {
         ManasSkill currentSkill = currentInstance.getSkill();
         if (!(currentSkill instanceof ISubAbilityModeHolder holder)) {
            return new SubordinateSkillAiController.EffectiveCast(currentInstance, currentMode);
         }

         if (!visited.add(currentSkill)) {
            return null;
         }

         int index = currentMode - holder.getSubModeOffset(currentInstance);
         List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(currentInstance);
         if (index >= 0 && index < slots.size()) {
            AbilitySlot slot = slots.get(index);
            ManasSkill subSkill = slot.getSkill();
            if (subSkill != null && slot.getMode() >= 0) {
               ManasSkillInstance subInstance = (ManasSkillInstance)currentInstance.getSubInstances().get(subSkill);
               if (subInstance != null && slot.getMode() < subInstance.getModes()) {
                  currentInstance = subInstance;
                  currentMode = slot.getMode();
                  depth++;
                  continue;
               }

               return null;
            }

            return null;
         }

         return null;
      }

      return null;
   }

   private static boolean matchesEffectiveSnapshot(SubordinateSkillAiController.CastSession session) {
      try {
         SubordinateSkillAiController.EffectiveCast current = resolveEffectiveCast(session.instance, session.mode);
         return current != null
            && current.instance == session.effectiveInstance
            && current.mode == session.effectiveMode
            && Objects.equals(current.instance.getSkillId(), session.effectiveSkillId);
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "sub-skill snapshot check", error);
         return false;
      }
   }

   private static boolean isSelfCast(SubordinateSkillAiController.EffectiveCast effective, ResourceLocation effectiveId) {
      if (SELF_CAST_SKILL_PATHS.contains(effectiveId.getPath().toLowerCase(Locale.ROOT))) {
         return true;
      } else if (!(effective.instance.getSkill() instanceof TensuraSkill skill)) {
         return false;
      } else {
         String modeId = skill.getModeId(effective.instance, effective.mode);
         return modeId != null && SELF_CAST_MODE_IDS.contains(modeId.toLowerCase(Locale.ROOT));
      }
   }

   private static boolean canActivateSkill(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (CAN_ACTIVATE_SKILL.method != null) {
         try {
            if (CAN_ACTIVATE_SKILL.method.invoke(instance, entity, mode) instanceof Boolean allowed) {
               return allowed;
            } else {
               throw new IllegalStateException("ManasCore canActivateSkill returned a non-boolean value");
            }
         } catch (InvocationTargetException error) {
            Throwable cause = error.getCause();
            if (cause instanceof RuntimeException runtime) {
               throw runtime;
            } else if (cause instanceof LinkageError linkage) {
               throw linkage;
            } else {
               throw new IllegalStateException("ManasCore canActivateSkill invocation failed", cause);
            }
         } catch (IllegalAccessException | IllegalArgumentException error) {
            throw new IllegalStateException("Cannot invoke ManasCore canActivateSkill", error);
         }
      } else {
         return CAN_ACTIVATE_SKILL.legacyFallback && instance.canInteractSkill(entity);
      }
   }

   private static SubordinateSkillAiController.ActivationMethod findCanActivateSkillMethod() {
      try {
         Method method = ManasSkillInstance.class.getMethod("canActivateSkill", LivingEntity.class, int.class);
         if (method.getReturnType() != boolean.class && method.getReturnType() != Boolean.class) {
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Unsupported ManasCore canActivateSkill return type: {}", method.getReturnType().getName());
            return SubordinateSkillAiController.ActivationMethod.UNAVAILABLE;
         } else {
            return new SubordinateSkillAiController.ActivationMethod(method, false);
         }
      } catch (NoSuchMethodException ignored) {
         return SubordinateSkillAiController.ActivationMethod.LEGACY;
      } catch (SecurityException | LinkageError error) {
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] Cannot inspect ManasCore canActivateSkill; subordinate activation disabled", error);
         return SubordinateSkillAiController.ActivationMethod.UNAVAILABLE;
      }
   }

   private static boolean isExcluded(ManasSkillInstance instance, ResourceLocation id) {
      try {
         if (instance.is(EXCLUDED_SKILLS)) {
            return true;
         }
      } catch (RuntimeException | LinkageError var4) {
      }

      String path = id.getPath().toLowerCase(Locale.ROOT);
      if (SubordinateClonePolicy.isCloningSkill(path)) {
         return true;
      } else if (UNSAFE_EXACT_PATHS.contains(path)) {
         return true;
      } else if (!path.contains("inventory")
         && !path.contains("storage")
         && !path.contains("reincarnat")
         && !path.contains("teleport")
         && !path.contains("portal")
         && !path.contains("summon")
         && !path.contains("doppelganger")
         && !path.contains("clairvoyance")) {
         String className = instance.getSkill().getClass().getName().toLowerCase(Locale.ROOT);
         return className.contains(".client.") || className.contains("screen") || className.contains("menu") || className.contains("container");
      } else {
         return true;
      }
   }

   private static boolean isExcludedMode(ManasSkillInstance instance, int mode) {
      if (!(instance.getSkill() instanceof TensuraSkill skill)) {
         return false;
      } else {
         String modeId = skill.getModeId(instance, mode);
         return SubordinateClonePolicy.isCloningMode(modeId) || modeId != null && UNSAFE_MODE_IDS.contains(modeId.toLowerCase(Locale.ROOT));
      }
   }

   private static boolean isUsefulCurrentState(ManasSkillInstance instance, ResourceLocation id, int mode, LivingEntity entity, @Nullable LivingEntity target) {
      String path = id.getPath().toLowerCase(Locale.ROOT);
      if ("healing".equals(path) || "recovery".equals(path) || "full_recovery".equals(path)) {
         return entity.getHealth() < entity.getMaxHealth();
      } else if ("antidote".equals(path)) {
         return entity.hasEffect(MobEffects.CONFUSION)
            || entity.hasEffect(MobEffects.POISON)
            || entity.hasEffect(MobEffects.WEAKNESS)
            || instance.isMastered(entity) && entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON));
      } else if ("body_armor".equals(path)) {
         return entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()
            || entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()
            || entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
            || entity.getItemBySlot(EquipmentSlot.FEET).isEmpty();
      } else if ("berserker".equals(path)) {
         AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
         AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
         return (armor == null || armor.getModifier(BERSERKER_MODIFIER) == null) && (damage == null || damage.getModifier(BERSERKER_MODIFIER) == null);
      } else if ("cook".equals(path) && mode == 0) {
         return instance.getTag() == null || !instance.getTag().getBoolean("ChaoticFateActivated");
      } else if ("magic_aura".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA));
      } else if ("aura_sword".equals(path)) {
         return !instance.isToggled() && !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD));
      } else if ("ogre_sword_guillotine".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.OGRE_GUILLOTINE));
      } else if ("strength".equals(path) || "steel_strength".equals(path)) {
         return !instance.isToggled() && !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN));
      } else if ("strength_aspectual".equals(path)) {
         return !entity.hasEffect(MobEffects.DAMAGE_BOOST);
      } else if ("agility".equals(path)) {
         return !entity.hasEffect(MobEffects.DIG_SPEED);
      } else if ("lighten".equals(path)) {
         return !entity.hasEffect(MobEffects.JUMP);
      } else if ("protection".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.PROTECTION));
      } else if ("reinforcement".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT));
      } else if ("wind_protection".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.WIND_PROTECTION));
      } else if ("diamond_path".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.DIAMOND_PATH));
      } else if ("beast_transformation".equals(path) || "royal_beast".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.BEAST_TRANSFORMATION));
      } else if ("ogre_berserker".equals(path) || "divine_berserker".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.OGRE_BERSERKER));
      } else if ("dragon_mode".equals(path)) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.DRAGON_MODE));
      } else if (path.startsWith("magic_") && path.endsWith("_transform")) {
         return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_ELEMENTAL_TRANSFORMATION));
      } else if ("multilayer_barrier".equals(path)) {
         AttributeInstance barrier = entity.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
         return barrier == null || barrier.getModifier(ResourceLocation.fromNamespaceAndPath("tensura", "multilayer_barrier")) == null;
      } else {
         String modeId = instance.getSkill() instanceof TensuraSkill skill ? skill.getModeId(instance, mode) : null;
         if ("infinity_prison.imprison".equals(modeId)) {
            return target != null && !target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT));
         } else if ("healer.virus".equals(modeId)) {
            return target != null && !target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION));
         } else if ("sniper.spatial".equals(modeId) || "spatial_domination.warp_shot".equals(modeId)) {
            AttributeInstance warpShot = entity.getAttribute(TensuraAttributes.WARP_SHOT);
            ResourceLocation modifier = ResourceLocation.fromNamespaceAndPath("tensura", path);
            return warpShot != null && warpShot.getModifier(modifier) == null;
         } else if ("guardian.iron_wall".equals(modeId)) {
            AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
            return armor == null || armor.getModifier(ResourceLocation.fromNamespaceAndPath("tensura", "guardian")) == null;
         } else if ("earth_lock.self".equals(modeId)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.EARTH_LOCK));
         } else if ("haki.coat".equals(modeId)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT));
         } else if ("absolute_severance.coat".equals(modeId)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE));
         } else if ("berserk.rage".equals(modeId)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN));
         } else if ("berserk.mad_ogre".equals(modeId) && entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAD_OGRE))) {
            return instance.isMastered(entity);
         } else if ("falsifier.concealment".equals(modeId)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FALSIFIER));
         } else if ("seer".equals(path)) {
            return !entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FUTURE_VISION));
         } else if (!"murderer".equals(path)) {
            return "haze".equals(path) ? !instance.isToggled() : true;
         } else {
            return instance.getTag() == null || !instance.getTag().getBoolean("Concealing");
         }
      }
   }

   private static SubordinateSkillAiController.CallbackProfile callbackProfile(ManasSkill skill) {
      return CALLBACK_CACHE.computeIfAbsent(skill.getClass(), SubordinateSkillAiController::inspectCallbacks);
   }

   private static SubordinateSkillAiController.CallbackProfile inspectCallbacks(Class<?> type) {
      try {
         boolean pressed = declaringClass(type, "onPressed", ManasSkillInstance.class, LivingEntity.class, int.class, int.class) != ManasSkill.class;
         Class<?> heldOwner = declaringClass(type, "onHeld", ManasSkillInstance.class, LivingEntity.class, int.class, int.class);
         boolean heldEffect = heldOwner != ManasSkill.class && heldOwner != Magic.class;
         boolean release = declaringClass(type, "onRelease", ManasSkillInstance.class, LivingEntity.class, int.class, int.class, int.class) != ManasSkill.class;
         return new SubordinateSkillAiController.CallbackProfile(pressed, heldEffect, release);
      } catch (ReflectiveOperationException | LinkageError error) {
         return SubordinateSkillAiController.CallbackProfile.NONE;
      }
   }

   private static Class<?> declaringClass(Class<?> type, String name, Class<?>... parameters) throws ReflectiveOperationException {
      Method method = type.getMethod(name, parameters);
      return method.getDeclaringClass();
   }

   @Nullable
   private static TickingSkill findTickingSkill(SkillStorage storage, SubordinateSkillAiController.CastSession session) {
      for (TickingSkill ticking : storage.getHeldSkills()) {
         if (ticking.matches(session.instance.getSkill(), session.mode, session.keyNumber)) {
            return ticking;
         }
      }

      return null;
   }

   private static void releaseSession(LivingEntity subordinate, SkillStorage storage, SubordinateSkillAiController.CastSession session) {
      boolean needsCleanup = false;

      try {
         storage.handleSkillRelease(session.instance, session.keyNumber, session.mode, false);
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "release", error);
         needsCleanup = true;
      }

      try {
         needsCleanup = findTickingSkill(storage, session) != null || needsCleanup;
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "release verification", error);
         needsCleanup = true;
      }

      if (needsCleanup) {
         boolean artifactsRemoved = cancelSkillArtifacts(subordinate, session);
         boolean heldRemoved = removeHeldSkill(subordinate, session, storage);
         if (!artifactsRemoved || !heldRemoved) {
            deferCleanup(subordinate, session, subordinate.level().getGameTime());
            return;
         }
      }

      completeSession(subordinate, session);
   }

   private static boolean removeHeldSkill(LivingEntity subordinate, SubordinateSkillAiController.CastSession session) {
      SkillStorage storage;
      try {
         storage = SkillAPI.getSkillsFrom(subordinate);
      } catch (RuntimeException | LinkageError error) {
         return false;
      }

      return removeHeldSkill(subordinate, session, storage);
   }

   private static boolean removeHeldSkill(LivingEntity subordinate, SubordinateSkillAiController.CastSession session, SkillStorage storage) {
      boolean removed = false;

      try {
         for (TickingSkill ticking : List.copyOf(storage.getHeldSkills())) {
            if (ticking.matches(session.instance.getSkill(), session.mode, session.keyNumber)) {
               storage.getHeldSkills().remove(ticking);
               removed = true;
            }
         }
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "cancellation", error);
         return false;
      }

      try {
         removeCastAttributeModifiers(subordinate, session);
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "attribute cleanup", error);
         return false;
      }

      if (removed) {
         try {
            storage.checkAndMarkDirty(session.instance);
         } catch (RuntimeException | LinkageError error) {
            markBroken(session.instance, "cancellation sync", error);
         }
      }

      return true;
   }

   private static boolean cancelSkillArtifacts(LivingEntity subordinate, SubordinateSkillAiController.CastSession session) {
      try {
         discardTaggedProjectiles(subordinate, session.instance);
         if (session.effectiveInstance != session.instance) {
            discardTaggedProjectiles(subordinate, session.effectiveInstance);
         }

         ManasSkill skill = session.effectiveInstance.getSkill();
         if (skill instanceof GreedSkill && session.effectiveMode == 2) {
            CompoundTag tag = session.effectiveInstance.getOrCreateTag();
            tag.putInt("targetDesire", 0);
            tag.remove("target");
            tag.putInt("heldSeconds", 0);
            session.effectiveInstance.markDirty();
         }

         return true;
      } catch (RuntimeException | LinkageError error) {
         markBroken(session.instance, "cast artifact cleanup", error);
         return false;
      }
   }

   private static void discardTaggedProjectiles(LivingEntity subordinate, ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         boolean changed = false;

         for (String tagKey : List.copyOf(tag.getAllKeys())) {
            if (isCastArtifactTag(tagKey)) {
               int entityId = tag.getInt(tagKey);
               if (entityId != 0) {
                  Entity artifact = subordinate.level().getEntity(entityId);
                  if (artifact == null) {
                     tag.putInt(tagKey, 0);
                     changed = true;
                  } else if (artifact instanceof Projectile projectile && projectile.getOwner() == subordinate) {
                     projectile.discard();
                     tag.putInt(tagKey, 0);
                     changed = true;
                  }
               }
            }
         }

         if (changed) {
            instance.markDirty();
         }
      }
   }

   private static boolean isCastArtifactTag(String tagKey) {
      for (String prefix : CAST_ARTIFACT_TAG_PREFIXES) {
         if (tagKey.equals(prefix)) {
            return true;
         }

         if (tagKey.startsWith(prefix)) {
            String suffix = tagKey.substring(prefix.length());
            if (suffix.startsWith("_")) {
               suffix = suffix.substring(1);
            }

            if (!suffix.isEmpty() && suffix.chars().allMatch(Character::isDigit)) {
               return true;
            }
         }
      }

      return false;
   }

   private static void removeCastAttributeModifiers(LivingEntity subordinate, SubordinateSkillAiController.CastSession session) {
      try {
         session.instance.removeAttributeModifiers(subordinate, session.mode);
      } finally {
         if (session.effectiveInstance != session.instance) {
            session.effectiveInstance.removeAttributeModifiers(subordinate, session.effectiveMode);
         }
      }
   }

   private static void completeSession(LivingEntity subordinate, SubordinateSkillAiController.CastSession session) {
      if (ACTIVE_CASTS.remove(subordinate, session)) {
         scheduleNextAttempt(subordinate, subordinate.level().getGameTime());
      }
   }

   private static boolean retryDeferredCleanup(LivingEntity subordinate, long gameTime) {
      SubordinateSkillAiController.CleanupCast cleanup = CLEANUP_CASTS.get(subordinate);
      if (cleanup == null) {
         return false;
      }

      if (gameTime < cleanup.nextAttempt) {
         return true;
      }

      boolean artifactsRemoved = cancelSkillArtifacts(subordinate, cleanup.session);
      boolean heldRemoved = removeHeldSkill(subordinate, cleanup.session);
      if (!artifactsRemoved || !heldRemoved) {
         CLEANUP_CASTS.replace(subordinate, cleanup, new SubordinateSkillAiController.CleanupCast(cleanup.session, gameTime + 10L));
      } else if (CLEANUP_CASTS.remove(subordinate, cleanup)) {
         scheduleNextAttempt(subordinate, gameTime);
      }

      return true;
   }

   private static void deferCleanup(LivingEntity subordinate, SubordinateSkillAiController.CastSession session, long gameTime) {
      cancelSkillArtifacts(subordinate, session);
      ACTIVE_CASTS.remove(subordinate, session);
      CLEANUP_CASTS.put(subordinate, new SubordinateSkillAiController.CleanupCast(session, gameTime + 10L));
   }

   private static void scheduleNextAttempt(LivingEntity subordinate, long gameTime) {
      long delay = 20L;
      if (subordinate instanceof Mob mob) {
         delay += mob.getRandom().nextInt(21);
      }

      NEXT_ATTEMPT.put(subordinate, gameTime + delay);
   }

   private static void aimForCast(Mob subordinate, LivingEntity target, boolean selfCast) {
      if (selfCast) {
         aimDown(subordinate);
      } else {
         aimAt(subordinate, target);
      }
   }

   private static void aimDown(Mob subordinate) {
      float yaw = subordinate.getYRot();
      subordinate.getLookControl().setLookAt(subordinate.getX(), subordinate.getY() - 4.0, subordinate.getZ(), 360.0F, 360.0F);
      subordinate.setYRot(yaw);
      subordinate.setYHeadRot(yaw);
      subordinate.setYBodyRot(yaw);
      subordinate.setXRot(90.0F);
   }

   private static void aimAt(Mob subordinate, LivingEntity target) {
      double dx = target.getX() - subordinate.getX();
      double dz = target.getZ() - subordinate.getZ();
      double dy = target.getY() + target.getBbHeight() * 0.55 - subordinate.getEyeY();
      double horizontal = Math.sqrt(dx * dx + dz * dz);
      float yaw = (float)(Mth.atan2(dz, dx) * 180.0F / (float)Math.PI) - 90.0F;
      float pitch = (float)(-(Mth.atan2(dy, horizontal) * 180.0F / (float)Math.PI));
      subordinate.getLookControl().setLookAt(target, 360.0F, 360.0F);
      subordinate.setYRot(yaw);
      subordinate.setYHeadRot(yaw);
      subordinate.setYBodyRot(yaw);
      subordinate.setXRot(Mth.clamp(pitch, -90.0F, 90.0F));
   }

   private static void markBroken(ManasSkillInstance instance, String stage, Throwable error) {
      ResourceLocation id;
      try {
         id = instance.getSkillId();
      } catch (RuntimeException | LinkageError ignored) {
         return;
      }

      if (id != null && BROKEN_SKILLS.add(id)) {
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] Disabled subordinate AI use of skill {} after {} failed", new Object[]{id, stage, error});
      }
   }

   private record ActivationMethod(@Nullable Method method, boolean legacyFallback) {
      private static final SubordinateSkillAiController.ActivationMethod LEGACY = new SubordinateSkillAiController.ActivationMethod(null, true);
      private static final SubordinateSkillAiController.ActivationMethod UNAVAILABLE = new SubordinateSkillAiController.ActivationMethod(null, false);
   }

   private record CallbackProfile(boolean pressed, boolean heldEffect, boolean release) {
      private static final SubordinateSkillAiController.CallbackProfile NONE = new SubordinateSkillAiController.CallbackProfile(false, false, false);

      private boolean hasActiveEffect() {
         return this.pressed || this.heldEffect || this.release;
      }
   }

   private record CastCandidate(
      ManasSkillInstance instance,
      int mode,
      SubordinateSkillAiController.EffectiveCast effective,
      int holdTicks,
      SubordinateSkillAiController.CastKey key,
      boolean selfCast
   ) {
   }

   private record CastKey(ResourceLocation skillId, int mode) {
   }

   private static final class CastSession {
      private final ManasSkillInstance instance;
      private final UUID targetId;
      private final int mode;
      private final int keyNumber;
      private final int holdTicks;
      private final long startedAt;
      private final ManasSkillInstance effectiveInstance;
      private final int effectiveMode;
      private final ResourceLocation effectiveSkillId;
      private final boolean selfCast;

      private CastSession(
         ManasSkillInstance instance,
         UUID targetId,
         int mode,
         int keyNumber,
         int holdTicks,
         long startedAt,
         SubordinateSkillAiController.EffectiveCast effective,
         ResourceLocation effectiveSkillId,
         boolean selfCast
      ) {
         this.instance = instance;
         this.targetId = targetId;
         this.mode = mode;
         this.keyNumber = keyNumber;
         this.holdTicks = holdTicks;
         this.startedAt = startedAt;
         this.effectiveInstance = effective.instance;
         this.effectiveMode = effective.mode;
         this.effectiveSkillId = effectiveSkillId;
         this.selfCast = selfCast;
      }

      private boolean matches(ManasSkill skill, int checkedMode, int checkedKeyNumber) {
         return this.instance.getSkill() == skill && this.mode == checkedMode && this.keyNumber == checkedKeyNumber;
      }
   }

   private record CleanupCast(SubordinateSkillAiController.CastSession session, long nextAttempt) {
   }

   private record EffectiveCast(ManasSkillInstance instance, int mode) {
   }
}
