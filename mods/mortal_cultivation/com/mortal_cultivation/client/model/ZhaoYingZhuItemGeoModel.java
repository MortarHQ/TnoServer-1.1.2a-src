package com.mortal_cultivation.client.model;

import com.mortal_cultivation.item.ZhaoYingZhuItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class ZhaoYingZhuItemGeoModel extends GeoModel<ZhaoYingZhuItem> {
   public RenderType getRenderType(ZhaoYingZhuItem animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }

   public ResourceLocation getModelResource(ZhaoYingZhuItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/entity/zhao_ying_zhu.geo.json");
   }

   public ResourceLocation getTextureResource(ZhaoYingZhuItem animatable) {
      return ZhaoYingZhuModel.BASE_TEXTURE;
   }

   public ResourceLocation getAnimationResource(ZhaoYingZhuItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/entity/zhao_ying_zhu.animation.json");
   }
}
