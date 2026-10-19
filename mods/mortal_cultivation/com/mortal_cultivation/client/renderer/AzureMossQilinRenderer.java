package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.client.model.AzureMossQilinModel;
import com.mortal_cultivation.entity.AzureMossQilinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class AzureMossQilinRenderer extends GeoEntityRenderer<AzureMossQilinEntity> {
   public AzureMossQilinRenderer(Context context) {
      super(context, new AzureMossQilinModel());
      this.shadowRadius = 1.0F;
   }

   protected float getShadowRadius(AzureMossQilinEntity entity) {
      return this.shadowRadius * entity.getScale();
   }
}
