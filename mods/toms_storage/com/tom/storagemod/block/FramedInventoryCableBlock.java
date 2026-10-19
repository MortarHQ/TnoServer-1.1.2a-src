package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.PaintedBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.util.BlockFace;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FramedInventoryCableBlock extends BaseEntityBlock implements IInventoryCable, IPaintable, NeoForgeBlock, IConfiguratorHighlight {
   public static final BooleanProperty UP = BlockStateProperties.UP;
   public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
   public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
   public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
   public static final BooleanProperty EAST = BlockStateProperties.EAST;
   public static final BooleanProperty WEST = BlockStateProperties.WEST;
   public static final MapCodec<FramedInventoryCableBlock> CODEC = ChestBlock.simpleCodec(properties -> new FramedInventoryCableBlock());

   public FramedInventoryCableBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.0F).noOcclusion());
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(DOWN, false)).setValue(UP, false))
                     .setValue(NORTH, false))
                  .setValue(EAST, false))
               .setValue(SOUTH, false))
            .setValue(WEST, false)
      );
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable("tooltip.toms_storage.paintable"));
      ClientUtil.tooltip("inventory_cable", tooltip);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{UP, DOWN, NORTH, SOUTH, EAST, WEST});
   }

   @Override
   public List<BlockFace> nextScan(Level world, BlockState state, BlockPos pos) {
      List<BlockFace> next = new ArrayList<>();

      for (Direction d : Direction.values()) {
         if ((Boolean)state.getValue(InventoryCableBlock.DIR_TO_PROPERTY[d.ordinal()])) {
            next.add(new BlockFace(pos.relative(d), d.getOpposite()));
         }
      }

      return next;
   }

   @Override
   public boolean isFunctionalNode() {
      return false;
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return (BlockState)stateIn.setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(facing), IInventoryCable.canConnect(facingState, facing));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.withConnectionProperties(context.getLevel(), context.getClickedPos());
   }

   public BlockState withConnectionProperties(LevelAccessor blockView_1, BlockPos blockPos_1) {
      BlockState block_1 = blockView_1.getBlockState(blockPos_1.below());
      BlockState block_2 = blockView_1.getBlockState(blockPos_1.above());
      BlockState block_3 = blockView_1.getBlockState(blockPos_1.north());
      BlockState block_4 = blockView_1.getBlockState(blockPos_1.east());
      BlockState block_5 = blockView_1.getBlockState(blockPos_1.south());
      BlockState block_6 = blockView_1.getBlockState(blockPos_1.west());
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState()
                        .setValue(DOWN, IInventoryCable.canConnect(block_1, Direction.DOWN)))
                     .setValue(UP, IInventoryCable.canConnect(block_2, Direction.UP)))
                  .setValue(NORTH, IInventoryCable.canConnect(block_3, Direction.NORTH)))
               .setValue(EAST, IInventoryCable.canConnect(block_4, Direction.EAST)))
            .setValue(SOUTH, IInventoryCable.canConnect(block_5, Direction.SOUTH)))
         .setValue(WEST, IInventoryCable.canConnect(block_6, Direction.WEST));
   }

   public BlockState rotate(BlockState blockState_1, Rotation blockRotation_1) {
      switch (blockRotation_1) {
         case CLOCKWISE_180:
            return (BlockState)((BlockState)((BlockState)((BlockState)blockState_1.setValue(NORTH, (Boolean)blockState_1.getValue(SOUTH)))
                     .setValue(EAST, (Boolean)blockState_1.getValue(WEST)))
                  .setValue(SOUTH, (Boolean)blockState_1.getValue(NORTH)))
               .setValue(WEST, (Boolean)blockState_1.getValue(EAST));
         case CLOCKWISE_90:
            return (BlockState)((BlockState)((BlockState)((BlockState)blockState_1.setValue(NORTH, (Boolean)blockState_1.getValue(EAST)))
                     .setValue(EAST, (Boolean)blockState_1.getValue(SOUTH)))
                  .setValue(SOUTH, (Boolean)blockState_1.getValue(WEST)))
               .setValue(WEST, (Boolean)blockState_1.getValue(NORTH));
         case COUNTERCLOCKWISE_90:
            return (BlockState)((BlockState)((BlockState)((BlockState)blockState_1.setValue(NORTH, (Boolean)blockState_1.getValue(WEST)))
                     .setValue(EAST, (Boolean)blockState_1.getValue(NORTH)))
                  .setValue(SOUTH, (Boolean)blockState_1.getValue(EAST)))
               .setValue(WEST, (Boolean)blockState_1.getValue(SOUTH));
         default:
            return blockState_1;
      }
   }

   public BlockState mirror(BlockState blockState_1, Mirror blockMirror_1) {
      switch (blockMirror_1) {
         case FRONT_BACK:
            return (BlockState)((BlockState)blockState_1.setValue(NORTH, (Boolean)blockState_1.getValue(SOUTH)))
               .setValue(SOUTH, (Boolean)blockState_1.getValue(NORTH));
         case LEFT_RIGHT:
            return (BlockState)((BlockState)blockState_1.setValue(EAST, (Boolean)blockState_1.getValue(WEST)))
               .setValue(WEST, (Boolean)blockState_1.getValue(EAST));
         default:
            return super.mirror(blockState_1, blockMirror_1);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack item, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_316140_
   ) {
      return this.tryScrape(item, state, world, pos, player, hand)
         ? ItemInteractionResult.sidedSuccess(world.isClientSide())
         : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   @Override
   public boolean paint(Level world, BlockPos pos, BlockState to) {
      BlockEntity te = world.getBlockEntity(pos);
      return te != null && te instanceof PaintedBlockEntity ? ((PaintedBlockEntity)te).setPaintedBlockState(to) : false;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PaintedBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return false;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
      if (p_49860_ instanceof ServerLevel l) {
         InventoryCableNetwork.getNetwork(l).markNodeInvalid(p_49861_);
      }
   }

   public void neighborChanged(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
      super.neighborChanged(p_60509_, p_60510_, p_60511_, p_60512_, p_60513_, p_60514_);
      if (!p_60510_.isClientSide) {
         InventoryCableNetwork n = InventoryCableNetwork.getNetwork(p_60510_);
         n.markNodeInvalid(p_60511_);
         n.markNodeInvalid(p_60513_);
      }
   }

   public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
      if (level instanceof ServerLevel l) {
         InventoryCableNetwork n = InventoryCableNetwork.getNetwork(l);
         n.markNodeInvalid(pos);
         n.markNodeInvalid(neighbor);
      }
   }

   @Override
   public int getHighlightColor() {
      return 16711680;
   }

   @Override
   public VoxelShape getHighlightShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Content.invCable.get().getCableShape(state, level, pos);
   }
}
