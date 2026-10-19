package com.github.wal_bos.moreskills.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class ParasiteServerData extends SavedData {
   public static final int CLEAN_HOST = 1;
   public static final int CLEAN_PARASITE = 2;
   private static final String FILE_NAME = "tensuramoreskills_parasite";
   private final Map<UUID, CompoundTag> pending = new HashMap<>();

   public static ParasiteServerData get(MinecraftServer server) {
      return (ParasiteServerData)server.overworld()
         .getDataStorage()
         .computeIfAbsent(new Factory(ParasiteServerData::new, ParasiteServerData::load), "tensuramoreskills_parasite");
   }

   public static ParasiteServerData load(CompoundTag tag, Provider registries) {
      ParasiteServerData data = new ParasiteServerData();

      for (Tag raw : tag.getList("Pending", 10)) {
         CompoundTag entry = (CompoundTag)raw;
         if (entry.hasUUID("Player") && entry.contains("Action", 10)) {
            data.pending.put(entry.getUUID("Player"), entry.getCompound("Action").copy());
         }
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      ListTag list = new ListTag();

      for (Entry<UUID, CompoundTag> entry : this.pending.entrySet()) {
         CompoundTag saved = new CompoundTag();
         saved.putUUID("Player", entry.getKey());
         saved.put("Action", entry.getValue().copy());
         list.add(saved);
      }

      tag.put("Pending", list);
      return tag;
   }

   public void queueHostCleanup(UUID host, UUID parasite) {
      CompoundTag action = new CompoundTag();
      action.putInt("Type", 1);
      action.putUUID("Other", parasite);
      this.pending.put(host, action);
      this.setDirty();
   }

   public void queueParasiteCleanup(UUID parasite, UUID host, double reward, ListTag uniqueCandidates) {
      CompoundTag action = new CompoundTag();
      action.putInt("Type", 2);
      action.putUUID("Other", host);
      action.putDouble("Reward", Math.max(0.0, reward));
      action.put("UniqueCandidates", uniqueCandidates.copy());
      this.pending.put(parasite, action);
      this.setDirty();
   }

   public CompoundTag take(UUID player) {
      CompoundTag action = this.pending.remove(player);
      if (action != null) {
         this.setDirty();
      }

      return action;
   }
}
