package team.lodestar.lodestone.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import net.neoforged.neoforge.event.GameShuttingDownEvent;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.handlers.ItemEventHandler;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.handlers.WorldEventRenderHandler;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.registry.client.LodestoneModels;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;
import team.lodestar.lodestone.systems.rendering.renderpass.RenderPassHandler;

@EventBusSubscriber(Dist.CLIENT)
public class ClientGameEvents {
   @SubscribeEvent
   public static void clientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != null) {
         if (minecraft.isPaused()) {
            return;
         }

         Camera camera = minecraft.gameRenderer.getMainCamera();
         WorldEventHandler.tick(level);
         ScreenshakeHandler.clientTick(level, camera);
         ScreenParticleHandler.tickParticles();
      }
   }

   @SubscribeEvent
   public static void cameraSetup(ComputeCameraAngles event) {
      ScreenshakeHandler.computeAngles(event);
   }

   @SubscribeEvent
   public static void addAttributeTooltips(AddAttributeTooltipsEvent event) {
      ItemEventHandler.addAttributeTooltips(event);
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void renderFog(RenderFog event) {
      LodestoneRenderHandler.cacheFogData(event);
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void fogColors(ComputeFogColor event) {
      LodestoneRenderHandler.cacheFogColors(event);
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void renderStages(RenderLevelStageEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      Camera camera = minecraft.gameRenderer.getMainCamera();
      float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
      PoseStack poseStack = event.getPoseStack();
      if (event.getStage().equals(Stage.AFTER_SKY)) {
         WorldEventRenderHandler.renderWorldEvents(minecraft.level, poseStack, camera, partial);
      }

      if (event.getStage().equals(Stage.AFTER_WEATHER)) {
         LodestoneRenderHandler.render();
      }
   }

   @SubscribeEvent
   public static void renderFrameEvent(net.neoforged.neoforge.client.event.RenderFrameEvent.Post event) {
      if (event != null) {
         ScreenParticleHandler.renderTick(event);
      }
   }

   @SubscribeEvent
   public static void shutdownEvent(GameShuttingDownEvent event) {
      LodestoneRenderSystem.wrap(() -> {
         LodestoneModels.cleanup();
         LodestoneRenderSystem.destroyBufferObjects();
         RenderPassHandler.close();
         LodestoneLib.LOGGER.info("Shutting down Lodestone");
      });
   }
}
