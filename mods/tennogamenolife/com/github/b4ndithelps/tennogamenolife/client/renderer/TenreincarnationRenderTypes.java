package com.github.b4ndithelps.tennogamenolife.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;

public final class TenreincarnationRenderTypes extends RenderType {
   private static final ResourceLocation GODLY_SMITE_CHARGE_CIRCLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tennogamenolife", "textures/entity/godly_smite_charge_circle.png"
   );
   private static final ResourceLocation GODLY_SMITE_FIREBALL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tennogamenolife", "textures/entity/vfx/godly_smite_fireball.png"
   );
   public static final RenderType STRIKE_BEAM = RenderType.create(
      "tennogamenolife:strike_beam",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      4096,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .setOutputState(WEATHER_TARGET)
         .createCompositeState(false)
   );
   public static final RenderType BARRIER_SPHERE = RenderType.create(
      "tennogamenolife:fourth_protection_sphere",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.TRIANGLES,
      4096,
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
   public static final RenderType GODLY_SMITE_SPHERE = RenderType.create(
      "tennogamenolife:godly_smite_sphere",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.TRIANGLES,
      131072,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .setOutputState(WEATHER_TARGET)
         .createCompositeState(false)
   );
   public static final RenderType GODLY_SMITE_FIREBALL = RenderType.create(
      "tennogamenolife:godly_smite_fireball",
      DefaultVertexFormat.POSITION_TEX_COLOR,
      Mode.TRIANGLES,
      262144,
      false,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getPositionTexColorShader))
         .setTextureState(new TextureStateShard(GODLY_SMITE_FIREBALL_TEXTURE, true, false))
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_DEPTH_WRITE)
         .setOutputState(WEATHER_TARGET)
         .createCompositeState(false)
   );
   public static final RenderType GODLY_SMITE_STREAK = RenderType.create(
      "tennogamenolife:godly_smite_streak",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      131072,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .setOutputState(WEATHER_TARGET)
         .createCompositeState(false)
   );
   public static final RenderType GODLY_SMITE_CHARGE_GLOW = RenderType.create(
      "tennogamenolife:godly_smite_charge_glow",
      DefaultVertexFormat.POSITION_TEX_COLOR,
      Mode.QUADS,
      4096,
      false,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getPositionTexColorShader))
         .setTextureState(new TextureStateShard(GODLY_SMITE_CHARGE_CIRCLE_TEXTURE, true, false))
         .setTransparencyState(LIGHTNING_TRANSPARENCY)
         .setDepthTestState(LEQUAL_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .createCompositeState(false)
   );
   public static final RenderType GODLY_SMITE_WHITEOUT = RenderType.create(
      "tennogamenolife:godly_smite_whiteout",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.TRIANGLES,
      65536,
      false,
      true,
      CompositeState.builder()
         .setShaderState(new ShaderStateShard(GameRenderer::getPositionColorShader))
         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
         .setDepthTestState(NO_DEPTH_TEST)
         .setCullState(NO_CULL)
         .setWriteMaskState(COLOR_WRITE)
         .setOutputState(WEATHER_TARGET)
         .createCompositeState(false)
   );

   private TenreincarnationRenderTypes(
      String name, VertexFormat format, Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState
   ) {
      super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
   }
}
