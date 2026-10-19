package net.regions_unexplored.world.level.block.cave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUParticleTypes;

public class PrismariteDoubleBlock extends DoublePlantBlock {
   public PrismariteDoubleBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER));
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.isSolidRender(level, pos);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (!(random.nextFloat() >= 0.1 * (0.1 * RUConfigHandler.CLIENT.particleRates.prismarite))) {
         int plantX = pos.getX();
         int plantY = pos.getY();
         int plantZ = pos.getZ();
         MutableBlockPos ambientPos = new MutableBlockPos();

         for (int i = 0; i < 2; i++) {
            ambientPos.set(plantX + Mth.nextInt(random, -2, 2), plantY + random.nextInt(3), plantZ + Mth.nextInt(random, -2, 2));
            BlockState particlePosState = level.getBlockState(ambientPos);
            if (!particlePosState.isCollisionShapeFullBlock(level, ambientPos)) {
               level.addParticle(
                  (ParticleOptions)RUParticleTypes.PRISMARITE_SPARKLE.get(),
                  ambientPos.getX() + random.nextDouble(),
                  ambientPos.getY() + random.nextDouble(),
                  ambientPos.getZ() + random.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }
   }
}
