package net.regions_unexplored.world.level.block.other;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AshVentBlock extends Block {
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean spawnParticles = true;

   public AshVentBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.stateDefinition.any());
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      for (int i = 0; i < 10; i++) {
         makeParticles(level, pos, true);
      }

      if (random.nextInt(10) == 0) {
         level.playLocalSound(
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            SoundEvents.CAMPFIRE_CRACKLE,
            SoundSource.BLOCKS,
            0.5F + random.nextFloat(),
            random.nextFloat() * 0.7F + 0.6F,
            false
         );
      }

      for (int i = 0; i < 2; i++) {
         if (this.spawnParticles) {
            for (int b = 0; b < random.nextInt(1) + 1; b++) {
               level.addParticle(
                  ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, random.nextFloat() / 2.0F, 5.0E-5, random.nextFloat() / 2.0F
               );
            }
         }
      }
   }

   public static void makeParticles(Level level, BlockPos pos, boolean bool) {
      RandomSource randomsource = level.getRandom();
      SimpleParticleType simpleparticletype = ParticleTypes.SMOKE;
      level.addAlwaysVisibleParticle(
         simpleparticletype,
         true,
         pos.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (randomsource.nextBoolean() ? 1 : -1),
         pos.getY() + randomsource.nextDouble() + randomsource.nextDouble(),
         pos.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (randomsource.nextBoolean() ? 1 : -1),
         0.0,
         0.07,
         0.0
      );
      if (bool) {
         level.addParticle(
            ParticleTypes.SMOKE,
            pos.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (randomsource.nextBoolean() ? 1 : -1),
            pos.getY() + 0.4,
            pos.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (randomsource.nextBoolean() ? 1 : -1),
            0.0,
            0.005,
            0.0
         );
      }
   }

   public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type) {
      return false;
   }
}
