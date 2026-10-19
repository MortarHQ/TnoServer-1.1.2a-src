package com.finderfeed.fdlib.nbt;

import net.minecraft.nbt.CompoundTag;

public interface TagDeserializer<T> {
   void serialize(String var1, T var2, CompoundTag var3);

   T deserialize(String var1, CompoundTag var2);
}
