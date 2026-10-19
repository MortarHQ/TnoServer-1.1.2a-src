package dev.ftbqlang.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "ftbqlang", value = Dist.CLIENT)
public final class ClientEvents {
   private ClientEvents() {
   }

   @SubscribeEvent
   public static void loggedIn(LoggingIn event) {
      QuestLanguageClient.reset();
   }

   @SubscribeEvent
   public static void loggedOut(LoggingOut event) {
      QuestLanguageClient.reset();
   }

   @SubscribeEvent
   public static void tick(Post event) {
      QuestLanguageClient.tick();
   }
}
