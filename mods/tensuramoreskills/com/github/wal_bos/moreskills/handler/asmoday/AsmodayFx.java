package com.github.wal_bos.moreskills.handler.asmoday;

import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public final class AsmodayFx {
   private AsmodayFx() {
   }

   public static void spawnSpaceOpen(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         double var8 = player.getX();
         double y = player.getY() + player.getBbHeight() * 0.5;
         double z = player.getZ();
         level.sendParticles(ParticleTypes.PORTAL, var8, y, z, 70, 0.75, 1.0, 0.75, 0.6);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(), var8, y, z, 22, 0.45, 0.7, 0.45, 0.16);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), var8, y, z, 12, 0.35, 0.55, 0.35, 0.12);
      }
   }

   public static void spawnSpaceClose(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         double var8 = player.getX();
         double y = player.getY() + player.getBbHeight() * 0.5;
         double z = player.getZ();
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, var8, y, z, 90, 0.85, 1.1, 0.85, 0.55);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), var8, y, z, 18, 0.45, 0.7, 0.45, 0.18);
      }
   }

   public static void spawnSpaceArrival(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         double var8 = player.getX();
         double y = player.getY() + player.getBbHeight() * 0.5;
         double z = player.getZ();
         level.sendParticles(ParticleTypes.PORTAL, var8, y, z, 110, 0.9, 1.2, 0.9, 0.85);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(), var8, y, z, 35, 0.55, 0.9, 0.55, 0.22);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), var8, y, z, 25, 0.45, 0.75, 0.45, 0.2);
      }
   }
}
