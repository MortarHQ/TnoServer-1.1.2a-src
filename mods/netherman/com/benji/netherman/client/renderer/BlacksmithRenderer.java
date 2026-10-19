package com.benji.netherman.client.renderer;

import com.benji.netherman.client.layer.BlacksmithHintLayer;
import com.benji.netherman.client.model.BlacksmithModel;
import com.benji.netherman.common.entity.BlacksmithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlacksmithRenderer extends GeoEntityRenderer<BlacksmithEntity> {
   public BlacksmithRenderer(Context renderManager) {
      super(renderManager, new BlacksmithModel());
      this.shadowRadius = 0.5F;
      this.addRenderLayer(new BlacksmithHintLayer(this));
   }
}
