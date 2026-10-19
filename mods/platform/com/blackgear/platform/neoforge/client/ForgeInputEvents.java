package com.blackgear.platform.neoforge.client;

import com.blackgear.platform.client.event.screen.HudInteractions;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ScreenEvent.MouseScrolled.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.MouseScrolled.Pre;

@EventBusSubscriber(modid = "platform", bus = Bus.GAME, value = Dist.CLIENT)
public class ForgeInputEvents {
   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseScrollPre(Pre event) {
      if (HudInteractions.SCROLLING_PRE
         .invoker()
         .onScrolling(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getScrollDeltaX(), event.getScrollDeltaY())
         .isCancelled()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseScrollPost(Post event) {
      HudInteractions.SCROLLING_POST
         .invoker()
         .onScrolling(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getScrollDeltaX(), event.getScrollDeltaY());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseClickPre(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      if (HudInteractions.CLICKING_PRE
         .invoker()
         .onClicking(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton())
         .isCancelled()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseClickPost(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Post event) {
      HudInteractions.CLICKING_POST.invoker().onClicking(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton());
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseReleasePre(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Pre event) {
      if (HudInteractions.RELEASING_PRE
         .invoker()
         .onReleasing(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton())
         .isCancelled()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onMouseReleasePost(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Post event) {
      HudInteractions.RELEASING_POST.invoker().onReleasing(Minecraft.getInstance(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton());
   }
}
