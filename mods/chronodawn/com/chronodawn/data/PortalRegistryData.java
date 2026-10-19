package com.chronodawn.data;

import com.chronodawn.compat.CompatSavedData;
import com.chronodawn.core.portal.PortalRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class PortalRegistryData extends ChronoDawnWorldData {
   private static final String DATA_NAME = "chronodawn_portal_registry";

   public static PortalRegistryData get(ServerLevel level) {
      return CompatSavedData.computeIfAbsent(level.getDataStorage(), PortalRegistryData::new, PortalRegistryData::load, "chronodawn_portal_registry");
   }

   private static PortalRegistryData load(CompoundTag tag, Provider registries) {
      PortalRegistryData data = new PortalRegistryData();
      data.loadData(tag);
      return data;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      PortalRegistry.getInstance().saveToNBT(tag);
      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      PortalRegistry.getInstance().loadFromNBT(tag);
   }

   public void savePortalRegistry() {
      this.setDirty();
   }
}
