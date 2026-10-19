package com.tensura_tno.registry;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = "tensura_tno", bus = Bus.MOD)
public final class TensuraTNOItemTabEvents {
   private TensuraTNOItemTabEvents() {
   }

   @SubscribeEvent
   public static void addItemsToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
         event.accept((ItemLike)TensuraTNOItems.RIMURU_PLUSH.get());
         event.accept((ItemLike)TensuraTNOItems.LUMINOUS_PLUSH.get());
      }

      if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
         event.accept((ItemLike)TensuraTNOItems.REQUIRED_QUEST_RESET_SCROLL.get());
      }
   }
}
