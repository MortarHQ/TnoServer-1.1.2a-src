package com.chronodawn.compat;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public abstract class CompatSavedData extends SavedData implements SavedDataHandler {
   public CompoundTag save(CompoundTag tag, Provider registries) {
      return this.saveData(tag);
   }

   @Override
   public abstract CompoundTag saveData(CompoundTag var1);

   @Override
   public abstract void loadData(CompoundTag var1);

   public static <T extends SavedData> T computeIfAbsent(
      DimensionDataStorage storage, Supplier<T> constructor, BiFunction<CompoundTag, Provider, T> deserializer, String id
   ) {
      return (T)storage.computeIfAbsent(new Factory(constructor, deserializer, null), id);
   }
}
