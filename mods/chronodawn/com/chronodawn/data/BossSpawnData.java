package com.chronodawn.data;

import com.chronodawn.compat.CompatSavedData;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class BossSpawnData extends CompatSavedData {
   private static final String DATA_NAME = "boss_spawns";
   private final Set<BlockPos> timeTyrantSpawnedDoors = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private int timeTyrantSpawnCount = 0;
   private final Set<BlockPos> timeGuardianSpawnedStructures = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Set<BlockPos> chronosWardenSpawnedDoors = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Set<BlockPos> entropyKeeperProcessedStructures = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Set<BlockPos> entropyKeeperSpawnedMarkers = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Set<BlockPos> clockworkColossusSpawnedStructures = Collections.newSetFromMap(new ConcurrentHashMap<>());

   public static String getDataName() {
      return "boss_spawns";
   }

   public static BossSpawnData load(CompoundTag tag, Provider provider) {
      BossSpawnData data = new BossSpawnData();
      data.loadData(tag);
      return data;
   }

   @Override
   public void loadData(CompoundTag tag) {
      if (tag.contains("TimeTyrant")) {
         CompoundTag tyrantTag = tag.getCompound("TimeTyrant");
         ListTag doorsList = tyrantTag.getList("SpawnedDoors", 10);

         for (int i = 0; i < doorsList.size(); i++) {
            CompoundTag doorTag = doorsList.getCompound(i);
            BlockPos pos = BlockPos.of(doorTag.getLong("Pos"));
            this.timeTyrantSpawnedDoors.add(pos);
         }

         this.timeTyrantSpawnCount = tyrantTag.getInt("SpawnCount");
      }

      if (tag.contains("TimeGuardian")) {
         CompoundTag guardianTag = tag.getCompound("TimeGuardian");
         ListTag structuresList = guardianTag.getList("SpawnedStructures", 10);

         for (int i = 0; i < structuresList.size(); i++) {
            CompoundTag structureTag = structuresList.getCompound(i);
            BlockPos pos = BlockPos.of(structureTag.getLong("Pos"));
            this.timeGuardianSpawnedStructures.add(pos);
         }
      }

      if (tag.contains("ChronosWarden")) {
         CompoundTag wardenTag = tag.getCompound("ChronosWarden");
         ListTag doorsList = wardenTag.getList("SpawnedDoors", 10);

         for (int i = 0; i < doorsList.size(); i++) {
            CompoundTag doorTag = doorsList.getCompound(i);
            BlockPos pos = BlockPos.of(doorTag.getLong("Pos"));
            this.chronosWardenSpawnedDoors.add(pos);
         }
      }

      if (tag.contains("EntropyKeeper")) {
         CompoundTag keeperTag = tag.getCompound("EntropyKeeper");
         ListTag processedList = keeperTag.getList("ProcessedStructures", 10);

         for (int i = 0; i < processedList.size(); i++) {
            CompoundTag structureTag = processedList.getCompound(i);
            BlockPos pos = BlockPos.of(structureTag.getLong("Pos"));
            this.entropyKeeperProcessedStructures.add(pos);
         }

         ListTag markersList = keeperTag.getList("SpawnedMarkers", 10);

         for (int i = 0; i < markersList.size(); i++) {
            CompoundTag markerTag = markersList.getCompound(i);
            BlockPos pos = BlockPos.of(markerTag.getLong("Pos"));
            this.entropyKeeperSpawnedMarkers.add(pos);
         }
      }

      if (tag.contains("ClockworkColossus")) {
         CompoundTag colossusTag = tag.getCompound("ClockworkColossus");
         ListTag structuresList = colossusTag.getList("SpawnedStructures", 10);

         for (int i = 0; i < structuresList.size(); i++) {
            CompoundTag structureTag = structuresList.getCompound(i);
            BlockPos pos = BlockPos.of(structureTag.getLong("Pos"));
            this.clockworkColossusSpawnedStructures.add(pos);
         }
      }
   }

   @Override
   public CompoundTag saveData(CompoundTag tag) {
      CompoundTag tyrantTag = new CompoundTag();
      ListTag tyrantDoorsList = new ListTag();

      for (BlockPos pos : this.timeTyrantSpawnedDoors) {
         CompoundTag doorTag = new CompoundTag();
         doorTag.putLong("Pos", pos.asLong());
         tyrantDoorsList.add(doorTag);
      }

      tyrantTag.put("SpawnedDoors", tyrantDoorsList);
      tyrantTag.putInt("SpawnCount", this.timeTyrantSpawnCount);
      tag.put("TimeTyrant", tyrantTag);
      CompoundTag guardianTag = new CompoundTag();
      ListTag guardianStructuresList = new ListTag();

      for (BlockPos pos : this.timeGuardianSpawnedStructures) {
         CompoundTag structureTag = new CompoundTag();
         structureTag.putLong("Pos", pos.asLong());
         guardianStructuresList.add(structureTag);
      }

      guardianTag.put("SpawnedStructures", guardianStructuresList);
      tag.put("TimeGuardian", guardianTag);
      CompoundTag wardenTag = new CompoundTag();
      ListTag wardenDoorsList = new ListTag();

      for (BlockPos pos : this.chronosWardenSpawnedDoors) {
         CompoundTag doorTag = new CompoundTag();
         doorTag.putLong("Pos", pos.asLong());
         wardenDoorsList.add(doorTag);
      }

      wardenTag.put("SpawnedDoors", wardenDoorsList);
      tag.put("ChronosWarden", wardenTag);
      CompoundTag keeperTag = new CompoundTag();
      ListTag processedList = new ListTag();

      for (BlockPos pos : this.entropyKeeperProcessedStructures) {
         CompoundTag structureTag = new CompoundTag();
         structureTag.putLong("Pos", pos.asLong());
         processedList.add(structureTag);
      }

      keeperTag.put("ProcessedStructures", processedList);
      ListTag markersList = new ListTag();

      for (BlockPos pos : this.entropyKeeperSpawnedMarkers) {
         CompoundTag markerTag = new CompoundTag();
         markerTag.putLong("Pos", pos.asLong());
         markersList.add(markerTag);
      }

      keeperTag.put("SpawnedMarkers", markersList);
      tag.put("EntropyKeeper", keeperTag);
      CompoundTag colossusTag = new CompoundTag();
      ListTag colossusStructuresList = new ListTag();

      for (BlockPos pos : this.clockworkColossusSpawnedStructures) {
         CompoundTag structureTag = new CompoundTag();
         structureTag.putLong("Pos", pos.asLong());
         colossusStructuresList.add(structureTag);
      }

      colossusTag.put("SpawnedStructures", colossusStructuresList);
      tag.put("ClockworkColossus", colossusTag);
      return tag;
   }

   public boolean hasTimeTyrantDoorSpawned(BlockPos pos) {
      return this.timeTyrantSpawnedDoors.contains(pos);
   }

   public void markTimeTyrantDoorSpawned(BlockPos pos) {
      this.timeTyrantSpawnedDoors.add(pos);
      this.setDirty();
   }

   public int getTimeTyrantSpawnCount() {
      return this.timeTyrantSpawnCount;
   }

   public void incrementTimeTyrantSpawnCount() {
      this.timeTyrantSpawnCount++;
      this.setDirty();
   }

   public boolean hasTimeGuardianStructureSpawned(BlockPos pos) {
      return this.timeGuardianSpawnedStructures.contains(pos);
   }

   public void markTimeGuardianStructureSpawned(BlockPos pos) {
      this.timeGuardianSpawnedStructures.add(pos);
      this.setDirty();
   }

   public boolean hasChronosWardenDoorSpawned(BlockPos pos) {
      return this.chronosWardenSpawnedDoors.contains(pos);
   }

   public void markChronosWardenDoorSpawned(BlockPos pos) {
      this.chronosWardenSpawnedDoors.add(pos);
      this.setDirty();
   }

   public boolean isEntropyKeeperStructureProcessed(BlockPos pos) {
      return this.entropyKeeperProcessedStructures.contains(pos);
   }

   public void markEntropyKeeperStructureProcessed(BlockPos pos) {
      this.entropyKeeperProcessedStructures.add(pos);
      this.setDirty();
   }

   public boolean hasEntropyKeeperMarkerSpawned(BlockPos pos) {
      return this.entropyKeeperSpawnedMarkers.contains(pos);
   }

   public void markEntropyKeeperMarkerSpawned(BlockPos pos) {
      this.entropyKeeperSpawnedMarkers.add(pos);
      this.setDirty();
   }

   public boolean hasClockworkColossusStructureSpawned(BlockPos pos) {
      return this.clockworkColossusSpawnedStructures.contains(pos);
   }

   public void markClockworkColossusStructureSpawned(BlockPos pos) {
      this.clockworkColossusSpawnedStructures.add(pos);
      this.setDirty();
   }

   public void resetTimeTyrant() {
      this.timeTyrantSpawnedDoors.clear();
      this.timeTyrantSpawnCount = 0;
      this.setDirty();
   }

   public void resetTimeGuardian() {
      this.timeGuardianSpawnedStructures.clear();
      this.setDirty();
   }

   public void resetChronosWarden() {
      this.chronosWardenSpawnedDoors.clear();
      this.setDirty();
   }

   public void resetEntropyKeeper() {
      this.entropyKeeperProcessedStructures.clear();
      this.entropyKeeperSpawnedMarkers.clear();
      this.setDirty();
   }

   public void resetClockworkColossus() {
      this.clockworkColossusSpawnedStructures.clear();
      this.setDirty();
   }

   public void resetAll() {
      this.resetTimeTyrant();
      this.resetTimeGuardian();
      this.resetChronosWarden();
      this.resetEntropyKeeper();
      this.resetClockworkColossus();
      this.setDirty();
   }
}
