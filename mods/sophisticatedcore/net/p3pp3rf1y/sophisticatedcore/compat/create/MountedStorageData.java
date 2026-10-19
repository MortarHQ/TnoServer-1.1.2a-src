package net.p3pp3rf1y.sophisticatedcore.compat.create;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageSavedData;

public class MountedStorageData extends SavedData implements IStorageSavedData {
   private static final String SAVED_DATA_PREFIX = "sophisticatedcore_mounted/";
   private CompoundTag movingStorageContents = new CompoundTag();
   private boolean toRemove = false;
   private static final Cache<UUID, MountedStorageData> clientStorageCopy = CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).build();
   private final Set<UUID> updatedStorageSettingsFlags = new HashSet<>();

   private MountedStorageData() {
   }

   public static MountedStorageData get(UUID storageId) {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            ServerLevel overworld = server.getLevel(Level.OVERWORLD);
            DimensionDataStorage storage = overworld.getDataStorage();
            return (MountedStorageData)storage.computeIfAbsent(
               new Factory(MountedStorageData::new, MountedStorageData::load), "sophisticatedcore_mounted/" + storageId
            );
         }
      }

      MountedStorageData storageData = (MountedStorageData)clientStorageCopy.getIfPresent(storageId);
      if (storageData == null) {
         storageData = new MountedStorageData();
         clientStorageCopy.put(storageId, storageData);
      }

      return storageData;
   }

   public static MountedStorageData load(CompoundTag nbt, Provider registries) {
      MountedStorageData storageData = new MountedStorageData();
      storageData.movingStorageContents = nbt;
      return storageData;
   }

   public CompoundTag save(CompoundTag compound, Provider registries) {
      return this.movingStorageContents != null ? this.movingStorageContents : new CompoundTag();
   }

   public void removeStorageContents() {
      this.toRemove = true;
      this.setDirty();
   }

   public void save(File file, Provider registries) {
      if (this.toRemove) {
         file.delete();
      } else {
         try {
            Files.createDirectories(file.toPath().getParent());
         } catch (IOException e) {
            SophisticatedCore.LOGGER.error("Failed to create directories for moving storage data", e);
         }

         super.save(file, registries);
      }
   }

   public void setContents(UUID storageUuid, CompoundTag contents) {
      for (String key : contents.getAllKeys()) {
         this.movingStorageContents.put(key, contents.get(key));
         if (key.equals("settings")) {
            this.updatedStorageSettingsFlags.add(storageUuid);
         }
      }

      this.setDirty();
   }

   @Override
   public CompoundTag getContents() {
      return this.movingStorageContents;
   }

   @Override
   public void setContents(CompoundTag contents) {
      this.movingStorageContents = contents;
      this.setDirty();
   }

   @Override
   public void markChanged() {
      this.setDirty();
   }

   public boolean removeUpdatedStorageSettingsFlag(UUID backpackUuid) {
      return this.updatedStorageSettingsFlags.remove(backpackUuid);
   }
}
