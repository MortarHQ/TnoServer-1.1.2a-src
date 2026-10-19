package com.github.wal_bos.moreskills.handler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class FeldwayRewardData extends SavedData {
   private static final String DATA_NAME = "tensuramoreskills_feldway_rewards";
   private final Set<UUID> rewardedPlayers = new HashSet<>();
   private final Set<UUID> pendingPlayers = new HashSet<>();

   public static Factory<FeldwayRewardData> factory() {
      return new Factory(FeldwayRewardData::new, FeldwayRewardData::load, null);
   }

   public static String name() {
      return "tensuramoreskills_feldway_rewards";
   }

   public static FeldwayRewardData load(CompoundTag tag, Provider provider) {
      FeldwayRewardData data = new FeldwayRewardData();
      ListTag rewarded = tag.getList("Rewarded", 10);

      for (int i = 0; i < rewarded.size(); i++) {
         CompoundTag entry = rewarded.getCompound(i);
         if (entry.hasUUID("Player")) {
            data.rewardedPlayers.add(entry.getUUID("Player"));
         }
      }

      ListTag pending = tag.getList("Pending", 10);

      for (int i = 0; i < pending.size(); i++) {
         CompoundTag entry = pending.getCompound(i);
         if (entry.hasUUID("Player")) {
            data.pendingPlayers.add(entry.getUUID("Player"));
         }
      }

      data.pendingPlayers.removeAll(data.rewardedPlayers);
      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag rewarded = new ListTag();

      for (UUID uuid : this.rewardedPlayers) {
         CompoundTag entry = new CompoundTag();
         entry.putUUID("Player", uuid);
         rewarded.add(entry);
      }

      ListTag pending = new ListTag();

      for (UUID uuid : this.pendingPlayers) {
         CompoundTag entry = new CompoundTag();
         entry.putUUID("Player", uuid);
         pending.add(entry);
      }

      tag.put("Rewarded", rewarded);
      tag.put("Pending", pending);
      return tag;
   }

   public boolean hasRewarded(UUID uuid) {
      return this.rewardedPlayers.contains(uuid);
   }

   public boolean isPending(UUID uuid) {
      return this.pendingPlayers.contains(uuid) && !this.rewardedPlayers.contains(uuid);
   }

   public void setPending(UUID uuid) {
      if (uuid != null) {
         if (this.rewardedPlayers.contains(uuid)) {
            this.pendingPlayers.remove(uuid);
            this.setDirty();
         } else {
            if (this.pendingPlayers.add(uuid)) {
               this.setDirty();
            }
         }
      }
   }

   public void clearPending(UUID uuid) {
      if (uuid != null && this.pendingPlayers.remove(uuid)) {
         this.setDirty();
      }
   }

   public void markRewarded(UUID uuid) {
      if (uuid != null) {
         boolean changed = this.pendingPlayers.remove(uuid);
         if (this.rewardedPlayers.add(uuid)) {
            changed = true;
         }

         if (changed) {
            this.setDirty();
         }
      }
   }

   public void resetPlayer(UUID uuid) {
      if (uuid != null) {
         boolean changed = this.pendingPlayers.remove(uuid);
         if (this.rewardedPlayers.remove(uuid)) {
            changed = true;
         }

         if (changed) {
            this.setDirty();
         }
      }
   }

   public void resetAll() {
      if (!this.rewardedPlayers.isEmpty() || !this.pendingPlayers.isEmpty()) {
         this.rewardedPlayers.clear();
         this.pendingPlayers.clear();
         this.setDirty();
      }
   }
}
