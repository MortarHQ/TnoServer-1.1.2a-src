package com.chronodawn.compat;

import net.minecraft.nbt.CompoundTag;

public interface SavedDataHandler {
   CompoundTag saveData(CompoundTag var1);

   void loadData(CompoundTag var1);
}
