package cn.leolezury.eternalstarlight.neoforge.client.event;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = "eternal_starlight", value = Dist.CLIENT)
public class ClientEvents {
   @SubscribeEvent
   private static void onClientTick(Post event) {
      ESClientHandler.onClientTick();
   }

   @SubscribeEvent
   private static void onComputeCameraAngles(ComputeCameraAngles event) {
      Vec3 angle = ESClientHandler.onComputeCameraAngles(new Vec3(event.getPitch(), event.getYaw(), event.getRoll()));
      event.setPitch((float)angle.x);
      event.setYaw((float)angle.y);
   }

   @SubscribeEvent
   private static void onComputeFovModifier(ComputeFovModifierEvent event) {
      ESClientHandler.onComputeFovModifier(event.getFovModifier()).ifPresent(d -> event.setNewFovModifier((float)d));
   }

   @SubscribeEvent
   private static void onRenderFog(RenderFog event) {
      ESClientHandler.onRenderFog(event.getCamera(), event.getMode());
   }

   @SubscribeEvent
   private static void onRenderBossBar(BossEventProgress event) {
      if (ESClientHandler.renderBossBar(event.getGuiGraphics(), event.getBossEvent(), event.getX(), event.getY())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   private static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_ENTITIES) {
         ESClientHandler.onAfterRenderEntities(
            event.getLevelRenderer().renderBuffers.bufferSource(),
            event.getPoseStack(),
            event.getPartialTick()
               .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())
         );
      }

      if (event.getStage() == Stage.AFTER_PARTICLES) {
         ESClientHandler.onAfterRenderParticles();
      }

      if (event.getStage() == Stage.AFTER_WEATHER) {
         ESClientHandler.onAfterRenderWeather(
            event.getPartialTick()
               .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())
         );
      }

      if (event.getStage() == Stage.AFTER_LEVEL) {
         ESClientHandler.onAfterRenderLevel();
      }
   }

   @SubscribeEvent
   private static void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event) {
      if (!event.isCanceled()) {
         boolean allow = ESClientHandler.onRenderBlockOverlay(event.getPlayer(), event.getBlockState());
         if (!allow) {
            event.setCanceled(true);
         }
      }
   }
}
