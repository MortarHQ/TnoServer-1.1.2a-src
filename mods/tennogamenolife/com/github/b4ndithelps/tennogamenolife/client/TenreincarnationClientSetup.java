package com.github.b4ndithelps.tennogamenolife.client;

import com.github.b4ndithelps.tennogamenolife.client.model.XiubiArmorModel;
import com.github.b4ndithelps.tennogamenolife.client.model.XiubiHaloModel;
import com.github.b4ndithelps.tennogamenolife.client.renderer.BlackRodRenderer;
import com.github.b4ndithelps.tennogamenolife.client.renderer.DragonRoarCircleRenderer;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT, bus = Bus.MOD)
public class TenreincarnationClientSetup {
   @SubscribeEvent
   public static void registerRenderers(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         EntityRenderers.register((EntityType)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), DragonRoarCircleRenderer::new);
         EntityRenderers.register((EntityType)TenreincarnationEntities.BLACK_ROD.get(), BlackRodRenderer::new);
      });
   }

   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(XiubiArmorModel.LAYER_LOCATION, XiubiArmorModel::createBodyLayer);
      event.registerLayerDefinition(XiubiHaloModel.LAYER_LOCATION, XiubiHaloModel::createBodyLayer);
   }

   @SubscribeEvent
   public static void registerAdditionalModels(RegisterAdditional event) {
      event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath("tennogamenolife", "item/sickle_gui")));
   }
}
