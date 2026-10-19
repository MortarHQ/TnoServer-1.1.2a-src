package xaero.common.graphics;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.CullStateShard;
import net.minecraft.client.renderer.RenderStateShard.DepthTestStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderStateShard.WriteMaskStateShard;
import net.minecraft.resources.ResourceLocation;
import xaero.hud.render.TextureLocations;
import xaero.lib.client.graphics.XaeroRenderType;
import xaero.lib.client.graphics.XaeroRenderType.MultiPhaseBuilder;
import xaero.lib.client.graphics.XaeroRenderType.MultiPhaseRenderType;
import xaero.lib.client.graphics.shader.LibShaders;

public class CustomRenderTypes extends RenderType {
   public static final RenderType GUI_BILINEAR;
   public static final RenderType GUI_BILINEAR_PREMULTIPLIED;
   public static final RenderType GUI_BILINEAR_NO_DEPTH;
   public static final RenderType GUI_NEAREST;
   public static final RenderType COLORED_WAYPOINTS_BGS;
   public static final RenderType MAP_CHUNK_OVERLAY;
   public static final RenderType MAP_LINES;
   public static final RenderType RADAR_NAME_BGS;
   public static final RenderType DEPTH_CLEAR;

   public static RenderType entityIconRenderType(ResourceLocation texture, CustomRenderTypes.EntityIconLayerPhases layerPhases) {
      ImmutableList<RenderStateShard> rendertype$state = new MultiPhaseBuilder()
         .texture(layerPhases.texture)
         .transparency(layerPhases.transparency)
         .shader(layerPhases.shader)
         .depthTest(layerPhases.depthTest)
         .writeMaskState(layerPhases.writeMask)
         .cull(layerPhases.cull)
         .lightmap(LIGHTMAP)
         .overlay(OVERLAY)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      return new MultiPhaseRenderType("xaero_entity_icon", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, true, true, rendertype$state);
   }

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

   public static CustomRenderTypes.EntityIconLayerPhases getBasicEntityIconLayerPhases(ResourceLocation texture) {
      return new CustomRenderTypes.EntityIconLayerPhases(
         new TextureStateShard(texture, false, false),
         XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY,
         LEQUAL_DEPTH_TEST,
         COLOR_DEPTH_WRITE,
         NO_CULL,
         RENDERTYPE_ENTITY_TRANSLUCENT_SHADER
      );
   }

   static {
      ImmutableList<RenderStateShard> multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(TextureLocations.GUI_TEXTURES, false, false))
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_NEAREST = new MultiPhaseRenderType("xaero_gui_nearest", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(TextureLocations.GUI_TEXTURES, true, false))
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_BILINEAR = new MultiPhaseRenderType("xaero_gui_bilinear", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(TextureLocations.GUI_TEXTURES, true, false))
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .depthTest(RenderStateShard.NO_DEPTH_TEST)
         .build();
      GUI_BILINEAR_NO_DEPTH = new MultiPhaseRenderType(
         "xaero_gui_bilinear_no_depth", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .texture(new TextureStateShard(TextureLocations.GUI_TEXTURES, true, false))
         .transparency(XaeroRenderType.PREMULTIPLIED_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_TEX_PRE))
         .cull(NO_CULL)
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      GUI_BILINEAR_PREMULTIPLIED = new MultiPhaseRenderType(
         "xaero_gui_bilinear_pre", XaeroRenderType.POSITION_COLOR_TEX, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .target(XaeroRenderType.KEEP_TARGET)
         .layering(XaeroRenderType.DEFAULT_POLYGON_OFFSET_LAYERING)
         .build();
      COLORED_WAYPOINTS_BGS = new MultiPhaseRenderType(
         "xaero_colored_waypoints", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .target(XaeroRenderType.KEEP_TARGET)
         .layering(XaeroRenderType.DEFAULT_POLYGON_OFFSET_LAYERING)
         .build();
      RADAR_NAME_BGS = new MultiPhaseRenderType("xaero_radar_name_bg", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR))
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_CHUNK_OVERLAY = new MultiPhaseRenderType(
         "xaero_chunk_overlay", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters
      );
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.LINES_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.FRAMEBUFFER_LINES))
         .target(XaeroRenderType.KEEP_TARGET)
         .build();
      MAP_LINES = new MultiPhaseRenderType("xaero_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, Mode.LINES, 256, false, false, multiPhaseParameters);
      multiPhaseParameters = new MultiPhaseBuilder()
         .transparency(XaeroRenderType.DEFAULT_TRANSLUCENT_TRANSPARENCY)
         .shader(new ShaderStateShard(() -> LibShaders.POSITION_COLOR_NO_ALPHA_TEST))
         .target(XaeroRenderType.KEEP_TARGET)
         .depthTest(GREATER_DEPTH_TEST)
         .writeMaskState(DEPTH_WRITE)
         .build();
      DEPTH_CLEAR = new MultiPhaseRenderType("xaero_depth_clear", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 256, false, false, multiPhaseParameters);
   }

   public static class EntityIconLayerPhases {
      public TextureStateShard texture;
      public TransparencyStateShard transparency;
      public DepthTestStateShard depthTest;
      public WriteMaskStateShard writeMask;
      public CullStateShard cull;
      public ShaderStateShard shader;

      public EntityIconLayerPhases(Object texture, Object transparency, Object depthTest, Object writeMask, Object cull, Object shader) {
         this.texture = (TextureStateShard)texture;
         this.transparency = (TransparencyStateShard)transparency;
         this.depthTest = (DepthTestStateShard)depthTest;
         this.writeMask = (WriteMaskStateShard)writeMask;
         this.cull = (CullStateShard)cull;
         this.shader = (ShaderStateShard)shader;
      }
   }
}
