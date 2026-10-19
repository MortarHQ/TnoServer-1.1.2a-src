package com.iafenvoy.uranus.object.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ISwingable {
   boolean onEntitySwing(ItemStack var1, Entity var2);

   boolean onSwingHand(ItemStack var1, Level var2, double var3, double var5, double var7);
}
