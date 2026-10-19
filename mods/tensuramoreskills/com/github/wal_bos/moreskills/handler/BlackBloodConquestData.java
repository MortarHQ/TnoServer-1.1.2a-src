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
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class BlackBloodConquestData extends SavedData {
   private static final String DATA_NAME = "tensuramoreskills_black_blood_conquest";
   private final Map<Long, BlackBloodConquestData.ChunkMemory> chunkMemory = new HashMap<>();
   private final Map<UUID, BlackBloodConquestData.PlayerMemory> playerMemory = new HashMap<>();
   private final Map<BlockPos, BlackBloodConquestData.RootData> roots = new LinkedHashMap<>();
   private final Map<Long, Integer> rootTargets = new HashMap<>();
   private final Map<Long, BlackBloodConquestData.PendingRootLaunch> pendingRootLaunches = new LinkedHashMap<>();
   private final Map<BlockPos, BlackBloodConquestData.BloomData> blooms = new LinkedHashMap<>();
   private BlockPos worldHeartPos;
   private int worldStage;
   private int completedRoots;
   private int createdBlooms;
   private int baseBreaches;
   private int conqueredChunks;
   private int changeVersion;

   public static Factory<BlackBloodConquestData> factory() {
      return new Factory(BlackBloodConquestData::new, BlackBloodConquestData::load);
   }

   public static BlackBloodConquestData get(ServerLevel level) {
      return (BlackBloodConquestData)level.getDataStorage().computeIfAbsent(factory(), "tensuramoreskills_black_blood_conquest");
   }

   public static BlackBloodConquestData load(CompoundTag tag, Provider provider) {
      BlackBloodConquestData data = new BlackBloodConquestData();
      data.worldStage = Math.max(0, tag.getInt("WorldStage"));
      data.completedRoots = Math.max(0, tag.getInt("CompletedRoots"));
      data.createdBlooms = Math.max(0, tag.getInt("CreatedBlooms"));
      data.baseBreaches = Math.max(0, tag.getInt("BaseBreaches"));
      data.conqueredChunks = Math.max(0, tag.getInt("ConqueredChunks"));
      if (tag.contains("WorldHeart", 10)) {
         CompoundTag heart = tag.getCompound("WorldHeart");
         data.worldHeartPos = new BlockPos(heart.getInt("X"), heart.getInt("Y"), heart.getInt("Z"));
      }

      ListTag chunks = tag.getList("ChunkMemory", 10);

      for (int i = 0; i < chunks.size(); i++) {
         CompoundTag entry = chunks.getCompound(i);
         long key = entry.getLong("Chunk");
         data.chunkMemory
            .put(
               key,
               new BlackBloodConquestData.ChunkMemory(
                  Math.max(0, entry.getInt("PlayerHeat")),
                  Math.max(0, entry.getInt("BaseHeat")),
                  Math.max(0, entry.getInt("InfectionPressure")),
                  Math.max(0L, entry.getLong("LastSeen")),
                  Math.max(0, entry.getInt("Breaches"))
               )
            );
      }

      ListTag players = tag.getList("PlayerMemory", 10);

      for (int i = 0; i < players.size(); i++) {
         CompoundTag entry = players.getCompound(i);
         if (entry.hasUUID("Player")) {
            data.playerMemory
               .put(
                  entry.getUUID("Player"),
                  new BlackBloodConquestData.PlayerMemory(
                     entry.getLong("FavoriteChunk"),
                     Math.max(0, entry.getInt("FavoriteScore")),
                     Math.max(0, entry.getInt("TotalSamples")),
                     Math.max(0L, entry.getLong("LastSeen"))
                  )
               );
         }
      }

      ListTag roots = tag.getList("Roots", 10);

      for (int i = 0; i < roots.size(); i++) {
         CompoundTag entry = roots.getCompound(i);
         BlockPos pos = readPos(entry, "X", "Y", "Z");
         BlockPos altar = readPos(entry, "AltarX", "AltarY", "AltarZ");
         BlackBloodConquestData.RootData root = new BlackBloodConquestData.RootData(
            altar.immutable(),
            entry.getLong("TargetChunk"),
            Math.max(0, entry.getInt("Age")),
            Math.max(0, entry.getInt("Progress")),
            entry.getBoolean("PlayerBaseTarget"),
            entry.contains("BloomOnArrival", 1) ? entry.getBoolean("BloomOnArrival") : true
         );
         data.roots.put(pos.immutable(), root);
         data.addRootTarget(root.targetChunk());
      }

      ListTag pendingRoots = tag.getList("PendingRootLaunches", 10);

      for (int i = 0; i < pendingRoots.size(); i++) {
         CompoundTag entry = pendingRoots.getCompound(i);
         BlockPos altar = readPos(entry, "AltarX", "AltarY", "AltarZ");
         long targetChunk = entry.getLong("TargetChunk");
         BlackBloodConquestData.PendingRootLaunch pending = new BlackBloodConquestData.PendingRootLaunch(
            altar.immutable(),
            targetChunk,
            Math.max(0, entry.getInt("Age")),
            entry.getBoolean("PlayerBaseTarget"),
            entry.contains("BloomOnArrival", 1) ? entry.getBoolean("BloomOnArrival") : true
         );
         data.pendingRootLaunches.put(targetChunk, pending);
         data.addRootTarget(targetChunk);
      }

      ListTag blooms = tag.getList("Blooms", 10);

      for (int i = 0; i < blooms.size(); i++) {
         CompoundTag entry = blooms.getCompound(i);
         BlockPos pos = readPos(entry, "X", "Y", "Z");
         BlockPos altar = readPos(entry, "AltarX", "AltarY", "AltarZ");
         data.blooms
            .put(
               pos.immutable(),
               new BlackBloodConquestData.BloomData(
                  altar.immutable(),
                  Math.max(0, entry.getInt("StoredBlood")),
                  Math.max(0, entry.getInt("Age")),
                  Math.max(0, entry.getInt("Tier")),
                  entry.getBoolean("FromPlayerBase")
               )
            );
      }

      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      tag.putInt("WorldStage", this.worldStage);
      tag.putInt("CompletedRoots", this.completedRoots);
      tag.putInt("CreatedBlooms", this.createdBlooms);
      tag.putInt("BaseBreaches", this.baseBreaches);
      tag.putInt("ConqueredChunks", this.conqueredChunks);
      if (this.worldHeartPos != null) {
         CompoundTag heart = new CompoundTag();
         heart.putInt("X", this.worldHeartPos.getX());
         heart.putInt("Y", this.worldHeartPos.getY());
         heart.putInt("Z", this.worldHeartPos.getZ());
         tag.put("WorldHeart", heart);
      }

      ListTag chunks = new ListTag();

      for (Entry<Long, BlackBloodConquestData.ChunkMemory> entry : this.chunkMemory.entrySet()) {
         BlackBloodConquestData.ChunkMemory memory = entry.getValue();
         if (memory.playerHeat() > 0 || memory.baseHeat() > 0 || memory.infectionPressure() > 0) {
            CompoundTag chunk = new CompoundTag();
            chunk.putLong("Chunk", entry.getKey());
            chunk.putInt("PlayerHeat", memory.playerHeat());
            chunk.putInt("BaseHeat", memory.baseHeat());
            chunk.putInt("InfectionPressure", memory.infectionPressure());
            chunk.putLong("LastSeen", memory.lastSeen());
            chunk.putInt("Breaches", memory.breaches());
            chunks.add(chunk);
         }
      }

      tag.put("ChunkMemory", chunks);
      ListTag players = new ListTag();

      for (Entry<UUID, BlackBloodConquestData.PlayerMemory> entry : this.playerMemory.entrySet()) {
         BlackBloodConquestData.PlayerMemory memory = entry.getValue();
         CompoundTag player = new CompoundTag();
         player.putUUID("Player", entry.getKey());
         player.putLong("FavoriteChunk", memory.favoriteChunk());
         player.putInt("FavoriteScore", memory.favoriteScore());
         player.putInt("TotalSamples", memory.totalSamples());
         player.putLong("LastSeen", memory.lastSeen());
         players.add(player);
      }

      tag.put("PlayerMemory", players);
      ListTag roots = new ListTag();

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : this.roots.entrySet()) {
         BlockPos pos = entry.getKey();
         BlackBloodConquestData.RootData root = entry.getValue();
         CompoundTag rootTag = new CompoundTag();
         rootTag.putInt("X", pos.getX());
         rootTag.putInt("Y", pos.getY());
         rootTag.putInt("Z", pos.getZ());
         rootTag.putInt("AltarX", root.altarPos().getX());
         rootTag.putInt("AltarY", root.altarPos().getY());
         rootTag.putInt("AltarZ", root.altarPos().getZ());
         rootTag.putLong("TargetChunk", root.targetChunk());
         rootTag.putInt("Age", root.age());
         rootTag.putInt("Progress", root.progress());
         rootTag.putBoolean("PlayerBaseTarget", root.playerBaseTarget());
         rootTag.putBoolean("BloomOnArrival", root.bloomOnArrival());
         roots.add(rootTag);
      }

      tag.put("Roots", roots);
      ListTag pendingRoots = new ListTag();

      for (Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry : this.pendingRootLaunches.entrySet()) {
         BlackBloodConquestData.PendingRootLaunch pending = entry.getValue();
         CompoundTag pendingTag = new CompoundTag();
         pendingTag.putInt("AltarX", pending.altarPos().getX());
         pendingTag.putInt("AltarY", pending.altarPos().getY());
         pendingTag.putInt("AltarZ", pending.altarPos().getZ());
         pendingTag.putLong("TargetChunk", pending.targetChunk());
         pendingTag.putInt("Age", pending.age());
         pendingTag.putBoolean("PlayerBaseTarget", pending.playerBaseTarget());
         pendingTag.putBoolean("BloomOnArrival", pending.bloomOnArrival());
         pendingRoots.add(pendingTag);
      }

      tag.put("PendingRootLaunches", pendingRoots);
      ListTag blooms = new ListTag();

      for (Entry<BlockPos, BlackBloodConquestData.BloomData> entry : this.blooms.entrySet()) {
         BlockPos pos = entry.getKey();
         BlackBloodConquestData.BloomData bloom = entry.getValue();
         CompoundTag bloomTag = new CompoundTag();
         bloomTag.putInt("X", pos.getX());
         bloomTag.putInt("Y", pos.getY());
         bloomTag.putInt("Z", pos.getZ());
         bloomTag.putInt("AltarX", bloom.mainAltarPos().getX());
         bloomTag.putInt("AltarY", bloom.mainAltarPos().getY());
         bloomTag.putInt("AltarZ", bloom.mainAltarPos().getZ());
         bloomTag.putInt("StoredBlood", bloom.storedBlood());
         bloomTag.putInt("Age", bloom.age());
         bloomTag.putInt("Tier", bloom.tier());
         bloomTag.putBoolean("FromPlayerBase", bloom.fromPlayerBase());
         blooms.add(bloomTag);
      }

      tag.put("Blooms", blooms);
      return tag;
   }

   private static BlockPos readPos(CompoundTag tag, String x, String y, String z) {
      return new BlockPos(tag.getInt(x), tag.getInt(y), tag.getInt(z));
   }

   private void markChanged() {
      this.changeVersion++;
      this.setDirty();
   }

   public int getChangeVersion() {
      return this.changeVersion;
   }

   public int worldStage() {
      return this.worldStage;
   }

   public void setWorldStage(int worldStage) {
      int value = Math.max(0, worldStage);
      if (this.worldStage != value) {
         this.worldStage = value;
         this.markChanged();
      }
   }

   public BlockPos worldHeartPos() {
      return this.worldHeartPos;
   }

   public void setWorldHeartPos(BlockPos pos) {
      BlockPos value = pos == null ? null : pos.immutable();
      if (!Objects.equals(this.worldHeartPos, value)) {
         this.worldHeartPos = value;
         this.markChanged();
      }
   }

   public int completedRoots() {
      return this.completedRoots;
   }

   public int createdBlooms() {
      return this.createdBlooms;
   }

   public int baseBreaches() {
      return this.baseBreaches;
   }

   public int conqueredChunks() {
      return this.conqueredChunks;
   }

   public void incrementCompletedRoots(boolean baseTarget) {
      this.completedRoots = safeAdd(this.completedRoots, 1);
      if (baseTarget) {
         this.baseBreaches = safeAdd(this.baseBreaches, 1);
      }

      this.markChanged();
   }

   public void incrementCreatedBlooms() {
      this.createdBlooms = safeAdd(this.createdBlooms, 1);
      this.markChanged();
   }

   public void recordCommandTarget(BlockPos pos, int playerHeatAmount, int baseHeatAmount, long gameTime) {
      long chunk = ChunkPos.asLong(pos);
      BlackBloodConquestData.ChunkMemory memory = this.chunkMemory.getOrDefault(chunk, BlackBloodConquestData.ChunkMemory.EMPTY);
      int playerHeat = Math.min(2000000000, memory.playerHeat() + Math.max(0, playerHeatAmount));
      int baseHeat = Math.min(2000000000, memory.baseHeat() + Math.max(0, baseHeatAmount));
      this.chunkMemory.put(chunk, new BlackBloodConquestData.ChunkMemory(playerHeat, baseHeat, memory.infectionPressure(), gameTime, memory.breaches()));
      this.markChanged();
   }

   public int getChunkBaseHeat(BlockPos pos) {
      return this.chunkMemory.getOrDefault(ChunkPos.asLong(pos), BlackBloodConquestData.ChunkMemory.EMPTY).baseHeat();
   }

   public int getChunkInfectionPressure(BlockPos pos) {
      return this.chunkMemory.getOrDefault(ChunkPos.asLong(pos), BlackBloodConquestData.ChunkMemory.EMPTY).infectionPressure();
   }

   public boolean hasConquestAnchorNear(BlockPos center, int radiusBlocks) {
      int radiusSqr = Math.max(1, radiusBlocks) * Math.max(1, radiusBlocks);
      if (this.worldHeartPos != null && this.worldHeartPos.distSqr(center) <= radiusSqr) {
         return true;
      }

      for (BlockPos pos : this.roots.keySet()) {
         if (pos.distSqr(center) <= radiusSqr) {
            return true;
         }
      }

      for (BlockPos pos : this.blooms.keySet()) {
         if (pos.distSqr(center) <= radiusSqr) {
            return true;
         }
      }

      return false;
   }

   public void recordPlayerSample(UUID player, BlockPos pos, int baseScore, long gameTime) {
      long chunk = ChunkPos.asLong(pos);
      BlackBloodConquestData.ChunkMemory memory = this.chunkMemory.getOrDefault(chunk, BlackBloodConquestData.ChunkMemory.EMPTY);
      int playerHeat = Math.min(2000000000, memory.playerHeat() + 1);
      int baseHeat = Math.min(2000000000, memory.baseHeat() + Math.max(0, baseScore));
      this.chunkMemory.put(chunk, new BlackBloodConquestData.ChunkMemory(playerHeat, baseHeat, memory.infectionPressure(), gameTime, memory.breaches()));
      BlackBloodConquestData.PlayerMemory old = this.playerMemory.getOrDefault(player, BlackBloodConquestData.PlayerMemory.EMPTY);
      int score = playerHeat + baseHeat * 3;
      if (score >= old.favoriteScore()) {
         this.playerMemory.put(player, new BlackBloodConquestData.PlayerMemory(chunk, score, safeAdd(old.totalSamples(), 1), gameTime));
      } else {
         this.playerMemory
            .put(player, new BlackBloodConquestData.PlayerMemory(old.favoriteChunk(), old.favoriteScore(), safeAdd(old.totalSamples(), 1), gameTime));
      }

      this.markChanged();
   }

   public void addInfectionPressure(BlockPos pos, int amount, int conqueredThreshold) {
      if (amount > 0) {
         long chunk = ChunkPos.asLong(pos);
         BlackBloodConquestData.ChunkMemory memory = this.chunkMemory.getOrDefault(chunk, BlackBloodConquestData.ChunkMemory.EMPTY);
         int oldPressure = memory.infectionPressure();
         int newPressure = Math.min(2000000000, oldPressure + amount);
         int oldConquered = oldPressure >= conqueredThreshold ? 1 : 0;
         int newConquered = newPressure >= conqueredThreshold ? 1 : 0;
         this.chunkMemory
            .put(chunk, new BlackBloodConquestData.ChunkMemory(memory.playerHeat(), memory.baseHeat(), newPressure, memory.lastSeen(), memory.breaches()));
         if (newConquered > oldConquered) {
            this.conqueredChunks = safeAdd(this.conqueredChunks, 1);
         }

         this.markChanged();
      }
   }

   public BlackBloodConquestData.TargetChunk findBestPlayerBaseTarget(long gameTime, int minPlayerHeat, int minBaseHeat) {
      long bestChunk = Long.MIN_VALUE;
      int bestScore = Integer.MIN_VALUE;
      boolean bestBase = false;

      for (Entry<Long, BlackBloodConquestData.ChunkMemory> entry : this.chunkMemory.entrySet()) {
         BlackBloodConquestData.ChunkMemory memory = entry.getValue();
         if (memory.playerHeat() >= minPlayerHeat || memory.baseHeat() >= minBaseHeat) {
            int agePenalty = (int)Math.min(50000L, Math.max(0L, gameTime - memory.lastSeen()) / 20L);
            int score = memory.playerHeat() + memory.baseHeat() * 8 + memory.infectionPressure() / 2 - memory.breaches() * 25000 - agePenalty;
            if (score > bestScore) {
               bestScore = score;
               bestChunk = entry.getKey();
               bestBase = memory.baseHeat() >= minBaseHeat;
            }
         }
      }

      return bestChunk == Long.MIN_VALUE ? null : new BlackBloodConquestData.TargetChunk(bestChunk, bestScore, bestBase);
   }

   public void markChunkBreached(long chunk) {
      BlackBloodConquestData.ChunkMemory memory = this.chunkMemory.getOrDefault(chunk, BlackBloodConquestData.ChunkMemory.EMPTY);
      this.chunkMemory
         .put(
            chunk,
            new BlackBloodConquestData.ChunkMemory(
               memory.playerHeat(), memory.baseHeat(), memory.infectionPressure(), memory.lastSeen(), safeAdd(memory.breaches(), 1)
            )
         );
      this.markChanged();
   }

   public boolean addPendingRootLaunch(BlockPos altarPos, long targetChunk, boolean playerBaseTarget, boolean bloomOnArrival) {
      if (this.rootTargets.getOrDefault(targetChunk, 0) <= 0 && !this.pendingRootLaunches.containsKey(targetChunk)) {
         this.pendingRootLaunches
            .put(targetChunk, new BlackBloodConquestData.PendingRootLaunch(altarPos.immutable(), targetChunk, 0, playerBaseTarget, bloomOnArrival));
         this.addRootTarget(targetChunk);
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   public BlackBloodConquestData.PendingRootLaunch getPendingRootLaunch(long targetChunk) {
      return this.pendingRootLaunches.get(targetChunk);
   }

   public boolean removePendingRootLaunch(long targetChunk) {
      BlackBloodConquestData.PendingRootLaunch removed = this.pendingRootLaunches.remove(targetChunk);
      if (removed != null) {
         this.removeRootTarget(removed.targetChunk());
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   public int pendingRootLaunchCount() {
      return this.pendingRootLaunches.size();
   }

   public int totalRootWorkCount() {
      return this.roots.size() + this.pendingRootLaunches.size();
   }

   public Iterable<Entry<Long, BlackBloodConquestData.PendingRootLaunch>> pendingRootLaunches() {
      return this.pendingRootLaunches.entrySet();
   }

   public List<Entry<Long, BlackBloodConquestData.PendingRootLaunch>> nextPendingRootLaunches(int maxEntries) {
      List<Entry<Long, BlackBloodConquestData.PendingRootLaunch>> result = new ArrayList<>();
      if (!this.pendingRootLaunches.isEmpty() && maxEntries > 0) {
         int count = Math.min(maxEntries, this.pendingRootLaunches.size());
         List<Entry<Long, BlackBloodConquestData.PendingRootLaunch>> rotate = new ArrayList<>(count);
         Iterator<Entry<Long, BlackBloodConquestData.PendingRootLaunch>> iterator = this.pendingRootLaunches.entrySet().iterator();

         while (iterator.hasNext() && rotate.size() < count) {
            Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry = iterator.next();
            rotate.add(new SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
            iterator.remove();
         }

         for (Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry : rotate) {
            BlackBloodConquestData.PendingRootLaunch pending = entry.getValue().tick();
            this.pendingRootLaunches.put(entry.getKey(), pending);
            result.add(new SimpleImmutableEntry<>(entry.getKey(), pending));
         }

         this.markChanged();
         return result;
      } else {
         return result;
      }
   }

   public boolean addRoot(BlockPos pos, BlockPos altarPos, long targetChunk, boolean playerBaseTarget) {
      return this.addRoot(pos, altarPos, targetChunk, playerBaseTarget, true);
   }

   public boolean addRoot(BlockPos pos, BlockPos altarPos, long targetChunk, boolean playerBaseTarget, boolean bloomOnArrival) {
      if (this.roots.containsKey(pos)) {
         return false;
      }

      BlackBloodConquestData.RootData root = new BlackBloodConquestData.RootData(altarPos.immutable(), targetChunk, 0, 0, playerBaseTarget, bloomOnArrival);
      this.roots.put(pos.immutable(), root);
      this.addRootTarget(targetChunk);
      this.markChanged();
      return true;
   }

   public BlackBloodConquestData.RootData getRoot(BlockPos pos) {
      return this.roots.get(pos);
   }

   public void setRoot(BlockPos oldPos, BlockPos newPos, BlackBloodConquestData.RootData root) {
      BlackBloodConquestData.RootData old = this.roots.remove(oldPos);
      if (old != null && old.targetChunk() != root.targetChunk()) {
         this.removeRootTarget(old.targetChunk());
         this.addRootTarget(root.targetChunk());
      }

      this.roots.put(newPos.immutable(), root);
      this.markChanged();
   }

   public boolean removeRoot(BlockPos pos) {
      BlackBloodConquestData.RootData removed = this.roots.remove(pos);
      if (removed != null) {
         this.removeRootTarget(removed.targetChunk());
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   public Iterable<Entry<BlockPos, BlackBloodConquestData.RootData>> roots() {
      return this.roots.entrySet();
   }

   public int rootCount() {
      return this.roots.size();
   }

   public boolean hasRootTargeting(long targetChunk) {
      return this.rootTargets.getOrDefault(targetChunk, 0) > 0;
   }

   private void addRootTarget(long targetChunk) {
      this.rootTargets.put(targetChunk, this.rootTargets.getOrDefault(targetChunk, 0) + 1);
   }

   private void removeRootTarget(long targetChunk) {
      int count = this.rootTargets.getOrDefault(targetChunk, 0);
      if (count <= 1) {
         this.rootTargets.remove(targetChunk);
      } else {
         this.rootTargets.put(targetChunk, count - 1);
      }
   }

   public boolean addBloom(BlockPos pos, BlockPos mainAltarPos, boolean fromPlayerBase, int storedBlood) {
      if (this.blooms.containsKey(pos)) {
         return false;
      }

      this.blooms
         .put(
            pos.immutable(),
            new BlackBloodConquestData.BloomData(mainAltarPos.immutable(), Math.max(0, storedBlood), 0, fromPlayerBase ? 2 : 1, fromPlayerBase)
         );
      this.incrementCreatedBlooms();
      return true;
   }

   public BlackBloodConquestData.BloomData getBloom(BlockPos pos) {
      return this.blooms.get(pos);
   }

   public void setBloom(BlockPos pos, BlackBloodConquestData.BloomData bloom) {
      BlackBloodConquestData.BloomData old = this.blooms.get(pos);
      if (!Objects.equals(old, bloom)) {
         this.blooms.put(pos.immutable(), bloom);
         this.markChanged();
      }
   }

   public boolean removeBloom(BlockPos pos) {
      if (this.blooms.remove(pos) != null) {
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   public Iterable<Entry<BlockPos, BlackBloodConquestData.BloomData>> blooms() {
      return this.blooms.entrySet();
   }

   public int bloomCount() {
      return this.blooms.size();
   }

   public List<Entry<BlockPos, BlackBloodConquestData.BloomData>> nextBlooms(int maxEntries) {
      List<Entry<BlockPos, BlackBloodConquestData.BloomData>> result = new ArrayList<>();
      if (!this.blooms.isEmpty() && maxEntries > 0) {
         int count = Math.min(maxEntries, this.blooms.size());
         List<Entry<BlockPos, BlackBloodConquestData.BloomData>> rotate = new ArrayList<>(count);
         Iterator<Entry<BlockPos, BlackBloodConquestData.BloomData>> iterator = this.blooms.entrySet().iterator();

         while (iterator.hasNext() && rotate.size() < count) {
            Entry<BlockPos, BlackBloodConquestData.BloomData> entry = iterator.next();
            rotate.add(new SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
            iterator.remove();
         }

         for (Entry<BlockPos, BlackBloodConquestData.BloomData> entry : rotate) {
            this.blooms.put(entry.getKey(), entry.getValue());
            result.add(entry);
         }

         return result;
      } else {
         return result;
      }
   }

   public List<Entry<BlockPos, BlackBloodConquestData.RootData>> nextRoots(int maxEntries) {
      List<Entry<BlockPos, BlackBloodConquestData.RootData>> result = new ArrayList<>();
      if (!this.roots.isEmpty() && maxEntries > 0) {
         int count = Math.min(maxEntries, this.roots.size());
         List<Entry<BlockPos, BlackBloodConquestData.RootData>> rotate = new ArrayList<>(count);
         Iterator<Entry<BlockPos, BlackBloodConquestData.RootData>> iterator = this.roots.entrySet().iterator();

         while (iterator.hasNext() && rotate.size() < count) {
            Entry<BlockPos, BlackBloodConquestData.RootData> entry = iterator.next();
            rotate.add(new SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
            iterator.remove();
         }

         for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : rotate) {
            this.roots.put(entry.getKey(), entry.getValue());
            result.add(entry);
         }

         return result;
      } else {
         return result;
      }
   }

   public void prune(long gameTime, long maxChunkMemoryAge) {
      boolean changed = false;
      Iterator<Entry<Long, BlackBloodConquestData.ChunkMemory>> chunks = this.chunkMemory.entrySet().iterator();

      while (chunks.hasNext()) {
         Entry<Long, BlackBloodConquestData.ChunkMemory> entry = chunks.next();
         BlackBloodConquestData.ChunkMemory memory = entry.getValue();
         boolean useful = memory.infectionPressure() > 0 || memory.playerHeat() > 0 || memory.baseHeat() > 0 || memory.breaches() > 0;
         boolean expired = gameTime - memory.lastSeen() > maxChunkMemoryAge;
         if ((!useful || expired) && (memory.infectionPressure() <= 0 || memory.breaches() <= 0)) {
            chunks.remove();
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

   public record BloomData(BlockPos mainAltarPos, int storedBlood, int age, int tier, boolean fromPlayerBase) {
      public BlackBloodConquestData.BloomData tick(int bloodGain, int maxStored) {
         return new BlackBloodConquestData.BloomData(
            this.mainAltarPos, Math.min(Math.max(1, maxStored), Math.max(0, this.storedBlood + bloodGain)), this.age + 1, this.tier, this.fromPlayerBase
         );
      }

      public BlackBloodConquestData.BloomData withStoredBlood(int storedBlood) {
         return new BlackBloodConquestData.BloomData(this.mainAltarPos, Math.max(0, storedBlood), this.age, this.tier, this.fromPlayerBase);
      }

      public BlackBloodConquestData.BloomData withTier(int tier) {
         return new BlackBloodConquestData.BloomData(this.mainAltarPos, this.storedBlood, this.age, Math.max(this.tier, tier), this.fromPlayerBase);
      }
   }

   public record ChunkMemory(int playerHeat, int baseHeat, int infectionPressure, long lastSeen, int breaches) {
      private static final BlackBloodConquestData.ChunkMemory EMPTY = new BlackBloodConquestData.ChunkMemory(0, 0, 0, 0L, 0);
   }

   public record PendingRootLaunch(BlockPos altarPos, long targetChunk, int age, boolean playerBaseTarget, boolean bloomOnArrival) {
      public BlackBloodConquestData.PendingRootLaunch tick() {
         return new BlackBloodConquestData.PendingRootLaunch(this.altarPos, this.targetChunk, this.age + 1, this.playerBaseTarget, this.bloomOnArrival);
      }
   }

   public record PlayerMemory(long favoriteChunk, int favoriteScore, int totalSamples, long lastSeen) {
      private static final BlackBloodConquestData.PlayerMemory EMPTY = new BlackBloodConquestData.PlayerMemory(0L, 0, 0, 0L);
   }

   public record RootData(BlockPos altarPos, long targetChunk, int age, int progress, boolean playerBaseTarget, boolean bloomOnArrival) {
      public BlackBloodConquestData.RootData tickAge(BlockPos newAltarPos) {
         return new BlackBloodConquestData.RootData(
            newAltarPos.immutable(), this.targetChunk, this.age + 1, this.progress, this.playerBaseTarget, this.bloomOnArrival
         );
      }

      public BlackBloodConquestData.RootData advance(BlockPos newAltarPos) {
         return new BlackBloodConquestData.RootData(
            newAltarPos.immutable(), this.targetChunk, this.age, this.progress + 1, this.playerBaseTarget, this.bloomOnArrival
         );
      }

      public BlackBloodConquestData.RootData tick(BlockPos newAltarPos) {
         return new BlackBloodConquestData.RootData(
            newAltarPos.immutable(), this.targetChunk, this.age + 1, this.progress + 1, this.playerBaseTarget, this.bloomOnArrival
         );
      }
   }

   public record TargetChunk(long chunk, int score, boolean baseLike) {
   }
}
