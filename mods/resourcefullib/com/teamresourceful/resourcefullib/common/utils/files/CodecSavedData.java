package com.teamresourceful.resourcefullib.common.utils.files;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public final class CodecSavedData<T> extends SavedData implements Supplier<T> {
   private static final Logger LOGGER = LogUtils.getLogger();
   private final CodecSavedData.Factory<T> factory;
   private T data;

   private CodecSavedData(CodecSavedData.Factory<T> factory, CompoundTag tag, Provider provider) {
      this.factory = factory;
      this.data = factory.codec
         .parse(provider.createSerializationContext(NbtOps.INSTANCE), tag)
         .ifError(e -> LOGGER.error("Failed to parse data for {}", factory.path, e))
         .result()
         .orElseGet(factory.defaultValue);
   }

   private CodecSavedData(CodecSavedData.Factory<T> factory) {
      this.factory = factory;
      this.data = factory.defaultValue.get();
   }

   @NotNull
   public CompoundTag save(CompoundTag tag, Provider provider) {
      this.factory
         .codec
         .encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this.get())
         .ifError(e -> LOGGER.error("Failed to encode data for {}", this.factory.path, e))
         .result()
         .ifPresent(compound -> {
            if (compound instanceof CompoundTag compoundTag) {
               if (this.factory.clean) {
                  Set<String> keys = new HashSet<>(tag.getAllKeys());
                  keys.forEach(tag::remove);
               }

               tag.merge(compoundTag);
            } else {
               LOGGER.error("Codec {} did not return a CompoundTag for {}", this.factory.codec, this.factory.path);
            }
         });
      return tag;
   }

   public boolean isDirty() {
      return this.factory.alwaysDirty || super.isDirty();
   }

   @Override
   public T get() {
      return this.data;
   }

   public void set(T data) {
      this.data = data;
      this.setDirty();
   }

   public static <T> CodecSavedData.Factory<T> create(Codec<T> codec, String path) {
      return new CodecSavedData.Factory<>(codec, path);
   }

   public static class Factory<T> {
      private final Codec<T> codec;
      private final String path;
      private Supplier<T> defaultValue = () -> null;
      private boolean alwaysDirty = false;
      private boolean global = false;
      private boolean clean = false;
      private net.minecraft.world.level.saveddata.SavedData.Factory<CodecSavedData<T>> factory;

      private Factory(Codec<T> codec, String path) {
         this.codec = codec;
         this.path = path;
      }

      public CodecSavedData.Factory<T> defaultValue(Supplier<T> defaultValue) {
         this.defaultValue = defaultValue;
         return this;
      }

      public CodecSavedData.Factory<T> alwaysDirty() {
         this.alwaysDirty = true;
         return this;
      }

      public CodecSavedData.Factory<T> global() {
         this.global = true;
         return this;
      }

      public CodecSavedData.Factory<T> clean() {
         this.clean = true;
         return this;
      }

      public CodecSavedData<T> create(ServerLevel level) {
         DimensionDataStorage storage = this.global ? level.getServer().overworld().getDataStorage() : level.getDataStorage();
         if (this.factory == null) {
            this.factory = new net.minecraft.world.level.saveddata.SavedData.Factory(
               () -> new CodecSavedData<>(this), (tag, provider) -> new CodecSavedData<>(this, tag, provider), null
            );
         }

         return (CodecSavedData<T>)storage.computeIfAbsent(this.factory, this.path);
      }
   }
}
