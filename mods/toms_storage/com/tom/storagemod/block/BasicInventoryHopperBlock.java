package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.BasicInventoryHopperBlockEntity;
import com.tom.storagemod.item.IItemFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class BasicInventoryHopperBlock extends AbstractInventoryHopperBlock {
   public static final MapCodec<BasicInventoryHopperBlock> CODEC = ChestBlock.simpleCodec(properties -> new BasicInventoryHopperBlock());

   public BasicInventoryHopperBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BasicInventoryHopperBlockEntity(pos, state);
   }

   protected ItemInteractionResult useItemOn(ItemStack is, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (is.isEmpty()) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      if (!world.isClientSide && world.getBlockEntity(pos) instanceof BasicInventoryHopperBlockEntity h) {
         if (!h.getFilter().isEmpty() && h.getFilter().getItem() instanceof IItemFilter) {
            popResource(world, pos, h.getFilter());
         }

         h.setFilter(is.copy());
         if (is.getItem() instanceof IItemFilter) {
            player.setItemInHand(hand, ItemStack.EMPTY);
         }

         Component txt = h.getFilter().getHoverName();
         player.displayClientMessage(Component.translatable("tooltip.toms_storage.filter_item", new Object[]{txt}), true);
      }

      return ItemInteractionResult.SUCCESS;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (!world.isClientSide && world.getBlockEntity(pos) instanceof BasicInventoryHopperBlockEntity h) {
         if (player.isShiftKeyDown()) {
            if (!h.getFilter().isEmpty() && h.getFilter().getItem() instanceof IItemFilter) {
               player.getInventory().add(h.getFilter());
            }

            h.setFilter(ItemStack.EMPTY);
            player.displayClientMessage(
               Component.translatable("tooltip.toms_storage.filter_item", new Object[]{Component.translatable("tooltip.toms_storage.empty")}), true
            );
         } else {
            ItemStack s = h.getFilter();
            Component txt = (Component)(s.isEmpty() ? Component.translatable("tooltip.toms_storage.empty") : s.getHoverName());
            player.displayClientMessage(Component.translatable("tooltip.toms_storage.filter_item", new Object[]{txt}), true);
         }
      }

      return InteractionResult.SUCCESS;
   }

   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean flag) {
      if (!state.is(state2.getBlock())) {
         if (world.getBlockEntity(pos) instanceof BasicInventoryHopperBlockEntity te
            && !te.getFilter().isEmpty()
            && te.getFilter().getItem() instanceof IItemFilter) {
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), te.getFilter());
         }

         super.onRemove(state, world, pos, state2, flag);
      }
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
