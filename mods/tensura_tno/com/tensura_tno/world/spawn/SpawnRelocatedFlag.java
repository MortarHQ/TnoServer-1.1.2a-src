package com.tensura_tno.world.spawn;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public final class SpawnRelocatedFlag extends SavedData {
   static final String DATA_NAME = "tensura_tno_spawn_relocated";
   private static final String NBT_KEY_DONE = "done";
   private boolean done = false;

   private SpawnRelocatedFlag() {
   }

   private static SpawnRelocatedFlag create() {
      return new SpawnRelocatedFlag();
   }

   public static SpawnRelocatedFlag load(CompoundTag tag, Provider provider) {
      SpawnRelocatedFlag flag = new SpawnRelocatedFlag();
      flag.done = tag.getBoolean("done");
      return flag;
   }

   public static SpawnRelocatedFlag get(ServerLevel level) {
      DimensionDataStorage storage = level.getDataStorage();
      return (SpawnRelocatedFlag)storage.computeIfAbsent(new Factory(SpawnRelocatedFlag::create, SpawnRelocatedFlag::load, null), "tensura_tno_spawn_relocated");
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      tag.putBoolean("done", this.done);
      return tag;
   }

   public boolean isDone() {
      return this.done;
   }

   public void markDone() {
      this.done = true;
      this.setDirty();
   }
}
