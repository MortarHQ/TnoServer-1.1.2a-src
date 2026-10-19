package net.regions_unexplored.world.level.block.cave;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUParticleTypes;

public class PrismariteBlock extends BushBlock {
   public static final MapCodec<? extends PrismariteBlock> CODEC = simpleCodec(PrismariteBlock::new);
   protected static final float AABB_OFFSET = 3.0F;
   protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);

   public PrismariteBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Vec3 vec3 = state.getOffset(getter, pos);
      return SHAPE.move(vec3.x, vec3.y, vec3.z);
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.isSolidRender(level, pos);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (!(random.nextFloat() >= 0.05 * RUConfigHandler.CLIENT.particleRates.prismarite)) {
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
