package com.blackgear.platform.common.integration;

import com.blackgear.platform.common.integration.neoforge.TradeIntegrationImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class TradeIntegration {
   @ExpectPlatform
   @Transformed
   public static void registerVillagerTrades(Consumer<TradeIntegration.Event> listener) {
      TradeIntegrationImpl.registerVillagerTrades(listener);
   }

   public interface Event {
      void registerTrade(VillagerProfession var1, VillagerLevel var2, ItemListing... var3);

      void registerWandererTrade(boolean var1, ItemListing... var2);
   }
}
