package me.decce.gnetum;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.ClientHooks;
import org.joml.Matrix4f;

public class FramebufferManager {
   private static final Minecraft mc = Minecraft.getInstance();
   private static final FramebufferManager instance = new FramebufferManager();
   private boolean dropCurrentFrame;
   private int catchUpSerialNumber;
   private int serialNumber;
   private int width;
   private int height;
   private boolean downscale;
   private double guiScale;
   private TextureTarget backFramebuffer;
   private TextureTarget frontFramebuffer;

   private FramebufferManager() {
      this.reset();
   }

   public static FramebufferManager getInstance() {
      return instance;
   }

   public void ensureSize() {
      if (mc.getWindow().getWidth() != this.width
         || mc.getWindow().getHeight() != this.height
         || mc.getWindow().getGuiScale() != this.guiScale
         || this.downscale != Gnetum.getConfig().downscale.get()) {
         this.reset();
      }
   }

   public void clear() {
      this.clear(this.backFramebuffer);
   }

   public void clear(TextureTarget framebuffer) {
      mc.getProfiler().push("clear");
      framebuffer.clear(Minecraft.ON_OSX);
      mc.getProfiler().pop();
   }

   public void reset() {
      Window window = mc.getWindow();
      this.width = window.getWidth();
      this.height = window.getHeight();
      this.guiScale = mc.getWindow().getGuiScale();
      this.downscale = Gnetum.getConfig().downscale.get();
      int fboWidth = this.downscale ? window.getGuiScaledWidth() : window.getWidth();
      int fboHeight = this.downscale ? window.getGuiScaledHeight() : window.getHeight();
      if (this.backFramebuffer != null) {
         this.backFramebuffer.destroyBuffers();
      }

      this.backFramebuffer = new TextureTarget(fboWidth, fboHeight, true, false);
      this.backFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.backFramebuffer.setFilterMode(9728);
      this.clear(this.backFramebuffer);
      if (this.frontFramebuffer != null) {
         this.frontFramebuffer.destroyBuffers();
      }

      this.frontFramebuffer = new TextureTarget(fboWidth, fboHeight, true, false);
      this.frontFramebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.frontFramebuffer.setFilterMode(9728);
      this.clear(this.frontFramebuffer);
      this.serialNumber = 0;
      this.catchUpSerialNumber = 0;
      Gnetum.passManager.current = 1;
   }

   public void bind(boolean setViewport) {
      this.backFramebuffer.bindWrite(setViewport);
   }

   public void bind() {
      this.bind(true);
   }

   public void unbind() {
      mc.getMainRenderTarget().bindWrite(true);
   }

   public void blit() {
      mc.getProfiler().push("blit");
      RenderSystem.enableBlend();
      RenderSystem.disableDepthTest();
      RenderSystem.blendFuncSeparate(1, 771, 1, 771);
      this.frontFramebuffer.blitToScreen(this.width, this.height, false);
      Window window = mc.getWindow();
      Matrix4f matrix4f = new Matrix4f()
         .setOrtho(
            0.0F,
            (float)(window.getWidth() / window.getGuiScale()),
            (float)(window.getHeight() / window.getGuiScale()),
            0.0F,
            1000.0F,
            ClientHooks.getGuiFarPlane()
         );
      RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      Lighting.setupFor3DItems();
      mc.getProfiler().pop();
   }

   public void swapFramebuffers() {
      if (!this.dropCurrentFrame) {
         TextureTarget temp = this.backFramebuffer;
         this.backFramebuffer = this.frontFramebuffer;
         this.frontFramebuffer = temp;
         this.serialNumber++;
         Gnetum.FPS_COUNTER.tick();
      }

      this.clear();
      this.dropCurrentFrame = false;
   }

   public void dropCurrentFrame() {
      this.dropCurrentFrame = true;
   }

   public int id() {
      return this.backFramebuffer.frameBufferId;
   }

   public void markForCatchUp() {
      this.catchUpSerialNumber = this.serialNumber;
   }

   public boolean needsCatchUp() {
      return this.serialNumber - this.catchUpSerialNumber <= 1;
   }
}
