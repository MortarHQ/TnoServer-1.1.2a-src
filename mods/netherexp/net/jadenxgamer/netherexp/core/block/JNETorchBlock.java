package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class JNETorchBlock {
   public static class Standing extends TorchBlock {
      private final Supplier<SimpleParticleType> particle;

      public Standing(Supplier<SimpleParticleType> particle, Properties properties) {
         super(ParticleTypes.FLAME, properties);
         this.particle = particle;
      }

      public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
         double x = pos.getX() + 0.5;
         double y = pos.getY() + 0.7;
         double z = pos.getZ() + 0.5;
         level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
         level.addParticle((ParticleOptions)this.particle.get(), x, y, z, 0.0, 0.0, 0.0);
      }
   }

   public static class Wall extends WallTorchBlock {
      private final Supplier<SimpleParticleType> particle;

      public Wall(Supplier<SimpleParticleType> particle, Properties properties) {
         super(ParticleTypes.FLAME, properties);
         this.particle = particle;
      }

      public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
         Direction direction = (Direction)state.getValue(FACING);
         double x = pos.getX() + 0.5;
         double y = pos.getY() + 0.7;
         double z = pos.getZ() + 0.5;
         Direction facing = direction.getOpposite();
         level.addParticle(ParticleTypes.SMOKE, x + 0.27 * facing.getStepX(), y + 0.22, z + 0.27 * facing.getStepZ(), 0.0, 0.0, 0.0);
         level.addParticle((ParticleOptions)this.particle.get(), x + 0.27 * facing.getStepX(), y + 0.22, z + 0.27 * facing.getStepZ(), 0.0, 0.0, 0.0);
      }
   }
}
