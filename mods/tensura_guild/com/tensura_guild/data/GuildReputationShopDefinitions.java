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
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

public class GuildReputationShopDefinitions extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().create();
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String DIRECTORY = "guild_reputation_shop";
   private static Map<String, List<GuildReputationShopService.ShopEntry>> POOLS = Map.of();

   public GuildReputationShopDefinitions() {
      super(GSON, "guild_reputation_shop");
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<String, List<GuildReputationShopService.ShopEntry>> pools = new HashMap<>();
      object.forEach(
         (id, json) -> GuildReputationShopDefinitions.ShopDefinition.CODEC
            .parse(JsonOps.INSTANCE, json)
            .resultOrPartial(error -> LOGGER.error("Failed to parse reputation shop entry {}: {}", id, error))
            .filter(GuildReputationShopDefinitions.ShopDefinition::isRequiredModLoaded)
            .ifPresent(definition -> pools.computeIfAbsent(definition.rank(), ignored -> new ArrayList<>()).add(definition.toShopEntry()))
      );
      Map<String, List<GuildReputationShopService.ShopEntry>> immutablePools = new HashMap<>();
      pools.forEach((rank, entries) -> immutablePools.put(rank, entries.stream().sorted(Comparator.comparing(entry -> entry.item().toString())).toList()));
      POOLS = Map.copyOf(immutablePools);
      LOGGER.info("Loaded {} reputation shop rank pool(s)", POOLS.size());
   }

   public static List<GuildReputationShopService.ShopEntry> poolForRank(String rank) {
      return POOLS.getOrDefault(GuildLevelData.normalizeRank(rank), List.of());
   }

   public record ShopDefinition(
      String rank,
      ResourceLocation item,
      int cost,
      int count,
      int maxCount,
      Optional<String> requiredMod,
      List<ResourceLocation> resultPool,
      Optional<String> displayName,
      Optional<String> uniqueGroup,
      Optional<String> magicTomePool
   ) {
      public static final Codec<GuildReputationShopDefinitions.ShopDefinition> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("rank").forGetter(GuildReputationShopDefinitions.ShopDefinition::rank),
               ResourceLocation.CODEC.fieldOf("item").forGetter(GuildReputationShopDefinitions.ShopDefinition::item),
               ExtraCodecs.POSITIVE_INT.fieldOf("cost").forGetter(GuildReputationShopDefinitions.ShopDefinition::cost),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(GuildReputationShopDefinitions.ShopDefinition::count),
               ExtraCodecs.POSITIVE_INT.fieldOf("max_count").forGetter(GuildReputationShopDefinitions.ShopDefinition::maxCount),
               Codec.STRING.optionalFieldOf("required_mod").forGetter(GuildReputationShopDefinitions.ShopDefinition::requiredMod),
               ResourceLocation.CODEC.listOf().optionalFieldOf("result_pool", List.of()).forGetter(GuildReputationShopDefinitions.ShopDefinition::resultPool),
               Codec.STRING.optionalFieldOf("display_name").forGetter(GuildReputationShopDefinitions.ShopDefinition::displayName),
               Codec.STRING.optionalFieldOf("unique_group").forGetter(GuildReputationShopDefinitions.ShopDefinition::uniqueGroup),
               Codec.STRING.optionalFieldOf("magic_tome_pool").forGetter(GuildReputationShopDefinitions.ShopDefinition::magicTomePool)
            )
            .apply(instance, GuildReputationShopDefinitions.ShopDefinition::new)
      );

      public ShopDefinition {
         rank = GuildLevelData.normalizeRank(rank);
         cost = Math.max(1, cost);
         count = Math.max(1, count);
         maxCount = Math.max(1, maxCount);
         requiredMod = requiredMod == null ? Optional.empty() : requiredMod.map(String::trim).filter(modId -> !modId.isEmpty());
         resultPool = resultPool == null ? List.of() : List.copyOf(resultPool);
         displayName = displayName == null ? Optional.empty() : displayName.map(String::trim).filter(key -> !key.isEmpty());
         uniqueGroup = uniqueGroup == null ? Optional.empty() : uniqueGroup.map(String::trim).filter(group -> !group.isEmpty());
         magicTomePool = magicTomePool == null ? Optional.empty() : magicTomePool.map(String::trim).filter(pool -> !pool.isEmpty());
      }

      public boolean isRequiredModLoaded() {
         return this.requiredMod.isEmpty() || ModList.get().isLoaded(this.requiredMod.get());
      }

      public GuildReputationShopService.ShopEntry toShopEntry() {
         return new GuildReputationShopService.ShopEntry(
            this.item,
            this.cost,
            this.count,
            this.maxCount,
            this.resultPool,
            this.displayName.orElse(""),
            this.uniqueGroup.orElse(""),
            this.magicTomePool.orElse(""),
            Optional.empty()
         );
      }
   }
}
