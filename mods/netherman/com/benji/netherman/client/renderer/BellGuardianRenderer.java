package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.BellGuardianModel;
import com.benji.netherman.common.entity.BellGuardianEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BellGuardianRenderer extends GeoEntityRenderer<BellGuardianEntity> {
   public BellGuardianRenderer(Context renderManager) {
      super(renderManager, new BellGuardianModel());
      this.shadowRadius = 0.5F;
   }
}
