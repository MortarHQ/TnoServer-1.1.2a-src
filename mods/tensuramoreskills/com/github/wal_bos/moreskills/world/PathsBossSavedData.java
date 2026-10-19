package com.github.wal_bos.moreskills.world;

import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class PathsBossSavedData extends SavedData {
   private static final String NAME = "tensuramoreskills_paths_single_entity";
   private UUID entityId;
   private boolean spawnedEver;

   public UUID getEntityId() {
      return this.entityId;
   }

   public boolean hasSpawnedEver() {
      return this.spawnedEver;
   }

   public void setEntityId(UUID id) {
      if (id == null ? this.entityId != null : !id.equals(this.entityId)) {
         this.entityId = id;
         this.setDirty();
      }
   }

   public void setSpawnedEver(boolean spawnedEver) {
      if (this.spawnedEver != spawnedEver) {
         this.spawnedEver = spawnedEver;
         this.setDirty();
      }
   }

   public static PathsBossSavedData load(CompoundTag tag, Provider provider) {
      PathsBossSavedData data = new PathsBossSavedData();
      if (tag.hasUUID("EntityId")) {
         data.entityId = tag.getUUID("EntityId");
      }

      data.spawnedEver = tag.getBoolean("SpawnedEver");
      if (data.entityId != null) {
         data.spawnedEver = true;
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      if (this.entityId != null) {
         tag.putUUID("EntityId", this.entityId);
      }

      tag.putBoolean("SpawnedEver", this.spawnedEver);
      return tag;
   }

   public static PathsBossSavedData get(ServerLevel level) {
      return (PathsBossSavedData)level.getDataStorage()
         .computeIfAbsent(new Factory(PathsBossSavedData::new, PathsBossSavedData::load), "tensuramoreskills_paths_single_entity");
   }
}
