package com.mortal_cultivation.event;

import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class VillagerTradeEventHandler {
   @SubscribeEvent
   public static void onVillagerTrades(VillagerTradesEvent event) {
      if (event.getType() == VillagerProfession.LIBRARIAN) {
         ((List)event.getTrades().get(2))
            .add(
               (trader, random) -> new MerchantOffer(
                  new ItemCost((ItemLike)CultivationItems.BREAK_AND_REBUILD_MANUAL.get(), 1), new ItemStack(Items.EMERALD, 5), 4, 10, 0.05F
               )
            );
      }
   }
}
