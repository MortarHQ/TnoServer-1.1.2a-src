package dev.foxboss.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;

final class FoxVfxRenderTypes extends RenderType {
   static final RenderType GLOW = create(
      "foxboss_glow",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      1536,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setWriteMaskState(COLOR_WRITE)
         .setCullState(NO_CULL)
         .createCompositeState(false)
   );

   private FoxVfxRenderTypes() {
      super("foxboss_vfx_types", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 1536, false, false, () -> {}, () -> {});
   }
}
