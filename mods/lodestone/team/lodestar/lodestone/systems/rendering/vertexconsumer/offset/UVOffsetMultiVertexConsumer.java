package team.lodestar.lodestone.systems.rendering.vertexconsumer.offset;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.rendering.vertexconsumer.MultiVertexConsumer;

public class UVOffsetMultiVertexConsumer extends MultiVertexConsumer {
   protected float uOffset;
   protected float vOffset;

   public UVOffsetMultiVertexConsumer(VertexConsumer... consumers) {
      super(consumers);
   }

   public UVOffsetMultiVertexConsumer setOffset(float uOffset, float vOffset) {
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
