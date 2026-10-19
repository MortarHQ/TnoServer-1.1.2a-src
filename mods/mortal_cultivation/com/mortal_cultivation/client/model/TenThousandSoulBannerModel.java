package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.TenThousandSoulBannerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TenThousandSoulBannerModel extends GeoModel<TenThousandSoulBannerEntity> {
   public ResourceLocation getModelResource(TenThousandSoulBannerEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/ten_thousand_soul_banner.geo.json");
   }

   public ResourceLocation getTextureResource(TenThousandSoulBannerEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/ten_thousand_soul_banner.png");
   }

   public ResourceLocation getAnimationResource(TenThousandSoulBannerEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/ten_thousand_soul_banner.animation.json");
   }
}
