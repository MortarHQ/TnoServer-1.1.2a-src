package com.cerbon.cerbons_api.neoforge.event;

import com.cerbon.cerbons_api.neoforge.attachment.saved_data.LevelEventScheduler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

@EventBusSubscriber(modid = "cerbons_api")
public class NeoForgeEvents {
   @SubscribeEvent
   public static void onPlayerLogging(LoggingIn event) {
      LevelEventScheduler.INSTANCE = new LevelEventScheduler();
   }

   @SubscribeEvent
   public static void onLevelTick(Post event) {
      LevelEventScheduler.get(event.getLevel()).updateEvents();
   }
}
