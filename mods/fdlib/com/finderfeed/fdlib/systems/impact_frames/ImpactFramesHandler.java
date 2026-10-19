package com.finderfeed.fdlib.systems.impact_frames;

import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.init.FDConfigs;
import com.finderfeed.fdlib.systems.post_shaders.FDPostShaderInitializeEvent;
import com.finderfeed.fdlib.systems.post_shaders.FDRenderPostShaderEvent;
import com.mojang.blaze3d.pipeline.RenderTarget;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(value = Dist.CLIENT, modid = "fdlib")
public class ImpactFramesHandler {
   private static final Queue<ImpactFrame> impactFrames = new ArrayDeque<>();
   private static ImpactFrame currentImpactFrame = null;
   private static int currentTick = 0;
   public static PostChain impactFrameShader;

   @SubscribeEvent
   public static void registerShader(FDPostShaderInitializeEvent event) {
      event.registerPostChain((textureManager, resourceProvider, renderTarget) -> {
         try {
            return new PostChain(textureManager, resourceProvider, renderTarget, FDLib.location("shaders/post/impact_frame.json"));
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }, chain -> impactFrameShader = chain);
   }

   @SubscribeEvent
   public static void tick(Pre event) {
      manageImpactFrames();
   }

   @SubscribeEvent
   public static void renderImpactFrameShader(FDRenderPostShaderEvent.Level event) {
      if (isImpactFrameShaderActive()) {
         beforePostEffect();
         event.doDefaultShaderBeforeShaderStuff();
         impactFrameShader.process(event.getDeltaTracker().getGameTimeDeltaPartialTick(false));
      }
   }

   private static void manageImpactFrames() {
      if (impactFrameShader != null) {
         if (currentImpactFrame == null) {
            if (!impactFrames.isEmpty()) {
               currentImpactFrame = impactFrames.poll();
               manageImpactFrameShaderUniforms(currentImpactFrame);
               currentTick = 1;
            } else {
               currentTick = 0;
            }
         } else if (currentTick >= currentImpactFrame.getDuration()) {
            if (!impactFrames.isEmpty()) {
               currentImpactFrame = impactFrames.poll();
               manageImpactFrameShaderUniforms(currentImpactFrame);
               currentTick = 1;
            } else {
               currentImpactFrame = null;
               currentTick = 0;
            }
         } else {
            manageImpactFrameShaderUniforms(currentImpactFrame);
            currentTick++;
         }
      }
   }

   public static void addImpactFrame(ImpactFrame frame) {
      if (FDConfigs.CLIENTSIDE_CONFIG.get().impactFramesEnabled) {
         impactFrames.offer(frame);
      }
   }

   private static void manageImpactFrameShaderUniforms(ImpactFrame frame) {
      impactFrameShader.setUniform("treshhold", frame.getTreshhold());
      impactFrameShader.setUniform("treshholdLerp", frame.getTreshholdLerp());
      impactFrameShader.setUniform("invert", frame.isInverted() ? 1.0F : 0.0F);
   }

   public static void beforePostEffect() {
      if (isImpactFrameShaderActive()) {
         RenderTarget main = Minecraft.getInstance().getMainRenderTarget();
         main.bindRead();
         int width = GL11.glGetTexLevelParameteri(3553, 0, 4096);
         int height = GL11.glGetTexLevelParameteri(3553, 0, 4097);
         float[] pixel = new float[3];
         int hhalfW = width / 4;
         int hhalfH = height / 4;
         Vector2i[] samplePoints = new Vector2i[]{
            new Vector2i(hhalfW, hhalfH),
            new Vector2i(hhalfW * 2, hhalfH),
            new Vector2i(hhalfW * 3, hhalfH),
            new Vector2i(hhalfW, hhalfH * 2),
            new Vector2i(hhalfW * 2, hhalfH * 2),
            new Vector2i(hhalfW * 3, hhalfH * 2),
            new Vector2i(hhalfW, hhalfH * 3),
            new Vector2i(hhalfW * 2, hhalfH * 3),
            new Vector2i(hhalfW * 3, hhalfH * 3)
         };
         float maxGrayscale = 0.0F;

         for (Vector2i point : samplePoints) {
            GL11.glReadPixels(point.x, point.y, 1, 1, 6407, 5126, pixel);
            float grayscale = Math.max(pixel[0], Math.max(pixel[1], pixel[2]));
            maxGrayscale = Math.max(maxGrayscale, grayscale);
         }

         impactFrameShader.setUniform("maxEstimatedGrayscale", maxGrayscale);
      }
   }

   public static boolean isImpactFrameShaderActive() {
      return currentImpactFrame != null;
   }
}
