package net.unusual.block_factorys_bosses.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.function.BiFunction;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;

public class BossesRiseRenderTypes {
   public static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE_CULL = Util.memoize(
      (textureLocation, outline) -> {
         CompositeState state = CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
            .setTextureState(new TextureStateShard(textureLocation, false, false))
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(outline);
         return RenderType.create(
            "block_factorys_bosses:entity_translucent_emissive_cull", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 1536, true, false, state
         );
      }
   );

   public static RenderType entityTranslucentEmissiveCull(ResourceLocation textureLocation, boolean outline) {
      return ENTITY_TRANSLUCENT_EMISSIVE_CULL.apply(textureLocation, outline);
   }

   public static RenderType entityTranslucentEmissiveCull(ResourceLocation textureLocation) {
      return entityTranslucentEmissiveCull(textureLocation, true);
   }
}
