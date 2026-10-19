package cn.leolezury.eternalstarlight.common.client.visual;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;

public class DelayedMultiBufferSource extends BufferSource {
   public DelayedMultiBufferSource(ByteBufferBuilder builder) {
      super(builder, new Object2ObjectLinkedOpenHashMap());
   }

   public VertexConsumer getBuffer(RenderType renderType) {
      if (!this.fixedBuffers.containsKey(renderType)) {
         this.fixedBuffers.put(renderType, new ByteBufferBuilder(renderType.bufferSize()));
      }

      return super.getBuffer(renderType);
   }

   public void endBatch() {
      for (RenderType renderType : this.fixedBuffers.keySet()) {
         this.endBatch(renderType);
         ((ByteBufferBuilder)this.fixedBuffers.get(renderType)).close();
      }

      this.fixedBuffers.clear();
   }
}
