package org.crypticdev.stextras.neoforge;

import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.crypticdev.stextras.client.render.WispRadarRenderer;

public final class StextrasNeoForgeClient {
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(StextrasNeoForgeClient::onRenderLevelStage);
      }
   }

   private static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         WispRadarRenderer.renderRadar(event.getCamera());
      }
   }

   private StextrasNeoForgeClient() {
   }
}
