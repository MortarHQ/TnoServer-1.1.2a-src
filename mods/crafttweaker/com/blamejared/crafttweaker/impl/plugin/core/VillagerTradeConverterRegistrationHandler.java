package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IVillagerTradeRegistrationHandler;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

final class VillagerTradeConverterRegistrationHandler implements IVillagerTradeRegistrationHandler {
   private VillagerTradeConverterRegistrationHandler() {
   }

   static void gather(Consumer<IVillagerTradeRegistrationHandler> consumer) {
      VillagerTradeConverterRegistrationHandler handler = new VillagerTradeConverterRegistrationHandler();
      consumer.accept(handler);
   }

   @Override
   public <T extends ItemListing> void registerTradeConverter(Class<T> tradeClass, Function<T, CTTradeObject> tradeConverter) {
      CTVillagerTrades.TRADE_CONVERTER.put(tradeClass, tradeConverter);
   }
}
