package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.InventoryInterfaceBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.util.TickerUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class InventoryInterfaceBlock extends BaseEntityBlock implements IInventoryCable, IInventoryNode, NeoForgeBlock {
   public static final MapCodec<InventoryInterfaceBlock> CODEC = ChestBlock.simpleCodec(properties -> new InventoryInterfaceBlock());

   public InventoryInterfaceBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F));
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new InventoryInterfaceBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return TickerUtil.createTicker(world, false, true);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("inventory_interface", tooltip);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
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
}
