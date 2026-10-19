package com.tensurabossstructure.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class SpawnerRespawnData extends SavedData {
   private static final String DATA_NAME = "tensura_boss_spawner_respawns";
   private final List<SpawnerRespawnData.Entry> entries = new ArrayList<>();

   public void addEntry(BlockPos pos, ResourceLocation blockId, ResourceLocation dimension, int cooldownTicks, UUID bossUuid) {
      this.entries.add(new SpawnerRespawnData.Entry(pos, blockId, dimension, cooldownTicks, bossUuid));
      this.setDirty();
   }

   public List<SpawnerRespawnData.Entry> getEntries() {
      return this.entries;
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      ListTag list = new ListTag();

      for (SpawnerRespawnData.Entry entry : this.entries) {
         CompoundTag entryTag = new CompoundTag();
         entryTag.putLong("Pos", entry.pos.asLong());
         entryTag.putString("BlockId", entry.blockId.toString());
         entryTag.putString("Dimension", entry.dimension.toString());
         entryTag.putInt("CooldownTicks", entry.cooldownTicks);
         entryTag.putInt("TicksLeft", entry.ticksLeft);
         entryTag.putUUID("BossUuid", entry.bossUuid);
         entryTag.putString("Status", entry.status.name());
         list.add(entryTag);
      }

      tag.put("Entries", list);
      return tag;
   }

   public static SpawnerRespawnData load(CompoundTag tag, Provider registries) {
      SpawnerRespawnData data = new SpawnerRespawnData();
      ListTag list = tag.getList("Entries", 10);

      for (int i = 0; i < list.size(); i++) {
         CompoundTag entryTag = list.getCompound(i);

         SpawnerRespawnData.Status status;
         try {
            status = SpawnerRespawnData.Status.valueOf(entryTag.getString("Status"));
         } catch (IllegalArgumentException e) {
            status = SpawnerRespawnData.Status.COOLING_DOWN;
         }

         data.entries
            .add(
               new SpawnerRespawnData.Entry(
                  BlockPos.of(entryTag.getLong("Pos")),
                  ResourceLocation.parse(entryTag.getString("BlockId")),
                  ResourceLocation.parse(entryTag.getString("Dimension")),
                  entryTag.getInt("CooldownTicks"),
                  entryTag.getInt("TicksLeft"),
                  entryTag.getUUID("BossUuid"),
                  status
               )
            );
      }

      return data;
   }

   public static SpawnerRespawnData get(ServerLevel level) {
      return (SpawnerRespawnData)level.getServer()
         .overworld()
         .getDataStorage()
         .computeIfAbsent(new Factory(SpawnerRespawnData::new, SpawnerRespawnData::load, DataFixTypes.LEVEL), "tensura_boss_spawner_respawns");
   }

   public static class Entry {
      public BlockPos pos;
      public ResourceLocation blockId;
      public ResourceLocation dimension;
      public int cooldownTicks;
      public int ticksLeft;
      public UUID bossUuid;
      public SpawnerRespawnData.Status status;

      public Entry(BlockPos pos, ResourceLocation blockId, ResourceLocation dimension, int cooldownTicks, UUID bossUuid) {
         this.pos = pos;
         this.blockId = blockId;
         this.dimension = dimension;
         this.cooldownTicks = cooldownTicks;
         this.ticksLeft = cooldownTicks;
         this.bossUuid = bossUuid;
         this.status = SpawnerRespawnData.Status.WAITING_FOR_DEATH;
      }

      public Entry(
         BlockPos pos, ResourceLocation blockId, ResourceLocation dimension, int cooldownTicks, int ticksLeft, UUID bossUuid, SpawnerRespawnData.Status status
      ) {
         this.pos = pos;
         this.blockId = blockId;
         this.dimension = dimension;
         this.cooldownTicks = cooldownTicks;
         this.ticksLeft = ticksLeft;
         this.bossUuid = bossUuid;
         this.status = status;
      }
   }

   public enum Status {
      WAITING_FOR_DEATH,
      COOLING_DOWN;
   }
}
