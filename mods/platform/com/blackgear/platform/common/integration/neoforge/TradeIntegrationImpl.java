package com.blackgear.platform.common.integration.neoforge;

import com.blackgear.platform.common.integration.TradeIntegration;
import com.blackgear.platform.common.integration.VillagerLevel;
import com.blackgear.platform.core.util.EventBus;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class TradeIntegrationImpl {
   public static void registerVillagerTrades(Consumer<TradeIntegration.Event> listener) {
      listener.accept(new TradeIntegration.Event() {
         @Override
         public void registerTrade(VillagerProfession profession, VillagerLevel level, ItemListing... trades) {
            EventBus.get(EventBus.LOADER).addListener(event -> {
               if (event.getType() == profession) {
                  ((List)event.getTrades().computeIfAbsent(level.getValue(), trade -> NonNullList.create())).addAll(List.of(trades));
               }
            });
         }

         @Override
         public void registerWandererTrade(boolean rare, ItemListing... trades) {
            EventBus.get(EventBus.LOADER).addListener(event -> {
               if (rare) {
                  event.getRareTrades().addAll(List.of(trades));
               } else {
                  event.getGenericTrades().addAll(List.of(trades));
               }
            });
         }
      });
   }
}
