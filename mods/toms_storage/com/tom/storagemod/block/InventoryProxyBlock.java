package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.InventoryProxyBlockEntity;
import com.tom.storagemod.block.entity.PaintedBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InventoryProxyBlock extends BaseEntityBlock implements IPaintable, IConfiguratorHighlight {
   public static final MapCodec<InventoryProxyBlock> CODEC = ChestBlock.simpleCodec(properties -> new InventoryProxyBlock());
   public static final DirectionProperty FACING = BlockStateProperties.FACING;

   public InventoryProxyBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F));
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FACING, Direction.DOWN));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable("tooltip.toms_storage.paintable"));
      ClientUtil.tooltip("inventory_proxy", tooltip);
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
      return new InventoryProxyBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
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

   @Override
   public int getHighlightColor() {
      return 8913032;
   }

   @Override
   public VoxelShape getHighlightShape(BlockState state, BlockGetter level, BlockPos pos) {
      switch ((Direction)state.getValue(FACING)) {
         case DOWN:
            return Shapes.or(box(4.0, 2.0, 4.0, 12.0, 12.0, 12.0), box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0));
         case EAST:
            return Shapes.or(box(4.0, 4.0, 4.0, 14.0, 12.0, 12.0), box(14.0, 2.0, 2.0, 16.0, 14.0, 14.0));
         case NORTH:
            return Shapes.or(box(4.0, 4.0, 2.0, 12.0, 12.0, 12.0), box(2.0, 2.0, 0.0, 14.0, 14.0, 2.0));
         case SOUTH:
            return Shapes.or(box(4.0, 4.0, 4.0, 12.0, 12.0, 14.0), box(2.0, 2.0, 14.0, 14.0, 14.0, 16.0));
         case UP:
            return Shapes.or(box(4.0, 4.0, 4.0, 12.0, 14.0, 12.0), box(2.0, 14.0, 2.0, 14.0, 16.0, 14.0));
         case WEST:
            return Shapes.or(box(2.0, 4.0, 4.0, 12.0, 12.0, 12.0), box(0.0, 2.0, 2.0, 2.0, 14.0, 14.0));
         default:
            return Shapes.block();
      }
   }
}
