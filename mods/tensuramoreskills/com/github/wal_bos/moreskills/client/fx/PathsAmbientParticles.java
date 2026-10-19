package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public class PathsAmbientParticles {
   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (!mc.isPaused()) {
         ClientLevel level = mc.level;
         if (level != null && mc.player != null) {
            if (level.dimension().equals(PathsClientState.PATHS)) {
               RandomSource r = level.random;
               double px = mc.player.getX();
               double py = mc.player.getY();
               double pz = mc.player.getZ();
               FeldwayAwakeningEntity awakening = findAwakening(level, mc.player.position());
               if ((level.getGameTime() & 3L) == 0L) {
                  spawnSoftGlow(level, r, px, py, pz);
                  if (r.nextInt(5) == 0) {
                     spawnEndRod(level, r, px, py, pz);
                  }

                  if (r.nextInt(10) == 0) {
                     spawnReversePortalRing(level, r, px, py, pz, 18.0F, 6.0, 12.0);
                  }
               }

               if (awakening != null) {
                  spawnAwakeningPathsParticles(level, r, awakening);
               }
            }
         }
      }
   }

   private static FeldwayAwakeningEntity findAwakening(ClientLevel level, Vec3 playerPos) {
      FeldwayAwakeningEntity best = null;
      double bestDist = Double.MAX_VALUE;

      for (Entity entity : level.entitiesForRendering()) {
         if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive()) {
            double dist = awakening.position().distanceToSqr(playerPos);
            if (dist < bestDist && dist <= 129600.0) {
               bestDist = dist;
               best = awakening;
            }
         }
      }

      return best;
   }

   private static void spawnSoftGlow(ClientLevel level, RandomSource r, double px, double py, double pz) {
      double x = px + (r.nextDouble() - 0.5) * 30.0;
      double y = py + 3.0 + r.nextDouble() * 12.0;
      double z = pz + (r.nextDouble() - 0.5) * 30.0;
      level.addParticle(ParticleTypes.GLOW, x, y, z, 0.0, 0.008, 0.0);
   }

   private static void spawnEndRod(ClientLevel level, RandomSource r, double px, double py, double pz) {
      double x = px + (r.nextDouble() - 0.5) * 22.0;
      double y = py + 2.0 + r.nextDouble() * 9.0;
      double z = pz + (r.nextDouble() - 0.5) * 22.0;
      double vx = (r.nextDouble() - 0.5) * 0.003;
      double vy = 0.002 + r.nextDouble() * 0.002;
      double vz = (r.nextDouble() - 0.5) * 0.003;
      level.addParticle(ParticleTypes.END_ROD, x, y, z, vx, vy, vz);
   }

   private static void spawnReversePortalRing(ClientLevel level, RandomSource r, double px, double py, double pz, float radius, double minY, double ySpan) {
      float ang = r.nextFloat() * (float) (Math.PI * 2);
      double x = px + Math.cos(ang) * radius;
      double z = pz + Math.sin(ang) * radius;
      double y = py + minY + r.nextDouble() * ySpan;
      level.addParticle(ParticleTypes.REVERSE_PORTAL, x, y, z, 0.0, 0.02, 0.0);
   }

   private static void spawnAwakeningPathsParticles(ClientLevel level, RandomSource r, FeldwayAwakeningEntity awakening) {
      float progress = awakening.getAwakeningProgress();
      float power = smooth(Mth.clamp((progress - 0.15F) / 0.85F, 0.0F, 1.0F));
      float climax = smooth(Mth.clamp((progress - 0.68F) / 0.32F, 0.0F, 1.0F));
      float finalBurst = smooth(Mth.clamp((progress - 0.88F) / 0.12F, 0.0F, 1.0F));
      Vec3 center = awakening.position().add(0.0, 2.0, 0.0);
      int baseCount = 4 + (int)(power * 10.0F) + (int)(climax * 18.0F);

      for (int i = 0; i < baseCount; i++) {
         double angle = r.nextDouble() * Math.PI * 2.0;
         double radius = 10.0 + r.nextDouble() * (42.0 + climax * 72.0);
         double height = 8.0 + r.nextDouble() * (46.0 + climax * 70.0);
         double x = center.x + Math.cos(angle) * radius;
         double z = center.z + Math.sin(angle) * radius;
         double y = center.y + height;
         double inwardX = (center.x - x) * (0.002 + climax * 0.006);
         double inwardZ = (center.z - z) * (0.002 + climax * 0.006);
         double vy = -0.01 - climax * 0.03 + r.nextDouble() * 0.008;
         level.addParticle(i % 3 == 0 ? ParticleTypes.END_ROD : ParticleTypes.GLOW, x, y, z, inwardX, vy, inwardZ);
      }

      if (level.getGameTime() % 2L == 0L) {
         int ringCount = 18 + (int)(climax * 46.0F);
         double ringRadius = 18.0 + power * 42.0 + finalBurst * 32.0;
         double y = center.y + 10.0 + climax * 30.0;

         for (int i = 0; i < ringCount; i++) {
            double angle = (float) (Math.PI * 2) * i / ringCount + level.getGameTime() * 0.04;
            double x = center.x + Math.cos(angle) * ringRadius;
            double z = center.z + Math.sin(angle) * ringRadius;
            double vx = -Math.sin(angle) * (0.01 + climax * 0.03);
            double vz = Math.cos(angle) * (0.01 + climax * 0.03);
            level.addParticle(i % 2 == 0 ? ParticleTypes.REVERSE_PORTAL : ParticleTypes.END_ROD, x, y, z, vx, 0.018 + finalBurst * 0.035, vz);
         }
      }

      if (climax > 0.25F && r.nextInt(2) == 0) {
         int rain = 12 + (int)(climax * 26.0F);

         for (int i = 0; i < rain; i++) {
            double angle = r.nextDouble() * Math.PI * 2.0;
            double radius = r.nextDouble() * (28.0 + climax * 92.0);
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            double y = center.y + 72.0 + r.nextDouble() * 96.0;
            double vx = (r.nextDouble() - 0.5) * 0.012;
            double vy = -0.045 - r.nextDouble() * 0.075 - finalBurst * 0.1;
            double vz = (r.nextDouble() - 0.5) * 0.012;
            level.addParticle(i % 4 == 0 ? ParticleTypes.WHITE_ASH : ParticleTypes.END_ROD, x, y, z, vx, vy, vz);
         }
      }

      if (finalBurst > 0.04F) {
         int burst = 10 + (int)(finalBurst * 44.0F);

         for (int i = 0; i < burst; i++) {
            double angle = r.nextDouble() * Math.PI * 2.0;
            double speed = 0.12 + r.nextDouble() * 0.42 + finalBurst * 0.28;
            double x = center.x + (r.nextDouble() - 0.5) * 3.0;
            double y = center.y + 2.0 + r.nextDouble() * 8.0;
            double z = center.z + (r.nextDouble() - 0.5) * 3.0;
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.05 + r.nextDouble() * 0.32;
            level.addParticle(i % 3 == 0 ? ParticleTypes.FLASH : ParticleTypes.GLOW, x, y, z, vx, vy, vz);
         }
      }
   }

   private static float smooth(float value) {
      return value * value * (3.0F - 2.0F * value);
   }
}
