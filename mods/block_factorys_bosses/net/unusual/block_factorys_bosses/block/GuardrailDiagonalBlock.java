package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GuardrailDiagonalBlock extends Block {
   private static final VoxelShape EAST_SHAPE = Shapes.or(
      box(0.0, 0.0, 6.0, 4.0, 4.0, 10.0),
      new VoxelShape[]{box(4.0, 0.0, 6.0, 8.0, 8.0, 10.0), box(8.0, 0.0, 6.0, 12.0, 12.0, 10.0), box(12.0, 0.0, 6.0, 16.0, 16.0, 10.0)}
   );
   private static final VoxelShape SOUTH_SHAPE = Shapes.or(
      box(6.0, 0.0, 0.0, 10.0, 4.0, 4.0),
      new VoxelShape[]{box(6.0, 0.0, 4.0, 10.0, 8.0, 8.0), box(6.0, 0.0, 8.0, 10.0, 12.0, 12.0), box(6.0, 0.0, 12.0, 10.0, 16.0, 16.0)}
   );
   private static final VoxelShape WEST_SHAPE = Shapes.or(
      box(12.0, 0.0, 6.0, 16.0, 4.0, 10.0),
      new VoxelShape[]{box(8.0, 0.0, 6.0, 12.0, 8.0, 10.0), box(4.0, 0.0, 6.0, 8.0, 12.0, 10.0), box(0.0, 0.0, 6.0, 4.0, 16.0, 10.0)}
   );
   private static final VoxelShape NORTH_SHAPE = Shapes.or(
      box(6.0, 0.0, 12.0, 10.0, 4.0, 16.0),
      new VoxelShape[]{box(6.0, 0.0, 8.0, 10.0, 8.0, 12.0), box(6.0, 0.0, 4.0, 10.0, 12.0, 8.0), box(6.0, 0.0, 0.0, 10.0, 16.0, 4.0)}
   );
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

   public GuardrailDiagonalBlock() {
      super(Properties.of().destroyTime(2.5F).ignitedByLava().explosionResistance(2.5F).noOcclusion().sound(SoundType.WOOD));
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case EAST -> EAST_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         default -> NORTH_SHAPE;
      };
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }
}
