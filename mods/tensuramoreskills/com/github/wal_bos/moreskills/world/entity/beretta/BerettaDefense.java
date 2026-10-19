package com.github.wal_bos.moreskills.world.entity.beretta;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;

public final class BerettaDefense {
   private static final Map<UUID, BerettaDefense.GuardState> GUARDS = new ConcurrentHashMap<>();

   private BerettaDefense() {
   }

   private static EntityConfigs.BerettaConfig cfg() {
      return EntityConfigs.ENTITY.beretta;
   }

   public static void tick(BerettaEntity boss) {
      if (boss != null && !boss.level().isClientSide()) {
         BerettaDefense.GuardState guard = guard(boss);
         normalizeCapWindow(boss, guard);
         boss.invulnerableTime = 0;
         boss.hurtTime = 0;
         if (!guard.finalDeathStarted) {
            guard.allowTrueDeath = false;
            if (isFinite(boss.getHealth()) && !(boss.getHealth() <= 0.0F)) {
               float gate = trueDeathGateHealth(boss);
               if (boss.getHealth() <= gate) {
                  armDeathGate(boss, guard);
                  setGuardedHealth(boss, guard, gate);
                  boss.deathTime = 0;
                  boss.hurtTime = 0;
                  boss.invulnerableTime = 0;
               } else if (boss.getHealth() > gate + 2.0F) {
                  guard.deathGateArmed = false;
                  guard.deathGateArmedTick = -1;
               }

               if (guard.antiOneShotTicks > 0) {
                  guard.antiOneShotTicks--;
               }

               if (boss.tickCount % 20 == 0) {
                  boss.refillExistence();
               }

               refreshLastSafeHealth(boss, guard);
               boss.clearFire();
            } else {
               if (canOpenTrueDeath(boss, guard)) {
                  beginFinalDeath(boss, guard);
               } else {
                  triggerLethalSafeguard(boss, guard);
               }
            }
         }
      }
   }

