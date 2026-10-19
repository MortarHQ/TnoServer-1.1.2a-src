package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EmissiveRenderLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
   private static final String EMISSIVE_SUFFIX = "_emissive";
   private final Function<T, ResourceLocation> emissiveTextureGetter;

   public EmissiveRenderLayer(GeoRenderer<T> renderer, Function<T, ResourceLocation> emissiveTextureGetter) {
      super(renderer);
      this.emissiveTextureGetter = emissiveTextureGetter;
   }

   public EmissiveRenderLayer(GeoRenderer<T> renderer, ResourceLocation emissiveTexture) {
      this(renderer, animatable -> emissiveTexture);
   }

   public EmissiveRenderLayer(GeoRenderer<T> renderer) {
      this(renderer, animatable -> addEmissiveTextureSuffix(renderer.getTextureLocation(animatable)));
   }

   protected ResourceLocation getTextureResource(T animatable) {
      return this.emissiveTextureGetter.apply(animatable);
   }

   public RenderType getRenderType(T animatable, ResourceLocation texture) {
      return RenderType.entityTranslucentEmissive(texture);
   }

   public void render(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ResourceLocation emissiveTexture = this.getTextureResource(animatable);
      RenderType emissiveRenderType = this.getRenderType(animatable, emissiveTexture);
      this.getRenderer()
         .reRender(
            bakedModel,
            poseStack,
            bufferSource,
            animatable,
            emissiveRenderType,
            bufferSource.getBuffer(emissiveRenderType),
            partialTick,
            15728640,
            packedOverlay,
            -1
         );
   }

   public static ResourceLocation addEmissiveTextureSuffix(ResourceLocation texture) {
      return texture.withPath(texture.getPath().substring(0, texture.getPath().length() - ".png".length()) + "_emissive.png");
   }

   public static <T extends GeoAnimatable> EmissiveRenderLayer<T> fromBaseTexture(GeoRenderer<T> animatable, ResourceLocation baseTexture) {
      if (animatable.getGeoModel() instanceof DefaultedGeoModel<T> defaultedModel) {
         return new EmissiveRenderLayer<>(animatable, defaultedModel.buildFormattedTexturePath(baseTexture.withSuffix("_emissive")));
      } else {
         throw new IllegalArgumentException("Given a renderer with a non-defaulted geo model - I don't know how to use that!");
      }
   }
}
