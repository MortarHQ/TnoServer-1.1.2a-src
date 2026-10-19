package it.hurts.octostudios.octolib;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import it.hurts.octostudios.octolib.client.animation.TweenSystem;
import it.hurts.octostudios.octolib.module.particle.OctoRenderManager;

public final class OctoLibClient {
   public static long DELTA_NANOS;

   public static void init() {
      registerEvents();
      TweenSystem.init();
   }

   private static void registerEvents() {
      ClientTickEvent.CLIENT_LEVEL_PRE.register(OctoRenderManager::clientTick);
      ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(OctoRenderManager::worldExit);
   }

   public static double getDeltaTime() {
      return DELTA_NANOS / 1.0E9;
   }
}
