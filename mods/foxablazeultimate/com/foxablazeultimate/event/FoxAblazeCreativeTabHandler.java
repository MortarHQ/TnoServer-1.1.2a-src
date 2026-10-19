package com.foxablazeultimate.event;

import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = "foxablazeultimate", bus = Bus.MOD)
public final class FoxAblazeCreativeTabHandler {
   private FoxAblazeCreativeTabHandler() {
   }

   @SubscribeEvent
   public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
         event.accept((ItemLike)FoxAblazeUltimateItems.WISDOM_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.GLUTTONY_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.COVENANT_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.WRATH_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.HARVEST_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.TIME_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.FLAME_CRYSTAL.get());
         event.accept((ItemLike)FoxAblazeUltimateItems.VOID_CRYSTAL.get());
      }
   }
}
