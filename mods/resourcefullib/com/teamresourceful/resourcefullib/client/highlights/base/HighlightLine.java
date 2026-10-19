package com.teamresourceful.resourcefullib.client.highlights.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public record HighlightLine(Vector3f start, Vector3f end, Vector3f normal) {
   public static final Codec<HighlightLine> CODEC = Codec.FLOAT
      .listOf()
      .comapFlatMap(floats -> Util.fixedSize(floats, 6).map(HighlightLine::new), HighlightLine::getAsList);

   public HighlightLine(Vector3f start, Vector3f end) {
      this(start, end, normal(start, end));
   }

   public HighlightLine(List<Float> list) {
      this(new Vector3f(list.get(0), list.get(1), list.get(2)), new Vector3f(list.get(3), list.get(4), list.get(5)));
   }

   public List<Float> getAsList() {
      return List.of(this.start.x(), this.start.y(), this.start.z(), this.end.x(), this.end.y(), this.end.z());
   }

   public HighlightLine copy() {
      return new HighlightLine(new Vector3f(this.start), new Vector3f(this.end));
   }

   public void recalculateNormal() {
      Vector3f normal = normal(this.start, this.end);
      this.normal.set(normal.x(), normal.y(), normal.z());
   }

   public void render(PoseStack poseStack, VertexConsumer consumer, float x, float y, float z) {
      render(
         poseStack,
         consumer,
         0.0F,
         0.0F,
         0.0F,
         0.4F,
         x,
         y,
         z,
         this.start.x(),
         this.start.y(),
         this.start.z(),
         this.end.x(),
         this.end.y(),
         this.end.z(),
         this.normal.x(),
         this.normal.y(),
         this.normal.z()
      );
   }

   public static void render(
      PoseStack stack,
      VertexConsumer consumer,
      float r,
      float g,
      float b,
      float a,
      float x,
      float y,
      float z,
      float x1,
      float y1,
      float z1,
      float x2,
      float y2,
      float z2,
      float normalX,
      float normalY,
      float normalZ
   ) {
      Pose last = stack.last();
      consumer.addVertex(last.pose(), x + x1, y + y1, z + z1).setColor(r, g, b, a).setNormal(last, normalX, normalY, normalZ);
      consumer.addVertex(last.pose(), x + x2, y + y2, z + z2).setColor(r, g, b, a).setNormal(last, normalX, normalY, normalZ);
   }

   private static Vector3f normal(Vector3f start, Vector3f end) {
      Vector3f diff = new Vector3f(start.x() - end.x(), start.y() - end.y(), start.z() - end.z());
      float sqrt = Mth.sqrt(diff.x() * diff.x() + diff.y() * diff.y() + diff.z() * diff.z());
      return new Vector3f(diff.x() / sqrt, diff.y() / sqrt, diff.z() / sqrt);
   }
}
