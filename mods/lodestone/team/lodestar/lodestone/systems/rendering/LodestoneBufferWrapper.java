package team.lodestar.lodestone.systems.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeRenderType;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

public class LodestoneBufferWrapper implements MultiBufferSource {
   public final RenderTypeProvider provider;
   public final MultiBufferSource buffer;

   public LodestoneBufferWrapper(RenderTypeProvider provider, MultiBufferSource buffer) {
      this.provider = provider;
      this.buffer = buffer;
   }

   public VertexConsumer getBuffer(RenderType renderType) {
      if (renderType instanceof CompositeRenderType composite && composite.state.textureState instanceof TextureStateShard textureState) {
         LodestoneRenderTypeBuilder key = this.provider.apply(RenderTypeToken.createToken(textureState));
         return this.buffer.getBuffer(key.getRenderType());
      } else {
         return this.buffer.getBuffer(renderType);
      }
   }
}
