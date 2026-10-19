package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class AbstractParticleSpawnerBlock extends Block {
   public static final IntegerProperty RADIUS = IntegerProperty.create("particle_radius", 1, 10);

   public AbstractParticleSpawnerBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(RADIUS, 1));
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      int radius = (Integer)state.getValue(RADIUS);
      MutableBlockPos mutablePos = new MutableBlockPos();

      for (int partCount = 0; partCount < this.getParticleCountPerSpawn(state); partCount++) {
         mutablePos.set(x + Mth.nextInt(random, -radius, radius), y + Mth.nextInt(random, -radius, radius), z + Mth.nextInt(random, -radius, radius));
         BlockState offState = level.getBlockState(mutablePos);
         if (!offState.isCollisionShapeFullBlock(level, mutablePos)) {
            level.addParticle(
               this.getParticlesToSpawn(),
               mutablePos.getX() + random.nextDouble(),
               mutablePos.getY() + random.nextDouble(),
               mutablePos.getZ() + random.nextDouble(),
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   public abstract ParticleOptions getParticlesToSpawn();

   public abstract int getParticleCountPerSpawn(BlockState var1);

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{RADIUS});
   }
}
