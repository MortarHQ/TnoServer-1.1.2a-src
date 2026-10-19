package com.tensura_tno.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = "tensura_tno", bus = Bus.MOD)
public final class TensuraTNOFoodTabEvents {
   private static final ResourceLocation COOKERY_FOOD_TAB = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "cookery_food");
   private static final ResourceLocation COOKERY_MAIN_TAB = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "cookery_main");

   private TensuraTNOFoodTabEvents() {
   }

   @SubscribeEvent
   public static void addFoodsToKaleidoscopeTab(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey().location().equals(COOKERY_MAIN_TAB)) {
         event.accept((ItemLike)TensuraTNOItems.TNO_STOVE.get());
      } else if (event.getTabKey().location().equals(COOKERY_FOOD_TAB)) {
         event.accept((ItemLike)TensuraTNOItems.STORM_DAEMON_HIPOKUTE_SOUP.get());
         event.accept((ItemLike)TensuraTNOItems.MONSTER_MEAT_PLATTER.get());
         event.accept((ItemLike)TensuraTNOItems.SLIME_SAUCE_BUN.get());
         event.accept((ItemLike)TensuraTNOItems.BLADE_TIGER_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.COOKED_BLADE_TIGER_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.CHARYBDIS_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.COOKED_CHARYBDIS_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.ARMORSAURUS_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.COOKED_ARMORSAURUS_MINCED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.KNIGHT_SPIDER_LEG_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.COOKED_KNIGHT_SPIDER_LEG_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.CURED_MEAT.get());
         event.accept((ItemLike)TensuraTNOItems.SLICED_CURED_MEAT.get());
      }
   }
}
