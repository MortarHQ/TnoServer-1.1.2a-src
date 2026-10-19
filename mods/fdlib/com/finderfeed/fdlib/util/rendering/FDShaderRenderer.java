package com.finderfeed.fdlib.util.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;

public class FDShaderRenderer {
   private ShaderInstance shaderInstance;
   private GuiGraphics graphics;
   private float x;
   private float y;
   private float z;
   private float xw;
   private float yw;
   private float[] color = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
   private boolean centered = false;
   private float xUvSpan = 1.0F;
   private float yUvSpan = 1.0F;

   private FDShaderRenderer() {
   }

   public static FDShaderRenderer start(GuiGraphics graphics, ShaderInstance shader) {
      FDShaderRenderer shaderRenderer = new FDShaderRenderer();
      shaderRenderer.graphics = graphics;
      shaderRenderer.shaderInstance = shader;
      return shaderRenderer;
   }

   public FDShaderRenderer setShaderUniform(String name, float... values) {
      this.shaderInstance.safeGetUniform(name).set(values);
      return this;
   }

   public FDShaderRenderer setCentered(boolean centered) {
      this.centered = centered;
      return this;
   }

   public FDShaderRenderer setUVSpan(float xSpan, float ySpan) {
      this.xUvSpan = xSpan;
      this.yUvSpan = ySpan;
      return this;
   }

   public FDShaderRenderer position(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   public FDShaderRenderer setResolution(float xw, float yw) {
      this.xw = xw;
      this.yw = yw;
      return this;
   }

   public FDShaderRenderer setColor(float r, float g, float b, float a) {
      for (int i = 0; i < 16; i += 4) {
         this.color[i] = r;
         this.color[i + 1] = g;
         this.color[i + 2] = b;
         this.color[i + 3] = a;
      }

      return this;
   }

   public FDShaderRenderer setColor(float r, float g, float b, float a, int index) {
      this.color[index * 4] = r;
      this.color[1 + index * 4] = g;
      this.color[2 + index * 4] = b;
      this.color[3 + index * 4] = a;
      return this;
   }

   public FDShaderRenderer setDownColor(float r, float g, float b, float a) {
      return this.setDownLeftColor(r, g, b, a).setDownRightColor(r, g, b, a);
   }

   public FDShaderRenderer setUpColor(float r, float g, float b, float a) {
      return this.setUpLeftColor(r, g, b, a).setUpRightColor(r, g, b, a);
   }

   public FDShaderRenderer setRightColor(float r, float g, float b, float a) {
      return this.setUpRightColor(r, g, b, a).setDownRightColor(r, g, b, a);
   }

   public FDShaderRenderer setLeftColor(float r, float g, float b, float a) {
      return this.setUpLeftColor(r, g, b, a).setDownLeftColor(r, g, b, a);
   }

   public FDShaderRenderer setUpLeftColor(float r, float g, float b, float a) {
      return this.setColor(r, g, b, a, 0);
   }

   public FDShaderRenderer setUpRightColor(float r, float g, float b, float a) {
      return this.setColor(r, g, b, a, 1);
   }

   public FDShaderRenderer setDownRightColor(float r, float g, float b, float a) {
      return this.setColor(r, g, b, a, 2);
   }

   public FDShaderRenderer setDownLeftColor(float r, float g, float b, float a) {
      return this.setColor(r, g, b, a, 3);
   }

   public void end() {
      VertexFormat format = this.shaderInstance.getVertexFormat();
      RenderSystem.setShader(() -> this.shaderInstance);
      Tesselator tesselator = Tesselator.getInstance();
      BufferBuilder vertex = tesselator.begin(Mode.QUADS, format);
      float xs = this.centered ? this.x - this.xw / 2.0F : this.x;
      float ys = this.centered ? this.y - this.yw / 2.0F : this.y;
      float xe = this.centered ? this.x + this.xw / 2.0F : this.x + this.xw;
      float ye = this.centered ? this.y + this.yw / 2.0F : this.y + this.yw;
      PoseStack matrix = this.graphics.pose();
      Matrix4f matrix4f = matrix.last().pose();
      RenderSystem.enableBlend();
      if (format == DefaultVertexFormat.POSITION_TEX_COLOR) {
         vertex.addVertex(matrix4f, xs, ye, this.z).setUv(0.0F, this.yUvSpan).setColor(this.color[12], this.color[13], this.color[14], this.color[15]);
         vertex.addVertex(matrix4f, xe, ye, this.z).setUv(this.xUvSpan, this.yUvSpan).setColor(this.color[8], this.color[9], this.color[10], this.color[11]);
         vertex.addVertex(matrix4f, xe, ys, this.z).setUv(this.xUvSpan, 0.0F).setColor(this.color[4], this.color[5], this.color[6], this.color[7]);
         vertex.addVertex(matrix4f, xs, ys, this.z).setUv(0.0F, 0.0F).setColor(this.color[0], this.color[1], this.color[2], this.color[3]);
      } else if (format == DefaultVertexFormat.POSITION_COLOR) {
         vertex.addVertex(matrix4f, xs, ye, this.z).setColor(this.color[12], this.color[13], this.color[14], this.color[15]);
         vertex.addVertex(matrix4f, xe, ye, this.z).setColor(this.color[8], this.color[9], this.color[10], this.color[11]);
         vertex.addVertex(matrix4f, xe, ys, this.z).setColor(this.color[4], this.color[5], this.color[6], this.color[7]);
         vertex.addVertex(matrix4f, xs, ys, this.z).setColor(this.color[0], this.color[1], this.color[2], this.color[3]);
      } else {
         if (format != DefaultVertexFormat.POSITION) {
            throw new RuntimeException("Shader vertex format is not supported by FDShaderRenderer");
         }

         vertex.addVertex(matrix4f, xs, ye, this.z);
         vertex.addVertex(matrix4f, xe, ye, this.z);
         vertex.addVertex(matrix4f, xe, ys, this.z);
         vertex.addVertex(matrix4f, xs, ys, this.z);
      }

      BufferUploader.drawWithShader(vertex.build());
   }
}
