package com.chronodawn.compat;

import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ItemDataHandler {
   void setString(ItemStack var1, String var2, String var3);

   String getString(ItemStack var1, String var2);

   void setInt(ItemStack var1, String var2, int var3);

   int getInt(ItemStack var1, String var2);

   boolean contains(ItemStack var1, String var2);

   CompoundTag getCustomData(ItemStack var1);

   void updateCustomData(ItemStack var1, Consumer<CompoundTag> var2);
}
