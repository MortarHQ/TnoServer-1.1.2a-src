package xaero.common.minimap.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.lib.client.graphics.shader.PositionTexAlphaTestShader;

public class MinimapRendererHelper {
   private static BlendMode defaultShaderDisabledBlendState = new BlendMode();
   private static BlendMode defaultShaderBlendState = new BlendMode(770, 771, 32774);

   public void drawMyTexturedModalRect(
      PoseStack matrixStack, float x, float y, int textureX, int textureY, float width, float height, float theight, float factor
   ) {
      this.drawMyTexturedModalRect(matrixStack, x, y, textureX, textureY, width, height, theight, factor, 0.0F, true);
   }

   public void drawMyTexturedModalRect(
      PoseStack matrixStack,
      float x,
      float y,
      int textureX,
      int textureY,
      float width,
      float height,
      float theight,
      float factor,
      float discardAlpha,
      boolean blend
   ) {
      if (discardAlpha < 0.0F) {
         RenderSystem.setShader(blend ? () -> LibShaders.POSITION_TEX_NO_ALPHA_TEST : () -> LibShaders.POSITION_TEX_NO_ALPHA_TEST_NO_BLEND);
      } else {
         RenderSystem.setShader(blend ? () -> LibShaders.POSITION_TEX_ALPHA_TEST : () -> LibShaders.POSITION_TEX_ALPHA_TEST_NO_BLEND);
         ((PositionTexAlphaTestShader)RenderSystem.getShader()).setDiscardAlpha(discardAlpha);
      }

      this.drawMyTexturedModalRectInternal(matrixStack, x, y, textureX, textureY, width, height, theight, factor);
   }

   public void drawIconOutline(
      PoseStack matrixStack, float x, float y, int textureX, int textureY, float width, float height, float theight, float factor, float discardAlpha
   ) {
      LibShaders.POSITION_TEX_ICON_OUTLINE.setDiscardAlpha(discardAlpha);
      RenderSystem.setShader(() -> LibShaders.POSITION_TEX_ICON_OUTLINE);
      this.drawMyTexturedModalRectInternal(matrixStack, x, y, textureX, textureY, width, height, theight, factor);
   }

