package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SkullCandleBlock extends AbstractHeadBlock {
   private final Supplier<SimpleParticleType> particle;

   public SkullCandleBlock(Supplier<SimpleParticleType> particle, Properties properties) {
      super(properties);
      this.particle = particle;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      double x = pos.getX() + 0.5;
      double y = pos.getY() + 1.0;
      double z = pos.getZ() + 0.5;
      float f = random.nextFloat();
      if (f < 0.3F) {
         level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
         if (f < 0.17F) {
            level.playLocalSound(
               x + 0.5, y + 0.5, z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false
            );
         }
      }

      level.addParticle((ParticleOptions)this.particle.get(), x, y, z, 0.0, 0.0, 0.0);
   }
}
