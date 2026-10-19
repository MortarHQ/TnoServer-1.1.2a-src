package com.github.wal_bos.moreskills.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class BlackBloodDomainData extends SavedData {
   private static final String DATA_NAME = "tensuramoreskills_black_blood_domain";
   private final Map<Long, Integer> infectedChunks = new HashMap<>();
   private final Map<BlockPos, BlackBloodDomainData.AltarData> altars = new HashMap<>();
   private final Map<BlockPos, BlackBloodDomainData.NodeData> nodes = new LinkedHashMap<>();
   private final Map<BlockPos, LinkedHashMap<BlockPos, BlackBloodDomainData.NodeData>> nodesByAltar = new HashMap<>();
   private final Map<BlockPos, Integer> nodeCountsByAltar = new HashMap<>();
   private int changeVersion = 0;

   public static Factory<BlackBloodDomainData> factory() {
      return new Factory(BlackBloodDomainData::new, BlackBloodDomainData::load);
   }

   public static BlackBloodDomainData load(CompoundTag tag, Provider provider) {
      BlackBloodDomainData data = new BlackBloodDomainData();
      ListTag chunks = tag.getList("Chunks", 10);

      for (int i = 0; i < chunks.size(); i++) {
         CompoundTag entry = chunks.getCompound(i);
         int value = entry.getInt("Value");
         if (value > 0) {
            data.infectedChunks.put(entry.getLong("Chunk"), value);
         }
      }

      ListTag altars = tag.getList("Altars", 10);

      for (int i = 0; i < altars.size(); i++) {
         CompoundTag entry = altars.getCompound(i);
         BlockPos pos = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
         UUID owner = entry.hasUUID("Owner") ? entry.getUUID("Owner") : new UUID(0L, 0L);
         int fuelBlood = entry.contains("FuelBlood", 3) ? entry.getInt("FuelBlood") : entry.getInt("Blood");
         int totalBlood = entry.contains("TotalBlood", 3) ? entry.getInt("TotalBlood") : Math.max(fuelBlood, entry.getInt("Blood"));
         int level = entry.getInt("Level");
         int generatedBlood = entry.contains("GeneratedBlood", 3) ? entry.getInt("GeneratedBlood") : 0;
         int domainKills = entry.contains("DomainKills", 3) ? entry.getInt("DomainKills") : 0;
         data.altars
            .put(
               pos.immutable(),
               new BlackBloodDomainData.AltarData(
                  owner, Math.max(0, fuelBlood), Math.max(0, totalBlood), Math.max(0, level), Math.max(0, generatedBlood), Math.max(0, domainKills)
               )
            );
      }

      ListTag nodes = tag.getList("Nodes", 10);

      for (int i = 0; i < nodes.size(); i++) {
         CompoundTag entry = nodes.getCompound(i);
         BlockPos pos = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
         BlockPos altar = new BlockPos(entry.getInt("AltarX"), entry.getInt("AltarY"), entry.getInt("AltarZ"));
         UUID owner = entry.hasUUID("Owner") ? entry.getUUID("Owner") : new UUID(0L, 0L);
         int generation = Math.max(1, entry.getInt("Generation"));
         BlockPos immutableAltar = altar.immutable();
         BlockPos immutablePos = pos.immutable();
         BlackBloodDomainData.NodeData node = new BlackBloodDomainData.NodeData(owner, immutableAltar, generation);
         data.nodes.put(immutablePos, node);
         data.addNodeIndex(immutablePos, node);
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag chunks = new ListTag();

      for (Entry<Long, Integer> entry : this.infectedChunks.entrySet()) {
         int value = entry.getValue();
         if (value > 0) {
            CompoundTag chunk = new CompoundTag();
            chunk.putLong("Chunk", entry.getKey());
            chunk.putInt("Value", value);
            chunks.add(chunk);
         }
      }

      tag.put("Chunks", chunks);
      ListTag altarList = new ListTag();

      for (Entry<BlockPos, BlackBloodDomainData.AltarData> entry : this.altars.entrySet()) {
         BlockPos pos = entry.getKey();
         BlackBloodDomainData.AltarData altar = entry.getValue();
         CompoundTag altarTag = new CompoundTag();
         altarTag.putInt("X", pos.getX());
         altarTag.putInt("Y", pos.getY());
         altarTag.putInt("Z", pos.getZ());
         altarTag.putUUID("Owner", altar.owner());
         altarTag.putInt("Blood", altar.fuelBlood());
         altarTag.putInt("FuelBlood", altar.fuelBlood());
         altarTag.putInt("TotalBlood", altar.totalBlood());
         altarTag.putInt("Level", altar.level());
         altarTag.putInt("GeneratedBlood", altar.generatedBlood());
         altarTag.putInt("DomainKills", altar.domainKills());
         altarList.add(altarTag);
      }

      tag.put("Altars", altarList);
      ListTag nodeList = new ListTag();

      for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : this.nodes.entrySet()) {
         BlockPos pos = entry.getKey();
         BlackBloodDomainData.NodeData node = entry.getValue();
         BlockPos altar = node.altarPos();
         CompoundTag nodeTag = new CompoundTag();
         nodeTag.putInt("X", pos.getX());
         nodeTag.putInt("Y", pos.getY());
         nodeTag.putInt("Z", pos.getZ());
         nodeTag.putUUID("Owner", node.owner());
         nodeTag.putInt("AltarX", altar.getX());
         nodeTag.putInt("AltarY", altar.getY());
         nodeTag.putInt("AltarZ", altar.getZ());
         nodeTag.putInt("Generation", node.generation());
         nodeList.add(nodeTag);
      }

      tag.put("Nodes", nodeList);
      return tag;
   }

   public static BlackBloodDomainData get(ServerLevel level) {
      return (BlackBloodDomainData)level.getDataStorage().computeIfAbsent(factory(), "tensuramoreskills_black_blood_domain");
   }

   public int getChangeVersion() {
      return this.changeVersion;
   }

   private void markChanged() {
      this.changeVersion++;
      this.setDirty();
   }

   private void addNodeIndex(BlockPos pos, BlackBloodDomainData.NodeData node) {
      BlockPos altarPos = node.altarPos();
      this.nodeCountsByAltar.put(altarPos, this.nodeCountsByAltar.getOrDefault(altarPos, 0) + 1);
      this.nodesByAltar.computeIfAbsent(altarPos, key -> new LinkedHashMap<>()).put(pos, node);
   }

   private void removeNodeIndex(BlockPos pos, BlackBloodDomainData.NodeData node) {
      BlockPos altarPos = node.altarPos();
      int count = this.nodeCountsByAltar.getOrDefault(altarPos, 0);
      if (count <= 1) {
         this.nodeCountsByAltar.remove(altarPos);
      } else {
         this.nodeCountsByAltar.put(altarPos, count - 1);
      }

      LinkedHashMap<BlockPos, BlackBloodDomainData.NodeData> byAltar = this.nodesByAltar.get(altarPos);
      if (byAltar != null) {
         byAltar.remove(pos);
         if (byAltar.isEmpty()) {
            this.nodesByAltar.remove(altarPos);
         }
      }
   }

   public int getChunkValue(BlockPos pos) {
      return this.infectedChunks.getOrDefault(ChunkPos.asLong(pos), 0);
   }

   public boolean isDomainChunk(BlockPos pos, int required) {
      return this.getChunkValue(pos) >= required;
   }

   public void addChunkValue(BlockPos pos, int amount, int max) {
      if (amount > 0) {
         long key = ChunkPos.asLong(pos);
         int oldValue = this.infectedChunks.getOrDefault(key, 0);
         int newValue = Math.min(max, oldValue + amount);
         if (newValue != oldValue) {
            this.infectedChunks.put(key, newValue);
            this.markChanged();
         }
      }
   }

   public void subtractChunkValue(BlockPos pos, int amount) {
      if (amount > 0) {
         long key = ChunkPos.asLong(pos);
         int oldValue = this.infectedChunks.getOrDefault(key, 0);
         if (oldValue > 0) {
            int newValue = Math.max(0, oldValue - amount);
            if (newValue <= 0) {
               this.infectedChunks.remove(key);
            } else {
               this.infectedChunks.put(key, newValue);
            }

            this.markChanged();
         }
      }
   }

   public boolean hasAnyAltar() {
      return !this.altars.isEmpty();
   }

   public boolean hasAltarNear(BlockPos pos, int radius) {
      int radiusSqr = radius * radius;

      for (BlockPos altar : this.altars.keySet()) {
         if (altar.distSqr(pos) <= radiusSqr) {
            return true;
         }
      }

      return false;
   }

   public boolean addAltar(BlockPos pos, UUID owner) {
      if (this.altars.containsKey(pos)) {
         return false;
      }

      this.altars.put(pos.immutable(), new BlackBloodDomainData.AltarData(owner, 0, 0, 0, 0, 0));
      this.markChanged();
      return true;
   }

   public BlackBloodDomainData.AltarData getAltar(BlockPos pos) {
      return this.altars.get(pos);
   }

   public boolean setAltar(BlockPos pos, BlackBloodDomainData.AltarData altar) {
      if (!this.altars.containsKey(pos)) {
         return false;
      }

      BlackBloodDomainData.AltarData old = this.altars.get(pos);
      if (Objects.equals(old, altar)) {
         return true;
      }

      this.altars.put(pos.immutable(), altar);
      this.markChanged();
      return true;
   }

   public Iterable<Entry<BlockPos, BlackBloodDomainData.AltarData>> altars() {
      return this.altars.entrySet();
   }

   public boolean addNode(BlockPos pos, UUID owner, BlockPos altarPos, int generation) {
      if (this.nodes.containsKey(pos)) {
         return false;
      }

      BlockPos immutableAltar = altarPos.immutable();
      BlockPos immutablePos = pos.immutable();
      BlackBloodDomainData.NodeData node = new BlackBloodDomainData.NodeData(owner, immutableAltar, Math.max(1, generation));
      this.nodes.put(immutablePos, node);
      this.addNodeIndex(immutablePos, node);
      this.markChanged();
      return true;
   }

   public boolean hasNodeNear(BlockPos pos, int radius) {
      int radiusSqr = radius * radius;

      for (BlockPos node : this.nodes.keySet()) {
         if (node.distSqr(pos) <= radiusSqr) {
            return true;
         }
      }

      return false;
   }

   public BlackBloodDomainData.NodeData getNode(BlockPos pos) {
      return this.nodes.get(pos);
   }

   public boolean removeNode(BlockPos pos) {
      BlackBloodDomainData.NodeData removed = this.nodes.remove(pos);
      if (removed != null) {
         this.removeNodeIndex(pos, removed);
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   public Iterable<Entry<BlockPos, BlackBloodDomainData.NodeData>> nodes() {
      return this.nodes.entrySet();
   }

   public int countNodesForAltar(BlockPos altarPos) {
      return this.nodeCountsByAltar.getOrDefault(altarPos, 0);
   }

   public List<Entry<BlockPos, BlackBloodDomainData.NodeData>> nextNodesForAltar(BlockPos altarPos, int maxEntries) {
      LinkedHashMap<BlockPos, BlackBloodDomainData.NodeData> byAltar = this.nodesByAltar.get(altarPos);
      List<Entry<BlockPos, BlackBloodDomainData.NodeData>> result = new ArrayList<>();
      if (byAltar != null && !byAltar.isEmpty() && maxEntries > 0) {
         int count = Math.min(maxEntries, byAltar.size());
         List<Entry<BlockPos, BlackBloodDomainData.NodeData>> rotate = new ArrayList<>(count);
         Iterator<Entry<BlockPos, BlackBloodDomainData.NodeData>> iterator = byAltar.entrySet().iterator();

         while (iterator.hasNext() && rotate.size() < count) {
            Entry<BlockPos, BlackBloodDomainData.NodeData> entry = iterator.next();
            rotate.add(new SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
            iterator.remove();
         }

         for (Entry<BlockPos, BlackBloodDomainData.NodeData> entry : rotate) {
            if (this.nodes.get(entry.getKey()) == entry.getValue() || this.nodes.containsKey(entry.getKey())) {
               byAltar.put(entry.getKey(), entry.getValue());
               result.add(entry);
            }
         }

         return result;
      } else {
         return result;
      }
   }

   public Iterable<Entry<BlockPos, BlackBloodDomainData.NodeData>> nodesForAltar(BlockPos altarPos) {
      LinkedHashMap<BlockPos, BlackBloodDomainData.NodeData> byAltar = this.nodesByAltar.get(altarPos);
      return byAltar == null ? List.of() : byAltar.entrySet();
   }

   public void removeMissingAltars(ServerLevel level, Block altarBlock) {
      Iterator<Entry<BlockPos, BlackBloodDomainData.AltarData>> iterator = this.altars.entrySet().iterator();
      boolean changed = false;

      while (iterator.hasNext()) {
         Entry<BlockPos, BlackBloodDomainData.AltarData> entry = iterator.next();
         if (level.isLoaded(entry.getKey()) && !level.getBlockState(entry.getKey()).is(altarBlock)) {
            iterator.remove();
            changed = true;
         }
      }

      if (changed) {
         this.markChanged();
      }
   }

   public void removeNodesForMissingAltars() {
      Iterator<Entry<BlockPos, BlackBloodDomainData.NodeData>> iterator = this.nodes.entrySet().iterator();
      boolean changed = false;

      while (iterator.hasNext()) {
         Entry<BlockPos, BlackBloodDomainData.NodeData> entry = iterator.next();
         if (!this.altars.containsKey(entry.getValue().altarPos())) {
            this.removeNodeIndex(entry.getKey(), entry.getValue());
            iterator.remove();
            changed = true;
         }
      }

      if (changed) {
         this.markChanged();
      }
   }

   private static int safeAdd(int current, int add) {
      if (add <= 0) {
         return current;
      } else {
         return current >= Integer.MAX_VALUE - add ? Integer.MAX_VALUE : current + add;
      }
   }

   public record AltarData(UUID owner, int fuelBlood, int totalBlood, int level, int generatedBlood, int domainKills) {
      public AltarData(UUID owner, int fuelBlood, int totalBlood, int level) {
         this(owner, fuelBlood, totalBlood, level, 0, 0);
      }

      public BlackBloodDomainData.AltarData withFuelBlood(int fuelBlood) {
         return new BlackBloodDomainData.AltarData(this.owner, Math.max(0, fuelBlood), this.totalBlood, this.level, this.generatedBlood, this.domainKills);
      }

      public BlackBloodDomainData.AltarData withGeneratedBlood(int amount, int maxFuelBlood, int newLevel) {
         int safeAmount = Math.max(0, amount);
         int nextFuel = Math.min(Math.max(1, maxFuelBlood), BlackBloodDomainData.safeAdd(this.fuelBlood, safeAmount));
         int nextTotal = BlackBloodDomainData.safeAdd(this.totalBlood, safeAmount);
         int nextGenerated = BlackBloodDomainData.safeAdd(this.generatedBlood, safeAmount);
         int nextKills = BlackBloodDomainData.safeAdd(this.domainKills, 1);
         return new BlackBloodDomainData.AltarData(
            this.owner, Math.max(0, nextFuel), Math.max(0, nextTotal), Math.max(this.level, newLevel), Math.max(0, nextGenerated), Math.max(0, nextKills)
         );
      }
   }

   public record NodeData(UUID owner, BlockPos altarPos, int generation) {
   }
}
