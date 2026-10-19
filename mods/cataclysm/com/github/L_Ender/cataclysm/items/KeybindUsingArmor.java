package com.github.L_Ender.cataclysm.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface KeybindUsingArmor {
   void onKeyPacket(Player var1, ItemStack var2, int var3);
}
