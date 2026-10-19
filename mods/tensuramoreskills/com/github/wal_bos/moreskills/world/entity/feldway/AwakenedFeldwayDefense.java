package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;

public final class AwakenedFeldwayDefense {
   private static final Map<UUID, AwakenedFeldwayDefense.GuardState> GUARDS = new ConcurrentHashMap<>();
   private static final float TRUE_DEATH_HP_PERCENT = ((Double)cfg().trueDeathHpPercent.get()).floatValue();
   private static final float TRUE_DEATH_GATE_BUFFER = ((Double)cfg().trueDeathGateBuffer.get()).floatValue();
   private static final float MAX_DAMAGE_PER_SECOND_PERCENT = ((Double)cfg().maxDamagePerSecondPercent.get()).floatValue();
   private static final float MAX_DAMAGE_PER_HIT_PERCENT = ((Double)cfg().maxDamagePerHitPercent.get()).floatValue();
   private static final float EMERGENCY_DAMAGE_PERCENT = ((Double)cfg().emergencyDamagePercent.get()).floatValue();
   private static final float SAFEGUARD_RECOVER_PERCENT = ((Double)cfg().safeguardRecoverPercent.get()).floatValue();
   private static final double MIN_AURA = (Double)cfg().minimumAura.get();
   private static final double MIN_MAGICULE = (Double)cfg().minimumMagicule.get();
   private static final double MIN_SPIRITUAL_HEALTH = (Double)cfg().minimumSpiritualHealth.get();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private AwakenedFeldwayDefense() {
   }

