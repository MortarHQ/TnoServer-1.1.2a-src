package xaero.map.graphics;

import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;

public class MapRenderHelper {
   private static BlendMode defaultShaderDisabledBlendState = new BlendMode();
   private static BlendMode defaultShaderBlendState = new BlendMode(770, 771, 32774);

   public static void renderBranchUpdate(
      float x,
      float y,
      float width,
      float height,
      int textureX,
      int textureY,
      float textureW,
      float textureH,
      float fullTextureWidth,
      float fullTextureHeight,
      boolean first
   ) {
      RenderSystem.setShader(() -> LibShaders.WORLD_MAP_BRANCH);
      Tesselator tessellator = Tesselator.getInstance();
      BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      float normalizedTextureX = textureX / fullTextureWidth;
      float normalizedTextureY = textureY / fullTextureHeight;
      float normalizedTextureX2 = (textureX + textureW) / fullTextureWidth;
      float normalizedTextureY2 = (textureY + textureH) / fullTextureHeight;
      vertexBuffer.addVertex(x + 0.0F, y + height, 0.0F).setUv(normalizedTextureX, normalizedTextureY2);
      vertexBuffer.addVertex(x + width, y + height, 0.0F).setUv(normalizedTextureX2, normalizedTextureY2);
      vertexBuffer.addVertex(x + width, y + 0.0F, 0.0F).setUv(normalizedTextureX2, normalizedTextureY);
      vertexBuffer.addVertex(x + 0.0F, y + 0.0F, 0.0F).setUv(normalizedTextureX, normalizedTextureY);
      MeshData buffer = vertexBuffer.build();
      if (first) {
         BufferUploader.drawWithShader(buffer);
         ProgramManager.glUseProgram(LibShaders.WORLD_MAP_BRANCH.getId());
      } else {
         BufferUploader.draw(buffer);
      }
   }

   public static void fillIntoExistingBuffer(Matrix4f matrix, VertexConsumer bufferBuilder, int x1, int y1, int x2, int y2, float r, float g, float b, float a) {
      bufferBuilder.addVertex(matrix, x1, y2, 0.0F).setColor(r, g, b, a);
      bufferBuilder.addVertex(matrix, x2, y2, 0.0F).setColor(r, g, b, a);
      bufferBuilder.addVertex(matrix, x2, y1, 0.0F).setColor(r, g, b, a);
      bufferBuilder.addVertex(matrix, x1, y1, 0.0F).setColor(r, g, b, a);
   }

   public static void blitIntoExistingBuffer(
      Matrix4f matrix,
      VertexConsumer bufferBuilder,
      float x,
      float y,
      int u,
      int v,
      int width,
      int height,
      int tW,
      int tH,
      float r,
      float g,
      float b,
      float a,
      int textureWidth,
      int textureHeight
   ) {
      float factorX = 1.0F / textureWidth;
      float factorY = 1.0F / textureHeight;
      float textureX1 = u * factorX;
      float textureX2 = (u + tW) * factorX;
      float textureY1 = v * factorY;
      float textureY2 = (v + tH) * factorY;
      bufferBuilder.addVertex(matrix, x, y + height, 0.0F).setColor(r, g, b, a).setUv(textureX1, textureY2);
      bufferBuilder.addVertex(matrix, x + width, y + height, 0.0F).setColor(r, g, b, a).setUv(textureX2, textureY2);
      bufferBuilder.addVertex(matrix, x + width, y, 0.0F).setColor(r, g, b, a).setUv(textureX2, textureY1);
      bufferBuilder.addVertex(matrix, x, y, 0.0F).setColor(r, g, b, a).setUv(textureX1, textureY1);
   }

