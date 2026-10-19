package com.github.wal_bos.moreskills.world.entity.beretta;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.BerettaAbyssalGearSweepPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaChaosCoreExposurePayload;
import com.github.wal_bos.moreskills.net.msg.BerettaChaosRingsPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaDarknessCannonPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaInvertedFusionPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaLowVoidLaserPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaMachinaGridPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaMineralEruptionPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaOrbCascadePayload;
import com.github.wal_bos.moreskills.net.msg.BerettaReversedCrossPayload;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class BerettaCombat {
   public static final int ATTACK_LOW_VOID_LASER = 1;
   public static final int ATTACK_ORB_CASCADE = 2;
   public static final int ATTACK_DARKNESS_CANNON = 3;
   public static final int ATTACK_MINERAL_ERUPTION = 4;
   public static final int ATTACK_REVERSED_CROSS = 5;
   public static final int ATTACK_CHAOS_RINGS = 6;
   public static final int ATTACK_MACHINA_GRID = 7;
   public static final int ATTACK_INVERTED_FUSION = 8;
   public static final int ATTACK_ABYSSAL_GEAR_SWEEP = 9;
   public static final int ATTACK_CHAOS_CORE_EXPOSURE = 10;
   public static final int LOW_LASER_BEAMS = 7;
   public static final int LOW_LASER_FIRST_FIRE = 32;
   public static final int LOW_LASER_FIRE_SPACING = 10;
   public static final int LOW_LASER_TELEGRAPH_TICKS = 22;
   public static final int LOW_LASER_VISUAL_TICKS = 10;
   public static final int LOW_LASER_DURATION = 122;
   public static final int ORB_COUNT = 40;
   public static final int ORB_FIRST_LAUNCH = 34;
   public static final int ORB_LAUNCH_SPACING = 2;
   public static final int ORB_TRAVEL_TICKS = 34;
   public static final int ORB_TELEGRAPH_TICKS = 20;
   public static final double ORB_MAX_RADIUS = 26.0;
   public static final int ORB_DURATION = 164;
   public static final int CANNON_WAVES = 5;
   public static final int CANNON_ORBS_PER_WAVE = 8;
   public static final int CANNON_FIRST_IMPACT = 28;
   public static final int CANNON_WAVE_SPACING = 16;
   public static final int CANNON_TELEGRAPH_TICKS = 14;
   public static final int CANNON_VFX_DURATION = 26;
   public static final double CANNON_FIELD_RADIUS = 23.0;
   public static final int CANNON_DURATION = 110;
   public static final int MINERAL_SPIKES = 12;
   public static final int MINERAL_FIRST_IMPACT = 24;
   public static final int MINERAL_SPIKE_SPACING = 6;
   public static final int MINERAL_TELEGRAPH_TICKS = 12;
   public static final int MINERAL_VFX_DURATION = 24;
   public static final int MINERAL_DURATION = 104;
   public static final int CROSS_CUTS = 8;
   public static final int CROSS_FIRST_FIRE = 34;
   public static final int CROSS_FIRE_SPACING = 9;
   public static final int CROSS_TELEGRAPH_TICKS = 20;
   public static final int CROSS_VISUAL_TICKS = 9;
   public static final int CROSS_DURATION = 124;
   public static final int RING_COUNT = 5;
   public static final int RING_FIRST_LAUNCH = 24;
   public static final int RING_LAUNCH_SPACING = 16;
   public static final int RING_TELEGRAPH_TICKS = 12;
   public static final int RING_ACTIVE_TICKS = 42;
   public static final int RING_VFX_DURATION = 60;
   public static final double RING_SPEED = 0.86;
   public static final int RING_DURATION = 138;
   public static final int GRID_LANES = 5;
   public static final int GRID_SIZE = 5;
   public static final double GRID_CELL_SIZE = 5.8;
   public static final int GRID_WAVES = 5;
   public static final int GRID_FIRST_FIRE = 36;
   public static final int GRID_FIRE_SPACING = 18;
   public static final int GRID_TELEGRAPH_TICKS = 22;
   public static final double GRID_LANE_SPACING = 5.8;
   public static final int GRID_DURATION = 132;
   public static final int FUSION_COUNTERS = 3;
   public static final int FUSION_FIRST_FIRE = 32;
   public static final int FUSION_FIRE_SPACING = 34;
   public static final int FUSION_TELEGRAPH_TICKS = 18;
   public static final int FUSION_LAUNCH_LEAD = 0;
   public static final int FUSION_SHOCKWAVE_TICKS = 10;
   public static final int FUSION_DASH_TICKS = 11;
   public static final int FUSION_ORBS_PER_DASH = 4;
   public static final int FUSION_ORB_SPAWN_SPACING = 2;
   public static final int FUSION_ORB_FUSE_TICKS = 15;
   public static final int FUSION_SHIELD_START = 4;
   public static final int FUSION_SHIELD_END = 124;
   public static final int FUSION_DURATION = 138;
   public static final int GEAR_SPOKES = 4;
   public static final double GEAR_RADIUS = 25.5;
   public static final double GEAR_ROTATION_PER_TICK = 0.034;
   public static final int GEAR_TELEGRAPH_TICKS = 16;
   public static final int GEAR_ACTIVE_START = 32;
   public static final int GEAR_ACTIVE_END = 146;
   public static final int GEAR_DURATION = 166;
   public static final int PHASE_TWO_COMBO_START = 24;
   private static final Map<UUID, BerettaCombat.AttackRuntime> RUNTIMES = new HashMap<>();

   private BerettaCombat() {
   }

   private static EntityConfigs.BerettaConfig cfg() {
      return EntityConfigs.ENTITY.beretta;
   }

   public static void startNextAttack(BerettaEntity boss, LivingEntity target) {
      int id = chooseAttack(boss, target);
      boss.setAttack(id, target);
      boss.advanceOpeningStep();
      RUNTIMES.put(boss.getUUID(), new BerettaCombat.AttackRuntime(id, boss.getAttackSeed()));
      if (boss.level() instanceof ServerLevel level) {
         level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.HOSTILE, 4.4F, 0.58F);
         level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.HOSTILE, 4.4F, 0.43F);
      }
   }

   public static void startCoreExposure(BerettaEntity boss, LivingEntity target) {
      boss.setAttack(10, target);
      RUNTIMES.put(boss.getUUID(), new BerettaCombat.AttackRuntime(10, boss.getAttackSeed()));
      if (boss.level() instanceof ServerLevel level) {
         level.playSound(null, boss.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 6.0F, 0.34F);
         level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 5.0F, 0.42F);
      }
   }

   public static void tickAttack(BerettaEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         int var5 = boss.getAttackId();
         LivingEntity target = boss.resolveTarget();
         if (target == null && var5 != 10) {
            finish(boss, 8);
         } else {
            int t = boss.getAttackTicks();
            if (t == 1 && isSinglePacketAttack(var5)) {
               sendVfx(level, boss, (LivingEntity)(target == null ? boss : target), var5, duration(var5), boss.getAttackSeed(), baseAnchor(boss, var5));
            }

            switch (var5) {
               case 1:
                  tickLowVoidLattice(level, boss, t);
                  break;
               case 2:
                  tickSpiralBarrage(level, boss, t);
                  break;
               case 3:
                  tickOrbField(level, boss, t);
                  break;
               case 4:
                  tickPursuitSpikes(level, boss, t);
                  break;
               case 5:
                  tickRiftGuillotine(level, boss, t);
                  break;
               case 6:
                  tickExpandingRings(level, boss, t);
                  break;
               case 7:
                  tickCollapseGrid(level, boss, t);
                  break;
               case 8:
                  tickChaosGuard(level, boss, t);
                  break;
               case 9:
                  tickAbyssalGearSweep(level, boss, t);
                  break;
               case 10:
                  tickCoreExposure(level, boss, t);
                  break;
               default:
                  finish(boss, 8);
            }

            if (var5 != 10 && isPhaseTwo(boss)) {
               tickPhaseTwoCombo(level, boss, target, var5, t);
            }
         }
      } else {
         boss.clearAttack();
         clearRuntime(boss);
      }
   }

   public static boolean isRecoveryWindow(BerettaEntity boss) {
      int id = boss.getAttackId();
      int t = boss.getAttackTicks();

      return switch (id) {
         case 1 -> t > 106;
         case 2 -> t > 146;
         case 3 -> t > 99;
         case 4 -> t > 97;
         case 5 -> t > 110;
         case 6 -> t > 130;
         case 7 -> t > 115;
         case 8 -> t > 124;
         case 9 -> t > 146;
         case 10 -> true;
         default -> false;
      };
   }

   public static boolean allowsAttackMovement(int attackId) {
      return attackId == 8;
   }

   public static boolean isGuardActive(BerettaEntity boss) {
      if (boss != null && boss.getAttackId() == 8) {
         int t = boss.getAttackTicks();
         return t >= 4 && t <= 124;
      } else {
         return false;
      }
   }

   private static boolean isPhaseTwo(BerettaEntity boss) {
      return boss.getHealth() <= boss.getMaxHealth() * 0.5F;
   }

   private static boolean isSinglePacketAttack(int id) {
      return id == 1 || id == 2 || id == 5 || id == 7 || id == 8 || id == 9 || id == 10;
   }

   private static Vec3 baseAnchor(BerettaEntity boss, int id) {
      return id != 1 && id != 5 && id != 7 ? boss.getAttackOrigin() : boss.getAttackAnchor();
   }

   private static int chooseAttack(BerettaEntity boss, LivingEntity target) {
      int opening = boss.getOpeningStep();
      if (opening == 0) {
         return 9;
      }

      if (opening == 1) {
         return 2;
      }

      if (opening == 2) {
         return 4;
      }

      if (opening == 3) {
         return 6;
      }

      if (opening == 4) {
         return 3;
      }

      List<Integer> weighted = new ArrayList<>();
      double distance = boss.distanceTo(target);
      boolean sight = boss.hasLineOfSight(target);
      if (sight) {
         addWeighted(weighted, 1, (Integer)cfg().lowLaserWeight.get() + (distance >= 10.0 ? 4 : 0));
         addWeighted(weighted, 5, (Integer)cfg().reversedCrossWeight.get() + (distance >= 9.0 && distance <= 34.0 ? 4 : 0));
         addWeighted(weighted, 7, (Integer)cfg().machinaGridWeight.get() + (distance >= 12.0 ? 4 : 0));
      }

      addWeighted(weighted, 2, (Integer)cfg().orbCascadeWeight.get() + 3);
      addWeighted(weighted, 3, (Integer)cfg().darknessCannonWeight.get() + 3);
      addWeighted(weighted, 4, (Integer)cfg().mineralEruptionWeight.get() + (!sight ? 6 : 3));
      addWeighted(weighted, 6, (Integer)cfg().chaosRingsWeight.get() + (distance <= 22.0 ? 6 : 2));
      addWeighted(weighted, 8, (Integer)cfg().invertedFusionWeight.get() + (isPhaseTwo(boss) ? 5 : 1));
      addWeighted(weighted, 9, (Integer)cfg().abyssalGearSweepWeight.get() + (distance <= 26.0 ? 5 : 2));
      weighted.removeIf(id -> id == boss.getLastAttackId() || id == boss.getPreviousAttackId());
      return weighted.isEmpty() ? 2 : weighted.get(boss.getRandom().nextInt(weighted.size()));
   }

   private static void addWeighted(List<Integer> list, int id, int weight) {
      for (int i = 0; i < Math.max(0, weight); i++) {
         list.add(id);
      }
   }

   private static void tickLowVoidLattice(ServerLevel level, BerettaEntity boss, int t) {
      for (int i = 0; i < 7; i++) {
         int fire = 32 + i * 10;
         int reveal = fire - 22;
         if (t == reveal) {
            level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 4.8F, 0.84F - i * 0.055F);
         }

         Vec3 direction = lowLaserDirection(boss.getAttackDirection(), boss.getAttackSeed(), i);
         Vec3 center = lowLaserCenter(boss.getAttackAnchor(), boss.getAttackDirection(), boss.getAttackSeed(), i).add(0.0, 1.0, 0.0);
         double width = i == 6 ? 2.05 : (i >= 4 ? 1.58 : 1.34);
         float damage = ((Double)cfg().lowLaserDamage.get()).floatValue() * (i == 6 ? 1.0F : (i >= 4 ? 0.76F : 0.66F));
         if (t == fire) {
            damageCenteredLine(level, boss, center, direction, 42.0, width, damage, TensuraDamageTypes.DARKNESS_ELEMENTAL);
            level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 6.0F, 0.68F - i * 0.045F);
         }

         if (t == fire + 5 && i < 6) {
            Vec3 echoCenter = lowLaserEchoCenter(center, direction, i);
            damageCenteredLine(
               level, boss, echoCenter, direction, 40.0, 0.98, ((Double)cfg().lowLaserDamage.get()).floatValue() * 0.44F, TensuraDamageTypes.DARKNESS_ELEMENTAL
            );
            level.playSound(null, echoCenter.x, echoCenter.y, echoCenter.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, 4.2F, 0.48F + i * 0.028F);
         }
      }

      if (t >= 122) {
         finish(boss, 2);
      }
   }

   private static void tickSpiralBarrage(ServerLevel level, BerettaEntity boss, int t) {
      Vec3 origin = boss.getAttackOrigin();

      for (int i = 0; i < 40; i++) {
         int launch = 34 + i * 2;
         int impactTick = launch + 34;
         int reveal = launch - 20;
         boolean heavy = i % 5 == 4;
         boolean finisher = i >= 35;
         if (t == reveal && i % 5 == 0) {
            level.playSound(null, boss.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 4.4F, 0.8F - i / 5 * 0.045F);
         }

         if (t == launch && i % 2 == 0) {
            level.playSound(null, origin.x, origin.y + 2.4, origin.z, SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 3.4F, 0.66F + i % 5 * 0.045F);
         }

         if (t == impactTick) {
            Vec3 point = spiralOrbEndpoint(origin, boss.getAttackSeed(), i);
            double radius = finisher ? 4.7 : (heavy ? 3.9 : 2.85);
            float scale = finisher ? 0.72F : (heavy ? 0.54F : 0.34F);
            damageArea(
               level,
               boss,
               point,
               radius,
               ((Double)cfg().orbDamage.get()).floatValue() * scale,
               TensuraDamageTypes.DARKNESS_ELEMENTAL,
               !heavy && !finisher ? 0.1 : 0.24
            );
            impact(level, point, finisher ? 0.82F : (heavy ? 0.62F : 0.4F));
            level.sendParticles(
               ParticleTypes.REVERSE_PORTAL,
               point.x,
               point.y + 0.7,
               point.z,
               finisher ? 32 : (heavy ? 24 : 16),
               radius * 0.3,
               1.0 + radius * 0.18,
               radius * 0.3,
               0.16
            );
            level.playSound(
               null,
               point.x,
               point.y,
               point.z,
               (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(),
               SoundSource.HOSTILE,
               finisher ? 6.2F : (heavy ? 5.0F : 3.6F),
               finisher ? 0.38F : (heavy ? 0.48F : 0.62F)
            );
         }
      }

      if (t >= 164) {
         finish(boss, 2);
      }
   }

   private static void tickOrbField(ServerLevel level, BerettaEntity boss, int t) {
      Vec3 center = boss.getAttackAnchor();

      for (int wave = 0; wave < 5; wave++) {
         int impact = 28 + wave * 16;
         int warning = impact - 14;
         int waveSeed = mixSeed(boss.getAttackSeed(), 300 + wave);
         if (t == warning) {
            sendVfx(level, boss, boss.resolveTarget(), 3, 26, waveSeed, center);
            level.playSound(null, center.x, center.y, center.z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 4.6F, 0.72F - wave * 0.065F);
         }

         if (t == impact) {
            for (int i = 0; i < 8; i++) {
               Vec3 point = orbFieldPoint(center, waveSeed, i);
               boolean core = i == 7;
               double radius = core ? 3.5 : (i < 5 ? 3.0 : 2.55);
               float damage = ((Double)cfg().darknessCannonDamage.get()).floatValue() * (core ? 0.66F : (i < 5 ? 0.42F : 0.34F));
               damageArea(level, boss, point, radius, damage, TensuraDamageTypes.DARKNESS_ELEMENTAL, 0.16);
               impact(level, point, core ? 0.48F : 0.3F);
            }

            level.playSound(
               null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 5.4F, 0.58F - wave * 0.05F
            );
         }
      }

      if (t >= 110) {
         finish(boss, 3);
      }
   }

   private static void tickPursuitSpikes(ServerLevel level, BerettaEntity boss, int t) {
      LivingEntity target = boss.resolveTarget();
      BerettaCombat.AttackRuntime runtime = runtime(boss);

      for (int i = 0; i < 12; i++) {
         int impact = 24 + i * 6;
         int warning = impact - 12;
         int key = i;
         if (t == warning && target != null) {
            Vec3 point = predictedGroundPoint(target, i, boss.getAttackSeed());
            runtime.points.put(key, point);
            sendVfx(level, boss, target, 4, 24, mixSeed(boss.getAttackSeed(), 700 + i), point);
            level.playSound(null, point.x, point.y, point.z, (SoundEvent)TensuraSoundEvents.CAST_EARTH.get(), SoundSource.HOSTILE, 3.6F, 0.72F - i % 4 * 0.06F);
         }

         if (t == impact) {
            Vec3 point = runtime.points.remove(key);
            if (point == null && target != null) {
               point = predictedGroundPoint(target, i, boss.getAttackSeed());
            }

            if (point != null) {
               double radius = i % 4 == 3 ? 2.9 : 2.35;
               float damage = ((Double)cfg().mineralEruptionDamage.get()).floatValue() * (i % 4 == 3 ? 0.92F : 0.68F);
               damageArea(level, boss, point, radius, damage, TensuraDamageTypes.EARTH_ELEMENTAL, 0.72);
               level.sendParticles(ParticleTypes.REVERSE_PORTAL, point.x, point.y + 1.0, point.z, 15, 0.9, 2.0, 0.9, 0.14);
               level.playSound(
                  null, point.x, point.y, point.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 4.0F, 0.64F - i % 3 * 0.08F
               );
            }
         }
      }

      if (t >= 104) {
         finish(boss, 3);
      }
   }

   private static void tickRiftGuillotine(ServerLevel level, BerettaEntity boss, int t) {
      Vec3 anchor = boss.getAttackAnchor().add(0.0, 1.1, 0.0);

      for (int i = 0; i < 8; i++) {
         int fire = 34 + i * 9;
         int reveal = fire - 20;
         if (t == reveal) {
            level.playSound(null, anchor.x, anchor.y, anchor.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 4.6F, 0.8F - i * 0.05F);
         }

         Vec3 direction = riftDirection(boss.getAttackDirection(), boss.getAttackSeed(), i);
         Vec3 center = riftCenter(boss.getAttackAnchor(), boss.getAttackDirection(), boss.getAttackSeed(), i).add(0.0, 1.1, 0.0);
         boolean finisher = i >= 6;
         double width = finisher ? 2.05 : 1.36;
         float damage = ((Double)cfg().reversedCrossDamage.get()).floatValue() * (finisher ? 0.88F : 0.6F);
         if (t == fire) {
            damageCenteredLine(level, boss, center, direction, 42.0, width, damage, TensuraDamageTypes.DARKNESS_ELEMENTAL);
            level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 5.8F, 0.64F - i * 0.038F);
         }

         if (t == fire + 5 && !finisher) {
            Vec3 echoCenter = riftEchoCenter(center, direction, i);
            damageCenteredLine(
               level,
               boss,
               echoCenter,
               direction,
               39.0,
               0.92,
               ((Double)cfg().reversedCrossDamage.get()).floatValue() * 0.38F,
               TensuraDamageTypes.DARKNESS_ELEMENTAL
            );
         }
      }

      if (t >= 124) {
         finish(boss, 3);
      }
   }

   private static void tickExpandingRings(ServerLevel level, BerettaEntity boss, int t) {
      BerettaCombat.AttackRuntime runtime = runtime(boss);
      LivingEntity target = boss.resolveTarget();

      for (int i = 0; i < 5; i++) {
         int launch = 24 + i * 16;
         int warning = launch - 12;
         int key = 100 + i;
         if (t == warning) {
            Vec3 center = boss.position();
            runtime.points.put(key, center);
            sendVfx(level, boss, target, 6, 60, mixSeed(boss.getAttackSeed(), 1100 + i), center);
            level.playSound(null, center.x, center.y, center.z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 4.2F, 0.72F - i * 0.08F);
         }

         int age = t - launch;
         if (age >= 0 && age <= 42) {
            Vec3 center = runtime.points.get(key);
            if (center == null) {
               center = boss.getAttackOrigin();
            }

            double radius = expandingRingRadius(age);
            damageJumpRing(level, boss, center, radius, i, runtime, ((Double)cfg().chaosRingDamage.get()).floatValue() * 0.78F);
            if (age == 0) {
               level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 5.0F, 0.72F - i * 0.07F);
            }
         }
      }

      if (t >= 138) {
         finish(boss, 3);
      }
   }

   private static void tickCollapseGrid(ServerLevel level, BerettaEntity boss, int t) {
      for (int wave = 0; wave < 5; wave++) {
         int fire = 36 + wave * 18;
         int reveal = fire - 22;
         if (t == reveal) {
            level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 4.8F, 0.78F - wave * 0.06F);
         }

         if (t == fire) {
            Vec3 direction = gridDirection(boss.getAttackDirection(), wave, boss.getAttackSeed());

            for (int gx = 0; gx < 5; gx++) {
               for (int gz = 0; gz < 5; gz++) {
                  if (isGridCellDangerous(boss.getAttackSeed(), wave, gx, gz)) {
                     Vec3 center = gridCellCenter(boss.getAttackAnchor(), direction, gx, gz);
                     float scale = wave == 4 ? 0.72F : 0.58F;
                     damageArea(
                        level,
                        boss,
                        center,
                        2.4939999999999998,
                        ((Double)cfg().machinaGridDamage.get()).floatValue() * scale,
                        TensuraDamageTypes.DARKNESS_ELEMENTAL,
                        0.24
                     );
                  }
               }
            }

            level.playSound(null, boss.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 5.7F, 0.64F - wave * 0.06F);
         }
      }

      if (t >= 132) {
         finish(boss, 3);
      }
   }

   private static void tickChaosGuard(ServerLevel level, BerettaEntity boss, int t) {
      LivingEntity target = boss.resolveTarget();
      BerettaCombat.AttackRuntime runtime = runtime(boss);
      if (t == 4) {
         level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 5.6F, 0.42F);
      }

      for (int dash = 0; dash < 3; dash++) {
         int dashStart = 32 + dash * 34;
         int warningTick = dashStart - 18;
         int dashEnd = dashStart + 11 - 1;
         int baseKey = 500 + dash * 100;
         if (t == warningTick && target != null) {
            Vec3 start = boss.position();
            Vec3 endpoint = guardDashEndpoint(start, target, dash, boss.getAttackSeed());
            runtime.points.put(baseKey, start);
            runtime.points.put(baseKey + 1, endpoint);
            level.playSound(null, endpoint.x, endpoint.y, endpoint.z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 4.8F, 0.76F - dash * 0.08F);
         }

         if (t == dashStart) {
            Vec3 start = boss.position();
            Vec3 endpoint = runtime.points.get(baseKey + 1);
            if (endpoint == null && target != null) {
               endpoint = guardDashEndpoint(start, target, dash, boss.getAttackSeed());
            }

            if (endpoint != null) {
               runtime.points.put(baseKey, start);
               runtime.points.put(baseKey + 1, endpoint);
               runtime.points.put(baseKey + 2, start);
            }

            level.playSound(null, boss.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 4.8F, 0.72F - dash * 0.06F);
         }

         if (t >= dashStart && t <= dashEnd) {
            Vec3 endpoint = runtime.points.get(baseKey + 1);
            if (endpoint != null) {
               Vec3 current = boss.position();
               Vec3 previous = runtime.points.getOrDefault(baseKey + 2, current);
               Vec3 remaining = endpoint.subtract(current);
               Vec3 horizontal = new Vec3(remaining.x, 0.0, remaining.z);
               int ticksLeft = Math.max(1, dashEnd - t + 1);
               double distance = horizontal.length();
               if (distance > 0.05) {
                  Vec3 direction = horizontal.normalize();
                  double progress = (double)(t - dashStart) / Math.max(1, 10);
                  double profile = 0.88 + Math.sin(progress * Math.PI) * 0.24;
                  double speed = Mth.clamp(distance / ticksLeft * profile, 0.78, dash == 2 ? 1.52 : 1.4);
                  boss.setDeltaMovement(direction.x * speed, Math.min(0.0, boss.getDeltaMovement().y), direction.z * speed);
                  boss.hurtMarked = true;
               }

               damageDashSegment(
                  level, boss, previous, current, dash, runtime, ((Double)cfg().invertedFusionDamage.get()).floatValue() * (dash == 2 ? 0.72F : 0.56F)
               );
               runtime.points.put(baseKey + 2, current);
            }
         }

         if (t == dashEnd + 1) {
            Vec3 current = boss.position();
            Vec3 previous = runtime.points.getOrDefault(baseKey + 2, current);
            damageDashSegment(
               level, boss, previous, current, dash, runtime, ((Double)cfg().invertedFusionDamage.get()).floatValue() * (dash == 2 ? 0.72F : 0.56F)
            );
            Vec3 motion = boss.getDeltaMovement();
            boss.setDeltaMovement(0.0, Math.min(0.0, motion.y), 0.0);
            boss.hurtMarked = true;
         }

         for (int orb = 0; orb < 4; orb++) {
            int spawnTick = dashStart + 1 + orb * 2;
            int explodeTick = spawnTick + 15;
            int orbKey = baseKey + 20 + orb;
            if (t == spawnTick) {
               Vec3 orbPoint = boss.position().add(0.0, 0.78 + orb % 2 * 0.18, 0.0);
               runtime.points.put(orbKey, orbPoint);
               level.playSound(null, orbPoint.x, orbPoint.y, orbPoint.z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 2.6F, 0.9F - orb * 0.08F);
            }

            if (t == explodeTick) {
               Vec3 orbPoint = runtime.points.remove(orbKey);
               if (orbPoint != null) {
                  double radius = dash == 2 && orb == 3 ? 4.4 : 3.35;
                  float scale = dash == 2 && orb == 3 ? 0.64F : 0.44F;
                  damageArea(
                     level,
                     boss,
                     orbPoint,
                     radius,
                     ((Double)cfg().invertedFusionDamage.get()).floatValue() * scale,
                     TensuraDamageTypes.DARKNESS_ELEMENTAL,
                     0.18
                  );
                  impact(level, orbPoint, radius > 4.0 ? 0.72F : 0.48F);
                  level.playSound(
                     null,
                     orbPoint.x,
                     orbPoint.y,
                     orbPoint.z,
                     (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(),
                     SoundSource.HOSTILE,
                     radius > 4.0 ? 5.8F : 4.2F,
                     radius > 4.0 ? 0.4F : 0.54F
                  );
               }
            }
         }
      }

      if (t >= 138) {
         finish(boss, 4);
      }
   }

   private static void tickAbyssalGearSweep(ServerLevel level, BerettaEntity boss, int t) {
      if (t == 16) {
         level.playSound(null, boss.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 5.0F, 0.48F);
      }

      if (t >= 32 && t <= 146 && (t - 32) % 4 == 0) {
         double rotation = gearRotation(boss.getAttackSeed(), t);
         Vec3 center = boss.getAttackOrigin().add(0.0, 0.58, 0.0);

         for (int spoke = 0; spoke < 4; spoke++) {
            double angle = rotation + (Math.PI * 2) * spoke / 4.0;
            Vec3 direction = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
            Vec3 start = center.add(direction.scale(2.8));
            Vec3 end = center.add(direction.scale(25.5));
            damageLowSweepLine(level, boss, start, end, center.y, 0.74, ((Double)cfg().abyssalGearSweepDamage.get()).floatValue() * 0.24F);
         }

         if (t == 32 || (t - 32) % 24 == 0) {
            level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 4.8F, 0.7F);
         }
      }

      if (t >= 166) {
         finish(boss, 4);
      }
   }

   private static void tickCoreExposure(ServerLevel level, BerettaEntity boss, int t) {
      if (t == 1) {
         level.playSound(null, boss.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 6.0F, 0.34F);
         level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 5.0F, 0.42F);
      }

      if (t == (Integer)cfg().coreExposeDuration.get() - 18) {
         level.playSound(null, boss.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 5.0F, 0.34F);
      }

      if (t >= (Integer)cfg().coreExposeDuration.get()) {
         clearRuntime(boss);
         boss.finishCoreExposure();
      }
   }

   private static void tickPhaseTwoCombo(ServerLevel level, BerettaEntity boss, LivingEntity target, int primaryId, int t) {
      if (target != null && t >= 24) {
         int local = t - 24;

         int combo = switch (primaryId) {
            case 1, 2, 3 -> 1;
            case 4, 6 -> 2;
            case 5, 7, 9 -> 0;
            default -> -1;
         };
         if (combo == 0) {
            tickComboSpikes(level, boss, target, local);
         } else if (combo == 1) {
            tickComboRings(level, boss, target, local);
         } else if (combo == 2) {
            tickComboOrbField(level, boss, target, local);
         }
      }
   }

   private static void tickComboSpikes(ServerLevel level, BerettaEntity boss, LivingEntity target, int local) {
      BerettaCombat.AttackRuntime runtime = runtime(boss);
      int count = 4;

      for (int i = 0; i < count; i++) {
         int impact = 18 + i * 10;
         int warning = impact - 12;
         int key = 1000 + i;
         if (local == warning) {
            Vec3 point = predictedGroundPoint(target, i + 20, boss.getAttackSeed());
            runtime.points.put(key, point);
            sendVfx(level, boss, target, 4, 24, mixSeed(boss.getAttackSeed(), 1700 + i), point);
         }

         if (local == impact) {
            Vec3 point = runtime.points.remove(key);
            if (point != null) {
               damageArea(level, boss, point, 2.1, ((Double)cfg().mineralEruptionDamage.get()).floatValue() * 0.46F, TensuraDamageTypes.EARTH_ELEMENTAL, 0.54);
               level.playSound(null, point.x, point.y, point.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 3.4F, 0.72F);
            }
         }
      }
   }

   private static void tickComboRings(ServerLevel level, BerettaEntity boss, LivingEntity target, int local) {
      BerettaCombat.AttackRuntime runtime = runtime(boss);

      for (int i = 0; i < 2; i++) {
         int launch = 18 + i * 34;
         int warning = launch - 12;
         int key = 1200 + i;
         if (local == warning) {
            Vec3 center = boss.position();
            runtime.points.put(key, center);
            sendVfx(level, boss, target, 6, 60, mixSeed(boss.getAttackSeed(), 1900 + i), center);
         }

         int age = local - launch;
         if (age >= 0 && age <= 42) {
            Vec3 center = runtime.points.get(key);
            if (center != null) {
               damageJumpRing(level, boss, center, expandingRingRadius(age), 20 + i, runtime, ((Double)cfg().chaosRingDamage.get()).floatValue() * 0.48F);
            }
         }
      }
   }

   private static void tickComboOrbField(ServerLevel level, BerettaEntity boss, LivingEntity target, int local) {
      Vec3 center = boss.getAttackAnchor();

      for (int wave = 0; wave < 2; wave++) {
         int impact = 22 + wave * 30;
         int warning = impact - 14;
         int waveSeed = mixSeed(boss.getAttackSeed(), 2200 + wave);
         if (local == warning) {
            sendVfx(level, boss, target, 3, 26, waveSeed, center);
         }

         if (local == impact) {
            for (int i = 0; i < 5; i++) {
               Vec3 point = orbFieldPoint(center, waveSeed, i);
               damageArea(level, boss, point, 2.4, ((Double)cfg().darknessCannonDamage.get()).floatValue() * 0.34F, TensuraDamageTypes.DARKNESS_ELEMENTAL, 0.12);
               impact(level, point, 0.22F);
            }
         }
      }
   }

   private static Vec3 predictedGroundPoint(LivingEntity target, int index, int seed) {
      Vec3 velocity = target.getDeltaMovement();
      Vec3 horizontal = new Vec3(velocity.x, 0.0, velocity.z);
      if (horizontal.lengthSqr() > 1.0) {
         horizontal = horizontal.normalize();
      }

      double lead = 2.0 + hash(seed, index * 31 + 7) * 2.2;
      double sideOffset = (hash(seed, index * 47 + 11) - 0.5) * 1.8;
      Vec3 side = horizontal.lengthSqr() < 0.001 ? new Vec3(1.0, 0.0, 0.0) : side(horizontal);
      return target.position().add(horizontal.scale(lead)).add(side.scale(sideOffset));
   }

   public static Vec3 guardDashEndpoint(Vec3 bossPosition, LivingEntity target, int dash, int seed) {
      Vec3 velocity = target.getDeltaMovement();
      Vec3 horizontalVelocity = new Vec3(velocity.x, 0.0, velocity.z);
      if (horizontalVelocity.lengthSqr() > 1.0) {
         horizontalVelocity = horizontalVelocity.normalize();
      }

      Vec3 predicted = target.position().add(horizontalVelocity.scale(2.6 + dash * 0.5));
      Vec3 toTarget = new Vec3(predicted.x - bossPosition.x, 0.0, predicted.z - bossPosition.z);
      if (toTarget.lengthSqr() < 0.001) {
         toTarget = new Vec3(0.0, 0.0, 1.0);
      }

      Vec3 direction = toTarget.normalize();
      double overshoot = 5.6 + dash * 0.9;
      double distance = Mth.clamp(Math.sqrt(toTarget.lengthSqr()) + overshoot, 11.5, dash == 2 ? 19.0 : 17.5);
      double offset = dash == 0 ? 0.0 : (dash == 1 ? 0.65 : -0.65);
      offset += (hash(seed, 4400 + dash) - 0.5) * 0.45;
      return bossPosition.add(direction.scale(distance)).add(side(direction).scale(offset));
   }

   public static Vec3 lowLaserDirection(Vec3 base, int seed, int index) {
      double jitter = (hash(seed, 91) - 0.5) * 0.055;

      double angle = switch (index) {
         case 0, 1 -> 0.0;
         case 2 -> Math.PI / 4;
         case 3 -> -Math.PI / 4;
         case 4, 5 -> (float) (Math.PI / 2);
         default -> 0.0;
      };
      return rotate(base, angle + jitter);
   }

   public static Vec3 lowLaserCenter(Vec3 anchor, Vec3 base, int seed, int index) {
      Vec3 forward = flat(base);
      Vec3 perpendicular = side(forward);

      return switch (index) {
         case 0 -> anchor.add(perpendicular.scale(-5.0));
         case 1 -> anchor.add(perpendicular.scale(5.0));
         case 2 -> anchor.add(forward.scale(1.8));
         case 3 -> anchor.subtract(forward.scale(1.8));
         case 4 -> anchor.add(forward.scale(-3.2));
         case 5 -> anchor.add(forward.scale(3.2));
         default -> anchor;
      };
   }

   public static Vec3 lowLaserEchoCenter(Vec3 center, Vec3 direction, int index) {
      double offset = index < 2 ? 2.7 : (index < 4 ? 3.2 : 2.4);
      return center.add(side(direction).scale((index & 1) == 0 ? offset : -offset));
   }

   public static Vec3 spiralOrbPosition(Vec3 origin, int seed, int index, double age) {
      double progress = Mth.clamp(age / 34.0, 0.0, 1.0);
      int arm = index % 5;
      int ring = index / 5;
      double rotation = hash(seed, 17) * Math.PI * 2.0;
      double handedness = (arm & 1) == 0 ? 1.0 : -1.0;
      double startAngle = rotation + arm * Math.PI * 2.0 / 5.0 + ring * 0.16;
      Vec3 endpoint = spiralOrbEndpoint(origin, seed, index);
      Vec3 start = origin.add(Math.cos(startAngle) * 2.2, 2.8 + arm * 0.12, Math.sin(startAngle) * 2.2);
      double endpointRadius = Math.sqrt((endpoint.x - origin.x) * (endpoint.x - origin.x) + (endpoint.z - origin.z) * (endpoint.z - origin.z));
      double controlAngle = startAngle + handedness * (1.15 + ring * 0.045);
      Vec3 control = origin.add(
         Math.cos(controlAngle) * (5.0 + endpointRadius * 0.48), 10.0 + ring * 0.42, Math.sin(controlAngle) * (5.0 + endpointRadius * 0.48)
      );
      double inverse = 1.0 - progress;
      Vec3 point = start.scale(inverse * inverse).add(control.scale(2.0 * inverse * progress)).add(endpoint.scale(progress * progress));
      Vec3 chord = new Vec3(endpoint.x - start.x, 0.0, endpoint.z - start.z);
      Vec3 lateral = chord.lengthSqr() < 0.001 ? new Vec3(1.0, 0.0, 0.0) : side(chord);
      double curl = Math.sin(progress * Math.PI) * Math.sin(progress * Math.PI * 2.0 + arm * 0.9 + ring * 0.32) * (1.7 + ring * 0.08);
      return point.add(lateral.scale(curl)).add(0.0, Math.sin(progress * Math.PI * 3.0 + arm) * 0.18 * Math.sin(progress * Math.PI), 0.0);
   }

   public static Vec3 spiralOrbEndpoint(Vec3 origin, int seed, int index) {
      int arm = index % 5;
      int ring = index / 5;
      double rotation = hash(seed, 17) * Math.PI * 2.0;
      double angle = rotation + arm * Math.PI * 2.0 / 5.0 + ring * 0.52 + ((arm & 1) == 0 ? ring * 0.055 : -ring * 0.055);
      double radius = 6.6 + ring * 2.72;
      if (index >= 35) {
         radius = 8.4 + arm * 3.2;
      }

      return origin.add(Math.cos(angle) * radius, 0.12, Math.sin(angle) * radius);
   }

   public static Vec3 orbFieldPoint(Vec3 center, int seed, int index) {
      int wave = Math.floorMod(seed, 5);
      double rotation = hash(seed, 3) * Math.PI * 2.0 + wave * 0.58;
      if (index == 7) {
         double angle = rotation + Math.PI;
         return center.add(Math.cos(angle) * 3.2, 0.0, Math.sin(angle) * 3.2);
      }

      if (index < 5) {
         double safeWedge = Math.floorMod(seed, 5) * Math.PI * 2.0 / 5.0;
         double angle = rotation + (Math.PI * 2) * index / 5.0 + 0.52;
         if (Math.abs(Mth.wrapDegrees((float)Math.toDegrees(angle - safeWedge))) < 28.0F) {
            angle += 0.72;
         }

         double radius = 13.5 + hash(seed, index * 17 + 9) * 8.0;
         return center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
      } else {
         double angle = rotation + (Math.PI * 2) * (index - 5) / 3.0 + 0.34;
         double radius = 6.0 + hash(seed, index * 23 + 11) * 3.6;
         return center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
      }
   }

   public static Vec3 riftDirection(Vec3 base, int seed, int index) {
      double jitter = (hash(seed, 404) - 0.5) * 0.055;

      double angle = switch (index) {
         case 0 -> 0.0;
         case 1 -> (float) (Math.PI / 2);
         case 2 -> Math.PI / 4;
         case 3 -> -Math.PI / 4;
         case 4 -> Math.PI / 8;
         case 5 -> -Math.PI / 8;
         case 6 -> Math.PI * 3.0 / 8.0;
         default -> -Math.PI * 3.0 / 8.0;
      };
      return rotate(base, angle + jitter);
   }

   public static Vec3 riftCenter(Vec3 anchor, Vec3 base, int seed, int index) {
      Vec3 forward = flat(base);
      Vec3 perpendicular = side(forward);
      double petal = 3.2 + index % 3 * 0.65;

      return switch (index) {
         case 0 -> anchor.add(perpendicular.scale(-petal));
         case 1 -> anchor.add(forward.scale(petal));
         case 2 -> anchor.add(perpendicular.scale(petal));
         case 3 -> anchor.subtract(forward.scale(petal));
         case 4 -> anchor.add(forward.scale(2.4)).add(perpendicular.scale(2.4));
         case 5 -> anchor.add(forward.scale(2.4)).subtract(perpendicular.scale(2.4));
         default -> anchor;
      };
   }

   public static Vec3 riftEchoCenter(Vec3 center, Vec3 direction, int index) {
      double offset = 2.5 + index % 3 * 0.45;
      return center.add(side(direction).scale((index & 1) == 0 ? offset : -offset));
   }

   public static double expandingRingRadius(double activeAge) {
      return 2.0 + Math.max(0.0, activeAge) * 0.86;
   }

   public static Vec3 gridLaneCenter(Vec3 anchor, Vec3 direction, int lane) {
      return anchor.add(side(direction).scale((lane - 2.0) * 5.8));
   }

   public static boolean isGridLaneDangerous(int seed, int wave, int lane) {
      return lane != gridSafeLaneStart(seed, wave);
   }

   public static Vec3 gridCellCenter(Vec3 anchor, Vec3 direction, int gridX, int gridZ) {
      Vec3 forward = flat(direction);
      Vec3 perpendicular = side(forward);
      double x = (gridX - 2.0) * 5.8;
      double z = (gridZ - 2.0) * 5.8;
      return anchor.add(perpendicular.scale(x)).add(forward.scale(z));
   }

   public static boolean isGridCellDangerous(int seed, int wave, int gridX, int gridZ) {
      int shift = Math.floorMod(mixSeed(seed, 3100 + wave), 5);
      int mirroredX = wave % 2 == 0 ? gridX : 4 - gridX;
      int mirroredZ = wave % 3 == 0 ? gridZ : 4 - gridZ;

      boolean safe = switch (wave) {
         case 0 -> gridX == shift || gridZ == 2 && Math.abs(gridX - shift) <= 2;
         case 1 -> Math.abs(mirroredX - mirroredZ) <= 1;
         case 2 -> gridZ == shift || gridX == 2 && Math.abs(gridZ - shift) <= 2;
         case 3 -> Math.abs(4 - mirroredX - mirroredZ) <= 1;
         default -> gridX == 2 || gridZ == 2 || Math.abs(gridX - gridZ) == 4;
      };
      if (safe) {
         return false;
      } else {
         return wave < 4 ? true : Math.floorMod(gridX + gridZ + shift, 2) == 0 || gridX == 0 || gridX == 4 || gridZ == 0 || gridZ == 4;
      }
   }

   public static int gridSafeLaneStart(int seed, int wave) {
      return Math.floorMod(mixSeed(seed, 2900 + wave), 4);
   }

   public static Vec3 gridDirection(Vec3 base, int wave, int seed) {
      Vec3 direction = flat(base);
      double offset = (hash(seed, 903) - 0.5) * 0.18;

      return switch (wave) {
         case 1 -> rotate(direction, (float) (Math.PI / 2) + offset);
         case 2 -> rotate(direction, (Math.PI / 4) + offset);
         case 3 -> rotate(direction, (-Math.PI / 4) + offset);
         case 4 -> rotate(direction, (float) (Math.PI / 4) + offset);
         default -> rotate(direction, offset);
      };
   }

   public static double gearRotation(int seed, double attackAge) {
      double elapsed = Math.max(0.0, attackAge - 32.0);
      double modulation = Math.sin(elapsed * 0.045) * 0.1;
      return seed * 1.7E-4 + elapsed * 0.034 + modulation;
   }

   public static int fusionSafeSpoke(int seed, int wave) {
      return Math.floorMod(mixSeed(seed, 3600 + wave), 10);
   }

   public static double fusionBaseRotation(int seed, int wave) {
      return seed * 1.3E-4 + wave * 0.41;
   }

   private static void sendVfx(ServerLevel level, BerettaEntity boss, LivingEntity target, int id, int duration, int seed, Vec3 anchor) {
      if (target == null) {
         target = boss;
      }

      Vec3 direction = boss.getAttackDirection();

      CustomPacketPayload payload = switch (id) {
         case 1 -> new BerettaLowVoidLaserPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 2 -> new BerettaOrbCascadePayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 3 -> new BerettaDarknessCannonPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 4 -> new BerettaMineralEruptionPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 5 -> new BerettaReversedCrossPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 6 -> new BerettaChaosRingsPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 7 -> new BerettaMachinaGridPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 8 -> new BerettaInvertedFusionPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 9 -> new BerettaAbyssalGearSweepPayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         case 10 -> new BerettaChaosCoreExposurePayload(boss.getId(), target.getId(), anchor.x, anchor.y, anchor.z, direction.x, direction.z, duration, seed);
         default -> null;
      };
      if (payload != null) {
         double range = (Double)cfg().vfxSendRange.get();
         AABB area = new AABB(anchor, anchor).inflate(range);

         for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   private static int duration(int id) {
      return switch (id) {
         case 1 -> 122;
         case 2 -> 164;
         case 3 -> 110;
         case 4 -> 104;
         case 5 -> 124;
         case 6 -> 138;
         case 7 -> 132;
         case 8 -> 138;
         case 9 -> 166;
         case 10 -> cfg().coreExposeDuration.get();
         default -> 60;
      };
   }

   private static void damageCenteredLine(
      ServerLevel level, BerettaEntity boss, Vec3 center, Vec3 direction, double halfLength, double radius, float damage, ResourceKey<DamageType> type
   ) {
      Vec3 start = center.subtract(direction.scale(halfLength));
      Vec3 end = center.add(direction.scale(halfLength));
      damageLine(level, boss, start, end, radius, damage, type);
   }

   private static void damageLine(ServerLevel level, BerettaEntity boss, Vec3 start, Vec3 end, double radius, float damage, ResourceKey<DamageType> type) {
      AABB box = new AABB(start, end).inflate(radius + 2.0);
      DamageSource source = source(level, boss, type, ((Double)cfg().barrierBypass.get()).floatValue(), ((Double)cfg().resistanceBypass.get()).floatValue());

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         if (!(distanceToSegment(center, start, end) > radius + living.getBbWidth() * 0.5)) {
            hurt(living, source, damage);
         }
      }
   }

   private static void damageArea(ServerLevel level, BerettaEntity boss, Vec3 center, double radius, float damage, ResourceKey<DamageType> type, double lift) {
      DamageSource source = source(level, boss, type, ((Double)cfg().barrierBypass.get()).floatValue(), ((Double)cfg().resistanceBypass.get()).floatValue());
      AABB box = new AABB(center, center).inflate(radius, radius + 2.0, radius);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 body = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         if (!(body.distanceTo(center.add(0.0, 0.8, 0.0)) > radius + living.getBbWidth())) {
            hurt(living, source, damage);
            if (lift > 0.0) {
               living.setDeltaMovement(living.getDeltaMovement().add(0.0, lift, 0.0));
               living.hurtMarked = true;
            }
         }
      }
   }

   private static void damageJumpRing(
      ServerLevel level, BerettaEntity boss, Vec3 center, double radius, int ringId, BerettaCombat.AttackRuntime runtime, float damage
   ) {
      DamageSource source = source(
         level,
         boss,
         TensuraDamageTypes.DARKNESS_ELEMENTAL,
         ((Double)cfg().barrierBypass.get()).floatValue(),
         ((Double)cfg().resistanceBypass.get()).floatValue()
      );
      AABB box = new AABB(center, center).inflate(radius + 2.0, 3.0, radius + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         double dx = living.getX() - center.x;
         double dz = living.getZ() - center.z;
         double distance = Math.sqrt(dx * dx + dz * dz);
         if (!(Math.abs(distance - radius) > 0.72 + living.getBbWidth() * 0.35) && !(living.getBoundingBox().minY > center.y + 0.82)) {
            String hitKey = ringId + ":" + living.getUUID();
            if (runtime.ringHits.add(hitKey)) {
               hurt(living, source, damage);
               Vec3 push = new Vec3(dx, 0.0, dz);
               if (push.lengthSqr() > 0.001) {
                  living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.24)).add(0.0, 0.12, 0.0));
                  living.hurtMarked = true;
               }
            }
         }
      }
   }

   private static void damageDashSegment(
      ServerLevel level, BerettaEntity boss, Vec3 start, Vec3 end, int dash, BerettaCombat.AttackRuntime runtime, float damage
   ) {
      AABB box = new AABB(start, end).inflate(2.6, 2.2, 2.6);
      DamageSource source = source(
         level,
         boss,
         TensuraDamageTypes.DARKNESS_ELEMENTAL,
         ((Double)cfg().barrierBypass.get()).floatValue(),
         ((Double)cfg().resistanceBypass.get()).floatValue()
      );

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 body = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         if (!(distanceToSegment(body, start.add(0.0, 1.0, 0.0), end.add(0.0, 1.0, 0.0)) > 1.85 + living.getBbWidth() * 0.5)) {
            String hitKey = "dash:" + dash + ":" + living.getUUID();
            if (runtime.ringHits.add(hitKey)) {
               hurt(living, source, damage);
               Vec3 push = new Vec3(end.x - start.x, 0.0, end.z - start.z);
               if (push.lengthSqr() > 0.001) {
                  living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.52)).add(0.0, 0.12, 0.0));
                  living.hurtMarked = true;
               }
            }
         }
      }
   }

   private static void damageLowSweepLine(ServerLevel level, BerettaEntity boss, Vec3 start, Vec3 end, double beamY, double radius, float damage) {
      AABB box = new AABB(start, end).inflate(radius + 2.0, 2.0, radius + 2.0);
      DamageSource source = source(
         level,
         boss,
         TensuraDamageTypes.DARKNESS_ELEMENTAL,
         ((Double)cfg().barrierBypass.get()).floatValue(),
         ((Double)cfg().resistanceBypass.get()).floatValue()
      );

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         if (!(living.getBoundingBox().minY > beamY + 0.52)) {
            Vec3 feet = new Vec3(living.getX(), beamY, living.getZ());
            if (!(distanceToSegment(feet, start, end) > radius + living.getBbWidth() * 0.45)) {
               hurt(living, source, damage);
            }
         }
      }
   }

   private static void damageGuardShockwave(
      ServerLevel level, BerettaEntity boss, Vec3 center, double radius, int wave, BerettaCombat.AttackRuntime runtime, float damage
   ) {
      DamageSource source = source(
         level,
         boss,
         TensuraDamageTypes.DARKNESS_ELEMENTAL,
         ((Double)cfg().barrierBypass.get()).floatValue(),
         ((Double)cfg().resistanceBypass.get()).floatValue()
      );
      AABB box = new AABB(center, center).inflate(radius + 2.0, 3.0, radius + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         double dx = living.getX() - center.x;
         double dz = living.getZ() - center.z;
         double distance = Math.sqrt(dx * dx + dz * dz);
         if (!(Math.abs(distance - radius) > 0.82 + living.getBbWidth() * 0.4) && !(living.getBoundingBox().minY > center.y + 0.95)) {
            String hitKey = "guard:" + wave + ":" + living.getUUID();
            if (runtime.ringHits.add(hitKey)) {
               hurt(living, source, damage);
               Vec3 push = new Vec3(dx, 0.0, dz);
               if (push.lengthSqr() > 0.001) {
                  living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.38)).add(0.0, 0.18, 0.0));
                  living.hurtMarked = true;
               }
            }
         }
      }
   }

   private static DamageSource source(ServerLevel level, BerettaEntity boss, ResourceKey<DamageType> type, float barrierBypass, float resistanceBypass) {
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, type, boss);
      if (source instanceof TensuraDamageSource tensura) {
         source = tensura.tensura$setBarrierBypassLevel(Math.max(3.0F, barrierBypass));
         source = ((TensuraDamageSource)source).tensura$setResistanceBypassLevel(Math.max(2.0F, resistanceBypass));
         source = ((TensuraDamageSource)source).tensura$setDodgeBypass();
      }

      return source;
   }

   private static void hurt(LivingEntity target, DamageSource source, float damage) {
      if (!(damage <= 0.0F) && target.isAlive()) {
         target.invulnerableTime = 0;
         target.hurt(source, damage);
         target.invulnerableTime = 0;
      }
   }

   private static void impact(ServerLevel level, Vec3 point, float scale) {
      TensuraParticleHelper.spawnServerParticles(
         level, TensuraParticleUtils.getPurpleWave(1.0F, 4.0F + scale * 8.0F, -0.35F, true), point.x, point.y + 0.6, point.z
      );
      level.sendParticles(ParticleTypes.PORTAL, point.x, point.y + 0.8, point.z, 20, 1.2 + scale, 1.0 + scale, 1.2 + scale, 0.2);
   }

   private static void finish(BerettaEntity boss, int cooldownBonus) {
      clearRuntime(boss);
      boss.finishAttack(cooldownBonus);
   }

   private static BerettaCombat.AttackRuntime runtime(BerettaEntity boss) {
      BerettaCombat.AttackRuntime runtime = RUNTIMES.get(boss.getUUID());
      if (runtime == null || runtime.attackId != boss.getAttackId() || runtime.seed != boss.getAttackSeed()) {
         runtime = new BerettaCombat.AttackRuntime(boss.getAttackId(), boss.getAttackSeed());
         RUNTIMES.put(boss.getUUID(), runtime);
      }

      return runtime;
   }

   public static void clearRuntime(BerettaEntity boss) {
      if (boss != null) {
         RUNTIMES.remove(boss.getUUID());
      }
   }

   private static Vec3 flat(Vec3 direction) {
      Vec3 value = new Vec3(direction.x, 0.0, direction.z);
      return value.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 0.0, 1.0) : value.normalize();
   }

   private static Vec3 rotate(Vec3 direction, double angle) {
      Vec3 value = flat(direction);
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      return new Vec3(value.x * cos - value.z * sin, 0.0, value.x * sin + value.z * cos).normalize();
   }

   private static Vec3 side(Vec3 direction) {
      Vec3 value = flat(direction);
      return new Vec3(-value.z, 0.0, value.x);
   }

   private static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double length = line.lengthSqr();
      if (length < 1.0E-4) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / length;
      t = Math.max(0.0, Math.min(1.0, t));
      return point.distanceTo(start.add(line.scale(t)));
   }

   private static int mixSeed(int seed, int salt) {
      int value = seed ^ salt * -1640531527;
      value ^= value >>> 16;
      value *= 2146121005;
      value ^= value >>> 15;
      value *= -2073254261;
      return value ^ value >>> 16;
   }

   public static double hash(int seed, int index) {
      return (mixSeed(seed, index) & 2147483647) / 2.147483647E9;
   }

   private static final class AttackRuntime {
      private final int attackId;
      private final int seed;
      private final Map<Integer, Vec3> points = new HashMap<>();
      private final Set<String> ringHits = new HashSet<>();

      private AttackRuntime(int attackId, int seed) {
         this.attackId = attackId;
         this.seed = seed;
      }
   }

   private static final class BerettaVfxMath {
      private static double easeOut(double value) {
         value = Mth.clamp(value, 0.0, 1.0);
         double inverse = 1.0 - value;
         return 1.0 - inverse * inverse * inverse;
      }

      private static double smoothStep(double value) {
         value = Mth.clamp(value, 0.0, 1.0);
         return value * value * (3.0 - 2.0 * value);
      }

      private static Vec3 side(Vec3 direction) {
         Vec3 value = new Vec3(direction.x, 0.0, direction.z);
         if (value.lengthSqr() < 1.0E-4) {
            return new Vec3(1.0, 0.0, 0.0);
         }

         value = value.normalize();
         return new Vec3(-value.z, 0.0, value.x);
      }
   }
}
