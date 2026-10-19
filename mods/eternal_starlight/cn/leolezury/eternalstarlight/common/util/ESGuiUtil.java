package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ESGuiUtil {
   public static void blitFloat(
      GuiGraphics graphics, ResourceLocation resourceLocation, float x, float y, float width, float height, float texWidth, float texHeight
   ) {
      innerBlitFloat(graphics, resourceLocation, x, x + width, y, y + height, width / texWidth, height / texHeight);
   }

   private static void innerBlitFloat(GuiGraphics graphics, ResourceLocation resourceLocation, float x, float xTo, float y, float yTo, float u, float v) {
      RenderSystem.setShaderTexture(0, resourceLocation);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      Matrix4f matrix4f = graphics.pose().last().pose();
      BufferBuilder bufferBuilder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bufferBuilder.addVertex(matrix4f, x, y, 0.0F).setUv(0.0F, 0.0F);
      bufferBuilder.addVertex(matrix4f, x, yTo, 0.0F).setUv(0.0F, v);
      bufferBuilder.addVertex(matrix4f, xTo, yTo, 0.0F).setUv(u, v);
      bufferBuilder.addVertex(matrix4f, xTo, y, 0.0F).setUv(u, 0.0F);
      BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
   }
}
