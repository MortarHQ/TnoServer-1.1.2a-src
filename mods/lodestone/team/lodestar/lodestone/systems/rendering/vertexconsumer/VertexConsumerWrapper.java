package team.lodestar.lodestone.systems.rendering.vertexconsumer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

public abstract class VertexConsumerWrapper implements VertexConsumer {
   public abstract VertexConsumer[] getWrappedConsumers();

   @NotNull
   public VertexConsumer addVertex(float x, float y, float z) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.addVertex(x, y, z);
      }

      return this;
   }

   @NotNull
   public VertexConsumer setColor(int red, int green, int blue, int alpha) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.setColor(red, green, blue, alpha);
      }

      return this;
   }

   @NotNull
   public VertexConsumer setUv(float u, float v) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.setUv(u, v);
      }

      return this;
   }

   @NotNull
   public VertexConsumer setUv1(int u, int v) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.setUv1(u, v);
      }

      return this;
   }

   @NotNull
   public VertexConsumer setUv2(int u, int v) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.setUv2(u, v);
      }

      return this;
   }

   @NotNull
   public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
      for (VertexConsumer consumer : this.getWrappedConsumers()) {
         consumer.setNormal(normalX, normalY, normalZ);
      }

      return this;
   }
}
