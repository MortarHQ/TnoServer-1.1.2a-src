package com.chronodawn.compat;

import net.minecraft.nbt.CompoundTag;

public interface BlockEntityDataHandler {
   void saveData(CompoundTag var1);

   void loadData(CompoundTag var1);
}
