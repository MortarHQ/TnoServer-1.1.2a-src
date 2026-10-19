package team.lodestar.lodestone.registry.client;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.ColorLogicStateShard;
import net.minecraft.client.renderer.RenderStateShard.CullStateShard;
import net.minecraft.client.renderer.RenderStateShard.DepthTestStateShard;
import net.minecraft.client.renderer.RenderStateShard.EmptyTextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.LayeringStateShard;
import net.minecraft.client.renderer.RenderStateShard.LightmapStateShard;
import net.minecraft.client.renderer.RenderStateShard.LineStateShard;
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard;
import net.minecraft.client.renderer.RenderStateShard.OverlayStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TexturingStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderStateShard.WriteMaskStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.RenderType.CompositeState.CompositeStateBuilder;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.rendeertype.ComplexRenderTypeToken;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

public class LodestoneRenderTypes extends RenderStateShard {
   public static final Runnable TRANSPARENT_FUNCTION = () -> RenderSystem.blendFuncSeparate(
      SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA
   );
   public static final Runnable ADDITIVE_FUNCTION = () -> RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   public static final EmptyTextureStateShard NO_TEXTURE = RenderStateShard.NO_TEXTURE;
   public static final LightmapStateShard LIGHTMAP = RenderStateShard.LIGHTMAP;
   public static final LightmapStateShard NO_LIGHTMAP = RenderStateShard.NO_LIGHTMAP;
   public static final CullStateShard CULL = RenderStateShard.CULL;
   public static final CullStateShard NO_CULL = RenderStateShard.NO_CULL;
   public static final WriteMaskStateShard COLOR_DEPTH_WRITE = RenderStateShard.COLOR_DEPTH_WRITE;
   public static final WriteMaskStateShard COLOR_WRITE = RenderStateShard.COLOR_WRITE;
   public static final LodestoneRenderType ADDITIVE_PARTICLE = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_PARTICLES),
      "additive_particle",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.ADDITIVE_TRANSPARENCY, LodestoneShaders.PARTICLE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_BLOCKS),
      "additive_block_particle",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.ADDITIVE_TRANSPARENCY, LodestoneShaders.PARTICLE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType ADDITIVE_BLOCK = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_BLOCKS),
      "additive_block",
      DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.ADDITIVE_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXTURE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType TRANSPARENT_PARTICLE = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_PARTICLES),
      "transparent_particle",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, LodestoneShaders.PARTICLE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType TRANSPARENT_BLOCK_PARTICLE = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_BLOCKS),
      "transparent_block_particle",
      DefaultVertexFormat.PARTICLE,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, LodestoneShaders.PARTICLE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType TRANSPARENT_BLOCK = createGenericRenderType(
      RenderTypeToken.createToken(TextureAtlas.LOCATION_BLOCKS),
      "transparent_block",
      DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
      Mode.QUADS,
      b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXTURE, NO_CULL, LIGHTMAP, COLOR_WRITE)
   );
   public static final LodestoneRenderType LUMITRANSPARENT_PARTICLE = TRANSPARENT_PARTICLE.copy("lumitransparent", ShaderUniformHandler.LUMITRANSPARENT);
   public static final LodestoneRenderType LUMITRANSPARENT_BLOCK_PARTICLE = TRANSPARENT_BLOCK_PARTICLE.copy(
      "lumitransparent", ShaderUniformHandler.LUMITRANSPARENT
   );
   public static final LodestoneRenderType LUMITRANSPARENT_BLOCK = TRANSPARENT_BLOCK.copy("lumitransparent", ShaderUniformHandler.LUMITRANSPARENT);
   public static final RenderTypeProvider TEXTURE = new RenderTypeProvider(
      token -> createGenericRenderType(
         token,
         "texture",
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.NO_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXTURE, CULL, LIGHTMAP)
      )
   );
   public static final RenderTypeProvider CUTOUT_TEXTURE = new RenderTypeProvider(
      token -> createGenericRenderType(
         token,
         "cutout_texture",
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.NO_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXTURE, CULL, LIGHTMAP)
      )
   );
   public static final RenderTypeProvider TRANSPARENT_TEXT = new RenderTypeProvider(
      token -> createGenericRenderType(
         token,
         "transparent_text",
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.NORMAL_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXT, LIGHTMAP)
      )
   );
   public static final RenderTypeProvider ADDITIVE_TEXT = new RenderTypeProvider(
      token -> createGenericRenderType(
         token,
         "additive_text",
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.ADDITIVE_TRANSPARENCY, LodestoneShaders.LODESTONE_TEXT, LIGHTMAP, COLOR_WRITE)
      )
   );
   public static final RenderTypeProvider TRANSPARENT_TEXTURE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_texture", token, LodestoneShaders.LODESTONE_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_DISTORTED_TEXTURE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_distorted_texture", token, LodestoneShaders.DISTORTED_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_triangle_texture", token, LodestoneShaders.TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_two_sided_triangle_texture", token, LodestoneShaders.TWO_SIDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_ROUNDED_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_rounded_triangle_texture", token, LodestoneShaders.ROUNDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_SCROLLING_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_scrolling_texture_triangle", token, LodestoneShaders.ROUNDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider TRANSPARENT_NINE_SLICE_TEXTURE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_nine_slice_texture", token, LodestoneShaders.NINE_SLICE)
   );
   public static final RenderTypeProvider TRANSPARENT_DISTORTED_NINE_SLICE_TEXTURE = new RenderTypeProvider(
      token -> createTransparentRenderType("transparent_distorted_nine_slice_texture", token, LodestoneShaders.DISTORTED_NINE_SLICE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_TEXTURE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_texture", token, LodestoneShaders.LODESTONE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_DISTORTED_TEXTURE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_distorted_texture", token, LodestoneShaders.DISTORTED_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_triangle_texture", token, LodestoneShaders.TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_two_sided_triangle_texture", token, LodestoneShaders.TWO_SIDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_ROUNDED_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_rounded_triangle_texture", token, LodestoneShaders.ROUNDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_SCROLLING_TEXTURE_TRIANGLE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_scrolling_texture_triangle", token, LodestoneShaders.ROUNDED_TRIANGLE_TEXTURE)
   );
   public static final RenderTypeProvider ADDITIVE_NINE_SLICE_TEXTURE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_nine_slice_texture", token, LodestoneShaders.NINE_SLICE)
   );
   public static final RenderTypeProvider ADDITIVE_DISTORTED_NINE_SLICE_TEXTURE = new RenderTypeProvider(
      token -> createAdditiveRenderType("additive_distorted_nine_slice_texture", token, LodestoneShaders.DISTORTED_NINE_SLICE_TEXTURE)
   );
   public static final RenderTypeProvider TEXTURE_FADE = new RenderTypeProvider(
      token -> createGenericRenderType(
         token,
         "texture_fade",
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.NORMAL_TRANSPARENCY, LodestoneShaders.TEXTURE_FADE, CULL, LIGHTMAP)
      )
   );
   public static final RenderTypeProvider DEBUG_SDF = new RenderTypeProvider(
      token -> createGenericRenderType(token, "debug_sdf", DefaultVertexFormat.POSITION, Mode.QUADS, builder(token, LodestoneShaders.DEBUG_SDF, CULL))
   );

   public LodestoneRenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
      super(p_110161_, p_110162_, p_110163_);
   }

   public static LodestoneRenderType createTransparentRenderType(String name, RenderTypeToken token, ShaderHolder shader) {
      return createGenericRenderType(
         token,
         name,
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.NORMAL_TRANSPARENCY, shader, CULL, LIGHTMAP, COLOR_WRITE)
      );
   }

   public static LodestoneRenderType createAdditiveRenderType(String name, RenderTypeToken token, ShaderHolder shader) {
      return createGenericRenderType(
         token,
         name,
         DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
         Mode.QUADS,
         builder(token, StateShards.ADDITIVE_TRANSPARENCY, shader, CULL, LIGHTMAP, COLOR_WRITE)
      );
   }

   public static LodestoneRenderType createGenericRenderType(
      @Nullable RenderTypeToken token, String name, VertexFormat format, Mode mode, Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier
   ) {
      LodestoneRenderTypes.LodestoneCompositeStateBuilder builder = new LodestoneRenderTypes.LodestoneCompositeStateBuilder();
      if (token != null) {
         builder.setTextureState(token);
      }

      modifier.accept(builder);
      return createGenericRenderType(token, name, format, mode, builder);
   }

   public static LodestoneRenderType createGenericRenderType(
      @Nullable RenderTypeToken token, String name, VertexFormat format, Mode mode, LodestoneRenderTypes.LodestoneCompositeStateBuilder builder
   ) {
      ShaderUniformHandler uniformHandler = null;
      if (token instanceof ComplexRenderTypeToken complex) {
         uniformHandler = complex.getUniformHandler();
         if (complex.getModifier() != null) {
            complex.getModifier().accept(builder);
         }
      }

      Mode renderTypeMode = builder.modeOverride != null ? builder.modeOverride : mode;
      CompositeState compositeState = builder.createCompositeState();
      return new LodestoneRenderType(name, format, renderTypeMode, 256, false, true, compositeState, uniformHandler);
   }

   public static LodestoneRenderTypes.LodestoneCompositeStateBuilder builder(Object... objects) {
      return builder().setStateShards(objects);
   }

   public static LodestoneRenderTypes.LodestoneCompositeStateBuilder builder() {
      return new LodestoneRenderTypes.LodestoneCompositeStateBuilder();
   }

   public static class LodestoneCompositeStateBuilder extends CompositeStateBuilder {
      protected Mode modeOverride;

      LodestoneCompositeStateBuilder() {
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder replaceVertexFormat(Mode mode) {
         this.modeOverride = mode;
         return this;
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder accepts(Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier) {
         modifier.accept(this);
         return this;
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setStateShards(Object... objects) {
         for (Object object : objects) {
            if (object instanceof CompositeState state) {
               this.copyState(state);
            }
         }

         for (Object object : objects) {
            switch (object) {
               case null:
                  LodestoneLib.LOGGER.warn("Null object passed for composite state, ignoring.");
                  break;
               case ResourceLocation texture:
                  this.setTextureState(texture);
                  break;
               case RenderTypeToken token:
                  this.setTextureState(token);
                  break;
               case EmptyTextureStateShard shard:
                  this.setTextureState(shard);
                  break;
               case ShaderInstance shaderInstance:
                  this.setShaderState(shaderInstance);
                  break;
               case ShaderHolder shaderHolder:
                  this.setShaderState(shaderHolder);
                  break;
               case ShaderStateShard shard:
                  this.setShaderState(shard);
                  break;
               case TransparencyStateShard shard:
                  this.setTransparencyState(shard);
                  break;
               case DepthTestStateShard shard:
                  this.setDepthTestState(shard);
                  break;
               case CullStateShard shard:
                  this.setCullState(shard);
                  break;
               case LightmapStateShard shard:
                  this.setLightmapState(shard);
                  break;
               case OverlayStateShard shard:
                  this.setOverlayState(shard);
                  break;
               case LayeringStateShard shard:
                  this.setLayeringState(shard);
                  break;
               case OutputStateShard shard:
                  this.setOutputState(shard);
                  break;
               case TexturingStateShard shard:
                  this.setTexturingState(shard);
                  break;
               case WriteMaskStateShard shard:
                  this.setWriteMaskState(shard);
                  break;
               case LineStateShard shard:
                  this.setLineState(shard);
                  break;
               case ColorLogicStateShard shard:
                  this.setColorLogicState(shard);
                  break;
               default:
                  LodestoneLib.LOGGER.warn("Unsupported object passed for composite state: {}, {}", object.getClass().getName(), object);
            }
         }

         return this;
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder copyState(CompositeState state) {
         this.setTextureState(state.textureState);
         this.setShaderState(state.shaderState);
         this.setTransparencyState(state.transparencyState);
         this.setDepthTestState(state.depthTestState);
         this.setCullState(state.cullState);
         this.setLightmapState(state.lightmapState);
         this.setOverlayState(state.overlayState);
         this.setLayeringState(state.layeringState);
         this.setOutputState(state.outputState);
         this.setTexturingState(state.texturingState);
         this.setWriteMaskState(state.writeMaskState);
         this.setLineState(state.lineState);
         this.setColorLogicState(state.colorLogicState);
         return this;
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setTextureState(RenderTypeToken token) {
         return this.setTextureState(token.getTexture());
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setTextureState(ResourceLocation texture) {
         return this.setTextureState(new TextureStateShard(texture, false, false));
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setShaderState(ShaderInstance shaderInstance) {
         return this.setShaderState(new ShaderStateShard(() -> shaderInstance));
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setShaderState(ShaderHolder shaderHolder) {
         return this.setShaderState(shaderHolder.getShard());
      }

      public CompositeState createCompositeState() {
         return super.createCompositeState(true);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setTextureState(EmptyTextureStateShard pTextureState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setTextureState(pTextureState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setShaderState(ShaderStateShard pShaderState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setShaderState(pShaderState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setTransparencyState(TransparencyStateShard pTransparencyState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setTransparencyState(pTransparencyState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setDepthTestState(DepthTestStateShard pDepthTestState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setDepthTestState(pDepthTestState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setCullState(CullStateShard pCullState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setCullState(pCullState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setLightmapState(LightmapStateShard pLightmapState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setLightmapState(pLightmapState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setOverlayState(OverlayStateShard pOverlayState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setOverlayState(pOverlayState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setLayeringState(LayeringStateShard pLayerState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setLayeringState(pLayerState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setOutputState(OutputStateShard pOutputState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setOutputState(pOutputState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setTexturingState(TexturingStateShard pTexturingState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setTexturingState(pTexturingState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setWriteMaskState(WriteMaskStateShard pWriteMaskState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setWriteMaskState(pWriteMaskState);
      }

      public LodestoneRenderTypes.LodestoneCompositeStateBuilder setLineState(LineStateShard pLineState) {
         return (LodestoneRenderTypes.LodestoneCompositeStateBuilder)super.setLineState(pLineState);
      }
   }
}
