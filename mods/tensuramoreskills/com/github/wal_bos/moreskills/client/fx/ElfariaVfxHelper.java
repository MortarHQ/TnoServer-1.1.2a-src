package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class ElfariaVfxHelper {
   private ElfariaVfxHelper() {
   }

   public static ClientLevel level() {
      return Minecraft.getInstance().level;
   }

   public static float hash(long seed, long salt) {
      long x = seed + salt * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (float)((x & 16777215L) / 1.6777216E7);
   }

   public static void particle(ClientLevel level, ParticleOptions option, double x, double y, double z, double vx, double vy, double vz) {
      if (level != null) {
         level.addParticle(option, x, y, z, vx, vy, vz);
      }
   }

   public static void snow(ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
      particle(level, ParticleTypes.SNOWFLAKE, x, y, z, vx, vy, vz);
   }

   public static void glint(ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
      particle(level, ParticleTypes.SNOWFLAKE, x, y, z, vx, vy, vz);
   }

   public static void cloud(ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
      particle(level, ParticleTypes.CLOUD, x, y, z, vx, vy, vz);
   }

   public static void ring(ClientLevel level, double x, double y, double z, double radius, int count, long seed, double yWave) {
      for (int i = 0; i < count; i++) {
         double a = i * Math.PI * 2.0 / count;
         double r = radius * (0.94 + hash(seed, i) * 0.12);
         double px = x + Math.cos(a) * r;
         double pz = z + Math.sin(a) * r;
         double py = y + Math.sin(a * 4.0 + hash(seed, 900 + i) * 6.283) * yWave;
         snow(level, px, py, pz, Math.cos(a) * 0.015, 0.01, Math.sin(a) * 0.015);
      }
   }

   public static void beam(ClientLevel level, Vec3 start, Vec3 end, int count, double spread, long seed) {
      Vec3 delta = end.subtract(start);

      for (int i = 0; i < count; i++) {
         double f = count <= 1 ? 0.0 : (double)i / (count - 1);
         Vec3 pos = start.add(delta.scale(f));
         double ox = (hash(seed, 1000 + i) - 0.5) * spread;
         double oy = (hash(seed, 2000 + i) - 0.5) * spread;
         double oz = (hash(seed, 3000 + i) - 0.5) * spread;
         snow(level, pos.x + ox, pos.y + oy, pos.z + oz, 0.0, 0.0, 0.0);
      }
   }

   public static double lerp(double f, double a, double b) {
      return Mth.lerp(f, a, b);
   }
}
