package noobanidus.mods.lootr.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrophyBlock extends Block {
   private static final VoxelShape EAST_WEST = Block.box(1.5, 0.0, 4.0, 14.5, 14.5, 12.0);
   private static final VoxelShape NORTH_SOUTH = Block.box(4.0, 0.0, 1.5, 12.0, 14.5, 14.5);

   public TrophyBlock(Properties properties) {
      super(properties);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{HorizontalDirectionalBlock.FACING});
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      Direction facing = (Direction)state.getValue(HorizontalDirectionalBlock.FACING);
      return facing != Direction.EAST && facing != Direction.WEST ? NORTH_SOUTH : EAST_WEST;
   }

   public BlockState rotate(BlockState p_60530_, Rotation p_60531_) {
      return (BlockState)p_60530_.setValue(HorizontalDirectionalBlock.FACING, p_60531_.rotate((Direction)p_60530_.getValue(HorizontalDirectionalBlock.FACING)));
   }

   public BlockState mirror(BlockState p_60528_, Mirror p_60529_) {
      return p_60528_.rotate(p_60529_.getRotation((Direction)p_60528_.getValue(HorizontalDirectionalBlock.FACING)));
   }
}
