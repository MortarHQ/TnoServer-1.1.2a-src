package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.LivingData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GeckoSunblockLayer<T extends LivingEntity & GeoEntity> extends GeoRenderLayer<T> {
   private static final ResourceLocation SUNBLOCK_ARMOR = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/sunblock_glow.png");

   public GeckoSunblockLayer(GeoRenderer<T> entityRendererIn, Context context) {
      super(entityRendererIn);
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
      if (DataHandler.<LivingData>getData(animatable, DataHandler.LIVING_DATA).getHasSunblock()) {
         float f = animatable.tickCount + partialTick;
         RenderType renderTypeSwirl = RenderType.energySwirl(this.getTextureLocation(), this.xOffset(f), f * 0.01F);
         this.getRenderer()
            .reRender(
               this.getDefaultBakedModel(animatable),
               poseStack,
               bufferSource,
               animatable,
               renderTypeSwirl,
               bufferSource.getBuffer(renderTypeSwirl),
               partialTick,
               packedLight,
               OverlayTexture.NO_OVERLAY,
               ARGB32.colorFromFloat(1.0F, 1.0F, 1.0F, 0.1F)
            );
      }
   }

   protected float xOffset(float p_225634_1_) {
      return p_225634_1_ * 0.02F;
   }

   protected ResourceLocation getTextureLocation() {
      return SUNBLOCK_ARMOR;
   }
}
