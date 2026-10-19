package com.foxablazeultimate.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.RenderType.CompositeState.CompositeStateBuilder;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public final class FoxAblazeRenderTypes {
   public static final RenderType AZATHOTH_COIL = RenderType.create(
      "foxablazeultimate:azathoth_coil",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      32768,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
         .createCompositeState(false)
   );
   private static final Function<ResourceLocation, RenderType> ADDITIVE_EMISSIVE = Util.memoize(
      texture -> RenderType.create(
         "foxablazeultimate:additive_emissive",
         DefaultVertexFormat.NEW_ENTITY,
         Mode.QUADS,
         1536,
         false,
         true,
         CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
            .setTextureState(new TextureStateShard(texture, false, false))
            .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
            .setCullState(RenderStateShard.NO_CULL)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .setOverlayState(RenderStateShard.OVERLAY)
            .createCompositeState(false)
      )
   );
   private static ShaderInstance velgryndGlintShader;
   private static final ShaderStateShard VELGRYND_GLINT_SHADER = new ShaderStateShard(
      () -> Objects.requireNonNull(velgryndGlintShader, "Velgrynd glint shader has not been loaded")
   );
   private static ShaderInstance azathothGlintShader;
   private static final ShaderStateShard AZATHOTH_GLINT_SHADER = new ShaderStateShard(() -> Objects.requireNonNull(azathothGlintShader));
   public static final RenderType AZATHOTH_GLINT = voidGlint("azathoth_glint", false, false);
   public static final RenderType AZATHOTH_GLINT_TRANSLUCENT = voidGlint("azathoth_glint_translucent", false, true);
   public static final RenderType AZATHOTH_ENTITY_GLINT = voidGlint("azathoth_entity_glint", true, true);
   public static final RenderType AZATHOTH_ENTITY_GLINT_DIRECT = voidGlint("azathoth_entity_glint_direct", true, false);
   public static final RenderType VELGRYND_GLINT = RenderType.create(
      "foxablazeultimate:velgrynd_glint",
      DefaultVertexFormat.POSITION_TEX,
      Mode.QUADS,
      1536,
      CompositeState.builder()
         .setShaderState(VELGRYND_GLINT_SHADER)
         .setTextureState(new TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
         .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
         .setTexturingState(RenderStateShard.GLINT_TEXTURING)
         .createCompositeState(false)
   );
   public static final RenderType VELGRYND_GLINT_TRANSLUCENT = RenderType.create(
      "foxablazeultimate:velgrynd_glint_translucent",
      DefaultVertexFormat.POSITION_TEX,
      Mode.QUADS,
      1536,
      CompositeState.builder()
         .setShaderState(VELGRYND_GLINT_SHADER)
         .setTextureState(new TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
         .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
         .setTexturingState(RenderStateShard.GLINT_TEXTURING)
         .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
         .createCompositeState(false)
   );
   public static final RenderType VELGRYND_ENTITY_GLINT = RenderType.create(
      "foxablazeultimate:velgrynd_entity_glint",
      DefaultVertexFormat.POSITION_TEX,
      Mode.QUADS,
      1536,
      CompositeState.builder()
         .setShaderState(VELGRYND_GLINT_SHADER)
         .setTextureState(new TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
         .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
         .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
         .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
         .createCompositeState(false)
   );
   public static final RenderType VELGRYND_ENTITY_GLINT_DIRECT = RenderType.create(
      "foxablazeultimate:velgrynd_entity_glint_direct",
      DefaultVertexFormat.POSITION_TEX,
      Mode.QUADS,
      1536,
      CompositeState.builder()
         .setShaderState(VELGRYND_GLINT_SHADER)
         .setTextureState(new TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
         .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
         .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
         .createCompositeState(false)
   );

   private FoxAblazeRenderTypes() {
   }

   public static RenderType additiveEmissive(ResourceLocation texture) {
      return ADDITIVE_EMISSIVE.apply(texture);
   }

   public static void registerVelgryndGlintShader(RegisterShadersEvent event) throws IOException {
      event.registerShader(
         new ShaderInstance(
            event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "velgrynd_glint"), DefaultVertexFormat.POSITION_TEX
         ),
         shader -> velgryndGlintShader = shader
      );
      event.registerShader(
         new ShaderInstance(
            event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "azathoth_glint"), DefaultVertexFormat.POSITION_TEX
         ),
         shader -> azathothGlintShader = shader
      );
   }

   private static RenderType voidGlint(String name, boolean entity, boolean itemTarget) {
      CompositeStateBuilder state = CompositeState.builder()
         .setShaderState(AZATHOTH_GLINT_SHADER)
         .setTextureState(new TextureStateShard(entity ? ItemRenderer.ENCHANTED_GLINT_ENTITY : ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .setCullState(RenderStateShard.NO_CULL)
         .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setTexturingState(entity ? RenderStateShard.ENTITY_GLINT_TEXTURING : RenderStateShard.GLINT_TEXTURING);
      if (itemTarget) {
         state.setOutputState(RenderStateShard.ITEM_ENTITY_TARGET);
      }

      return RenderType.create("foxablazeultimate:" + name, DefaultVertexFormat.POSITION_TEX, Mode.QUADS, 1536, state.createCompositeState(false));
   }
}
