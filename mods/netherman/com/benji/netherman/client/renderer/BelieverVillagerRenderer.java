package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.BelieverVillagerModel;
import com.benji.netherman.common.entity.BelieverVillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BelieverVillagerRenderer extends GeoEntityRenderer<BelieverVillagerEntity> {
   public BelieverVillagerRenderer(Context renderManager) {
      super(renderManager, new BelieverVillagerModel());
      this.shadowRadius = 0.5F;
   }
}
