package com.benji.netherman.client.renderer;

import com.benji.netherman.client.layer.BelieverPrayEmissiveLayer;
import com.benji.netherman.client.model.BelieverModel;
import com.benji.netherman.common.entity.BelieverEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BelieverRenderer extends GeoEntityRenderer<BelieverEntity> {
   public BelieverRenderer(Context renderManager) {
      super(renderManager, new BelieverModel());
      this.shadowRadius = 0.5F;
      this.addRenderLayer(new BelieverPrayEmissiveLayer(this));
   }
}
