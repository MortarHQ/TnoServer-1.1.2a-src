package team.lodestar.lodestone.systems.rendering.vertexconsumer;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class MultiVertexConsumer extends VertexConsumerWrapper {
   protected final VertexConsumer[] consumers;

   public MultiVertexConsumer(VertexConsumer... consumers) {
      this.consumers = consumers;
   }

   @Override
   public VertexConsumer[] getWrappedConsumers() {
      return this.consumers;
   }
}
