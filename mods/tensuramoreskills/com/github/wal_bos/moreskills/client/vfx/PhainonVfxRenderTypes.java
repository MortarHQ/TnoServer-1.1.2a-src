package com.github.wal_bos.moreskills.client.vfx;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.io.IOException;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public final class PhainonVfxRenderTypes extends RenderType {
   private static ShaderInstance phainonGoldAuraShader;
   private static ShaderInstance asmodayBlackHoleShader;
   private static final ShaderStateShard PHAINON_GOLD_AURA_SHADER = new ShaderStateShard(() -> phainonGoldAuraShader);
   private static final ShaderStateShard ASMODAY_BLACK_HOLE_SHADER = new ShaderStateShard(() -> asmodayBlackHoleShader);
   private static final Function<ResourceLocation, RenderType> PHAINON_GOLD_AURA = texture -> RenderType.create(
      "tensuramoreskills:phainon_gold_aura",
      DefaultVertexFormat.NEW_ENTITY,
      Mode.QUADS,
      512,
      false,
      true,
      CompositeState.builder()
         .setShaderState(PHAINON_GOLD_AURA_SHADER)
         .setTextureState(new TextureStateShard(texture, false, false))
         .setTransparencyState(ADDITIVE_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setLightmapState(LIGHTMAP)
         .setOverlayState(OVERLAY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false)
   );
   private static final RenderType PHAINON_ASMODAY_BLACK_HOLE = RenderType.create(
      "tensuramoreskills:phainon_asmoday_black_hole",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      1536,
      false,
      true,
      CompositeState.builder()
         .setShaderState(ASMODAY_BLACK_HOLE_SHADER)
         .setTransparencyState(ADDITIVE_TRANSPARENCY)
         .setCullState(NO_CULL)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false)
   );

   private PhainonVfxRenderTypes(
      String name, VertexFormat format, Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState
   ) {
      super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
   }

   public static RenderType phainonGoldAura(ResourceLocation texture) {
      return PHAINON_GOLD_AURA.apply(texture);
   }

   public static RenderType phainonAsmodayBlackHole() {
      return PHAINON_ASMODAY_BLACK_HOLE;
   }

   public static void registerShaders(RegisterShadersEvent event) {
      try {
         event.registerShader(
            new ShaderInstance(
               event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_gold_aura"), DefaultVertexFormat.NEW_ENTITY
            ),
            shader -> phainonGoldAuraShader = shader
         );
         event.registerShader(
            new ShaderInstance(
               event.getResourceProvider(),
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_black_hole"),
               DefaultVertexFormat.POSITION_COLOR
            ),
            shader -> asmodayBlackHoleShader = shader
         );
      } catch (IOException exception) {
         throw new RuntimeException("Failed to load Phainon VFX shaders", exception);
      }
   }
}
