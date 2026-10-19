package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class FeldwayAwakeningEvent {
   private FeldwayAwakeningEvent() {
   }

   public static void tick(FeldwayAwakeningEntity entity, ServerLevel level, int age, int duration) {
      float progress = Mth.clamp((float)age / duration, 0.0F, 1.0F);
      timeDistortion(entity, level, age, progress);
      bodyLiftPulse(entity, level, age, progress);
      entityGravityDrag(entity, level, age, progress);
      skyPressure(entity, level, age, progress);
      finalCompression(entity, level, age, duration, progress);
   }

   public static void finish(FeldwayAwakeningEntity entity, ServerLevel level) {
      Vec3 center = entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);
      level.playSound(null, center.x, center.y + 18.0, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 7.0F, 0.42F);
      level.playSound(null, center.x, center.y + 22.0, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 7.0F, 0.32F);
      level.playSound(null, center.x, center.y + 30.0, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 6.0F, 0.18F);
      level.playSound(null, center.x, center.y + 44.0, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 9.0F, 0.25F);
      level.sendParticles(ParticleTypes.FLASH, center.x, center.y, center.z, 42, 0.0, 0.0, 0.0, 0.0);
      level.sendParticles(ParticleTypes.SONIC_BOOM, center.x, center.y, center.z, 6, 0.0, 0.0, 0.0, 0.0);
      level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y, center.z, 5, 0.0, 0.0, 0.0, 0.0);
      level.sendParticles(ParticleTypes.END_ROD, center.x, center.y + 6.0, center.z, 1200, 18.0, 18.0, 18.0, 0.75);
      level.sendParticles(ParticleTypes.REVERSE_PORTAL, center.x, center.y + 4.0, center.z, 900, 14.0, 12.0, 14.0, 1.0);
      level.sendParticles(ParticleTypes.WHITE_ASH, center.x, center.y + 4.0, center.z, 1800, 22.0, 14.0, 22.0, 0.45);
      AABB area = new AABB(center, center).inflate(58.0, 52.0, 58.0);

      for (Entity target : level.getEntitiesOfClass(Entity.class, area, Entity::isAlive)) {
         if (target != entity && !(target instanceof Player player && player.isCreative())) {
            Vec3 away = target.position().subtract(center);
            if (away.lengthSqr() < 0.01) {
               away = new Vec3(level.random.nextDouble() - 0.5, 0.5, level.random.nextDouble() - 0.5);
            }

            target.setNoGravity(false);
            target.setDeltaMovement(target.getDeltaMovement().add(away.normalize().scale(5.2).add(0.0, 3.2, 0.0)));
            target.hurtMarked = true;
         }
      }
   }

   private static void timeDistortion(FeldwayAwakeningEntity entity, ServerLevel level, int age, float progress) {
      if (age >= 80) {
         float violent = Mth.clamp((progress - 0.45F) / 0.55F, 0.0F, 1.0F);
         violent *= violent;
         double visual = entity.getVisualDayTime();
         visual += 1200.0 + progress * 28000.0 + violent * 160000.0;
         visual += Math.sin(age * 0.36) * 8000.0;
         visual += Math.sin(age * 1.1) * 24000.0 * violent;
         entity.setVisualTime(level, (long)visual);
         Vec3 center = entity.position();
         if (age % 14 == 0) {
            level.sendParticles(ParticleTypes.FLASH, center.x, center.y + 34.0, center.z, progress > 0.72F ? 3 : 1, 0.0, 0.0, 0.0, 0.0);
         }

         if (age % 24 == 0) {
            level.playSound(
               null, center.x, center.y + 26.0, center.z, SoundEvents.BEACON_AMBIENT, SoundSource.AMBIENT, 1.8F + progress * 3.0F, 0.08F + progress * 0.14F
            );
         }

         if (age % 34 == 0 && progress > 0.5F) {
            level.playSound(null, center.x, center.y + 32.0, center.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.AMBIENT, 3.4F, 0.05F + progress * 0.08F);
         }
      }
   }

   private static void bodyLiftPulse(FeldwayAwakeningEntity entity, ServerLevel level, int age, float progress) {
      Vec3 center = entity.position().add(0.0, 0.65, 0.0);
      if (age % 10 == 0) {
         double radius = 1.4 + progress * 24.0;
         ring(level, center, radius, progress);
      }

      if (age % 5 == 0) {
         level.sendParticles(
            ParticleTypes.REVERSE_PORTAL,
            center.x,
            center.y + 0.5,
            center.z,
            12 + (int)(progress * 28.0F),
            0.7 + progress * 1.8,
            0.45 + progress,
            0.7 + progress * 1.8,
            0.22 + progress * 0.34
         );
         level.sendParticles(
            ParticleTypes.END_ROD,
            center.x,
            center.y + 1.0,
            center.z,
            6 + (int)(progress * 18.0F),
            0.45 + progress,
            0.35 + progress,
            0.45 + progress,
            0.08 + progress * 0.18
         );
      }

      if (age == 120 || age == 230 || age == 350 || age == 450) {
         level.sendParticles(ParticleTypes.FLASH, center.x, center.y + 1.2, center.z, 8, 0.8, 0.8, 0.8, 0.0);
         level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 4.0F, 0.16F);
      }
   }

   private static void entityGravityDrag(FeldwayAwakeningEntity entity, ServerLevel level, int age, float progress) {
      if (age >= 170 && age % 2 == 0) {
         Vec3 center = entity.position();
         double radius = 24.0 + progress * 82.0;
         AABB area = new AABB(center, center).inflate(radius, 90.0, radius);

         for (Entity target : level.getEntitiesOfClass(Entity.class, area, Entity::isAlive)) {
            if (target != entity && !(target instanceof Player player && player.isCreative())) {
               Vec3 toCenter = new Vec3(center.x - target.getX(), 0.0, center.z - target.getZ());
               if (toCenter.lengthSqr() < 0.01) {
                  toCenter = new Vec3(1.0, 0.0, 0.0);
               }

               Vec3 tangent = new Vec3(-toCenter.z, 0.0, toCenter.x).normalize();
               Vec3 pull = toCenter.normalize().scale(0.012 + progress * 0.055);
               Vec3 spiral = tangent.scale(0.028 + progress * 0.105);
               Vec3 lift = new Vec3(0.0, 0.018 + progress * 0.12, 0.0);
               target.setDeltaMovement(target.getDeltaMovement().scale(0.88).add(pull).add(spiral).add(lift));
               target.hurtMarked = true;
            }
         }
      }
   }

   private static void skyPressure(FeldwayAwakeningEntity entity, ServerLevel level, int age, float progress) {
      if (age >= 130 && age % 8 == 0) {
         Vec3 center = entity.position();
         double radius = 20.0 + progress * 78.0;
         int count = 24 + (int)(progress * 74.0F);
         level.sendParticles(
            ParticleTypes.END_ROD,
            center.x,
            center.y + 38.0 + progress * 36.0,
            center.z,
            count,
            radius * 0.45,
            16.0 + progress * 24.0,
            radius * 0.45,
            0.18 + progress * 0.3
         );
         level.sendParticles(
            ParticleTypes.WHITE_ASH,
            center.x,
            center.y + 30.0 + progress * 26.0,
            center.z,
            count * 2,
            radius * 0.55,
            14.0 + progress * 20.0,
            radius * 0.55,
            0.06 + progress * 0.15
         );
         if (progress > 0.65F && age % 24 == 0) {
            level.playSound(null, center.x, center.y + 50.0, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 4.5F, 0.2F);
         }
      }
   }

   private static void finalCompression(FeldwayAwakeningEntity entity, ServerLevel level, int age, int duration, float progress) {
      int left = duration - age;
      if (left <= 90) {
         Vec3 center = entity.position().add(0.0, 2.0, 0.0);
         if (age % 4 == 0) {
            level.sendParticles(ParticleTypes.END_ROD, center.x, center.y + 7.0, center.z, 120, 16.0, 14.0, 16.0, 0.34);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, center.x, center.y + 4.0, center.z, 90, 10.0, 9.0, 10.0, 0.66);
         }

         if (left == 80 || left == 60 || left == 40 || left == 20 || left == 8) {
            level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.HOSTILE, 5.0F, 0.05F + left * 0.0015F);
            level.sendParticles(ParticleTypes.FLASH, center.x, center.y + 3.0, center.z, 7, 0.6, 0.6, 0.6, 0.0);
         }

         if (left <= 20) {
            level.sendParticles(ParticleTypes.WHITE_ASH, center.x, center.y + 8.0, center.z, 420, 22.0, 16.0, 22.0, 0.24);
         }
      }
   }

   private static void ring(ServerLevel level, Vec3 center, double radius, float progress) {
      int points = 56;

      for (int i = 0; i < points; i++) {
         double angle = (Math.PI * 2) * i / points;
         double x = center.x + Math.cos(angle) * radius;
         double z = center.z + Math.sin(angle) * radius;
         double y = center.y + Math.sin(angle * 2.0 + progress * 18.0) * 0.35;
         level.sendParticles(i % 3 == 0 ? ParticleTypes.END_ROD : ParticleTypes.REVERSE_PORTAL, x, y, z, 1, 0.0, 0.04, 0.0, 0.05 + progress * 0.08);
      }
   }
}
