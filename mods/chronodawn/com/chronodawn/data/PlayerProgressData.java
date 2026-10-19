package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;

public class PlayerProgressData extends ChronoDawnWorldData {
   private static final String DATA_NAME = "chronodawn_player_progress";
   private final Map<UUID, PlayerProgressData.PlayerProgress> playerData = new HashMap<>();

   public static PlayerProgressData get(ServerLevel level) {
      return CompatSavedData.computeIfAbsent(level.getDataStorage(), PlayerProgressData::new, PlayerProgressData::load, "chronodawn_player_progress");
   }

   private static PlayerProgressData load(CompoundTag tag, Provider registries) {
      PlayerProgressData data = new PlayerProgressData();
      CompoundTag playersTag = tag.getCompound("players");
      ChronoDawn.LOGGER.debug("PlayerProgressData loading: {} players in NBT", playersTag.getAllKeys().size());

      for (String key : playersTag.getAllKeys()) {
         UUID playerId = UUID.fromString(key);
         CompoundTag playerTag = playersTag.getCompound(key);
         PlayerProgressData.PlayerProgress progress = new PlayerProgressData.PlayerProgress();
         progress.hasChronosEye = playerTag.getBoolean("has_chronos_eye");
         ListTag portalList = playerTag.getList("stabilized_portals", 8);

         for (int i = 0; i < portalList.size(); i++) {
            progress.stabilizedPortals.add(UUID.fromString(portalList.getString(i)));
         }

         ListTag bossList = playerTag.getList("defeated_bosses", 8);

         for (int i = 0; i < bossList.size(); i++) {
            progress.defeatedBosses.add(bossList.getString(i));
         }

         progress.shieldSpeedCooldownEndTick = playerTag.getLong("shield_speed_cd");
         progress.shieldEchoActiveUntilTick = playerTag.getLong("shield_echo_until");
         progress.shieldEchoCooldownEndTick = playerTag.getLong("shield_echo_cd");
         data.playerData.put(playerId, progress);
         ChronoDawn.LOGGER.debug("  Loaded player {}: hasChronosEye={}", playerId, progress.hasChronosEye);
      }

      return data;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      CompoundTag playersTag = new CompoundTag();

      for (Entry<UUID, PlayerProgressData.PlayerProgress> entry : this.playerData.entrySet()) {
         CompoundTag playerTag = new CompoundTag();
         PlayerProgressData.PlayerProgress progress = entry.getValue();
         playerTag.putBoolean("has_chronos_eye", progress.hasChronosEye);
         ListTag portalList = new ListTag();

         for (UUID portalId : progress.stabilizedPortals) {
            portalList.add(StringTag.valueOf(portalId.toString()));
         }

         playerTag.put("stabilized_portals", portalList);
         ListTag bossList = new ListTag();

         for (String bossName : progress.defeatedBosses) {
            bossList.add(StringTag.valueOf(bossName));
         }

         playerTag.put("defeated_bosses", bossList);
         playerTag.putLong("shield_speed_cd", progress.shieldSpeedCooldownEndTick);
         playerTag.putLong("shield_echo_until", progress.shieldEchoActiveUntilTick);
         playerTag.putLong("shield_echo_cd", progress.shieldEchoCooldownEndTick);
         playersTag.put(entry.getKey().toString(), playerTag);
      }

      tag.put("players", playersTag);
      ChronoDawn.LOGGER.debug("PlayerProgressData saved: {} players", this.playerData.size());

      for (Entry<UUID, PlayerProgressData.PlayerProgress> entry : this.playerData.entrySet()) {
         ChronoDawn.LOGGER.debug("  Player {}: hasChronosEye={}", entry.getKey(), entry.getValue().hasChronosEye);
      }

      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      CompoundTag playersTag = tag.getCompound("players");

      for (String key : playersTag.getAllKeys()) {
         UUID playerId = UUID.fromString(key);
         CompoundTag playerTag = playersTag.getCompound(key);
         PlayerProgressData.PlayerProgress progress = new PlayerProgressData.PlayerProgress();
         progress.hasChronosEye = playerTag.getBoolean("has_chronos_eye");
         ListTag portalList = playerTag.getList("stabilized_portals", 8);

         for (int i = 0; i < portalList.size(); i++) {
            progress.stabilizedPortals.add(UUID.fromString(portalList.getString(i)));
         }

         ListTag bossList = playerTag.getList("defeated_bosses", 8);

         for (int i = 0; i < bossList.size(); i++) {
            progress.defeatedBosses.add(bossList.getString(i));
         }

         progress.shieldSpeedCooldownEndTick = playerTag.getLong("shield_speed_cd");
         progress.shieldEchoActiveUntilTick = playerTag.getLong("shield_echo_until");
         progress.shieldEchoCooldownEndTick = playerTag.getLong("shield_echo_cd");
         this.playerData.put(playerId, progress);
      }
   }

