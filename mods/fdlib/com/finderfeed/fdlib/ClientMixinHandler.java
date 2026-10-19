package com.finderfeed.fdlib;

import com.finderfeed.fdlib.systems.shake.ScreenShake;
import com.finderfeed.fdlib.systems.shake.ScreenShakeInstance;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT)
public class ClientMixinHandler {
   private static final List<ScreenShakeInstance> SHAKES = new ArrayList<>();
   private static boolean renderShake = true;

   @SubscribeEvent
   public static void clientTick(Post event) {
      Iterator<ScreenShakeInstance> iter = SHAKES.iterator();

      while (iter.hasNext()) {
         ScreenShakeInstance inst = iter.next();
         if (!inst.hasEnded()) {
            inst.tick();
         } else {
            iter.remove();
         }
      }
   }

   @SubscribeEvent
   public static void onLogoff(LoggingOut event) {
      SHAKES.clear();
   }

   public static void beforeLevel() {
      renderShake = true;
   }

   public static void beforeHand() {
      renderShake = false;
   }

   public static void bobHurt(PoseStack matrices, float pticks) {
      if (renderShake) {
         if (Minecraft.getInstance().level != null) {
            if (!Minecraft.getInstance().isPaused()) {
               if (Minecraft.getInstance().player != null) {
                  for (ScreenShakeInstance instance : SHAKES) {
                     ScreenShake shake = instance.shake;
                     int time = instance.currentTime;
                     shake.process(matrices, time, pticks);
                  }
               }
            }
         }
      }
   }

   public static void addShake(ScreenShake shake) {
      SHAKES.add(new ScreenShakeInstance(shake));
   }
}
