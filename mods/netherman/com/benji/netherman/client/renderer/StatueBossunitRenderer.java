package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.StatueBossunitModel;
import com.benji.netherman.common.entity.StatueBossunitEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StatueBossunitRenderer extends GeoEntityRenderer<StatueBossunitEntity> {
   public StatueBossunitRenderer(Context renderManager) {
      super(renderManager, new StatueBossunitModel());
      this.shadowRadius = 0.5F;
   }
}
