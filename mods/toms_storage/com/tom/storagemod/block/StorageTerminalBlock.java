package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.StorageTerminalBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StorageTerminalBlock extends AbstractStorageTerminalBlock {
   public static final MapCodec<StorageTerminalBlock> CODEC = ChestBlock.simpleCodec(properties -> new StorageTerminalBlock());

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new StorageTerminalBlockEntity(pos, state);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("storage_terminal", tooltip);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
