package com.benji.netherman.client.renderer;

import com.benji.netherman.client.layer.TraderHintLayer;
import com.benji.netherman.client.model.TraderModel;
import com.benji.netherman.common.entity.TraderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TraderRenderer extends GeoEntityRenderer<TraderEntity> {
   public TraderRenderer(Context renderManager) {
      super(renderManager, new TraderModel());
      this.shadowRadius = 0.5F;
      this.addRenderLayer(new TraderHintLayer(this));
   }
}
