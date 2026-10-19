package com.benji.netherman.common.data;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class TeleportDestinationData extends SavedData {
   private final Set<BlockPos> destinations = new HashSet<>();

   public void addDestination(BlockPos pos) {
      if (this.destinations.add(pos)) {
         this.setDirty();
      }
   }

   public void removeDestination(BlockPos pos) {
      if (this.destinations.remove(pos)) {
         this.setDirty();
      }
   }

   public BlockPos getClosestDestination(BlockPos sourcePos) {
      if (this.destinations.isEmpty()) {
         return null;
      }

      BlockPos closest = null;
      double minDistanceSq = Double.MAX_VALUE;

      for (BlockPos pos : this.destinations) {
         if (!pos.equals(sourcePos)) {
            double distSq = pos.distSqr(sourcePos);
            if (distSq < minDistanceSq) {
               minDistanceSq = distSq;
               closest = pos;
            }
         }
      }

      return closest;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag list = new ListTag();

      for (BlockPos pos : this.destinations) {
         list.add(LongTag.valueOf(pos.asLong()));
      }

      tag.put("Destinations", list);
      return tag;
   }

   public static TeleportDestinationData load(CompoundTag tag, Provider provider) {
      TeleportDestinationData data = new TeleportDestinationData();
      ListTag list = tag.getList("Destinations", 4);

      for (int i = 0; i < list.size(); i++) {
         data.destinations.add(BlockPos.of(((LongTag)list.get(i)).getAsLong()));
      }

      return data;
   }

   public static TeleportDestinationData get(ServerLevel level) {
      return (TeleportDestinationData)level.getDataStorage()
         .computeIfAbsent(new Factory(TeleportDestinationData::new, TeleportDestinationData::load, null), "netherman_teleports");
   }
}
