package noobanidus.mods.lootr.common.api.data;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.File;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import noobanidus.mods.lootr.common.api.LootrAPI;

public class NewTickingData {
   private static final Function<String, Factory<NewTickingData.Section>> STRING_FACTORY = name -> new Factory(
      () -> new NewTickingData.Section(name), NewTickingData.Section::load, null
   );
   private static final Object2ObjectMap<UUID, String> CACHED_NAMES = new Object2ObjectOpenHashMap();
   private static final Object2ObjectMap<UUID, String> CACHED_FILE_NAMES = new Object2ObjectOpenHashMap();
   private static final NewTickingData REFRESH_DATA = new NewTickingData(NewTickingData.TickingType.REFRESH);
   private static final NewTickingData DECAY_DATA = new NewTickingData(NewTickingData.TickingType.DECAY);
   private final String prefix;
   private final NewTickingData.TickingType type;

   public static NewTickingData getRefreshData() {
      return REFRESH_DATA;
   }

   public static NewTickingData getDecayData() {
      return DECAY_DATA;
   }

   public void migrateOldData(MinecraftServer server, TickingData oldData) {
      long currentGameTime = server.getWorldData().overworldData().getGameTime();
      ObjectIterator var5 = oldData.getTickMap().object2IntEntrySet().iterator();

      while (var5.hasNext()) {
         Entry<UUID> entry = (Entry<UUID>)var5.next();
         UUID id = (UUID)entry.getKey();
         long completesAt = currentGameTime;
         int oldValue = entry.getIntValue();
         if (oldValue > 0) {
            completesAt += oldValue;
         }

         NewTickingData.Section section = this.getSection(server, id);

         try {
            section.setCompletesAt(id, completesAt);
         } catch (NewTickingData.SectionException e) {
            LootrAPI.LOG.error("Unable to migrate {} ticking data for id {}: section mismatch, expected {}", this.type.getPrefix(), id, section.cachedName);
         }
      }

      oldData.clear();
   }

   protected NewTickingData(NewTickingData.TickingType type) {
      this.type = type;
      this.prefix = "lootr/ticking/" + type.getPrefix() + "/";
   }

   public void setCompletesIn(MinecraftServer server, UUID id, long tickTime) {
      NewTickingData.Section section = this.getSection(server, id);

      try {
         section.setCompletesAt(id, server.getWorldData().overworldData().getGameTime() + tickTime);
      } catch (NewTickingData.SectionException e) {
         LootrAPI.LOG.error("Unable to set {} ticking data for id {}: section mismatch, expected {}", this.type.getPrefix(), id, section.cachedName);
      }
   }

   public void clearTicking(MinecraftServer server, UUID id) {
      NewTickingData.Section section = this.getSection(server, id);

      try {
         section.setCompletesAt(id, -1L);
      } catch (NewTickingData.SectionException e) {
         LootrAPI.LOG.error("Unable to clear {} ticking data for id {}: section mismatch, expected {}", this.type.getPrefix(), id, section.cachedName);
      }
   }

   public long howLongUntilComplete(MinecraftServer server, UUID id) {
      NewTickingData.Section section = this.getSection(server, id);

      try {
         long completesAt = section.completesAt(id);
         if (completesAt == -1L) {
            return -1L;
         }

         long currentTime = server.getWorldData().overworldData().getGameTime();
         return Math.max(0L, completesAt - currentTime);
      } catch (NewTickingData.SectionException e) {
         LootrAPI.LOG.error("Unable to get {} ticking data for id {}: section mismatch, expected {}", this.type.getPrefix(), id, section.cachedName);
         return -1L;
      }
   }

   private static String getCached(UUID id) {
      return (String)CACHED_NAMES.computeIfAbsent(id, UUID::toString);
   }

   private static String getBaseFileName(UUID id) {
      return (String)CACHED_FILE_NAMES.computeIfAbsent(id, uuid -> {
         String name = getCached(uuid);
         return name.substring(0, 2);
      });
   }

   private String getFileName(UUID id) {
      return this.prefix + getBaseFileName(id);
   }

   private NewTickingData.Section getSection(MinecraftServer server, UUID id) {
      ServerLevel level = server.overworld();
      DimensionDataStorage dataStorage = level.getDataStorage();
      return (NewTickingData.Section)dataStorage.computeIfAbsent(STRING_FACTORY.apply(getBaseFileName(id)), this.getFileName(id));
   }

   protected static class Section extends SavedData {
      private final Object2LongMap<UUID> tickMap = new Object2LongOpenHashMap();
      private final String cachedName;

      public Section(String cachedName) {
         this.tickMap.defaultReturnValue(-1L);
         this.cachedName = cachedName;
      }

      private boolean excludes(UUID id) {
         return !this.cachedName.equals(NewTickingData.getBaseFileName(id));
      }

      public boolean completed(MinecraftServer server, UUID id) throws NewTickingData.SectionException {
         if (this.excludes(id)) {
            throw new NewTickingData.SectionException();
         }

         long completesAt = this.completesAt(id);
         return completesAt == -1L ? false : server.getWorldData().overworldData().getGameTime() >= completesAt;
      }

      public long completesAt(UUID id) throws NewTickingData.SectionException {
         if (this.excludes(id)) {
            throw new NewTickingData.SectionException();
         } else {
            return this.tickMap.getLong(id);
         }
      }

      public void setCompletesAt(UUID id, long tickTime) throws NewTickingData.SectionException {
         if (this.excludes(id)) {
            throw new NewTickingData.SectionException();
         }

         this.tickMap.put(id, tickTime);
         this.setDirty();
      }

      public CompoundTag save(CompoundTag tag, Provider registries) {
         ListTag decayList = new ListTag();
         ObjectIterator var4 = this.tickMap.object2LongEntrySet().iterator();

         while (var4.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<UUID> entry = (it.unimi.dsi.fastutil.objects.Object2LongMap.Entry<UUID>)var4.next();
            CompoundTag thisTag = new CompoundTag();
            thisTag.putUUID("id", (UUID)entry.getKey());
            thisTag.putLong("value", entry.getLongValue());
            decayList.add(thisTag);
         }

         tag.put("result", decayList);
         tag.putString("cachedName", this.cachedName);
         return tag;
      }

      public static NewTickingData.Section load(CompoundTag pCompound, Provider provider) {
         String cachedName = pCompound.getString("cachedName");
         NewTickingData.Section data = new NewTickingData.Section(cachedName);
         data.tickMap.clear();
         data.tickMap.defaultReturnValue(-1L);
         ListTag decayList = pCompound.getList("result", 10);

         for (int i = 0; i < decayList.size(); i++) {
            CompoundTag thisTag = decayList.getCompound(i);
            data.tickMap.put(thisTag.getUUID("id"), thisTag.getLong("value"));
         }

         return data;
      }

      public void save(File file, Provider registries) {
         if (this.isDirty()) {
            file.getParentFile().mkdirs();
         }

         super.save(file, registries);
      }
   }

   public static class SectionException extends Exception {
   }

   public enum TickingType {
      DECAY("decay"),
      REFRESH("refresh");

      private final String prefix;

      TickingType(String prefix) {
         this.prefix = prefix;
      }

      public String getPrefix() {
         return this.prefix;
      }
   }
}
