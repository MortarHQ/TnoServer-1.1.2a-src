package com.foxablazeultimate.client;

import com.foxablazeultimate.client.render.AzathothArmLayer;
import com.foxablazeultimate.client.render.CapturedEntityRenderer;
import com.foxablazeultimate.client.render.FoxAblazeRenderTypes;
import com.foxablazeultimate.client.render.HellSniperBeamRenderer;
import com.foxablazeultimate.client.render.HellSniperCircleRenderer;
import com.foxablazeultimate.client.render.YogTimeStopWaveRenderer;
import com.foxablazeultimate.registry.FoxAblazeUltimateEntityTypes;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import io.github.manasmods.tensura.client.entity.misc.GluttonyMistRenderer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderBuffersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = "foxablazeultimate", bus = Bus.MOD, value = Dist.CLIENT)
public final class FoxAblazeUltimateModClient {
   private static final ResourceLocation VELDORA_BLACK_SLASH_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "foxablazeultimate", "textures/entity/veldora_black_slash.png"
   );
   private static CapturedEntityRenderer capturedEntityRendererInstance;

   private FoxAblazeUltimateModClient() {
   }

   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(
         (EntityType)FoxAblazeUltimateEntityTypes.VELDORA_BLACK_MIST.get(), context -> new GluttonyMistRenderer(context, VELDORA_BLACK_SLASH_TEXTURE)
      );
      event.registerEntityRenderer((EntityType)FoxAblazeUltimateEntityTypes.HELL_SNIPER_CIRCLE.get(), HellSniperCircleRenderer::new);
      event.registerEntityRenderer((EntityType)FoxAblazeUltimateEntityTypes.HELL_SNIPER_BEAM.get(), HellSniperBeamRenderer::new);
      event.registerEntityRenderer((EntityType)FoxAblazeUltimateEntityTypes.YOG_TIME_STOP_WAVE.get(), YogTimeStopWaveRenderer::new);
   }

   @SubscribeEvent
   public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
      event.registerRenderBuffer(FoxAblazeRenderTypes.VELGRYND_GLINT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.VELGRYND_GLINT_TRANSLUCENT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.VELGRYND_ENTITY_GLINT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.VELGRYND_ENTITY_GLINT_DIRECT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.AZATHOTH_GLINT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.AZATHOTH_GLINT_TRANSLUCENT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.AZATHOTH_ENTITY_GLINT);
      event.registerRenderBuffer(FoxAblazeRenderTypes.AZATHOTH_ENTITY_GLINT_DIRECT);
   }

   @SubscribeEvent
   public static void addAzathothArmLayers(AddLayers event) {
      for (Model skin : event.getSkins()) {
         PlayerRenderer renderer = (PlayerRenderer)event.getSkin(skin);
         if (renderer != null) {
            renderer.addLayer(new AzathothArmLayer(renderer));
         }
      }
   }

   @SubscribeEvent
   public static void registerShaders(RegisterShadersEvent event) throws IOException {
      FoxAblazeRenderTypes.registerVelgryndGlintShader(event);
   }

   @SubscribeEvent
   public static void registerGuiLayers(RegisterGuiLayersEvent event) {
      event.registerAboveAll(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "wisdom_crystal_easter_egg"), WisdomCrystalEasterEggOverlay::render);
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new IClientItemExtensions() {
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               if (FoxAblazeUltimateModClient.capturedEntityRendererInstance == null) {
                  Minecraft mc = Minecraft.getInstance();
                  FoxAblazeUltimateModClient.capturedEntityRendererInstance = new CapturedEntityRenderer(
                     mc.getBlockEntityRenderDispatcher(), mc.getEntityModels()
                  );
               }

               return FoxAblazeUltimateModClient.capturedEntityRendererInstance;
            }
         },
         new Item[]{(Item)FoxAblazeUltimateItems.CAPTURED_ENTITY.get()}
      );
   }
}
