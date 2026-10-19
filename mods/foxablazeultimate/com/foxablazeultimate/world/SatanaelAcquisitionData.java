package com.foxablazeultimate.world;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class SatanaelAcquisitionData extends SavedData {
   private static final String STORAGE_KEY = "foxablazeultimate_satanael_acquisition";
   private static final Factory<SatanaelAcquisitionData> FACTORY = new Factory(SatanaelAcquisitionData::new, SatanaelAcquisitionData::load, (DataFixTypes)null);
   private final Set<UUID> witnessedNamedDeath = new HashSet<>();
   private final Set<UUID> inactivityReclaimed = new HashSet<>();

   public static SatanaelAcquisitionData get(ServerLevel level) {
      return (SatanaelAcquisitionData)level.getServer().overworld().getDataStorage().computeIfAbsent(FACTORY, "foxablazeultimate_satanael_acquisition");
   }

   public static SatanaelAcquisitionData peek(ServerLevel level) {
      return level != null && level.getServer() != null && level.getServer().overworld() != null
         ? (SatanaelAcquisitionData)level.getServer().overworld().getDataStorage().get(FACTORY, "foxablazeultimate_satanael_acquisition")
         : null;
   }

   public boolean hasWitnessedNamedDeath(UUID playerId) {
      return playerId != null && this.witnessedNamedDeath.contains(playerId);
   }

   public void markWitnessedNamedDeath(UUID playerId) {
      if (playerId != null && this.witnessedNamedDeath.add(playerId)) {
         this.setDirty();
      }
   }

   public boolean isInactivityReclaimed(UUID playerId) {
      return playerId != null && this.inactivityReclaimed.contains(playerId);
   }

   public void markInactivityReclaimed(UUID playerId) {
      if (playerId != null && this.inactivityReclaimed.add(playerId)) {
         this.setDirty();
      }
   }

   public void clearInactivityReclaimed(UUID playerId) {
      if (playerId != null && this.inactivityReclaimed.remove(playerId)) {
         this.setDirty();
      }
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag players = new ListTag();

      for (UUID playerId : this.witnessedNamedDeath) {
         CompoundTag playerTag = new CompoundTag();
         playerTag.putUUID("player", playerId);
         players.add(playerTag);
      }

      tag.put("players", players);
      ListTag reclaimedPlayers = new ListTag();

      for (UUID playerId : this.inactivityReclaimed) {
         CompoundTag playerTag = new CompoundTag();
         playerTag.putUUID("player", playerId);
         reclaimedPlayers.add(playerTag);
      }

      tag.put("inactivity_reclaimed_players", reclaimedPlayers);
      return tag;
   }

   private static SatanaelAcquisitionData load(CompoundTag tag, Provider provider) {
      SatanaelAcquisitionData data = new SatanaelAcquisitionData();
      ListTag players = tag.getList("players", 10);

      for (int index = 0; index < players.size(); index++) {
         CompoundTag playerTag = players.getCompound(index);
         if (playerTag.hasUUID("player")) {
            data.witnessedNamedDeath.add(playerTag.getUUID("player"));
         }
      }

      ListTag reclaimedPlayers = tag.getList("inactivity_reclaimed_players", 10);

      for (int index = 0; index < reclaimedPlayers.size(); index++) {
         CompoundTag playerTag = reclaimedPlayers.getCompound(index);
         if (playerTag.hasUUID("player")) {
            data.inactivityReclaimed.add(playerTag.getUUID("player"));
         }
      }

      return data;
   }
}
