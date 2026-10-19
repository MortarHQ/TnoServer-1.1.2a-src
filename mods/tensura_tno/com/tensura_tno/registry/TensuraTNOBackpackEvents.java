package com.tensura_tno.registry;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

@EventBusSubscriber(modid = "tensura_tno", bus = Bus.MOD)
public final class TensuraTNOBackpackEvents {
   private TensuraTNOBackpackEvents() {
   }

   @SubscribeEvent
   public static void onCommonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         Set<Block> validBlocks = new HashSet<>(((BlockEntityType)ModBlocks.BACKPACK_TILE_TYPE.get()).validBlocks);
         validBlocks.add((Block)TensuraTNOBlocks.LOW_MAGISTEEL_BACKPACK.get());
         validBlocks.add((Block)TensuraTNOBlocks.HIGH_MAGISTEEL_BACKPACK.get());
         validBlocks.add((Block)TensuraTNOBlocks.PURE_MAGISTEEL_BACKPACK.get());
         validBlocks.add((Block)TensuraTNOBlocks.ADAMANTITE_BACKPACK.get());
         validBlocks.add((Block)TensuraTNOBlocks.HIHIIROKANE_BACKPACK.get());
         ((BlockEntityType)ModBlocks.BACKPACK_TILE_TYPE.get()).validBlocks = validBlocks;
      });
   }

   @SubscribeEvent
   public static void addToSophisticatedBackpacksTab(BuildCreativeModeTabContentsEvent event) {
      if (event.getTab().equals(ModItems.CREATIVE_TAB.get())) {
         event.accept((ItemLike)TensuraTNOBackpacks.LOW_MAGISTEEL_BACKPACK.get());
         event.accept((ItemLike)TensuraTNOBackpacks.HIGH_MAGISTEEL_BACKPACK.get());
         event.accept((ItemLike)TensuraTNOBackpacks.PURE_MAGISTEEL_BACKPACK.get());
         event.accept((ItemLike)TensuraTNOBackpacks.ADAMANTITE_BACKPACK.get());
         event.accept((ItemLike)TensuraTNOBackpacks.HIHIIROKANE_BACKPACK.get());
      }
   }
}
