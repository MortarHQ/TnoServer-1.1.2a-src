package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.FilingCabinetBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
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

public class FilingCabinetBlock extends BaseEntityBlock {
   public static final MapCodec<FilingCabinetBlock> CODEC = ChestBlock.simpleCodec(properties -> new FilingCabinetBlock());
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

   public FilingCabinetBlock() {
      super(Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(3.0F));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("filing_cabinet", tooltip);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
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
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (world.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      if (world.getBlockEntity(pos) instanceof FilingCabinetBlockEntity be) {
         player.openMenu(be);
      }

      return InteractionResult.SUCCESS;
   }

   public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
      return new FilingCabinetBlockEntity(p_153215_, p_153216_);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean flag) {
      if (!state.is(state2.getBlock())) {
         if (world.getBlockEntity(pos) instanceof FilingCabinetBlockEntity te) {
            Containers.dropContents(world, pos, te.getInv());
            world.updateNeighbourForOutputSignal(pos, this);
         }

         super.onRemove(state, world, pos, state2, flag);
      }
   }
}
