package team.lodestar.lodestone.systems.texture;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.common.NeoForgeConfig;

public class CustomizableTextureTarget extends RenderTarget {
   private final InternalTextureFormat internalFormat;

   public CustomizableTextureTarget(int width, int height, boolean useDepth) {
      this(width, height, useDepth, InternalTextureFormat.RGBA16F);
   }

   public CustomizableTextureTarget(int width, int height, boolean useDepth, InternalTextureFormat internalFormat) {
      super(useDepth);
      this.internalFormat = internalFormat;
      RenderSystem.assertOnRenderThreadOrInit();
      this.resize(width, height, Minecraft.ON_OSX);
   }

   public void createBuffers(int width, int height, boolean clearError) {
      RenderSystem.assertOnRenderThreadOrInit();
      int i = RenderSystem.maxSupportedTextureSize();
      if (width > 0 && width <= i && height > 0 && height <= i) {
         this.viewWidth = width;
         this.viewHeight = height;
         this.width = width;
         this.height = height;
         this.frameBufferId = GlStateManager.glGenFramebuffers();
         this.colorTextureId = TextureUtil.generateTextureId();
         if (this.useDepth) {
            this.depthBufferId = TextureUtil.generateTextureId();
            GlStateManager._bindTexture(this.depthBufferId);
            GlStateManager._texParameter(3553, 10241, 9728);
            GlStateManager._texParameter(3553, 10240, 9728);
            GlStateManager._texParameter(3553, 34892, 0);
            GlStateManager._texParameter(3553, 10242, 33071);
            GlStateManager._texParameter(3553, 10243, 33071);
            if (!this.isStencilEnabled()) {
               GlStateManager._texImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, null);
            } else {
               GlStateManager._texImage2D(3553, 0, 36013, this.width, this.height, 0, 34041, 36269, null);
            }
         }

         this.setFilterMode(9728, true);
         GlStateManager._bindTexture(this.colorTextureId);
         GlStateManager._texParameter(3553, 10242, 33071);
         GlStateManager._texParameter(3553, 10243, 33071);
         GlStateManager._texImage2D(3553, 0, this.internalFormat.getGlFormat(), this.width, this.height, 0, 6408, this.internalFormat.getGlType(), null);
         GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
         GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
         if (this.useDepth) {
            if (!this.isStencilEnabled()) {
               GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
            } else if ((Boolean)NeoForgeConfig.CLIENT.useCombinedDepthStencilAttachment.get()) {
               GlStateManager._glFramebufferTexture2D(36160, 33306, 3553, this.depthBufferId, 0);
            } else {
               GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
               GlStateManager._glFramebufferTexture2D(36160, 36128, 3553, this.depthBufferId, 0);
            }
         }

         this.checkStatus();
         this.clear(clearError);
         this.unbindRead();
      } else {
         throw new IllegalArgumentException("Window " + width + "x" + height + " size out of bounds (max. size: " + i + ")");
      }
   }
}
