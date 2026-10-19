package com.tensura_guild.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

public class DwarfTaskDefinitions extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().create();
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String DIRECTORY = "dwarf_tasks";
   public static final ResourceLocation HIPOKUTE_FLOWER_TASK = ResourceLocation.fromNamespaceAndPath("tensura_guild", "dwarf_hipokute_flowers");
   public static final ResourceLocation MINER_MAGIC_ORE_SHARDS_TASK = ResourceLocation.fromNamespaceAndPath("tensura_guild", "dwarf_miner_magic_ore_shards");
   private static final Map<ResourceLocation, DwarfTaskDefinitions.DwarfTaskDefinition> DEFAULT_TASKS = Map.of(
      HIPOKUTE_FLOWER_TASK,
      new DwarfTaskDefinitions.DwarfTaskDefinition(
         "dwarf_task.tensura_guild.dwarf_hipokute_flowers.title",
         "dwarf_task.tensura_guild.dwarf_hipokute_flowers.description",
         new DwarfTaskDefinitions.DialogueKeys(
            "dialogue.tensura_guild.dwarf_task.dwarf_hipokute_flowers.offer",
            "dialogue.tensura_guild.dwarf_task.dwarf_hipokute_flowers.active",
            "dialogue.tensura_guild.dwarf_task.dwarf_hipokute_flowers.ready"
         ),
         ResourceLocation.fromNamespaceAndPath("tensura", "hipokute_flower"),
         List.of(),
         Optional.empty(),
         Optional.empty(),
         18,
         Optional.empty(),
         288000,
         ResourceLocation.fromNamespaceAndPath("tensura", "low_potion"),
         1,
         List.of(),
         Optional.empty(),
         5,
         5
      ),
      MINER_MAGIC_ORE_SHARDS_TASK,
      new DwarfTaskDefinitions.DwarfTaskDefinition(
         "dwarf_task.tensura_guild.dwarf_miner_magic_ore_shards.title",
         "dwarf_task.tensura_guild.dwarf_miner_magic_ore_shards.description",
         new DwarfTaskDefinitions.DialogueKeys(
            "dialogue.tensura_guild.dwarf_task.dwarf_miner_magic_ore_shards.offer",
            "dialogue.tensura_guild.dwarf_task.dwarf_miner_magic_ore_shards.active",
            "dialogue.tensura_guild.dwarf_task.dwarf_miner_magic_ore_shards.ready"
         ),
         ResourceLocation.fromNamespaceAndPath("tensura", "magic_ore_shard"),
         List.of(),
         Optional.empty(),
         Optional.of(ResourceLocation.fromNamespaceAndPath("tensura", "miner")),
         20,
         Optional.empty(),
         576000,
         ResourceLocation.fromNamespaceAndPath("tensura", "pure_magisteel_pickaxe"),
         1,
         List.of(),
         Optional.empty(),
         10,
         5
      )
   );
   private static Map<ResourceLocation, DwarfTaskDefinitions.DwarfTaskDefinition> TASKS = DEFAULT_TASKS;

   public DwarfTaskDefinitions() {
      super(GSON, "dwarf_tasks");
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<ResourceLocation, DwarfTaskDefinitions.DwarfTaskDefinition> map = new HashMap<>();
      object.forEach(
         (id, json) -> DwarfTaskDefinitions.DwarfTaskDefinition.CODEC
            .parse(JsonOps.INSTANCE, json)
            .resultOrPartial(error -> LOGGER.error("Failed to parse dwarf task {}: {}", id, error))
            .filter(DwarfTaskDefinitions.DwarfTaskDefinition::isRequiredModLoaded)
            .ifPresent(task -> map.put(id, task))
      );
      TASKS = map.isEmpty() ? DEFAULT_TASKS : Map.copyOf(map);
      LOGGER.info("Loaded {} dwarf task(s)", TASKS.size());
   }

   public static Optional<DwarfTaskDefinitions.DwarfTaskDefinition> get(ResourceLocation id) {
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

   public static List<ResourceLocation> availableTaskIdsForProfession(ResourceLocation profession) {
      if (profession == null) {
         return List.of();
      }

      List<ResourceLocation> ids = new ArrayList<>();
      TASKS.forEach((id, task) -> {
         if (task.matchesProfession(profession)) {
            ids.add(id);
         }
      });
      ids.sort(Comparator.comparing(ResourceLocation::toString));
      return ids;
   }

   public record DialogueKeys(String offer, String active, String ready) {
      private static final String DEFAULT_OFFER = "screen.tensura_guild.dwarf_task.dialogue.offer";
      private static final String DEFAULT_ACTIVE = "screen.tensura_guild.dwarf_task.dialogue.active";
      private static final String DEFAULT_READY = "screen.tensura_guild.dwarf_task.dialogue.ready";
      private static final DwarfTaskDefinitions.DialogueKeys DEFAULT = new DwarfTaskDefinitions.DialogueKeys(
         "screen.tensura_guild.dwarf_task.dialogue.offer", "screen.tensura_guild.dwarf_task.dialogue.active", "screen.tensura_guild.dwarf_task.dialogue.ready"
      );
      private static final Codec<DwarfTaskDefinitions.DialogueKeys> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("offer").forGetter(DwarfTaskDefinitions.DialogueKeys::offer),
               Codec.STRING.fieldOf("active").forGetter(DwarfTaskDefinitions.DialogueKeys::active),
               Codec.STRING.fieldOf("ready").forGetter(DwarfTaskDefinitions.DialogueKeys::ready)
            )
            .apply(instance, DwarfTaskDefinitions.DialogueKeys::new)
      );

      public DialogueKeys {
         offer = normalizeKey(offer, "screen.tensura_guild.dwarf_task.dialogue.offer");
         active = normalizeKey(active, "screen.tensura_guild.dwarf_task.dialogue.active");
         ready = normalizeKey(ready, "screen.tensura_guild.dwarf_task.dialogue.ready");
      }

      private static String normalizeKey(String value, String fallback) {
         return value != null && !value.isBlank() ? value.trim() : fallback;
      }
   }

   public record DwarfTaskDefinition(
      String titleKey,
      String descriptionKey,
      DwarfTaskDefinitions.DialogueKeys dialogue,
      ResourceLocation requiredItem,
      List<ResourceLocation> requiredItems,
      Optional<String> requiredMod,
      Optional<ResourceLocation> profession,
      int amount,
      Optional<Integer> maxAmount,
      int timeLimitTicks,
      ResourceLocation rewardItem,
      int rewardCount,
      List<DwarfTaskDefinitions.RandomBonusReward> randomBonusRewards,
      Optional<String> randomBonusTextKey,
      int experienceReward,
      int dwarfReputationReward
   ) {
      public static final Codec<DwarfTaskDefinitions.DwarfTaskDefinition> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.optionalFieldOf("title", "dwarf_task.tensura_guild.unknown.title").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::titleKey),
               Codec.STRING
                  .optionalFieldOf("description", "dwarf_task.tensura_guild.unknown.description")
                  .forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::descriptionKey),
               DwarfTaskDefinitions.DialogueKeys.CODEC
                  .optionalFieldOf("dialogue", DwarfTaskDefinitions.DialogueKeys.DEFAULT)
                  .forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::dialogue),
               ResourceLocation.CODEC.fieldOf("required_item").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::requiredItem),
               ResourceLocation.CODEC.listOf().optionalFieldOf("required_items", List.of()).forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::requiredItems),
               Codec.STRING.optionalFieldOf("required_mod").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::requiredMod),
               ResourceLocation.CODEC.optionalFieldOf("profession").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::profession),
               ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::amount),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_amount").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::maxAmount),
               ExtraCodecs.POSITIVE_INT.fieldOf("time_limit_ticks").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::timeLimitTicks),
               ResourceLocation.CODEC.fieldOf("reward_item").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::rewardItem),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("reward_count", 1).forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::rewardCount),
               DwarfTaskDefinitions.RandomBonusReward.CODEC
                  .listOf()
                  .optionalFieldOf("random_bonus_rewards", List.of())
                  .forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::randomBonusRewards),
               Codec.STRING.optionalFieldOf("random_bonus_text").forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::randomBonusTextKey),
               Codec.INT.optionalFieldOf("experience", 0).forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::experienceReward),
               Codec.INT.optionalFieldOf("dwarf_reputation", 0).forGetter(DwarfTaskDefinitions.DwarfTaskDefinition::dwarfReputationReward)
            )
            .apply(instance, DwarfTaskDefinitions.DwarfTaskDefinition::new)
      );

      public DwarfTaskDefinition {
         titleKey = titleKey != null && !titleKey.isBlank() ? titleKey.trim() : "dwarf_task.tensura_guild.unknown.title";
         descriptionKey = descriptionKey != null && !descriptionKey.isBlank() ? descriptionKey.trim() : "dwarf_task.tensura_guild.unknown.description";
         dialogue = dialogue == null ? DwarfTaskDefinitions.DialogueKeys.DEFAULT : dialogue;
         requiredItems = requiredItems == null ? List.of() : List.copyOf(requiredItems);
         requiredMod = requiredMod == null ? Optional.empty() : requiredMod.map(String::trim).filter(modId -> !modId.isEmpty());
         profession = profession == null ? Optional.empty() : profession;
         amount = Math.max(1, amount);
         int minAmount = amount;
         maxAmount = maxAmount == null ? Optional.empty() : maxAmount.map(value -> Math.max(minAmount, value));
         timeLimitTicks = Math.max(1, timeLimitTicks);
         rewardCount = Math.max(1, rewardCount);
         randomBonusRewards = randomBonusRewards == null ? List.of() : List.copyOf(randomBonusRewards);
         randomBonusTextKey = randomBonusTextKey == null ? Optional.empty() : randomBonusTextKey.map(String::trim).filter(key -> !key.isEmpty());
         experienceReward = Math.max(0, experienceReward);
         dwarfReputationReward = Math.max(0, dwarfReputationReward);
      }

      public boolean isRequiredModLoaded() {
         return this.requiredMod.isEmpty() || ModList.get().isLoaded(this.requiredMod.get());
      }

      public int requiredCount() {
         return this.hasRequiredItemSet() ? this.requiredItems.size() : this.amount;
      }

      public int rollRequiredCount(RandomSource random) {
         if (this.hasRequiredItemSet()) {
            return this.requiredCount();
         }

         int max = this.maxAmount.orElse(this.amount);
         return max > this.amount && random != null ? this.amount + random.nextInt(max - this.amount + 1) : this.amount;
      }

      public boolean matchesProfession(ResourceLocation profession) {
         return this.profession.filter(profession::equals).isPresent();
      }

      public boolean hasRequiredItemSet() {
         return !this.requiredItems.isEmpty();
      }

      public boolean hasRandomBonusRewards() {
         return !this.randomBonusRewards.isEmpty();
      }

      public Optional<DwarfTaskDefinitions.RandomBonusReward> rollRandomBonusReward(RandomSource random) {
         if (this.randomBonusRewards.isEmpty()) {
            return Optional.empty();
         } else {
            return random != null && this.randomBonusRewards.size() != 1
               ? Optional.of(this.randomBonusRewards.get(random.nextInt(this.randomBonusRewards.size())))
               : Optional.of(this.randomBonusRewards.getFirst());
         }
      }

      public int randomBonusMinCount() {
         return this.randomBonusRewards.stream().mapToInt(DwarfTaskDefinitions.RandomBonusReward::minCount).min().orElse(0);
      }

      public int randomBonusMaxCount() {
         return this.randomBonusRewards.stream().mapToInt(DwarfTaskDefinitions.RandomBonusReward::maxRollCount).max().orElse(0);
      }
   }

   public record RandomBonusReward(ResourceLocation item, int count, Optional<Integer> maxCount) {
      private static final Codec<DwarfTaskDefinitions.RandomBonusReward> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("item").forGetter(DwarfTaskDefinitions.RandomBonusReward::item),
               ExtraCodecs.POSITIVE_INT.fieldOf("count").forGetter(DwarfTaskDefinitions.RandomBonusReward::count),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_count").forGetter(DwarfTaskDefinitions.RandomBonusReward::maxCount)
            )
            .apply(instance, DwarfTaskDefinitions.RandomBonusReward::new)
      );

      public RandomBonusReward {
         count = Math.max(1, count);
         int minCount = count;
         maxCount = maxCount == null ? Optional.empty() : maxCount.map(value -> Math.max(minCount, value));
      }

      public int minCount() {
         return this.count;
      }

      public int maxRollCount() {
         return this.maxCount.orElse(this.count);
      }

      public int rollCount(RandomSource random) {
         int max = this.maxRollCount();
         return random != null && max > this.count ? this.count + random.nextInt(max - this.count + 1) : this.count;
      }
   }
}
