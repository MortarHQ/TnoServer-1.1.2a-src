package com.teamresourceful.resourcefullib.common.nbt;

import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public interface NbtReader<I, T extends Tag> {
   byte type();

   void setDefault(I var1);

   default void castRead(I instance, Tag tag) {
      this.read(instance, (T)tag);
   }

   void read(I var1, T var2);

   T write(I var1);
}
