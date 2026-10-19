package com.cupboard.event;

import com.cupboard.config.CupboardConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;

public class ClientEventHandler {
   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (!Minecraft.getInstance().hasSingleplayerServer()) {
         CupboardConfig.pollConfigs();
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onClientTick(Opening event) {
      if (event.getNewScreen() instanceof TitleScreen) {
         CupboardConfig.initloadAll();
      }
   }
}
