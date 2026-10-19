package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.client.model.MillenniumGinsengModel;
import com.mortal_cultivation.entity.MillenniumGinsengEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class MillenniumGinsengRenderer extends GeoEntityRenderer<MillenniumGinsengEntity> {
   public MillenniumGinsengRenderer(Context context) {
      super(context, new MillenniumGinsengModel());
      this.shadowRadius = 0.25F;
      this.shadowStrength = 0.65F;
   }
}
