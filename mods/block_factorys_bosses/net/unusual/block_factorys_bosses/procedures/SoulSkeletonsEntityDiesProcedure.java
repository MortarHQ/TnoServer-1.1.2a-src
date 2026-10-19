package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

public class SoulSkeletonsEntityDiesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.sendParticles(ParticleTypes.SOUL, x, y + 1.2, z, Mth.nextInt(RandomSource.create(), 5, 10), 0.4, 0.4, 0.4, 0.0);
      }

      if (world instanceof ServerLevel _level) {
         _level.sendParticles(
            (SimpleParticleType)BossesRiseParticleTypes.FANCY_SMOKE.get(), x, y + 1.2, z, Mth.nextInt(RandomSource.create(), 3, 5), 0.4, 0.4, 0.4, 0.0
         );
      }

      if (world instanceof Level _level) {
         if (!_level.isClientSide()) {
            _level.playSound(
               null,
               BlockPos.containing(x, y, z),
               SoundEvents.SOUL_SAND_BREAK,
               SoundSource.HOSTILE,
               1.0F,
               (float)Mth.nextDouble(RandomSource.create(), 0.4, 0.7)
            );
         } else {
            _level.playLocalSound(
               x, y, z, SoundEvents.SOUL_SAND_BREAK, SoundSource.HOSTILE, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.4, 0.7), false
            );
         }
      }
   }
}
