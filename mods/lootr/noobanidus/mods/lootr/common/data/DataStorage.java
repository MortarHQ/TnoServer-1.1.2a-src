package noobanidus.mods.lootr.common.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelResource;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.LootFiller;
import noobanidus.mods.lootr.common.api.data.NewTickingData;
import noobanidus.mods.lootr.common.api.data.TickingData;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.chunk.LoadedChunks;
import noobanidus.mods.lootr.common.command.IOUtil;
import noobanidus.mods.lootr.common.mixin.accessor.AccessorMixinDimensionDataStorage;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public class DataStorage {
   @Deprecated
   public static final String ADVANCEMENTS = "lootr/Lootr-AdvancementData";
   @Deprecated
   public static final String DECAYS = "lootr/Lootr-DecayData";
   @Deprecated
   public static final String REFRESHES = "lootr/Lootr-RefreshData";

   @Internal
   @Nullable
   public static DimensionDataStorage getDataStorage() {
      MinecraftServer server = LootrAPI.getServer();
      if (server == null) {
         LootrAPI.LOG.error("MinecraftServer is null at this stage; Lootr cannot fetch data storage.");
         return null;
      } else {
         ServerLevel overworld = server.overworld();
         if (overworld == null) {
            LootrAPI.LOG.error("The Overworld is null at this stage; Lootr cannot fetch data storage.");
            return null;
         } else {
            return overworld.getDataStorage();
         }
      }
   }

   @Deprecated
   @Internal
   public static boolean isAwarded(ILootrInfoProvider provider, ServerPlayer player) {
      return isAwarded(provider.getInfoUUID(), player);
   }

   @Deprecated
   @Internal
   public static boolean isAwarded(UUID uuid, ServerPlayer player) {
      return false;
   }

   @Deprecated
   @Internal
   public static void award(ILootrInfoProvider provider, ServerPlayer player) {
      award(provider.getInfoUUID(), player);
   }

   @Deprecated
   @Internal
   public static void award(UUID id, ServerPlayer player) {
   }

   @Internal
   public static int getDecayValue(ILootrInfoProvider provider) {
      DimensionDataStorage manager = getDataStorage();
      if (manager != null) {
         MinecraftServer server = LootrAPI.getServer();
         TickingData oldData = (TickingData)manager.computeIfAbsent(TickingData.FACTORY, "lootr/Lootr-DecayData");
         NewTickingData data = NewTickingData.getDecayData();
         data.migrateOldData(server, oldData);
         return (int)data.howLongUntilComplete(server, provider.getInfoUUID());
      } else if (provider.getInfoLevel() != null && (provider.getInfoLevel() == null || !provider.getInfoLevel().isClientSide())) {
         LootrAPI.LOG.error("DataStorage is null at this stage; Lootr cannot determine the decay value for {}.", provider.getInfoUUID());
         return -1;
      } else {
         return -1;
      }
   }

   @Internal
   public static boolean isDecayed(ILootrInfoProvider provider) {
      return getDecayValue(provider) == 0;
   }

   @Internal
   public static void setDecaying(ILootrInfoProvider provider) {
      DimensionDataStorage manager = getDataStorage();
      if (manager != null) {
         TickingData oldData = (TickingData)manager.computeIfAbsent(TickingData.FACTORY, "lootr/Lootr-DecayData");
         NewTickingData data = NewTickingData.getDecayData();
         MinecraftServer server = LootrAPI.getServer();
         data.migrateOldData(server, oldData);
         data.setCompletesIn(server, provider.getInfoUUID(), LootrAPI.getDecayValue());
      } else if (provider.getInfoLevel() != null && (provider.getInfoLevel() == null || !provider.getInfoLevel().isClientSide())) {
         LootrAPI.LOG.error("DataStorage is null at this stage; Lootr cannot set the decay value for {}.", provider.getInfoUUID());
      }
   }

   @Internal
   public static void removeDecayed(ILootrInfoProvider provider) {
   }

   @Deprecated
   @Internal
   public static void doTick() {
   }

   @Internal
   public static int getRefreshValue(ILootrInfoProvider provider) {
      DimensionDataStorage manager = getDataStorage();
      if (manager != null) {
         TickingData oldData = (TickingData)manager.computeIfAbsent(TickingData.FACTORY, "lootr/Lootr-RefreshData");
         NewTickingData data = NewTickingData.getRefreshData();
         MinecraftServer server = LootrAPI.getServer();
         data.migrateOldData(server, oldData);
         return (int)data.howLongUntilComplete(server, provider.getInfoUUID());
      } else if (provider.getInfoLevel() != null && (provider.getInfoLevel() == null || !provider.getInfoLevel().isClientSide())) {
         LootrAPI.LOG.error("DataStorage is null at this stage; Lootr cannot determine the refresh value for {}.", provider.getInfoUUID());
         return -1;
      } else {
         return -1;
      }
   }

   @Internal
   public static boolean isRefreshed(ILootrInfoProvider provider) {
      return getRefreshValue(provider) == 0;
   }

   @Internal
   public static void setRefreshing(ILootrInfoProvider provider) {
      DimensionDataStorage manager = getDataStorage();
      if (manager != null) {
         TickingData data = (TickingData)manager.computeIfAbsent(TickingData.FACTORY, "lootr/Lootr-RefreshData");
         NewTickingData newData = NewTickingData.getRefreshData();
         MinecraftServer server = LootrAPI.getServer();
         newData.migrateOldData(server, data);
         newData.setCompletesIn(server, provider.getInfoUUID(), LootrAPI.getRefreshValue());
      } else if (provider.getInfoLevel() != null && (provider.getInfoLevel() == null || !provider.getInfoLevel().isClientSide())) {
         LootrAPI.LOG.error("DataStorage is null at this stage; Lootr cannot set the refresh value for {}.", provider.getInfoUUID());
      }
   }

   @Internal
   public static void removeRefreshed(ILootrInfoProvider provider) {
   }

   @Internal
   public static LootrSavedData getData(ILootrInfoProvider provider) {
      DimensionDataStorage manager = getDataStorage();
      if (manager != null) {
         LootrSavedData result = (LootrSavedData)manager.computeIfAbsent(
            new Factory(LootrSavedData.fromInfo(provider), LootrSavedData::load, null), provider.getInfoKey()
         );
         result.update(provider);
         return result;
      } else if (provider.getInfoLevel() != null && (provider.getInfoLevel() == null || !provider.getInfoLevel().isClientSide())) {
         LootrAPI.LOG
            .error(
               "DataStorage is null at this stage; Lootr cannot fetch data for {} at {} with ID {} and cannot continue.",
               provider.getInfoDimension(),
               provider.getInfoPos(),
               provider.getInfoUUID()
            );
         return null;
      } else {
         return null;
      }
   }

   @Nullable
   public static ILootrInventory getInventory(ILootrInfoProvider provider, ServerPlayer player, LootFiller filler) {
      LootrSavedData data = getData(provider);
      return data == null ? null : data.getOrCreateInventory(provider, player, filler);
   }

   @Internal
   public static boolean clearInventories(Player player) {
      return clearInventories(player.getUUID());
   }

   public static Set<String> getAllLootrFiles() {
      DimensionDataStorage data = getDataStorage();
      if (data == null) {
         return Collections.emptySet();
      }

      MinecraftServer server = LootrAPI.getServer();
      if (server == null) {
         LootrAPI.LOG.error("MinecraftServer is null at this stage; Lootr cannot clear inventories.");
         return Collections.emptySet();
      }

      Path dataPath = server.getWorldPath(new LevelResource("data")).resolve("lootr");
      Set<String> files = new HashSet<>();

      for (String cache : ((AccessorMixinDimensionDataStorage)data).getCache().keySet()) {
         if (cache.startsWith("lootr/") && !cache.startsWith("lootr/Lootr-") && !cache.startsWith("lootr/lootr-")) {
            files.add(cache);
         }
      }

      try (Stream<Path> paths = Files.walk(dataPath)) {
         paths.forEach(path -> {
            if (Files.isRegularFile(path)) {
               String fileName = path.getFileName().toString();
               if (fileName.startsWith("lootr-") || fileName.startsWith("Lootr-")) {
                  return;
               }

               files.add("lootr/" + fileName.charAt(0) + "/" + fileName.substring(0, 2) + "/" + fileName.replace(".dat", ""));
            }
         });
         return files;
      } catch (IOException e) {
         return files;
      }
   }

   @Internal
   public static boolean clearInventories(UUID id) {
      DimensionDataStorage data = getDataStorage();
      if (data == null) {
         return false;
      }

      MinecraftServer server = LootrAPI.getServer();
      if (server == null) {
         LootrAPI.LOG.error("MinecraftServer is null at this stage; Lootr cannot clear inventories.");
         return false;
      }

      Set<String> files = getAllLootrFiles();
      int count = 0;

      for (String file : files) {
         SavedData datum = data.get(new Factory(() -> DataStorage.LootrDummyData.INSTANCE, LootrSavedData::load, null), file);
         if (datum == DataStorage.LootrDummyData.INSTANCE) {
            LootrAPI.LOG.error("Failed to load data for {}, removing from cache.", file);
            ((AccessorMixinDimensionDataStorage)data).getCache().remove(file);
         } else if (datum instanceof LootrSavedData lootrSavedData) {
            if (lootrSavedData.hasBeenOpened() && lootrSavedData.clearInventories(id)) {
               count++;
               ServerLevel level = server.getLevel(lootrSavedData.getInfoDimension());
               if (level != null) {
                  ServerChunkCache chunkCache = level.getChunkSource();
                  ChunkPos chunkPos = new ChunkPos(lootrSavedData.getInfoPos());
                  if (chunkCache.hasChunk(chunkPos.x, chunkPos.z) && LoadedChunks.getLoadedChunks(lootrSavedData.getInfoDimension()).contains(chunkPos)) {
                     ILootrInfoProvider provider = null;
                     if (lootrSavedData.isEntity()) {
                        if (level.getEntity(lootrSavedData.getInfoUUID()) instanceof ILootrEntity cart) {
                           provider = cart;
                        }
                     } else {
                        BlockEntity entity = level.getBlockEntity(lootrSavedData.getInfoPos());
                        if (LootrAPI.resolveBlockEntity(entity) instanceof ILootrBlockEntity blockEntity) {
                           provider = blockEntity;
                        }
                     }

                     if (provider != null) {
                        provider.removeVisualOpener(id);
                        provider.performClose();
                        provider.performUpdate();
                     }
                  }
               }
            }
         } else {
            LootrAPI.LOG.error("Data for {} is not a LootrSavedData instance.", file);
            ((AccessorMixinDimensionDataStorage)data).getCache().remove(file);
         }
      }

      if (count > 0) {
         data.save();
         LootrAPI.LOG.info("Cleared {} inventories for player UUID {}", count, id.toString());
         return true;
      } else {
         return false;
      }
   }

   @Internal
   public static int cullInventories() {
      DimensionDataStorage data = getDataStorage();
      if (data == null) {
         return 0;
      }

      MinecraftServer server = LootrAPI.getServer();
      if (server == null) {
         LootrAPI.LOG.error("MinecraftServer is null at this stage; Lootr cannot clear inventories.");
         return 0;
      }

      Set<String> files = getAllLootrFiles();
      Set<String> filesToDelete = new HashSet<>();

      for (String file : files) {
         SavedData datum = data.get(new Factory(() -> DataStorage.LootrDummyData.INSTANCE, LootrSavedData::load, null), file);
         if (datum == DataStorage.LootrDummyData.INSTANCE) {
            LootrAPI.LOG.error("Failed to load data for {}, removing from cache.", file);
            ((AccessorMixinDimensionDataStorage)data).getCache().remove(file);
         } else if (datum instanceof LootrSavedData lootrSavedData) {
            if (lootrSavedData.canBeCulled()) {
               filesToDelete.add(file);
               ((AccessorMixinDimensionDataStorage)data).getCache().remove(file);
            }
         } else {
            LootrAPI.LOG.error("Data for {} is not a LootrSavedData instance.", file);
            ((AccessorMixinDimensionDataStorage)data).getCache().remove(file);
         }
      }

      if (!filesToDelete.isEmpty()) {
         IOUtil.cullSavedDataAsync(server, filesToDelete);
         LootrAPI.LOG.info("Culling {} inventories.", filesToDelete.size());
      }

      return filesToDelete.size();
   }

   private static class LootrDummyData extends SavedData {
      public static final DataStorage.LootrDummyData INSTANCE = new DataStorage.LootrDummyData();

      public LootrDummyData() {
      }

      public CompoundTag save(CompoundTag p_77763_, Provider p_323640_) {
         return null;
      }
   }
}
