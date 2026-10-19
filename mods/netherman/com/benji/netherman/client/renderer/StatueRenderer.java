package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.StatueModel;
import com.benji.netherman.common.entity.StatueEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StatueRenderer extends GeoEntityRenderer<StatueEntity> {
   public StatueRenderer(Context renderManager) {
      super(renderManager, new StatueModel());
      this.shadowRadius = 0.5F;
   }
}
