package com.tensura_tno.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.io.IOException;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public final class RimuruPlushRenderTypes extends RenderStateShard {
   private static ShaderInstance itemShader;
   private static final ShaderStateShard ITEM_SHADER = new ShaderStateShard(() -> itemShader);
   private static final Function<ResourceLocation, RenderType> ITEM = Util.memoize(
      texture -> RenderType.create(
         "tensura_tno_plush_item",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         256,
         true,
         false,
         CompositeState.builder()
            .setShaderState(ITEM_SHADER)
            .setTextureState(new TextureStateShard(texture, false, false))
            .setTransparencyState(NO_TRANSPARENCY)
            .setCullState(CULL)
            .setLightmapState(LIGHTMAP)
            .setOverlayState(OVERLAY)
            .setWriteMaskState(COLOR_DEPTH_WRITE)
            .createCompositeState(true)
      )
   );

   private RimuruPlushRenderTypes() {
      super("tensura_tno_plush_item", () -> {}, () -> {});
   }

   public static void registerShaders(RegisterShadersEvent event) throws IOException {
      event.registerShader(
         new ShaderInstance(
            event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("tensura_tno", "rimuru_plush_item"), DefaultVertexFormat.NEW_ENTITY
         ),
         shader -> itemShader = shader
      );
   }

   public static RenderType plush(ResourceLocation texture) {
      return RimuruPlushShaderCompat.useVanillaRenderType() ? RenderType.entityCutout(texture) : ITEM.apply(texture);
   }
}
