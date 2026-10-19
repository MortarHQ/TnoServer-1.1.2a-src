package com.tom.storagemod.block;

import com.tom.storagemod.block.entity.StorageTerminalBlockEntity;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.TickerUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractStorageTerminalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, IInventoryCable {
   public static final EnumProperty<AbstractStorageTerminalBlock.TerminalPos> TERMINAL_POS = EnumProperty.create(
      "pos", AbstractStorageTerminalBlock.TerminalPos.class
   );
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape SHAPE_N = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 6.0);
   private static final VoxelShape SHAPE_S = Block.box(0.0, 0.0, 10.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_E = Block.box(10.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_W = Block.box(0.0, 0.0, 0.0, 6.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_U = Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_D = Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public AbstractStorageTerminalBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F).lightLevel(s -> 6));
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(TERMINAL_POS, AbstractStorageTerminalBlock.TerminalPos.CENTER))
               .setValue(WATERLOGGED, false))
            .setValue(FACING, Direction.NORTH)
      );
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, WATERLOGGED, TERMINAL_POS});
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (world.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      if (world.getBlockEntity(pos) instanceof StorageTerminalBlockEntity term) {
         if (term.canInteractWith(player, false)) {
            player.openMenu(term);
         } else {
            player.displayClientMessage(Component.translatable("chat.toms_storage.terminal_out_of_range"), true);
         }
      }

      return InteractionResult.SUCCESS;
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction direction = context.getClickedFace().getOpposite();
      FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
      AbstractStorageTerminalBlock.TerminalPos pos = AbstractStorageTerminalBlock.TerminalPos.CENTER;
      if (direction.getAxis() == Axis.Y) {
         if (direction == Direction.UP) {
            pos = AbstractStorageTerminalBlock.TerminalPos.UP;
         }

         if (direction == Direction.DOWN) {
            pos = AbstractStorageTerminalBlock.TerminalPos.DOWN;
         }

         direction = context.getHorizontalDirection();
      }

      return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, direction.getAxis() == Axis.Y ? Direction.NORTH : direction))
            .setValue(TERMINAL_POS, pos))
         .setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.getValue(WATERLOGGED)) {
         worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
      }

      return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      switch ((AbstractStorageTerminalBlock.TerminalPos)state.getValue(TERMINAL_POS)) {
         case CENTER:
            switch ((Direction)state.getValue(FACING)) {
               case NORTH:
                  return SHAPE_N;
               case SOUTH:
                  return SHAPE_S;
               case EAST:
                  return SHAPE_E;
               case WEST:
                  return SHAPE_W;
            }
         default:
            return SHAPE_N;
         case UP:
            return SHAPE_U;
         case DOWN:
            return SHAPE_D;
      }
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return TickerUtil.createTicker(world, false, true);
   }

   @Override
   public boolean canConnectFrom(BlockState state, Direction dir) {
      Direction d = (Direction)state.getValue(FACING);
      AbstractStorageTerminalBlock.TerminalPos p = (AbstractStorageTerminalBlock.TerminalPos)state.getValue(TERMINAL_POS);
      if (p == AbstractStorageTerminalBlock.TerminalPos.UP) {
         d = Direction.UP;
      }

      if (p == AbstractStorageTerminalBlock.TerminalPos.DOWN) {
         d = Direction.DOWN;
      }

      return dir == d;
   }

   @Override
   public List<BlockFace> nextScan(Level world, BlockState state, BlockPos pos) {
      Direction d = (Direction)state.getValue(FACING);
      AbstractStorageTerminalBlock.TerminalPos p = (AbstractStorageTerminalBlock.TerminalPos)state.getValue(TERMINAL_POS);
      if (p == AbstractStorageTerminalBlock.TerminalPos.UP) {
         d = Direction.UP;
      }

      if (p == AbstractStorageTerminalBlock.TerminalPos.DOWN) {
         d = Direction.DOWN;
      }

      return Collections.singletonList(new BlockFace(pos.relative(d), d.getOpposite()));
   }

   public enum TerminalPos implements StringRepresentable {
      CENTER("center"),
      UP("up"),
      DOWN("down");

      private String name;

      TerminalPos(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
