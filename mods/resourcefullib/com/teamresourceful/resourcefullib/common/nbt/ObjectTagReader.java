package com.teamresourceful.resourcefullib.common.nbt;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public class ObjectTagReader<I> implements NbtReader<I, CompoundTag> {
   private final boolean defaultReaders;
   private final Map<String, NbtReader<I, ? extends Tag>> readers = new HashMap<>();

   public ObjectTagReader(boolean defaultReaders) {
      this.defaultReaders = defaultReaders;
   }

   public void addReader(String key, NbtReader<I, ? extends Tag> reader) {
      this.readers.put(key, reader);
   }

   @Override
   public byte type() {
      return 10;
   }

   @Override
   public void setDefault(I instance) {
      if (this.defaultReaders) {
         this.readers.forEach((key, reader) -> reader.setDefault(instance));
      }
   }

   public void read(I instance, CompoundTag tag) {
      this.readers.forEach((key, reader) -> {
         if (tag.contains(key, reader.type())) {
            reader.castRead(instance, tag.get(key));
         } else {
            reader.setDefault(instance);
         }
      });
   }

   public CompoundTag write(I instance) {
      CompoundTag tag = new CompoundTag();
      this.write(instance, tag);
      return tag;
   }

   public void write(I instance, CompoundTag tag) {
      this.readers.forEach((key, reader) -> tag.put(key, reader.write(instance)));
   }
}