   public static boolean shouldCancelDamage(BerettaEntity boss, DamageSource source, float amount) {
      if (boss == null || boss.level().isClientSide()) {
         return true;
      } else if (source == null || boss.isRemoved()) {
         return true;
      } else if (!(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         BerettaDefense.GuardState guard = guard(boss);
         return canOpenTrueDeath(boss, guard) ? false : isOwnDamage(boss, source);
      } else {
         return true;
      }
   }

   public static float modifyIncomingDamage(BerettaEntity boss, DamageSource source, float amount) {
      if (boss == null || boss.level().isClientSide()) {
         return 0.0F;
      }

      if (source != null && !(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         BerettaDefense.GuardState guard = guard(boss);
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
         if (!isFinite(before) || before <= 0.0F) {
            triggerLethalSafeguard(boss, guard);
            return 0.0F;
         }

         if (before <= gate) {
            armDeathGate(boss, guard);
            return 0.0F;
         }

         if (isDangerousBypassSource(source)) {
            amount = Math.min(amount, emergencyDamageCap(boss));
         }

         if (boss.isCoreExposed()) {
            amount *= ((Double)cfg().coreIncomingDamageMultiplier.get()).floatValue();
         }

         if (BerettaCombat.isGuardActive(boss)) {
            amount *= 0.35F;
         }

         if (guard.antiOneShotTicks > 0) {
            amount *= ((Double)cfg().antiOneShotDamageMultiplier.get()).floatValue();
         }

         float remaining = perWindowCap(boss) - guard.capDamageTaken;
         if (remaining <= 0.0F) {
            return 0.0F;
         }

         float perHit = maxDamagePerHit(boss);
         amount = Math.min(amount, perHit);
         amount = Math.min(amount, remaining);
         if (before - amount <= gate) {
            amount = Math.max(0.0F, before - gate);
            armDeathGate(boss, guard);
         }

         return Math.max(0.0F, amount);
      } else {
         return 0.0F;
      }
   }

   public static void afterDamage(BerettaEntity boss, float beforeHealth, float allowedDamage) {
      if (boss != null && !boss.level().isClientSide()) {
         BerettaDefense.GuardState guard = guard(boss);
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
                  maxAllowedLoss = Math.min(maxAllowedLoss, Math.max(0.0F, perWindowCap(boss) - guard.capDamageTaken + actualLoss));
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
                  if (guard.capDamageTaken > perWindowCap(boss)) {
                     guard.capDamageTaken = perWindowCap(boss);
                  }

                  guard.lastSafeHealth = boss.getHealth();
                  guard.lastSafeHealthTick = boss.tickCount;
               } else {
                  triggerLethalSafeguard(boss, guard);
               }
            }
         }
      }
   }

   public static float guardSetHealth(BerettaEntity boss, float value) {
      if (boss == null || boss.level().isClientSide() || boss.tickCount <= 0) {
         return value;
      }

      if (!Float.isNaN(value) && !Float.isInfinite(value)) {
         if (value < 0.0F) {
            value = 0.0F;
         }

         BerettaDefense.GuardState guard = guard(boss);
         if (!guard.internalHealthSet && !guard.damageApplication && !guard.finalDeathStarted) {
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
               float remaining = perWindowCap(boss) - guard.capDamageTaken;
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
               if (guard.capDamageTaken > perWindowCap(boss)) {
                  guard.capDamageTaken = perWindowCap(boss);
               }

               guard.antiOneShotTicks = Math.max(guard.antiOneShotTicks, (Integer)cfg().setHealthAntiOneShotTicks.get());
               guard.lastSafeHealth = fixed;
               guard.lastSafeHealthTick = boss.tickCount;
               boss.deathTime = 0;
               boss.hurtTime = 0;
               boss.invulnerableTime = 0;
               return fixed;
            }
         } else {
            return value;
         }
      } else {
         return Math.max(1.0F, boss.getHealth());
      }
   }

   public static boolean shouldStopDeath(BerettaEntity boss, DamageSource source) {
      if (boss != null && !boss.level().isClientSide()) {
         BerettaDefense.GuardState guard = guard(boss);
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

   public static boolean shouldBlockRemoval(BerettaEntity boss, RemovalReason reason) {
      if (boss == null || boss.level().isClientSide()) {
         return false;
      }

      if (reason != RemovalReason.UNLOADED_TO_CHUNK && reason != RemovalReason.UNLOADED_WITH_PLAYER && reason != RemovalReason.CHANGED_DIMENSION) {
         BerettaDefense.GuardState guard = guard(boss);
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

   public static void beginDamageApplication(BerettaEntity boss) {
      if (boss != null) {
         guard(boss).damageApplication = true;
      }
   }

   public static void endDamageApplication(BerettaEntity boss) {
      if (boss != null) {
         guard(boss).damageApplication = false;
      }
   }

   public static void resetDamageWindow(BerettaEntity boss) {
      if (boss != null) {
         BerettaDefense.GuardState guard = guard(boss);
         guard.capWindowTicks = Math.max(1, (Integer)cfg().capWindowTicks.get());
         guard.capWindowLastTick = boss.tickCount;
         guard.capDamageTaken = 0.0F;
      }
   }

   public static void clear(BerettaEntity boss) {
      if (boss != null) {
         GUARDS.remove(boss.getUUID());
      }
   }

   private static boolean isOwnDamage(BerettaEntity boss, DamageSource source) {
      try {
         Entity attacker = source.getEntity();
         Entity direct = source.getDirectEntity();
         if (attacker == boss || direct == boss) {
            return true;
         }

         if (attacker instanceof LivingEntity livingAttacker && (livingAttacker.isAlliedTo(boss) || boss.isAlliedTo(livingAttacker))) {
            return true;
         }

         if (direct instanceof LivingEntity livingDirect && (livingDirect.isAlliedTo(boss) || boss.isAlliedTo(livingDirect))) {
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

         String value = id.toLowerCase();
         return value.contains("out_of_world")
            || value.contains("void")
            || value.contains("true")
            || value.contains("kill")
            || value.contains("delete")
            || value.contains("erase")
            || value.contains("execute")
            || value.contains("remove")
            || value.contains("despawn")
            || value.contains("bypass")
            || value.contains("absolute")
            || value.contains("spiritual")
            || value.contains("death")
            || value.contains("severance")
            || value.contains("disintegration")
            || value.contains("existence");
      } catch (Throwable var3) {
         return false;
      }
   }

   private static void triggerLethalSafeguard(BerettaEntity boss, BerettaDefense.GuardState guard) {
      if (!guard.finalDeathStarted && !canOpenTrueDeath(boss, guard)) {
         float restore = Math.max(oneShotGuardRecoverTo(boss), trueDeathGateHealth(boss) + 1.0F);
         setGuardedHealth(boss, guard, Math.min(boss.getMaxHealth(), restore));
         boss.deathTime = 0;
         boss.hurtTime = 0;
         boss.invulnerableTime = 0;
         guard.antiOneShotTicks = Math.max(guard.antiOneShotTicks, (Integer)cfg().safeguardAntiOneShotTicks.get());
         guard.allowTrueDeath = false;
         guard.finalDeathStarted = false;
         guard.deathGateArmed = false;
         guard.deathGateArmedTick = -1;
         guard.lastSafeHealth = boss.getHealth();
         guard.lastSafeHealthTick = boss.tickCount;
         boss.refillExistence();
         boss.clearFire();
      } else {
         beginFinalDeath(boss, guard);
      }
   }

   private static void beginFinalDeath(BerettaEntity boss, BerettaDefense.GuardState guard) {
      guard.allowTrueDeath = true;
      guard.finalDeathStarted = true;
      guard.antiOneShotTicks = 0;
      guard.capDamageTaken = 0.0F;
      boss.invulnerableTime = 0;
      boss.hurtTime = 0;
      boss.clearFire();
   }

   private static void refreshLastSafeHealth(BerettaEntity boss, BerettaDefense.GuardState guard) {
      if (boss.tickCount != guard.lastSafeHealthTick) {
         guard.lastSafeHealthTick = boss.tickCount;
         float hp = boss.getHealth();
         if (hp > 0.0F && isFinite(hp)) {
            guard.lastSafeHealth = hp;
         }
      }
   }

   private static void normalizeCapWindow(BerettaEntity boss, BerettaDefense.GuardState guard) {
      if (guard.capWindowLastTick != boss.tickCount) {
         guard.capWindowLastTick = boss.tickCount;
         if (guard.capWindowTicks > 0) {
            guard.capWindowTicks--;
         }

         if (guard.capWindowTicks <= 0) {
            guard.capWindowTicks = Math.max(1, (Integer)cfg().capWindowTicks.get());
            guard.capDamageTaken = 0.0F;
         }
      }
   }

   private static void armDeathGate(BerettaEntity boss, BerettaDefense.GuardState guard) {
      if (!guard.deathGateArmed) {
         guard.deathGateArmed = true;
         guard.deathGateArmedTick = boss.tickCount;
      }
   }

   private static boolean canOpenTrueDeath(BerettaEntity boss, BerettaDefense.GuardState guard) {
      if (boss == null || guard == null) {
         return false;
      } else {
         return !guard.finalDeathStarted && !guard.allowTrueDeath
            ? boss.getHealth() <= trueDeathGateHealth(boss)
               && guard.deathGateArmed
               && guard.deathGateArmedTick >= 0
               && boss.tickCount > guard.deathGateArmedTick
            : true;
      }
   }

   private static void setGuardedHealth(BerettaEntity boss, BerettaDefense.GuardState guard, float health) {
      guard.internalHealthSet = true;

      try {
         boss.setHealth(Math.max(0.0F, health));
      } finally {
         guard.internalHealthSet = false;
      }
   }

   private static BerettaDefense.GuardState guard(BerettaEntity boss) {
      return GUARDS.computeIfAbsent(boss.getUUID(), id -> new BerettaDefense.GuardState(boss.getHealth(), boss.tickCount));
   }

   private static float trueDeathThresholdHealth(BerettaEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * ((Double)cfg().trueDeathHpPercent.get()).floatValue());
   }

   private static float trueDeathGateHealth(BerettaEntity boss) {
      return trueDeathThresholdHealth(boss) + ((Double)cfg().trueDeathGateBuffer.get()).floatValue();
   }

   private static float oneShotGuardRecoverTo(BerettaEntity boss) {
      return Math.max(trueDeathGateHealth(boss) + 1.0F, boss.getMaxHealth() * ((Double)cfg().safeguardRecoverPercent.get()).floatValue());
   }

   private static float perWindowCap(BerettaEntity boss) {
      float percent;
      if (boss.isCoreExposed()) {
         percent = ((Double)cfg().coreDamagePerSecondPercent.get()).floatValue();
      } else if (BerettaCombat.isRecoveryWindow(boss)) {
         percent = ((Double)cfg().recoveryDamagePerSecondPercent.get()).floatValue();
      } else {
         percent = ((Double)cfg().maxDamagePerSecondPercent.get()).floatValue();
      }

      return Math.max(1.0F, boss.getMaxHealth() * percent);
   }

   private static float maxDamagePerHit(BerettaEntity boss) {
      float percent;
      if (boss.isCoreExposed()) {
         percent = ((Double)cfg().coreDamagePerHitPercent.get()).floatValue();
      } else if (BerettaCombat.isRecoveryWindow(boss)) {
         percent = ((Double)cfg().recoveryDamagePerHitPercent.get()).floatValue();
      } else {
         percent = ((Double)cfg().maxDamagePerHitPercent.get()).floatValue();
      }

      return Math.max(1.0F, boss.getMaxHealth() * percent);
   }

   private static float emergencyDamageCap(BerettaEntity boss) {
      return Math.max(1.0F, boss.getMaxHealth() * ((Double)cfg().emergencyDamagePercent.get()).floatValue());
   }

   private static boolean isFinite(float value) {
      return !Float.isNaN(value) && !Float.isInfinite(value);
   }

   private static final class GuardState {
      int capWindowTicks;
      int capWindowLastTick;
      float capDamageTaken;
      int antiOneShotTicks;
      boolean allowTrueDeath;
      boolean finalDeathStarted;
      boolean deathGateArmed;
      int deathGateArmedTick = -1;
      boolean internalHealthSet;
      boolean damageApplication;
      float lastSafeHealth;
      int lastSafeHealthTick;

      GuardState(float health, int tick) {
         this.capWindowTicks = Math.max(1, (Integer)BerettaDefense.cfg().capWindowTicks.get());
         this.capWindowLastTick = tick;
         this.antiOneShotTicks = (Integer)BerettaDefense.cfg().initialAntiOneShotTicks.get();
         this.lastSafeHealth = health;
         this.lastSafeHealthTick = tick;
      }
   }
}
