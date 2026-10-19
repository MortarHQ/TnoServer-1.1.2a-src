package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BackpackStorage extends SavedData {
   private static final String SAVED_DATA_NAME = "sophisticatedbackpacks";
   private final Map<UUID, CompoundTag> backpackContents = new HashMap<>();
   private static final BackpackStorage clientStorageCopy = new BackpackStorage();
   private final Map<UUID, AccessLogRecord> accessLogRecords = new HashMap<>();
   private final Set<UUID> updatedBackpackSettingsFlags = new HashSet<>();

   private BackpackStorage() {
   }

   public static BackpackStorage get() {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            ServerLevel overworld = server.getLevel(Level.OVERWORLD);
            DimensionDataStorage storage = overworld.getDataStorage();
            return (BackpackStorage)storage.computeIfAbsent(new Factory(BackpackStorage::new, BackpackStorage::load), "sophisticatedbackpacks");
         }
      }

      return clientStorageCopy;
   }

   public static BackpackStorage load(CompoundTag nbt, Provider registries) {
      BackpackStorage storage = new BackpackStorage();
      readAccessLogs(nbt, storage);
      readBackpackContents(nbt, storage);
      return storage;
   }

   private static void readAccessLogs(CompoundTag nbt, BackpackStorage storage) {
      for (Tag n : nbt.getList("accessLogRecords", 10)) {
         AccessLogRecord alr = AccessLogRecord.deserializeFromNBT((CompoundTag)n);
         storage.accessLogRecords.put(alr.getBackpackUuid(), alr);
      }
   }

   private static void readBackpackContents(CompoundTag nbt, BackpackStorage storage) {
      for (Tag n : nbt.getList("backpackContents", 10)) {
         CompoundTag uuidContentsPair = (CompoundTag)n;
         UUID uuid = NbtUtils.loadUUID(Objects.requireNonNull(uuidContentsPair.get("uuid")));
         CompoundTag contents = uuidContentsPair.getCompound("contents");
         if (isPlayerBackpackOrNotEmpty(storage, uuid, contents)) {
            storage.backpackContents.put(uuid, contents);
         }
      }
   }

   private static boolean isPlayerBackpackOrNotEmpty(BackpackStorage storage, UUID backpackUuid, CompoundTag contentsNbt) {
      if (storage.accessLogRecords.containsKey(backpackUuid)) {
         return true;
      }

      if (contentsNbt.contains("inventory")) {
         CompoundTag inventoryNbt = contentsNbt.getCompound("inventory");
         if (inventoryNbt.contains("Items")) {
            return !inventoryNbt.getList("Items", 10).isEmpty();
         }
      }

      return false;
   }

   public CompoundTag save(CompoundTag compound, Provider registries) {
      CompoundTag ret = new CompoundTag();
      this.writeBackpackContents(ret);
      this.writeAccessLogs(ret);
      return ret;
   }

   private void writeBackpackContents(CompoundTag ret) {
      ListTag backpackContentsNbt = new ListTag();

      for (Entry<UUID, CompoundTag> entry : this.backpackContents.entrySet()) {
         CompoundTag uuidContentsPair = new CompoundTag();
         uuidContentsPair.put("uuid", NbtUtils.createUUID(entry.getKey()));
         uuidContentsPair.put("contents", (Tag)entry.getValue());
         backpackContentsNbt.add(uuidContentsPair);
      }

      ret.put("backpackContents", backpackContentsNbt);
   }

   private void writeAccessLogs(CompoundTag ret) {
      ListTag accessLogsNbt = new ListTag();

      for (AccessLogRecord alr : this.accessLogRecords.values()) {
         accessLogsNbt.add(alr.serializeToNBT());
      }

      ret.put("accessLogRecords", accessLogsNbt);
   }

   public CompoundTag getOrCreateBackpackContents(UUID backpackUuid) {
      return this.backpackContents.computeIfAbsent(backpackUuid, uuid -> {
         this.setDirty();
         return new CompoundTag();
      });
   }

   public void putAccessLog(AccessLogRecord alr) {
      this.accessLogRecords.put(alr.getBackpackUuid(), alr);
      this.setDirty();
   }

   public void removeBackpackContents(UUID backpackUuid) {
      this.backpackContents.remove(backpackUuid);
      this.setDirty();
   }

   public void setBackpackContents(UUID backpackUuid, CompoundTag contents) {
      if (!this.backpackContents.containsKey(backpackUuid)) {
         this.backpackContents.put(backpackUuid, contents);
         this.updatedBackpackSettingsFlags.add(backpackUuid);
      } else {
         CompoundTag currentContents = this.backpackContents.get(backpackUuid);

         for (String key : contents.getAllKeys()) {
            currentContents.put(key, contents.get(key));
            if (key.equals("settings")) {
               this.updatedBackpackSettingsFlags.add(backpackUuid);
            }
         }

         this.setDirty();
      }
   }

   public Map<UUID, AccessLogRecord> getAccessLogs() {
      return this.accessLogRecords;
   }

   public int removeNonPlayerBackpackContents(boolean onlyWithEmptyInventory) {
      AtomicInteger numberRemoved = new AtomicInteger(0);
      this.backpackContents.entrySet().removeIf(entry -> {
         if (this.accessLogRecords.containsKey(entry.getKey()) || onlyWithEmptyInventory && entry.getValue().contains("inventory")) {
            return false;
         }

         numberRemoved.incrementAndGet();
         return true;
      });
      if (numberRemoved.get() > 0) {
         this.setDirty();
      }

      return numberRemoved.get();
   }

   public boolean removeUpdatedBackpackSettingsFlag(UUID backpackUuid) {
      return this.updatedBackpackSettingsFlags.remove(backpackUuid);
   }

   public static void onClientWorldLoad(Load evt) {
      if (evt.getLevel().isClientSide()) {
         clientStorageCopy.backpackContents.clear();
         clientStorageCopy.accessLogRecords.clear();
      }
   }
}
