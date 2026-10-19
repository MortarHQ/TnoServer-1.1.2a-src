package net.regions_unexplored.block.type.wood;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class BeardBlock extends BushBlock {
   public static final MapCodec<? extends BeardBlock> CODEC = simpleCodec(BeardBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

   public BeardBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH -> box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
         case EAST -> box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
         case WEST -> box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
         default -> box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = ((Direction)state.getValue(FACING)).getOpposite();
      MutableBlockPos blockPos = pos.mutable().move(direction);
      return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(RUBlockTags.SUPPORTS_BRANCHES) && state.isCollisionShapeFullBlock(getter, pos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction facing = context.getClickedFace();
      if (facing.getAxis() == Axis.Y) {
         facing = Direction.NORTH;
      }

      return (BlockState)this.defaultBlockState().setValue(FACING, facing);
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }
}
