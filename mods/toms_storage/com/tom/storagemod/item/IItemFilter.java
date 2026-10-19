package com.tom.storagemod.item;

import com.tom.storagemod.inventory.filter.ItemFilter;
import com.tom.storagemod.util.BlockFaceReference;
import java.util.function.BooleanSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemFilter {
   ItemFilter createFilter(BlockFaceReference var1, ItemStack var2);

   void openGui(ItemStack var1, Player var2, BooleanSupplier var3, Runnable var4);
}
