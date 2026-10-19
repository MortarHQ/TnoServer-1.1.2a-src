package com.chronodawn.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class TimeKeeperVillageData extends CompatSavedData {
   private static final String DATA_NAME = "chronodawn_time_keeper_village";
   private boolean placed = false;
   private BlockPos position = BlockPos.ZERO;

   public static TimeKeeperVillageData get(ServerLevel level) {
      DimensionDataStorage storage = level.getDataStorage();
      return CompatSavedData.computeIfAbsent(storage, TimeKeeperVillageData::new, TimeKeeperVillageData::load, "chronodawn_time_keeper_village");
   }

   public static TimeKeeperVillageData load(CompoundTag tag, Provider provider) {
      TimeKeeperVillageData data = new TimeKeeperVillageData();
      data.loadData(tag);
      ChronoDawn.LOGGER.debug("Loaded TimeKeeperVillageData: placed={}, position={}", data.placed, data.position);
      return data;
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      tag.putBoolean("Placed", this.placed);
      tag.putInt("PosX", this.position.getX());
      tag.putInt("PosY", this.position.getY());
      tag.putInt("PosZ", this.position.getZ());
      return tag;
   }

   @Override
   public void loadData(CompoundTag tag) {
      this.placed = tag.getBoolean("Placed");
      if (tag.contains("PosX") && tag.contains("PosY") && tag.contains("PosZ")) {
         this.position = new BlockPos(tag.getInt("PosX"), tag.getInt("PosY"), tag.getInt("PosZ"));
      }
   }

   public boolean isPlaced() {
      return this.placed;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public void setPlaced(BlockPos pos) {
      this.placed = true;
      this.position = pos.immutable();
      this.setDirty();
      ChronoDawn.LOGGER.debug("Time Keeper Village marked as placed at {}", pos);
   }
}
