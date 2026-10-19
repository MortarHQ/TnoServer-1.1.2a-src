package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class ChronoDawnTimeData extends CompatSavedData {
   private static final String DATA_NAME = "chronodawn_time";
   private static final String TIME_KEY = "IndependentTime";
   private long independentTime;

   public ChronoDawnTimeData() {
      this.independentTime = 0L;
   }

   public ChronoDawnTimeData(long independentTime) {
      this.independentTime = independentTime;
   }

   public static ChronoDawnTimeData get(ServerLevel level) {
      return CompatSavedData.computeIfAbsent(level.getDataStorage(), ChronoDawnTimeData::new, ChronoDawnTimeData::load, "chronodawn_time");
   }

   public static ChronoDawnTimeData load(CompoundTag tag, Provider provider) {
      ChronoDawnTimeData data = new ChronoDawnTimeData();
      data.loadData(tag);
      ChronoDawn.LOGGER.debug("ChronoDawnTimeData: Loaded independent time = {}", data.independentTime);
      return data;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      tag.putLong("IndependentTime", this.independentTime);
      ChronoDawn.LOGGER.debug("ChronoDawnTimeData: Saved independent time = {}", this.independentTime);
      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      this.independentTime = tag.getLong("IndependentTime");
   }

   public long getIndependentTime() {
      return this.independentTime;
   }

   public void setIndependentTime(long time) {
      this.independentTime = time;
      this.setDirty();
   }
}
