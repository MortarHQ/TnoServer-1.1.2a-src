package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class DimensionStateData extends ChronoDawnWorldData {
   private static final String DATA_NAME = "chronodawn_dimension_state";
   private boolean isStabilized = false;
   private DimensionStateData.TimeDistortionLevel timeDistortionLevel = DimensionStateData.TimeDistortionLevel.SLOWNESS_IV;

   public static DimensionStateData get(ServerLevel level) {
      return CompatSavedData.computeIfAbsent(level.getDataStorage(), DimensionStateData::new, DimensionStateData::load, "chronodawn_dimension_state");
   }

   private static DimensionStateData load(CompoundTag tag, Provider registries) {
      DimensionStateData data = new DimensionStateData();
      data.isStabilized = tag.getBoolean("is_stabilized");
      String levelString = tag.getString("time_distortion_level");
      if (levelString.isEmpty()) {
         data.timeDistortionLevel = DimensionStateData.TimeDistortionLevel.SLOWNESS_IV;
         ChronoDawn.LOGGER.debug("DimensionStateData loaded with default time distortion level (SLOWNESS_IV)");
      } else {
         data.timeDistortionLevel = DimensionStateData.TimeDistortionLevel.valueOf(levelString);
         ChronoDawn.LOGGER.debug("DimensionStateData loaded: isStabilized={}, timeDistortionLevel={}", data.isStabilized, data.timeDistortionLevel);
      }

      return data;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      tag.putBoolean("is_stabilized", this.isStabilized);
      tag.putString("time_distortion_level", this.timeDistortionLevel.name());
      ChronoDawn.LOGGER.debug("DimensionStateData saved: isStabilized={}, timeDistortionLevel={}", this.isStabilized, this.timeDistortionLevel);
      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      this.isStabilized = tag.getBoolean("is_stabilized");
      String levelString = tag.getString("time_distortion_level");
      if (levelString.isEmpty()) {
         this.timeDistortionLevel = DimensionStateData.TimeDistortionLevel.SLOWNESS_IV;
         ChronoDawn.LOGGER.debug("DimensionStateData loadData() with default time distortion level (SLOWNESS_IV)");
      } else {
         this.timeDistortionLevel = DimensionStateData.TimeDistortionLevel.valueOf(levelString);
         ChronoDawn.LOGGER.debug("DimensionStateData loadData(): isStabilized={}, timeDistortionLevel={}", this.isStabilized, this.timeDistortionLevel);
      }
   }

   public boolean isStabilized() {
      return this.isStabilized;
   }

   public void setStabilized(boolean stabilized) {
      if (this.isStabilized != stabilized) {
         this.isStabilized = stabilized;
         this.setDirty();
         ChronoDawn.LOGGER.debug("Dimension stabilization state changed to: {}", stabilized);
      }
   }

   public DimensionStateData.TimeDistortionLevel getTimeDistortionLevel() {
      return this.timeDistortionLevel;
   }

   public void setTimeDistortionLevel(DimensionStateData.TimeDistortionLevel level) {
      if (this.timeDistortionLevel != level) {
         this.timeDistortionLevel = level;
         this.setDirty();
         ChronoDawn.LOGGER.debug("Time distortion level changed to: {}", level);
      }
   }

   public void enhanceTimeDistortion() {
      this.setStabilized(true);
      this.setTimeDistortionLevel(DimensionStateData.TimeDistortionLevel.SLOWNESS_V);
      ChronoDawn.LOGGER.debug("Dimension enhanced with Eye of Chronos - Slowness V activated");
   }

   public enum TimeDistortionLevel {
      SLOWNESS_IV(4),
      SLOWNESS_V(5);

      private final int amplifier;

      TimeDistortionLevel(int amplifier) {
         this.amplifier = amplifier;
      }

      public int getAmplifier() {
         return this.amplifier;
      }
   }
}
