package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;

public final class ExtrasRenderTypes extends RenderStateShard {
   public static final RenderType ALIGNMENT_FIELD = RenderType.create(
      "tno_tensura_extras:alignment_field",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.TRIANGLES,
      32768,
      false,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getPositionColorShader))
         .setLayeringState(NO_LAYERING)
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false)
   );

   private ExtrasRenderTypes() {
      super("", () -> {}, () -> {});
   }
}
