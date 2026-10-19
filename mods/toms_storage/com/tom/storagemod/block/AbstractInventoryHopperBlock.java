package com.tom.storagemod.block;

import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.TickerUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractInventoryHopperBlock extends BaseEntityBlock implements IInventoryCable {
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

   protected AbstractInventoryHopperBlock(Properties p_49224_) {
      super(p_49224_);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.DOWN)).setValue(ENABLED, true));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("inventory_hopper", tooltip);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return TickerUtil.createTicker(world, false, true);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, ENABLED});
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   @Override
   public List<BlockFace> nextScan(Level world, BlockState state, BlockPos pos) {
      return Collections.emptyList();
   }

   @Override
   public boolean canConnectFrom(BlockState state, Direction dir) {
      return ((Direction)state.getValue(FACING)).getAxis() == dir.getAxis();
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      switch ((Direction)state.getValue(FACING)) {
         case DOWN:
            return Shapes.or(box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0), box(3.0, 6.0, 3.0, 13.0, 16.0, 13.0));
         case EAST:
            return Shapes.or(box(10.0, 5.0, 5.0, 16.0, 11.0, 11.0), box(0.0, 3.0, 3.0, 10.0, 13.0, 13.0));
         case NORTH:
            return Shapes.or(box(5.0, 5.0, 0.0, 11.0, 11.0, 6.0), box(3.0, 3.0, 6.0, 13.0, 13.0, 16.0));
         case SOUTH:
            return Shapes.or(box(5.0, 5.0, 10.0, 11.0, 11.0, 16.0), box(3.0, 3.0, 0.0, 13.0, 13.0, 10.0));
         case UP:
            return Shapes.or(box(5.0, 10.0, 5.0, 11.0, 16.0, 11.0), box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0));
         case WEST:
            return Shapes.or(box(0.0, 5.0, 5.0, 6.0, 11.0, 11.0), box(6.0, 3.0, 3.0, 16.0, 13.0, 13.0));
         default:
            return Shapes.block();
      }
   }

   public void onPlace(BlockState p_54110_, Level p_54111_, BlockPos p_54112_, BlockState p_54113_, boolean p_54114_) {
      if (!p_54113_.is(p_54110_.getBlock())) {
         this.updateState(p_54111_, p_54112_, p_54110_);
      }
   }

   public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      this.updateState(worldIn, pos, state);
   }

   private void updateState(Level worldIn, BlockPos pos, BlockState state) {
      boolean flag = !worldIn.hasNeighborSignal(pos);
      if (flag != (Boolean)state.getValue(ENABLED)) {
         worldIn.setBlock(pos, (BlockState)state.setValue(ENABLED, flag), 4);
      }
   }
}
