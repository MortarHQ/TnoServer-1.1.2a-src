package com.tom.storagemod.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.OptionalDouble;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.LineStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;

public class CustomRenderTypes extends RenderType {
   private static final RenderType LINES_NO_DEPTH = create(
      "toms_storage:lines_no_depth",
      DefaultVertexFormat.POSITION_COLOR_NORMAL,
      Mode.LINES,
      256,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LINES_SHADER)
         .setLineState(new LineStateShard(OptionalDouble.empty()))
         .setLayeringState(VIEW_OFFSET_Z_LAYERING)
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setOutputState(ITEM_ENTITY_TARGET)
         .setWriteMaskState(COLOR_DEPTH_WRITE)
         .setCullState(NO_CULL)
         .setDepthTestState(NO_DEPTH_TEST)
         .createCompositeState(false)
   );

   private CustomRenderTypes(
      String nameIn,
      VertexFormat formatIn,
      Mode drawModeIn,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      Runnable setupTaskIn,
      Runnable clearTaskIn
   ) {
      super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
   }

   public static RenderType linesNoDepth() {
      return LINES_NO_DEPTH;
   }
}
