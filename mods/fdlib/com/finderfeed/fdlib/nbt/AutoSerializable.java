package com.finderfeed.fdlib.nbt;

import net.minecraft.nbt.CompoundTag;

public interface AutoSerializable {
   default void autoSave(CompoundTag tag) {
      TagSerializationHelper.saveFields(tag, this);
   }

   default void autoSave(String name, CompoundTag tag) {
      CompoundTag t = new CompoundTag();
      this.autoSave(t);
      tag.put(name, t);
   }

   default void autoLoad(CompoundTag tag) {
      TagSerializationHelper.loadFields(tag, this);
   }

   default void autoLoad(String name, CompoundTag tag) {
      CompoundTag t = tag.getCompound(name);
      this.autoLoad(t);
   }
}
