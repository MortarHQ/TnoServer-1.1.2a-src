package com.finderfeed.fdlib.systems.screen.screen_effect;

import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(bus = Bus.GAME, value = Dist.CLIENT, modid = "fdlib")
public class ScreenEffectOverlay implements Layer {
   private static final List<ScreenEffectInstance> screenEffects = new ArrayList<>();

   public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
      Window window = Minecraft.getInstance().getWindow();
      float width = window.getGuiScaledWidth();
      float height = window.getGuiScaledHeight();

      for (ScreenEffectInstance instance : screenEffects) {
         instance.effect.render(graphics, deltaTracker, instance.currentTime, width, height);
      }
   }

   @SubscribeEvent
   public static void tickEvent(Post event) {
      if (!Minecraft.getInstance().isPaused()) {
         Iterator<ScreenEffectInstance> screenEffectInstanceIterator = screenEffects.iterator();

         while (screenEffectInstanceIterator.hasNext()) {
            ScreenEffectInstance instance = screenEffectInstanceIterator.next();
            if (instance.isFinished()) {
               screenEffectInstanceIterator.remove();
            } else {
               instance.tick();
            }
         }
      }
   }

   public static List<ScreenEffectInstance> getScreenEffects() {
      return screenEffects;
   }

   public static void addScreenEffect(ScreenEffect effect) {
      screenEffects.add(new ScreenEffectInstance(effect));
   }
}
