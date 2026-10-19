package net.unusual.block_factorys_bosses.block;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FancyWallTorchBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape SOUTH = Shapes.or(
      box(7.0, -3.5, 3.0, 9.0, 9.5, 5.0),
      new VoxelShape[]{box(5.0, 0.0, 0.0, 11.0, 6.0, 2.0), box(6.5, -0.5, 2.5, 9.5, 3.5, 5.5), box(6.0, 5.5, 2.0, 10.0, 9.5, 6.0)}
   );
   private static final VoxelShape NORTH = Shapes.or(
      box(7.0, -3.5, 11.0, 9.0, 9.5, 13.0),
      new VoxelShape[]{box(5.0, 0.0, 14.0, 11.0, 6.0, 16.0), box(6.5, -0.5, 10.5, 9.5, 3.5, 13.5), box(6.0, 5.5, 10.0, 10.0, 9.5, 14.0)}
   );
   private static final VoxelShape EAST = Shapes.or(
      box(3.0, -3.5, 7.0, 5.0, 9.5, 9.0),
      new VoxelShape[]{box(0.0, 0.0, 5.0, 2.0, 6.0, 11.0), box(2.5, -0.5, 6.5, 5.5, 3.5, 9.5), box(2.0, 5.5, 6.0, 6.0, 9.5, 10.0)}
   );
   private static final VoxelShape WEST = Shapes.or(
      box(11.0, -3.5, 7.0, 13.0, 9.5, 9.0),
      new VoxelShape[]{box(14.0, 0.0, 5.0, 16.0, 6.0, 11.0), box(10.5, -0.5, 6.5, 13.5, 3.5, 9.5), box(10.0, 5.5, 6.0, 14.0, 9.5, 10.0)}
   );
   private final Supplier<SimpleParticleType> particleType;

   public FancyWallTorchBlock(Supplier<SimpleParticleType> particleType) {
      super(Properties.of().sound(SoundType.WOOD).strength(0.3F, 10.0F).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
      this.particleType = particleType;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH -> NORTH;
         case EAST -> EAST;
         case WEST -> WEST;
         default -> SOUTH;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return context.getClickedFace().getAxis() == Axis.Y ? null : (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace());
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockPos blockpos = pos.relative(direction.getOpposite());
      BlockState blockstate = level.getBlockState(blockpos);
      return blockstate.isFaceSturdy(level, blockpos, direction);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
      return !state.canSurvive(world, currentPos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      Direction direction = (Direction)state.getValue(FACING);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      if (direction == Direction.WEST) {
         level.addParticle((ParticleOptions)this.particleType.get(), x + 0.68, y + 0.64, z + 0.5, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.SMOKE, x + 0.68, y + 0.64, z + 0.5, 0.0, 0.0, 0.0);
      } else if (direction == Direction.EAST) {
         level.addParticle((ParticleOptions)this.particleType.get(), x + 0.32, y + 0.64, z + 0.5, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.SMOKE, x + 0.32, y + 0.64, z + 0.5, 0.0, 0.0, 0.0);
      } else if (direction == Direction.NORTH) {
         level.addParticle((ParticleOptions)this.particleType.get(), x + 0.5, y + 0.64, z + 0.68, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.SMOKE, x + 0.5, y + 0.64, z + 0.68, 0.0, 0.0, 0.0);
      } else if (direction == Direction.SOUTH) {
         level.addParticle((ParticleOptions)this.particleType.get(), x + 0.5, y + 0.64, z + 0.32, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.SMOKE, x + 0.5, y + 0.64, z + 0.32, 0.0, 0.0, 0.0);
      }
   }
}
