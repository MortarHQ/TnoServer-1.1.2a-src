package com.progwml6.ironchest.client;

import com.progwml6.ironchest.client.render.IronChestRenderer;
import com.progwml6.ironchest.client.screen.IronChestScreen;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "ironchest", value = Dist.CLIENT, bus = Bus.MOD)
public class IronChestsClientRegistration {
   public static final ModelLayerLocation IRON_CHEST = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ironchest", "iron_chest"), "main");

   @SubscribeEvent
   public static void registerLayer(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(IRON_CHEST, IronChestRenderer::createLayerDefinition);
   }

   @SubscribeEvent
   public static void registerScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)IronChestsMenuTypes.IRON_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.GOLD_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.DIAMOND_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.CRYSTAL_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.COPPER_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.OBSIDIAN_CHEST.get(), IronChestScreen::new);
      event.register((MenuType)IronChestsMenuTypes.DIRT_CHEST.get(), IronChestScreen::new);
   }

   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.IRON_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.GOLD_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.DIAMOND_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.COPPER_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.CRYSTAL_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.OBSIDIAN_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.DIRT_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_IRON_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_GOLD_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_DIAMOND_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_COPPER_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_CRYSTAL_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_OBSIDIAN_CHEST.get(), IronChestRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST.get(), IronChestRenderer::new);
   }
}
