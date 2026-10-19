package com.benji.netherman.client.renderer.entity;

import com.benji.netherman.client.layer.GhastlyHintLayer;
import com.benji.netherman.client.model.GhastlyModel;
import com.benji.netherman.common.entity.GhastlyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GhastlyRenderer extends GeoEntityRenderer<GhastlyEntity> {
   public GhastlyRenderer(Context renderManager) {
      super(renderManager, new GhastlyModel());
      this.shadowRadius = 0.3F;
      this.addRenderLayer(new GhastlyHintLayer(this));
   }
}
