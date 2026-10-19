package com.sbjeiindex.emi.transfer;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface EmiInputSource {
   ItemStack getItem();

   boolean mayPickup(Player var1);

   boolean isCrafting(List<Slot> var1);

   int extract(Player var1, int var2, ItemStack var3);
}
