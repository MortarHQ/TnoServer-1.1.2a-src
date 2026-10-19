package com.finderfeed.fdlib.systems.post_shaders;

import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

@EventBusSubscriber(modid = "fdlib", value = Dist.CLIENT)
public class FDPostShadersHandler {
   public static boolean WAS_LOADED_ONCE = false;
   public static final List<PostChain> POST_SHADERS = new ArrayList<>();
   private static int width;
   private static int height;

   @SubscribeEvent
   public static void processPostShaders(Pre event) {
      resizeAllShadersIfNeeded();
   }

   private static void resizeAllShadersIfNeeded() {
      Window window = Minecraft.getInstance().getWindow();
      int width = window.getWidth();
      int height = window.getHeight();
      if (width != FDPostShadersHandler.width || height != FDPostShadersHandler.height) {
         for (PostChain postChain : POST_SHADERS) {
            postChain.resize(width, height);
         }

         FDPostShadersHandler.width = width;
         FDPostShadersHandler.height = height;
      }
   }
}
