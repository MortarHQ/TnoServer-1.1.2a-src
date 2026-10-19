package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.LunarVineBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LunarVineBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<LunarVineBlock> CODEC = simpleCodec(LunarVineBlock::new);
   private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

   public LunarVineBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<LunarVineBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      if (context.getClickedFace().getAxis() != Axis.Y) {
         BlockState state = (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
         if (this.canSurvive(state, context.getLevel(), context.getClickedPos())) {
            return state;
         }
      }

      return null;
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.relative((Direction)state.getValue(FACING)))
         .isFaceSturdy(level, pos.relative((Direction)state.getValue(FACING)), ((Direction)state.getValue(FACING)).getOpposite());
   }

   protected BlockState updateShape(
      BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2
   ) {
      return !blockState.canSurvive(level, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
   }

   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return switch ((Direction)blockState.getValue(FACING)) {
         case NORTH -> NORTH_AABB;
         case SOUTH -> SOUTH_AABB;
         case WEST -> WEST_AABB;
         case EAST -> EAST_AABB;
         default -> super.getShape(blockState, blockGetter, blockPos, collisionContext);
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   protected RenderShape getRenderShape(BlockState blockState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new LunarVineBlockEntity(blockPos, blockState);
   }
}
