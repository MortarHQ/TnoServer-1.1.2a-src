package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.VillagerPrisonerModel;
import com.benji.netherman.common.entity.VillagerPrisonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VillagerPrisonerRenderer extends GeoEntityRenderer<VillagerPrisonerEntity> {
   public VillagerPrisonerRenderer(Context renderManager) {
      super(renderManager, new VillagerPrisonerModel());
   }
}
