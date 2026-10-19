package com.iafenvoy.iceandfire.util.trade;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TradeOfferInternals {
   private static final Logger LOGGER = LoggerFactory.getLogger("fabric-villager-api-v1");

   private TradeOfferInternals() {
   }

   public static synchronized void registerVillagerOffers(VillagerProfession profession, int level, Consumer<List<ItemListing>> factory) {
      Objects.requireNonNull(profession, "VillagerProfession may not be null.");
      registerOffers(VillagerTrades.TRADES.computeIfAbsent(profession, key -> new Int2ObjectOpenHashMap()), level, factory);
   }

   public static synchronized void registerWanderingTraderOffers(int level, Consumer<List<ItemListing>> factory) {
      registerOffers(VillagerTrades.WANDERING_TRADER_TRADES, level, factory);
   }

   private static void registerOffers(Int2ObjectMap<ItemListing[]> leveledTradeMap, int level, Consumer<List<ItemListing>> factory) {
      List<ItemListing> list = new ArrayList<>();
      factory.accept(list);
      ItemListing[] originalEntries = (ItemListing[])leveledTradeMap.computeIfAbsent(level, key -> new ItemListing[0]);
      ItemListing[] addedEntries = list.toArray(new ItemListing[0]);
      ItemListing[] allEntries = (ItemListing[])ArrayUtils.addAll(originalEntries, addedEntries);
      leveledTradeMap.put(level, allEntries);
   }

   public static void printRefreshOffersWarning() {
      Throwable loggingThrowable = new Throwable();
      LOGGER.warn("TradeOfferHelper#refreshOffers does not do anything, yet it was called! Stack trace:", loggingThrowable);
   }
}
