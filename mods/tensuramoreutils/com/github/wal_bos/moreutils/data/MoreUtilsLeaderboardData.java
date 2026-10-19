package com.github.wal_bos.moreutils.data;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class MoreUtilsLeaderboardData extends SavedData {
   private static final String DATA_NAME = "moreutils_leaderboard";
   private final Map<UUID, MoreUtilsLeaderboardData.Snapshot> snapshots = new HashMap<>();

   public static Factory<MoreUtilsLeaderboardData> factory() {
      return new Factory(MoreUtilsLeaderboardData::new, MoreUtilsLeaderboardData::load);
   }

   public static MoreUtilsLeaderboardData get(MinecraftServer server) {
      return (MoreUtilsLeaderboardData)server.overworld().getDataStorage().computeIfAbsent(factory(), "moreutils_leaderboard");
   }

   public static MoreUtilsLeaderboardData load(CompoundTag tag, Provider registries) {
      MoreUtilsLeaderboardData data = new MoreUtilsLeaderboardData();
      ListTag players = tag.getList("Players", 10);

      for (int i = 0; i < players.size(); i++) {
         CompoundTag playerTag = players.getCompound(i);
         UUID uuid = playerTag.getUUID("UUID");
         String name = playerTag.getString("Name");
         double ep = playerTag.getDouble("EP");
         int unique = playerTag.getInt("UniqueSkills");
         int ultimate = playerTag.getInt("UltimateSkills");
         if (!name.isBlank()) {
            data.snapshots.put(uuid, new MoreUtilsLeaderboardData.Snapshot(uuid, name, ep, unique, ultimate));
         }
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      ListTag players = new ListTag();

      for (MoreUtilsLeaderboardData.Snapshot snapshot : this.snapshots.values()) {
         CompoundTag playerTag = new CompoundTag();
         playerTag.putUUID("UUID", snapshot.uuid());
         playerTag.putString("Name", snapshot.name());
         playerTag.putDouble("EP", snapshot.ep());
         playerTag.putInt("UniqueSkills", snapshot.uniqueSkills());
         playerTag.putInt("UltimateSkills", snapshot.ultimateSkills());
         players.add(playerTag);
      }

      tag.put("Players", players);
      return tag;
   }

   public void update(ServerPlayer player) {
      this.snapshots.put(player.getUUID(), MoreUtilsLeaderboardData.Snapshot.from(player));
      this.setDirty();
   }

   public void updateOnlinePlayers(MinecraftServer server) {
      boolean changed = false;

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         MoreUtilsLeaderboardData.Snapshot snapshot = MoreUtilsLeaderboardData.Snapshot.from(player);
         MoreUtilsLeaderboardData.Snapshot old = this.snapshots.put(player.getUUID(), snapshot);
         if (!snapshot.equals(old)) {
            changed = true;
         }
      }

      if (changed) {
         this.setDirty();
      }
   }

   public List<MoreUtilsLeaderboardData.Snapshot> getSnapshots() {
      return new ArrayList<>(this.snapshots.values());
   }

   public record Snapshot(UUID uuid, String name, double ep, int uniqueSkills, int ultimateSkills) {
      public static MoreUtilsLeaderboardData.Snapshot from(ServerPlayer player) {
         int unique = 0;
         int ultimate = 0;

         for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
            if (instance.getSkill() instanceof Skill skill) {
               if (skill.getType() == SkillType.UNIQUE) {
                  unique++;
               } else if (skill.getType() == SkillType.ULTIMATE) {
                  ultimate++;
               }
            }
         }

         return new MoreUtilsLeaderboardData.Snapshot(player.getUUID(), player.getGameProfile().getName(), EnergyHelper.getMaxEP(player), unique, ultimate);
      }
   }
}
