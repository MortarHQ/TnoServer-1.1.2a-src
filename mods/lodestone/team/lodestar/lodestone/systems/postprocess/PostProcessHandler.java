package team.lodestar.lodestone.systems.postprocess;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.GAME)
public class PostProcessHandler {
   private static final List<PostProcessor> instances = new ArrayList<>();
   private static boolean didCopyDepth = false;

   public static void addInstance(PostProcessor instance) {
      instances.add(instance);
   }

   public static void render() {
      instances.forEach(PostProcessor::applyPostProcess);
      Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
   }

   public static void copyDepthBuffer() {
      if (!didCopyDepth) {
         instances.forEach(PostProcessor::copyDepthBuffer);
         didCopyDepth = true;
      }
   }

   public static void resize(int width, int height) {
      instances.forEach(i -> i.resize(width, height));
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onWorldRenderLast(RenderLevelStageEvent event) {
      if (event.getStage().equals(Stage.AFTER_PARTICLES)) {
         PostProcessor.viewModelMatrix = RenderSystem.getModelViewMatrix();
      }

      if (event.getStage().equals(Stage.AFTER_LEVEL)) {
         copyDepthBuffer();
         render();
         didCopyDepth = false;
      }
   }
}
