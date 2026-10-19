package xaero.map.graphics;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import xaero.lib.client.graphics.XaeroRenderType;
import xaero.lib.client.graphics.XaeroRenderType.MultiPhaseBuilder;
import xaero.lib.client.graphics.XaeroRenderType.MultiPhaseRenderType;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.map.WorldMap;

public class CustomRenderTypes extends RenderType {
   public static final RenderType GUI_BILINEAR;
   public static final RenderType GUI_BILINEAR_PREMULTIPLIED;
   public static final RenderType GUI_NEAREST;
   public static final RenderType MAP;
   public static final RenderType MAP_COLOR_OVERLAY;
   public static final RenderType MAP_FRAME_TEXTURE_OVER_TRANSPARENT;
   public static final RenderType MAP_COLOR_FILLER;
   public static final RenderType MAP_ELEMENT_TEXT_BG;

   private CustomRenderTypes(
      String name,
      VertexFormat vertexFormat,
      Mode drawMode,
      int expectedBufferSize,
      boolean hasCrumbling,
      boolean translucent,
      Runnable startAction,
      Runnable endAction
   ) {
      super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
   }

   static {
      ImmutableList<RenderStateShard> multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(WorldMap.guiTextures, false, false))
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_NEAREST = new MultiPhaseRenderType("xaero_wm_gui_nearest", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(WorldMap.guiTextures, true, false))
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_BILINEAR = new MultiPhaseRenderType("xaero_wm_gui_bilinear", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(WorldMap.guiTextures, true, false))
         .transparency(XaeroRenderType.PREMULTIPLIED_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX_PRE))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_BILINEAR_PREMULTIPLIED = new MultiPhaseRenderType(
         "xaero_wm_gui_bilinear_pre", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(WorldMap.guiTextures, false, false))
         .transparency(XaeroRenderType.DEST_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.WORLD_MAP))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP = new MultiPhaseRenderType("xaero_wm_map_with_light", DefaultVertexFormat.POSITION_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_COLOR_OVERLAY = new MultiPhaseRenderType(
         "xaero_wm_world_map_overlay", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(WorldMap.guiTextures, true, false))
         .transparency(XaeroRenderType.DEST_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_FRAME_TEXTURE_OVER_TRANSPARENT = new MultiPhaseRenderType(
         "xaero_wm_frame_texture", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_COLOR_FILLER = new MultiPhaseRenderType(
         "xaero_wm_world_map_filler", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_ELEMENT_TEXT_BG = new MultiPhaseRenderType(
         "xaero_wm_world_map_waypoint_name_bg", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 42, false, false, multiPhaseParameters
      );
   }
}
