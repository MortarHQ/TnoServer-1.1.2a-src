package com.illusivesoulworks.polymorph.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre;

public class ClientEventsListener {
   @SubscribeEvent
   public void tick(Post evt) {
      PolymorphClientEvents.tick();
   }

   @SubscribeEvent
   public void initScreen(net.neoforged.neoforge.client.event.ScreenEvent.Init.Post evt) {
      PolymorphClientEvents.initScreen(evt.getScreen());
   }

   @SubscribeEvent
   public void render(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post evt) {
      PolymorphClientEvents.render(evt.getScreen(), evt.getGuiGraphics(), evt.getMouseX(), evt.getMouseY(), evt.getPartialTick());
   }

   @SubscribeEvent
   public void mouseClick(Pre evt) {
      if (PolymorphClientEvents.mouseClick(evt.getScreen(), evt.getMouseX(), evt.getMouseY(), evt.getButton())) {
         evt.setCanceled(true);
      }
   }
}
