package com.iafenvoy.iceandfire.util.trade;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public final class TradeOfferHelper {
   public static void registerVillagerOffers(VillagerProfession profession, int level, ItemListing... factories) {
      registerVillagerOffers(profession, level, List.of(factories));
   }

   public static void registerVillagerOffers(VillagerProfession profession, int level, List<ItemListing> factories) {
      registerVillagerOffers(profession, level, factory -> factory.addAll(factories));
   }

   public static void registerVillagerOffers(VillagerProfession profession, int level, Consumer<List<ItemListing>> factories) {
      TradeOfferInternals.registerVillagerOffers(profession, level, factories);
   }

   public static void registerWanderingTraderOffers(int level, ItemListing... factories) {
      registerWanderingTraderOffers(level, List.of(factories));
   }

   public static void registerWanderingTraderOffers(int level, List<ItemListing> factories) {
      registerWanderingTraderOffers(level, factory -> factory.addAll(factories));
   }

   public static void registerWanderingTraderOffers(int level, Consumer<List<ItemListing>> factory) {
      TradeOfferInternals.registerWanderingTraderOffers(level, factory);
   }

   @Deprecated(forRemoval = true)
   public static void refreshOffers() {
      TradeOfferInternals.printRefreshOffersWarning();
   }

   private TradeOfferHelper() {
   }
}
