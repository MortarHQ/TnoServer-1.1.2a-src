package xaero.map.region.texture;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import xaero.map.exception.OpenGLException;
import xaero.map.graphics.ImprovedFramebuffer;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.misc.Misc;

public class BranchTextureRenderer {
   private ImprovedFramebuffer renderFBO;
   private int glEmptyTexture;
   private PoseStack matrixStack = new PoseStack();
   private Matrix4f projectionMatrix = new Matrix4f().setOrtho(0.0F, 64.0F, 64.0F, 0.0F, -1.0F, 1.0F);

   public void render(
      int destTexture,
      Integer srcTextureTopLeft,
      Integer srcTextureTopRight,
      Integer srcTextureBottomLeft,
      Integer srcTextureBottomRight,
      RenderTarget defaultFramebuffer,
      boolean justAllocated
   ) {
      if (this.renderFBO == null) {
         this.renderFBO = new ImprovedFramebuffer(64, 64, false);
         this.glEmptyTexture = this.renderFBO.getFramebufferTexture();
         this.renderFBO.bindAsMainTarget(true);
         GlStateManager._disableBlend();
         GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GlStateManager._clear(16384, Minecraft.ON_OSX);
         GlStateManager._enableBlend();
      }

      GlStateManager._bindTexture(0);
      this.renderFBO.bindAsMainTarget(true);
      this.renderFBO.setFramebufferTexture(destTexture);
      OpenGLException.checkGLError();
      Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
      shaderMatrixStack.pushMatrix();
      shaderMatrixStack.identity();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.setProjectionMatrix(this.projectionMatrix, VertexSorting.ORTHOGRAPHIC_Z);
      GlStateManager._disableBlend();
      if (justAllocated) {
         GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager._clear(16384, Minecraft.ON_OSX);
      }

      boolean first = true;
      if (srcTextureTopLeft != null) {
         first = this.renderCorner(srcTextureTopLeft, 0, 0, first);
      }

      if (srcTextureTopRight != null) {
         first = this.renderCorner(srcTextureTopRight, 1, 0, first);
      }

      if (srcTextureBottomLeft != null) {
         first = this.renderCorner(srcTextureBottomLeft, 0, 1, first);
      }

      if (srcTextureBottomRight != null) {
         first = this.renderCorner(srcTextureBottomRight, 1, 1, first);
      }

      OpenGLException.checkGLError(false, "updating a map branch texture");
      GlStateManager._enableBlend();
      ProgramManager.glUseProgram(0);
      GlStateManager._bindTexture(0);
      shaderMatrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      Minecraft mc = Minecraft.getInstance();
      Misc.minecraftOrtho(mc, false);
      this.renderFBO.unbindWrite();
      this.renderFBO.bindDefaultFramebuffer(mc);
      GlStateManager._viewport(0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight());
      OpenGLException.checkGLError();
   }

   private boolean renderCorner(Integer srcTexture, int cornerX, int cornerY, boolean first) {
      int xOffset = cornerX * 32;
      int yOffset = (1 - cornerY) * 32;
      int texture = srcTexture != -1 ? srcTexture : this.glEmptyTexture;
      if (first) {
         RenderSystem.setShaderTexture(0, texture);
      } else {
         GlStateManager._activeTexture(33984);
         GlStateManager._bindTexture(texture);
      }

      MapRenderHelper.renderBranchUpdate(xOffset, yOffset, 32.0F, 32.0F, 0, 64, 64.0F, -64.0F, 64.0F, 64.0F, first);
      return false;
   }
}
