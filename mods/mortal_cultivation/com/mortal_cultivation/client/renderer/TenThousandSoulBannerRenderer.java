package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.client.model.TenThousandSoulBannerModel;
import com.mortal_cultivation.entity.TenThousandSoulBannerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TenThousandSoulBannerRenderer extends GeoEntityRenderer<TenThousandSoulBannerEntity> {
   public TenThousandSoulBannerRenderer(Context renderManager) {
      super(renderManager, new TenThousandSoulBannerModel());
      this.shadowRadius = 0.0F;
   }

   public ResourceLocation getTextureLocation(TenThousandSoulBannerEntity entity) {
      return this.getGeoModel().getTextureResource(entity);
   }
}
