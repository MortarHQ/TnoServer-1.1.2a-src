package com.finderfeed.fdlib.util.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;

public class ColoredVertexConsumer implements VertexConsumer {
   public int r;
   public int g;
   public int b;
   public int a;
   public VertexConsumer original;

   public ColoredVertexConsumer(VertexConsumer original, int r, int g, int b, int a) {
      this.original = original;
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = a;
   }

   public VertexConsumer addVertex(float px, float py, float pz) {
      return this.original.addVertex(px, py, pz);
   }

   public VertexConsumer setColor(int r, int g, int b, int a) {
      return this.original.setColor(this.r, this.g, this.b, this.a);
   }

   public VertexConsumer setUv(float u, float v) {
      return this.original.setUv(u, v);
   }

   public VertexConsumer setUv1(int u, int v) {
      return this.original.setUv1(u, v);
   }

   public VertexConsumer setUv2(int u, int v) {
      return this.original.setUv2(u, v);
   }

   public VertexConsumer setNormal(float px, float py, float pz) {
      return this.original.setNormal(px, py, pz);
   }

   public static MultiBufferSource wrapBufferSource(MultiBufferSource source, int r, int g, int b, int a) {
      return renderType -> new ColoredVertexConsumer(source.getBuffer(renderType), r, g, b, a);
   }
}
