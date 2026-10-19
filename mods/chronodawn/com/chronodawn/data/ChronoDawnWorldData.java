package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public abstract class ChronoDawnWorldData extends CompatSavedData {
   @Override
   public abstract CompoundTag saveData(CompoundTag var1);

   @Override
   public abstract void loadData(CompoundTag var1);

   protected void markDirty() {
      this.setDirty();
   }

   protected static <T extends SavedData> T getOrCreate(ServerLevel level, Factory<T> factory, String name) {
      return (T)level.getDataStorage().computeIfAbsent(factory, name);
   }

   protected void debug(String message) {
      ChronoDawn.LOGGER.debug("[WorldData] {}", message);
   }
}
