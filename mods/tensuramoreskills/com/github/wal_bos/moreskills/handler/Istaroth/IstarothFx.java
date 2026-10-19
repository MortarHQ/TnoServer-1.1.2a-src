package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleType;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.storage.effect.EffectStorage;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class IstarothFx {
   private IstarothFx() {
   }

   public static void passiveClockAura(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var6 = center(entity);
         double t = entity.tickCount * 0.13;
         level.sendParticles(ParticleTypes.WHITE_ASH, var6.x, var6.y, var6.z, 18, entity.getBbWidth(), entity.getBbHeight() * 0.45, entity.getBbWidth(), 0.018);
         level.sendParticles(
            ParticleTypes.END_ROD, var6.x, var6.y + 0.18, var6.z, 8, entity.getBbWidth() * 0.7, entity.getBbHeight() * 0.3, entity.getBbWidth() * 0.7, 0.006
         );
         orbit(entity, 1.55F, 9, 0.62F, ParticleTypes.END_ROD);
         orbit(entity, 2.15F, 13, 0.42F, ParticleTypes.WHITE_ASH);
         if (entity.tickCount % 18 == 0) {
            ringParticles(level, entity.position().add(0.0, 0.12, 0.0), 1.8F, 42, ParticleTypes.END_ROD);
            verticalHourMarks(level, var6, 1.65F, 1.15F, 12);
         }

         if (entity.tickCount % 45 == 0) {
            risingTimeDust(entity, 1.35F, 34);
            ghostEcho(entity, entity.position().subtract(entity.getDeltaMovement().scale(2.5)), 0.85F);
         }

         if (entity.tickCount % 90 == 0) {
            clockCircle(entity, 2.0F, 30, MagicCircleVariant.SPACE);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.22F, 1.55F);
         }

         if (entity.tickCount % 6 == 0) {
            Vec3 p = var6.add(Math.cos(t) * 1.9, Math.sin(t * 1.6) * 0.25, Math.sin(t) * 1.9);
            level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 1, 0.01, 0.01, 0.01, 0.0);
         }
      }
   }

   public static void grayPulse(Entity entity, float size) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var4 = center(entity);
         level.sendParticles(ParticleTypes.WHITE_ASH, var4.x, var4.y, var4.z, Math.max(30, (int)(size * 24.0F)), size * 0.3, size * 0.16, size * 0.3, 0.06);
         level.sendParticles(ParticleTypes.POOF, var4.x, var4.y, var4.z, Math.max(8, (int)(size * 3.0F)), size * 0.12, size * 0.06, size * 0.12, 0.018);
         level.sendParticles(ParticleTypes.FLASH, var4.x, var4.y, var4.z, size >= 4.0F ? 1 : 0, 0.0, 0.0, 0.0, 0.0);
         ringParticles(level, var4, size * 0.62F, 54, ParticleTypes.WHITE_ASH);
         ringParticles(level, var4.add(0.0, 0.18, 0.0), size * 0.92F, 68, ParticleTypes.END_ROD);
         radialBurst(level, var4, size * 0.88F, 18, ParticleTypes.WHITE_ASH);
         if (size >= 3.0F) {
            timeFractureCage(level, var4, size * 0.55F, size * 0.35F, 18);
         }
      }
   }

   public static void clockDust(Entity entity, float size, int count) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var6 = center(entity);
         int dust = Math.max(8, count);
         level.sendParticles(ParticleTypes.WHITE_ASH, entity.getX(), entity.getY() + 0.25, entity.getZ(), dust, size, entity.getBbHeight() * 0.6, size, 0.08);
         level.sendParticles(ParticleTypes.END_ROD, var6.x, var6.y, var6.z, Math.max(4, dust / 6), size * 0.42, entity.getBbHeight() * 0.34, size * 0.42, 0.018);
         spiral(entity, size * 0.72F, entity.getBbHeight() + 0.55F, Math.max(18, dust), ParticleTypes.WHITE_ASH);
         reverseSpiral(entity, size * 0.54F, entity.getBbHeight() + 0.45F, Math.max(14, dust / 2), ParticleTypes.END_ROD);
         if (entity.tickCount % 10 == 0) {
            ringParticles(level, var6, size * 0.9F, 32, ParticleTypes.WHITE_ASH);
         }
      }
   }

   public static void frozenProjectileAura(Entity entity) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var3 = center(entity);
         level.sendParticles(ParticleTypes.END_ROD, var3.x, var3.y, var3.z, 10, 0.18, 0.18, 0.18, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, var3.x, var3.y, var3.z, 18, 0.32, 0.32, 0.32, 0.012);
         level.sendParticles(ParticleTypes.CRIT, var3.x, var3.y, var3.z, 4, 0.14, 0.14, 0.14, 0.02);
         ringParticles(level, var3, 0.46F, 24, ParticleTypes.END_ROD);
         ringParticles(level, var3.add(0.0, 0.05, 0.0), 0.72F, 32, ParticleTypes.WHITE_ASH);
         tinyClockHands(level, var3, 0.68F, entity.tickCount);
      }
   }

   public static void glassWall(LivingEntity owner, Vec3 pos) {
      if (owner.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 22, 0.32, 0.32, 0.32, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, pos.x, pos.y, pos.z, 34, 0.48, 0.48, 0.48, 0.018);
         level.sendParticles(ParticleTypes.CRIT, pos.x, pos.y, pos.z, 12, 0.3, 0.3, 0.3, 0.04);
         ringParticles(level, pos, 0.9F, 40, ParticleTypes.END_ROD);
         ringParticles(level, pos.add(0.0, 0.08, 0.0), 1.2F, 48, ParticleTypes.WHITE_ASH);
         crossSlash(level, pos, 1.15F, ParticleTypes.WHITE_ASH);
         timeFractureCage(level, pos, 0.85F, 0.65F, 12);
         if (owner.tickCount % 8 == 0) {
            level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.45F, 1.85F);
            level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GLASS_HIT, SoundSource.PLAYERS, 0.35F, 0.65F);
         }
      }
   }

   public static void clockShatter(Entity entity, float size) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var4 = center(entity);
         level.sendParticles(ParticleTypes.EXPLOSION, var4.x, var4.y, var4.z, 2, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(ParticleTypes.FLASH, var4.x, var4.y, var4.z, 1, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, var4.x, var4.y, var4.z, Math.max(120, (int)(size * 30.0F)), size * 0.46, size * 0.17, size * 0.46, 0.15);
         level.sendParticles(ParticleTypes.END_ROD, var4.x, var4.y, var4.z, Math.max(50, (int)(size * 12.0F)), size * 0.32, size * 0.14, size * 0.32, 0.06);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(),
            var4.x,
            var4.y,
            var4.z,
            Math.max(30, (int)(size * 8.0F)),
            size * 0.3,
            size * 0.15,
            size * 0.3,
            0.18
         );
         ringParticles(level, var4, size * 0.7F, 88, ParticleTypes.WHITE_ASH);
         ringParticles(level, var4.add(0.0, 0.25, 0.0), size * 1.05F, 104, ParticleTypes.END_ROD);
         radialBurst(level, var4, size * 1.35F, 42, ParticleTypes.WHITE_ASH);
         timeFractureCage(level, var4, size * 0.62F, size * 0.45F, 24);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, size * 2.05F, -0.5F, true), var4.x, var4.y, var4.z
         );
         level.playSound(null, var4.x, var4.y, var4.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.65F, 0.2F);
         level.playSound(null, var4.x, var4.y, var4.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1.1F, 0.32F);
      }
   }

   public static void judgementStreak(LivingEntity target, float height, int density) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var18 = center(target);
         int amount = Math.max(8, density);

         for (int i = 0; i < amount; i++) {
            double angle = (Math.PI * 2) * i / amount;
            double spread = target.getBbWidth() * (1.4 + target.getRandom().nextDouble() * 3.3);
            double ox = Math.cos(angle) * spread + (target.getRandom().nextDouble() - 0.5) * 0.9;
            double oz = Math.sin(angle) * spread + (target.getRandom().nextDouble() - 0.5) * 0.9;
            Vec3 top = new Vec3(target.getX() + ox, target.getY() + height + target.getRandom().nextDouble() * 4.0, target.getZ() + oz);
            Vec3 mid = var18.add(ox * 0.22, target.getRandom().nextDouble() * 0.8, oz * 0.22);
            Vec3 bottom = new Vec3(target.getX() + ox * 0.08, target.getY() + target.getBbHeight() * 0.25, target.getZ() + oz * 0.08);
            line(level, top, mid, ParticleTypes.END_ROD);
            line(level, mid, bottom, ParticleTypes.WHITE_ASH);
            if (i % 3 == 0) {
               line(level, top, bottom, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get());
            }
         }

         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(),
            var18.x,
            var18.y,
            var18.z,
            Math.max(24, amount * 4),
            target.getBbWidth(),
            target.getBbHeight() * 0.75,
            target.getBbWidth(),
            0.26
         );
         level.sendParticles(ParticleTypes.FLASH, var18.x, var18.y, var18.z, 2, 0.0, 0.0, 0.0, 0.0);
         ringParticles(level, target.position().add(0.0, 0.15, 0.0), target.getBbWidth() * 3.2F, 72, ParticleTypes.END_ROD);
         radialBurst(level, var18, target.getBbWidth() * 3.0F, 26, ParticleTypes.END_ROD);
      }
   }

   public static void causalityPillar(LivingEntity target, float radius, int count) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var13 = center(target);
         int amount = Math.max(10, count);

         for (int i = 0; i < amount; i++) {
            double angle = (Math.PI * 2) * i / amount;
            double px = target.getX() + Math.cos(angle) * radius;
            double pz = target.getZ() + Math.sin(angle) * radius;
            line(level, new Vec3(px, target.getY() + target.getBbHeight() + 11.0, pz), var13, i % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH);
         }

         level.sendParticles(ParticleTypes.FLASH, var13.x, var13.y, var13.z, 2, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(),
            var13.x,
            var13.y,
            var13.z,
            35,
            radius * 0.18,
            target.getBbHeight() * 0.55,
            radius * 0.18,
            0.18
         );
         clockCircle(target, radius * 1.75F, 55, MagicCircleVariant.SPACE);
         verdictClock(target, radius * 1.35F, 45);
         EffectStorage.setCameraShake(target, radius * 2.3, 0.08F, 16);
      }
   }

   public static void finalRuptureExplosion(Entity entity, float size) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var5 = center(entity);
         float s = Math.max(2.0F, size);
         level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, var5.x, var5.y, var5.z, 1, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(ParticleTypes.EXPLOSION, var5.x, var5.y, var5.z, 18, s * 0.18, s * 0.14, s * 0.18, 0.0);
         level.sendParticles(ParticleTypes.FLASH, var5.x, var5.y, var5.z, 8, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, var5.x, var5.y, var5.z, Math.max(360, (int)(s * 48.0F)), s * 0.68, s * 0.28, s * 0.68, 0.25);
         level.sendParticles(ParticleTypes.END_ROD, var5.x, var5.y, var5.z, Math.max(220, (int)(s * 25.0F)), s * 0.46, s * 0.22, s * 0.46, 0.12);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            var5.x,
            var5.y,
            var5.z,
            Math.max(130, (int)(s * 11.0F)),
            s * 0.32,
            s * 0.2,
            s * 0.32,
            0.4
         );
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), var5.x, var5.y, var5.z, 8, s * 0.1, s * 0.1, s * 0.1, 0.05);
         ringParticles(level, var5, s * 1.08F, 190, ParticleTypes.WHITE_ASH);
         radialBurst(level, var5, s * 2.35F, 84, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get());
         timeFractureCage(level, var5, s * 1.45F, s * 0.55F, 44);
         brokenRealitySlices(level, var5, s * 2.1F, 46);
         implosionLinesAt(level, var5, s * 2.4F, 52);
         TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, s * 3.0F, -0.5F, true), var5.x, var5.y, var5.z);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, s * 3.8F, -0.5F, true), var5.x, var5.y + 0.1, var5.z
         );
         level.playSound(null, var5.x, var5.y, var5.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.8F, 0.14F);
         level.playSound(null, var5.x, var5.y, var5.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 0.32F);
         EffectStorage.setCameraShake(entity, s * 3.0, 0.22F, 42);
      }
   }

   public static void timeStopScreen(LivingEntity center, double radius, int ticks) {
      timeStopScreen(center, radius, ticks, 1);
   }

   public static void timeStopScreen(LivingEntity center, double radius, int ticks, int amplifier) {
      if (center.level() instanceof ServerLevel level) {
         for (ServerPlayer player : level.players()) {
            if (player.distanceTo(center) <= radius) {
               player.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), ticks, amplifier, false, false, true));
            }
         }
      }
   }

   public static void loopSnapScreen(LivingEntity target) {
      if (target instanceof ServerPlayer player) {
         player.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), 20, 2, false, false, true));
         player.playNotifySound(SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1.0F, 0.22F);
         player.playNotifySound(SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.55F, 0.28F);
      }

      ghostEcho(target, target.position(), 1.0F);
      grayPulse(target, 3.0F);
      clockShatter(target, 1.8F);
   }

   public static void finalChargeScreen(LivingEntity center, double radius) {
      timeStopScreen(center, radius, 160, 6);
      timeDome(center, (float)radius, 110);
      finalClock(center, (float)radius, 110);
   }

   public static void finalCollapseScreen(LivingEntity center, double radius) {
      timeStopScreen(center, radius, 70, 7);
      implosionLines(center, (float)radius, 58);
      finalRuptureExplosion(center, (float)radius * 0.38F);
   }

   public static void line(Level level, Vec3 start, Vec3 end, ParticleOptions particle) {
      if (level instanceof ServerLevel serverLevel) {
         Vec3 diff = end.subtract(start);
         int steps = Math.max(5, Mth.floor(diff.length() * 3.8));

         for (int i = 0; i <= steps; i++) {
            Vec3 pos = start.add(diff.scale((double)i / steps));
            serverLevel.sendParticles(particle, pos.x, pos.y, pos.z, 1, 0.018, 0.018, 0.018, 0.0);
         }
      }
   }

   public static void timeSkipTrail(Level level, Vec3 start, Vec3 end) {
      line(level, start, end, ParticleTypes.END_ROD);
      line(level, start.add(0.0, 0.18, 0.0), end.add(0.0, 0.18, 0.0), ParticleTypes.WHITE_ASH);
      line(level, start.add(0.0, 0.36, 0.0), end.add(0.0, 0.36, 0.0), ParticleTypes.POOF);
      if (level instanceof ServerLevel serverLevel) {
         Vec3 mid = start.add(end).scale(0.5);
         serverLevel.sendParticles(ParticleTypes.FLASH, end.x, end.y, end.z, 1, 0.0, 0.0, 0.0, 0.0);
         ringParticles(serverLevel, start, 1.2F, 40, ParticleTypes.WHITE_ASH);
         ringParticles(serverLevel, end, 1.5F, 48, ParticleTypes.END_ROD);
         brokenRealitySlices(serverLevel, mid, 1.7F, 10);
      }
   }

   public static void afterimage(LivingEntity entity, Vec3 pos) {
      if (entity.level() instanceof ServerLevel level) {
         ghostEcho(entity, pos, 1.0F);
         Vec3 var4 = pos.add(0.0, entity.getBbHeight() * 0.5, 0.0);
         level.sendParticles(
            ParticleTypes.WHITE_ASH, var4.x, var4.y, var4.z, 38, entity.getBbWidth() * 0.55, entity.getBbHeight() * 0.42, entity.getBbWidth() * 0.55, 0.026
         );
         level.sendParticles(
            ParticleTypes.END_ROD, var4.x, var4.y, var4.z, 12, entity.getBbWidth() * 0.42, entity.getBbHeight() * 0.32, entity.getBbWidth() * 0.42, 0.0
         );
         ringParticles(level, pos.add(0.0, 0.12, 0.0), Math.max(0.8F, entity.getBbWidth() * 1.5F), 30, ParticleTypes.WHITE_ASH);
      }
   }

   public static void rejectedFutureAfterimage(LivingEntity entity, Vec3 pos) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var4 = pos.add(0.0, entity.getBbHeight() * 0.5, 0.0);
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME, var4.x, var4.y, var4.z, 16, entity.getBbWidth() * 0.48, entity.getBbHeight() * 0.35, entity.getBbWidth() * 0.48, 0.0
         );
         level.sendParticles(
            ParticleTypes.WHITE_ASH, var4.x, var4.y, var4.z, 34, entity.getBbWidth() * 0.6, entity.getBbHeight() * 0.42, entity.getBbWidth() * 0.6, 0.018
         );
         crossSlash(level, var4, Math.max(0.9F, entity.getBbWidth() * 1.35F), ParticleTypes.WHITE_ASH);
         ringParticles(level, var4, Math.max(0.85F, entity.getBbWidth() * 1.4F), 30, ParticleTypes.END_ROD);
      }
   }

   public static void clockCircle(Entity entity, float radius, int life, MagicCircleVariant variant) {
      if (entity instanceof LivingEntity living) {
         MagicCircle.castMagicCircle(radius, life, entity.position().add(0.0, 0.05, 0.0), variant, living, null, 0, Pair.of(0.0, 0.0));
         if (entity.level() instanceof ServerLevel level) {
            ringParticles(level, entity.position().add(0.0, 0.08, 0.0), radius, Math.max(42, (int)(radius * 14.0F)), ParticleTypes.END_ROD);
            verticalHourMarks(level, entity.position().add(0.0, 0.1, 0.0), radius, 0.35F, 12);
         }
      }
   }

   public static void followClockCircle(LivingEntity entity, float radius, int life, MagicCircleVariant variant) {
      MagicCircle.castMagicCircle(radius, life, variant, entity, 0.0F, Vec3.ZERO, null, 0, Pair.of(0.0, 0.0));
      if (entity.level() instanceof ServerLevel level) {
         Vec3 c = center(entity);
         ringParticles(level, c, radius, Math.max(36, (int)(radius * 14.0F)), ParticleTypes.WHITE_ASH);
         tinyClockHands(level, c, radius, entity.tickCount);
      }
   }

   public static void overheadClock(LivingEntity entity, float radius, int life) {
      clockCircle(entity, radius, life, MagicCircleVariant.SPACE);
      if (entity.level() instanceof ServerLevel level) {
         Vec3 c = entity.position().add(0.0, entity.getBbHeight() + 5.0, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, c.x, c.y, c.z, 260, radius * 0.42, 0.18, radius * 0.42, 0.035);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), c.x, c.y, c.z, 40, radius * 0.22, 0.08, radius * 0.22, 0.08
         );
         ringParticles(level, c, radius, Math.max(110, (int)(radius * 12.0F)), ParticleTypes.END_ROD);
         clockHands(level, c, radius, entity.tickCount);
      }
   }

   public static void verdictClock(LivingEntity target, float radius, int life) {
      clockCircle(target, radius, life, MagicCircleVariant.SPACE);
      Vec3 c = center(target);
      clockDust(target, Math.max(0.9F, radius * 0.25F), 90);
      if (target.level() instanceof ServerLevel level) {
         ringParticles(level, c, radius, Math.max(50, (int)(radius * 14.0F)), ParticleTypes.END_ROD);
         ringParticles(level, c, radius * 0.65F, Math.max(40, (int)(radius * 10.0F)), ParticleTypes.WHITE_ASH);
         clockHands(level, c, radius, target.tickCount);
      }
   }

   public static void finalClock(Entity entity, float radius, int life) {
      if (entity instanceof LivingEntity living) {
         overheadClock(living, radius * 0.55F, life);
      }

      clockCircle(entity, radius, life, MagicCircleVariant.SPACE);
      if (entity.level() instanceof ServerLevel level) {
         Vec3 top = entity.position().add(0.0, 4.0, 0.0);
         Vec3 ground = entity.position().add(0.0, 0.18, 0.0);
         level.sendParticles(ParticleTypes.WHITE_ASH, top.x, top.y, top.z, 320, radius * 0.4, 0.25, radius * 0.4, 0.04);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), top.x, top.y, top.z, 90, radius * 0.22, 0.1, radius * 0.22, 0.1
         );
         ringParticles(level, ground, radius, 220, ParticleTypes.WHITE_ASH);
         radialBurst(level, top, radius * 0.62F, 54, ParticleTypes.END_ROD);
      }
   }

   public static void sound(Entity entity, SoundEvent sound, float volume, float pitch) {
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, volume, pitch);
   }

   public static void glassSound(Entity entity) {
      sound(entity, SoundEvents.GLASS_BREAK, 1.0F, 0.25F);
      sound(entity, SoundEvents.AMETHYST_BLOCK_RESONATE, 0.5F, 1.7F);
   }

   public static void teleportSound(Entity entity) {
      sound(entity, SoundEvents.ENDERMAN_TELEPORT, 0.95F, 1.9F);
      sound(entity, SoundEvents.AMETHYST_BLOCK_RESONATE, 0.65F, 1.65F);
      sound(entity, SoundEvents.CHORUS_FRUIT_TELEPORT, 0.55F, 0.75F);
   }

   public static void clockCrackSound(Entity entity) {
      sound(entity, SoundEvents.AMETHYST_BLOCK_RESONATE, 1.0F, 0.24F);
      sound(entity, SoundEvents.GLASS_BREAK, 0.42F, 0.52F);
      sound(entity, SoundEvents.CHAIN_BREAK, 0.45F, 0.7F);
   }

   public static void heavyImpact(Entity entity, float size) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var4 = center(entity);
         TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, size, -0.5F, true), var4.x, var4.y, var4.z);
         level.sendParticles(ParticleTypes.EXPLOSION, var4.x, var4.y, var4.z, 2, 0.0, 0.0, 0.0, 0.0);
         radialBurst(level, var4, size * 0.8F, 40, ParticleTypes.WHITE_ASH);
         ringParticles(level, var4, size, 70, ParticleTypes.END_ROD);
         EffectStorage.setCameraShake(entity, size * 1.6, 0.11F, 16);
      }
   }

   public static void cameraShake(Entity entity, double range, float strength, int ticks) {
      EffectStorage.setCameraShake(entity, range, strength, ticks);
   }

   public static void timeDome(Entity entity, float radius, int life) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var9 = center(entity);

         for (int r = 0; r < 5; r++) {
            float ring = radius * (0.25F + r * 0.16F);
            double y = (r - 2) * 1.15;
            ringParticles(level, var9.add(0.0, y, 0.0), ring, 120, r % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH);
         }

         timeFractureCage(level, var9, radius * 0.78F, radius * 0.36F, 48);
         brokenRealitySlices(level, var9, radius * 0.72F, 32);
         implosionLinesAt(level, var9, radius * 0.9F, 36);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, radius * 1.85F, -0.5F, true), var9.x, var9.y, var9.z
         );
      }
   }

   public static void implosionLines(Entity entity, float radius, int count) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var5 = center(entity);
         implosionLinesAt(level, var5, radius, count);
         level.sendParticles(ParticleTypes.FLASH, var5.x, var5.y, var5.z, 2, 0.0, 0.0, 0.0, 0.0);
      }
   }

   public static void fxChronicle(LivingEntity entity) {
      clockCircle(entity, 3.0F, 45, MagicCircleVariant.SPACE);
      grayPulse(entity, 3.5F);
      cameraShake(entity, 10.0, 0.025F, 8);
      if (entity.level() instanceof ServerLevel level) {
         wave(entity, 6.0F);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 1.8);
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 0.55F);
      }
   }

   public static void fxTimeSkip(LivingEntity entity, Vec3 start, Vec3 end) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var5 = start.add(end).scale(0.5);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, 4.5F, -0.5F, false), var5.x, var5.y + entity.getBbHeight() * 0.5, var5.z
         );
         TensuraParticleHelper.addServerParticlesAroundPos(
            entity.getRandom(), level, end.add(0.0, entity.getBbHeight() * 0.5, 0.0), (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), 2.4
         );
      }
   }

   public static void fxTimeSkipHit(LivingEntity owner, LivingEntity target) {
      rejectedFutureAfterimage(target, target.position().subtract(owner.getLookAngle().scale(1.8)));
      clockDust(target, 1.6F, 18);
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), 1.2);
      }
   }

   public static void fxMomentumTheft(LivingEntity entity, double range) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var5 = entity.getEyePosition().add(entity.getLookAngle().normalize().scale(range));
         TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), level, var5, ParticleTypes.END_ROD, 2.2);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(), 1.8);
         wave(entity, 3.5F);
      }
   }

   public static void fxMomentumTheftTarget(LivingEntity owner, Entity target) {
      clockDust(target, 1.1F, 10);
      glassWall(owner, target.position().add(0.0, target.getBbHeight() * 0.5, 0.0));
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundPos(
            owner.getRandom(), level, center(target), (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), 1.3
         );
      }
   }

   public static void fxBorrowedSeconds(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         wave(entity, 7.0F);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.WAX_ON, 2.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.SOUL.get(), 1.6);
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.1F, 0.45F);
      }
   }

   public static void fxBorrowedSecondsTarget(LivingEntity owner, LivingEntity target) {
      clockDust(target, 1.4F, 16);
      line(owner.level(), target.getEyePosition(), owner.getEyePosition(), ParticleTypes.WHITE_ASH);
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.SOUL.get(), 1.1);
      }
   }

   public static void fxEternalMoment(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.END_ROD, 1.4);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(), 1.0);
         wave(entity, entity.getBbWidth() * 4.5F);
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.55F, 1.75F);
      }
   }

   public static void fxEternalMomentTarget(LivingEntity owner, LivingEntity target) {
      rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(2.0)));
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 1.1);
      }
   }

   public static void fxObjectPermanence(LivingEntity owner, Entity target) {
      grayPulse(target, 3.2F);
      clockCircle(target, Math.max(2.2F, target.getBbWidth() * 3.8F), 36, MagicCircleVariant.SPACE);
      if (target.level() instanceof ServerLevel level) {
         Vec3 c = center(target);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, c, ParticleTypes.CRIT, 1.6);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, c, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 1.2);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, c, (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), 0.9);
         TensuraParticleHelper.spawnServerParticles(
            target.level(), TensuraParticleUtils.getColorlessWave(0.8F, target.getBbWidth() * 5.0F, -0.5F, true), c.x, c.y, c.z
         );
      }
   }

   public static void fxStasisRelease(LivingEntity owner, LivingEntity target) {
      finalRuptureExplosion(target, Math.max(4.0F, target.getBbWidth() * 4.0F));
      line(owner.level(), owner.getEyePosition(), target.getEyePosition(), ParticleTypes.END_ROD);
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), 2.4);
      }
   }

   public static void fxRewind(LivingEntity owner, LivingEntity target, Vec3 old) {
      if (owner.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundPos(
            owner.getRandom(), level, old.add(0.0, target.getBbHeight() * 0.5, 0.0), ParticleTypes.WHITE_ASH, 2.0
         );
         TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.REVERSE_PORTAL, 1.8);
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(), 1.2);
      }
   }

   public static void fxDelayedVerdict(LivingEntity owner, LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 1.5);
         wave(target, target.getBbWidth() * 4.0F);
      }
   }

   public static void fxEffectBeforeCause(LivingEntity owner, LivingEntity target) {
      judgementStreak(target, 12.0F, 16);
      rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(4.0)));
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), 2.2);
      }
   }

   public static void fxEffectBeforeCauseTarget(LivingEntity owner, LivingEntity target) {
      clockDust(target, 1.5F, 14);
      rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(2.6)));
   }

   public static void fxLoop(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         wave(entity, 14.0F);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.REVERSE_PORTAL, 3.0);
         TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), 2.5);
         level.playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.2F, 0.3F
         );
      }
   }

   public static void fxLoopTarget(LivingEntity owner, LivingEntity target) {
      verdictClock(target, Math.max(2.5F, target.getBbWidth() * 3.2F), 55);
      clockDust(target, 1.8F, 20);
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), 1.3);
      }
   }

   public static void fxCondemnedRepeat(LivingEntity owner, LivingEntity target) {
      rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(3.0)));
      verdictClock(target, Math.max(2.8F, target.getBbWidth() * 3.8F), 70);
      if (target.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), 1.8);
      }
   }

   public static void fxFutureExile(LivingEntity owner, LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var4 = center(target);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, ParticleTypes.REVERSE_PORTAL, 3.5);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, ParticleTypes.WHITE_ASH, 2.2);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, (ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get(), 2.5);
         TensuraParticleHelper.addServerParticlesAroundPos(
            owner.getRandom(), level, var4, (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), 2.6
         );
         waveAt(target, var4, target.getBbWidth() * 8.0F);
         level.playSound(null, var4.x, var4.y, var4.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.2F, 0.35F);
      }
   }

   public static void fxFutureExileResisted(LivingEntity owner, LivingEntity target) {
      clockShatter(target, 2.5F);
      rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(3.0)));
   }

   public static void fxPastBurial(LivingEntity owner, LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var4 = center(target);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, ParticleTypes.SCULK_SOUL, 2.6);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, ParticleTypes.REVERSE_PORTAL, 4.0);
         TensuraParticleHelper.addServerParticlesAroundPos(owner.getRandom(), level, var4, (ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get(), 3.2);
         TensuraParticleHelper.addServerParticlesAroundPos(
            owner.getRandom(), level, var4, (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), 2.2
         );
         waveAt(target, var4, target.getBbWidth() * 9.0F);
      }
   }

   public static void fxBanishmentHold(LivingEntity owner, LivingEntity target, boolean absolute) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var5 = center(target);
         level.sendParticles(
            absolute ? ParticleTypes.SCULK_SOUL : ParticleTypes.REVERSE_PORTAL,
            var5.x,
            var5.y,
            var5.z,
            absolute ? 22 : 12,
            target.getBbWidth(),
            target.getBbHeight() * 0.4,
            target.getBbWidth(),
            0.04
         );
         level.sendParticles(
            absolute
               ? (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get()
               : (TensuraParticleType)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(),
            var5.x,
            var5.y,
            var5.z,
            absolute ? 10 : 5,
            target.getBbWidth() * 0.45,
            target.getBbHeight() * 0.25,
            target.getBbWidth() * 0.45,
            0.1
         );
      }
   }

   public static void fxBanishmentReturn(LivingEntity owner, LivingEntity target, boolean absolute) {
      clockShatter(target, absolute ? 6.5F : 4.0F);
      finalRuptureExplosion(target, absolute ? 7.0F : 4.5F);
      line(
         owner.level(),
         owner.getEyePosition(),
         target.getEyePosition(),
         (ParticleOptions)(absolute ? (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get() : ParticleTypes.END_ROD)
      );
   }

   public static void fxVerdictDetonate(LivingEntity owner, LivingEntity target) {
      finalRuptureExplosion(target, Math.max(5.0F, target.getBbWidth() * 5.0F));
      line(owner.level(), owner.getEyePosition(), target.getEyePosition(), (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get());
   }

   private static void wave(Entity entity, float size) {
      waveAt(entity, center(entity), size);
   }

   private static void waveAt(Entity entity, Vec3 c, float size) {
      TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getColorlessWave(1.0F, size, -0.5F, true), c.x, c.y, c.z);
   }

   private static Vec3 center(Entity entity) {
      return entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);
   }

   private static void risingTimeDust(Entity entity, float size, int count) {
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.WHITE_ASH, entity.getX(), entity.getY() + 0.15, entity.getZ(), count, size, entity.getBbHeight() * 0.68, size, 0.1);
         level.sendParticles(
            ParticleTypes.END_ROD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.6,
            entity.getZ(),
            Math.max(4, count / 7),
            size * 0.45,
            entity.getBbHeight() * 0.35,
            size * 0.45,
            0.014
         );
      }
   }

   private static void orbit(LivingEntity entity, float radius, int count, float height, ParticleOptions particle) {
      if (entity.level() instanceof ServerLevel level) {
         double var13 = entity.tickCount * 0.13;
         double y = entity.getY() + entity.getBbHeight() * height;

         for (int i = 0; i < Math.max(1, count); i++) {
            double angle = var13 + (Math.PI * 2) * i / count;
            level.sendParticles(
               particle,
               entity.getX() + Math.cos(angle) * radius,
               y + Math.sin(angle * 2.0) * 0.25,
               entity.getZ() + Math.sin(angle) * radius,
               1,
               0.015,
               0.015,
               0.015,
               0.0
            );
         }
      }
   }

   private static void ringParticles(ServerLevel level, Vec3 center, float radius, int count, ParticleOptions particle) {
      int safeCount = Math.max(8, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         level.sendParticles(particle, center.x + Math.cos(angle) * radius, center.y, center.z + Math.sin(angle) * radius, 1, 0.01, 0.01, 0.01, 0.0);
      }
   }

   private static void radialBurst(ServerLevel level, Vec3 center, float radius, int count, ParticleOptions particle) {
      int safeCount = Math.max(6, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         line(level, center, center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius), particle);
      }
   }

   private static void crossSlash(ServerLevel level, Vec3 center, float size, ParticleOptions particle) {
      line(level, center.add(-size, size * 0.45, -size), center.add(size, -size * 0.45, size), particle);
      line(level, center.add(size, size * 0.45, -size), center.add(-size, -size * 0.45, size), particle);
      line(level, center.add(-size, 0.0, size), center.add(size, 0.0, -size), particle);
   }

   private static void spiral(Entity entity, float radius, float height, int count, ParticleOptions particle) {
      if (entity.level() instanceof ServerLevel level) {
         int var14 = Math.max(10, count);

         for (int i = 0; i < var14; i++) {
            double progress = (double)i / var14;
            double angle = progress * Math.PI * 4.3 + entity.tickCount * 0.08;
            double r = radius * (0.35 + progress * 0.65);
            level.sendParticles(
               particle, entity.getX() + Math.cos(angle) * r, entity.getY() + progress * height, entity.getZ() + Math.sin(angle) * r, 1, 0.01, 0.01, 0.01, 0.0
            );
         }
      }
   }

   private static void reverseSpiral(Entity entity, float radius, float height, int count, ParticleOptions particle) {
      if (entity.level() instanceof ServerLevel level) {
         int var14 = Math.max(10, count);

         for (int i = 0; i < var14; i++) {
            double progress = (double)i / var14;
            double angle = -progress * Math.PI * 4.3 - entity.tickCount * 0.06;
            double r = radius * (0.35 + progress * 0.65);
            level.sendParticles(
               particle, entity.getX() + Math.cos(angle) * r, entity.getY() + progress * height, entity.getZ() + Math.sin(angle) * r, 1, 0.01, 0.01, 0.01, 0.0
            );
         }
      }
   }

   private static void clockHands(ServerLevel level, Vec3 center, float radius, int tick) {
      double a1 = tick * 0.045;
      double a2 = -tick * 0.11;
      double a3 = tick * 0.18;
      line(level, center, center.add(Math.cos(a1) * radius * 0.78, 0.0, Math.sin(a1) * radius * 0.78), ParticleTypes.END_ROD);
      line(level, center, center.add(Math.cos(a2) * radius * 0.48, 0.0, Math.sin(a2) * radius * 0.48), ParticleTypes.WHITE_ASH);
      line(
         level,
         center,
         center.add(Math.cos(a3) * radius * 0.92, 0.0, Math.sin(a3) * radius * 0.92),
         (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get()
      );
   }

   private static void tinyClockHands(ServerLevel level, Vec3 center, float radius, int tick) {
      double a1 = tick * 0.08;
      double a2 = -tick * 0.18;
      line(level, center, center.add(Math.cos(a1) * radius * 0.8, 0.0, Math.sin(a1) * radius * 0.8), ParticleTypes.END_ROD);
      line(level, center, center.add(Math.cos(a2) * radius * 0.5, 0.0, Math.sin(a2) * radius * 0.5), ParticleTypes.WHITE_ASH);
   }

   private static void timeFractureCage(ServerLevel level, Vec3 center, float radius, float height, int count) {
      int safeCount = Math.max(8, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         double x = center.x + Math.cos(angle) * radius;
         double z = center.z + Math.sin(angle) * radius;
         line(level, new Vec3(x, center.y + height, z), new Vec3(x, center.y - height, z), i % 2 == 0 ? ParticleTypes.WHITE_ASH : ParticleTypes.END_ROD);
      }

      ringParticles(level, center.add(0.0, height, 0.0), radius, safeCount, ParticleTypes.END_ROD);
      ringParticles(level, center.add(0.0, -height, 0.0), radius, safeCount, ParticleTypes.WHITE_ASH);
   }

   private static void verticalHourMarks(ServerLevel level, Vec3 center, float radius, float height, int count) {
      int safeCount = Math.max(4, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         double x = center.x + Math.cos(angle) * radius;
         double z = center.z + Math.sin(angle) * radius;
         line(
            level,
            new Vec3(x, center.y + height * 0.5, z),
            new Vec3(x, center.y - height * 0.5, z),
            i % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH
         );
      }
   }

   private static void brokenRealitySlices(ServerLevel level, Vec3 center, float radius, int count) {
      int safeCount = Math.max(6, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         double offset = (i % 5 - 2) * 0.18;
         Vec3 a = center.add(Math.cos(angle) * radius * 0.35, offset, Math.sin(angle) * radius * 0.35);
         Vec3 b = center.add(Math.cos(angle + 0.35) * radius, offset + 0.35, Math.sin(angle + 0.35) * radius);
         line(level, a, b, (ParticleOptions)(i % 3 == 0 ? (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get() : ParticleTypes.WHITE_ASH));
      }
   }

   private static void implosionLinesAt(ServerLevel level, Vec3 center, float radius, int count) {
      int safeCount = Math.max(8, count);

      for (int i = 0; i < safeCount; i++) {
         double angle = (Math.PI * 2) * i / safeCount;
         double height = i % 7 * 0.42 - 1.2;
         Vec3 outer = center.add(Math.cos(angle) * radius, height, Math.sin(angle) * radius);
         line(level, outer, center, i % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH);
      }
   }

   private static void ghostEcho(LivingEntity entity, Vec3 pos, float scale) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var7 = pos.add(0.0, entity.getBbHeight() * 0.5, 0.0);
         float width = Math.max(0.35F, entity.getBbWidth() * scale);
         float height = Math.max(0.75F, entity.getBbHeight() * scale);
         bodyOutline(level, var7, width, height, ParticleTypes.WHITE_ASH);
         bodyOutline(level, var7.add(0.05, 0.0, 0.05), width * 0.72F, height * 0.88F, ParticleTypes.END_ROD);
      }
   }

   private static void bodyOutline(ServerLevel level, Vec3 center, float width, float height, ParticleOptions particle) {
      Vec3 p1 = center.add(-width, -height * 0.5F, -width);
      Vec3 p2 = center.add(width, -height * 0.5F, -width);
      Vec3 p3 = center.add(width, -height * 0.5F, width);
      Vec3 p4 = center.add(-width, -height * 0.5F, width);
      Vec3 q1 = p1.add(0.0, height, 0.0);
      Vec3 q2 = p2.add(0.0, height, 0.0);
      Vec3 q3 = p3.add(0.0, height, 0.0);
      Vec3 q4 = p4.add(0.0, height, 0.0);
      line(level, p1, q1, particle);
      line(level, p2, q2, particle);
      line(level, p3, q3, particle);
      line(level, p4, q4, particle);
      line(level, q1, q2, particle);
      line(level, q2, q3, particle);
      line(level, q3, q4, particle);
      line(level, q4, q1, particle);
   }
}
