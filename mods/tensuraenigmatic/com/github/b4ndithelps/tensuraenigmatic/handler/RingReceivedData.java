package com.github.b4ndithelps.tensuraenigmatic.handler;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.level.saveddata.SavedData;

public class RingReceivedData extends SavedData {
   private static final String PLAYERS_KEY = "players";
   private final Set<String> receivedPlayers = new HashSet<>();

   public RingReceivedData() {
   }

   public RingReceivedData(CompoundTag tag) {
      ListTag list = tag.getList("players", 8);

      for (int i = 0; i < list.size(); i++) {
         this.receivedPlayers.add(list.getString(i));
      }
   }

   public boolean hasPlayerReceived(String uuid) {
      return this.receivedPlayers.contains(uuid);
   }

   public void addPlayer(String uuid) {
      if (this.receivedPlayers.add(uuid)) {
         this.setDirty();
      }
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      ListTag list = new ListTag();
      this.receivedPlayers.forEach(uuid -> list.add(StringTag.valueOf(uuid)));
      tag.put("players", list);
      return tag;
   }
}
