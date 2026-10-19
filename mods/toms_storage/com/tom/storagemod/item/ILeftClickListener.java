package com.tom.storagemod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ILeftClickListener {
   boolean onLeftClick(ItemStack var1, BlockPos var2, Player var3);
}
