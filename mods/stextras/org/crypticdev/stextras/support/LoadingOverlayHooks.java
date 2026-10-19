package org.crypticdev.stextras.support;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.client.config.STExtrasClientConfig;
import org.crypticdev.stextras.client.loadingScreen.loadingScreen;
import org.crypticdev.stextras.client.loadingScreen.texture.GifTextureRenderer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public final class LoadingOverlayHooks {
   private static final boolean BACKGROUND_IMAGE = true;
   private static final int BACKGROUND_COLOR = -16777216;
   private static final int LOGO_MOJANG = 0;
   private static final int LOGO_ASPECT_1TO1 = 1;
   private static final int LOGO_STYLE = 1;
   private static final int PB_VANILLA = 0;
   private static final int PB_CUSTOM = 1;
   private static final int PB_LOGO = 2;
   private static final int PB_BACKGROUND = 3;
   private static final int PROGRESS_BAR_TYPE = 1;
   private static final int MODE_STRETCH = 0;
   private static final int MODE_SLIDE = 1;
   private static final int CUSTOM_PROGRESS_BAR_MODE = 0;
   private static final boolean CUSTOM_PROGRESS_BAR_BACKGROUND = true;
   private static final int PROGRESS_BAR_COLOR = -1;
   private static final int PROGRESS_FRAME_COLOR = -5592406;
   private static ResourceLocation aspect1To1Texture;
   private static ResourceLocation mojangLogo;
   private static ResourceLocation customProgressBarTexture;
   private static ResourceLocation customProgressBarBackgroundTexture;
   private static ResourceLocation backgroundTexture;
   private static ResourceLocation animatedBackgroundTexture;
   private static GifTextureRenderer backgroundGifRenderer;
   private static GifTextureRenderer logoGifRenderer;
   private static CompletableFuture<GifTextureRenderer.DecodedGif> backgroundGifFuture;
   private static boolean backgroundGifFailed;

   private LoadingOverlayHooks() {
   }

   public static void injectTextures(Minecraft mc, CallbackInfo ci) {
      if (STExtrasClientConfig.isLoadingScreenEnabled(mc)) {
         releaseRenderer(backgroundGifRenderer);
         backgroundGifRenderer = null;
         backgroundGifFuture = null;
         backgroundGifFailed = false;
         aspect1To1Texture = loadingScreen.LOGO_LIGHT;
         mojangLogo = loadingScreen.MOJANGSTUDIOS;
         customProgressBarTexture = loadingScreen.PROGRESSBAR;
         customProgressBarBackgroundTexture = loadingScreen.PROGRESSBAR_BACKGROUND;
         backgroundTexture = loadingScreen.IMAGE;
         animatedBackgroundTexture = loadingScreen.IMAGE_ANIMATED;
         registerImmediateTexture(mc, mojangLogo);
         if (aspect1To1Texture.getPath().endsWith(".gif")) {
            logoGifRenderer = new GifTextureRenderer(aspect1To1Texture, mc);
            logoGifRenderer.registerFrames();
         } else {
            registerImmediateTexture(mc, aspect1To1Texture);
         }

         registerImmediateTexture(mc, backgroundTexture);
         if (shouldRenderAnimatedBackground(mc)) {
            startBackgroundGifLoad(mc);
         }

         registerImmediateTexture(mc, customProgressBarTexture);
         registerImmediateTexture(mc, customProgressBarBackgroundTexture);
         ci.cancel();
      }
   }

   public static void injectRender(LoadingOverlayHooks.Access self, GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      if (STExtrasClientConfig.isLoadingScreenEnabled(self.stextras$getMinecraft())) {
         ci.cancel();
         boolean renderAnimatedBackground = shouldRenderAnimatedBackground(self.stextras$getMinecraft());
         if (renderAnimatedBackground) {
            tryPromoteBackgroundGif(self.stextras$getMinecraft());
         }

         int width = gfx.guiWidth();
         int height = gfx.guiHeight();
         long millis = Util.getMillis();
         if (self.stextras$isFadeIn() && self.stextras$getFadeInStart() == -1L) {
            self.stextras$setFadeInStart(millis);
         }

         float fadeOutTime = self.stextras$getFadeOutStart() > -1L ? (float)(millis - self.stextras$getFadeOutStart()) / 1000.0F : -1.0F;
         float fadeInTime = self.stextras$getFadeInStart() > -1L ? (float)(millis - self.stextras$getFadeInStart()) / 500.0F : -1.0F;
         float timedAlpha;
         if (fadeOutTime >= 1.0F) {
            if (self.stextras$getMinecraft().screen != null) {
               self.stextras$getMinecraft().screen.render(gfx, mouseX, mouseY, partialTicks);
            }

            int alpha = Mth.ceil((1.0F - Mth.clamp(fadeOutTime - 1.0F, 0.0F, 1.0F)) * 255.0F);
            gfx.fill(RenderType.guiOverlay(), 0, 0, width, height, changeAlpha(getBGColor(renderAnimatedBackground), alpha));
            timedAlpha = 1.0F - Mth.clamp(fadeOutTime - 1.0F, 0.0F, 1.0F);
         } else if (self.stextras$isFadeIn()) {
            if (self.stextras$getMinecraft().screen != null && fadeInTime < 1.0F) {
               self.stextras$getMinecraft().screen.render(gfx, mouseX, mouseY, partialTicks);
            }

            int alpha = Mth.ceil(Mth.clamp(fadeInTime, 0.15, 1.0) * 255.0);
            gfx.fill(RenderType.guiOverlay(), 0, 0, width, height, changeAlpha(getBGColor(renderAnimatedBackground), alpha));
            timedAlpha = Mth.clamp(fadeInTime, 0.0F, 1.0F);
         } else {
            int color = getBGColor(renderAnimatedBackground);
            float r = (color >> 16 & 0xFF) / 255.0F;
            float g = (color >> 8 & 0xFF) / 255.0F;
            float b = (color & 0xFF) / 255.0F;
            GlStateManager._clearColor(r, g, b, 1.0F);
            GlStateManager._clear(16384, Minecraft.ON_OSX);
            timedAlpha = 1.0F;
         }

         if (backgroundGifRenderer != null) {
            backgroundGifRenderer.renderNextFrame(gfx, width, height, timedAlpha);
         } else {
            renderStaticBackground(gfx, width, height, timedAlpha);
         }

         int scaledWidth = (int)(width * 0.5);
         int scaledHeight = (int)(height * 0.5);
         double scale = Math.min(width * 0.75, height) * 0.25;
         int i1 = (int)(scale * 0.5);
         double d0 = scale * 4.0;
         int j1 = (int)(d0 * 0.5);
         int k1 = (int)(height * 0.8325);
         float actualProgress = self.stextras$getReload().getActualProgress();
         self.stextras$setCurrentProgress(Mth.clamp(self.stextras$getCurrentProgress() * 0.95F + actualProgress * 0.050000012F, 0.0F, 1.0F));
         if (fadeOutTime < 1.0F) {
            drawProgressBar(self, gfx, width / 2 - j1, k1 - 5, width / 2 + j1, k1 + 5, 1.0F - Mth.clamp(fadeOutTime, 0.0F, 1.0F));
         }

         renderLogo(gfx, timedAlpha);
         if (fadeOutTime >= 2.0F) {
            self.stextras$getMinecraft().setOverlay((Overlay)null);
         }

         if (self.stextras$getFadeOutStart() == -1L && self.stextras$getReload().isDone() && (!self.stextras$isFadeIn() || fadeInTime >= 2.0F)) {
            try {
               self.stextras$getReload().checkExceptions();
               self.stextras$getOnFinish().accept(Optional.empty());
            } catch (Throwable t) {
               self.stextras$getOnFinish().accept(Optional.of(t));
            }

            self.stextras$setFadeOutStart(Util.getMillis());
            if (self.stextras$getMinecraft().screen != null) {
               self.stextras$getMinecraft().screen.init(self.stextras$getMinecraft(), width, height);
            }
         }
      }
   }

   private static void drawProgressBar(LoadingOverlayHooks.Access self, GuiGraphics gfx, int x1, int y1, int x2, int y2, float opacity) {
      int progressWidth = Mth.ceil((x2 - x1 - 2) * self.stextras$getCurrentProgress());
      int regionWidth = x2 - x1;
      int h = (int)((x2 - x1) / 400.0F * 10.0F);
      int u = 0;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      gfx.blit(customProgressBarBackgroundTexture, x1, y1, x2 - x1, h, 0.0F, 0.0F, x2 - x1, h, x2 - x1, h);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      gfx.blit(customProgressBarTexture, x1, y1, progressWidth, h, u, 0.0F, regionWidth, h, x2 - x1, h);
   }

   private static void renderLogo(GuiGraphics gfx, float alpha) {
      int size = 100;
      int x = 20;
      int y = 20;
      if (logoGifRenderer != null) {
         logoGifRenderer.renderNextFrame(gfx, x, y, size, size, alpha, 512);
      } else {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
         gfx.blit(aspect1To1Texture, x, y, size, size, 0.0F, 0.0F, 512, 512, 512, 512);
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private static void renderBackgroundBar(LoadingOverlayHooks.Access self, GuiGraphics gfx, float opacity) {
      int maxX = self.stextras$getMinecraft().getWindow().getGuiScaledWidth();
      int maxY = self.stextras$getMinecraft().getWindow().getGuiScaledHeight();
      self.stextras$setLastWidth(Math.max(Math.round(self.stextras$getCurrentProgress() * maxX), self.stextras$getLastWidth()));
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
      gfx.blit(customProgressBarTexture, 0, 0, 0, 0.0F, 0.0F, self.stextras$getLastWidth(), maxY, maxX, maxY);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   private static void renderStaticBackground(GuiGraphics gfx, int width, int height, float alpha) {
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
      gfx.blit(backgroundTexture, 0, 0, 0, 0.0F, 0.0F, width, height, width, height);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
   }

   private static void startBackgroundGifLoad(Minecraft minecraft) {
      if (backgroundGifFuture == null && !backgroundGifFailed && animatedBackgroundTexture != null) {
         backgroundGifFuture = GifTextureRenderer.decodeAsync(animatedBackgroundTexture, minecraft);
      }
   }

   private static void tryPromoteBackgroundGif(Minecraft minecraft) {
      if (backgroundGifRenderer == null && backgroundGifFuture != null && backgroundGifFuture.isDone()) {
         try {
            backgroundGifRenderer = GifTextureRenderer.fromDecoded(backgroundGifFuture.join(), minecraft);
         } catch (CompletionException e) {
            backgroundGifFailed = true;
            Stextras.LOGGER.error("Failed to prepare animated loading background {}", animatedBackgroundTexture, e.getCause());
         } finally {
            backgroundGifFuture = null;
         }
      }
   }

   private static void releaseRenderer(GifTextureRenderer renderer) {
      if (renderer != null) {
         renderer.unloadAll();
      }
   }

   private static void registerImmediateTexture(Minecraft mc, ResourceLocation location) {
      try {
         Optional<Resource> resource = mc.getResourceManager().getResource(location);
         if (resource.isPresent()) {
            try (InputStream is = resource.get().open()) {
               NativeImage image = NativeImage.read(is);
               DynamicTexture dynamicTexture = new DynamicTexture(image);
               mc.getTextureManager().register(location, dynamicTexture);
            }
         } else {
            System.err.println("StExtras: Could not find texture " + location);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static int changeAlpha(int color, int alpha) {
      return color & 16777215 | alpha << 24;
   }

   private static boolean shouldRenderAnimatedBackground(Minecraft minecraft) {
      return STExtrasClientConfig.isAnimatedLoadingBackgroundEnabled(minecraft);
   }

   private static int getBGColor(boolean backgroundImage) {
      return backgroundImage ? ARGB32.color(0, 0, 0, 0) : -16777216;
   }

   public interface Access {
      boolean stextras$isFadeIn();

      long stextras$getFadeInStart();

      void stextras$setFadeInStart(long var1);

      long stextras$getFadeOutStart();

      void stextras$setFadeOutStart(long var1);

      Minecraft stextras$getMinecraft();

      float stextras$getCurrentProgress();

      void stextras$setCurrentProgress(float var1);

      ReloadInstance stextras$getReload();

      Consumer<Optional<Throwable>> stextras$getOnFinish();

      int stextras$getLastWidth();

      void stextras$setLastWidth(int var1);
   }
}
