package com.tensura_guild.data;

import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.item.misc.MagicTomeItem;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class GuildReputationShopService {
   private static final int VANILLA_DAY_TICKS = 24000;
   private static final int VISIBLE_SHOP_ENTRIES = 6;
   private static final int DEFAULT_DWARF_TASK_RANK_LEVEL = GuildLevelData.levelIndexForRank("F");
   private static final ResourceLocation MAGIC_TOME = ResourceLocation.fromNamespaceAndPath("tensura", "magic_tome");
   private static final String MAGIC_TOME_POOL_ALL = "all";
   private static final Set<ResourceLocation> EXCLUDED_MAGIC_TOMES = Set.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "create_lesser_undead"),
      ResourceLocation.fromNamespaceAndPath("tensura", "create_greater_undead"),
      ResourceLocation.fromNamespaceAndPath("tensura", "curse"),
      ResourceLocation.fromNamespaceAndPath("tensura", "curse_bind")
   );
   private static final Map<String, GuildReputationShopService.RewardRange> REWARD_RANGES = Map.of(
      "F",
      new GuildReputationShopService.RewardRange(5, 10),
      "E",
      new GuildReputationShopService.RewardRange(1, 5),
      "D",
      new GuildReputationShopService.RewardRange(10, 15),
      "C",
      new GuildReputationShopService.RewardRange(10, 15),
      "B",
      new GuildReputationShopService.RewardRange(15, 30),
      "A",
      new GuildReputationShopService.RewardRange(30, 60),
      "S",
      new GuildReputationShopService.RewardRange(60, 100)
   );

   private GuildReputationShopService() {
   }

   public static int coins(ServerPlayer player) {
      return Math.max(0, (Integer)player.getData(GuildAttachments.REPUTATION_COINS));
   }

   public static void addCoins(ServerPlayer player, int amount) {
      if (amount > 0) {
         long next = (long)coins(player) + amount;
         player.setData(GuildAttachments.REPUTATION_COINS, (int)Math.min(2147483647L, next));
         player.syncData(GuildAttachments.REPUTATION_COINS);
      }
   }

   public static void awardGuildTaskCoins(ServerPlayer player, String rank) {
      awardCoinsForRank(player, GuildLevelData.levelIndexForRank(rank), GuildTaskRewardPenalty.NONE);
   }

   public static void awardGuildTaskCoins(ServerPlayer player, String rank, GuildTaskRewardPenalty penalty) {
      awardCoinsForRank(player, GuildLevelData.levelIndexForRank(rank), penalty);
   }

   public static void awardDwarfTaskCoins(ServerPlayer player) {
      awardCoinsForRank(player, DEFAULT_DWARF_TASK_RANK_LEVEL, GuildTaskRewardPenalty.NONE);
   }

   public static void awardDwarfTaskCoins(ServerPlayer player, GuildTaskRewardPenalty penalty) {
      awardCoinsForRank(player, DEFAULT_DWARF_TASK_RANK_LEVEL, penalty);
   }

   public static List<GuildReputationShopService.ShopEntry> entriesForRank(String rank) {
      return entriesForRank(rank, 0L, new UUID(0L, 0L));
   }

   public static List<GuildReputationShopService.ShopEntry> entriesForRank(String rank, long gameTime, UUID playerId) {
      return entriesForRank(rank, gameTime, playerId, 0L);
   }

   public static List<GuildReputationShopService.ShopEntry> entriesForRank(String rank, long gameTime, UUID playerId, long refreshNonce) {
      String normalizedRank = GuildLevelData.normalizeRank(rank);
      List<GuildReputationShopService.ShopEntry> pool = GuildReputationShopDefinitions.poolForRank(normalizedRank);
      if (pool.size() <= 6) {
         return pool;
      }

      List<GuildReputationShopService.ShopEntry> shuffled = new ArrayList<>(pool);
      Collections.shuffle(shuffled, new Random(shopSeed(normalizedRank, refreshCycle(gameTime), refreshNonce, playerId)));
      List<GuildReputationShopService.ShopEntry> visible = new ArrayList<>();
      Set<String> usedGroups = new HashSet<>();

      for (GuildReputationShopService.ShopEntry entry : shuffled) {
         if (entry.uniqueGroup().isEmpty() || usedGroups.add(entry.uniqueGroup())) {
            visible.add(entry);
            if (visible.size() >= 6) {
               break;
            }
         }
      }

      return List.copyOf(visible);
   }

   public static List<GuildReputationShopService.ShopEntry> entriesForPlayer(ServerPlayer player, String rank, long gameTime, long refreshNonce) {
      if (player == null) {
         return entriesForRank(rank, gameTime, new UUID(0L, 0L), refreshNonce);
      }

      String normalizedRank = GuildLevelData.normalizeRank(rank);
      List<GuildReputationShopService.ShopEntry> entries = entriesForRank(normalizedRank, gameTime, player.getUUID(), refreshNonce);
      List<GuildReputationShopService.ShopEntry> resolvedEntries = new ArrayList<>(entries.size());

      for (int slot = 0; slot < entries.size(); slot++) {
         GuildReputationShopService.ShopEntry entry = entries.get(slot);
         if (entry.item().equals(MAGIC_TOME)) {
            resolvedEntries.add(entry.withMagicTomeSkill(selectMagicTomeSkill(player, entry, normalizedRank, gameTime, refreshNonce, slot)));
         } else {
            resolvedEntries.add(entry);
         }
      }

      return List.copyOf(resolvedEntries);
   }

   public static GuildReputationShopService.BuyResult buy(ServerPlayer player, int rankLevel, int slot, int requestedMultiplier) {
      if (slot >= 0 && slot < 6) {
         int normalizedRankLevel = Math.max(1, Math.min(GuildLevelData.MAX_LEVEL, rankLevel));
         if (((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex() < normalizedRankLevel) {
            player.displayClientMessage(Component.translatable("screen.tensura_guild.shop.locked"), true);
            return GuildReputationShopService.BuyResult.FAILED;
         }

         long gameTime = player.level().getGameTime();
         long refreshNonce = GuildWorldData.get(player.server).shopRefreshNonce();
         long refreshCycle = effectiveRefreshCycle(gameTime, refreshNonce);
         List<GuildReputationShopService.ShopEntry> entries = entriesForPlayer(player, GuildLevelData.rankForLevel(normalizedRankLevel), gameTime, refreshNonce);
         if (slot >= entries.size()) {
            return GuildReputationShopService.BuyResult.FAILED;
         }

         GuildReputationShopService.ShopEntry entry = entries.get(slot);
         Item item = (Item)BuiltInRegistries.ITEM.get(entry.item());
         if (item != Items.AIR && entry.cost() > 0 && entry.count() > 0) {
            String purchaseKey = purchaseKey(normalizedRankLevel, refreshCycle, slot, entry);
            int purchasedCount = Math.max(0, ((Map)player.getData(GuildAttachments.REPUTATION_SHOP_PURCHASES)).getOrDefault(purchaseKey, 0));
            int multiplier = effectiveMultiplier(entry, purchasedCount, requestedMultiplier);
            if (multiplier <= 0) {
               return GuildReputationShopService.BuyResult.LIMIT_REACHED;
            }

            int affordableMultiplier = coins(player) / entry.cost();
            multiplier = Math.min(multiplier, affordableMultiplier);
            if (multiplier <= 0) {
               player.displayClientMessage(Component.translatable("screen.tensura_guild.shop.not_enough_coins"), true);
               return GuildReputationShopService.BuyResult.FAILED;
            }

            int totalCost = safeMultiply(entry.cost(), multiplier);
            int totalCount = safeMultiply(entry.count(), multiplier);
            if (coins(player) < totalCost) {
               player.displayClientMessage(Component.translatable("screen.tensura_guild.shop.not_enough_coins"), true);
               return GuildReputationShopService.BuyResult.FAILED;
            }

            ItemStack stack = createPurchaseStack(player, entry, totalCount);
            if (stack.isEmpty()) {
               return GuildReputationShopService.BuyResult.FAILED;
            }

            player.setData(GuildAttachments.REPUTATION_COINS, coins(player) - totalCost);
            player.syncData(GuildAttachments.REPUTATION_COINS);
            if (!player.getInventory().add(stack)) {
               player.drop(stack, false);
            }

            Map<String, Integer> purchases = new HashMap<>((Map<? extends String, ? extends Integer>)player.getData(GuildAttachments.REPUTATION_SHOP_PURCHASES));
            purchases.entrySet()
               .removeIf(
                  entrySet -> entrySet.getKey().startsWith(normalizedRankLevel + ":")
                     && !entrySet.getKey().startsWith(normalizedRankLevel + ":" + refreshCycle + ":")
               );
            purchases.put(purchaseKey, purchasedCount + totalCount);
            player.setData(GuildAttachments.REPUTATION_SHOP_PURCHASES, purchases);
            return GuildReputationShopService.BuyResult.SUCCESS;
         } else {
            return GuildReputationShopService.BuyResult.FAILED;
         }
      } else {
         return GuildReputationShopService.BuyResult.FAILED;
      }
   }

   public static List<Integer> purchasedCounts(
      ServerPlayer player, int rankLevel, List<GuildReputationShopService.ShopEntry> entries, long gameTime, long refreshNonce
   ) {
      if (player != null && entries != null && !entries.isEmpty()) {
         int normalizedRankLevel = Math.max(1, Math.min(GuildLevelData.MAX_LEVEL, rankLevel));
         long refreshCycle = effectiveRefreshCycle(gameTime, refreshNonce);
         Map<String, Integer> purchases = (Map<String, Integer>)player.getData(GuildAttachments.REPUTATION_SHOP_PURCHASES);
         List<Integer> counts = new ArrayList<>();

         for (int i = 0; i < entries.size(); i++) {
            counts.add(Math.max(0, purchases.getOrDefault(purchaseKey(normalizedRankLevel, refreshCycle, i, entries.get(i)), 0)));
         }

         return List.copyOf(counts);
      } else {
         return List.of();
      }
   }

   public static int effectiveMultiplier(GuildReputationShopService.ShopEntry entry, int purchasedCount, int requestedMultiplier) {
      if (entry != null && entry.count() > 0) {
         int requested = Math.max(1, Math.min(10, requestedMultiplier));
         int remaining = Math.max(0, entry.maxCount() - Math.max(0, purchasedCount));
         int maxByLimit = remaining / entry.count();
         return Math.max(0, Math.min(requested, maxByLimit));
      } else {
         return 0;
      }
   }

   private static int safeMultiply(int value, int multiplier) {
      long result = (long)Math.max(0, value) * Math.max(0, multiplier);
      return (int)Math.min(2147483647L, result);
   }

   private static ItemStack createPurchaseStack(ServerPlayer player, GuildReputationShopService.ShopEntry entry, int totalCount) {
      if (entry.item().equals(MAGIC_TOME)) {
         ItemStack tome = entry.magicTomeSkill()
            .map(GuildReputationShopService::createMagicTomeForSkill)
            .orElseGet(() -> createRandomMagicTome(player, entry.magicTomePool()));
         if (!tome.isEmpty()) {
            tome.setCount(Math.max(1, totalCount));
            return tome;
         }
      }

      ResourceLocation resultItem = randomResultItem(player, entry);
      Item item = (Item)BuiltInRegistries.ITEM.get(resultItem);
      return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item, totalCount);
   }

   private static ResourceLocation randomResultItem(ServerPlayer player, GuildReputationShopService.ShopEntry entry) {
      List<ResourceLocation> pool = entry.resultPool();
      if (pool.isEmpty()) {
         return entry.item();
      }

      List<ResourceLocation> loadedItems = pool.stream().filter(itemId -> BuiltInRegistries.ITEM.get(itemId) != Items.AIR).toList();
      return loadedItems.isEmpty() ? entry.item() : loadedItems.get(player.getRandom().nextInt(loadedItems.size()));
   }

   private static Optional<ResourceLocation> selectMagicTomeSkill(
      ServerPlayer player, GuildReputationShopService.ShopEntry entry, String rank, long gameTime, long refreshNonce, int slot
   ) {
      List<Holder<ManasSkill>> skills = magicTomeChoices(player, entry.magicTomePool());
      if (skills.isEmpty()) {
         return Optional.empty();
      }

      long seed = shopSeed(rank, refreshCycle(gameTime), refreshNonce, player.getUUID());
      seed = seed * 31L + slot;
      seed = seed * 31L + entry.item().hashCode();
      seed = seed * 31L + entry.magicTomePool().hashCode();
      Holder<ManasSkill> skill = skills.get(new Random(seed).nextInt(skills.size()));
      return skill.unwrapKey().map(key -> key.location()).or(() -> Optional.ofNullable(((ManasSkill)skill.value()).getRegistryName()));
   }

   private static ItemStack createRandomMagicTome(ServerPlayer player, String magicTomePool) {
      return Optional.of(magicTomeChoices(player, magicTomePool))
         .filter(skills -> !skills.isEmpty())
         .map(skills -> MagicTomeItem.createForMagic((ManasSkill)skills.get(player.getRandom().nextInt(skills.size())).value()))
         .orElseGet(() -> new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(MAGIC_TOME)));
   }

   private static ItemStack createMagicTomeForSkill(ResourceLocation skillId) {
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
      return skill == null ? new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(MAGIC_TOME)) : MagicTomeItem.createForMagic(skill);
   }

   private static List<Holder<ManasSkill>> magicTomeChoices(ServerPlayer player, String magicTomePool) {
      return player.registryAccess()
         .registryOrThrow(SkillAPI.getSkillRegistry().key())
         .getTag("all".equals(magicTomePool) ? TensuraSkillTags.MAGIC : TensuraSkillTags.FOUND_IN_TOME)
         .<Stream>map(HolderSet::stream)
         .map(stream -> stream.filter(holder -> isAllowedMagicTome((Holder<ManasSkill>)holder, magicTomePool)).toList())
         .orElse(List.of());
   }

   private static boolean isAllowedMagicTome(Holder<ManasSkill> holder, String magicTomePool) {
      return "all".equals(magicTomePool)
         ? true
         : !holder.is(TensuraSkillTags.SUMMONING_MAGIC) && holder.unwrapKey().map(key -> !EXCLUDED_MAGIC_TOMES.contains(key.location())).orElse(true);
   }

   private static void awardCoinsForRank(ServerPlayer player, int rankLevel, GuildTaskRewardPenalty penalty) {
      String rank = GuildLevelData.rankForLevel(rankLevel);
      GuildReputationShopService.RewardRange range = REWARD_RANGES.getOrDefault(rank, REWARD_RANGES.get("F"));
      int amount = (penalty == null ? GuildTaskRewardPenalty.NONE : penalty).adjustCurrencyAmount(range.roll(player));
      addCoins(player, amount);
      player.displayClientMessage(Component.translatable("screen.tensura_guild.shop.coins_reward", new Object[]{amount}), true);
   }

   public static long refreshPeriodTicks() {
      return Math.max(1, GuildConfig.shopRefreshDays()) * 24000L;
   }

   public static long refreshCycle(long gameTime) {
      return Math.max(0L, gameTime) / refreshPeriodTicks();
   }

   private static long effectiveRefreshCycle(long gameTime, long refreshNonce) {
      return refreshCycle(gameTime) + Math.max(0L, refreshNonce);
   }

   public static long nextRefreshGameTime(long gameTime) {
      return (refreshCycle(gameTime) + 1L) * refreshPeriodTicks();
   }

   public static int refreshDaysRemaining(long gameTime) {
      long periodTicks = refreshPeriodTicks();
      long ticksIntoCycle = Math.max(0L, gameTime) % periodTicks;
      long ticksRemaining = periodTicks - ticksIntoCycle;
      return (int)Math.max(1L, (ticksRemaining + 24000L - 1L) / 24000L);
   }

   private static String purchaseKey(int rankLevel, long refreshCycle, int slot, GuildReputationShopService.ShopEntry entry) {
      return rankLevel + ":" + refreshCycle + ":" + slot + ":" + entry.item();
   }

   private static long shopSeed(String rank, long refreshCycle, long refreshNonce, UUID playerId) {
      UUID normalizedPlayerId = playerId == null ? new UUID(0L, 0L) : playerId;
      long seed = 1831565813L;
      seed = seed * 31L + rank.hashCode();
      seed = seed * 31L + refreshCycle;
      seed = seed * 31L + refreshNonce;
      seed = seed * 31L + normalizedPlayerId.getMostSignificantBits();
      return seed * 31L + normalizedPlayerId.getLeastSignificantBits();
   }

   public enum BuyResult {
      SUCCESS,
      LIMIT_REACHED,
      FAILED;
   }

   private record RewardRange(int min, int max) {
      private int roll(ServerPlayer player) {
         int low = Math.max(0, Math.min(this.min, this.max));
         int high = Math.max(low, Math.max(this.min, this.max));
         return low + player.getRandom().nextInt(high - low + 1);
      }
   }

   public record ShopEntry(
      ResourceLocation item,
      int cost,
      int count,
      int maxCount,
      List<ResourceLocation> resultPool,
      String displayName,
      String uniqueGroup,
      String magicTomePool,
      Optional<ResourceLocation> magicTomeSkill
   ) {
      private static final StreamCodec<ByteBuf, List<ResourceLocation>> RESOURCE_LOCATION_LIST_STREAM_CODEC = ResourceLocation.STREAM_CODEC
         .apply(ByteBufCodecs.list());
      private static final StreamCodec<ByteBuf, Optional<ResourceLocation>> OPTIONAL_RESOURCE_LOCATION_STREAM_CODEC = ByteBufCodecs.optional(
         ResourceLocation.STREAM_CODEC
      );
      public static final StreamCodec<ByteBuf, GuildReputationShopService.ShopEntry> STREAM_CODEC = new StreamCodec<ByteBuf, GuildReputationShopService.ShopEntry>() {
         public GuildReputationShopService.ShopEntry decode(ByteBuf buffer) {
            ResourceLocation item = (ResourceLocation)ResourceLocation.STREAM_CODEC.decode(buffer);
            int cost = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            int count = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            int maxCount = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            List<ResourceLocation> resultPool = (List<ResourceLocation>)GuildReputationShopService.ShopEntry.RESOURCE_LOCATION_LIST_STREAM_CODEC.decode(buffer);
            String displayName = (String)ByteBufCodecs.STRING_UTF8.decode(buffer);
            String uniqueGroup = (String)ByteBufCodecs.STRING_UTF8.decode(buffer);
            String magicTomePool = (String)ByteBufCodecs.STRING_UTF8.decode(buffer);
            Optional<ResourceLocation> magicTomeSkill = (Optional<ResourceLocation>)GuildReputationShopService.ShopEntry.OPTIONAL_RESOURCE_LOCATION_STREAM_CODEC
               .decode(buffer);
            return new GuildReputationShopService.ShopEntry(item, cost, count, maxCount, resultPool, displayName, uniqueGroup, magicTomePool, magicTomeSkill);
         }

         public void encode(ByteBuf buffer, GuildReputationShopService.ShopEntry entry) {
            ResourceLocation.STREAM_CODEC.encode(buffer, entry.item());
            ByteBufCodecs.VAR_INT.encode(buffer, entry.cost());
            ByteBufCodecs.VAR_INT.encode(buffer, entry.count());
            ByteBufCodecs.VAR_INT.encode(buffer, entry.maxCount());
            GuildReputationShopService.ShopEntry.RESOURCE_LOCATION_LIST_STREAM_CODEC.encode(buffer, entry.resultPool());
            ByteBufCodecs.STRING_UTF8.encode(buffer, entry.displayName());
            ByteBufCodecs.STRING_UTF8.encode(buffer, entry.uniqueGroup());
            ByteBufCodecs.STRING_UTF8.encode(buffer, entry.magicTomePool());
            GuildReputationShopService.ShopEntry.OPTIONAL_RESOURCE_LOCATION_STREAM_CODEC.encode(buffer, entry.magicTomeSkill());
         }
      };

      public ShopEntry {
         resultPool = resultPool == null ? List.of() : List.copyOf(resultPool);
         displayName = displayName == null ? "" : displayName;
         uniqueGroup = uniqueGroup == null ? "" : uniqueGroup;
         magicTomePool = magicTomePool == null ? "" : magicTomePool;
         magicTomeSkill = magicTomeSkill == null ? Optional.empty() : magicTomeSkill;
      }

      public ShopEntry(ResourceLocation item, int cost, int count, int maxCount) {
         this(item, cost, count, maxCount, List.of(), "", "", "", Optional.empty());
      }

      public GuildReputationShopService.ShopEntry withMagicTomeSkill(Optional<ResourceLocation> magicTomeSkill) {
         return new GuildReputationShopService.ShopEntry(
            this.item, this.cost, this.count, this.maxCount, this.resultPool, this.displayName, this.uniqueGroup, this.magicTomePool, magicTomeSkill
         );
      }
   }
}
