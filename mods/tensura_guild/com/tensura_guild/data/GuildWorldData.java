package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class GuildWorldData extends SavedData {
   private static final String DATA_NAME = "tensura_guild_world";
   private static final int CURRENT_TASK_CATALOG_VERSION = 4;
   private static final Codec<GuildWorldData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.optionalFieldOf("next_member_number", 1).forGetter(data -> data.nextMemberNumber),
            ResourceLocation.CODEC.listOf().optionalFieldOf("available_tasks", GuildTaskDefinitions.availableTaskIds()).forGetter(data -> data.availableTasks),
            Codec.INT.optionalFieldOf("task_catalog_version", 1).forGetter(data -> data.taskCatalogVersion),
            ResourceLocation.CODEC.listOf().optionalFieldOf("reserved_tasks", List.of()).forGetter(data -> data.reservedTasks),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.LONG)
               .optionalFieldOf("available_task_listed_at", Map.of())
               .forGetter(data -> data.availableTaskListedAt),
            Codec.LONG.optionalFieldOf("shop_refresh_nonce", 0L).forGetter(data -> data.shopRefreshNonce)
         )
         .apply(instance, GuildWorldData::new)
   );
   private int nextMemberNumber;
   private List<ResourceLocation> availableTasks;
   private int taskCatalogVersion;
   private List<ResourceLocation> reservedTasks;
   private Map<ResourceLocation, Long> availableTaskListedAt;
   private long shopRefreshNonce;

   private GuildWorldData(
      int nextMemberNumber,
      List<ResourceLocation> availableTasks,
      int taskCatalogVersion,
      List<ResourceLocation> reservedTasks,
      Map<ResourceLocation, Long> availableTaskListedAt,
      long shopRefreshNonce
   ) {
      this.nextMemberNumber = Math.max(1, nextMemberNumber);
      this.availableTasks = new ArrayList<>(availableTasks == null ? List.of() : availableTasks);
      this.taskCatalogVersion = Math.max(1, taskCatalogVersion);
      this.reservedTasks = new ArrayList<>(reservedTasks == null ? List.of() : reservedTasks);
      this.availableTaskListedAt = new HashMap<>(availableTaskListedAt == null ? Map.of() : availableTaskListedAt);
      this.shopRefreshNonce = Math.max(0L, shopRefreshNonce);
   }

   public static GuildWorldData get(MinecraftServer server) {
      ServerLevel level = server.overworld();
      GuildWorldData data = (GuildWorldData)level.getDataStorage()
         .computeIfAbsent(new Factory(GuildWorldData::create, GuildWorldData::load, null), "tensura_guild_world");
      data.initializeAvailableTaskTimes(level.getGameTime());
      return data;
   }

   private static GuildWorldData create() {
      return new GuildWorldData(1, GuildTaskDefinitions.availableTaskIds(), 4, List.of(), Map.of(), 0L);
   }

   private static GuildWorldData load(CompoundTag tag, Provider provider) {
      GuildWorldData data = CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag).result().orElseGet(GuildWorldData::create);
      if (!tag.contains("available_tasks") && tag.contains("slime_task_available")) {
         data.availableTasks.clear();
         if (tag.getBoolean("slime_task_available")) {
            data.availableTasks.add(GuildTaskDefinitions.KILL_5_SLIMES);
         }
      }

      data.updateTaskCatalog();
      return data;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this).result().ifPresent(encoded -> {
         if (encoded instanceof CompoundTag compoundTag) {
            tag.merge(compoundTag);
         }
      });
      return tag;
   }

   public int claimNextMemberNumber() {
      int value = this.nextMemberNumber++;
      this.setDirty();
      return value;
   }

   public long shopRefreshNonce() {
      return this.shopRefreshNonce;
   }

   public long refreshReputationShop() {
      this.shopRefreshNonce++;
      this.setDirty();
      return this.shopRefreshNonce;
   }

   public List<ResourceLocation> availableTasks() {
      return List.copyOf(this.availableTasks);
   }

   public long availableTaskWithdrawAt(ResourceLocation taskId, int withdrawTicks) {
      if (taskId != null && withdrawTicks > 0 && this.availableTasks.contains(taskId)) {
         long listedAt = this.availableTaskListedAt.getOrDefault(taskId, -1L);
         return listedAt < 0L ? -1L : listedAt + withdrawTicks;
      } else {
         return -1L;
      }
   }

   public Map<ResourceLocation, Long> availableTaskWithdrawAt(Collection<ResourceLocation> taskIds, int withdrawTicks) {
      if (withdrawTicks > 0 && taskIds != null && !taskIds.isEmpty()) {
         Map<ResourceLocation, Long> withdrawAt = new HashMap<>();

         for (ResourceLocation taskId : taskIds) {
            long value = this.availableTaskWithdrawAt(taskId, withdrawTicks);
            if (value >= 0L) {
               withdrawAt.put(taskId, value);
            }
         }

         return Map.copyOf(withdrawAt);
      } else {
         return Map.of();
      }
   }

   public boolean isTaskAvailable(ResourceLocation taskId) {
      return this.availableTasks.contains(taskId);
   }

   public boolean addTask(ResourceLocation taskId) {
      if (GuildTaskDefinitions.contains(taskId) && !this.availableTasks.contains(taskId) && !this.reservedTasks.contains(taskId)) {
         this.availableTasks.add(taskId);
         this.availableTaskListedAt.put(taskId, -1L);
         this.availableTasks.sort(Comparator.comparing(ResourceLocation::toString));
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   public boolean consumeTask(ResourceLocation taskId) {
      if (!this.isTaskAvailable(taskId)) {
         return false;
      }

      this.availableTasks.remove(taskId);
      this.availableTaskListedAt.remove(taskId);
      if (!this.reservedTasks.contains(taskId)) {
         this.reservedTasks.add(taskId);
         this.reservedTasks.sort(Comparator.comparing(ResourceLocation::toString));
      }

      this.setDirty();
      return true;
   }

   public boolean releaseTask(ResourceLocation taskId) {
      if (taskId != null && this.reservedTasks.remove(taskId)) {
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   public boolean reserveTask(ResourceLocation taskId) {
      if (taskId != null && GuildTaskDefinitions.contains(taskId) && !this.reservedTasks.contains(taskId)) {
         this.availableTasks.remove(taskId);
         this.availableTaskListedAt.remove(taskId);
         this.reservedTasks.add(taskId);
         this.reservedTasks.sort(Comparator.comparing(ResourceLocation::toString));
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   public int refreshAvailableTasks(Random random) {
      List<ResourceLocation> refreshedTasks = new ArrayList<>();

      for (String rank : GuildLevelData.ranksUpToLevel(GuildLevelData.MAX_LEVEL)) {
         List<ResourceLocation> candidates = new ArrayList<>(GuildTaskDefinitions.availableTaskIdsForRank(rank));
         candidates.removeIf(this.reservedTasks::contains);
         candidates.removeIf(taskIdx -> !passesRefreshChance(random, taskIdx));
         Collections.shuffle(candidates, random);
         GuildWorldData.TaskRefreshRange range = GuildWorldData.TaskRefreshRange.forRank(rank);
         int maxCount = Math.min(range.max(), candidates.size());
         int minCount = Math.min(range.min(), maxCount);
         int count = maxCount <= minCount ? maxCount : minCount + random.nextInt(maxCount - minCount + 1);
         refreshedTasks.addAll(candidates.subList(0, count));
      }

      Map<ResourceLocation, Long> refreshedTimes = new HashMap<>();

      for (ResourceLocation taskId : refreshedTasks) {
         refreshedTimes.put(taskId, -1L);
      }

      this.availableTasks = refreshedTasks;
      this.availableTaskListedAt = refreshedTimes;
      this.availableTasks.sort(Comparator.comparing(ResourceLocation::toString));
      this.setDirty();
      return this.availableTasks.size();
   }

   public boolean addRandomAvailableTask(Random random, int maxTasks, Collection<ResourceLocation> excludedTasks) {
      return this.addRandomAvailableTask(random, maxTasks, excludedTasks, GuildLevelData.rankForLevel(1));
   }

   public boolean addRandomAvailableTask(Random random, int maxTasks, Collection<ResourceLocation> excludedTasks, String rank) {
      return this.addRandomAvailableTasks(random, maxTasks, excludedTasks, rank, 1, maxTasks) > 0;
   }

   public int addRandomAvailableTasks(Random random, int maxTasks, Collection<ResourceLocation> excludedTasks, String rank, int minTarget, int maxTarget) {
      String normalizedRank = GuildLevelData.normalizeRank(rank);
      List<ResourceLocation> rankTaskIds = GuildTaskDefinitions.availableTaskIdsForRank(normalizedRank);
      int limit = Math.min(Math.max(1, maxTasks), Math.min(Math.max(1, maxTarget), rankTaskIds.size()));
      int minCount = Math.min(Math.max(0, minTarget), limit);
      int targetCount = limit <= minCount ? limit : minCount + random.nextInt(limit - minCount + 1);
      int availableRankCount = (int)this.availableTasks
         .stream()
         .filter(taskId -> GuildTaskDefinitions.get(taskId).map(task -> task.isRank(normalizedRank)).orElse(false))
         .count();
      if (availableRankCount >= targetCount) {
         return 0;
      }

      List<ResourceLocation> excluded = excludedTasks == null ? List.of() : List.copyOf(excludedTasks);
      List<ResourceLocation> candidates = new ArrayList<>(rankTaskIds);
      candidates.removeIf(
         taskId -> this.availableTasks.contains(taskId)
            || this.reservedTasks.contains(taskId)
            || excluded.contains(taskId)
            || !passesRefreshChance(random, taskId)
      );
      if (candidates.isEmpty()) {
         return 0;
      }

      Collections.shuffle(candidates, random);
      int addCount = Math.min(targetCount - availableRankCount, candidates.size());

      for (ResourceLocation selected : candidates.subList(0, addCount)) {
         this.availableTasks.add(selected);
         this.availableTaskListedAt.put(selected, -1L);
      }

      this.availableTasks.sort(Comparator.comparing(ResourceLocation::toString));
      this.setDirty();
      return addCount;
   }

   public boolean expireAvailableTasks(long gameTime, int withdrawTicks) {
      if (withdrawTicks <= 0) {
         return false;
      }

      boolean changed = false;
      Iterator<ResourceLocation> iterator = this.availableTasks.iterator();

      while (iterator.hasNext()) {
         ResourceLocation taskId = iterator.next();
         long listedAt = this.availableTaskListedAt.getOrDefault(taskId, -1L);
         if (listedAt < 0L) {
            this.availableTaskListedAt.put(taskId, Math.max(0L, gameTime));
            changed = true;
         } else if (gameTime - listedAt >= withdrawTicks) {
            iterator.remove();
            this.availableTaskListedAt.remove(taskId);
            changed = true;
         }
      }

      if (this.availableTaskListedAt.keySet().removeIf(taskIdx -> !this.availableTasks.contains(taskIdx))) {
         changed = true;
      }

      if (changed) {
         this.setDirty();
      }

      return changed;
   }

   private void initializeAvailableTaskTimes(long gameTime) {
      boolean changed = false;

      for (ResourceLocation taskId : this.availableTasks) {
         if (this.availableTaskListedAt.getOrDefault(taskId, -1L) < 0L) {
            this.availableTaskListedAt.put(taskId, Math.max(0L, gameTime));
            changed = true;
         }
      }

      if (this.availableTaskListedAt.keySet().removeIf(taskIdx -> !this.availableTasks.contains(taskIdx))) {
         changed = true;
      }

      if (changed) {
         this.setDirty();
      }
   }

   private void updateTaskCatalog() {
      if (this.taskCatalogVersion < 4) {
         this.addTaskIfKnown(GuildTaskDefinitions.KILL_5_HOUND_DOGS);
         this.addTaskIfKnown(GuildTaskDefinitions.KILL_5_BARGHESTS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_5_MAGIC_ORE_SHARDS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_10_MEDIUM_QUALITY_MAGIC_CRYSTALS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_3_PALM_SAPLINGS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_5_RAW_BLADE_TIGER_MEAT);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_5_RAW_GIANT_BAT_MEAT);
         this.addTaskIfKnown(GuildTaskDefinitions.CRAFT_3_HIGH_POTIONS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_2_SISSIE_FINS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_20_MONSTER_LEATHER_C);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_10_MONSTER_LEATHER_B);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_4_HIPOKUTE_FLOWERS);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_3_BATTLEWILL_MANUALS);
         this.addTaskIfKnown(GuildTaskDefinitions.CRAFT_20_TRAINING_DUMMIES);
         this.addTaskIfKnown(GuildTaskDefinitions.CRAFT_8_BRICKS_MAGIC_ENGINES);
         this.addTaskIfKnown(GuildTaskDefinitions.COLLECT_64_OAK_TOOL_RACKS);
         this.addTaskIfKnown(GuildTaskDefinitions.CRAFT_2_KILN_MITHRIL);
         this.availableTasks.sort(Comparator.comparing(ResourceLocation::toString));
         this.taskCatalogVersion = 4;
         this.setDirty();
      }
   }

   private void addTaskIfKnown(ResourceLocation taskId) {
      if (GuildTaskDefinitions.contains(taskId) && !this.availableTasks.contains(taskId) && !this.reservedTasks.contains(taskId)) {
         this.availableTasks.add(taskId);
         this.availableTaskListedAt.put(taskId, -1L);
      }
   }

   private static boolean passesRefreshChance(Random random, ResourceLocation taskId) {
      double chance = GuildTaskDefinitions.get(taskId).map(GuildTaskDefinitions.GuildTaskDefinition::refreshChance).orElse(1.0);
      return chance >= 1.0 || random.nextDouble() < chance;
   }

   public record TaskRefreshRange(int min, int max) {
      public TaskRefreshRange {
         min = Math.max(0, min);
         max = Math.max(min, max);
      }

      public static GuildWorldData.TaskRefreshRange forRank(String rank) {
         return switch (GuildLevelData.normalizeRank(rank)) {
            case "E", "D" -> new GuildWorldData.TaskRefreshRange(3, 8);
            case "C" -> new GuildWorldData.TaskRefreshRange(3, 7);
            case "B", "A" -> new GuildWorldData.TaskRefreshRange(2, 5);
            case "S" -> new GuildWorldData.TaskRefreshRange(1, 3);
            default -> new GuildWorldData.TaskRefreshRange(4, 9);
         };
      }
   }
}
