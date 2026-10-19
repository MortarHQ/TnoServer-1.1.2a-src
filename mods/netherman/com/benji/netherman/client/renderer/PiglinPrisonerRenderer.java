package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.PiglinPrisonerModel;
import com.benji.netherman.common.entity.PiglinPrisonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PiglinPrisonerRenderer extends GeoEntityRenderer<PiglinPrisonerEntity> {
   public PiglinPrisonerRenderer(Context renderManager) {
      super(renderManager, new PiglinPrisonerModel());
   }
}
