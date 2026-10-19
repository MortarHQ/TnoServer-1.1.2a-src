package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class ZhaoYingZhuModel extends GeoModel<ZhaoYingZhuEntity> {
   public static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/zhao_ying_zhu.png");
   public static final ResourceLocation STORED_TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/zhao_ying_zhu_stored.png");

   public RenderType getRenderType(ZhaoYingZhuEntity animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }

   public ResourceLocation getModelResource(ZhaoYingZhuEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/entity/zhao_ying_zhu.geo.json");
   }

   public ResourceLocation getTextureResource(ZhaoYingZhuEntity animatable) {
      return animatable.isFilled() ? STORED_TEXTURE : BASE_TEXTURE;
   }

   public ResourceLocation getAnimationResource(ZhaoYingZhuEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/entity/zhao_ying_zhu.animation.json");
   }
}
