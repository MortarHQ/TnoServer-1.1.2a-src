package com.bobmowzie.mowziesmobs.client.render;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.OffsetTexturingStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class MMRenderType extends RenderType {
   public static ParticleRenderType PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH = new ParticleRenderType() {
      public BufferBuilder begin(@NotNull Tesselator tesselator, @NotNull TextureManager manager) {
         Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH";
      }
   };
   public static ParticleRenderType TERRAIN_SHEET_NO_CULL = new ParticleRenderType() {
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.depthMask(true);
         RenderSystem.disableCull();
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
         return "TERRAIN_SHEET_NO_CULL";
      }
   };

   public MMRenderType(
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

   public static RenderType getGlowingEffect(ResourceLocation locationIn) {
      TextureStateShard shard = new TextureStateShard(locationIn, false, false);
      CompositeState rendertype$state = CompositeState.builder()
         .setTextureState(shard)
         .setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setOverlayState(OVERLAY)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false);
      return create("glow_effect", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, true, true, rendertype$state);
   }

   public static RenderType getSolarFlare(ResourceLocation locationIn) {
      TextureStateShard shard = new TextureStateShard(locationIn, false, false);
      CompositeState rendertype$state = CompositeState.builder()
         .setTextureState(shard)
         .setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(NO_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setOverlayState(NO_OVERLAY)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false);
      return create("solar_flare", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, true, false, rendertype$state);
   }

   public static RenderType highlight(ResourceLocation resourceLocation, float x, float y) {
      return create(
         "highlight",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         256,
         false,
         true,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
            .setTextureState(new TextureStateShard(resourceLocation, false, false))
            .setTexturingState(new OffsetTexturingStateShard(x, y))
            .setTransparencyState(ADDITIVE_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setLayeringState(POLYGON_OFFSET_LAYERING)
            .createCompositeState(false)
      );
   }
}
