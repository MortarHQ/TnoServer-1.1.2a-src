package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public abstract class ESRenderType extends RenderType {
   public static final RenderType PORTAL = create(
      "eternal_starlight:starlight_portal",
      DefaultVertexFormat.BLOCK,
      Mode.QUADS,
      1536,
      true,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(ESShaders::getRenderTypeStarlightPortal))
         .setTextureState(NO_TEXTURE)
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .createCompositeState(true)
   );
   public static final RenderType ECLIPSE = create(
      "eternal_starlight:eclipse",
      DefaultVertexFormat.BLOCK,
      Mode.QUADS,
      1536,
      true,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(ESShaders::getRenderTypeEclipse))
         .setTextureState(NO_TEXTURE)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .createCompositeState(true)
   );
   public static final RenderType PARTICLE_NO_DEPTH = create(
      "eternal_starlight:particle_no_depth",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      1536,
      true,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getParticleShader))
         .setTextureState(new TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(true)
   );
   public static final RenderType PARTICLE_ADDITIVE_GLOW = create(
      "eternal_starlight:particle_additive_glow",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      1536,
      true,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getParticleShader))
         .setTextureState(new TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(true)
   );
   public static final RenderType DRAGON_RAYS_QUADS = create(
      "eternal_starlight:dragon_rays_quads",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      1536,
      true,
      true,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
         .setTextureState(NO_TEXTURE)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(true)
   );
   public static final Function<ResourceLocation, RenderType> TRANSLUCENT_ADDITIVE_GLOW = Util.memoize(
      location -> create(
         "eternal_starlight:entity_translucent_additive_glow",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         1536,
         true,
         true,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
            .setTextureState(new TextureStateShard(location, false, false))
            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setCullState(NO_CULL)
            .setWriteMaskState(COLOR_WRITE)
            .setOverlayState(OVERLAY)
            .createCompositeState(true)
      )
   );
   public static final Function<ResourceLocation, RenderType> TRANSLUCENT_GLOW = Util.memoize(
      location -> create(
         "eternal_starlight:entity_translucent_glow",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         1536,
         true,
         true,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
            .setTextureState(new TextureStateShard(location, false, false))
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setCullState(NO_CULL)
            .setWriteMaskState(COLOR_WRITE)
            .setOverlayState(OVERLAY)
            .createCompositeState(true)
      )
   );
   public static final Function<ResourceLocation, RenderType> TRANSLUCENT_NO_DEPTH = Util.memoize(
      location -> create(
         "eternal_starlight:entity_translucent_no_depth",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         1536,
         true,
         true,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
            .setTextureState(new TextureStateShard(location, false, false))
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setCullState(NO_CULL)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setOverlayState(OVERLAY)
            .createCompositeState(true)
      )
   );
   public static final Function<ResourceLocation, RenderType> CUTOUT_GLOW = Util.memoize(
      location -> create(
         "eternal_starlight:entity_cutout_glow",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         256,
         true,
         true,
         CompositeState.builder()
            .setTextureState(new TextureStateShard(location, false, false))
            .setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
            .setTransparencyState(NO_TRANSPARENCY)
            .setLightmapState(LIGHTMAP)
            .setOverlayState(OVERLAY)
            .createCompositeState(false)
      )
   );

   public ESRenderType(
      String string,
      VertexFormat vertexFormat,
      Mode mode,
      int bufferSize,
      boolean affectsCrumbling,
      boolean sortOnUpload,
      Runnable setupState,
      Runnable clearState
   ) {
      super(string, vertexFormat, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
   }

   public static RenderType entityTranslucentAdditiveGlow(ResourceLocation location) {
      return TRANSLUCENT_ADDITIVE_GLOW.apply(location);
   }

   public static RenderType entityTranslucentGlow(ResourceLocation location) {
      return TRANSLUCENT_GLOW.apply(location);
   }

   public static RenderType entityTranslucentNoDepth(ResourceLocation location) {
      return TRANSLUCENT_NO_DEPTH.apply(location);
   }

   public static RenderType entityCutoutGlow(ResourceLocation location) {
      return CUTOUT_GLOW.apply(location);
   }
}
