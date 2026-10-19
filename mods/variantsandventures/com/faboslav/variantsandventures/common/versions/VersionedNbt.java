package com.faboslav.variantsandventures.common.versions;

import net.minecraft.nbt.CompoundTag;

public final class VersionedNbt {
   public static int getInt(CompoundTag nbt, String key, int defaultValue) {
      return nbt.contains(key) ? nbt.getInt(key) : defaultValue;
   }

   public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue) {
      return nbt.contains(key) ? nbt.getBoolean(key) : defaultValue;
   }
}
