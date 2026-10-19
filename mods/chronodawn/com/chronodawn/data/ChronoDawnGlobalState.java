package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ChronoDawnGlobalState extends CompatSavedData {
   private static final String DATA_NAME = "chronodawn_global_state";
   private boolean hasEnteredChronoDawn = false;
   private boolean isPortalStabilized = false;
   private boolean isTyrantDefeated = false;

   public static ChronoDawnGlobalState get(MinecraftServer server) {
      ServerLevel overworld = server.getLevel(Level.OVERWORLD);
      if (overworld == null) {
         return new ChronoDawnGlobalState();
      }

      DimensionDataStorage storage = overworld.getDataStorage();
      return CompatSavedData.computeIfAbsent(storage, ChronoDawnGlobalState::new, ChronoDawnGlobalState::load, "chronodawn_global_state");
   }

   public static ChronoDawnGlobalState load(CompoundTag tag, Provider provider) {
      ChronoDawnGlobalState state = new ChronoDawnGlobalState();
      state.loadData(tag);
      ChronoDawn.LOGGER
         .debug(
            "Loaded ChronoDawnGlobalState: hasEntered={}, isStabilized={}, tyrantDefeated={}",
            new Object[]{state.hasEnteredChronoDawn, state.isPortalStabilized, state.isTyrantDefeated}
         );
      return state;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      tag.putBoolean("HasEnteredChronoDawn", this.hasEnteredChronoDawn);
      tag.putBoolean("IsPortalStabilized", this.isPortalStabilized);
      tag.putBoolean("IsTyrantDefeated", this.isTyrantDefeated);
      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      this.hasEnteredChronoDawn = tag.getBoolean("HasEnteredChronoDawn");
      this.isPortalStabilized = tag.getBoolean("IsPortalStabilized");
      this.isTyrantDefeated = tag.getBoolean("IsTyrantDefeated");
   }

   public void markChronoDawnEntered() {
      if (!this.hasEnteredChronoDawn) {
         this.hasEnteredChronoDawn = true;
         this.isPortalStabilized = false;
         this.setDirty();
         ChronoDawn.LOGGER.debug("ChronoDawn entered for the first time - portals are now unstable");
      }
   }

   public void markPortalStabilized() {
      this.isPortalStabilized = true;
      this.setDirty();
      ChronoDawn.LOGGER.debug("Portal stabilized - portals can now be created normally");
   }

   public boolean arePortalsUnstable() {
      return this.hasEnteredChronoDawn && !this.isPortalStabilized;
   }

   public boolean hasEnteredChronoDawn() {
      return this.hasEnteredChronoDawn;
   }

   public boolean isPortalStabilized() {
      return this.isPortalStabilized;
   }

   public void markTyrantDefeated() {
      this.isTyrantDefeated = true;
      this.setDirty();
      ChronoDawn.LOGGER.debug("Time Tyrant defeated - dimension permanently stabilized");
   }

   public boolean isTyrantDefeated() {
      return this.isTyrantDefeated;
   }
}
