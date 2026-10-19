package org.crypticdev.stextras.client.loadingScreen.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.imageio.ImageIO;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.client.loadingScreen.util.GifDecoder;

public class GifTextureRenderer {
   private final List<ResourceLocation> frameLocations = new ArrayList<>();
   private int frameCount;
   private int currentFrame;
   private final Minecraft mc;
   private int tick = 0;
   private final String textureBaseID;
   private int imageWidth;
   private int imageHeight;
   private int FRAME_RATE = 10;
   private boolean loadFailed = false;

   public GifTextureRenderer(ResourceLocation gifLocation, Minecraft minecraft) {
      this.mc = minecraft;
      this.textureBaseID = "gif_" + Math.abs(new Random().nextInt());

      try (InputStream input = this.getStream(gifLocation)) {
         GifDecoder gifDecoder = new GifDecoder();
         gifDecoder.read(input);
         this.imageWidth = gifDecoder.getFrameSize().width;
         this.imageHeight = gifDecoder.getFrameSize().height;
         if (this.imageWidth <= 0 || this.imageHeight <= 0) {
            throw new IOException("Invalid GIF size: " + this.imageWidth + "x" + this.imageHeight);
         }

         this.frameCount = gifDecoder.getFrameCount();
         this.currentFrame = 0;

         for (int i = 0; i < this.frameCount; i++) {
            BufferedImage frame = gifDecoder.getFrame(i);
            if (frame != null) {
               NativeImage nativeImage = convertToNativeImage(frame);
               DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
               ResourceLocation frameLoc = ResourceLocation.parse(this.textureBaseID + "_frame_" + i);
               this.mc.getTextureManager().register(frameLoc, dynamicTexture);
               this.frameLocations.add(frameLoc);
            }
         }

         this.frameCount = this.frameLocations.size();
         Stextras.LOGGER.info("Loaded GIF {} with {} frames. Size: {}x{}", new Object[]{gifLocation, this.frameCount, this.imageWidth, this.imageHeight});
      } catch (Exception e) {
         Stextras.LOGGER.error("CRITICAL ERROR: Failed to decode gif {}", gifLocation, e);
         this.frameCount = 0;
         this.currentFrame = 0;
         this.loadFailed = true;
      }
   }

   private GifTextureRenderer(GifTextureRenderer.DecodedGif decodedGif, Minecraft minecraft) {
      this.mc = minecraft;
      this.textureBaseID = "gif_" + Math.abs(new Random().nextInt());
      this.imageWidth = decodedGif.imageWidth();
      this.imageHeight = decodedGif.imageHeight();

      for (int i = 0; i < decodedGif.frames().size(); i++) {
         NativeImage nativeImage = decodedGif.frames().get(i);
         DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
         ResourceLocation frameLoc = ResourceLocation.parse(this.textureBaseID + "_frame_" + i);
         this.mc.getTextureManager().register(frameLoc, dynamicTexture);
         this.frameLocations.add(frameLoc);
      }

      this.frameCount = this.frameLocations.size();
      this.currentFrame = 0;
      Stextras.LOGGER
         .info("Uploaded GIF {} with {} frames. Size: {}x{}", new Object[]{decodedGif.source(), this.frameCount, this.imageWidth, this.imageHeight});
   }

   public static CompletableFuture<GifTextureRenderer.DecodedGif> decodeAsync(ResourceLocation gifLocation, Minecraft minecraft) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            return decodeGif(gifLocation, minecraft);
         } catch (Exception e) {
            throw new CompletionException(e);
         }
      }, Util.backgroundExecutor());
   }

   public static GifTextureRenderer fromDecoded(GifTextureRenderer.DecodedGif decodedGif, Minecraft minecraft) {
      return new GifTextureRenderer(decodedGif, minecraft);
   }

   private InputStream getStream(ResourceLocation location) throws IOException {
      return openStream(this.mc, location);
   }

   private static InputStream openStream(Minecraft mc, ResourceLocation location) throws IOException {
      Optional<Resource> res = mc.getResourceManager().getResource(location);
      if (res.isPresent()) {
         return res.get().open();
      }

      String manualPath = "/assets/" + location.getNamespace() + "/" + location.getPath();
      InputStream stream = GifTextureRenderer.class.getResourceAsStream(manualPath);
      if (stream == null) {
         throw new IOException("Could not find resource via Manager OR ClassLoader: " + manualPath);
      }

      Stextras.LOGGER.info("Found GIF via Direct ClassLoader (Early Load): " + manualPath);
      return stream;
   }

   private static GifTextureRenderer.DecodedGif decodeGif(ResourceLocation gifLocation, Minecraft minecraft) throws IOException {
      try (InputStream input = openStream(minecraft, gifLocation)) {
         GifDecoder gifDecoder = new GifDecoder();
         gifDecoder.read(input);
         int width = gifDecoder.getFrameSize().width;
         int height = gifDecoder.getFrameSize().height;
         if (width <= 0 || height <= 0) {
            throw new IOException("Invalid GIF size: " + width + "x" + height);
         }

         List<NativeImage> frames = new ArrayList<>();

         for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
            BufferedImage frame = gifDecoder.getFrame(i);
            if (frame != null) {
               frames.add(convertToNativeImage(frame));
            }
         }

         if (frames.isEmpty()) {
            throw new IOException("GIF has no readable frames: " + gifLocation);
         } else {
            return new GifTextureRenderer.DecodedGif(gifLocation, width, height, List.copyOf(frames));
         }
      }
   }

   public void registerFrames() {
   }

   public void renderNextFrame(GuiGraphics gfx, int screenWidth, int screenHeight, float alpha) {
      if (!this.loadFailed && this.frameCount != 0) {
         this.tick++;
         if (this.tick >= this.FRAME_RATE) {
            this.currentFrame = (this.currentFrame + 1) % this.frameCount;
            this.tick = 0;
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
         ResourceLocation location = this.frameLocations.get(this.currentFrame);
         gfx.blit(location, 0, 0, screenWidth, screenHeight, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         RenderSystem.defaultBlendFunc();
      } else {
         gfx.fill(0, 0, screenWidth, screenHeight, -65536);
      }
   }

   public void renderNextFrame(GuiGraphics gfx, int x, int y, int width, int height, float alpha, int clip) {
      if (!this.loadFailed && this.frameCount != 0) {
         this.tick++;
         if (this.tick >= this.FRAME_RATE) {
            this.currentFrame = (this.currentFrame + 1) % this.frameCount;
            this.tick = 0;
         }

         RenderSystem.enableBlend();
         RenderSystem.blendEquation(32774);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
         ResourceLocation location = this.frameLocations.get(this.currentFrame);
         gfx.blit(location, x, y, width, height, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private static NativeImage convertToNativeImage(BufferedImage bufferedImage) throws IOException {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", os);

      try (InputStream is = new ByteArrayInputStream(os.toByteArray())) {
         return NativeImage.read(is);
      }
   }

   public void unloadAll() {
      for (ResourceLocation loc : this.frameLocations) {
         this.mc.getTextureManager().release(loc);
      }

      this.frameLocations.clear();
   }

   public record DecodedGif(ResourceLocation source, int imageWidth, int imageHeight, List<NativeImage> frames) {
   }
}