   public static void tick(FeldwayAwakenedEntity boss) {
      if (boss != null && !boss.level().isClientSide()) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         normalizeCapWindow(boss, guard);
         boss.invulnerableTime = 0;
         boss.hurtTime = 0;
         if (guard.finalDeathStarted) {
            boss.invulnerableTime = 0;
            boss.hurtTime = 0;
         } else {
            cleanseEffects(boss);
            guard.allowTrueDeath = false;
            if (isFinite(boss.getHealth()) && !(boss.getHealth() <= 0.0F)) {
               if (boss.getHealth() <= trueDeathGateHealth(boss)) {
                  armDeathGate(boss, guard);
                  setGuardedHealth(boss, guard, trueDeathGateHealth(boss));
                  boss.deathTime = 0;
                  boss.hurtTime = 0;
                  boss.invulnerableTime = 0;
               } else if (boss.getHealth() > trueDeathGateHealth(boss) + 2.0F) {
                  guard.deathGateArmed = false;
                  guard.deathGateArmedTick = -1;
               }

               enforceExistence(boss);
               if (guard.antiOneShotTicks > 0) {
                  guard.antiOneShotTicks--;
               }

               guard.capImmuneTicks = 0;
               guard.hardIFrameTicks = 0;
               refreshLastSafeHealth(boss, guard);
               if (boss.tickCount % 20 == 0) {
                  giveAndToggleShadeSkills(boss);
                  giveResistanceSkills(boss);
                  guard.lastSafeHealth = boss.getHealth();
                  guard.lastSafeHealthTick = boss.tickCount;
               }

               boss.clearFire();
            } else if (canOpenTrueDeath(boss, guard)) {
               beginFinalDeath(boss, guard);
            } else {
               triggerLethalSafeguard(boss, guard);
               boss.clearFire();
            }
         }
      }
   }

   public static boolean canBeAffected(FeldwayAwakenedEntity boss, MobEffectInstance instance) {
      return false;
   }

   public static boolean shouldCancelDamage(FeldwayAwakenedEntity boss, DamageSource source, float amount) {
      if (boss == null || boss.level().isClientSide()) {
         return true;
      } else if (source == null) {
         return true;
      } else if (boss.isRemoved()) {
         return true;
      } else if (!(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         return canOpenTrueDeath(boss, guard) ? false : isOwnDamage(boss, source);
      } else {
         return true;
      }
   }

   public static float modifyIncomingDamage(FeldwayAwakenedEntity boss, DamageSource source, float amount) {
      if (boss == null || boss.level().isClientSide()) {
         return 0.0F;
      }

      if (source == null) {
         return 0.0F;
      }

      if (!(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         normalizeCapWindow(boss, guard);
         refreshLastSafeHealth(boss, guard);
         float before = boss.getHealth();
         float gate = trueDeathGateHealth(boss);
         boss.invulnerableTime = 0;
         boss.hurtTime = 0;
         if (canOpenTrueDeath(boss, guard)) {
            beginFinalDeath(boss, guard);
            return Math.max(amount, gate + 10.0F);
         }

         guard.allowTrueDeath = false;
         if (isFinite(before) && !(before <= 0.0F)) {
            enforceExistence(boss);
            if (before <= gate) {
               armDeathGate(boss, guard);
               return 0.0F;
            }

            if (isDangerousBypassSource(source)) {
               amount = Math.min(amount, emergencyDamageCap(boss));
            }

            if (guard.antiOneShotTicks > 0) {
               amount *= 0.35F;
            }

            float remaining = perSecondCap(boss) - guard.capDamageTaken;
            if (remaining <= 0.0F) {
               return 0.0F;
            }

            float perHit = Math.min(maxDamagePerHit(boss), emergencyDamageCap(boss));
            amount = Math.min(amount, perHit);
            amount = Math.min(amount, remaining);
            if (before - amount <= gate) {
               amount = Math.max(0.0F, before - gate);
               armDeathGate(boss, guard);
            }

            return Math.max(0.0F, amount);
         } else {
            triggerLethalSafeguard(boss, guard);
            return 0.0F;
         }
      } else {
         return 0.0F;
      }
   }

   public static void afterDamage(FeldwayAwakenedEntity boss, float beforeHealth, float allowedDamage) {
      if (boss != null && !boss.level().isClientSide()) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         normalizeCapWindow(boss, guard);
         if (guard.finalDeathStarted) {
            boss.invulnerableTime = 0;
            boss.hurtTime = 0;
         } else {
            float after = boss.getHealth();
            float gate = trueDeathGateHealth(boss);
            float actualLoss = Math.max(0.0F, beforeHealth - after);
            boss.invulnerableTime = 0;
            boss.hurtTime = 0;
            if (canOpenTrueDeath(boss, guard) && after <= 0.0F) {
               beginFinalDeath(boss, guard);
            } else {
               guard.allowTrueDeath = false;
               if (isFinite(after) && !(after <= 0.0F)) {
                  float maxAllowedLoss = Math.min(Math.max(0.0F, allowedDamage), maxDamagePerHit(boss));
                  maxAllowedLoss = Math.min(maxAllowedLoss, Math.max(0.0F, perSecondCap(boss) - guard.capDamageTaken + actualLoss));
                  if (actualLoss > maxAllowedLoss) {
                     float fixed = Math.max(gate, beforeHealth - maxAllowedLoss);
                     setGuardedHealth(boss, guard, fixed);
                     after = boss.getHealth();
                     actualLoss = Math.max(0.0F, beforeHealth - after);
                  }

                  if (after <= gate) {
                     setGuardedHealth(boss, guard, gate);
                     boss.deathTime = 0;
                     boss.hurtTime = 0;
                     boss.invulnerableTime = 0;
                     armDeathGate(boss, guard);
                     actualLoss = Math.max(0.0F, beforeHealth - gate);
                  }

                  guard.capDamageTaken = guard.capDamageTaken + Math.max(0.0F, actualLoss);
                  if (guard.capDamageTaken > perSecondCap(boss)) {
                     guard.capDamageTaken = perSecondCap(boss);
                  }

                  enforceExistence(boss);
                  guard.lastSafeHealth = boss.getHealth();
                  guard.lastSafeHealthTick = boss.tickCount;
               } else {
                  triggerLethalSafeguard(boss, guard);
               }
            }
         }
      }
   }

   public static float guardSetHealth(FeldwayAwakenedEntity boss, float value) {
      if (boss == null || boss.level().isClientSide()) {
         return value;
      }

      if (!Float.isNaN(value) && !Float.isInfinite(value)) {
         if (value < 0.0F) {
            value = 0.0F;
         }

         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         if (guard.internalHealthSet) {
            return value;
         }

         if (guard.finalDeathStarted) {
            return value;
         }

         normalizeCapWindow(boss, guard);
         float before = boss.getHealth();
         float gate = trueDeathGateHealth(boss);
         if (canOpenTrueDeath(boss, guard)) {
            beginFinalDeath(boss, guard);
            return value;
         }

         guard.allowTrueDeath = false;
         if (!isFinite(before)) {
            return Math.max(oneShotGuardRecoverTo(boss), gate + 1.0F);
         }

         enforceExistence(boss);
         if (before <= 0.0F && value <= 0.0F) {
            return Math.max(gate, 1.0F);
         }

         if (value >= before) {
            if (value > gate + 2.0F) {
               guard.deathGateArmed = false;
               guard.deathGateArmedTick = -1;
            }

            return value;
         } else {
            float remaining = perSecondCap(boss) - guard.capDamageTaken;
            if (remaining <= 0.0F) {
               boss.deathTime = 0;
               boss.hurtTime = 0;
               boss.invulnerableTime = 0;
               return Math.max(before, gate);
            }

            float maxDrop = Math.min(maxDamagePerHit(boss), emergencyDamageCap(boss));
            maxDrop = Math.min(maxDrop, remaining);
            float wantedDrop = before - value;
            float actualDrop = Math.min(wantedDrop, maxDrop);
            float fixed = before - actualDrop;
            if (fixed <= gate) {
               fixed = gate;
               armDeathGate(boss, guard);
            }

            if (fixed <= 0.0F) {
               fixed = gate;
               armDeathGate(boss, guard);
            }

            float counted = Math.max(0.0F, before - fixed);
            guard.capDamageTaken += counted;
            if (guard.capDamageTaken > perSecondCap(boss)) {
               guard.capDamageTaken = perSecondCap(boss);
            }

            guard.antiOneShotTicks = Math.max(guard.antiOneShotTicks, 80);
            guard.lastSafeHealth = fixed;
            guard.lastSafeHealthTick = boss.tickCount;
            boss.deathTime = 0;
            boss.hurtTime = 0;
            boss.invulnerableTime = 0;
            return fixed;
         }
      } else {
         return Math.max(1.0F, boss.getHealth());
      }
   }

   public static boolean shouldStopDeath(FeldwayAwakenedEntity boss, DamageSource source) {
      if (boss != null && !boss.level().isClientSide()) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         if (canOpenTrueDeath(boss, guard)) {
            beginFinalDeath(boss, guard);
            return false;
         } else {
            triggerLethalSafeguard(boss, guard);
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean shouldBlockRemoval(FeldwayAwakenedEntity boss, RemovalReason reason) {
      if (boss == null || boss.level().isClientSide()) {
         return false;
      }

      if (reason != RemovalReason.UNLOADED_TO_CHUNK && reason != RemovalReason.UNLOADED_WITH_PLAYER && reason != RemovalReason.CHANGED_DIMENSION) {
         AwakenedFeldwayDefense.GuardState guard = guard(boss);
         if (guard.finalDeathStarted) {
            GUARDS.remove(boss.getUUID());
            return false;
         } else if (canOpenTrueDeath(boss, guard)) {
            beginFinalDeath(boss, guard);
            GUARDS.remove(boss.getUUID());
            return false;
         } else {
            triggerLethalSafeguard(boss, guard);
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean isInvulnerableTo(FeldwayAwakenedEntity boss, DamageSource source) {
      return false;
   }

   private static void giveAndToggleShadeSkills(FeldwayAwakenedEntity boss) {
      AwakenedFeldwayDefense.GuardState guard = guard(boss);
      if (!guard.finalDeathStarted && !canOpenTrueDeath(boss, guard)) {
         learnAndToggle(boss, (ManasSkill)ShadeSkills.NABERIUS.get());
         learnAndToggle(boss, (ManasSkill)ShadeSkills.ISTAROTH.get());
      }
   }

   private static void learnAndToggle(FeldwayAwakenedEntity boss, ManasSkill skill) {
      if (skill != null) {
         try {
            SkillHelper.learnSkill(boss, skill);
         } catch (Throwable var5) {
         }

         try {
            Skills skills = SkillAPI.getSkillsFrom(boss);
            ManasSkillInstance instance = (ManasSkillInstance)skills.getSkill(skill).orElse(null);
            if (instance == null) {
               return;
            }

            if (instance.getMastery() < 0.0) {
               instance.setMastery(0.0);
            }

            if (!instance.isToggled()) {
               instance.setToggled(true);
            }

            instance.markDirty();
            skills.markDirty();
         } catch (Throwable var4) {
         }
      }
   }

   private static void giveResistanceSkills(FeldwayAwakenedEntity boss) {
      AwakenedFeldwayDefense.GuardState guard = guard(boss);
      if (!guard.finalDeathStarted && !canOpenTrueDeath(boss, guard)) {
         learnSafe(boss, (ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.CORROSION_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.EARTH_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.ELECTRICITY_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.FLAME_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.HEAT_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.LIGHT_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.PAIN_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.PARALYSIS_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.PIERCE_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.POISON_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.WATER_ATTACK_NULLIFICATION.get());
         learnSafe(boss, (ManasSkill)ResistanceSkills.WIND_ATTACK_NULLIFICATION.get());
      }
   }

   private static void learnSafe(FeldwayAwakenedEntity boss, ManasSkill skill) {
      if (skill != null) {
         try {
            SkillHelper.learnSkill(boss, skill);
         } catch (Throwable var3) {
         }
      }
   }

   private static void enforceExistence(FeldwayAwakenedEntity boss) {
      AwakenedFeldwayDefense.GuardState guard = guard(boss);
      if (!guard.finalDeathStarted && !canOpenTrueDeath(boss, guard)) {
         try {
            IExistence ex = TensuraStorages.getExistenceFrom(boss);
            double minSpiritual = Math.max(MIN_SPIRITUAL_HEALTH, boss.getMaxHealth() * (Double)cfg().minimumSpiritualHealthMaxHealthMultiplier.get());
            ex.setAura(safeAtLeast(ex.getAura(), MIN_AURA));
            ex.setMagicule(safeAtLeast(ex.getMagicule(), MIN_MAGICULE));
            ex.setSpiritualHealth(safeAtLeast(ex.getSpiritualHealth(), minSpiritual));
            ex.markDirty();
         } catch (Throwable var5) {
         }
      }
   }

   private static void cleanseEffects(FeldwayAwakenedEntity boss) {
      try {
         boss.removeAllEffects();
      } catch (Throwable var3) {
      }

      try {
         TensuraStorages.resetEffect(boss);
      } catch (Throwable var2) {
      }

      boss.clearFire();
   }

   private static boolean isOwnDamage(FeldwayAwakenedEntity boss, DamageSource source) {
      try {
         AwakenedFeldwayDefense.EntitySourcePair pair = new AwakenedFeldwayDefense.EntitySourcePair(source.getEntity(), source.getDirectEntity());
         if (pair.attacker == boss || pair.direct == boss) {
            return true;
         }

         if (pair.attacker instanceof LivingEntity livingAttacker && (livingAttacker.isAlliedTo(boss) || boss.isAlliedTo(livingAttacker))) {
            return true;
         }

         if (pair.direct instanceof LivingEntity livingDirect && (livingDirect.isAlliedTo(boss) || boss.isAlliedTo(livingDirect))) {
            return true;
         }
      } catch (Throwable var5) {
      }

      return false;
   }

   private static boolean isDangerousBypassSource(DamageSource source) {
      try {
         String id = source.getMsgId();
         if (id == null) {
            return false;
         }

         String s = id.toLowerCase();
         return s.contains("out_of_world")
            || s.contains("void")
            || s.contains("true")
            || s.contains("kill")
            || s.contains("delete")
            || s.contains("erase")
            || s.contains("execute")
            || s.contains("remove")
            || s.contains("despawn")
            || s.contains("bypass")
            || s.contains("absolute")
            || s.contains("spiritual")
            || s.contains("death")
            || s.contains("severance")
            || s.contains("disintegration")
            || s.contains("existence");
      } catch (Throwable var3) {
         return false;
      }
   }

   private static void triggerLethalSafeguard(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      if (!guard.finalDeathStarted && !canOpenTrueDeath(boss, guard)) {
         float restore = Math.max(oneShotGuardRecoverTo(boss), trueDeathGateHealth(boss) + 1.0F);
         setGuardedHealth(boss, guard, Math.min(boss.getMaxHealth(), restore));
         boss.deathTime = 0;
         boss.hurtTime = 0;
         boss.invulnerableTime = 0;
         guard.capImmuneTicks = 0;
         guard.hardIFrameTicks = 0;
         guard.antiOneShotTicks = Math.max(guard.antiOneShotTicks, 100);
         guard.allowTrueDeath = false;
         guard.finalDeathStarted = false;
         guard.deathGateArmed = false;
         guard.deathGateArmedTick = -1;
         guard.lastSafeHealth = boss.getHealth();
         guard.lastSafeHealthTick = boss.tickCount;
         enforceExistence(boss);
         cleanseEffects(boss);
      } else {
         beginFinalDeath(boss, guard);
      }
   }

   private static void beginFinalDeath(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      guard.allowTrueDeath = true;
      guard.finalDeathStarted = true;
      guard.capImmuneTicks = 0;
      guard.hardIFrameTicks = 0;
      guard.antiOneShotTicks = 0;
      guard.capDamageTaken = 0.0F;
      boss.invulnerableTime = 0;
      boss.hurtTime = 0;
      boss.clearFire();

      try {
         boss.removeAllEffects();
      } catch (Throwable var3) {
      }
   }

   private static void refreshLastSafeHealth(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      if (boss.tickCount != guard.lastSafeHealthTick) {
         guard.lastSafeHealthTick = boss.tickCount;
         float hp = boss.getHealth();
         if (hp > 0.0F && isFinite(hp)) {
            guard.lastSafeHealth = hp;
         }
      }
   }

   private static void normalizeCapWindow(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      if (guard.capWindowLastTick != boss.tickCount) {
         guard.capWindowLastTick = boss.tickCount;
         if (guard.capWindowTicks > 0) {
            guard.capWindowTicks--;
         }

         if (guard.capWindowTicks <= 0) {
            guard.capWindowTicks = 20;
            guard.capDamageTaken = 0.0F;
         }
      }
   }

   private static void armDeathGate(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      if (!guard.deathGateArmed) {
         guard.deathGateArmed = true;
         guard.deathGateArmedTick = boss.tickCount;
      }
   }

   private static boolean canOpenTrueDeath(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard) {
      if (boss == null || guard == null) {
         return false;
      } else if (guard.finalDeathStarted) {
         return true;
      } else {
         return guard.allowTrueDeath
            ? true
            : boss.getHealth() <= trueDeathGateHealth(boss)
               && guard.deathGateArmed
               && guard.deathGateArmedTick >= 0
               && boss.tickCount > guard.deathGateArmedTick;
      }
   }

   private static void setGuardedHealth(FeldwayAwakenedEntity boss, AwakenedFeldwayDefense.GuardState guard, float health) {
      guard.internalHealthSet = true;

      try {
         boss.setHealth(Math.max(0.0F, health));
      } finally {
         guard.internalHealthSet = false;
      }
   }

   private static AwakenedFeldwayDefense.GuardState guard(FeldwayAwakenedEntity boss) {
      return GUARDS.computeIfAbsent(boss.getUUID(), id -> new AwakenedFeldwayDefense.GuardState(boss.getHealth(), boss.tickCount));
   }

   private static float trueDeathThresholdHealth(FeldwayAwakenedEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * TRUE_DEATH_HP_PERCENT);
   }

   private static float trueDeathGateHealth(FeldwayAwakenedEntity boss) {
      return trueDeathThresholdHealth(boss) + TRUE_DEATH_GATE_BUFFER;
   }

   private static float oneShotGuardRecoverTo(FeldwayAwakenedEntity boss) {
      return Math.max(trueDeathGateHealth(boss) + 1.0F, boss.getMaxHealth() * SAFEGUARD_RECOVER_PERCENT);
   }

   private static float perSecondCap(FeldwayAwakenedEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * MAX_DAMAGE_PER_SECOND_PERCENT);
   }

   private static float maxDamagePerHit(FeldwayAwakenedEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * MAX_DAMAGE_PER_HIT_PERCENT);
   }

   private static float emergencyDamageCap(FeldwayAwakenedEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * EMERGENCY_DAMAGE_PERCENT);
   }

   private static boolean isFinite(float value) {
      return !Float.isNaN(value) && !Float.isInfinite(value);
   }

   private static double safeAtLeast(double value, double min) {
      return !Double.isNaN(value) && !Double.isInfinite(value) ? Math.max(value, min) : min;
   }

   private record EntitySourcePair(Entity attacker, Entity direct) {
   }

   private static final class GuardState {
      int capWindowTicks = 20;
      int capWindowLastTick;
      float capDamageTaken = 0.0F;
      int capImmuneTicks = 0;
      int hardIFrameTicks = 0;
      int antiOneShotTicks = 80;
      boolean allowTrueDeath = false;
      boolean finalDeathStarted = false;
      boolean deathGateArmed = false;
      int deathGateArmedTick = -1;
      boolean internalHealthSet = false;
      float lastSafeHealth;
      int lastSafeHealthTick;

      GuardState(float health, int tick) {
         this.lastSafeHealth = health;
         this.lastSafeHealthTick = tick;
         this.capWindowLastTick = tick;
      }
   }
}