   public PlayerProgressData.PlayerProgress getProgress(UUID playerId) {
      return this.playerData.computeIfAbsent(playerId, k -> new PlayerProgressData.PlayerProgress());
   }

   public boolean hasStabilizedPortals(UUID playerId) {
      PlayerProgressData.PlayerProgress progress = this.playerData.get(playerId);
      return progress != null && !progress.stabilizedPortals.isEmpty();
   }

   public void setChronosEye(UUID playerId, boolean hasEye) {
      PlayerProgressData.PlayerProgress progress = this.getProgress(playerId);
      progress.hasChronosEye = hasEye;
      this.setDirty();
   }

   public void addStabilizedPortal(UUID playerId, UUID portalId) {
      PlayerProgressData.PlayerProgress progress = this.getProgress(playerId);
      progress.stabilizedPortals.add(portalId);
      this.setDirty();
   }

   public void addDefeatedBoss(UUID playerId, String bossName) {
      PlayerProgressData.PlayerProgress progress = this.getProgress(playerId);
      progress.defeatedBosses.add(bossName);
      this.setDirty();
   }

   public long getShieldSpeedCooldownEnd(UUID playerId) {
      return this.playerData.getOrDefault(playerId, new PlayerProgressData.PlayerProgress()).shieldSpeedCooldownEndTick;
   }

   public void setShieldSpeedCooldownEnd(UUID playerId, long endTick) {
      this.playerData.computeIfAbsent(playerId, k -> new PlayerProgressData.PlayerProgress()).shieldSpeedCooldownEndTick = endTick;
      this.setDirty();
   }

   public long getShieldEchoActiveUntil(UUID playerId) {
      return this.playerData.getOrDefault(playerId, new PlayerProgressData.PlayerProgress()).shieldEchoActiveUntilTick;
   }

   public void setShieldEchoActiveUntil(UUID playerId, long untilTick) {
      this.playerData.computeIfAbsent(playerId, k -> new PlayerProgressData.PlayerProgress()).shieldEchoActiveUntilTick = untilTick;
      this.setDirty();
   }

   public long getShieldEchoCooldownEnd(UUID playerId) {
      return this.playerData.getOrDefault(playerId, new PlayerProgressData.PlayerProgress()).shieldEchoCooldownEndTick;
   }

   public void setShieldEchoCooldownEnd(UUID playerId, long endTick) {
      this.playerData.computeIfAbsent(playerId, k -> new PlayerProgressData.PlayerProgress()).shieldEchoCooldownEndTick = endTick;
      this.setDirty();
   }

   public static class PlayerProgress {
      public boolean hasChronosEye = false;
      public Set<UUID> stabilizedPortals = new HashSet<>();
      public Set<String> defeatedBosses = new HashSet<>();
      public long shieldSpeedCooldownEndTick = 0L;
      public long shieldEchoActiveUntilTick = 0L;
      public long shieldEchoCooldownEndTick = 0L;
   }
}
