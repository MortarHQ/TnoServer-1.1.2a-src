package net.p3pp3rf1y.sophisticatedcore.api;

import net.minecraft.nbt.CompoundTag;

public interface IStorageSavedData {
   CompoundTag getContents();

   void setContents(CompoundTag var1);

   void markChanged();
}
