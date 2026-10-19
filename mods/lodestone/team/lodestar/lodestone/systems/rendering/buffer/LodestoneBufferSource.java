package team.lodestar.lodestone.systems.rendering.buffer;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.SequencedMap;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;

public class LodestoneBufferSource extends BufferSource {
   protected final Supplier<ByteBufferBuilder> bufferSupplier;
   private final Collection<RenderType> additives = new HashSet<>();
   private final Collection<RenderType> nonAdditives = new HashSet<>();

   protected LodestoneBufferSource() {
      this(() -> new ByteBufferBuilder(786432), new LinkedHashMap<>());
   }

   protected LodestoneBufferSource(Supplier<ByteBufferBuilder> bufferSupplier, SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers) {
      super(bufferSupplier.get(), fixedBuffers);
      this.bufferSupplier = bufferSupplier;
   }

   public void endBatches(boolean additives) {
      for (RenderType type : additives ? this.getAdditives() : this.getNonAdditives()) {
         this.endBatch(type);
      }
   }

   @NotNull
   public VertexConsumer getBuffer(@NotNull LodestoneRenderTypeBuilder renderTypeBuilder) {
      return this.getBuffer(renderTypeBuilder.getRenderType());
   }

   @NotNull
   public VertexConsumer getBuffer(@NotNull RenderType renderType) {
      if (!this.fixedBuffers.containsKey(renderType)) {
         this.fixedBuffers.put(renderType, this.bufferSupplier.get());
         if (renderType instanceof LodestoneRenderType lodestoneRenderType) {
            Collection<RenderType> group = lodestoneRenderType.isAdditive() ? this.additives : this.nonAdditives;
            group.add(renderType);
         }
      }

      return super.getBuffer(renderType);
   }

   public void endBatch(@NotNull RenderType renderType) {
      LodestoneRenderHandler.updateUniforms(renderType);
      super.endBatch(renderType);
      LodestoneRenderHandler.resetUniforms(renderType);
   }

   public Collection<RenderType> getAdditives() {
      return this.additives;
   }

   public Collection<RenderType> getNonAdditives() {
      return this.nonAdditives;
   }
}
