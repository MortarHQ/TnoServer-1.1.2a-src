package com.tensura_guild.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

public class GuildTaskDefinitions extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().create();
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String DIRECTORY = "guild_tasks";
   public static final int DEFAULT_KILL_TASK_TIME_LIMIT_TICKS = 288000;
   public static final ResourceLocation KILL_5_SLIMES = ResourceLocation.fromNamespaceAndPath("tensura_guild", "kill_5_slimes");
   public static final ResourceLocation KILL_5_HOUND_DOGS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "kill_5_hound_dogs");
   public static final ResourceLocation KILL_5_BARGHESTS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "kill_5_barghests");
   public static final ResourceLocation COLLECT_5_MAGIC_ORE_SHARDS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_5_magic_ore_shards");
   public static final ResourceLocation COLLECT_10_MEDIUM_QUALITY_MAGIC_CRYSTALS = ResourceLocation.fromNamespaceAndPath(
      "tensura_guild", "collect_10_medium_quality_magic_crystals"
   );
   public static final ResourceLocation COLLECT_3_PALM_SAPLINGS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_3_palm_saplings");
   public static final ResourceLocation COLLECT_5_RAW_BLADE_TIGER_MEAT = ResourceLocation.fromNamespaceAndPath(
      "tensura_guild", "collect_5_raw_blade_tiger_meat"
   );
   public static final ResourceLocation COLLECT_5_RAW_GIANT_BAT_MEAT = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_5_raw_giant_bat_meat");
   public static final ResourceLocation CRAFT_3_HIGH_POTIONS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "craft_3_high_potions");
   public static final ResourceLocation COLLECT_2_SISSIE_FINS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_2_sissie_fins");
   public static final ResourceLocation COLLECT_20_MONSTER_LEATHER_C = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_20_monster_leather_c");
   public static final ResourceLocation COLLECT_10_MONSTER_LEATHER_B = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_10_monster_leather_b");
   public static final ResourceLocation COLLECT_4_HIPOKUTE_FLOWERS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_4_hipokute_flowers");
   public static final ResourceLocation COLLECT_3_BATTLEWILL_MANUALS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_3_battlewill_manuals");
   public static final ResourceLocation CRAFT_20_TRAINING_DUMMIES = ResourceLocation.fromNamespaceAndPath("tensura_guild", "craft_20_training_dummies");
   public static final ResourceLocation CRAFT_8_BRICKS_MAGIC_ENGINES = ResourceLocation.fromNamespaceAndPath("tensura_guild", "craft_8_bricks_magic_engines");
   public static final ResourceLocation COLLECT_64_OAK_TOOL_RACKS = ResourceLocation.fromNamespaceAndPath("tensura_guild", "collect_64_oak_tool_racks");
   public static final ResourceLocation CRAFT_2_KILN_MITHRIL = ResourceLocation.fromNamespaceAndPath("tensura_guild", "craft_2_kiln_mithril");
   private static final String DEFAULT_RANK = "F";
   private static final ResourceLocation BRONZE_COIN = ResourceLocation.fromNamespaceAndPath("tensura", "bronze_coin");
   private static final Map<ResourceLocation, GuildTaskDefinitions.GuildTaskDefinition> DEFAULT_TASKS = Map.ofEntries(
      Map.entry(KILL_5_SLIMES, killTask("kill_5_slimes", ResourceLocation.withDefaultNamespace("slime"), 5)),
      Map.entry(KILL_5_HOUND_DOGS, killTask("kill_5_hound_dogs", ResourceLocation.fromNamespaceAndPath("tensura", "hound_dog"), 5)),
      Map.entry(KILL_5_BARGHESTS, killTask("kill_5_barghests", ResourceLocation.fromNamespaceAndPath("tensura", "barghest"), 5)),
      Map.entry(COLLECT_5_MAGIC_ORE_SHARDS, itemTask("collect_5_magic_ore_shards", ResourceLocation.fromNamespaceAndPath("tensura", "magic_ore_shard"), 5)),
      Map.entry(
         COLLECT_10_MEDIUM_QUALITY_MAGIC_CRYSTALS,
         itemTask("collect_10_medium_quality_magic_crystals", ResourceLocation.fromNamespaceAndPath("tensura", "medium_quality_magic_crystal"), 10)
      ),
      Map.entry(COLLECT_3_PALM_SAPLINGS, itemTask("collect_3_palm_saplings", ResourceLocation.fromNamespaceAndPath("tensura", "palm_sapling"), 3)),
      Map.entry(
         COLLECT_5_RAW_BLADE_TIGER_MEAT,
         itemTask("collect_5_raw_blade_tiger_meat", ResourceLocation.fromNamespaceAndPath("tensura", "raw_blade_tiger_meat"), 5)
      ),
      Map.entry(
         COLLECT_5_RAW_GIANT_BAT_MEAT, itemTask("collect_5_raw_giant_bat_meat", ResourceLocation.fromNamespaceAndPath("tensura", "raw_giant_bat_meat"), 5)
      ),
      Map.entry(CRAFT_3_HIGH_POTIONS, itemTask("craft_3_high_potions", ResourceLocation.fromNamespaceAndPath("tensura", "high_potion"), 3)),
      Map.entry(COLLECT_2_SISSIE_FINS, itemTask("collect_2_sissie_fins", ResourceLocation.fromNamespaceAndPath("tensura", "sissie_fin"), 2)),
      Map.entry(
         COLLECT_20_MONSTER_LEATHER_C, itemTask("collect_20_monster_leather_c", ResourceLocation.fromNamespaceAndPath("tensura", "monster_leather_c"), 20)
      ),
      Map.entry(
         COLLECT_10_MONSTER_LEATHER_B, itemTask("collect_10_monster_leather_b", ResourceLocation.fromNamespaceAndPath("tensura", "monster_leather_b"), 10)
      ),
      Map.entry(COLLECT_4_HIPOKUTE_FLOWERS, itemTask("collect_4_hipokute_flowers", ResourceLocation.fromNamespaceAndPath("tensura", "hipokute_flower"), 4))
   );
   private static Map<ResourceLocation, GuildTaskDefinitions.GuildTaskDefinition> TASKS = DEFAULT_TASKS;

   public GuildTaskDefinitions() {
      super(GSON, "guild_tasks");
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<ResourceLocation, GuildTaskDefinitions.GuildTaskDefinition> map = new HashMap<>();
      object.forEach(
         (id, json) -> GuildTaskDefinitions.GuildTaskDefinition.CODEC
            .parse(JsonOps.INSTANCE, json)
            .resultOrPartial(error -> LOGGER.error("Failed to parse guild task {}: {}", id, error))
            .filter(GuildTaskDefinitions.GuildTaskDefinition::isRequiredModLoaded)
            .ifPresent(task -> map.put(id, task))
      );
      TASKS = map.isEmpty() ? DEFAULT_TASKS : Map.copyOf(map);
      LOGGER.info("Loaded {} guild task(s)", TASKS.size());
   }

   public static Optional<GuildTaskDefinitions.GuildTaskDefinition> get(ResourceLocation id) {
      return Optional.ofNullable(TASKS.get(id));
   }

   public static boolean contains(ResourceLocation id) {
      return TASKS.containsKey(id);
   }

   public static List<ResourceLocation> availableTaskIds() {
      List<ResourceLocation> ids = new ArrayList<>(TASKS.keySet());
      ids.sort(Comparator.comparing(ResourceLocation::toString));
      return ids;
   }

   public static List<ResourceLocation> availableTaskIdsFor(GuildLevelData levelData) {
      List<ResourceLocation> ids = new ArrayList<>();
      TASKS.forEach((id, task) -> {
         if (task.isUnlockedFor(levelData)) {
            ids.add(id);
         }
      });
      ids.sort(Comparator.comparing(ResourceLocation::toString));
      return ids;
   }

   public static List<ResourceLocation> availableTaskIdsForRank(String rank) {
      String normalizedRank = GuildLevelData.normalizeRank(rank);
      List<ResourceLocation> ids = new ArrayList<>();
      TASKS.forEach((id, task) -> {
         if (task.isRank(normalizedRank)) {
            ids.add(id);
         }
      });
      ids.sort(Comparator.comparing(ResourceLocation::toString));
      return ids;
   }

   private static GuildTaskDefinitions.GuildTaskDefinition killTask(String key, ResourceLocation target, int amount) {
      return new GuildTaskDefinitions.GuildTaskDefinition(
         "task.tensura_guild." + key + ".title",
         "task.tensura_guild." + key + ".description",
         "F",
         Optional.of(target),
         Optional.empty(),
         Optional.empty(),
         Optional.empty(),
         amount,
         Optional.empty(),
         BRONZE_COIN,
         6,
         5,
         false,
         false,
         Optional.empty(),
         0,
         Optional.empty(),
         0,
         Optional.empty(),
         false,
         0,
         0,
         0,
         1,
         1,
         List.of(),
         1.0,
         false
      );
   }

   private static GuildTaskDefinitions.GuildTaskDefinition itemTask(String key, ResourceLocation item, int amount) {
      return new GuildTaskDefinitions.GuildTaskDefinition(
         "task.tensura_guild." + key + ".title",
         "task.tensura_guild." + key + ".description",
         "F",
         Optional.empty(),
         Optional.of(item),
         Optional.empty(),
         Optional.empty(),
         amount,
         Optional.empty(),
         BRONZE_COIN,
         6,
         5,
         false,
         false,
         Optional.empty(),
         0,
         Optional.empty(),
         0,
         Optional.empty(),
         false,
         0,
         0,
         0,
         1,
         1,
         List.of(),
         1.0,
         false
      );
   }

   public record GuildTaskDefinition(
      String titleKey,
      String descriptionKey,
      String rank,
      Optional<ResourceLocation> targetEntity,
      Optional<ResourceLocation> requiredItem,
      Optional<ResourceLocation> requiredItemTag,
      Optional<String> requiredMod,
      int amount,
      Optional<Integer> maxAmount,
      ResourceLocation rewardItem,
      int rewardCount,
      int experienceReward,
      boolean partyOnly,
      boolean experiencePerMember,
      Optional<ResourceLocation> structureTag,
      int structureRadius,
      Optional<ResourceLocation> visitStructureTag,
      int visitRadius,
      Optional<Integer> timeLimitTicks,
      boolean spawnTarget,
      int spawnRadius,
      int spawnMinDistance,
      int spawnTriggerDistance,
      int spawnCount,
      int spawnMaxCount,
      List<ResourceLocation> spawnBiomes,
      double refreshChance,
      boolean rewardPerMember
   ) {
      public static final Codec<GuildTaskDefinitions.GuildTaskDefinition> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("title").forGetter(GuildTaskDefinitions.GuildTaskDefinition::titleKey),
               Codec.STRING.fieldOf("description").forGetter(GuildTaskDefinitions.GuildTaskDefinition::descriptionKey),
               Codec.STRING.optionalFieldOf("rank", "F").forGetter(GuildTaskDefinitions.GuildTaskDefinition::rank),
               ResourceLocation.CODEC.optionalFieldOf("target").forGetter(GuildTaskDefinitions.GuildTaskDefinition::targetEntity),
               ResourceLocation.CODEC.optionalFieldOf("required_item").forGetter(GuildTaskDefinitions.GuildTaskDefinition::requiredItem),
               ResourceLocation.CODEC.optionalFieldOf("required_item_tag").forGetter(GuildTaskDefinitions.GuildTaskDefinition::requiredItemTag),
               ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(GuildTaskDefinitions.GuildTaskDefinition::amount),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_amount").forGetter(GuildTaskDefinitions.GuildTaskDefinition::maxAmount),
               ResourceLocation.CODEC.fieldOf("reward_item").forGetter(GuildTaskDefinitions.GuildTaskDefinition::rewardItem),
               ExtraCodecs.POSITIVE_INT.fieldOf("reward_count").forGetter(GuildTaskDefinitions.GuildTaskDefinition::rewardCount),
               Codec.INT.optionalFieldOf("experience", 5).forGetter(GuildTaskDefinitions.GuildTaskDefinition::experienceReward),
               Codec.BOOL.optionalFieldOf("party_only", false).forGetter(GuildTaskDefinitions.GuildTaskDefinition::partyOnly),
               Codec.BOOL.optionalFieldOf("experience_per_member", false).forGetter(GuildTaskDefinitions.GuildTaskDefinition::experiencePerMember),
               ResourceLocation.CODEC.optionalFieldOf("structure_tag").forGetter(GuildTaskDefinitions.GuildTaskDefinition::structureTag),
               Codec.INT.optionalFieldOf("structure_radius", 0).forGetter(GuildTaskDefinitions.GuildTaskDefinition::structureRadius),
               GuildTaskDefinitions.TaskOptions.CODEC.forGetter(GuildTaskDefinitions.GuildTaskDefinition::taskOptions)
            )
            .apply(
               instance,
               (titleKey, descriptionKey, rank, targetEntity, requiredItem, requiredItemTag, amount, maxAmount, rewardItem, rewardCount, experienceReward, partyOnly, experiencePerMember, structureTag, structureRadius, options) -> new GuildTaskDefinitions.GuildTaskDefinition(
                  titleKey,
                  descriptionKey,
                  rank,
                  targetEntity,
                  requiredItem,
                  requiredItemTag,
                  options.requiredMod(),
                  amount,
                  maxAmount,
                  rewardItem,
                  rewardCount,
                  experienceReward,
                  partyOnly,
                  experiencePerMember,
                  structureTag,
                  structureRadius,
                  options.visitStructureTag(),
                  options.visitRadius(),
                  options.timeLimitTicks(),
                  options.spawnTarget(),
                  options.spawnRadius(),
                  options.spawnMinDistance(),
                  options.spawnTriggerDistance(),
                  options.spawnCount(),
                  options.spawnMaxCount(),
                  options.spawnBiomes(),
                  options.refreshChance(),
                  options.rewardPerMember()
               )
            )
      );

      public GuildTaskDefinition {
         rank = GuildLevelData.normalizeRank(rank);
         targetEntity = targetEntity == null ? Optional.empty() : targetEntity;
         requiredItem = requiredItem == null ? Optional.empty() : requiredItem;
         requiredItemTag = requiredItemTag == null ? Optional.empty() : requiredItemTag;
         requiredMod = requiredMod == null ? Optional.empty() : requiredMod.map(String::trim).filter(modId -> !modId.isEmpty());
         amount = Math.max(1, amount);
         int minAmount = amount;
         maxAmount = maxAmount == null ? Optional.empty() : maxAmount.map(value -> Math.max(minAmount, value));
         experienceReward = Math.max(0, experienceReward);
         structureTag = structureTag == null ? Optional.empty() : structureTag;
         structureRadius = Math.max(0, structureRadius);
         visitStructureTag = visitStructureTag == null ? Optional.empty() : visitStructureTag;
         visitRadius = Math.max(0, visitRadius);
         timeLimitTicks = timeLimitTicks == null ? Optional.empty() : timeLimitTicks.map(value -> Math.max(0, value));
         spawnRadius = Math.max(0, spawnRadius);
         spawnMinDistance = Math.max(0, Math.min(spawnMinDistance, spawnRadius));
         spawnTriggerDistance = Math.max(0, spawnTriggerDistance);
         spawnCount = Math.max(1, spawnCount);
         spawnMaxCount = Math.max(spawnCount, spawnMaxCount);
         spawnBiomes = spawnBiomes == null ? List.of() : List.copyOf(spawnBiomes);
         refreshChance = Math.max(0.0, Math.min(1.0, refreshChance));
      }

      public boolean isKillTask() {
         return this.targetEntity.isPresent();
      }

      public boolean isAreaKillTask() {
         return this.targetEntity.isPresent() && this.structureTag.isPresent() && this.structureRadius > 0;
      }

      public boolean isVisitStructureTask() {
         return this.visitStructureTag.isPresent() && this.visitRadius > 0;
      }

      public boolean isSpawnTargetTask() {
         return this.isKillTask() && this.spawnTarget && this.spawnRadius > 0 && this.spawnTriggerDistance > 0;
      }

      public boolean isMultiSpawnTargetTask() {
         return this.isSpawnTargetTask() && (this.spawnCount > 1 || this.spawnMaxCount > 1);
      }

      public int randomSpawnCount(Random random) {
         int min = Math.max(1, this.spawnCount);
         int max = Math.max(min, this.spawnMaxCount);
         if (max <= min) {
            return min;
         }

         Random safeRandom = random == null ? new Random() : random;
         return min + safeRandom.nextInt(max - min + 1);
      }

      public boolean isItemTask() {
         return this.requiredItem.isPresent() || this.requiredItemTag.isPresent();
      }

      public int effectiveTimeLimitTicks() {
         if (this.timeLimitTicks.isPresent()) {
            return this.timeLimitTicks.get();
         } else {
            return !this.isKillTask() && !this.isVisitStructureTask() ? 0 : 288000;
         }
      }

      public boolean isRequiredModLoaded() {
         return this.requiredMod.isEmpty() || ModList.get().isLoaded(this.requiredMod.get());
      }

      public int requiredKills() {
         return this.amount;
      }

      public int requiredCount() {
         return this.amount;
      }

      public boolean hasRandomKillCount() {
         return this.isKillTask() && this.maxAmount.filter(max -> max > this.amount).isPresent();
      }

      public int rollRequiredCount(Random random) {
         int max = this.maxAmount.orElse(this.amount);
         if (max <= this.amount) {
            return this.amount;
         }

         Random safeRandom = random == null ? new Random() : random;
         return this.amount + safeRandom.nextInt(max - this.amount + 1);
      }

      public boolean isUnlockedFor(GuildLevelData levelData) {
         GuildLevelData normalized = levelData == null ? GuildLevelData.DEFAULT : levelData;
         return normalized.levelIndex() >= GuildLevelData.levelIndexForRank(this.rank);
      }

      public boolean isRank(String rank) {
         return this.rank.equals(GuildLevelData.normalizeRank(rank));
      }

      public boolean countsKill(ResourceLocation entityType, ServerLevel level, BlockPos killPos) {
         if (this.targetEntity.filter(entityType::equals).isEmpty()) {
            return false;
         } else if (this.structureTag.isEmpty() || this.structureRadius <= 0) {
            return true;
         } else if (level != null && killPos != null) {
            TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, this.structureTag.get());
            int searchRadius = Math.max(1, (int)Math.ceil(this.structureRadius / 16.0) + 2);
            BlockPos nearest = level.findNearestMapStructure(tag, killPos, searchRadius, false);
            return nearest != null && nearest.distSqr(killPos) <= (double)this.structureRadius * this.structureRadius;
         } else {
            return false;
         }
      }

      private GuildTaskDefinitions.TaskOptions taskOptions() {
         return new GuildTaskDefinitions.TaskOptions(
            this.requiredMod,
            this.timeLimitTicks,
            this.spawnTarget,
            this.spawnRadius,
            this.spawnMinDistance,
            this.spawnTriggerDistance,
            this.spawnCount,
            this.spawnMaxCount,
            this.spawnBiomes,
            this.refreshChance,
            this.rewardPerMember,
            this.visitStructureTag,
            this.visitRadius
         );
      }
   }

   private record TaskOptions(
      Optional<String> requiredMod,
      Optional<Integer> timeLimitTicks,
      boolean spawnTarget,
      int spawnRadius,
      int spawnMinDistance,
      int spawnTriggerDistance,
      int spawnCount,
      int spawnMaxCount,
      List<ResourceLocation> spawnBiomes,
      double refreshChance,
      boolean rewardPerMember,
      Optional<ResourceLocation> visitStructureTag,
      int visitRadius
   ) {
      private static final MapCodec<GuildTaskDefinitions.TaskOptions> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.STRING.optionalFieldOf("required_mod").forGetter(GuildTaskDefinitions.TaskOptions::requiredMod),
               ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("time_limit_ticks").forGetter(GuildTaskDefinitions.TaskOptions::timeLimitTicks),
               Codec.BOOL.optionalFieldOf("spawn_target", false).forGetter(GuildTaskDefinitions.TaskOptions::spawnTarget),
               Codec.INT.optionalFieldOf("spawn_radius", 0).forGetter(GuildTaskDefinitions.TaskOptions::spawnRadius),
               Codec.INT.optionalFieldOf("spawn_min_distance", 0).forGetter(GuildTaskDefinitions.TaskOptions::spawnMinDistance),
               Codec.INT.optionalFieldOf("spawn_trigger_distance", 0).forGetter(GuildTaskDefinitions.TaskOptions::spawnTriggerDistance),
               Codec.INT.optionalFieldOf("spawn_count", 1).forGetter(GuildTaskDefinitions.TaskOptions::spawnCount),
               Codec.INT.optionalFieldOf("spawn_max_count", 1).forGetter(GuildTaskDefinitions.TaskOptions::spawnMaxCount),
               ResourceLocation.CODEC.listOf().optionalFieldOf("spawn_biomes", List.of()).forGetter(GuildTaskDefinitions.TaskOptions::spawnBiomes),
               Codec.DOUBLE.optionalFieldOf("refresh_chance", 1.0).forGetter(GuildTaskDefinitions.TaskOptions::refreshChance),
               Codec.BOOL.optionalFieldOf("reward_per_member", false).forGetter(GuildTaskDefinitions.TaskOptions::rewardPerMember),
               ResourceLocation.CODEC.optionalFieldOf("visit_structure_tag").forGetter(GuildTaskDefinitions.TaskOptions::visitStructureTag),
               Codec.INT.optionalFieldOf("visit_radius", 0).forGetter(GuildTaskDefinitions.TaskOptions::visitRadius)
            )
            .apply(instance, GuildTaskDefinitions.TaskOptions::new)
      );
   }
}
