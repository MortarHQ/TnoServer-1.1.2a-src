package net.p3pp3rf1y.sophisticatedcore.settings;

import net.minecraft.nbt.CompoundTag;

public interface ISettingsCategory<T extends ISettingsCategory<?>> {
   void reloadFrom(CompoundTag var1);

   void overwriteWith(T var1);

   boolean isLargerThanNumberOfSlots(int var1);

   void copyTo(T var1, int var2, int var3);

   void deleteSlotSettingsFrom(int var1);
}