   public static void blitIntoExistingBuffer(
      Matrix4f matrix, VertexConsumer bufferBuilder, int x, int y, int u, int v, int width, int height, float r, float g, float b, float a
   ) {
      blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, width, height, r, g, b, a, 256, 256);
   }

   public static void blitIntoMultiTextureRenderer(
      Matrix4f matrix,
      MultiTextureRenderTypeRenderer renderer,
      float x,
      float y,
      int u,
      int v,
      int width,
      int height,
      int tW,
      int tH,
      float r,
      float g,
      float b,
      float a,
      int textureWidth,
      int textureHeight,
      int texture
   ) {
      VertexConsumer bufferBuilder = renderer.begin(texture);
      blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, tW, tH, r, g, b, a, textureWidth, textureHeight);
   }

   public static void blitIntoMultiTextureRenderer(
      Matrix4f matrix,
      MultiTextureRenderTypeRenderer renderer,
      float x,
      float y,
      int u,
      int v,
      int width,
      int height,
      float r,
      float g,
      float b,
      float a,
      int textureWidth,
      int textureHeight,
      int texture
   ) {
      blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, width, height, r, g, b, a, textureWidth, textureHeight, texture);
   }

   public static void blitIntoMultiTextureRenderer(
      Matrix4f matrix,
      MultiTextureRenderTypeRenderer renderer,
      float x,
      float y,
      int u,
      int v,
      int width,
      int height,
      float r,
      float g,
      float b,
      float a,
      int texture
   ) {
      blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, r, g, b, a, 256, 256, texture);
   }

   public static void renderDynamicHighlight(
      PoseStack matrixStack,
      VertexConsumer overlayBuffer,
      int flooredCameraX,
      int flooredCameraZ,
      int leftX,
      int rightX,
      int topZ,
      int bottomZ,
      float sideR,
      float sideG,
      float sideB,
      float sideA,
      float centerR,
      float centerG,
      float centerB,
      float centerA
   ) {
      fillIntoExistingBuffer(
         matrixStack.last().pose(),
         overlayBuffer,
         leftX - 1 - flooredCameraX,
         topZ - 1 - flooredCameraZ,
         leftX - flooredCameraX,
         bottomZ + 1 - flooredCameraZ,
         sideR,
         sideG,
         sideB,
         sideA
      );
      fillIntoExistingBuffer(
         matrixStack.last().pose(),
         overlayBuffer,
         leftX - flooredCameraX,
         topZ - 1 - flooredCameraZ,
         rightX - flooredCameraX,
         topZ - flooredCameraZ,
         sideR,
         sideG,
         sideB,
         sideA
      );
      fillIntoExistingBuffer(
         matrixStack.last().pose(),
         overlayBuffer,
         rightX - flooredCameraX,
         topZ - 1 - flooredCameraZ,
         rightX + 1 - flooredCameraX,
         bottomZ + 1 - flooredCameraZ,
         sideR,
         sideG,
         sideB,
         sideA
      );
      fillIntoExistingBuffer(
         matrixStack.last().pose(),
         overlayBuffer,
         leftX - flooredCameraX,
         bottomZ - flooredCameraZ,
         rightX - flooredCameraX,
         bottomZ + 1 - flooredCameraZ,
         sideR,
         sideG,
         sideB,
         sideA
      );
      fillIntoExistingBuffer(
         matrixStack.last().pose(),
         overlayBuffer,
         leftX - flooredCameraX,
         topZ - flooredCameraZ,
         rightX - flooredCameraX,
         bottomZ - flooredCameraZ,
         centerR,
         centerG,
         centerB,
         centerA
      );
   }

   public static void drawCenteredStringWithBackground(
      GuiGraphics guiGraphics,
      Font font,
      String string,
      int x,
      int y,
      int color,
      float bgRed,
      float bgGreen,
      float bgBlue,
      float bgAlpha,
      VertexConsumer backgroundVertexBuffer
   ) {
      int stringWidth = font.width(string);
      drawStringWithBackground(guiGraphics, font, string, x - stringWidth / 2, y, color, bgRed, bgGreen, bgBlue, bgAlpha, backgroundVertexBuffer);
   }

   public static void drawStringWithBackground(
      GuiGraphics guiGraphics,
      Font font,
      String string,
      int x,
      int y,
      int color,
      float bgRed,
      float bgGreen,
      float bgBlue,
      float bgAlpha,
      VertexConsumer backgroundVertexBuffer
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      int stringWidth = font.width(string);
      matrixStack.translate(0.0F, 0.0F, -1.0F);
      fillIntoExistingBuffer(matrixStack.last().pose(), backgroundVertexBuffer, x - 1, y - 1, x + stringWidth + 1, y + 9, bgRed, bgGreen, bgBlue, bgAlpha);
      matrixStack.translate(0.0F, 0.0F, 1.0F);
      guiGraphics.drawString(font, string, x, y, color);
   }

   public static void drawCenteredStringWithBackground(
      GuiGraphics guiGraphics,
      Font font,
      Component text,
      int x,
      int y,
      int color,
      float bgRed,
      float bgGreen,
      float bgBlue,
      float bgAlpha,
      VertexConsumer backgroundVertexBuffer
   ) {
      int stringWidth = font.width(text);
      drawStringWithBackground(guiGraphics, font, text, x - stringWidth / 2, y, color, bgRed, bgGreen, bgBlue, bgAlpha, backgroundVertexBuffer);
   }

   public static void drawStringWithBackground(
      GuiGraphics guiGraphics,
      Font font,
      Component text,
      int x,
      int y,
      int color,
      float bgRed,
      float bgGreen,
      float bgBlue,
      float bgAlpha,
      VertexConsumer backgroundVertexBuffer
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      int stringWidth = font.width(text);
      matrixStack.translate(0.0F, 0.0F, -1.0F);
      fillIntoExistingBuffer(matrixStack.last().pose(), backgroundVertexBuffer, x - 1, y - 1, x + stringWidth + 1, y + 9, bgRed, bgGreen, bgBlue, bgAlpha);
      matrixStack.translate(0.0F, 0.0F, 1.0F);
      guiGraphics.drawString(font, text, x, y, color);
   }

   public static void blitIntoExistingBuffer(
      Matrix4f matrix, VertexConsumer bufferBuilder, float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight
   ) {
      float factorX = 1.0F / textureWidth;
      float factorY = 1.0F / textureHeight;
      float textureX1 = u * factorX;
      float textureX2 = (u + width) * factorX;
      float textureY1 = v * factorY;
      float textureY2 = (v + height) * factorY;
      bufferBuilder.addVertex(matrix, x, y + height, 0.0F).setUv(textureX1, textureY2);
      bufferBuilder.addVertex(matrix, x + width, y + height, 0.0F).setUv(textureX2, textureY2);
      bufferBuilder.addVertex(matrix, x + width, y, 0.0F).setUv(textureX2, textureY1);
      bufferBuilder.addVertex(matrix, x, y, 0.0F).setUv(textureX1, textureY1);
   }

   public static void blitIntoExistingBuffer(Matrix4f matrix, VertexConsumer bufferBuilder, int x, int y, int u, int v, int width, int height) {
      blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, 256, 256);
   }

   public static void blitIntoMultiTextureRenderer(
      Matrix4f matrix,
      MultiTextureRenderTypeRenderer renderer,
      int x,
      int y,
      int u,
      int v,
      int width,
      int height,
      int textureWidth,
      int textureHeight,
      int texture
   ) {
      VertexConsumer bufferBuilder = renderer.begin(texture);
      blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, textureWidth, textureHeight);
   }

   public static void blitIntoMultiTextureRenderer(
      Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, int x, int y, int u, int v, int width, int height, int texture
   ) {
      blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, 256, 256, texture);
   }

   public static void restoreDefaultShaderBlendState() {
      defaultShaderDisabledBlendState.apply();
      defaultShaderBlendState.apply();
      RenderSystem.defaultBlendFunc();
   }
}
