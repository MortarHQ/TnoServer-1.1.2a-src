package team.lodestar.lodestone.systems.rendering.vertexconsumer.offset;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.rendering.vertexconsumer.VertexConsumerWrapper;

public class UVOffsetVertexConsumer extends VertexConsumerWrapper {
   protected final VertexConsumer consumer;
   protected final VertexConsumer[] cachedArray;
   protected float uOffset;
   protected float vOffset;

   public UVOffsetVertexConsumer(VertexConsumer consumer) {
      this.consumer = consumer;
      this.cachedArray = new VertexConsumer[]{consumer};
   }

   @Override
   public VertexConsumer[] getWrappedConsumers() {
      return this.cachedArray;
   }

   public UVOffsetVertexConsumer setOffset(float uOffset, float vOffset) {
      this.uOffset = uOffset;
      this.vOffset = vOffset;
      return this;
   }

   @NotNull
   @Override
   public VertexConsumer setUv(float u, float v) {
      return super.setUv(u + this.uOffset, v + this.vOffset);
   }
}
