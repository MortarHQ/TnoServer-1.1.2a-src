package net.regions_unexplored.world.level.block.cave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.RUParticleTypes;
import net.regions_unexplored.world.level.block.state.properties.HangingPrismariteShape;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class HangingPrismariteBlock extends Block {
   public static final EnumProperty<HangingPrismariteShape> SHAPE = RuBlockStateProperties.HANGING_PRISMARITE_SHAPE;
   protected static final VoxelShape BOX = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public HangingPrismariteBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SHAPE, HangingPrismariteShape.TIP));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
      stateBuilder.add(new Property[]{SHAPE});
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Vec3 vec3 = state.getOffset(getter, pos);
      return BOX.move(vec3.x, vec3.y, vec3.z);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.above();
      return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.isFaceSturdy(getter, pos, Direction.DOWN) || state.is(RUBlocks.HANGING_PRISMARITE.get());
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
      HangingPrismariteShape shape = (HangingPrismariteShape)state.getValue(SHAPE);
      if (level.getBlockState(pos.below()) == RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(SHAPE, HangingPrismariteShape.TIP)) {
         shape = HangingPrismariteShape.FRUSTUM;
      } else if (level.getBlockState(pos.below()) == RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(SHAPE, HangingPrismariteShape.FRUSTUM)) {
         shape = HangingPrismariteShape.COLUMN;
      } else if (!level.getBlockState(pos.below()).is(RUBlocks.HANGING_PRISMARITE.get())) {
         shape = HangingPrismariteShape.TIP;
      }

      return !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : (BlockState)state.setValue(SHAPE, shape);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      if (context.getLevel().getBlockState(context.getClickedPos().below())
            == RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(SHAPE, HangingPrismariteShape.FRUSTUM)
         || context.getLevel().getBlockState(context.getClickedPos().below())
            == RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(SHAPE, HangingPrismariteShape.COLUMN)) {
         return (BlockState)this.defaultBlockState().setValue(SHAPE, HangingPrismariteShape.COLUMN);
      } else {
         return context.getLevel().getBlockState(context.getClickedPos().below())
               == RUBlocks.HANGING_PRISMARITE.get().defaultBlockState().setValue(SHAPE, HangingPrismariteShape.TIP)
            ? (BlockState)this.defaultBlockState().setValue(SHAPE, HangingPrismariteShape.FRUSTUM)
            : (BlockState)this.defaultBlockState().setValue(SHAPE, HangingPrismariteShape.TIP);
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (!(random.nextFloat() >= 0.15 * RUConfigHandler.CLIENT.particleRates.prismarite)) {
         int plantX = pos.getX();
         int plantY = pos.getY();
         int plantZ = pos.getZ();
         MutableBlockPos ambientPos = new MutableBlockPos();

         for (int i = 0; i < 3; i++) {
            ambientPos.set(plantX + Mth.nextInt(random, -2, 2), plantY + Mth.nextInt(random, -2, 2), plantZ + Mth.nextInt(random, -2, 2));
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