   private void drawMyTexturedModalRectInternal(
      PoseStack matrixStack, float x, float y, int textureX, int textureY, float width, float height, float theight, float factor
   ) {
      float f = 1.0F / factor;
      float f1 = f;
      Matrix4f matrix = matrixStack.last().pose();
      Tesselator tessellator = Tesselator.getInstance();
      BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setUv((textureX + 0) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setUv((textureX + width) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setUv((textureX + width) * f, (textureY + theight) * f1);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setUv((textureX + 0) * f, (textureY + theight) * f1);
      BufferUploader.drawWithShader(vertexBuffer.build());
   }

   public void prepareMyTexturedColoredModalRect(
      Matrix4f matrix,
      float x,
      float y,
      int textureX,
      int textureY,
      float width,
      float height,
      float theight,
      float factor,
      int textureId,
      float r,
      float g,
      float b,
      float a,
      MultiTextureRenderTypeRenderer renderer
   ) {
      float f = 1.0F / factor;
      float f1 = f;
      BufferBuilder vertexBuffer = renderer.begin(textureId);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setColor(r, g, b, a).setUv((textureX + 0) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setColor(r, g, b, a).setUv((textureX + width) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setColor(r, g, b, a).setUv((textureX + width) * f, (textureY + theight) * f1);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setColor(r, g, b, a).setUv((textureX + 0) * f, (textureY + theight) * f1);
   }

   public void prepareMyTexturedModalRect(
      Matrix4f matrix,
      float x,
      float y,
      int textureX,
      int textureY,
      float width,
      float height,
      float theight,
      float factor,
      int textureId,
      MultiTextureRenderTypeRenderer renderer
   ) {
      float f = 1.0F / factor;
      float f1 = f;
      BufferBuilder vertexBuffer = renderer.begin(textureId);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setUv((textureX + 0) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setUv((textureX + width) * f, (textureY + 0) * f1);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setUv((textureX + width) * f, (textureY + theight) * f1);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setUv((textureX + 0) * f, (textureY + theight) * f1);
   }

   void drawTexturedElipseInsideRectangle(
      PoseStack matrixStack, double startAngle, int sides, float x, float y, int textureX, int textureY, float width, float widthFactor
   ) {
      this.drawTexturedElipseInsideRectangle(matrixStack, startAngle, sides, x, y, textureX, textureY, width, width, widthFactor);
   }

   void drawTexturedElipseInsideRectangle(
      PoseStack matrixStack, double startAngle, int sides, float x, float y, int textureX, int textureY, float width, float theight, float widthFactor
   ) {
      float f = 1.0F / widthFactor;
      float f1 = f;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      Matrix4f matrix = matrixStack.last().pose();
      Tesselator tessellator = Tesselator.getInstance();
      float halfWidth = width / 2.0F;
      double centerX = x + halfWidth;
      double centerY = y + halfWidth;
      float centerU = (textureX + halfWidth) * f;
      float centerV = (float)((textureY + theight * 0.5) * f1);
      double fullCircle = Math.PI * 2;
      float prevVertexLocalX = 0.0F;
      float prevVertexLocalY = 0.0F;
      float prevVertexLocalV = 0.0F;
      BufferBuilder vertexBuffer = tessellator.begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX);

      for (int i = 0; i <= sides; i++) {
         double angle = startAngle + (double)i / sides * fullCircle;
         double sin = Math.sin(angle);
         double cos = Math.cos(angle);
         float vertexLocalX = halfWidth + (float)(halfWidth * sin);
         float vertexLocalY = (float)(halfWidth * (1.0 - cos));
         float vertexLocalV = (float)(theight * (1.0 - 0.5 * (1.0 - cos)));
         if (i > 0) {
            vertexBuffer.addVertex(matrix, x + vertexLocalX, y + vertexLocalY, 0.0F).setUv((textureX + vertexLocalX) * f, (textureY + vertexLocalV) * f1);
            vertexBuffer.addVertex(matrix, x + prevVertexLocalX, y + prevVertexLocalY, 0.0F)
               .setUv((textureX + prevVertexLocalX) * f, (textureY + prevVertexLocalV) * f1);
            vertexBuffer.addVertex(matrix, (float)centerX, (float)centerY, 0.0F).setUv(centerU, centerV);
         }

         prevVertexLocalX = vertexLocalX;
         prevVertexLocalY = vertexLocalY;
         prevVertexLocalV = vertexLocalV;
      }

      BufferUploader.drawWithShader(vertexBuffer.build());
   }

   void drawTexturedElipseInsideRectangleFrame(
      PoseStack matrixStack,
      boolean resetTexture,
      boolean reverseTexture,
      double startAngle,
      int startIndex,
      int endIndex,
      int sides,
      float thickness,
      float x,
      float y,
      int textureX,
      int textureY,
      float width,
      float twidth,
      float theight,
      int seamWidth,
      float widthFactor
   ) {
      float f = 1.0F / widthFactor;
      float f1 = f;
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      Matrix4f matrix = matrixStack.last().pose();
      Tesselator tessellator = Tesselator.getInstance();
      float halfWidth = width / 2.0F;
      double fullCircle = Math.PI * 2;
      float prevVertexLocalX = 0.0F;
      float prevVertexLocalY = 0.0F;
      float prevVertexLocalOuterX = 0.0F;
      float prevVertexLocalOuterY = 0.0F;
      float prevSegmentTextureX = 0.0F;
      BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      float outerRadius = halfWidth + thickness;
      float segmentOuterWidth = (float)(fullCircle / sides * outerRadius);
      startIndex = Math.max(Math.min(startIndex, sides), 0);
      endIndex = Math.max(Math.min(endIndex, sides), startIndex);
      int textureStartIndex = resetTexture ? (reverseTexture ? endIndex : startIndex) : 0;
      float seamThreshold = reverseTexture ? seamWidth + segmentOuterWidth : seamWidth;

      for (int i = startIndex; i <= endIndex; i++) {
         double angle = startAngle + (double)i / sides * fullCircle;
         double sin = Math.sin(angle);
         double cos = Math.cos(angle);
         float vertexLocalX = halfWidth + (float)(halfWidth * sin);
         float vertexLocalY = (float)(halfWidth * (1.0 - cos));
         float vertexLocalOuterX = halfWidth + (float)(outerRadius * sin);
         float vertexLocalOuterY = (float)(halfWidth - outerRadius * cos);
         float segmentTextureStartX = textureX;
         float offsetX = Math.abs(segmentOuterWidth * (i - textureStartIndex));
         if (offsetX >= seamThreshold) {
            segmentTextureStartX = textureX + seamWidth;
            offsetX -= seamThreshold;
            if (offsetX >= twidth) {
               offsetX %= twidth;
            }
         }

         float segmentTextureX = segmentTextureStartX + offsetX;
         if (i > startIndex) {
            vertexBuffer.addVertex(matrix, x + prevVertexLocalX, y + prevVertexLocalY, 0.0F).setUv(prevSegmentTextureX * f, (textureY + theight) * f1);
            vertexBuffer.addVertex(matrix, x + vertexLocalX, y + vertexLocalY, 0.0F).setUv(segmentTextureX * f, (textureY + theight) * f1);
            vertexBuffer.addVertex(matrix, x + vertexLocalOuterX, y + vertexLocalOuterY, 0.0F).setUv(segmentTextureX * f, textureY * f1);
            vertexBuffer.addVertex(matrix, x + prevVertexLocalOuterX, y + prevVertexLocalOuterY, 0.0F).setUv(prevSegmentTextureX * f, textureY * f1);
         }

         prevVertexLocalX = vertexLocalX;
         prevVertexLocalY = vertexLocalY;
         prevVertexLocalOuterX = vertexLocalOuterX;
         prevVertexLocalOuterY = vertexLocalOuterY;
         prevSegmentTextureX = segmentTextureX;
      }

      BufferUploader.drawWithShader(vertexBuffer.build());
   }

   public void addTexturedRectToExistingBuffer(Matrix4f matrix, VertexConsumer vertexBuffer, float x, float y, int u, int v, int w, int h) {
      float f = 0.00390625F;
      float normalizedU1 = u * f;
      float normalizedV1 = v * f;
      float normalizedU2 = (u + w) * f;
      float normalizedV2 = (v + h) * f;
      vertexBuffer.addVertex(matrix, x, y + h, 0.0F).setUv(normalizedU1, normalizedV2);
      vertexBuffer.addVertex(matrix, x + w, y + h, 0.0F).setUv(normalizedU2, normalizedV2);
      vertexBuffer.addVertex(matrix, x + w, y, 0.0F).setUv(normalizedU2, normalizedV1);
      vertexBuffer.addVertex(matrix, x, y, 0.0F).setUv(normalizedU1, normalizedV1);
   }

   public void drawMyColoredRect(PoseStack matrixStack, float x1, float y1, float x2, float y2) {
      RenderSystem.setShader(GameRenderer::getPositionShader);
      Matrix4f matrix = matrixStack.last().pose();
      Tesselator tessellator = Tesselator.getInstance();
      BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION);
      vertexBuffer.addVertex(matrix, x1, y2, 0.0F);
      vertexBuffer.addVertex(matrix, x2, y2, 0.0F);
      vertexBuffer.addVertex(matrix, x2, y1, 0.0F);
      vertexBuffer.addVertex(matrix, x1, y1, 0.0F);
      BufferUploader.drawWithShader(vertexBuffer.build());
   }

   public void addColoredLineToExistingBuffer(
      Pose matrices, VertexConsumer vertexBuffer, float x1, float y1, float x2, float y2, float r, float g, float b, float a
   ) {
      vertexBuffer.addVertex(matrices, x1, y1, 0.0F).setColor(r, g, b, a).setNormal(matrices, x2 - x1, y2 - y1, 0.0F);
      vertexBuffer.addVertex(matrices, x2, y2, 0.0F).setColor(r, g, b, a).setNormal(matrices, x2 - x1, y2 - y1, 0.0F);
   }

   public void drawMyColoredRect(Matrix4f matrix, float x1, float y1, float x2, float y2, int color) {
      float a = (color >> 24 & 0xFF) / 255.0F;
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Tesselator tessellator = Tesselator.getInstance();
      BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      vertexBuffer.addVertex(matrix, x1, y2, 0.0F).setColor(r, g, b, a);
      vertexBuffer.addVertex(matrix, x2, y2, 0.0F).setColor(r, g, b, a);
      vertexBuffer.addVertex(matrix, x2, y1, 0.0F).setColor(r, g, b, a);
      vertexBuffer.addVertex(matrix, x1, y1, 0.0F).setColor(r, g, b, a);
      BufferUploader.drawWithShader(vertexBuffer.build());
   }

   void bindTextureBuffer(ByteBuffer image, int width, int height, int par0) {
      GlStateManager._bindTexture(par0);
      GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, image);
      RenderSystem.setShaderTexture(0, par0);
   }

   void putColor(byte[] bytes, int x, int y, int red, int green, int blue, int size) {
      int pixel = (y * size + x) * 3;
      bytes[pixel] = (byte)red;
      bytes[++pixel] = (byte)green;
      bytes[++pixel] = (byte)blue;
   }

   void gridOverlay(int[] result, int grid, int red, int green, int blue) {
      result[0] = (red * 3 + (grid >> 16 & 0xFF)) / 4;
      result[1] = (green * 3 + (grid >> 8 & 0xFF)) / 4;
      result[2] = (blue * 3 + (grid & 0xFF)) / 4;
   }

   void slimeOverlay(int[] result, int red, int green, int blue) {
      result[0] = (red + 82) / 2;
      result[1] = (green + 241) / 2;
      result[2] = (blue + 64) / 2;
   }

   public void defaultOrtho(RenderTarget framebuffer) {
      if (framebuffer != null) {
         Matrix4f ortho = new Matrix4f().setOrtho(0.0F, framebuffer.width, framebuffer.height, 0.0F, 1000.0F, 21000.0F);
         RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);
      }
   }

   public static void restoreDefaultShaderBlendState() {
      defaultShaderDisabledBlendState.apply();
      defaultShaderBlendState.apply();
      RenderSystem.defaultBlendFunc();
   }
}
