package net.p3pp3rf1y.sophisticatedcore.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class SettingsTemplateStorage extends SavedData {
   private static final String SAVED_DATA_NAME = "sophisticatedcore_settings_templates";
   private Map<UUID, Map<Integer, CompoundTag>> playerTemplates = new HashMap<>();
   private Map<UUID, Map<String, CompoundTag>> playerNamedTemplates = new HashMap<>();
   private static final SettingsTemplateStorage clientStorageCopy = new SettingsTemplateStorage();

   private SettingsTemplateStorage() {
   }

   private SettingsTemplateStorage(Map<UUID, Map<Integer, CompoundTag>> playerTemplates, Map<UUID, Map<String, CompoundTag>> playerNamedTemplates) {
      this.playerTemplates = playerTemplates;
      this.playerNamedTemplates = playerNamedTemplates;
   }

   public void putPlayerTemplate(Player player, int slot, CompoundTag settingsTag) {
      this.playerTemplates.computeIfAbsent(player.getUUID(), u -> new HashMap<>()).put(slot, settingsTag);
      this.setDirty();
   }

   public void putPlayerNamedTemplate(Player player, String name, CompoundTag settingsTag) {
      this.playerNamedTemplates.computeIfAbsent(player.getUUID(), u -> new TreeMap<>()).put(name, settingsTag);
      this.setDirty();
   }

   public Map<Integer, CompoundTag> getPlayerTemplates(Player player) {
      return this.playerTemplates.getOrDefault(player.getUUID(), new HashMap<>());
   }

   public Map<String, CompoundTag> getPlayerNamedTemplates(Player player) {
      return this.playerNamedTemplates.getOrDefault(player.getUUID(), new TreeMap<>());
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      NBTHelper.putMap(
         tag,
         "playerTemplates",
         this.playerTemplates,
         UUID::toString,
         slotTemplates -> NBTHelper.putMap(new CompoundTag(), "slotTemplates", slotTemplates, String::valueOf, settingsTag -> settingsTag)
      );
      NBTHelper.putMap(
         tag,
         "playerNamedTemplates",
         this.playerNamedTemplates,
         UUID::toString,
         namedTemplates -> NBTHelper.putMap(new CompoundTag(), "namedTemplates", namedTemplates, v -> v, settingsTag -> settingsTag)
      );
      return tag;
   }

   public static SettingsTemplateStorage get() {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            ServerLevel overworld = server.getLevel(Level.OVERWORLD);
            DimensionDataStorage storage = overworld.getDataStorage();
            return (SettingsTemplateStorage)storage.computeIfAbsent(
               new Factory(SettingsTemplateStorage::new, SettingsTemplateStorage::load), "sophisticatedcore_settings_templates"
            );
         }
      }

      return clientStorageCopy;
   }

   private static SettingsTemplateStorage load(CompoundTag tag, Provider registries) {
      return new SettingsTemplateStorage(
         NBTHelper.<UUID, Map<Integer, CompoundTag>>getMap(
               tag,
               "playerTemplates",
               UUID::fromString,
               (key, playerTemplatesTag) -> NBTHelper.getMap(
                  (CompoundTag)playerTemplatesTag, "slotTemplates", Integer::valueOf, (k, settingsTag) -> Optional.of((CompoundTag)settingsTag)
               )
            )
            .orElse(new HashMap<>()),
         NBTHelper.<UUID, Map<String, CompoundTag>>getMap(
               tag,
               "playerNamedTemplates",
               UUID::fromString,
               (key, playerNamedTemplatesTag) -> NBTHelper.getMap(
                  (CompoundTag)playerNamedTemplatesTag, "namedTemplates", v -> v, (k, settingsTag) -> Optional.of((CompoundTag)settingsTag), TreeMap::new
               )
            )
            .orElse(new TreeMap<>())
      );
   }

   public void clearPlayerTemplates(Player player) {
      this.playerTemplates.remove(player.getUUID());
      this.playerNamedTemplates.remove(player.getUUID());
      this.setDirty();
   }
}
