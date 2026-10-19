package com.tom.storagemod.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface WirelessTerminal {
   int getRange(Player var1, ItemStack var2);

   void open(Player var1, ItemStack var2);

   boolean canOpen(ItemStack var1);
}
