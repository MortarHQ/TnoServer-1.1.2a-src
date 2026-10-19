package com.github.b4ndithelps.tensuraenigmatic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(Dist.CLIENT)
public class TitleClearHandler {
   private static boolean wasInWorld;
   private static int clearCounter;

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      boolean currentlyInWorld = minecraft.level != null;
      if (wasInWorld && !currentlyInWorld) {
         clearCounter = 60;
      }

      if (clearCounter > 0) {
         clearTitles(minecraft);
         clearCounter--;
      }

      wasInWorld = currentlyInWorld;
   }

   private static void clearTitles(Minecraft minecraft) {
      minecraft.gui.setTitle(Component.empty());
      minecraft.gui.setSubtitle(Component.empty());
      minecraft.gui.setTimes(0, 0, 0);
   }
}
